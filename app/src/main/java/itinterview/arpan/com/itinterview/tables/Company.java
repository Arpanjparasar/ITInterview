package itinterview.arpan.com.itinterview.tables;

import java.util.ArrayList;

public class Company {

    String name;
    String url;

    public Company() {
    }

    public Company(String name, String url) {

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
}
