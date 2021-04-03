package com.example.trafficquery.traffic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.example.trafficquery.R;
import com.example.trafficquery.traffic.adapter.TrafficAdapter;
import com.example.trafficquery.traffic.pojo.Traffic;
import com.example.trafficquery.utils.KenUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrafficListActivity extends AppCompatActivity {


    private List<Traffic> trafficList = new ArrayList<>();
    private ListView listView_traffic;
    private TrafficAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_list);
        listView_traffic = findViewById(R.id.listView_traffic);
        Intent intent = getIntent();
        if (intent.getStringExtra("engine") != null){
            String engine = intent.getStringExtra("engine");
            String number = intent.getStringExtra("number");
            getTrafficList(engine,number);
        }


    }

    public void getTrafficList(String engine,String number){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://124.93.196.45:10002/userinfo/illegal/list?pageNum=1&pageSize=10&catType=小型汽车 &engineNumber="+engine+"&licencePlate="+number;
                    String json = KenUtils.Get(url);
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for (int i = 0; i < jsonArray.length() ; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int id = object.getInt("id");
                        String licencePlate = object.getString("licencePlate");
                        String disposeState = object.getString("disposeState");
                        String badTime = object.getString("badTime");
                        String money = object.getString("money");
                        String deductMarks = object.getString("deductMarks");
                        String illegalSites = object.getString("illegalSites");
                        String noticeNo = object.getString("noticeNo");
                        String engineNumber = object.getString("engineNumber");
                        String catType = object.getString("catType");
                        String trafficOffence = object.getString("trafficOffence");
                        trafficList.add(new Traffic(id,licencePlate,disposeState,badTime,money,deductMarks,illegalSites,noticeNo,engineNumber,catType,trafficOffence));
                    }

                    handler.sendEmptyMessage(1);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 点击查询
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    adapter = new TrafficAdapter(TrafficListActivity.this,R.layout.traffic_item,trafficList);
                    listView_traffic.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };

}