package com.example.phonepeoffers.Account_new;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class validation_volley {

    callback_to_mobile_otp callback_to_mobile_otp;

    public void verify_volley( Context context,String text,String password,String isemail){

        String email_or_phone=text;
        Log.d("verif",email_or_phone);
        callback_to_mobile_otp=(callback_to_mobile_otp)context;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://anshul.ohari5336.in/your_code/coupons/verify_acc.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("verify", response);

                        if(response.equals("not found")){
                            Toast.makeText(context,"No User Found",Toast.LENGTH_SHORT).show();
                        }
                        else if(isemail.equals("yes")){
                            Toast.makeText(context,"Otp has been sent to your gmail",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // call interface to call back a function to send otp now
                            JSONObject jo=null;
                            JSONArray userdata = null;
                            try {
                                jo=new JSONObject(response);
                                userdata = new JSONArray();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                userdata.put(jo.get("id_account").toString());
                                userdata.put(jo.get("phone_no").toString());
                                userdata.put(jo.get("wallet_amount").toString());
                                userdata.put(jo.get("withdrawn_amount").toString());
                                userdata.put(jo.get("recharge_amount").toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            storeuserdata.saveuserdata(context,userdata);
                            Log.d("userdata", storeuserdata.getuserdata(context).toString());

                            Account_new_mobile_otp();
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {
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
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("first",text);
                MyData.put("password",password);
                MyData.put("isemail",isemail);
                Log.d("MyData",MyData.toString());
                return MyData;
            }
        };
        requestQueue.add(stringRequest);

    }
    public void Account_new_mobile_otp(){
        callback_to_mobile_otp.send_mobile_otp();
    }
}
