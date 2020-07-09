package com.advantech.ibms.service;

import com.advantech.ibms.POJO.DataSource;
import com.advantech.ibms.POJO.Symbol;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public interface DataBindingService {
     JSONObject parse(String path);
     JSONObject replace(String[] workdir, String[] targets, DataSource dataSource, JSONObject jsonObject, String [] key, Map<String,String> symbol,String[] sensor,String [] topo);
     boolean update(String json,String path);
     String login();
     String[] targetCompose(String [] topo, String[] sensors, String feature,String displayName) throws IOException;
}
