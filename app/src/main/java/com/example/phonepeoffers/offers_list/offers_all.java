package com.example.phonepeoffers.offers_list;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phonepeoffers.R;
import com.example.phonepeoffers.modal.modal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class offers_all extends Fragment {
    String url="https://anshul.ohari5336.in/your_code/coupons/";
    int i=0;
    boolean list_fetches=false;
    RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.offers_all, container, false);

        recyclerView=root.findViewById(R.id.offers_list);


        get_list();
        return root;

    }
    public void get_list(){
        i=0;
        request_volley(i);
        i=i+100;
        if(i==100){
            Toast.makeText(getContext(),"100 records successfully fetched",Toast.LENGTH_SHORT).show();
        }
//        final int[] delay_time = {10000};
//        final Handler ha=new Handler();
//        ha.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
////                //call function
////
////                request_volley(i);
////                i=i+100;
////                delay_time[0] = delay_time[0] +i*100;
////                if(!list_fetches)
////                ha.postDelayed(this, delay_time[0]);
//            }
//        }, delay_time[0]);
    }
    public void request_volley(int last_index){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://anshul.ohari5336.in/your_code/coupons/get_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("res", response);
                        if(response.equals("fetched"))
                            list_fetches=true;
                        Log.d("list_Data",response);
                        try {
                            set_data_to_list(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("verror1", error.getMessage() + "");
                try {
                    if (error instanceof TimeoutError) {
                    } else if (error instanceof NoConnectionError) {
                    } else if (error instanceof AuthFailureError) {
                    } else if (error instanceof ServerError) {
                    } else if (error instanceof NetworkError) {
                    } else if (error instanceof ParseError) {
                    } else {
                    }
                } catch (Exception e) {
                    Log.d("verror", e.getMessage());
                }
            }
        }) {
            protected Map<String, String> getParams()  {

                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("objective","fetch_list");
                return MyData;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void set_data_to_list(String response) throws JSONException {
        //first time loading is done
        if(i<100){

        }
        else{
            JSONArray jsonArray= new JSONArray(response);
            ArrayList<modal> listholder = new ArrayList<modal>();
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jo = null;
                jo = jsonArray.getJSONObject(i);
                String[] ary = jo.get("images_strinng").toString().split("##");

                modal obj1 = new modal();
                obj1.setImages(ary);
                obj1.setitle(jo.get("title").toString());
                obj1.setid(jo.get("id").toString());
                obj1.set_Expiry_date(jo.get("expiry_date").toString());
                obj1.set_code(jo.get("code").toString());
                obj1.set_code_attachment(jo.get("code_attachment").toString());
                obj1.set_offer_status(jo.get("offer_status").toString());
                obj1.set_offer_taken_by(jo.get("offer_taken_by").toString());
                obj1.set_offer_posted_by(jo.get("offer_posted_by").toString());
                obj1.set_price_without_Tax(jo.get("amount_without_tax").toString());
                Log.d("json_data_list",ary[0].toString());

                listholder.add(obj1);

            }
            // now pass this data to adapter
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            offers_list_adapter adapter = new offers_list_adapter(getContext(),listholder);
            recyclerView.setAdapter(adapter);

        }


    }

}