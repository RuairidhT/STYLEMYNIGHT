package com.stylemynight.ruairidh.stylemynight;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class BrandFragment extends Fragment implements View.OnClickListener {

    private boolean firstTime;


    private View myView;
    private ImageButton button1;
    private ImageButton button2;
    private ImageButton button3;
    private ImageButton button4;
    private Button button5;

    public static boolean button1pressed = false;
    public static boolean button2pressed = false;
    public static boolean button3pressed = false;
    public static boolean button4pressed = false;

    public static String brands = "";
    public static String companyId = "";
    public static int uniqueId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_brand_selection, container, false);

        SharedPreferences settings = this.getContext().getSharedPreferences("PREFS", 0);
        firstTime = settings.getBoolean("firstTimeStart", true);

        if (firstTime){
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstTimeStart", false).commit();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_brand, null);
            builder.setView(mView);
            AlertDialog dialog = builder.create();
            dialog.show();
        }


        button1 = myView.findViewById(R.id.boohoobutton);
        button1.setOnClickListener(this);
        button2 = myView.findViewById(R.id.PTLbutton);
        button2.setOnClickListener(this);
        button3 = myView.findViewById(R.id.missguidedbuttin);
        button3.setOnClickListener(this);
        button4 = myView.findViewById(R.id.inthestylebutton);
        button4.setOnClickListener(this);
        button5 = myView.findViewById(R.id.nextbutton);
        button5.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        brands = "";
        companyId = "";
        uniqueId = 0;

        button1pressed = false;
        button2pressed = false;
        button3pressed = false;
        button4pressed = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boohoobutton:
                if (button1pressed == true) {
                    button1.setBackgroundResource(R.drawable.white_background);
                    button1pressed = false;
                    break;
                }
                if (button1pressed == false) {
                    button1.setBackgroundResource(R.drawable.pink_background);
                    button1pressed = true;
                    break;
                }
            case R.id.PTLbutton:
                if (button2pressed == true) {
                    button2.setBackgroundResource(R.drawable.white_background);
                    button2pressed = false;
                    break;
                }
                if (button2pressed == false) {
                    button2.setBackgroundResource(R.drawable.pink_background);
                    button2pressed = true;
                    break;
                }
            case R.id.missguidedbuttin:
                if (button3pressed == true) {
                    button3.setBackgroundResource(R.drawable.white_background);
                    button3pressed = false;
                    break;
                }
                if (button3pressed == false) {
                    button3.setBackgroundResource(R.drawable.pink_background);
                    button3pressed = true;
                    break;
                }
            case R.id.inthestylebutton:
                if (button4pressed == true) {
                    button4.setBackgroundResource(R.drawable.white_background);
                    button4pressed = false;
                    break;
                }
                if (button4pressed == false) {
                    button4.setBackgroundResource(R.drawable.pink_background);
                    button4pressed = true;
                    break;
                }
            case R.id.nextbutton:
                if (button1pressed == false && button2pressed == false && button3pressed == false && button4pressed == false) {
                    Toast.makeText(getContext(), "Please select a brand", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    if (button1pressed == true) {
                        brands += ",Boohoo";
                    }
                    if (button2pressed == true) {
                        brands += ",Pretty Little Thing";
                    }
                    if (button3pressed == true) {
                        brands += ",MissGuided";
                    }
                    if (button4pressed == true) {
                        brands += ",In The Style";
                    }

                    getIds();

                    PriceFragment fragment = new PriceFragment();
                    Bundle wardrobe = new Bundle();
                    wardrobe.putString("Brands", brands);
                    wardrobe.putString("companyId", companyId);
                    wardrobe.putInt("uniqueId", uniqueId);
                    fragment.setArguments(wardrobe);
                    getFragmentManager().beginTransaction().replace(R.id.fragmet_placeholder, fragment).addToBackStack(null).commit();

                    break;
                }
            default:
                break;
        }

    }

    private void getIds() {

        //1 brand selected
        if (button1pressed == true && button2pressed == false && button3pressed == false && button4pressed == false) {
            companyId = "companyid1";
            uniqueId = 1;
        }
        if (button1pressed == false && button2pressed == true && button3pressed == false && button4pressed == false) {
            companyId = "companyid1";
            uniqueId = 2;
        }
        if (button1pressed == false && button2pressed == false && button3pressed == true && button4pressed == false) {
            companyId = "companyid1";
            uniqueId = 3;
        }
        if (button1pressed == false && button2pressed == false && button3pressed == false && button4pressed == true) {
            companyId = "companyid1";
            uniqueId = 4;
        }

        //2 brands selected
        if (button1pressed == true && button2pressed == true && button3pressed == false && button4pressed == false) {
            companyId = "companyid2";
            uniqueId = 5;
        }
        if (button1pressed == true && button2pressed == false && button3pressed == true && button4pressed == false) {
            companyId = "companyid3";
            uniqueId = 6;
        }
        if (button1pressed == true && button2pressed == false && button3pressed == false && button4pressed == true) {
            companyId = "companyid4";
            uniqueId = 7;
        }
        if (button1pressed == false && button2pressed == true && button3pressed == true && button4pressed == false) {
            companyId = "companyid4";
            uniqueId = 8;
        }
        if (button1pressed == false && button2pressed == true && button3pressed == false && button4pressed == true) {
            companyId = "companyid3";
            uniqueId = 9;
        }
        if (button1pressed == false && button2pressed == false && button3pressed == true && button4pressed == true) {
            companyId = "companyid2";
            uniqueId = 10;
        }

        //3 brands selected
        if (button1pressed == true && button2pressed == false && button3pressed == true && button4pressed == true) {
            companyId = "companyid5";
            uniqueId = 11;
        }
        if (button1pressed == true && button2pressed == true && button3pressed == false && button4pressed == true) {
            companyId = "companyid6";
            uniqueId = 12;
        }
        if (button1pressed == true && button2pressed == true && button3pressed == true && button4pressed == false) {
            companyId = "companyid7";
            uniqueId = 13;
        }
        if (button1pressed == false && button2pressed == true && button3pressed == true && button4pressed == true) {
            companyId = "companyid8";
            uniqueId = 14;
        }

        //4 brands selected
        if (button1pressed == true && button2pressed == true && button3pressed == true && button4pressed == true) {
            companyId = "companyid9";
            uniqueId = 15;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
