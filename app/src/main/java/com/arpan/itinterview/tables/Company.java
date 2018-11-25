package com.arpan.itinterview.tables;

import com.arpan.itinterview.model.ExpandableChild;

public class Company extends ExpandableChild {

    String name;
    String url;

    public Company() {
        super();
    }

    public Company(String name, String url) {
        super(name,url);
    }

}
