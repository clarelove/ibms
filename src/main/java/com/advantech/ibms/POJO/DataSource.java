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
    //冷源
    String template = "{\"animatedOptions\":{\"advancedAnimate\":true,\"func\":\"__ht__function(v1,v2,v3){\\r\\n    if(v1 === 1){\\r\\n        offsetArray[0]=1\\r\\n        v3.data._children._as.forEach(child =>{\\r\\n            child.s('shape3d.blend',\\\"rgba(51,115,145,0.30)\\\")\\r\\n            child.s('wf.color',\\\"rgb(94,156,189)\\\")\\r\\n        })\\r\\n    }else{\\r\\n        offsetArray[0]=0\\r\\n        v3.data._children._as.forEach(child =>{\\r\\n            child.s('shape3d.blend',\\\"rgba(87,87,87,0.60)\\\")\\r\\n            child.s('wf.color',\\\"rgb(36,36,36)\\\")\\r\\n        })\\r\\n    }\\r\\n}\",\"details\":{},\"advFunc\":\"function(v1,v2,v3){\\r\\n    if(v1 === 1){\\r\\n        offsetArray[0]=1\\r\\n        v3.data._children._as.forEach(child =>{\\r\\n            child.s('shape3d.blend',\\\"rgba(51,115,145,0.30)\\\")\\r\\n            child.s('wf.color',\\\"rgb(94,156,189)\\\")\\r\\n        })\\r\\n    }else{\\r\\n        offsetArray[0]=0\\r\\n        v3.data._children._as.forEach(child =>{\\r\\n            child.s('shape3d.blend',\\\"rgba(87,87,87,0.60)\\\")\\r\\n            child.s('wf.color',\\\"rgb(36,36,36)\\\")\\r\\n        })\\r\\n    }\\r\\n}\"},\"id\":\"template\",\"targets\":[{\"sourceType\":\"simulation\",\"formatType\":\"timeseries\",\"scDataType\":\"value\",\"target\":\"random#0#1\",\"dataType\":\"random\",\"minimum\":\"0\",\"maximum\":\"1\",\"fixConst\":false}]}";
    //    String template = "{\"id\":\"template\",\"animatedOptions\":{\"advancedAnimate\":false,\"func\":\"__ht__function(value){\\nreturn value;\\n}\",\"details\":{\"directFeed\":true,\"matchList\":[]},\"animateCondition\":\"setMatch\",\"directFeed\":true},\"targets\":[{\"sourceType\":\"simulation\",\"formatType\":\"timeseries\",\"scDataType\":\"value\",\"target\":\"random#0#100\",\"dataType\":\"random\",\"minimum\":\"0\",\"maximum\":\"100\",\"fixConst\":false}]}";
//    String template = "{\"id\": \"1594015433075_0\",     \"animatedOptions\": {         \"advancedAnimate\": true,         \"func\": \"__ht__function(v1, v2, v3) {\\n    if (v1 == 1) {\\n        v3.data._children._as.forEach(item => {\\n            if (item._displayName === '上')\\n                item.s('all.color', 'rgba(111,111,111,0.3)')\\n            else if (item._displayName === '燈管'){\\n                item.s('shape3d.color','rgb(255,255,255)')\\n            }\\n            else if (item._displayName === '隔')\\n                item.s('all.color', 'rgb(110,110,110)')\\n            else\\n                item.s('all.color', 'rgba(153,153,153,0.5)')\\n\\n        })\\n    }else{\\n        v3.data._children._as.forEach(item => {\\n            if (item._displayName === '上')\\n                item.s('all.color', 'rgba(89,89,89,0.3)')\\n            else if (item._displayName === '燈管'){\\n                item.s('shape3d.color','rgb(99,99,99)')\\n            }\\n            else if (item._displayName === '隔')\\n                item.s('all.color', 'rgb(77,77,77)')\\n            else\\n                item.s('all.color', 'rgba(84,84,84,0.5)')\\n        })\\n    }\\n    // return value;\\n}\",         \"details\": {         },         \"advFunc\": \"function(v1, v2, v3) {\\n    if (v1 == 1) {\\n        v3.data._children._as.forEach(item => {\\n            if (item._displayName === '上')\\n                item.s('all.color', 'rgba(111,111,111,0.3)')\\n            else if (item._displayName === '燈管'){\\n                item.s('shape3d.color','rgb(255,255,255)')\\n            }\\n            else if (item._displayName === '隔')\\n                item.s('all.color', 'rgb(110,110,110)')\\n            else\\n                item.s('all.color', 'rgba(153,153,153,0.5)')\\n\\n        })\\n    }else{\\n        v3.data._children._as.forEach(item => {\\n            if (item._displayName === '上')\\n                item.s('all.color', 'rgba(89,89,89,0.3)')\\n            else if (item._displayName === '燈管'){\\n                item.s('shape3d.color','rgb(99,99,99)')\\n            }\\n            else if (item._displayName === '隔')\\n                item.s('all.color', 'rgb(77,77,77)')\\n            else\\n                item.s('all.color', 'rgba(84,84,84,0.5)')\\n        })\\n    }\\n    // return value;\\n}\"     },     \"targets\": [         {             \"sourceType\": \"simulation\",             \"formatType\": \"timeseries\",             \"scDataType\": \"value\",             \"target\": \"random#0#100\",             \"dataType\": \"random\",             \"minimum\": \"0\",             \"maximum\": \"100\",             \"fixConst\": false         }     ] } ";
    public DataSource(){
        Map jsonObject =  JSONObject.parseObject(this.template);
        for(Object key: jsonObject.entrySet()){
        }
        this.id = (String) jsonObject.get("id");
        this.animatedOptions = (Map)jsonObject.get("animatedOptions");
        this.targets = (JSONArray) jsonObject.get("targets");
    }

    public static void main(String[] args) {
    }
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
    public JSONObject toJSON() {
        return JSONObject.parseObject(toString());
    }
}
