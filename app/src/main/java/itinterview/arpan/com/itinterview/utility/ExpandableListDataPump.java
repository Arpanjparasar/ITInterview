package itinterview.arpan.com.itinterview.utility;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itinterview.arpan.com.itinterview.R;

public class ExpandableListDataPump {
    public static HashMap<String, List<ListData>> getData() {
        HashMap<String, List<ListData>> expandableListDetail = new HashMap<String, List<ListData>>();

        List<String> Domainwise = new ArrayList<String>();
        Domainwise.add("India");
        Domainwise.add("Pakistan");
        Domainwise.add("Australia");
        Domainwise.add("England");
        Domainwise.add("South Africa");

        List<ListData> listDataArrayListDomain = new ArrayList<>();
        listDataArrayListDomain.add(new ListData("JAVA", R.mipmap.java));
        listDataArrayListDomain.add(new ListData("ANDROID",R.mipmap.android));


        List<String> CompanyWise = new ArrayList<String>();
        CompanyWise.add("Brazil");
        CompanyWise.add("Spain");
        CompanyWise.add("Germany");
        CompanyWise.add("Netherlands");
        CompanyWise.add("Italy");

        List<ListData> listDataArrayListCompany = new ArrayList<>();
        listDataArrayListCompany.add(new ListData("TCS",R.mipmap.tcs));
        listDataArrayListCompany.add(new ListData("ACCENTURE",R.mipmap.accenture));
        listDataArrayListCompany.add(new ListData("WIPRO",R.mipmap.wipro));


        expandableListDetail.put("Domain Wise", listDataArrayListDomain);
        expandableListDetail.put("Company Wise", listDataArrayListCompany);

        return expandableListDetail;
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
}
