package com.example.phonepeoffers.Account_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonepeoffers.MainActivity;
import com.example.phonepeoffers.R;

import java.util.regex.Pattern;

public class Account_new extends AppCompatActivity implements callback_to_mobile_otp{
    TextView get_otp,skip_login,otp_sent_head,continue_mobile_head;
    LinearLayout enter_code_box,get_back_dox;
    EditText email_or_phone;
    LinearLayout get_otp_box,verify_otp_box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_new);


        get_otp=findViewById(R.id.get_otp);
        enter_code_box=findViewById(R.id.enter_code_box);
        get_back_dox=findViewById(R.id.get_back_box);
        email_or_phone=findViewById(R.id.enter_mobile);
        skip_login=findViewById(R.id.skip_login);
        get_otp_box=findViewById(R.id.send_otp_box);
        verify_otp_box=findViewById(R.id.otp_verif_box);
        otp_sent_head=findViewById(R.id.otp_sent_head);
        continue_mobile_head=findViewById(R.id.continue_mobile_head);

        skip_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                validation_volley validation_volley=new validation_volley();
                String email_or_phone_text=email_or_phone.getText().toString().trim();
                Toast.makeText(getApplicationContext(),email_or_phone_text,Toast.LENGTH_LONG).show();

                if(email_or_phone_text.isEmpty()||email_or_phone_text==null){
                    Toast.makeText(getApplicationContext(),"Please fill up all the details ",Toast.LENGTH_LONG).show();
                }
                else{
                    enter_code_box.setVisibility(View.VISIBLE);
                    get_back_dox.setVisibility(View.GONE);
//                  get_otp.setVisibility(View.GONE);
                    email_or_phone.setEnabled(true);

//                    if(isValidMail(email_or_phone_text)){
//                        validation_volley.verify_volley(Account_new.this,email_or_phone_text,pasword_text,"yes");
//                    }
                    if(isValidMobile(email_or_phone_text)){
                        validation_volley.verify_volley(Account_new.this,email_or_phone_text,email_or_phone_text,"no");
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Enter a valid email or phone",Toast.LENGTH_LONG).show();
                        enter_code_box.setVisibility(View.GONE);

                        get_otp_box.setVisibility(View.VISIBLE);
                        email_or_phone.setEnabled(false);
                    }
                }
            }
        });



    }
    public void reverseTimer(int Seconds){

        new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

            }

            public void onFinish() {


            }
        }.start();
    }
    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 13;
        }
        return false;
    }

    // interface method
    @Override
    public void send_mobile_otp() {
        Toast.makeText(getApplicationContext(),"I am being called by interface",Toast.LENGTH_LONG).show();
        verify_otp_box.setVisibility(View.VISIBLE);
        get_otp_box.setVisibility(View.GONE);
        continue_mobile_head.setVisibility(View.GONE);
        otp_sent_head.setVisibility(View.VISIBLE);
        enter_code_box.setVisibility(View.VISIBLE);

        verify_otp();
        reverseTimer(120);
        // call to get otp
    }


    public void verify_otp(){
        EditText otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four;
        TextView verify_otp;


        otp_textbox_one = findViewById(R.id.otp_edit_box1);
        otp_textbox_two = findViewById(R.id.otp_edit_box2);
        otp_textbox_three = findViewById(R.id.otp_edit_box3);
        otp_textbox_four = findViewById(R.id.otp_edit_box4);
        verify_otp = findViewById(R.id.verify_otp);



        otp_textbox_one.addTextChangedListener(new GenericTextWatcher(otp_textbox_two, otp_textbox_one));
        otp_textbox_two.addTextChangedListener(new GenericTextWatcher(otp_textbox_three, otp_textbox_one));
        otp_textbox_three.addTextChangedListener(new GenericTextWatcher(otp_textbox_four, otp_textbox_two));
        otp_textbox_four.addTextChangedListener(new GenericTextWatcher(otp_textbox_four, otp_textbox_three));


        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(Account_new.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });

        otp_sent_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify_otp_box.setVisibility(View.GONE);
                get_otp_box.setVisibility(View.VISIBLE);
                continue_mobile_head.setVisibility(View.VISIBLE);
                otp_sent_head.setVisibility(View.GONE);
                enter_code_box.setVisibility(View.GONE);
                get_back_dox.setVisibility(View.VISIBLE);

            }
        });
    }
}