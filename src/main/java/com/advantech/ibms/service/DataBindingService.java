package com.advantech.ibms.service;

import com.advantech.ibms.POJO.Symbol;

import java.util.LinkedHashMap;

public interface DataBindingService {
     Symbol[] parse(String json);
}
