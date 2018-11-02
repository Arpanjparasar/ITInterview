package itinterview.arpan.com.itinterview.adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itinterview.arpan.com.itinterview.R;
import itinterview.arpan.com.itinterview.listener.TapToAnswerListener;
import itinterview.arpan.com.itinterview.tables.Question;
import itinterview.arpan.com.itinterview.utility.IViewConstants;

public class QuestionAndAnswerExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    HashMap<String, ArrayList<Question>> questionAnswerMap;

    private final TapToAnswerListener tapToAnswerListener;

    public QuestionAndAnswerExpandableListAdapter(Context context, HashMap<String, ArrayList<Question>> questionAnswerMap, TapToAnswerListener tapToAnswerListener) {
        this.context = context;
        this.expandableListTitle = new ArrayList<String>(questionAnswerMap.keySet());
        this.questionAnswerMap = questionAnswerMap;
        this.tapToAnswerListener = tapToAnswerListener;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.questionAnswerMap.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Question listData= (Question) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.question_answer_list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedQAListItem);


        expandedListTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listData.getAnswer().equalsIgnoreCase(IViewConstants.TAP_TO_ANSWER)){
                    tapToAnswerListener.onClickTapToAnswer(listData);
                }
            }
        });

        expandedListTextView.setText(listData.getAnswer());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.questionAnswerMap.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.question_answer_list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listQA);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
