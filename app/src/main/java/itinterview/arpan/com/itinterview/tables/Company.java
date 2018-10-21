package itinterview.arpan.com.itinterview.tables;

import java.util.ArrayList;

import itinterview.arpan.com.itinterview.model.ExpandableChild;

public class Company extends ExpandableChild{

    String name;
    String url;

    public Company() {
        super();
    }

    public Company(String name, String url) {
        super(name,url);
    }

}
