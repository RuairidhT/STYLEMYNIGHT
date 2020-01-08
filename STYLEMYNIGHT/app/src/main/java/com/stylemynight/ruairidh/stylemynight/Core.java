package com.stylemynight.ruairidh.stylemynight;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Core extends AppCompatActivity {

    private boolean isLoggedIn;
    CallbackManager mCallbackManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private FirebaseAuth mAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.fragmet_placeholder, new BrandFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                case R.id.navigation_wishlist:
                    if (mAuth.getCurrentUser() != null || isLoggedIn == true) {

                        String id = null;

                        if (mAuth.getCurrentUser() != null) {
                            id = mAuth.getCurrentUser().getUid();
                        }
                        if (isLoggedIn == true) {
                            Profile profile = Profile.getCurrentProfile();
                            id = profile.getId();
                        }

                        db.collection("favourites").whereEqualTo("users_id", id).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.getResult().isEmpty()) {
                                            FragmentManager fragmentManager = getSupportFragmentManager();
                                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                                            transaction.replace(R.id.fragmet_placeholder, new WishlistEmptyFragment());
                                            transaction.addToBackStack(null);
                                            transaction.commit();
                                        } else {
                                            FragmentManager fragmentManager = getSupportFragmentManager();
                                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                                            transaction.replace(R.id.fragmet_placeholder, new WishlistFragment());
                                            transaction.addToBackStack(null);
                                            transaction.commit();
                                        }
                                    }
                                });
                        return true;
                    } else {
                        transaction.replace(R.id.fragmet_placeholder, new WishlistLoginFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                case R.id.navigation_me:
                    //email log in
                    if (mAuth.getCurrentUser() != null) {
                        transaction.replace(R.id.fragmet_placeholder, new ProfileFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                    //facebook login
                    if (isLoggedIn == true) {
                        transaction.replace(R.id.fragmet_placeholder, new FacebookFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                    //not logged in
                    else {
                        transaction.replace(R.id.fragmet_placeholder, new EmptyProfileFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_brand);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmet_placeholder, new BrandFragment());
        transaction.commit();
    }

}
