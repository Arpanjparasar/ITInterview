package itinterview.arpan.com.itinterview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import itinterview.arpan.com.itinterview.R;
import itinterview.arpan.com.itinterview.listener.QuestionAnswerFetchListener;
import itinterview.arpan.com.itinterview.tables.Question;
import itinterview.arpan.com.itinterview.utility.FireBaseUtility;
import itinterview.arpan.com.itinterview.utility.IViewConstants;


public class QuestionAnswerFragment extends Fragment implements QuestionAnswerFetchListener {

    private View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.questionansweractivity, container, false);

        String value = getArguments().getString(IViewConstants.CATEGORY);

        getActivity().setTitle(value);

        new FireBaseUtility().fetchQuestionAnswer("company",value,this);

        return fragmentView;
    }

    @Override
    public void onFetchSuccess(HashMap<String, ArrayList<Question>> questionAnswerMap) {


    }

    @Override
    public void onFailure(Exception exception) {

    }
}
