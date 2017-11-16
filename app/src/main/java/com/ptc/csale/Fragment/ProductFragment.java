package com.ptc.csale.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ptc.csale.R;
import com.ptc.csale.activity.MainActivity;
import com.ptc.csale.adapter.ProductAdapter;
import com.ptc.csale.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by phamthanhcong on 10/11/2017.
 */

public class ProductFragment extends Fragment {
    private View mRootView;
    private Spinner spCategory, spAddress;
    private Button btnSearch;
    private GridView gridViewProduct;
    private String[] listCategory, listAddress;
    private ArrayAdapter aaCategory, aaAddress;
    private ArrayList<Product> productArrayList;
    private ProductAdapter productAdapter;
    private ImageView imageRefresh;
    String urlReceiveProduct = MainActivity.urlReceiveProduct;
    String TAG = "Error";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_product, container, false);
        initData();
        initViews();
        Log.e(TAG, "onCreateView" );
        initControls();
        return mRootView;
    }
    private void initControls() {
        imageRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadJSonProduct(urlReceiveProduct);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conditionProduct();
            }
        });
    }
    private void conditionProduct() {
        ArrayList<Product> pArray = new ArrayList<>();
        String category = spCategory.getSelectedItem().toString();
        String address = spAddress.getSelectedItem().toString();
        if (category.equals("Danh mục") && address.equals("Địa chỉ")) {
            ReadJSonProduct(urlReceiveProduct);
        } else if (category.equals("Danh mục") || address.equals("Địa chỉ")) {
            for (int i = 0; i < productArrayList.size(); i++) {
                if (productArrayList.get(i).getAddress().equals(address) || productArrayList.get(i).getCategory().equals(category)) {
                    pArray.add(productArrayList.get(i));
                }
            }
        } else {
            for (int i = 0; i < productArrayList.size(); i++) {
                if (productArrayList.get(i).getAddress().equals(address) && productArrayList.get(i).getCategory().equals(category)) {
                    pArray.add(productArrayList.get(i));
                }
            }
        }
        productAdapter = new ProductAdapter(mRootView.getContext(), pArray);
        gridViewProduct.setAdapter(productAdapter);
    }
    private void initData() {
        listCategory = new String[]{"Danh mục", "Bất động sản", "Dụng cụ học tập", "Dụng cụ nấu ăn"};
        listAddress = new String[]{"Địa chỉ", "Hải Phòng", "Hưng Yên", "Đà Nẵng", "Thành phố Hồ Chí Minh"};
    }
    private void initViews() {
productArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(mRootView.getContext(), productArrayList);
        spCategory = mRootView.findViewById(R.id.sp_category);
        spAddress = mRootView.findViewById(R.id.sp_address);
        btnSearch = mRootView.findViewById(R.id.btn_search);
        gridViewProduct = mRootView.findViewById(R.id.grid_view_product);
        gridViewProduct.setAdapter(productAdapter);
        aaCategory = new ArrayAdapter(mRootView.getContext(), android.R.layout.simple_spinner_dropdown_item, listCategory);
        aaAddress = new ArrayAdapter(mRootView.getContext(), android.R.layout.simple_spinner_dropdown_item, listAddress);
        imageRefresh = mRootView.findViewById(R.id.image_view_refresh);
        spCategory.setAdapter(aaCategory);
        spAddress.setAdapter(aaAddress);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
    public void ReadJSonProduct(String url) {
        productArrayList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(mRootView.getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String id = object.getString("id");
                        String name = object.getString("name");
                        String phoneNumber = object.getString("phoneNumber");
                        String category = object.getString("category");
                        String title = object.getString("title");
                        String price = object.getString("price");
                        String address = object.getString("address");
                        String thumbnail = object.getString("thumbnail");
                        String time = object.getString("time");
                        String love = object.getString("love");
                        String userId = object.getString("userId");
                        String userName = object.getString("userName");
                        String type = object.getString("type");
                        Product product = new Product(name, phoneNumber, category, title, price, address, thumbnail, time);
                        productArrayList.add(product);
                        productAdapter = new ProductAdapter(mRootView.getContext(), productArrayList);
                        gridViewProduct.setAdapter(productAdapter);
                        Log.e(TAG, productArrayList.size()+"x" );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    @Override
    public void onResume() {
        ReadJSonProduct(urlReceiveProduct);
        Log.e(TAG, "onResume " );
        Log.e(TAG, urlReceiveProduct );
        super.onResume();
    }
}
