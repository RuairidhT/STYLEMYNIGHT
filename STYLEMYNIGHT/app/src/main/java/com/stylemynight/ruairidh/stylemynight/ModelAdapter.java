package com.stylemynight.ruairidh.stylemynight;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

//https://www.youtube.com/watch?v=ub6mNHWGVHw&fbclid=IwAR2FGapffEfh_wU3rtSSUbYvyNbsU0BApON0Ho0dTxgioNWmPPwKz0KaqvE

public class ModelAdapter extends FirestoreRecyclerAdapter<Model, ModelAdapter.ModelHolder> {

    private OnItemClickListener listener;

    public ModelAdapter(@NonNull FirestoreRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ModelHolder holder, int position, @NonNull Model model) {
        holder.mTitleTv.setText(model.getTitle());
        if (model.getImage().isEmpty()) {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/stylemynight-b6c18.appspot.com/o/no-photo.jpg?alt=media&token=850864b5-5cc2-43a2-9eaa-14eeaa743291).into(holder.mImageView").into(holder.mImageView);
        } else {
            Picasso.get().load(model.getImage()).into(holder.mImageView);
        }
        holder.mBrand.setText(model.getCompany());

        String thePrice = String.format("%.2f", model.getPrice());
        holder.mPrice.setText("£" + thePrice);
    }

    @NonNull
    @Override
    public ModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ModelHolder(v);
    }

    class ModelHolder extends RecyclerView.ViewHolder {
        TextView mTitleTv;
        TextView mPrice;
        ImageView mImageView;
        TextView mBrand;

        public ModelHolder(@NonNull View itemView) {
            super(itemView);

            mTitleTv = itemView.findViewById(R.id.rTitleTv);
            mPrice = itemView.findViewById(R.id.rDescription);
            mImageView = itemView.findViewById(R.id.rImageView);
            mBrand = itemView.findViewById(R.id.rCompany);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);

                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
