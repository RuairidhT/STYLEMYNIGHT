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

public class FavouritesAdapter extends FirestoreRecyclerAdapter<Favourites, FavouritesAdapter.FavouritesHolder> {

    private OnItemClickListener listener;

    public FavouritesAdapter(@NonNull FirestoreRecyclerOptions<Favourites> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FavouritesHolder holder, int position, @NonNull Favourites favourites) {
        holder.mTitleTv.setText(favourites.getTitle());
        if (favourites.getImage().isEmpty()) {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/stylemynight-b6c18.appspot.com/o/no-photo.jpg?alt=media&token=850864b5-5cc2-43a2-9eaa-14eeaa743291).into(holder.mImageView").into(holder.mImageView);
        } else {
            Picasso.get().load(favourites.getImage()).into(holder.mImageView);
        }

        holder.mBrand.setText(favourites.getCompany());
        holder.mPrice.setText("£" + favourites.getPrice());
    }

    @NonNull
    @Override
    public FavouritesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new FavouritesHolder(v);
    }

    class FavouritesHolder extends RecyclerView.ViewHolder {
        TextView mTitleTv;
        TextView mPrice;
        ImageView mImageView;
        TextView mBrand;

        public FavouritesHolder(@NonNull View itemView) {
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
