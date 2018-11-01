package itinterview.arpan.com.itinterview.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itinterview.arpan.com.itinterview.activity.HomeScreen;
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





                QuestionAnswerFragment questionAnswerFragment = new QuestionAnswerFragment();

                Bundle bundle = new Bundle();
                bundle.putString(IViewConstants.CATEGORY,expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(childPosition).getName());

                bundle.putString(IViewConstants.TITLE,expandableListTitle.get(groupPosition));

                questionAnswerFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction =        fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, questionAnswerFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();



                return false;
            }
        });

        getActivity().setTitle("IT Interview");

       // FireBaseUtility.saveCatagory();


        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(NetworkUtility.isNetworkConnected(getActivity())) {
            showProgressDialog();
            new FireBaseUtility().getCatagory(this);
        }else{



            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Connect to Internet and try again")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();




        }
    }

    @Override
    public void onFetchCategorySucces(ArrayList<ExpandableChild> companies, ArrayList<ExpandableChild> domains) {

        HomeScreen homeScreen = (HomeScreen) getActivity();
        homeScreen.setCompanies(companies);
        homeScreen.setDomains(domains);

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
}