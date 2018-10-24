package itinterview.arpan.com.itinterview.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import itinterview.arpan.com.itinterview.R;
import itinterview.arpan.com.itinterview.listener.FetchContactUs;

import itinterview.arpan.com.itinterview.tables.ContactUs;
import itinterview.arpan.com.itinterview.utility.FireBaseUtility;

public class ContactUsFragment extends Fragment implements FetchContactUs {



    private View fragmentView;
    private TextView mTvContact,mtvphone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.contact_us_activity, container, false);
       mTvContact = fragmentView.findViewById(R.id.tv_contact);
        mtvphone = fragmentView.findViewById(R.id.tv_mobileNo);

       FireBaseUtility.getContact(this);
      return fragmentView;
    }


    @Override
    public void onContactSuccess(ContactUs contactUs) {
       mTvContact.setText(contactUs.getEmail());
        mtvphone.setText(contactUs.getMobileNo());
        mTvContact.setPaintFlags(View.INVISIBLE);


    }

    @Override
    public void onContactFailure(Exception exception) {
        Log.w("arpan", "getUser:onCancelled"+ exception.getMessage());
    }
}
