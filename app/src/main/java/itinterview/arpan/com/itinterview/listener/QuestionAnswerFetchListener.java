package itinterview.arpan.com.itinterview.listener;

import java.util.ArrayList;
import java.util.HashMap;

import itinterview.arpan.com.itinterview.tables.Question;

public interface QuestionAnswerFetchListener {


    void onFetchSuccess(HashMap<String,ArrayList<Question> > questionAnswerMap);
    void onFailure(Exception exception);
}
