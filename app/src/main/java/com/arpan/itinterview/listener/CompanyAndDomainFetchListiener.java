package com.arpan.itinterview.listener;

import java.util.ArrayList;

import com.arpan.itinterview.model.ExpandableChild;

public interface CompanyAndDomainFetchListiener {


    void onFetchCategorySucces(ArrayList<ExpandableChild> companies , ArrayList<ExpandableChild> domains);
    void onFailure(Exception ex);
}
