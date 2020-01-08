package com.stylemynight.ruairidh.stylemynight;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewHolderLike extends RecyclerView.ViewHolder {

    View mView;


    public ViewHolderLike(View itemView){
        super((itemView));

        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
    }

    public void setDetails(Context ctx, String title, String price, String image, String brand) {
        TextView mTitleTv = mView.findViewById(R.id.rTitleTv);
        TextView mDetailTv = mView.findViewById(R.id.rDescription);
        ImageView mImageTv = mView.findViewById(R.id.rImageView);
        TextView mBrandTv = mView.findViewById(R.id.rCompany);

        mBrandTv.setText(brand);
        mTitleTv.setText(title);
        mDetailTv.setText("£" + price);
        Picasso.get().load(image).into(mImageTv);
    }

    private  ViewHolder.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position);

    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
