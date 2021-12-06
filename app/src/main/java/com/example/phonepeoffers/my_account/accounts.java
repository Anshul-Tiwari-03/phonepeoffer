package com.example.phonepeoffers.my_account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.phonepeoffers.Account_new.storeuserdata;
import com.example.phonepeoffers.R;
import com.example.phonepeoffers.btm_dilg_smple_det;

import org.json.JSONArray;
import org.json.JSONException;

public class accounts extends AppCompatActivity {

    TextView wallet_amount,withdrawn_amount,recharge_Amount;
    JSONArray userdata=null;
    Button withdraw_now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        TextView go_back_accounts=findViewById(R.id.go_back_myacc);
        wallet_amount=findViewById(R.id.account_balance);
        recharge_Amount=findViewById(R.id.recharge_amount);
        withdrawn_amount=findViewById(R.id.withdrawn_Amount);
        withdraw_now=findViewById(R.id.withdraw_now);

        go_back_accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            Log.d("userdata",storeuserdata.getuserdata(getApplicationContext()));
              userdata=new JSONArray(storeuserdata.getuserdata(getApplicationContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            set_up_details();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        withdraw_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_bottom_dialog();
            }
        });
    }
    public void set_up_details() throws JSONException {
        wallet_amount.setText(" ₹ "+userdata.get(2).toString());
        withdrawn_amount.setText(userdata.get(3).toString()+" INR");
        recharge_Amount.setText(userdata.get(4).toString()+" INR");
    }
    public void show_bottom_dialog(){
        btm_dilg_smple_det bottomsheetdialog = new btm_dilg_smple_det(getApplicationContext());
        Bundle bundle = new Bundle();
        bundle.putString("text1", String.valueOf(wallet_amount.getText().toString()));
        bottomsheetdialog.setArguments(bundle);
        //  bundle.putSerializable("data", data.get(position).toString() );
        bottomsheetdialog.show(getSupportFragmentManager(),"bottom_dialog");
    }
}