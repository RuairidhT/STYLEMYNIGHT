package com.stylemynight.ruairidh.stylemynight;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class WishlistEmptyFragment extends Fragment implements View.OnClickListener {

    private View myView;

    private Button shop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_wishlist_empty, container, false);

        shop = myView.findViewById(R.id.shopBtn);
        shop.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopBtn:
                BrandFragment fragment = new BrandFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragmet_placeholder, fragment).addToBackStack(null).commit();
                break;
            default:
                break;
        }
    }

}
