package com.advantech.ibms.POJO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataSource {
    private String id;
    private Map<String,String> animatedOptions;
    private JSONArray targets;
    private JSONObject target;

    public void setId(String id) {
        this.id = id;
    }

    public void setAnimatedOptions(Map<String, String> animatedOptions) {
        this.animatedOptions = animatedOptions;
    }

    public void setTargets(JSONArray targets) {
        this.targets = targets;
    }
    public void addTarget(JSONObject target){
        this.target = target;
        this.targets.clear();
        this.targets.add(target);
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getAnimatedOptions() {
        return animatedOptions;
    }

    public JSONArray getTargets() {
        return targets;
    }
    String tamplate = "{\"id\":\"tamplate\",\"animatedOptions\":{\"advancedAnimate\":false,\"func\":\"__ht__function(value){\\nreturn value;\\n}\",\"details\":{\"directFeed\":true,\"matchList\":[]},\"animateCondition\":\"setMatch\",\"directFeed\":true},\"targets\":[{\"sourceType\":\"simulation\",\"formatType\":\"timeseries\",\"scDataType\":\"value\",\"target\":\"random#0#100\",\"dataType\":\"random\",\"minimum\":\"0\",\"maximum\":\"100\",\"fixConst\":false}]}";
    public DataSource(){
        Map jsonObject =  JSONObject.parseObject(this.tamplate);
        for(Object key: jsonObject.entrySet()){
            System.out.println(key.toString());
        }
        this.id = (String) jsonObject.get("id");
        this.animatedOptions = (Map)jsonObject.get("animatedOptions");
        this.targets = (JSONArray) jsonObject.get("targets");
    }

    public static void main(String[] args) {
        System.out.println(new DataSource().toString());;
    }
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
    public JSONObject toJSON() {
        return JSONObject.parseObject(toString());
    }
}
