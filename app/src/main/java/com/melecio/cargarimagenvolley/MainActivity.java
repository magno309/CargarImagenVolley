package com.melecio.cargarimagenvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        getData();
    }

    private void getData() {
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor, espere...", "Cargando datos...", false, false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL,
                response -> {
                    loading.dismiss();
                    showGrid(response);
                },
                error -> {}
                );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
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
                e.printStackTrace();
            }
        }
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this,images,names);
        gridView.setAdapter(gridViewAdapter);
    }

}
