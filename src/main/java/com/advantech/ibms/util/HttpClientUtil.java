package com.advantech.ibms.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpClientUtil {
//    public static void main(String[] args) {
//        System.out.println("post");
//        List<Header> headerList = new ArrayList<>();
//        headerList.add(new BasicHeader("Content-Type", "application/json"));
//        try {
//            HttpEntity requestEntity = new StringEntity("{\"mock\":1,\"range\":{\"from\":\"2020-05-19T08:04:00.548Z\",\"to\":\"2020-05-19T10:20:00.549Z\",\"raw\":{\"from\":\"now-5m\",\"to\":\"now\"}},\"maxDataPoints\":1,\"interval\":\"2.5s\",\"intervalMs\":2500,\"targets\":[{\"target\":\"portal|ibms|bems\",\"queryType\":\"bems_multi\",\"resourceId\":\"3a7dcf39-60a1-4095-aa83-f563679c0007\",\"subitemCode\":\"01000\",\"formulaUnit\":\"month\",\"formulaType\":120,\"displayName\":\"建筑信息\"}]}");
////            postRequest("https://api-bee-apm-eks007.hz.wise-paas.com.cn/v1/simplejson/query/v1", headerList, requestEntity);
//            getRequest("https://saas-composer-user1-eks007.hz.wise-paas.com.cn/displays/自来水/設備監控影像.json?org_id=4",null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static String postRequest(String uri, List<Header> headerList, HttpEntity requestEntity,boolean cookieRequire) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        headerList.forEach(o -> {
            httpPost.setHeader(o);
        });
        httpPost.setEntity(requestEntity);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200 || statusCode == 201) {
                HttpEntity entity = response.getEntity();
                String entityStr = EntityUtils.toString(entity);
                if(cookieRequire)
                    return response.getHeaders("Set-Cookie")[0].getValue();
                return entityStr;
            }
            throw new IOException("调用请求" + uri + "失败code = " + statusCode);
        } finally {
            response.close();
        }
    }

    public static String getRequest(String uri, List<Header> headerList) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        if(headerList != null)
        headerList.forEach(o -> {
            httpGet.setHeader(o);
        });
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200 || statusCode == 201) {
                HttpEntity entity = response.getEntity();
                String entityStr = EntityUtils.toString(entity);
                System.out.println(entityStr);
                return entityStr;
            }
            throw new IOException("调用请求" + uri + "失败code = " + statusCode+ response.toString());
        } finally {
            response.close();
        }
    }
}
