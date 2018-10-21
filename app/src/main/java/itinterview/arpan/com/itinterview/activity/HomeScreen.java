package itinterview.arpan.com.itinterview.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import itinterview.arpan.com.itinterview.fragment.ContactUsFragment;
import itinterview.arpan.com.itinterview.model.ExpandableChild;
import itinterview.arpan.com.itinterview.tables.Company;
import itinterview.arpan.com.itinterview.tables.Question;
import itinterview.arpan.com.itinterview.utility.FireBaseUtility;
import itinterview.arpan.com.itinterview.R;
import itinterview.arpan.com.itinterview.fragment.AboutUs;
import itinterview.arpan.com.itinterview.fragment.Homefragment;
import itinterview.arpan.com.itinterview.fragment.Terms;
import itinterview.arpan.com.itinterview.tables.ContactUs;
import itinterview.arpan.com.itinterview.volley.CustomVolleyNetworkQueue;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tv_empname, tv_empemail;
    private NetworkImageView profilePic;
    private ImageLoader imageLoader;

    private ArrayList<ExpandableChild> companies;

    public ArrayList<ExpandableChild> getCompanies() {
        return companies;
    }

    ArrayList<String> getCompanyNames(){

        ArrayList<String> names = new ArrayList<>();

        for(ExpandableChild company:companies){
            names.add(company.getName());
        }
        return names;
    }

    ArrayList<String> getDomainNames(){

        ArrayList<String> names = new ArrayList<>();

        for(ExpandableChild domain:domains){
            names.add(domain.getName());
        }
        return names;
    }

    public void setCompanies(ArrayList<ExpandableChild> companies) {
        this.companies = companies;
    }

    public ArrayList<ExpandableChild> getDomains() {
        return domains;
    }

    public void setDomains(ArrayList<ExpandableChild> domains) {
        this.domains = domains;
    }

    private ArrayList<ExpandableChild> domains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        imageLoader = CustomVolleyNetworkQueue.getInstance(this).getImageLoader();

       // FireBaseUtility.saveData(new About(" Hi arpan fkjbvdfkjvndfkjvndfkjvndfkj"));

     //   FireBaseUtility.saveContact(new ContactUs("11","3465","fdsf"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

               showQestionDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        profilePic = headerView.findViewById(R.id.iv_dp);

        tv_empname = (TextView) headerView.findViewById(R.id.TV_profileName);
        tv_empemail = (TextView) headerView.findViewById(R.id.TV_profileEmail);
        //FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        String userEmail = mFirebaseUser.getEmail();
        String name = mFirebaseUser.getDisplayName();

        tv_empname.setText(name);
        tv_empemail.setText(userEmail);


        imageLoader.get(mFirebaseUser.getPhotoUrl().toString(), ImageLoader.getImageListener(profilePic,
                R.mipmap.ic_launcher, android.R.drawable
                        .ic_dialog_alert));

        profilePic.setImageUrl(mFirebaseUser.getPhotoUrl().toString(), imageLoader);

        goToHomeFragment();


    }

    private void showQestionDialog() {

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View reportView=getLayoutInflater().inflate(R.layout.dialog_post_question,null);
        final EditText etQuestion=reportView.findViewById(R.id.et_question);
        final Spinner spinnerDomain = reportView.findViewById(R.id.spinner_domain);
        final Spinner spinnerCompany = reportView.findViewById(R.id.spinner_company);

        Button btnSubmit = reportView.findViewById(R.id.btn_submit);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaDomain = new ArrayAdapter(this,android.R.layout.simple_spinner_item,getDomainNames());
        aaDomain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerDomain.setAdapter(aaDomain);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaCompany = new ArrayAdapter(this,android.R.layout.simple_spinner_item,getCompanyNames());
        aaCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerCompany.setAdapter(aaCompany);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String domain = spinnerDomain.getSelectedItem().toString();
                //String company = spinnerCompany.getSelectedItem().toString();
                //String que = etQuestion.getText().toString();

                final Question question = new Question("JAVA","TCS","What is java ?");
                FireBaseUtility.saveQuestion(question);
                alertDialog.dismiss();
            }
        });


        alertDialog.setView(reportView);
        alertDialog.setCancelable(true);
        alertDialog.show();

    }

    private void goToHomeFragment() {
        setTitle("IT Interview");
        android.support.v4.app.Fragment aboutFragment= new Homefragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content,aboutFragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_homescreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_contactus) {


            setTitle("Contact Us");

            android.support.v4.app.Fragment aboutFragment= new ContactUsFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content,aboutFragment);
            fragmentTransaction.commit();




        }

        else if(id==R.id.nav_home){

            goToHomeFragment();

        } else if (id == R.id.nav_terms) {

            setTitle("Terms and Conditions");

            android.support.v4.app.Fragment aboutFragment= new Terms();

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content,aboutFragment);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_report) {


            AlertDialog.Builder report=new AlertDialog.Builder(HomeScreen.this);
            View reportView=getLayoutInflater().inflate(R.layout.reportproblem,null);
            EditText problem=reportView.findViewById(R.id.ET_subject);

            report.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(HomeScreen.this,"Report Successfully Sent",Toast.LENGTH_LONG).show();


                }
            });
            report.setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            report.setView(reportView);
            report.show();

        } else if (id == R.id.nav_about) {

            setTitle("About us");

            android.support.v4.app.Fragment aboutFragment= new AboutUs();

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content,aboutFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_share) {

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            String body = "Download IT Interview!";
            String sub = "The best IT Interview app in India.";
            share.putExtra(Intent.EXTRA_TEXT, body);
            share.putExtra(Intent.EXTRA_SUBJECT, sub);
            share.putExtra(Intent.EXTRA_TEXT, "give apk share link");
            startActivity(Intent.createChooser(share, "Share Using"));

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, SplashScreen.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
