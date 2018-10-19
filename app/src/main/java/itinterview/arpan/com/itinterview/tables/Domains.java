package itinterview.arpan.com.itinterview.tables;

import java.util.ArrayList;

public class Domains {

    public Domains() {
    }

    public Domains(ArrayList<String> domainList) {
        this.domainList = domainList;
    }

    ArrayList<String> domainList;

    public ArrayList<String> getDomainList() {
        return domainList;
    }

    public void setDomainList(ArrayList<String> domainList) {
        this.domainList = domainList;
    }
}
