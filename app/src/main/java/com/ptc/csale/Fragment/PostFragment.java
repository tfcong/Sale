package com.ptc.csale.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ptc.csale.R;
import com.ptc.csale.activity.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by phamthanhcong on 10/11/2017.
 */

public class PostFragment extends Fragment {
    private View mRootView;
    private EditText edtName, edtPhoneNumber, edtTitle, edtPrice;
    private ImageView imageThumbnail;
    private Button btnRe, btnPost;
    private Spinner spCategory, spAddress;
    private String[] listCategory = {"Bất động sản", "Dụng cụ học tập", "Dụng cụ nấu ăn"};
    private String[] listAddress = {"Hà Nội", "Hải Phòng", "Hưng Yên", "Đà Nẵng", "Thành phố Hồ Chí Minh"};
    private ArrayAdapter aaCategory, aaAddress;
    private static int SELECT_IMAGE = 0;
    private Bitmap bitmap;
    private static String FILE_NAME = "user.txt";
    String urlInsertProduct = MainActivity.urlInsertProduct;
    String userName ;
    private byte[] bytes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_post, container, false);
        initData();
        initViews();
        initControls();
        restoringPreferences();
        return mRootView;
    }
    private void restoringPreferences() {
        SharedPreferences pref = mRootView.getContext().getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        boolean chk = pref.getBoolean("isRemember", false);
        if (chk) {
            userName= pref.getString("name", "");
            String password = pref.getString("password", "");
            bytes = userName.getBytes();
        }
    }

    private void initControls() {
        imageThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_IMAGE);
            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInfo();
            }
        });
    }

    private void initData() {
        bitmap = BitmapFactory.decodeResource(mRootView.getContext().getResources(), R.drawable.no_image_icon);
        // anh mac dinh lay trong thu muc

    }

    private void initViews() {
        edtName = mRootView.findViewById(R.id.edt_name);
        edtPhoneNumber = mRootView.findViewById(R.id.edt_phone_number);
        edtTitle = mRootView.findViewById(R.id.edt_title);
        edtPrice = mRootView.findViewById(R.id.edt_price);
        imageThumbnail = mRootView.findViewById(R.id.image_view_thumbnail);
        btnRe = mRootView.findViewById(R.id.btn_re);
        btnPost = mRootView.findViewById(R.id.btn_post);
        spCategory = mRootView.findViewById(R.id.sp_category);
        spAddress = mRootView.findViewById(R.id.sp_address);
        aaCategory = new ArrayAdapter(mRootView.getContext(), android.R.layout.simple_spinner_dropdown_item, listCategory);
        aaAddress = new ArrayAdapter(mRootView.getContext(), android.R.layout.simple_spinner_dropdown_item, listAddress);
        spCategory.setAdapter(aaCategory);
        spAddress.setAdapter(aaAddress);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageThumbnail.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void validateInfo() {
        String name = edtName.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String category = spCategory.getSelectedItem().toString().trim();
        String title = edtTitle.getText().toString().trim();
        String address = spAddress.getSelectedItem().toString();
        String price = edtPrice.getText().toString().trim();
        if (name.length() < 6 || name.length() > 18) {
            Toast.makeText(mRootView.getContext(), "Tên phải từ 6-18 kí tự !", Toast.LENGTH_SHORT).show();
        } else if (title.length() < 1) {
            Toast.makeText(mRootView.getContext(), "Mời bạn nhập tiêu đề !", Toast.LENGTH_SHORT).show();
        } else if (price.length() < 1) {
            Toast.makeText(mRootView.getContext(), "Mời bạn nhập giá !", Toast.LENGTH_SHORT).show();
        } else {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formattedDate = df.format(c.getTime());
            long miliTime = c.getTimeInMillis();
            String fileName = miliTime + bytes.toString();
            addProduct(urlInsertProduct, name, phoneNumber, category, title, price, address, imageToString(bitmap), formattedDate, "0", "ewqe",userName, "true", fileName);
            Toast.makeText(mRootView.getContext(), "Đã đăng", Toast.LENGTH_SHORT).show();
        }
    }

    //'$name','$phoneNumber','$category','$title','$price','$address','$thumbnail','$time','$love','$userId','$type')";
    private void addProduct(String url, final String name, final String phoneNumber, final String category, final String title, final String price, final String address, final String thumbnail, final String time, final String love, final String userId,final String userName, final String type, final String fileName) {
        RequestQueue requestQueue = Volley.newRequestQueue(mRootView.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mRootView.getContext(), "Success !", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mRootView.getContext(), "Error :" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("phoneNumber", phoneNumber);
                map.put("category", category);
                map.put("title", title);
                map.put("price", price);
                map.put("address", address);
                map.put("thumbnail", thumbnail);
                map.put("time", time);
                map.put("love", love);
                map.put("userId", userId);
                map.put("userName",userName);
                map.put("type", type);
                map.put("fileName", fileName);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

}
