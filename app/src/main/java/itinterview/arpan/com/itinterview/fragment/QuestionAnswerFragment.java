package itinterview.arpan.com.itinterview.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import itinterview.arpan.com.itinterview.R;
import itinterview.arpan.com.itinterview.activity.HomeActivity;
import itinterview.arpan.com.itinterview.adapter.QuestionAndAnswerExpandableListAdapter;
import itinterview.arpan.com.itinterview.listener.QuestionAnswerFetchListener;
import itinterview.arpan.com.itinterview.listener.TapToAnswerListener;
import itinterview.arpan.com.itinterview.tables.Question;
import itinterview.arpan.com.itinterview.utility.FireBaseUtility;
import itinterview.arpan.com.itinterview.utility.IViewConstants;
import itinterview.arpan.com.itinterview.utility.NetworkUtility;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class QuestionAnswerFragment extends BaseFragment implements QuestionAnswerFetchListener ,TapToAnswerListener{

    private View fragmentView;
    private ExpandableListView expandableListView;
    private QuestionAndAnswerExpandableListAdapter expandableListAdapter;
    private AdView mAdView;
    private String value;
    private String title;
    private boolean isGeneral;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.questionansweractivity, container, false);

        expandableListView = (ExpandableListView) fragmentView.findViewById(R.id.expandableQAListView);

        isGeneral = getArguments().getBoolean(IViewConstants.IS_GENERAL);


        if(isGeneral){

            getActivity().setTitle("General");
            if(NetworkUtility.isNetworkConnected(getActivity())) {
                showProgressDialog();
                new FireBaseUtility().fetchQuestionAnswerFromGeneral(this);
            }

        }else{

            title = getArguments().getString(IViewConstants.CATEGORY);
            value = getArguments().getString(IViewConstants.TITLE);
            getActivity().setTitle(title);

            if(NetworkUtility.isNetworkConnected(getActivity())) {
                showProgressDialog();
                new FireBaseUtility().fetchQuestionAnswer(value, title, this);
            }

        }



        MobileAds.initialize(getActivity(), "ca-app-pub-1996444408149363~5500096398");

        mAdView =(AdView) fragmentView.findViewById(R.id.adView);
        AdRequest.Builder builder = new AdRequest.Builder();
        builder.addTestDevice("1AB35343665A25E0874F7F8DF85013DC");
        AdRequest adRequest = builder.build();


        mAdView.loadAd(adRequest);

        return fragmentView;
    }

    @Override
    public void onFetchSuccess(HashMap<String, ArrayList<Question>> questionAnswerMap) {

        expandableListAdapter = new QuestionAndAnswerExpandableListAdapter(getActivity(), questionAnswerMap, this);
        expandableListView.setAdapter(expandableListAdapter);

        dissMissProgressDialog();
    }

    @Override
    public void onFailure(Exception exception) {
    dissMissProgressDialog();
    }

    @Override
    public void onClickTapToAnswer(Question question) {

        showAnswerDialog(question);
    }

    private void showAnswerDialog(final Question question) {

        AlertDialog.Builder report = new AlertDialog.Builder(getActivity());
        View answerView = getLayoutInflater().inflate(R.layout.post_answers_dialog, null);

        final EditText etAnswer = answerView.findViewById(R.id.ET_answer);
        final TextView tvQuestion = answerView.findViewById(R.id.TV_question);
        tvQuestion.setText(question.getQuestion());

        report.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                question.setAnswer(etAnswer.getText().toString());
                if(isGeneral){
                    new FireBaseUtility().saveGeneralAnswer(question);
                }else {
                    new FireBaseUtility().saveAnswer(value,title,question);
                }


            }
        });
        report.setNegativeButton("Abort", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        report.setView(answerView);
        report.show();
    }
}
