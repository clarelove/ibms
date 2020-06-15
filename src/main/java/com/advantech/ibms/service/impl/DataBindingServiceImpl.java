package com.advantech.ibms.service.impl;

import com.advantech.ibms.POJO.Symbol;
import com.advantech.ibms.service.DataBindingService;
import com.advantech.ibms.util.CommonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@PropertySource(value="classpath:application.properties",encoding = "UTF-8")
public class DataBindingServiceImpl implements DataBindingService {
    @Value("${baseurl}")
    private String url;
    @Value("${org_id}")
    private int org_id;
    @Override
    public JSONObject parse(String path) {
        path = path.replace(" ","%20");
        String display = null;
        try{
            display = CommonUtil.getDisplay(url+path,org_id);
        }catch (IOException e){
            e.printStackTrace();
        }
        return JSONObject.parseObject(display);
    }

    @Override
    public JSONObject replace(String[] target, Object now,JSONObject jsonObject,String [] key,String value) {
        key = new String[]{"i"};
        target = new String[]{"i"};
        now = 666;
        value = "6192";
        JSONObject destination ;
        JSONArray d =  jsonObject.getJSONArray("d");
        for(int i = 0; i < d.size();i++){
//            System.out.println(d.getJSONObject(i).toJSONString());
            destination = d.getJSONObject(i);
            for(int j = 0;j<key.length-1;j++){
                destination = destination.getJSONObject(key[j]);
            }
            if(value.equals(destination.getString(key[key.length-1]))){
                System.out.println("true");
                
            }
        }
        return null;
    }
}
