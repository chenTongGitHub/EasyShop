package edu.feicui.easyshopdemo.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.feicui.easyshopdemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnLoginFragment extends Fragment {


    public UnLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_un_login, container, false);
    }

}
