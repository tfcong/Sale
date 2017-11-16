package com.ptc.csale.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptc.csale.R;
import com.ptc.csale.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by phamthanhcong on 11/11/2017.
 */

public class ProductAdapter extends BaseAdapter {
    private List<Product> listProduct;
    private LayoutInflater inflater;

    public ProductAdapter(Context context, List<Product> listProduct) {
        this.listProduct = listProduct;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int i) {
        return listProduct.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_product, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imageViewThumbnail = view.findViewById(R.id.image_view_thumbnail);
            viewHolder.tvTitle = view.findViewById(R.id.tv_product_title);
            viewHolder.tvPrice = view.findViewById(R.id.tv_product_price);
            viewHolder.tvAddress = view.findViewById(R.id.tv_product_address);
            viewHolder.tvTime = view.findViewById(R.id.tv_product_time);
            view.setTag(viewHolder);        }
            else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = listProduct.get(i);
        Picasso.with(view.getContext()).load(product.getThumbnail()).into(viewHolder.imageViewThumbnail);
        viewHolder.tvTitle.setText(product.getTitle());
        viewHolder.tvPrice.setText(product.getPrice());
        viewHolder.tvAddress.setText(product.getAddress());
        viewHolder.tvTime.setText(product.getTime());
        return view;
    }

    private class ViewHolder {
        ImageView imageViewThumbnail;
        TextView tvTitle, tvPrice, tvAddress, tvTime;

    }
}
