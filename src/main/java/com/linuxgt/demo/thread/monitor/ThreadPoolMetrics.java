package com.linuxgt.demo.thread.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by liuteng on 2019/4/22
 */
@Slf4j
public class ThreadPoolMetrics extends Thread {

    private ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();

    private ApplicationContext applicationContext;

    ThreadPoolMetrics(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void run() {
        threadLocal.set(true);
        while (threadLocal.get()) {
            try {
                Thread.sleep(5000);
                if (this.applicationContext == null) {
                    log.error("thread name:{},spring context initializing", Thread.currentThread().getName());
                    threadLocal.set(false);
                } else {
                    log.info("spring context initialized");
                    String[] beans = this.applicationContext.getBeanNamesForType(ThreadPoolExecutor.class);
                    Arrays.asList(beans).forEach(o -> {
                        log.info("bean:" + o);
                        ThreadPoolExecutor tpe = this.applicationContext.getBean(o, ThreadPoolExecutor.class);
                        log.info("getActiveCount:{}", tpe.getActiveCount());
                        log.info("getCompletedTaskCount:{}", tpe.getCompletedTaskCount());
                        log.info("getCorePoolSize:{}", tpe.getCorePoolSize());
                        log.info("getKeepAliveTime:{}", tpe.getKeepAliveTime(TimeUnit.MILLISECONDS));
                        log.info("getLargestPoolSize:{}", tpe.getLargestPoolSize());
                        log.info("getMaximumPoolSize:{}", tpe.getMaximumPoolSize());
                        log.info("getPoolSize:{}", tpe.getPoolSize());
                        log.info("getQueue size:{}", tpe.getQueue().size());
                        log.info("getTaskCount:{}", tpe.getTaskCount());
                    });
                }
            } catch (InterruptedException e) {
                log.error("InterruptedException:{}", e.getMessage());
            }
        }
    }
}
