package com.advantech.ibms.service;

import com.advantech.ibms.POJO.Symbol;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;

public interface DataBindingService {
     JSONObject parse(String path);
     JSONObject replace(String[] path,Object now,JSONObject jsonObject,String [] key,String value);

}
