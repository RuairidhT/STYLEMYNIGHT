package com.stylemynight.ruairidh.stylemynight;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class ClothingFragment extends Fragment implements View.OnClickListener {

    private View myView;

    private String title;
    private String price;
    private String image;
    private String link;
    private String company;

    private Button favourites;
    private boolean isLoggedIn;

    CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView mTitleTv, mDetailTv;
    Button mlink;
    ImageView mImageTv;

    private Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_clothing, container, false);

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();


        mTitleTv = myView.findViewById(R.id.titleTv);
        mDetailTv = myView.findViewById(R.id.description);
        mImageTv = myView.findViewById(R.id.imageView);
        mlink = myView.findViewById(R.id.link);

        favourites = myView.findViewById(R.id.favouritebtn);
        favourites.setOnClickListener(this);

        //https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application
        Bundle clothes = this.getArguments();
        if (clothes != null) {
            title = clothes.getString("Title");
            price = clothes.getString("Description");
            link = clothes.getString("link");
            image = clothes.getString("image");
            company = clothes.getString("brand");
        }

        String itemLink = "<a href='" + link + "'> BUY IT HERE </a>";
        mTitleTv.setText(title);
        mDetailTv.setText("£" + price);
        mlink.setText(Html.fromHtml(itemLink));

        mlink.setMovementMethod(LinkMovementMethod.getInstance());

        if (image.isEmpty()) {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/stylemynight-b6c18.appspot.com/o/no-photo.jpg?alt=media&token=850864b5-5cc2-43a2-9eaa-14eeaa743291).into(holder.mImageView").into(mImageTv);
        } else {
            Picasso.get().load(image).into(mImageTv);
        }

        return myView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favouritebtn:
                if ((mAuth.getCurrentUser() != null) || (isLoggedIn == true)) {
                    addToFavourites();
                    break;
                } else {
                    showDialogbox();
                    break;
                }
            default:
                break;
        }
    }

    private void showDialogbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        builder.setView(mView);

        Button signIn = mView.findViewById(R.id.signInD);
        Button register = mView.findViewById(R.id.registerD);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), LoginScreen.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), createAccountActivity.class);
                startActivity(intent);
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addToFavourites() {

        String id = null;

        final Map<String, Object> clothingData = new HashMap<>();
        if (mAuth.getCurrentUser() != null) {
            id = mAuth.getCurrentUser().getUid();
        }
        if (isLoggedIn == true) {
            Profile profile = Profile.getCurrentProfile();
            id = profile.getId();
        }
        clothingData.put("users_id", id);
        clothingData.put("title", title);
        clothingData.put("price", price);
        clothingData.put("image", image);
        clothingData.put("link", link);
        clothingData.put("company", company);


        db.collection("favourites").whereEqualTo("users_id", id).whereEqualTo("title", title).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult().isEmpty()) {
                            db.collection("favourites").add(clothingData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getActivity(), "Added to wishlist", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Already in wishlist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

