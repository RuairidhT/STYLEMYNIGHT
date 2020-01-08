package com.stylemynight.ruairidh.stylemynight;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;


public class FacebookFragment extends Fragment implements View.OnClickListener {

    private View myView;
    private Button logoutButn;
    private TextView facebookName;
    private ImageView profilePicture;
    private boolean isLoggedIn;
    CallbackManager mCallbackManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_facebook, container, false);

        mCallbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();

        facebookName = myView.findViewById(R.id.profileName);
        profilePicture = myView.findViewById(R.id.profilePicture);
        logoutButn = myView.findViewById(R.id.logOut);
        logoutButn.setOnClickListener(this);


        if (AccessToken.getCurrentAccessToken() != null) {
            Profile profile = Profile.getCurrentProfile();

            facebookName.setText(profile.getName());
            String profileImage = profile.getProfilePictureUri(400, 400).toString();

            Picasso.get().load(profileImage).into(profilePicture);
        }

        return myView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logOut:
                if (isLoggedIn == true) {
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(getActivity(), LoginScreen.class);
                    startActivity(intent);
                    this.getActivity().finish();
                }
                break;
            default:
                break;
        }
    }


}
