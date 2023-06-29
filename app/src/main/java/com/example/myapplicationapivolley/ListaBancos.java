package com.example.myapplicationapivolley;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaBancos extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener {


    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_bancos);

        Bundle bundle = getIntent().getExtras();
        Map<String, String> infor = new HashMap<>();
        infor.put("fuente", "1");

        String url = "https://api.uealecpeterson.net/public/productos/search";
        JsonObjectRequest res = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(infor),
                this, this) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> inicio = new HashMap<>();
                inicio.put("Authorization", "Bearer " + bundle.getString("TOKEN"));
                return inicio;
            }
    };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(res);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        TextView Inf = (TextView)findViewById(R.id.Respuesta);
        Inf.setText("error");

    }

    @Override
    public void onResponse(JSONObject response) {

        ArrayList<String> lBanco = new ArrayList<String>();
        JSONArray productosArray = null;
        try {
            productosArray = response.getJSONArray("productos");

            for (int i = 0; i < productosArray.length(); i++) {
                JSONObject producto = productosArray.getJSONObject(i);
                lBanco.add("("+i+")"+producto.getString("barcode").toString()+" "+" "+
                        " "+producto.getString("descripcion").toString()
                        +producto.getString("costo").toString()+" "+" "+
                        " "+producto.getString("impuesto").toString()+"\n");
            }

            TextView txtvolley = findViewById(R.id.Respuesta);
            txtvolley.setText(lBanco.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}