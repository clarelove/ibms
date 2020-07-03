package com.advantech.ibms.service.impl;

import com.advantech.ibms.POJO.DataSource;
import com.advantech.ibms.POJO.Node;
import com.advantech.ibms.POJO.Symbol;
import com.advantech.ibms.service.DataBindingService;
import com.advantech.ibms.util.CommonUtil;
import com.advantech.ibms.util.HttpClientUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.*;

@Service
@PropertySource(value="classpath:application.properties",encoding = "UTF-8")
public class DataBindingServiceImpl implements DataBindingService {
    @Value("${baseurl}")
    private String baseurl;
    @Value("${apmurl}")
    private String apmurl;
    private String accesstoken;
    @Value("${org_id}")
    private int org_id;
    private List<Header> headers;
    private HttpEntity requestEntity;
    @Value("${user}")
    private String userName = "zhihao.jia@advantech.com.cn";
    @Value("${password}")
    private String password;
    private boolean flag = true;
    private int id;
    private LinkedHashMap<String,String> globalobject;
    private StringBuffer targetstring;
    //name: sensorname
    private String response;
    @Value("${tenant-id}")
    private String tenantId;
    @Override
    public String[] targetCompose(String[] topo, String[] sensors, String feature) throws IOException{
        String[] resmap = new String[sensors.length];
        String uri;
        List<Header> headerList = new ArrayList<>();
        headerList.add(new BasicHeader("Authorization", "Bearer " + accesstoken));
        System.out.println("Bearer "+accesstoken);
        headerList.add(new BasicHeader("Content-Type","application/json"));
        headerList.add(new BasicHeader("tenant-id",tenantId));
//        headerList.add(new BasicHeader("host",""));
        StringBuffer target = new StringBuffer();
        LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
        int id;
        String response = null;
        if(flag){
            map.put("sourceType","apm");
            map.put("formatType","timeseries");
            map.put("scDataType","value");
            String Name = topo[0];
            map.put("topo",Name);
            target.append(Name).append("|");


            uri = apmurl+"/topo/root/node?topoName="+topo[0];
            response = HttpClientUtil.getRequest(uri,headerList);
            id =findid(Name,response);
            System.out.println("root"+id);
            target.append(id).append("|");
            map.put("root",new Node(id,true).toString());


            uri = apmurl+"/topo/progeny/node?nodeId="+id+"&type=child";
            response = HttpClientUtil.getRequest(uri,headerList);
            id = findid(topo[1],response);
            System.out.println("node1"+id);
            target.append(id).append("|");
            map.put("Node1",new Node(id,true).toString());


            uri = apmurl+"/topo/progeny/node?nodeId="+id+"&type=child";
            response = HttpClientUtil.getRequest(uri,headerList);
            id = findid(topo[2],response);
            System.out.println("node2"+id);
            target.append(id).append("|");
            map.put("Node2",new Node(id,true).toString());

            uri = apmurl+"/topo/progeny/node?nodeId="+id+"&type=child";
            response = HttpClientUtil.getRequest(uri,headerList);
            id = findid(topo[3],response);
            System.out.println("node3"+id);
            target.append(id).append("|");
            map.put("Node3",new Node(id,true).toString());


            uri = apmurl+"/topo/progeny/node?nodeId="+id+"&type=child";
            response = HttpClientUtil.getRequest(uri,headerList);
            flag = false;
            this.id = id;
            this.globalobject = map;
            this.targetstring = new StringBuffer(target);
            this.response = response;
        }else {
            response = this.response;
            id = this.id;
            map = this.globalobject;
            target = new StringBuffer(this.targetstring);
        }
        //maybe change
        id = findid(feature,response);
        System.out.println("node4"+id);
        if(id == 0){
            System.out.println("+++++++++++++++++++++++++++++++++warning,nothing match++++++++++++++++++++++++++++++++++");
//            System.exit(0);
        }
        target.append(id).append("|");
        map.put("Node4",new Node(id,false).toString());
        map.put("target",target.toString());

        for (int i = 0;i<sensors.length;i++) {
            String sensorName = "monitor-"+sensors[i];
            target.append(sensorName).append("|real");
            map.put("sensor",sensorName);
            map.put("dataType","real");
            resmap[i]=JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue);
        }
        return resmap;
    }

    @Override
    public JSONObject parse(String path) {
        path = path.replace(" ","%20");
        String display = null;
        try{
            display = CommonUtil.getDisplay(baseurl+path,org_id);
        }catch (IOException e){
            e.printStackTrace();
        }
        return JSONObject.parseObject(display);
    }
    private Integer  findid(String Name,String response) throws IOException{
        JSONArray jSONArray = JSONArray.parseArray(response);
        for(int i = 0;i<jSONArray.size();i++){
            if(Name.equals(jSONArray.getJSONObject(i).getString("description")))
                return jSONArray.getJSONObject(i).getInteger("id");
            else if(Name.equals(jSONArray.getJSONObject(i).getString("name")))
                return jSONArray.getJSONObject(i).getInteger("id");
        }
        return 0;
    }
    public String getFeature(String [] path,JSONObject symbol){
        for (int i = 0;i<path.length-1;i++) {
            if(symbol.getJSONObject(path[i])!=null){
                    symbol = symbol.getJSONObject(path[i]);
            }else{
                System.out.println("null");
                return "null";
            }
        }

        return symbol.getString(path[path.length-1]);
    }
    @Override
    public JSONObject replace(String[] workdir, String[] targets, DataSource dataSource1, JSONObject jsonObject, String [] key, String value) {
//        key = new String[]{"i"};
//        workdir = new String[]{};
//        targets = new String[]{"i"};
//        dataSource = 666;
//        value = "6192";
        JSONObject destination ;
        JSONArray d =  jsonObject.getJSONArray("d");
        for(int i = 0; i < d.size();i++){
//            System.out.println(d.getJSONObject(i).toJSONString());
            destination = d.getJSONObject(i);
            for(int j = 0;j<key.length-1;j++){
                destination = destination.getJSONObject(key[j]);
            }
            if(value.equals(destination.getString(key[key.length-1]))){
                //匹配到图标大类后寻找图标名
                String deviceName = getFeature(new String[]{"s","text"},d.getJSONObject(i));
                System.out.println(deviceName+"device");
                String[] targetArray = new String[targets.length];
                try{
                    targetArray = targetCompose(new String[]{"電梯系統","E栋","1层","客梯"},new String[]{"UP","MENT"},deviceName);//ALKU_AEL01
                    System.out.println(targetArray);
                }catch (IOException e){
                    e.printStackTrace();
                }
                destination = d.getJSONObject(i);
                for(int k = 0;k<workdir.length;k++){
                    if(destination.getJSONObject(workdir[k]) == null){
                        destination.put(workdir[k],new JSONObject());
                    }
                    destination = destination.getJSONObject(workdir[k]);
                }
                for (int index = 0;index<targets.length;index++) {
                    DataSource dataSource = new DataSource();
                    dataSource.addTarget(JSONObject.parseObject(targetArray[index], Feature.OrderedField));
                    destination.put(targets[index],dataSource);
                }
            }
        }
        System.out.println(JSONObject.toJSONString(jsonObject,SerializerFeature.DisableCircularReferenceDetect));
        return jsonObject;
    }

    @Override
    public boolean update(String json, String path) {
        return false;
    }

    @Override
    public String login() {
        String loginurl = baseurl + "api/login";
        headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        String body = "{\"username\":\""+userName+"\",\"password\":\""+password+"\"}";
        String response = null;
        try {
            requestEntity = new StringEntity(body);
            response = HttpClientUtil.postRequest(loginurl,headers,requestEntity,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null){
            this.accesstoken = response.substring(8,response.indexOf(";"));

        }
        return null;
    }
}
