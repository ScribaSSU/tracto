package com.scribassu.tracto.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupEvents implements ApplicationListener<ContextRefreshedEvent> {

    private final TablesDataInitializer tablesDataInitializer;

    @Autowired
    public StartupEvents(TablesDataInitializer tablesDataInitializer) {
        this.tablesDataInitializer = tablesDataInitializer;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        tablesDataInitializer.init();
    }
}