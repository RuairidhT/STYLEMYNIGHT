package com.stylemynight.ruairidh.stylemynight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class EmptyProfileFragment extends Fragment implements View.OnClickListener {

    private View myView;

    private TextView signin;
    private TextView register;
    private Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_empty_profile, container, false);

        signin = myView.findViewById(R.id.signin1);
        register = myView.findViewById(R.id.register1);

        register.setOnClickListener(this);
        signin.setOnClickListener(this);

        return myView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin1:
                intent = new Intent(getActivity(), LoginScreen.class);
                startActivity(intent);
                break;
            case R.id.register1:
                intent = new Intent(getActivity(), createAccountActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }


}
