package itinterview.arpan.com.itinterview.listener;

import java.util.ArrayList;

import itinterview.arpan.com.itinterview.model.ExpandableChild;
import itinterview.arpan.com.itinterview.tables.Company;
import itinterview.arpan.com.itinterview.tables.Domain;

public interface CompanyAndDomainFetchListiener {


    void onFetchCategorySucces(ArrayList<ExpandableChild> companies , ArrayList<ExpandableChild> domains);
    void onFailure(Exception ex);
}
