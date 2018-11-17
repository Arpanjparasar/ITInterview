package itinterview.arpan.com.itinterview.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itinterview.arpan.com.itinterview.activity.HomeActivity;
import itinterview.arpan.com.itinterview.adapter.CustomExpandableListAdapter;
import itinterview.arpan.com.itinterview.listener.CompanyAndDomainFetchListiener;
import itinterview.arpan.com.itinterview.model.ExpandableChild;
import itinterview.arpan.com.itinterview.R;
import itinterview.arpan.com.itinterview.utility.FireBaseUtility;
import itinterview.arpan.com.itinterview.utility.IViewConstants;
import itinterview.arpan.com.itinterview.utility.NetworkUtility;

public class Homefragment extends BaseFragment implements CompanyAndDomainFetchListiener{

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<ExpandableChild>> expandableListDetail;

    private View fragmentView;
    private TextView mTvGeneral;
    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.homefragment, container, false);

        //dialog = new ProgressDialog(getActivity());
        expandableListView = (ExpandableListView) fragmentView.findViewById(R.id.expandableListView);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
               /* Toast.makeText(getActivity(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getActivity(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
               /* Toast.makeText(
                        getActivity(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/


                gotoQuestionAnswerFragment(groupPosition, childPosition,false);



                return false;
            }
        });

        getActivity().setTitle("IT Interview");

       // FireBaseUtility.saveCatagory();

        mTvGeneral = fragmentView.findViewById(R.id.tv_general);

        mTvGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoQuestionAnswerFragment(0,0,true);
            }
        });



        MobileAds.initialize(getActivity(), "ca-app-pub-1996444408149363~5500096398");

        mAdView =(AdView) fragmentView.findViewById(R.id.adView);
        AdRequest.Builder builder = new AdRequest.Builder();
        //builder.addTestDevice("1AB35343665A25E0874F7F8DF85013DC");
        AdRequest adRequest = builder.build();


        mAdView.loadAd(adRequest);

        return fragmentView;
    }

    private void gotoQuestionAnswerFragment(int groupPosition, int childPosition ,boolean isGeneral) {
        QuestionAnswerFragment questionAnswerFragment = new QuestionAnswerFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean(IViewConstants.IS_GENERAL,isGeneral);
        if(!isGeneral){
            bundle.putString(IViewConstants.CATEGORY, expandableListDetail.get(
                    expandableListTitle.get(groupPosition)).get(childPosition).getName());

            bundle.putString(IViewConstants.TITLE, expandableListTitle.get(groupPosition));
        }

        questionAnswerFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =        fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, questionAnswerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(NetworkUtility.isNetworkConnected(getActivity())) {
            showProgressDialog();
            new FireBaseUtility().getCatagory(this);
        }
    }

    @Override
    public void onFetchCategorySucces(ArrayList<ExpandableChild> companies, ArrayList<ExpandableChild> domains) {

        HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.setCompanies(companies);
        homeActivity.setDomains(domains);

        expandableListDetail = new HashMap<>();
        expandableListDetail.put("Domains",domains);
        expandableListDetail.put("Companies",companies);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        dissMissProgressDialog();
    }

    @Override
    public void onFailure(Exception ex) {
        dissMissProgressDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dissMissProgressDialog();
    }

    @Override
    public void isInternetConnected() {
        showProgressDialog();
        new FireBaseUtility().getCatagory(this);
    }
}