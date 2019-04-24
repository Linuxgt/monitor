package com.linuxgt.demo.thread.monitor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by liuteng on 2019/4/22
 */
public class ThreadPoolMonitorListener implements ApplicationListener, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void onApplicationEvent(ApplicationEvent event) {
        ThreadPoolMetrics threadPoolMetrics = new ThreadPoolMetrics(this.applicationContext);
        threadPoolMetrics.start();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
