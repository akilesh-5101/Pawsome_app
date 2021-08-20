package com.abolt.pawsome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button api;
    private ImageView image;
    private TextView d_name;
    private float x_cor;
    private int width;
    private int height;
    private int id;
    private float x;
    private static final String TAG = "please";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        width = display.widthPixels;
        height = display.heightPixels;
        id = 1;

        image = findViewById(R.id.image_dog);
        d_name = findViewById(R.id.name);
        get_dog();
    }
    private void loadImageFromUrl(String url) {
        Glide.with(MainActivity.this)
                .load(url)
                .into(image)
                ;

//
    }
    private void get_dog() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.thedogapi.com/v1/images/search?size=full&order=DESC&limit=1&breed_id=" + id ;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                String dog_image = new String();
                String name = new String();

                try {
                    JSONObject dog_info = response.getJSONObject(0);
                    dog_image = dog_info.getString("url");

                    name = dog_info.getJSONArray("breeds").getJSONObject(0).getString("name");
                    Log.d(TAG, "" + name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loadImageFromUrl(dog_image);
                d_name.setText(name);
                d_name.setVisibility(View.VISIBLE);

                //   Toast.makeText(MainActivity.this, "Name is: " + dog_name, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error bro", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-api-key", "4299ee69-d38a-4f6e-91cb-51671964e9fa");
                return headers;
            }
        };

        queue.add(request);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        float y = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                x = event.getX();
                y = event.getY();
                x_cor = x;
                Log.d(TAG,""+ x_cor);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                x = event.getX();
                y = event.getY();

                return value;
            }
        }
        Log.d(TAG,""+ x_cor);
        Log.d(TAG,""+ x);
        if(x_cor >= x + width/4 ){
            id++;
            get_dog();
        }
        if(x >= x_cor + width/4 && id > 1){
            id--;
            get_dog();
        }
        return value;
    }

}
