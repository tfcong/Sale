package com.ptc.csale.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ptc.csale.R;
import com.ptc.csale.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends Activity implements View.OnClickListener {
    private ImageView imageViewBack;
    private EditText edtName, edtPassword, edtEmail, edtBirthDate, edtPhoneNumber, edtAddress;
    private Button btnRe, btnSignUp, btnBirthDate;
    private Spinner spAddress;
    private String[] listAddress = {"Hà Nội", "TP.Hồ Chí Minh", "Đà Nẵng", "Nghệ An", "Hải Phòng", "Hưng Yên"};
    private ArrayAdapter aaAddress;
    private RadioButton rdMale, rdFeMale;
    private static String FILE_NAME = "user.txt";
    private boolean isChooseDate = false;
    private ArrayList<String> arrEmail,arrName;
    String urlUserData=MainActivity.urlUserData,urlInsertUserData=MainActivity.urlInsertUserData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
        addControls();
        ReadJSonEmail(urlUserData);
    }

    @Override
    protected void onResume() {
        ReadJSonEmail(urlUserData);
        super.onResume();
    }

    private void addControls() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        spAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                edtAddress.setText(listAddress[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                re();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInfo();
            }
        });
        edtBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private boolean checkEmailUsed() {
        String email = edtEmail.getText().toString().trim();
        for (int i = 0; i < arrEmail.size(); i++) {
            if (arrEmail.get(i).equals(email))
                return true;
        }
        return false;
    }
    private boolean checkNameUsed() {
        String name = edtName.getText().toString().trim();
        for (int i = 0; i < arrName.size(); i++) {
            if (arrName.get(i).equals(name))
                return true;
        }
        return false;
    }

    private void initViews() {
        imageViewBack = findViewById(R.id.image_view_back);
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtBirthDate = findViewById(R.id.edt_birth_date);
        edtBirthDate.setEnabled(false);
        btnBirthDate = findViewById(R.id.btn_birth_date);
        edtPhoneNumber = findViewById(R.id.edt_phone_number);
        edtAddress = findViewById(R.id.edt_address);
        rdFeMale = findViewById(R.id.rd_female);
        rdMale = findViewById(R.id.rd_male);
        btnRe = findViewById(R.id.btn_re);
        btnSignUp = findViewById(R.id.btn_sign_up);
        spAddress = findViewById(R.id.sp_address);
        arrEmail = new ArrayList<>();
        arrName=new ArrayList<>();
        getDataToSpinner();
    }

    private void getDataToSpinner() {
        aaAddress = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_single_choice, listAddress);
        spAddress.setAdapter(aaAddress);
    }

    private void re() {
        edtName.setText("");
        edtBirthDate.setText("");
        edtPassword.setText("");
        edtEmail.setText("");
        edtAddress.setText("");
        rdMale.setChecked(true);
    }

    private void validateInfo() {
        String name = edtName.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String birthDate = edtBirthDate.getText().toString().trim();
        String gender = "";
        if (rdMale.isChecked())
            gender = "Male";
        else gender = "FeMale";
        if (name.length() < 6 || name.length() > 18) {
            Toast.makeText(getApplicationContext(), "Tên phải từ 6-18 kí tự !", Toast.LENGTH_SHORT).show();
        } else if (pass.length() < 6 || pass.length() > 18) {
            Toast.makeText(getApplicationContext(), "Mật khẩu phải từ 6-18 kí tự !", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmailAddress(email)) {
            Toast.makeText(getApplicationContext(), "Email không đúng !", Toast.LENGTH_SHORT).show();
        } else if (checkEmailUsed()) {
            Toast.makeText(getApplicationContext(), "Email đã được sử dụng !", Toast.LENGTH_SHORT).show();
        }
        else if (checkNameUsed()) {
            Toast.makeText(getApplicationContext(), "Tên đã được sử dụng !", Toast.LENGTH_SHORT).show();
        } else if (phoneNumber.length() < 5) {
            Toast.makeText(getApplicationContext(), "Số điện thoại không đúng !", Toast.LENGTH_SHORT).show();
        } else if (!isChooseDate) {
            Toast.makeText(getApplicationContext(), "Mời bạn nhập ngày sinh !", Toast.LENGTH_SHORT).show();
        } else {
            getDataUser(name, pass, email, address, phoneNumber, birthDate, gender);
            addUser(urlInsertUserData, name, pass, email, address, phoneNumber, birthDate, gender);
            Toast.makeText(getApplicationContext(), "Đăng kí thành công !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataUser(final String name, final String pass, final String email, final String address, final String phoneNumber, final String birthDate, final String gender) {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        User user = new User(name, pass, email, address, phoneNumber, birthDate, gender);
        bundle.putSerializable("user", user);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    private void ReadJSonEmail(String url) {
        arrEmail.clear();
        arrName.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String email = object.getString("email");
                        arrEmail.add(email);
                        String name = object.getString("name");
                        arrName.add(name);
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

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtBirthDate.setText(simpleDateFormat.format(calendar.getTime()));
                isChooseDate = true;
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_back:
        }
    }
    private void addUser(String url, final String name, final String pass, final String email, final String address, final String phoneNumber, final String birthDate, final String gender) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response + "", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage() + "", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("password", pass);
                map.put("email", email);
                map.put("address", address);
                map.put("phoneNumber", phoneNumber);
                map.put("birthDate", birthDate);
                map.put("gender", gender);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
