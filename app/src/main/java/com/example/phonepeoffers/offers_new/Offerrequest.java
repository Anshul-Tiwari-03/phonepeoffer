package com.example.phonepeoffers.offers_new;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.phonepeoffers.Account_new.storeuserdata;
import com.example.phonepeoffers.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Offerrequest {
    JSONArray userdata=null;String id="",bank_amt="";
    public void send_data(String title_text, ArrayList<String> mArrayUri, String start_date, String expire_date, String amount, String code, String offer, Context applicationContext, String s)
    {
        final ProgressDialog progressDialog = new ProgressDialog(applicationContext);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        try {
            userdata=new JSONArray(storeuserdata.getuserdata(applicationContext));
            id= (String) userdata.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        JSONArray finalUserdata = userdata;
        String finalId = id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://anshul.ohari5336.in/your_code/coupons/add_offer.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("res", response);
                        progressDialog.dismiss();
                        Toast.makeText(applicationContext,"Profile Pic Uploaded",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(applicationContext, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("flag", "modify");
                        applicationContext.startActivity(i);
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
                MyData.put("title",title_text);
                MyData.put("marrayuri", String.valueOf(mArrayUri.size()));
                for(int i =0;i<mArrayUri.size();i++){
                    MyData.put("image"+i,mArrayUri.get(i));
                }
                MyData.put("start_date",start_date);
                MyData.put("end_date",expire_date);
                MyData.put("offer_status",offer);
                MyData.put("amount",amount);
                MyData.put("code",code);
                MyData.put("offer_type",offer);
                MyData.put("offer_posted_by", finalId);
                MyData.put("price_without_tax",amount);



                return MyData;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void buy_coupon(Context context,int pay_amount,String coupon_id,String user_id) {

        try {
            userdata=new JSONArray(storeuserdata.getuserdata(context));
            id= (String) userdata.get(0);
            bank_amt=userdata.get(2).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ProgressDialog progressDialog2 = new ProgressDialog(context);
        progressDialog2.setMessage("Loading...");
        progressDialog2.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://anshul.ohari5336.in/your_code/coupons/transaction.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("res", response);

                        progressDialog2.dismiss();

                        if(response.equals("succcess")){
                            Toast.makeText(context,"Succesfully Bought code",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context,"Transaction Failed Amount will be refunded soon",Toast.LENGTH_SHORT).show();

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
                MyData.put("pay_amt",String.valueOf(pay_amount));
                MyData.put("person_id",id);
                MyData.put("coupon_id",coupon_id);
                MyData.put("bank_amount",bank_amt);

                return MyData;
            }
        };
        requestQueue.add(stringRequest);
    }
}
