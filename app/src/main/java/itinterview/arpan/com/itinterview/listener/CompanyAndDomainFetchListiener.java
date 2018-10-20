package itinterview.arpan.com.itinterview.listener;

import java.util.ArrayList;

import itinterview.arpan.com.itinterview.tables.Company;
import itinterview.arpan.com.itinterview.tables.Domain;

public interface CompanyAndDomainFetchListiener {


    void onFetchDomainSucces(ArrayList<Domain> domains);
    void onFetchCompanySucces(ArrayList<Company> companies);
    void onFailure(Exception ex);
}
