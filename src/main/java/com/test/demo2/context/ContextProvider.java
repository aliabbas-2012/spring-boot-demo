package com.test.demo2.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextProvider.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static Object getBean(@SuppressWarnings("rawtypes") Class cls) {
        return ContextProvider.applicationContext.getBean(cls);
    }
}