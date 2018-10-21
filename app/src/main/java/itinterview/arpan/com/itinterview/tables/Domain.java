package itinterview.arpan.com.itinterview.tables;

import java.util.ArrayList;

import itinterview.arpan.com.itinterview.model.ExpandableChild;

public class Domain extends ExpandableChild{

    String name;
    String url;

    public Domain() {
        super();
    }

    public Domain(String name, String url) {
       super(name,url);
    }

}
