package com.example.salon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salon.database.clsWebRequest;
import com.example.salon.logIn.activityLogIn;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import cz.msebera.android.httpclient.Header;

public class ActivityAver extends AppCompatActivity {
    Context context;
    Button button;
    String m_Text;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aver);
        this.context = this;
        this.button = (Button) findViewById(R.id.botonaver);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Datos");
// Set up the input
                final EditText input,inputA,inputdni;
                input = new EditText(context);
                inputA = new EditText(context);
                inputdni = new EditText(context);
                layout.addView(input);
                layout.addView(inputA);
                layout.addView(inputdni);
                input.setHint("Nombre");
                inputA.setHint("Apellido");
                inputdni.setHint("DNI");
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(layout);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString() +" " + inputA.getText().toString();
                        Toast.makeText(context,m_Text,Toast.LENGTH_LONG).show();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

    }

}





