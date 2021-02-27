package com.melecio.cargarimagenvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    public static final String DATA_URL = "https://simplifiedcoding.net/demos/view-flipper/heroes.php";
    public static final String TAG_IMAGE_URL = "imageurl";
    public static final String TAG_NAME = "name";

    private GridView gridView;

    private ArrayList<String> images;
    private ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.gridView);
        images = new ArrayList<>();
        names = new ArrayList<>();
        getData();
    }

    private void getData() {
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor, espere...", "Cargando datos...", false, false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                DATA_URL,
                null,
                response -> {
                    try {
                        JSONArray array = response.getJSONArray("heroes");
                        loading.dismiss();
                        showGrid(array);
                        //response.getJSONObject("heroes");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("jsonArray", "Entro al catch:\n" + e.getMessage());
                    }
                },
                error -> {

                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void showGrid(JSONArray jsonArray){
        Log.d("jsonArray", jsonArray.toString());
        for(int i = 0; i<jsonArray.length(); i++){
            JSONObject obj = null;
            try {
                obj = jsonArray.getJSONObject(i);
                images.add(obj.getString(TAG_IMAGE_URL));
                names.add(obj.getString(TAG_NAME));
            }catch (JSONException e){
                Log.d("jsonArray", "78 - Entro al catch:\n" + e.getMessage());
            }
        }
        try {
            GridViewAdapter gridViewAdapter = new GridViewAdapter(this,images,names);
            gridView.setAdapter(gridViewAdapter);
        }catch (Exception e){
            Log.d("jsonArray", "85 - Entro al catch:\n" + e.getMessage());
        }
    }
}
