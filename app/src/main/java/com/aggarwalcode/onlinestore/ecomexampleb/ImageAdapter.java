package com.aggarwalcode.onlinestore.ecomexampleb;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lenovo-64 on 2/5/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> implements ProductsListFragment.OnFragmentInteractionListener{
    private Context mContext;
    RecyclerView recyclerViewEcom;
    private List<Upload> mUploads;

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.cards_ecom_landing, parent, false);
        return new ImageViewHolder(v);
    }

    public ImageAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textViewName;

        public ImageViewHolder (View itemView){
            super(itemView);
            textViewName = itemView.findViewById(R.id.image_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }

}
