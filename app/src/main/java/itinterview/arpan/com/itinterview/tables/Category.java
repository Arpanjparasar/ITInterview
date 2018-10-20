package itinterview.arpan.com.itinterview.tables;

public class Category {

    String companies = "Wipro,TCS,Acce";
    String domain = "Java,C,C++";

    public Category(String companies, String domain) {
        this.companies = companies;
        this.domain = domain;
    }



    public String getCompanies() {
        return companies;
    }

    public void setCompanies(String companies) {
        this.companies = companies;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
