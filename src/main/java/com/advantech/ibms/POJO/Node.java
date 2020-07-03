package com.advantech.ibms.POJO;

public class Node {
    private int id = 12;
    private boolean hasChild = true;
    private String name = "asd";
    @Override
    public String toString() {
        return "{\"id\":"+id+",\"hasChild\":"+hasChild+"}";
    }


    public Node(int id, boolean hasChild) {
        this.id = id;
        this.hasChild = hasChild;
    }
}
