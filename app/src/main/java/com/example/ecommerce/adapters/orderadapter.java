package com.example.ecommerce.adapters;



import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.databinding.ItemProductBinding;
import com.example.ecommerce.databinding.TimelineBinding;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.StringUtils;
import com.example.ecommerce.model.order;

import java.util.ArrayList;

public class orderadapter extends RecyclerView.Adapter<orderadapter.OrderViewHolder>{
    Context context;
    ArrayList<order> products;

    public orderadapter(Context context, ArrayList<order> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new orderadapter.OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.timeline, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
                order order=products.get(position);
               if(order.getConfirmed().equals("waiting")) {

//             String a= StringUtils.difference( order.getImage(),("https://dummyjson.com/image/i/products/4/thumbnail.jpg"));
                   Toast.makeText(context.getApplicationContext(), order.getImage(),Toast.LENGTH_LONG).show();
                   Glide.with(context)
                           .load(order.getImage())
                           .into(holder.binding.productimg);
                   holder.binding.textView3.setBackground(ContextCompat.getDrawable(context, R.drawable.colorfullcircle));
                   holder.binding.fline.setBackground(ContextCompat.getDrawable(context, R.drawable.colorfullline));
                   holder.binding.customerProductdetails.setText(order.getName());
                   holder.binding.frec.setVisibility(View.VISIBLE);
                   holder.binding.frec.setBackground(ContextCompat.getDrawable(context,R.drawable.circular_grey_bordersolid));

               }else if(order.getConfirmed().equals("confirmed")){
                   Glide.with(context)
                           .load(order.getImage())
                           .into(holder.binding.productimg);
                   holder.binding.customerProductdetails.setText(order.getName());
                   holder.binding.textView3.setBackground(ContextCompat.getDrawable(context, R.drawable.colorfullcircle));
                   holder.binding.fline.setBackground(ContextCompat.getDrawable(context, R.drawable.colorfullline));
                   holder.binding.textView4.setBackground(ContextCompat.getDrawable(context, R.drawable.colorfullcircle));
                   holder.binding.sline.setBackground(ContextCompat.getDrawable(context, R.drawable.colorfullline));
                   holder.binding.frec.setVisibility(View.VISIBLE);
                   holder.binding.frec.setBackground(ContextCompat.getDrawable(context,R.drawable.circular_grey_bordersolid));
                   holder.binding.srec.setVisibility(View.VISIBLE);
                   holder.binding.srec.setBackground(ContextCompat.getDrawable(context,R.drawable.circular_grey_bordersolid));
               }else{
                   Glide.with(context)
                           .load(order.getImage())
                           .into(holder.binding.productimg);
                   holder.binding.textView5.setBackground(ContextCompat.getDrawable(context, R.drawable.colorfullcircle));
                   holder.binding.textView3.setBackground(ContextCompat.getDrawable(context, R.drawable.colorfullcircle));
                   holder.binding.fline.setBackground(ContextCompat.getDrawable(context, R.drawable.colorfullline));
                   holder.binding.textView4.setBackground(ContextCompat.getDrawable(context, R.drawable.colorfullcircle));
                   holder.binding.sline.setBackground(ContextCompat.getDrawable(context, R.drawable.colorfullline));
                   holder.binding.srec.setVisibility(View.VISIBLE);
                   holder.binding.customerProductdetails.setText(order.getName());
                   holder.binding.frec.setVisibility(View.VISIBLE);
                   holder.binding.frec.setBackground(ContextCompat.getDrawable(context,R.drawable.circular_grey_bordersolid));
                   holder.binding.srec.setBackground(ContextCompat.getDrawable(context,R.drawable.circular_grey_bordersolid));
                   holder.binding.trec.setVisibility(View.VISIBLE);
                   holder.binding.trec.setBackground(ContextCompat.getDrawable(context,R.drawable.circular_grey_bordersolid));
               }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
         TimelineBinding binding;
         RelativeLayout f1,s1,t1;
         TextView t1line,s1line,f1line,waiting,confirmed,delivered;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =  TimelineBinding.bind(itemView);
        }
    }
}
