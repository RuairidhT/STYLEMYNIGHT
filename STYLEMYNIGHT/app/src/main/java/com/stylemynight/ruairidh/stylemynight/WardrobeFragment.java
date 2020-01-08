package com.stylemynight.ruairidh.stylemynight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class WardrobeFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static int Price1;
    public static int Price2;
    public static String Brands = "";
    public static String Looks = "";
    public static String companyId = "";
    public static int uniqueId;
    public Query query;

    private CollectionReference modelRef = db.collection("clothes");
    private ModelAdapter adapter;


    private View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_wardrobe, container, false);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            Price1 = bundle.getInt("price1");  //bottom end
            Price2 = bundle.getInt("price2");  //top end
            Brands = bundle.getString("Brands");
            Looks = bundle.getString("Looks");
            companyId = bundle.getString("companyId");
            uniqueId = bundle.getInt("uniqueId");
        }

        setUpRecylcerView(Price1, Price2);


        return myView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //https://www.youtube.com/watch?v=ub6mNHWGVHw&fbclid=IwAR2FGapffEfh_wU3rtSSUbYvyNbsU0BApON0Ho0dTxgioNWmPPwKz0KaqvE
    private void setUpRecylcerView(int i, int j) {

        if (Looks == "all") {
            query = modelRef.whereEqualTo(companyId, uniqueId).whereLessThanOrEqualTo("price", j).whereGreaterThanOrEqualTo("price", i);

        } else {
            query = modelRef.whereEqualTo(companyId, uniqueId).whereEqualTo("look", Looks).whereLessThanOrEqualTo("price", j).whereGreaterThanOrEqualTo("price", i);
        }

        FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                .setQuery(query, Model.class)
                .build();
        adapter = new ModelAdapter(options);


        RecyclerView recyclerView = myView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(myView.getContext(), 2));

        adapter.setOnItemClickListener(new ModelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Model model = documentSnapshot.toObject(Model.class);
                String mTitleTv = model.getTitle();
                String mPrice = String.format("%.2f", model.getPrice());
                String mImageView = model.getImage();
                String mLink = model.getLink();
                String mBrand = model.getCompany();

                ClothingFragment fragment = new ClothingFragment();
                Bundle clothes = new Bundle();
                clothes.putString("Title", mTitleTv);
                clothes.putString("Description", mPrice);
                clothes.putString("image", mImageView);
                clothes.putString("link", mLink);
                clothes.putString("brand", mBrand);
                fragment.setArguments(clothes);
                getFragmentManager().beginTransaction().replace(R.id.fragmet_placeholder, fragment).addToBackStack(null).commit();

            }
        });

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
