package com.advantech.ibms.util;

import com.advantech.ibms.POJO.DataSource;
import com.advantech.ibms.service.DataBindingService;
import com.alibaba.fastjson.JSONObject;
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
//        Map<String,String> map = new HashMap<>();
//        map.put("CWP","PUMPS");
//        map.put("CHP","PUMPS");
//        map.put("二次冰水泵","PUMP");
//        map.put("冷水機組","CHU");
//        map.put("冷卻水塔","PUMP");
//
//        dataBindingService.replace(
//                new String [] {"p","dataBindings","a"},
//                new String [] {"custom.animatePercentage3D"},
//                new DataSource(),
//                dataBindingService.parse("scenes/modify/冷源2.json"),
//                new String []{"p","displayName"},
//                map,
//                new String [] {":RUN"}
//        );


//        Map<String,String> map = new HashMap<>();
//        map.put("FIRE","billboard");
//        JSONObject  jsonObject=  dataBindingService.replace(
//                new String [] {"p","dataBindings","a"},
//                new String [] {"custom.animatePercentage3D"},
//                new DataSource(),
//                dataBindingService.parse("scenes/iBMS demo/研華樓層圖/消防/B3停車場.json"),
//                new String []{"s","shape3d"},
//                map,
//                new String [] {":FAULT"},
//                new String [] {"消防系統","E栋","地下3层",null}
//        );
//        map.clear();
//        map.put("EXAT","billboard");
//        dataBindingService.replace(
//                new String [] {"p","dataBindings","a"},
//                new String [] {"custom.animatePercentage3D"},
//                new DataSource(),
//                jsonObject,
//                new String []{"s","shape3d"},
//                map,
//                new String [] {":FAULT"},
//                new String [] {"消防系統","E栋","地下3层",null}
//        );


//        Map<String,String> map = new HashMap<>();
//        map.put("LGH","燈s");
//        dataBindingService.replace(
//                new String [] {"p","dataBindings","a"},
//                new String [] {"custom.animatePercentage3D"},
//                new DataSource(),
//                dataBindingService.parse("scenes/iBMS demo/研華樓層圖/照明/B3.json"),
//                new String []{"p","displayName"},
//                map,
//                new String [] {":ST"},
//                new String [] {"照明系統","E栋","地下3层",null}
//
//        );


        //空调
        Map<String,String> map = new HashMap<>();
        map.put("VAV","VAV billboard");
        dataBindingService.replace(
                new String [] {"p","dataBindings","a"},
                new String [] {"indoor_temperature","air_flow","temperature_setting","throttle_opening"},
                new DataSource(),
                dataBindingService.parse("scenes/iBMS demo/研華樓層圖/空調/3F.json"),
                new String []{"p","displayName"},
                map,
                new String [] {":VTEM",":VAVE",":VTES",":VDMP"},
                new String [] {"空調末端系統","E栋","3层",null}

        );
    }
}
