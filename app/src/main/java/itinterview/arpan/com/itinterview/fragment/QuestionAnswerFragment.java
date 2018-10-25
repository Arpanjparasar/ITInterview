package itinterview.arpan.com.itinterview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

import itinterview.arpan.com.itinterview.R;
import itinterview.arpan.com.itinterview.adapter.CustomExpandableListAdapter;
import itinterview.arpan.com.itinterview.adapter.QuestionAndAnswerExpandableListAdapter;
import itinterview.arpan.com.itinterview.listener.QuestionAnswerFetchListener;
import itinterview.arpan.com.itinterview.tables.Question;
import itinterview.arpan.com.itinterview.utility.FireBaseUtility;
import itinterview.arpan.com.itinterview.utility.IViewConstants;


public class QuestionAnswerFragment extends Fragment implements QuestionAnswerFetchListener {

    private View fragmentView;
    private ExpandableListView expandableListView;
    private QuestionAndAnswerExpandableListAdapter expandableListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.questionansweractivity, container, false);

        expandableListView = (ExpandableListView) fragmentView.findViewById(R.id.expandableQAListView);

        String value = getArguments().getString(IViewConstants.CATEGORY);
        String title = getArguments().getString(IViewConstants.TITLE);

        getActivity().setTitle(value);

        new FireBaseUtility().fetchQuestionAnswer(title,value,this);

        return fragmentView;
    }

    @Override
    public void onFetchSuccess(HashMap<String, ArrayList<Question>> questionAnswerMap) {

        expandableListAdapter = new QuestionAndAnswerExpandableListAdapter(getActivity(), questionAnswerMap);
        expandableListView.setAdapter(expandableListAdapter);
    }

    @Override
    public void onFailure(Exception exception) {

    }
}
