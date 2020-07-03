package com.advantech.ibms.util;
import java.io.IOException;

public class CommonUtil {
    public static String getDisplay(String path, int org_id) throws IOException {
        System.out.println(path+"&org_id="+org_id);
        return HttpClientUtil.getRequest(path+"?org_id="+org_id,null);
    }

//    public static void main(String[] args) throws IOException{
//        new CommonUtil().getDisplay("");
//    }


}
