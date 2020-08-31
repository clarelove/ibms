package com.advantech.ibms.util;

import com.advantech.ibms.POJO.DataSource;
import com.advantech.ibms.service.DataBindingService;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
//        map.put("PUMPS","pumps");
//        map.put("CHU","冰水主機");
//        map.put("PUMP","水塔");
//
//        dataBindingService.replace(
//                new String [] {"p","dataBindings","a"},
//                new String [] {"custom.animatePercentage3D"},
//                new DataSource(),
//                dataBindingService.parse("scenes/modify/冷源3D空間.json"),
//                new String []{"p","displayName"},
//                map,
//                new String [] {":RUN"},
//                new String [] {"冷源系統","E栋","floors",null}
//        );

//        String  s = JSONObject.toJSONString(dataBindingService.parse("scenes/test/冷源3D空間.json"),SerializerFeature.DisableCircularReferenceDetect ,SerializerFeature.WriteMapNullValue);
//        System.out.println(s);


//        Map<String,String> map = new HashMap<>();
//        map.put("FIRE","billboard");
//        JSONObject  jsonObject=  dataBindingService.replace(
//                new String [] {"p","dataBindings","a"},
//                new String [] {"custom.animatePercentage3D"},
//                new DataSource(),
//                dataBindingService.parse("scenes/iBMS demo/研華樓層圖/消防/bk/5F.json"),
//                new String []{"s","shape3d"},
//                map,
//                new String [] {"FIRE:FAULT"},
//                new String [] {"消防系統","E栋","5层",null},
//                new String[] {"a","device_number"}
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
//                new String [] {"EXAT:FAULT"},
//                new String [] {"消防系統","E栋","5层",null},
//                new String[] {"a","device_number"}
//        );


        Map<String,String> map = new HashMap<>();
        map.put("LGH","燈s");
        dataBindingService.replace(
                new String [] {"p","dataBindings","a"},
                new String [] {"custom.animatePercentage3D"},
                new DataSource(),
                dataBindingService.parse("scenes/iBMS demo/研華樓層圖/照明/B1停車場.json"),
                new String []{"p","displayName"},
                map,
                new String [] {"LGH:ST"},
                new String [] {"照明系統","E栋","地下1层",null},
                new String [] {"p", "toolTip"}
        );


        //空调
//        Map<String,String> map = new HashMap<>();
//        map.put("VAV","VAV billboard");
//        dataBindingService.replace(
//                new String [] {"p","dataBindings","a"},
//                new String [] {"indoor_temperature","air_flow","temperature_setting","throttle_opening"},
//                new DataSource(),
//                dataBindingService.parse("scenes/iBMS demo/研華樓層圖/空調/6F.json"),
//                new String []{"p","displayName"},
//                map,
//                new String [] {":VTEM",":VAVE",":VTES",":VDMP"},
//                new String [] {"空調末端系統","E栋","6层",null}
//        );
        //空调箱
//        Map<String,String> map = new HashMap<>();
//        map.put("AHU","billboard");
//        JSONObject j =  dataBindingService.replace(
//                new String [] {"p","dataBindings","a"},
//                new String [] {"co2","windOpening","temp","pressure","waterOpening","frequency"},
//                new DataSource(),
//                dataBindingService.parse("scenes/iBMS demo/研華樓層圖/空調/6F.json"),
//                new String []{"s","shape3d"},
//                map,
//                new String [] {"MD:OUT","PID1:PV1","AIT1:PV1","AIP1:PV1","TV:FOUT","PID5:PV1"},
//                new String [] {"空調末端系統","E栋","6层",null},
//                new String[]{"a", "device_number"}
//        );
//        map.clear();
//        map.put("SFG","風機billboard");
//        dataBindingService.replace(
//                new String [] {"p","dataBindings","a"},
//                new String [] {"input_voltage","output_power","input_electricity","rotate_speed"},
//                new DataSource(),
//                j,
//                new String []{"p","displayName"},
//                map,
//                new String [] {"SFG:OUT","SFG:PV1","SFG:PV1","SFG:SFHZ"},
//                new String [] {"空調末端系統","E栋","6层",null},
//                new String[]{"p", "toolTip"}
//        );
        //        map.clear();
//        map.put("FCUS","送風機");
//        dataBindingService.replace(
//                new String [] {"p","dataBindings","a"},
//                new String [] {"custom.animatePercentage3D"},
//                new DataSource(),
//                dataBindingService.parse("scenes/iBMS demo/研華樓層圖/空調/B1停車場.json"),
//                new String []{"p","displayName"},
//                map,
//                new String [] {":FON"},
//                new String [] {"空調末端系統","E栋","地下1层",null}
//        );

    }
}
