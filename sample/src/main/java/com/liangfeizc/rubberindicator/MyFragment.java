package com.liangfeizc.rubberindicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hkq325800 on 2017/3/29.
 */

public class MyFragment extends Fragment {
    private TextView mFragmentTxt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mFragmentTxt = (TextView) view.findViewById(R.id.mFragmentTxt);
        mFragmentTxt.setText("this is " + getArguments().getString("this"));
        super.onViewCreated(view, savedInstanceState);
    }
}
