package com.example.appdbothitit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch, btnChangeActivity;
    TextView txtName, txtCountry, txtTemp, txtStatus, txtHumidity, txtCloud, txtDay, txtWind;
    ImageView imgIcon;
    String City ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        GetCurrentWeatherData("HaNoi");
        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String city= edtSearch.getText().toString();
                if(city.equals("")){
                    City="HaNoi";
                    GetCurrentWeatherData(City);
                }
                else{
                    City=city;
                    GetCurrentWeatherData(City);
                }
            }
        } );
        btnChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city= edtSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this , MainActivity2.class);
                Intent intent1 = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("name",city);
                startActivities(new Intent[]{intent});

            }
        });
    }
    /*private void startActivities(Intent intent) {
    }*/
    public void GetCurrentWeatherData(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url ="https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=d7bb54fc0a9007baa304ccb145eeb4ac";
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtName.setText("Tên thành phố: "+name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyy-MM-dd HH-mm-ss");
                            String Day= simpleDateFormat.format(date);
                            txtDay.setText(Day);

                            JSONArray jsonArrayWeather= jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather= jsonArrayWeather.getJSONObject(0);
                            String status=jsonObjectWeather.getString("main");
                            String icon= jsonObjectWeather.getString("icon");

                            Picasso.get().load("https://openweathermap.org/img/wn/"+icon+".png").into(imgIcon);
                            txtStatus.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo= jsonObjectMain.getString("temp");
                            String doam= jsonObjectMain.getString("humidity");

                            Double a= Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf( a.intValue());
                             txtTemp.setText(Nhietdo+"C");
                             txtHumidity.setText(doam+"%");

                             JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                             String gio = jsonObjectWind.getString("speed");
                             txtWind.setText(gio+"m/s");

                             if (jsonObject.has("cloud")) {
                                JSONObject jsonObjectCloud = jsonObject.getJSONObject("cloud");
                                String may = jsonObjectCloud.getString("all");
                                txtCloud.setText(may + "%");
                            } else {
                                // Handle the case where "cloud" field is not present in the JSON response
                                txtCloud.setText("12%");
                            }
                             /* JSONObject jsonObjectCloud =jsonObject.getJSONObject("cloud");
                             String may = jsonObjectCloud.getString("all");
                             txtCloud.setText(may+ "%");*/

                             JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                             String country = jsonObjectSys.getString("country");
                             txtCountry.setText("Tên quốc gia: "+ country);
                        }
                        catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(stringRequest);

    }

    private void Anhxa() {
        edtSearch = ( EditText) findViewById(R.id.edittextSearch);
        btnSearch= (Button) findViewById(R.id.buttonSearch);
        btnChangeActivity=(Button) findViewById(R.id.buttonChangeActivity);
        txtName=(TextView) findViewById(R.id.textviewName);
        txtCountry=(TextView) findViewById(R.id.textviewCountry);
        txtTemp=(TextView) findViewById(R.id.textviewTemp);
        txtStatus=(TextView) findViewById(R.id.textViewStatus);
        txtHumidity=(TextView) findViewById(R.id.textviewHumidity);
        txtCloud=(TextView) findViewById(R.id.textviewCloud);
        txtWind=(TextView) findViewById(R.id.textviewGio);
        txtDay=(TextView) findViewById(R.id.textviewDay);
        imgIcon =(ImageView) findViewById(R.id.imageIcon);



    }
}