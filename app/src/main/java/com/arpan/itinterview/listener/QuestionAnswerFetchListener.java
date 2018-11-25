package com.arpan.itinterview.listener;

import java.util.ArrayList;
import java.util.HashMap;

import com.arpan.itinterview.tables.Question;

public interface QuestionAnswerFetchListener {


    void onFetchSuccess(HashMap<String,ArrayList<Question> > questionAnswerMap);
    void onFailure(Exception exception);
}
