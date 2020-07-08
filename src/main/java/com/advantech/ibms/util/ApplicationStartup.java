package com.advantech.ibms.util;

import com.advantech.ibms.POJO.DataSource;
import com.advantech.ibms.service.DataBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    DataBindingService dataBindingService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        dataBindingService.login();
        Map<String,String> map = new HashMap<>();
        map.put("CWP","PUMPS");
        map.put("CHP","PUMPS");
        map.put("二次冰水泵","EKG");
        map.put("冷水機組","CHU");
        map.put("冷卻水塔","PUMP");

        dataBindingService.replace(
                new String [] {"p","dataBindings","a"},
                new String [] {"custom.animatePercentage3D"},
                new DataSource(),
                dataBindingService.parse("scenes/modify/冷源2.json"),
                new String []{"p","displayName"},
                map,
                new String [] {":RUN"}
        );

    }
}
