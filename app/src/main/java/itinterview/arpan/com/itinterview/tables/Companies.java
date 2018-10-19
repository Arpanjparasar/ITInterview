package itinterview.arpan.com.itinterview.tables;

import java.util.ArrayList;

public class Companies {

    public Companies() {
    }

    public Companies(ArrayList<String> companyList) {
        this.companyList = companyList;
    }

    ArrayList<String> companyList;

    public ArrayList<String> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(ArrayList<String> companyList) {
        this.companyList = companyList;
    }
}
