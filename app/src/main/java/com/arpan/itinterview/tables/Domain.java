package com.arpan.itinterview.tables;

import com.arpan.itinterview.model.ExpandableChild;

public class Domain extends ExpandableChild {

    String name;
    String url;

    public Domain() {
        super();
    }

    public Domain(String name, String url) {
       super(name,url);
    }

}
