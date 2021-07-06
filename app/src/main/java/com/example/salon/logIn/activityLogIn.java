package com.example.salon.logIn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.salon.MainActivity;
import com.example.salon.R;
import com.example.salon.activityHome;
import com.example.salon.database.clsWebRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class activityLogIn extends MainActivity {

    protected TextInputEditText TILemail,TILpassword;
    protected Button bLogin, bReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createViews();
        init();
        createButtons();
    }

    public void iniciarSesion(View view){
        String email = TILemail.getText().toString();
        String pass = TILpassword.getText().toString();
        if(!email.equals("") || !pass.equals("")){
            if (isUserNameValid(email)) {
                /*RequestParams params = new RequestParams();
                params.add("type", "login");
                params.add("email", email);
                params.add("password", pass);
                Log.d("PABLO::0 ", email + " " + pass);

                clsWebRequest.post(context, "apiSalon", params, new activityLogIn.ResponseHandler()); //cambiar el context*/
                sharedPrefEditor.putBoolean("logeado",true);
                sharedPrefEditor.putString("id","1");
                sharedPrefEditor.putString("email","pizarro993@gmail.com");
                sharedPrefEditor.putString("nombre","pablo");
                sharedPrefEditor.putString("apellido","pizarro");
                sharedPrefEditor.putString("tipoUsuarioId","1");
                sharedPrefEditor.apply();
                sharedPrefEditor.commit();

                Intent i = new Intent(context, activityHome.class);
                Random ran = new Random();
                Toast.makeText(context,"Hola",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context,"Mail no valido",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context,"Campo mail vacío",Toast.LENGTH_SHORT).show();
        }
    }


    public void createButtons(){
        /*this.bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = TILemail.getText().toString();
                String pass = TILpassword.getText().toString();
                if (isUserNameValid(email)){
                    RequestParams params = new RequestParams();
                    params.add("type", "login");
                    params.add("email", email);
                    params.add("password", pass);

                    Log.d("PABLO::0 ", email + " " + pass);

                    clsWebRequest.post(context, "apiSalon", params, new activityLogIn.ResponseHandler()); //cambiar el context
                }
            }
        });*/
        this.bReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ActivityRegister.class);
                startActivity(intent);
            }
        });
    }

    public void createViews(){
        TILemail = (TextInputEditText) findViewById(R.id.TILEmail);
        TILpassword = (TextInputEditText) findViewById(R.id.TILPass);
        bLogin = (Button) findViewById(R.id.buttonLogIn);
        bReg = (Button) findViewById(R.id.buttonRegister);

    }


    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    protected void init() {
        super.init();
        context = this;
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private class ResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);

            try {
                if (response.getBoolean("error")) {
                    // error de login
                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    // login exito
                    JSONObject user = response.getJSONObject("user");
                   /* sharedPrefEditor.putBoolean("logeado",true);
                    sharedPrefEditor.putString("id",user.getString("idUsuario"));
                    sharedPrefEditor.putString("email",user.getString("email"));
                    sharedPrefEditor.putString("nombre",user.getString("nombre"));
                    sharedPrefEditor.putString("apellido",user.getString("apellido"));
                    sharedPrefEditor.putString("tipoUsuarioId",user.getString("tipoUsuarioId"));*/
                    sharedPrefEditor.putBoolean("logeado",true);
                    sharedPrefEditor.putString("id","1");
                    sharedPrefEditor.putString("email","pizarro993@gmail.com");
                    sharedPrefEditor.putString("nombre","pablo");
                    sharedPrefEditor.putString("apellido","pizarro");
                    sharedPrefEditor.putString("tipoUsuarioId","1");
                    sharedPrefEditor.apply();
                    sharedPrefEditor.commit();

                    Intent i = new Intent(context, activityHome.class);
                    Random ran = new Random();


                    startActivity(i);
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            Log.d("MENSAJEMIO::1", "Error  Ago anda mal che " + statusCode);
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        @Override
        public void onFinish() {
            super.onFinish();
        }
    }



}