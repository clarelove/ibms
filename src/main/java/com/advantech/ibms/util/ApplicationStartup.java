package com.advantech.ibms.util;

import com.advantech.ibms.POJO.DataSource;
import com.advantech.ibms.service.DataBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    DataBindingService dataBindingService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        dataBindingService.login();
//        try{
//            dataBindingService.targetCompose(new String[]{"风机盘管","风机盘管","31层"},new String[]{"asd","sss"},"低区设备间");
//        }catch(IOException e){
//            e.printStackTrace();
//        }
        dataBindingService.replace(
                new String [] {"p","dataBindings","a"},
                new String [] {"custom.animatePercentage3D"},
                new DataSource(),
                dataBindingService.parse("scenes/iBMS demo/研華樓層圖/照明/1F.json"),
                new String []{"p","displayName"},
                "燈s"
        );

    }
}
