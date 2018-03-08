package com.aggarwalcode.onlinestore.ecomexampleb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aggarwalcode.onlinestore.ecomexampleb.ItemFragmentCat.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter <MyItemRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<ShopByCat> mShopByCat;
    private int mOriginalHeight = 0;
    private boolean mIsViewExpanded = false;


    public MyItemRecyclerViewAdapter(Context context, List<ShopByCat> catHeader) {
        mShopByCat = catHeader;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        ShopByCat shopByCat = mShopByCat.get(position);
        holder.mContentView.setText(shopByCat.getHeader());

    }

    @Override
    public int getItemCount() {
        return mShopByCat.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public ShopByCat shopByCat;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
