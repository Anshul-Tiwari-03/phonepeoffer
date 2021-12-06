package com.example.phonepeoffers.orders_history;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.phonepeoffers.Account_new.storeuserdata;
import com.example.phonepeoffers.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class offers_history extends Fragment {

    RecyclerView orders_list_frame;
    JSONArray userdata=null;
    String user_id="";

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_history, container, false);

        orders_list_frame=root.findViewById(R.id.orders_list_frame);

        get_my_history();
        return root;
    }
    public void get_my_history(){

        try {
            userdata=new JSONArray(storeuserdata.getuserdata(getContext()));
            user_id= (String) userdata.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://anshul.ohari5336.in/your_code/coupons/get_my_order.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("my_orders", response);

                        try {
                            my_orders_response(response);
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
                MyData.put("owner_id",user_id);
                return MyData;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void my_orders_response(String response) throws JSONException {
        JSONArray jsonArray= new JSONArray(response);
        ArrayList<orders_modal> listholder = new ArrayList<orders_modal>();
        for(int i=0;i<jsonArray.length();i++) {
            JSONObject jo = null;
            jo = jsonArray.getJSONObject(i);
//            String[] ary = jo.get("images_strinng").toString().split("##");

            orders_modal obj1 = new orders_modal();

            obj1.setid(jo.get("order_id").toString());
            obj1.set_coupon_id(jo.get("coupon_id").toString());
            obj1.set_coupon_owner_id(jo.get("coupon_owner_id").toString());
            obj1.set_coupon_buyer_id(jo.get("coupon_buyer_id").toString());
            obj1.set_price_without_Tax(jo.get("price").toString());
            obj1.set_Buy_date(jo.get("offer_buyed_date").toString());


            listholder.add(obj1);

        }
        // now pass this data to adapter
        orders_list_frame.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        offers_history_adapter adapter = new offers_history_adapter(getContext(),listholder);
        orders_list_frame.setAdapter(adapter);
    }
}