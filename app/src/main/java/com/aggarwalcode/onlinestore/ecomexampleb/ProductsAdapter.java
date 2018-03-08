package com.aggarwalcode.onlinestore.ecomexampleb;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lenovo-64 on 2/11/2018.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>{

    public Context context;
    public String imageUrl;
    public String brand;
    public String description;
    public int price;
    public String other;
    public String name;
    public List<ProductsHolder> mProducts;

    public ProductsAdapter(Context context, String imageUrl, String brand, String description, int price, String other, String name, List <ProductsHolder> mProducts) {
        this.context = context;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.other = other;
        this.name = name;
        this.mProducts = mProducts;
    }

    public ProductsAdapter(){}
    public ProductsAdapter(FragmentActivity activity, List <ProductsHolder> mProducts) {
        this.context = activity;
        this.mProducts = mProducts;
    }


    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.products_card_view, parent, false);
        return new ProductsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        final ProductsHolder products = mProducts.get(position);
        holder.brand.setText(products.getBrand());
        holder.description.setText(products.getDescription());
        holder.name.setText(products.getName());
        holder.price.setText(String.valueOf(products.getPrice()));
        Picasso.with(context)
                .load(products.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.productImg);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {

        ImageView productImg;
        TextView brand,description,other,name,price;

        public ProductsViewHolder(View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.productBrand );
            description = itemView.findViewById(R.id.productDesc );
            name = itemView.findViewById(R.id.productName );
            price = itemView.findViewById(R.id.productPrice );
            productImg = itemView.findViewById(R.id.productImg);
        }
    }
}
