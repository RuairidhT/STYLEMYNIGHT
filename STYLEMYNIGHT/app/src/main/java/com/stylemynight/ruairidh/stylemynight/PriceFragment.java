package com.stylemynight.ruairidh.stylemynight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PriceFragment extends Fragment implements View.OnClickListener {

    private View myView;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;


    public boolean button1pressed = false;
    public boolean button2pressed = false;
    public boolean button3pressed = false;
    public boolean button4pressed = false;
    public boolean button5pressed = false;
    public boolean button6pressed = false;

    public String Brands = "";
    public String companyId = "";
    public int uniqueId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_price, container, false);

        Bundle wardrobe = new Bundle();
        wardrobe.remove("Price1");
        wardrobe.remove("Price2");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Brands = bundle.getString("Brands");
            companyId = bundle.getString("companyId");
            uniqueId = bundle.getInt("uniqueId");
        }

        button1 = myView.findViewById(R.id.range1);
        button1.setOnClickListener(this);
        button2 = myView.findViewById(R.id.range2);
        button2.setOnClickListener(this);
        button3 = myView.findViewById(R.id.range3);
        button3.setOnClickListener(this);
        button4 = myView.findViewById(R.id.range4);
        button4.setOnClickListener(this);
        button5 = myView.findViewById(R.id.range5);
        button5.setOnClickListener(this);
        button6 = myView.findViewById(R.id.range6);
        button6.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.range1:
                button1pressed = true;
                button1.setBackgroundResource(R.drawable.pink_background);
                fragmentTransaction(0, 20);
                break;
            case R.id.range2:
                button2pressed = true;
                button2.setBackgroundResource(R.drawable.pink_background);
                fragmentTransaction(20, 30);
                break;
            case R.id.range3:
                button3.setBackgroundResource(R.drawable.pink_background);
                button3pressed = true;
                fragmentTransaction(30, 40);
                break;
            case R.id.range4:
                button4pressed = true;
                button4.setBackgroundResource(R.drawable.pink_background);
                fragmentTransaction(40, 50);
                break;
            case R.id.range5:
                button5pressed = true;
                button5.setBackgroundResource(R.drawable.pink_background);
                fragmentTransaction(50, 300);
                break;
            case R.id.range6:
                button6pressed = true;
                button6.setBackgroundResource(R.drawable.pink_background);
                fragmentTransaction(0, 301);
                break;
            default:
                break;
        }
    }

    public void fragmentTransaction(int i, int j) {

        LookFragment fragment = new LookFragment();
        Bundle wardrobe = new Bundle();
        wardrobe.putString("Brands", Brands);
        wardrobe.putInt("price1", i);
        wardrobe.putInt("price2", j);
        wardrobe.putString("companyId", companyId);
        wardrobe.putInt("uniqueId", uniqueId);
        fragment.setArguments(wardrobe);
        getFragmentManager().beginTransaction().replace(R.id.fragmet_placeholder, fragment).addToBackStack(null).commit();

    }
}

