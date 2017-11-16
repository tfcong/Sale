package com.ptc.csale.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ptc.csale.R;
import com.ptc.csale.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
public class MainActivity extends Activity implements View.OnClickListener {
    private Button btnSignIn;
    private TextView tvSignUp;
    private EditText edtName, edtPassWord;
    public static String FILE_NAME = "user.txt";
    private CheckBox checkBoxRememberAccount;
    public static String urlUserData = "http://172.16.200.82/csaleserver/index.php";
    public static String urlInsertProduct = "http://172.16.200.82/csaleserver/insert_product.php";
    public static String urlInsertUserData = "http://172.16.200.82/csaleserver/insertdata.php";
    public static String urlReceiveProduct = "http://172.16.200.82/csaleserver/receive_product.php";
    private ArrayList<User> arrUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        addControls();
        ReadJSonUser(urlUserData);
    }
    private void addControls() {
        tvSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        restoringPreferences();
        getDataFromSignUp();
    }
    @Override
    protected void onResume() {
        ReadJSonUser(urlUserData);
        super.onResume();
    }
    private void initViews() {
        btnSignIn = findViewById(R.id.btn_sign_in);
        tvSignUp = findViewById(R.id.tv_sign_up);
        edtName = findViewById(R.id.edt_name);
        edtPassWord = findViewById(R.id.edt_password);
        checkBoxRememberAccount = findViewById(R.id.checkbox_remember_account);
        arrUser = new ArrayList<>();
    }
    private void ReadJSonUser(String url) {
        arrUser.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        User user = new User(object.getString("name"), object.getString("password"));
                        arrUser.add(user);
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
    private void checkUser() {
        boolean check = false;
        String name = edtName.getText().toString().trim();
        String pass = edtPassWord.getText().toString().trim();
        for (int i = 0; i < arrUser.size(); i++) {
            if (arrUser.get(i).getName().equals(name) && arrUser.get(i).getPass().equals(pass)) {
                check = true;
            }
        }
        if (check) {
            savingPreferences();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(getApplicationContext(), "Tài khoản không đúng !", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_up:
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_sign_in:
                checkUser();
                break;
        }
    }
    private void restoringPreferences() {
        SharedPreferences pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        boolean chk = pref.getBoolean("isRemember", false);
        if (chk) {
            String name = pref.getString("name", "");
            String password = pref.getString("password", "");
            edtName.setText(name);
            edtPassWord.setText(password);
        }
        checkBoxRememberAccount.setChecked(chk);
    }
    private void savingPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String name = edtName.getText().toString();
        String password = edtPassWord.getText().toString();
        boolean chk = checkBoxRememberAccount.isChecked();
        if (!chk) editor.clear();
        else {
            editor.putString("name", name);
            editor.putString("password", password);
            editor.putBoolean("isRemember", chk);
        }
        editor.commit();
    }
    private void getDataFromSignUp() {
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra("bundle");
            User user = (User) bundle.getSerializable("user");
            edtName.setText(user.getName());
            edtPassWord.setText(user.getPass());
        } catch (Exception e) {
        }
    }


}