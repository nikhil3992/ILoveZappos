package com.example.nikhil.zapposapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.nikhil.zapposapp.databinding.SingleProductLayoutBinding;
import java.util.List;


public class ProductsAdapter extends ArrayAdapter<Result> {

    private List<Result> products;
    private Context context;

    public ProductsAdapter(Context context, List<Result> list) {
        super(context,R.layout.single_product_layout,list);
        this.context = context;
        products = list;
    }

    public ProductsAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, (List<Result>) objects);
        this.context = context;
        products = objects;
    }

    @Override
    public int getPosition(Result item) {
        return super.getPosition(item);
    }

    @Nullable
    @Override
    public Result getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(products.get(position).getProductId());
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SingleProductLayoutBinding binding;
        if(convertView == null) {
             binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.single_product_layout, parent,false);
            convertView = binding.getRoot();
        } else {
            binding =  (SingleProductLayoutBinding) convertView.getTag();
        }
        binding.setProduct(this.getItem(position));
        binding.productOriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        convertView.setTag(binding);
        return convertView;
    }
}
