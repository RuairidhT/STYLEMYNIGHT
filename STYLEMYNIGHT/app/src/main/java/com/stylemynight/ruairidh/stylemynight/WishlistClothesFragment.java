package com.stylemynight.ruairidh.stylemynight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class WishlistClothesFragment extends Fragment implements View.OnClickListener {

    private View myView;

    private String title;
    private String price;
    private String image;
    private String link;

    private Button favourites;
    private TextView titletv;
    private TextView pricetv;
    private Button linktv;
    private ImageView imageiv;

    private boolean isLoggedIn;
    CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_wishlist_clothes, container, false);

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();

        favourites = myView.findViewById(R.id.unfavouritebtn);
        favourites.setOnClickListener(this);
        titletv = myView.findViewById(R.id.wltitleTv);
        pricetv = myView.findViewById(R.id.wlPrice);
        linktv = myView.findViewById(R.id.wlLink);
        imageiv = myView.findViewById(R.id.wlImage);

        Bundle clothes = this.getArguments();
        if (clothes != null) {
            title = clothes.getString("wlTitle");
            price = clothes.getString("wlDescription");
            link = clothes.getString("wlLink");
            image = clothes.getString("wlImage");

        }

        String itemLink = "<a href='" + link + "'> BUY IT HERE! </a>";
        titletv.setText(title);
        pricetv.setText("£" + price);
        linktv.setText(Html.fromHtml(itemLink));
        linktv.setMovementMethod(LinkMovementMethod.getInstance());
        if (image.isEmpty()) {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/stylemynight-b6c18.appspot.com/o/no-photo.jpg?alt=media&token=850864b5-5cc2-43a2-9eaa-14eeaa743291).into(holder.mImageView").into(imageiv);
        } else {
            Picasso.get().load(image).into(imageiv);
        }

        return myView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.unfavouritebtn:
                if ((mAuth.getCurrentUser() != null) || (isLoggedIn == true)) {
                    removeFromFavourites();
                    break;
                } else {
                    Toast.makeText(getActivity(), "You're not signed in", Toast.LENGTH_SHORT).show();
                    break;
                }
            default:
                break;
        }
    }

    private void removeFromFavourites() {

        String id = null;

        favourites.setEnabled(false);

        if (mAuth.getCurrentUser() != null) {
            id = mAuth.getCurrentUser().getUid();
        }
        if (isLoggedIn == true) {
            Profile profile = Profile.getCurrentProfile();
            id = profile.getId();
        }

        db.collection("favourites").whereEqualTo("users_id", id).whereEqualTo("title", title).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String docId = task.getResult().getDocuments().get(0).getId();
                            db.collection("favourites").document(docId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    chooseFragment();
                                    Toast.makeText(getActivity(), "Item removed", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Error removing item", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {

                        }
                    }
                });

        favourites.setEnabled(true);

    }

    private void chooseFragment() {

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
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragmet_placeholder, new WishlistEmptyFragment());
                            transaction.commit();
                        } else {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragmet_placeholder, new WishlistFragment());
                            transaction.commit();
                        }
                    }
                });
    }
}

