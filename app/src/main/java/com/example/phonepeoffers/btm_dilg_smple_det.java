package com.example.phonepeoffers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class btm_dilg_smple_det extends BottomSheetDialogFragment {

    Context context;
    public btm_dilg_smple_det(Context context) {
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View bottomsheetview= inflater.inflate(R.layout.bottom_sample,container);

        setStyle(STYLE_NORMAL, R.style.Bottomsheetdialogtheme);

        TextView account_balance=bottomsheetview.findViewById(R.id.account_bal);
        Bundle extras= this.getArguments();
        account_balance.setText(extras.getString("text1"));


        return  bottomsheetview;
    }

    @Override
    public int getTheme() {
        return R.style.Bottomsheetdialogtheme;
    }


}
