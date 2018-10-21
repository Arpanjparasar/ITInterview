package itinterview.arpan.com.itinterview.utility;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import itinterview.arpan.com.itinterview.ITInterviewApplication;
import itinterview.arpan.com.itinterview.listener.CompanyAndDomainFetchListiener;
import itinterview.arpan.com.itinterview.listener.FetchAboutListener;
import itinterview.arpan.com.itinterview.listener.FetchContactUs;
import itinterview.arpan.com.itinterview.model.ExpandableChild;
import itinterview.arpan.com.itinterview.tables.About;
import itinterview.arpan.com.itinterview.tables.Company;
import itinterview.arpan.com.itinterview.tables.ContactUs;
import itinterview.arpan.com.itinterview.tables.Domain;
import itinterview.arpan.com.itinterview.tables.Question;

public class FireBaseUtility {


    public static void saveData(About about) {

//Write code to save data to firebase
        String userId = ITInterviewApplication.getFireBaseDatabase().push().getKey();
        ITInterviewApplication.getFireBaseDatabase().child("about").setValue(about);


    }

    public static void saveContact(ContactUs fdsf) {
        ITInterviewApplication.getFireBaseDatabase().child("contact").setValue(fdsf);
    }

    public static void getAbout(final FetchAboutListener fetchAboutListener) {

        ITInterviewApplication.getFireBaseDatabase().child("about").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                About value = dataSnapshot.getValue(About.class);
                System.out.println("print"+value.toString());
                fetchAboutListener.onSuccess(value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fetchAboutListener.onFailure(databaseError.toException());
                // ...
            }
        });
    }


    public static void getContact(final FetchContactUs fetchContactUs) {

        ITInterviewApplication.getFireBaseDatabase().child("contact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ContactUs value = dataSnapshot.getValue(ContactUs.class);
                System.out.println("print"+value.toString());
                fetchContactUs.onContactSuccess(value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fetchContactUs.onContactFailure(databaseError.toException());
                // ...
            }
        });
    }


    public static void saveQuestion(Question question){
        ITInterviewApplication.getFireBaseDatabase().child("domain").child(question.getDomain()).setValue(question);
        ITInterviewApplication.getFireBaseDatabase().child("company").child(question.getCompany()).setValue(question);
    }

    public static void saveCatagory(){

        ArrayList<Domain> domains = new ArrayList<>();
        ArrayList<Company> companies = new ArrayList<>();

        Domain domain = new Domain("JAVA","");
        Domain domain1 = new Domain("Android","");
        Domain domain2 = new Domain("C++","");

        domains.add(domain);
        domains.add(domain1);
        domains.add(domain2);


        Company company = new Company("TCS","");
        Company company1 = new Company("WIPRO","");
        Company company2 = new Company("ACC","");

        companies.add(company);
        companies.add(company1);
        companies.add(company2);

        for (Domain dom:domains){
            ITInterviewApplication.getFireBaseDatabase().child("Catagory").child("domain").child(dom.getName()).setValue(dom);
        }

        for (Company com:companies){
            ITInterviewApplication.getFireBaseDatabase().child("Catagory").child("company").child(com.getName()).setValue(com);
        }

    }

    public void getCatagory(final CompanyAndDomainFetchListiener companyAndDomainFetchListiener){

       final ArrayList<ExpandableChild> companies = new ArrayList<>();
        final ArrayList<ExpandableChild> domains = new ArrayList<>();

        ITInterviewApplication.getFireBaseDatabase().child("Catagory").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                if(dataSnapshot1.getKey().equalsIgnoreCase("company")){


                    for(DataSnapshot dataSnapshot2 :dataSnapshot1.getChildren()){
                        Company company = dataSnapshot2.getValue(Company.class);

                        ExpandableChild expandableChild =(ExpandableChild)company;

                        System.out.println(company);
                        companies.add(company);
                    }

                }

                if(dataSnapshot1.getKey().equalsIgnoreCase("domain")){


                    for(DataSnapshot dataSnapshot2 :dataSnapshot1.getChildren()){
                        Domain domain = dataSnapshot2.getValue(Domain.class);
                        ExpandableChild expandableChild =(ExpandableChild)domain;
                        System.out.println(domain);
                        domains.add(domain);
                    }

                }

            }

            companyAndDomainFetchListiener.onFetchCategorySucces(companies,domains);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });



    }

}
