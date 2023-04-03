package com.example.oblivion;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class RecommendedProductsRecyclerViewAdapter  extends RecyclerView.Adapter<RecommendedProductsRecyclerViewAdapter.ViewHolder>{

    private List<SingleProduct> recommProducts;
    Context context;

    public RecommendedProductsRecyclerViewAdapter(List<SingleProduct> products, Context context) {
        this.recommProducts = products;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_one_product,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(recommProducts.get(position).getImg_url()).into(holder.iv_product_image);

    }


    @Override
    public int getItemCount() {
        return recommProducts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_product_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_product_image = itemView.findViewById(R.id.product_img);
        }
    }

}



