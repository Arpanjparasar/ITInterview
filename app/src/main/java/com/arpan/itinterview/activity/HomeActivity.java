package com.arpan.itinterview.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import com.arpan.itinterview.fragment.AboutUs;
import com.arpan.itinterview.fragment.QuestionAnswerFragment;
import com.arpan.itinterview.fragment.Terms;
import com.arpan.itinterview.utility.FireBaseUtility;
import com.arpan.itinterview.utility.IViewConstants;
import com.arpan.itinterview.broadcast.InternetConnectionWatcher;
import com.arpan.itinterview.fragment.ContactUsFragment;
import com.arpan.itinterview.listener.InternetConnectionListener;
import com.arpan.itinterview.model.ExpandableChild;
import com.arpan.itinterview.tables.Question;
import itinterview.arpan.com.itinterview.R;
import com.arpan.itinterview.fragment.Homefragment;
import com.arpan.itinterview.volley.CustomVolleyNetworkQueue;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,InternetConnectionListener {

    private TextView tv_empname, tv_empemail;
    private NetworkImageView profilePic;
    private ImageLoader imageLoader;

    private ArrayList<ExpandableChild> companies = new ArrayList<>();
    private InternetConnectionWatcher internetConnectionWatcher;
    private AlertDialog alert;

    public ArrayList<ExpandableChild> getCompanies() {
        return companies;
    }

    ArrayList<String> getCompanyNames() {

        ArrayList<String> names = new ArrayList<>();

        if(companies!=null && companies.size()!=0) names.add(IViewConstants.SELECT_COMPANY);

        for (ExpandableChild company : companies) {
            names.add(company.getName());
        }

        return names;
    }

    ArrayList<String> getDomainNames() {

        ArrayList<String> names = new ArrayList<>();

        if(domains!=null && domains.size()!=0) names.add(IViewConstants.SELECT_DOMAIN);

        for (ExpandableChild domain : domains) {
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

    private ArrayList<ExpandableChild> domains = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerWifiReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(internetConnectionWatcher);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);


       /* try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/

        imageLoader = CustomVolleyNetworkQueue.getInstance(this).getImageLoader();

        //FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (mFirebaseUser == null) {
            fab.setEnabled(false);
        }
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

        if (mFirebaseUser != null) {
            String userEmail = mFirebaseUser.getEmail();
            String name = mFirebaseUser.getDisplayName();

            tv_empname.setText(name);
            tv_empemail.setText(userEmail);


            imageLoader.get(mFirebaseUser.getPhotoUrl().toString(), ImageLoader.getImageListener(profilePic,
                    R.mipmap.ic_launcher, android.R.drawable
                            .ic_dialog_alert));

            profilePic.setImageUrl(mFirebaseUser.getPhotoUrl().toString(), imageLoader);
        }

        internetConnectionWatcher = new InternetConnectionWatcher(this);

        goToHomeFragment();


    }

    private void showQestionDialog() {

        if (getDomainNames().size() == 0 || getCompanyNames().size() == 0) return;


        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View reportView = getLayoutInflater().inflate(R.layout.dialog_post_question, null);
        final EditText etQuestion = reportView.findViewById(R.id.et_question);
        final Spinner spinnerDomain = reportView.findViewById(R.id.spinner_domain);
        final Spinner spinnerCompany = reportView.findViewById(R.id.spinner_company);

        final Button btnSubmit = reportView.findViewById(R.id.btn_submit);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaDomain = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getDomainNames());
        aaDomain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerDomain.setAdapter(aaDomain);



        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaCompany = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getCompanyNames());
        aaCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerCompany.setAdapter(aaCompany);



        btnSubmit.setEnabled(false);

        etQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length()!=0) {
                    btnSubmit.setEnabled(true);
                }else{
                    Toast.makeText(getBaseContext(), "You did not entered a question", Toast.LENGTH_SHORT).show();
                    btnSubmit.setEnabled(false);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String domain = spinnerDomain.getSelectedItem().toString();
                String company = spinnerCompany.getSelectedItem().toString();
                String que = etQuestion.getText().toString();

                final Question question = new Question(domain, company, que);
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
        android.support.v4.app.Fragment aboutFragment = new Homefragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, aboutFragment);
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

            android.support.v4.app.Fragment aboutFragment = new ContactUsFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, aboutFragment);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_home) {

            goToHomeFragment();

        } else if (id == R.id.nav_terms) {

            setTitle("Terms and Conditions");

            android.support.v4.app.Fragment aboutFragment = new Terms();

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, aboutFragment);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_report) {


            AlertDialog.Builder report = new AlertDialog.Builder(HomeActivity.this);
            View reportView = getLayoutInflater().inflate(R.layout.reportproblem, null);
            final EditText problem = reportView.findViewById(R.id.ET_subject);

            report.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(HomeActivity.this, "Report Successfully Sent", Toast.LENGTH_LONG).show();
                    sendEmail(problem.getText().toString());


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

            android.support.v4.app.Fragment aboutFragment = new AboutUs();

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, aboutFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_share) {

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            String body = "Download IT Interview!";
            String sub = "The best IT Interview app in India.";
            share.putExtra(Intent.EXTRA_TEXT, body);
            share.putExtra(Intent.EXTRA_SUBJECT, sub);
            share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.arpan.itinterview&hl=en");
            startActivity(Intent.createChooser(share, "Share Using"));

        } else if (id == R.id.nav_logout) {
            if(LoginManager.getInstance()!= null){
                LoginManager.getInstance().logOut();
            }


            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, SplashScreen.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void sendEmail(String message) {


        String recipient = "arpanjyotiparasar@gmail.com";
        String problem = "IT Interview problem";
        String[] recipients = {recipient.toString()};
        Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        // prompts email clients only
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL, recipients);
        email.putExtra(Intent.EXTRA_SUBJECT, problem);
        email.putExtra(Intent.EXTRA_TEXT, message);
        try {
            // the user can choose the email client
            startActivity(Intent.createChooser(email, "Choose the email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(HomeActivity.this, "No email client installed.",

                    Toast.LENGTH_LONG).show();

        }

    }


    @Override
    public void isInternetConnected() {

        if(alert != null && alert.isShowing()){
            alert.dismiss();
        }

        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        for(Fragment fragment:fragments){

            if(fragment instanceof Homefragment && fragment.isVisible()){

                Homefragment homefragment =(Homefragment)fragment;
                homefragment.isInternetConnected();
            }

            if(fragment instanceof QuestionAnswerFragment && fragment.isVisible()){

                QuestionAnswerFragment questionAnswerFragment =(QuestionAnswerFragment)fragment;
                questionAnswerFragment.isInternetConnected();
            }
        }
    }

    @Override
    public void isInternetDisconnected() {
        showInternetLostDialog();
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetConnectionWatcher, filter);
    }

    private void createAlertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Connect to Internet and try again")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.this.finish();
                    }
                });
        alert = builder.create();
    }

    private void showInternetLostDialog() {
        if(alert == null) createAlertDialog();
        alert.show();
    }
}

