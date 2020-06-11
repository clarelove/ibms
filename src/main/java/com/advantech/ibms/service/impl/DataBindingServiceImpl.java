package com.advantech.ibms.service.impl;

import com.advantech.ibms.POJO.Symbol;
import com.advantech.ibms.service.DataBindingService;
import com.advantech.ibms.util.CommonUtil;
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
    public Symbol[] parse(String json) {
        System.out.println(url);
        try{
            String display = CommonUtil().getDisplay(url+"displays/"+"自来水/設備監控影像.json",org_id);
        }catch (IOException e){
            e.printStackTrace();
        }
        return new Symbol[0];
    }
}
