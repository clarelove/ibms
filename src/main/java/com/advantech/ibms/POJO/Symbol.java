package com.advantech.ibms.POJO;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Map;

public class Symbol {
    public String getC() {
        return c;
    }

    public int getI() {
        return i;
    }

    public void setC(String c) {
        this.c = c;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setP(Map p) {
        this.p = p;
    }

    public void setS(Map s) {
        this.s = s;
    }

    public void setA(Map a) {
        this.a = a;
    }

    public Map getP() {
        return p;
    }

    public Map getS() {
        return s;
    }

    public Map getA() {
        return a;
    }

    private String c;
    private int i;
    private Map p;
    private Map s;
    private Map a;
    public Symbol(String c, int i, Map p, Map s, Map a) {
        this.c = c;
        this.i = i;
        this.p = p;
        this.s = s;
        this.a = a;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "c='" + c + '\'' +
                ", i=" + i +
                ", p=" + p +
                ", s=" + s +
                ", a=" + a +
                '}';
    }

    public static void main(String[] args) {
        Symbol symbol = new Symbol("c",1,null,null,null);
        System.out.println(symbol.toString());
        System.out.println(JSONObject.toJSONString( symbol, SerializerFeature.PrettyFormat));
    }
}
