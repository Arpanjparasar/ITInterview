package itinterview.arpan.com.itinterview;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        listDataArrayListDomain.add(new ListData("java",R.drawable.ic_about));
        listDataArrayListDomain.add(new ListData("java",R.drawable.ic_menu_camera));


        List<String> CompanyWise = new ArrayList<String>();
        CompanyWise.add("Brazil");
        CompanyWise.add("Spain");
        CompanyWise.add("Germany");
        CompanyWise.add("Netherlands");
        CompanyWise.add("Italy");

        List<ListData> listDataArrayListCompany = new ArrayList<>();
        listDataArrayListCompany.add(new ListData("TCS",R.drawable.tcs));
        listDataArrayListCompany.add(new ListData("HCL",R.drawable.ic_menu_camera));


        expandableListDetail.put("Domain Wise", listDataArrayListDomain);
        expandableListDetail.put("Company Wise", listDataArrayListCompany);

        return expandableListDetail;
    }

    static class ListData {

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
