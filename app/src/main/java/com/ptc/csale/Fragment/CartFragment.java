package com.ptc.csale.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by phamthanhcong on 10/11/2017.
 */

public class CartFragment extends Fragment {
    private View mRootView;

    private ListView lvProduct;
    private ArrayList<Product> productArrayList;
    private ProductAdapter productAdapter;

    private String[] productType = {"Sản Phẩm Mua", "Sản Phẩm Bán"};
    private Spinner spProductType;
    private ArrayAdapter aaProductType;
    private String userCurrentName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_cart, container, false);
        initData();
        initViews();
        return mRootView;
    }

    private void initData() {
        productArrayList = new ArrayList<>();
        SharedPreferences pref = mRootView.getContext().getSharedPreferences(MainActivity.FILE_NAME, MODE_PRIVATE);
        userCurrentName=pref.getString("name", "");
        ReadJSonProduct(MainActivity.urlReceiveProduct,"true");
    }

    public void ReadJSonProduct(String url, final String productType) {
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
                        String type = object.getString("type");
                        Product product = new Product(name, phoneNumber, category, title, price, address, thumbnail, time);
                        productArrayList.add(product);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userName", userCurrentName);
                map.put("phoneNumber", productType);
                return map;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

    private void initViews() {
        lvProduct = mRootView.findViewById(R.id.lv_product);
        productAdapter = new ProductAdapter(mRootView.getContext(),productArrayList);
        lvProduct.setAdapter(productAdapter);
        spProductType = mRootView.findViewById(R.id.sp_product_type);
        aaProductType = new ArrayAdapter(mRootView.getContext(), android.R.layout.simple_spinner_dropdown_item, productType);
        spProductType.setAdapter(aaProductType);    }


}
