package com.example.myapplicationapivolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btIniciar (View view){
        EditText idEmailAddress = findViewById(R.id.idEmailAddress);
        EditText idPassword = findViewById(R.id.idPassword);
        String EmailAddress;
        String  Password;
        EmailAddress=idEmailAddress.getText().toString();
        Password=idPassword.getText().toString();

        Bundle bundle = this.getIntent().getExtras();
        Map<String, String> infor = new HashMap<String, String>();
        infor.put("correo",EmailAddress);
        infor.put("clave",Password);
        WebService ws= new WebService(" https://api.uealecpeterson.net/public/login"
                ,infor, MainActivity.this, MainActivity.this);
        ws.execute("POST");

    }
    @Override
    public void processFinish(String result) throws JSONException {

        TextView respuesta =findViewById(R.id.idResul);
        JSONObject jsonrespuesta = new JSONObject(result);

        if (jsonrespuesta.has("error"))
        {
            respuesta.setText(jsonrespuesta.getString("error"));
        }
        else
        {

            Bundle b = new Bundle();
            b.putString("TOKEN", jsonrespuesta.getString("access_token"));
            Intent intent = new Intent(MainActivity.this, ListaBancos.class);
            intent.putExtras(b);
            startActivity(intent);

        }


    }
}