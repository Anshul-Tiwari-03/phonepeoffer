package com.example.phonepeoffers.my_account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.phonepeoffers.Account_new.storeuserdata;
import com.example.phonepeoffers.R;

import org.json.JSONArray;
import org.json.JSONException;

public class my_profile extends Fragment {

    TextView account_balance;
    FrameLayout accounts_bolck;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.my_profile, container, false);
        account_balance=root.findViewById(R.id.account_balance);
        accounts_bolck=root.findViewById(R.id.accounts_bolck);

        try {
            fill_up_details();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }
    public void fill_up_details() throws JSONException {
        String userdata= storeuserdata.getuserdata(getContext());
        JSONArray dataresponse=new JSONArray(userdata);
        account_balance.setText(" ₹ "+dataresponse.get(2).toString());

        accounts_bolck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), accounts.class);
                startActivity(i);
            }
        });

    }
}
