package com.stylemynight.ruairidh.stylemynight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LookFragment extends Fragment implements View.OnClickListener {

    private View myView;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;


    public boolean button1pressed = false;
    public boolean button2pressed = false;
    public boolean button3pressed = false;
    public boolean button4pressed = false;
    public boolean button5pressed = false;
    public boolean button6pressed = false;
    public boolean button7pressed = false;
    public boolean button8pressed = false;
    public boolean button9pressed = false;

    public static String Brands = "";
    public static int Price1;
    public static int Price2;
    public static String Looks = "";
    public static String companyId = "";
    public static int uniqueId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            Price1 = bundle.getInt("price1");
            Price2 = bundle.getInt("price2");
            Brands = bundle.getString("Brands");
            companyId = bundle.getString("companyId");
            uniqueId = bundle.getInt("uniqueId");
        }


        myView = inflater.inflate(R.layout.fragment_look, container, false);

        button1 = myView.findViewById(R.id.trendone);
        button1.setOnClickListener(this);
        button2 = myView.findViewById(R.id.trendtwo);
        button2.setOnClickListener(this);
        button3 = myView.findViewById(R.id.trendthree);
        button3.setOnClickListener(this);
        button4 = myView.findViewById(R.id.trendfour);
        button4.setOnClickListener(this);
        button5 = myView.findViewById(R.id.occone);
        button5.setOnClickListener(this);
        button6 = myView.findViewById(R.id.occtwo);
        button6.setOnClickListener(this);
        button7 = myView.findViewById(R.id.occthree);
        button7.setOnClickListener(this);
        button8 = myView.findViewById(R.id.occfour);
        button8.setOnClickListener(this);
        button9 = myView.findViewById(R.id.all);
        button9.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Looks = "";

        button1pressed = false;
        button2pressed = false;
        button3pressed = false;
        button4pressed = false;
        button5pressed = false;
        button6pressed = false;
        button7pressed = false;
        button8pressed = false;
        button9pressed = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.trendone:
                button1pressed = true;
                button1.setBackgroundResource(R.drawable.white_background);
                Looks = "animal print";
                fragmentTransaction();
                break;
            case R.id.trendtwo:
                button2pressed = true;
                button2.setBackgroundResource(R.drawable.white_background);
                Looks = "neon";
                fragmentTransaction();
                break;
            case R.id.trendthree:
                button3pressed = true;
                button3.setBackgroundResource(R.drawable.white_background);
                Looks = "90s";
                fragmentTransaction();
                break;
            case R.id.trendfour:
                button4pressed = true;
                button4.setBackgroundResource(R.drawable.white_background);
                Looks = "polka dot";
                fragmentTransaction();
                break;
            case R.id.occone:
                button5pressed = true;
                button5.setBackgroundResource(R.drawable.white_background);
                Looks = "night out";
                fragmentTransaction();
                break;
            case R.id.occtwo:
                button6pressed = true;
                button6.setBackgroundResource(R.drawable.white_background);
                Looks = "date night";
                fragmentTransaction();
                break;
            case R.id.occthree:
                button7pressed = true;
                button7.setBackgroundResource(R.drawable.white_background);
                Looks = "brunch";
                fragmentTransaction();
                break;
            case R.id.occfour:
                button8pressed = true;
                button8.setBackgroundResource(R.drawable.white_background);
                Looks = "xmas";
                fragmentTransaction();
                break;
            case R.id.all:
                button9pressed = true;
                button9.setBackgroundResource(R.drawable.white_background);
                Looks = "all";
                fragmentTransaction();
                break;
            default:
                break;
        }
    }


    public void fragmentTransaction() {
        WardrobeFragment fragment = new WardrobeFragment();
        Bundle wardrobe = new Bundle();
        wardrobe.remove("Looks");
        wardrobe.putString("Brands", Brands);
        wardrobe.putInt("price1", Price1);
        wardrobe.putInt("price2", Price2);
        wardrobe.putString("Looks", Looks);
        wardrobe.putString("companyId", companyId);
        wardrobe.putInt("uniqueId", uniqueId);
        fragment.setArguments(wardrobe);
        getFragmentManager().beginTransaction().replace(R.id.fragmet_placeholder, fragment).addToBackStack(null).commit();
    }

}
