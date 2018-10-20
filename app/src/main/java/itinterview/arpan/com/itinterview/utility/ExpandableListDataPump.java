package itinterview.arpan.com.itinterview.utility;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itinterview.arpan.com.itinterview.R;
import itinterview.arpan.com.itinterview.listener.CompanyAndDomainFetchListiener;
import itinterview.arpan.com.itinterview.tables.Company;
import itinterview.arpan.com.itinterview.tables.Domain;

public class ExpandableListDataPump implements CompanyAndDomainFetchListiener {
    public static HashMap<String, List<ListData>> getData() {
        HashMap<String, List<ListData>> expandableListDetail = new HashMap<String, List<ListData>>();



        List<ListData> listDataArrayListDomain = new ArrayList<>();
        listDataArrayListDomain.add(new ListData("JAVA", R.mipmap.java));
        listDataArrayListDomain.add(new ListData("ANDROID",R.mipmap.android));



        List<ListData> listDataArrayListCompany = new ArrayList<>();
        listDataArrayListCompany.add(new ListData("TCS",R.mipmap.tcs));
        listDataArrayListCompany.add(new ListData("ACCENTURE",R.mipmap.accenture));
        listDataArrayListCompany.add(new ListData("WIPRO",R.mipmap.wipro));


        expandableListDetail.put("Domain Wise", listDataArrayListDomain);
        expandableListDetail.put("Company Wise", listDataArrayListCompany);

        ArrayList<Domain> domains = new ArrayList<>();

        ArrayList<Company> companies = new ArrayList<>();


        return expandableListDetail;
    }

    public void getFireBaseData(){
        new FireBaseUtility().getCatagory(this);
    }

    @Override
    public void onFetchDomainSucces(ArrayList<Domain> domains) {

    }

    @Override
    public void onFetchCompanySucces(ArrayList<Company> companies) {

    }

    @Override
    public void onFailure(Exception ex) {

    }

    public static class ListData {

        String title;
        int icon;

        ListData(String title, int icon) {

            this.title = title;
            this.icon = icon;

        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }

    private void getDomains(){

    }

    private void getCompanies(){

    }
}
