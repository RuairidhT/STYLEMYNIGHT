package com.stylemynight.ruairidh.stylemynight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


public class WishlistFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Query query;

    CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private boolean isLoggedIn;

    private CollectionReference modelRef = db.collection("favourites");
    private FavouritesAdapter adapter;


    private View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_wishlist, container, false);

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();

        setUpRecylcerView();


        return myView;
    }


    private void setUpRecylcerView() {

        String id = null;

        if (mAuth.getCurrentUser() != null) {
            id = mAuth.getCurrentUser().getUid();
        }
        if (isLoggedIn == true) {
            Profile profile = Profile.getCurrentProfile();
            id = profile.getId();
        }

        query = modelRef.whereEqualTo("users_id", id).orderBy("price");

        FirestoreRecyclerOptions<Favourites> options = new FirestoreRecyclerOptions.Builder<Favourites>()
                .setQuery(query, Favourites.class)
                .build();
        adapter = new FavouritesAdapter(options);


        RecyclerView recyclerView = myView.findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(myView.getContext(), 2));

        adapter.setOnItemClickListener(new FavouritesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Favourites favourites = documentSnapshot.toObject(Favourites.class);
                String mTitleTv = favourites.getTitle();
                String mPrice = favourites.getPrice();
                String mImageView = favourites.getImage();
                String mLink = favourites.getLink();

                WishlistClothesFragment fragment = new WishlistClothesFragment();
                Bundle clothes = new Bundle();
                clothes.putString("wlTitle", mTitleTv);
                clothes.putString("wlDescription", mPrice);
                clothes.putString("wlImage", mImageView);
                clothes.putString("wlLink", mLink);
                fragment.setArguments(clothes);
                getFragmentManager().beginTransaction().replace(R.id.fragmet_placeholder, fragment).addToBackStack(null).commit();

            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
