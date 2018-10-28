package itinterview.arpan.com.itinterview.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by philips on 10/27/18.
 */

public class BaseFragment extends Fragment {

    private ProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(getActivity());
    }

    protected void showProgressDialog() {

        if(dialog == null){
            dialog = new ProgressDialog(getActivity());
        }
        dialog.setCancelable(false);
        dialog.setMessage("Fetching data..");

        if(!dialog.isShowing())
            dialog.show();


    }

    protected void dissMissProgressDialog(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }

    }
}
