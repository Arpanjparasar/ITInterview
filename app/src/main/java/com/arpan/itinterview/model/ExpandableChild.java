package com.arpan.itinterview.model;

public class ExpandableChild {


    String name;
    String url;

    public ExpandableChild() {
    }

    public ExpandableChild(String name, String url) {

        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return name + " : " +url;
    }
}
