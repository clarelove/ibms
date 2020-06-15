package com.advantech.ibms.util;

import com.advantech.ibms.service.DataBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    DataBindingService dataBindingService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        dataBindingService.replace(null,null,dataBindingService.parse("scenes/iBMS demo/研華樓層圖/testforline.json"),null,null);
    }
}
