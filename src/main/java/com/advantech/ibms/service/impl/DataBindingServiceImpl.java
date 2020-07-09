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
    private String flag = null;
    private int id;
    private LinkedHashMap<String,String> globalobject;
    private StringBuffer targetstring;
    //name: sensorname
    private String response;
    @Value("${tenant-id}")
    private String tenantId;
    private int tag = 0;
    @Override
    public String[] targetCompose(String[] topo, String[] sensors, String feature,String displayName) throws IOException{
        String[] resmap = new String[sensors.length];
        String uri;
        List<Header> headerList = new ArrayList<>();
        headerList.add(new BasicHeader("Authorization", "Bearer " + accesstoken));
//        System.out.println("Bearer "+accesstoken);
        headerList.add(new BasicHeader("Content-Type","application/json"));
        headerList.add(new BasicHeader("tenant-id",tenantId));
        StringBuffer target = new StringBuffer();
        LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
        int id;
        String response = null;
        if(!displayName.equals(flag)){
            System.out.println(flag+"not equals"+displayName);
            flag = displayName;
            map.put("sourceType","APM");
            map.put("formatType","timeseries");
            map.put("scDataType","value");
            String Name = topo[0];
            map.put("topo",Name);
            target.append(Name).append("|");


            uri = apmurl+"/topo/root/node?topoName="+topo[0];
            response = HttpClientUtil.getRequest(uri,headerList);
            id =findid(Name,response);
//            System.out.println("root"+id);
            target.append(id).append("|");
            map.put("root",new Node(id,true).toString());


            uri = apmurl+"/topo/progeny/node?nodeId="+id+"&type=child";
            response = HttpClientUtil.getRequest(uri,headerList);
            id = findid(topo[1],response);
//            System.out.println("node1"+id);
            target.append(id).append("|");
            map.put("Node1",new Node(id,true).toString());


            uri = apmurl+"/topo/progeny/node?nodeId="+id+"&type=child";
            response = HttpClientUtil.getRequest(uri,headerList);
            id = findid(topo[2],response);
//            System.out.println("node2"+id);
            target.append(id).append("|");
            map.put("Node2",new Node(id,true).toString());

            uri = apmurl+"/topo/progeny/node?nodeId="+id+"&type=child";
            response = HttpClientUtil.getRequest(uri,headerList);
            id = findid(topo[3],response);
//            System.out.println("node3"+id);
            target.append(id).append("|");
            map.put("Node3",new Node(id,true).toString());


            uri = apmurl+"/topo/progeny/node?nodeId="+id+"&type=child";
            response = HttpClientUtil.getRequest(uri,headerList);
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
        this.tag = id;
        System.out.println("node4"+id);
        if(id == 0){
            System.out.println("+++++++++++++++++++++++++++++++++warning,nothing match++++++++++++++++++++++++++++++++++");
//            System.exit(0);
            return null;

        }
        target.append(id).append("|");
        map.put("Node4",new Node(id,false).toString());
        for (int i = 0;i<sensors.length;i++) {
            StringBuffer target1  = new StringBuffer(target);
            String sensorName = "monitor-"+sensors[i];
//            System.out.println(sensorName);
            target1.append(sensorName).append("|real");
            map.put("sensor",sensorName);
            map.put("dataType","real");
            map.put("target",target1.toString());
            resmap[i]=JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue);
        }
        return resmap;
    }

    @Override
    public JSONObject parse(String path) {
        path = path.replace(" ","%20");
        String display = null;
        //因sc与apm的token不同暂时修改 从016登录 从005拿图
        String temporaryUrl = "https://saas-composer-ibms-eks005.bm.wise-paas.com.cn/";
        try{
            display = CommonUtil.getDisplay(temporaryUrl+path,org_id);

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
        String res = symbol.getString(path[path.length-1]);
//        return regExp(res);
        return res;
    }

    public String regExp(String name){
        String [] nameArray = name.split("-");
        String head = nameArray[0].replaceFirst("M","ALKU_AFM");
        int number = Integer.parseInt(nameArray[1]);
        StringBuffer tail = new StringBuffer(String.valueOf(number / 100));
        tail.append(String.valueOf(number % 100 / 10));
        tail.append(String.valueOf(number % 100 % 10));
        return head+tail;
    }
    public JSONObject setFeature(String [] path,JSONObject symbol,int value){
        JSONObject res = symbol;
        for (int i = 0;i<path.length-1;i++) {
            if(symbol.getJSONObject(path[i])==null)
                symbol.put(path[i],new JSONObject());
                symbol = symbol.getJSONObject(path[i]);
        }
        symbol.put(path[path.length-1],value);
        return res;
    }
    @Override
    public JSONObject replace(String[] workdir,String [] targets, DataSource dataSource1, JSONObject jsonObject, String [] key, Map<String,String> symbol,String[] sensors,String [] topo) {

        JSONObject destination ;
        JSONArray d =  jsonObject.getJSONArray("d");
        for(int i = 0; i < d.size();i++){
//            System.out.println(d.getJSONObject(i).toJSONString());
            destination = d.getJSONObject(i);
            for(int j = 0;j<key.length-1;j++){
                destination = destination.getJSONObject(key[j]);
            }
            Iterator<Map.Entry<String,String>> symbolIterator = symbol.entrySet().iterator();
            while (symbolIterator.hasNext()) {
                Map.Entry<String,String> entry = symbolIterator.next();
//                System.out.println("key"+entry.getKey());
                if (entry.getValue().equals(destination.getString(key[key.length - 1]))) {
                    //匹配到图标大类后寻找图标名
                    String deviceName = getFeature(new String[]{"a", "device_number"}, d.getJSONObject(i));
//                    String deviceName = getFeature(new String[]{"p", "toolTip"}, d.getJSONObject(i));
                    if (deviceName == null)
                        continue;
                    System.out.println(deviceName + "device" + entry.getKey());
//                String[] targetArray = new String[targets.length];
                    String[] targetArray = null;
                    try {
                        topo[3] = entry.getKey();
                        String [] clone = sensors.clone();
                        for(int sensorIndex = 0;sensorIndex<clone.length;sensorIndex++){
                            clone[sensorIndex] = entry.getKey()+clone[sensorIndex];
//                            System.out.println(sensors[sensorIndex]);
                        }
                        targetArray = targetCompose(topo, clone, "ALKA_"+deviceName,entry.getKey());//ALKU_AEL01
                        System.out.println("target:"+ targetArray);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JSONObject destination1 = d.getJSONObject(i);
                    if(this.tag != 0)
                    destination1 = setFeature(new String[]{"p", "tag"}, destination1, this.tag);
                    for (int k = 0; k < workdir.length; k++) {
                        if (destination1.getJSONObject(workdir[k]) == null) {
                            destination1.put(workdir[k], new JSONObject());
                        }
                        destination1 = destination1.getJSONObject(workdir[k]);
                    }
                    for (int index = 0; targetArray!=null && index < targets.length; index++) {
                        DataSource dataSource = new DataSource();
                        dataSource.addTarget(JSONObject.parseObject(targetArray[index], Feature.OrderedField));
                        destination1.put(targets[index], dataSource);
                    }
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
