package com.example.phonepeoffers.offer_detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.phonepeoffers.R;
import com.example.phonepeoffers.btm_dilg_smple_det;
import com.example.phonepeoffers.offers_new.Offerrequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

public class offer_details extends AppCompatActivity {
    int wallet_amount=0;
    TextView wallet_money,paying_price,message;
    String offer_delievered="";
    String offer_delievered_to="";
    Button buy_now;
    int pay_amount=0;
    String product_id="";
    TextView price_text;
    TextView show_image;
    String user_id="";
    ImageView attached_img;
    JSONArray userdata=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_details);


        try {
            userdata=new JSONArray(storeuserdata.getuserdata(getApplicationContext()));
            user_id= (String) userdata.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent=getIntent();
        product_id=intent.getStringExtra("offer_id");

        wallet_money=findViewById(R.id.wallet_money);
        paying_price=findViewById(R.id.paying_price);
        price_text=findViewById(R.id.price_text);
        show_image=findViewById(R.id.show_attachment);
        attached_img=findViewById(R.id.imageattached);

        buy_now=findViewById(R.id.buy_now);
        ImageView backpress= findViewById(R.id.back_details);
        message=findViewById(R.id.message);

        getDetails();

        wallet_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_bottom_dialog();
            }
        });
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void getDetails(){
        final ProgressDialog progressDialog = new ProgressDialog(offer_details.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://anshul.ohari5336.in/your_code/coupons/offer_detail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("res", response);
                        progressDialog.dismiss();
                        if(!response.equals("null")){
                            try {
                                show_details(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                MyData.put("offer_id",product_id);

                return MyData;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void show_details( String response) throws JSONException {

        CircleIndicator circleIndicator = findViewById(R.id.circleindicator);
        ViewPager imageslider=findViewById(R.id.slider);


        wallet_amount=Integer.valueOf(userdata.get(2).toString());
        String amountext=String.valueOf(wallet_amount)+" ₹ ";
        wallet_money.setText(amountext);

        JSONObject jo = new JSONObject(response);

        Log.d("jsonres",jo.toString());

        /*  Setting up image in view pager  start   */

        String[] ary = jo.get("images_strinng").toString().split("##");
        imageslideradapter myadapter = new imageslideradapter(ary, offer_details.this);
        imageslider.setAdapter(myadapter);
        circleIndicator.setViewPager(imageslider);

        /* Setting up image in view pager end  */

        /*  Setting price for the coupon   */
        price_text.setText("Code");
        pay_amount=Integer.valueOf(jo.get("amount_without_tax").toString());

        /*  IF OFFER IS DELIEVERED OR PRE-ORDERED THEN ANY OTHER CANNOT APPEAL FOR THE OFFER  */
        if(jo.get("offer_status").equals("delievered")&& !jo.get("offer_taken_by").equals(user_id)){
            buy_now.setVisibility(View.GONE);
            message.setText("This Coupon is no longer for sale");
        }


        /*   SHOW THE DETAILS TO THE PERSON WHO  BOUGHT IT   */
        else if(jo.get("offer_taken_by").equals(user_id)) {

            message.setText("You currently own this Coupon ");
            show_image.setVisibility(View.VISIBLE);
            buy_now.setVisibility(View.GONE);

            price_text.setText("Code");
            paying_price.setText(jo.get("code").toString());

            show_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (attached_img.getVisibility() == View.VISIBLE) {
                        attached_img.setVisibility(View.GONE);
                        show_image.setText("View Attachment");
                    } else {
                        attached_img.setVisibility(View.VISIBLE);
                        show_image.setText("Hide Attachment");
                    }
                }
            });
        }
        else{


            paying_price.setText(String.valueOf(pay_amount)+" INR ");


            buy_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        buy_product(jo.get("id").toString(),pay_amount);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void buy_product(String id, int pay_amount){


        if(pay_amount>wallet_amount){
            Toast.makeText(getApplicationContext(),"Top-up Your wallet",Toast.LENGTH_SHORT).show();

        }
        else{
            final ProgressDialog progressDialog = new ProgressDialog(offer_details.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            Handler h= new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    wallet_amount=wallet_amount-pay_amount;
                    String amountext=String.valueOf(wallet_amount)+" ₹ ";
                    wallet_money.setText(amountext);

                    Offerrequest offerrequest=new Offerrequest();
                    // offerrequest
                    offerrequest.buy_coupon(offer_details.this,pay_amount,id,user_id);
                }
            },300);

        }
    }

    public void show_bottom_dialog(){
        btm_dilg_smple_det bottomsheetdialog = new btm_dilg_smple_det(getApplicationContext());
        Bundle bundle = new Bundle();
        bundle.putString("text1", String.valueOf(wallet_amount));
        bottomsheetdialog.setArguments(bundle);
        //  bundle.putSerializable("data", data.get(position).toString() );
        bottomsheetdialog.show(getSupportFragmentManager(),"bottom_dialog");

    }
}