package com.example.phonepeoffers.offers_new;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonepeoffers.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class offer_new extends AppCompatActivity {

    Button add_offer;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    EditText amount_without_tax;
    EditText amount_with_tax;
    TextView submit;
    LinearLayout offer_end_date;
    TextView offer_end_date_text;
    TextView offer_start_date;
    int num_images=0;
    ArrayList<Uri> mArrayUri;

    ArrayList<String> img64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_new);

        // this page is to add pics to our main program
        // set up camera or open image gallery
        // pay to our company
        // we will send the amount to wallet
        // if payment succeeds and the code is been availed

        // add money to your wallet first if you wanna buy

        add_offer=findViewById(R.id.add_offer);
        amount_with_tax=findViewById(R.id.amount_with_tax);
        amount_without_tax=findViewById(R.id.amount_without_tax);
        submit=findViewById(R.id.submit);
        offer_end_date=findViewById(R.id.offer_end_date);
        offer_end_date_text=findViewById(R.id.offer_end_date_text);

        offer_start_date=findViewById(R.id.offer_start_date);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_offer();
            }
        });
        add_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_gallery();
            }
        });
        amount_without_tax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(amount_without_tax.getText().toString().length()<10000) {
                    try {
                        long price = Long.parseLong((amount_without_tax.getText().toString().trim()));
                        double price2 = price - 0.1 * price;
                        if (price2 < 100000) {
                            amount_with_tax.setText(String.valueOf(price2));
                            Log.d("texttttt", String.valueOf(price));
                        }
                    }
                    catch (Exception e){
                        amount_without_tax.getText().clear();
                        Toast.makeText(getApplicationContext(),"Illegal Interrupt",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        show_dates();
    }
    public void open_gallery(){
        num_images=0;

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){
                    mArrayUri = new ArrayList<Uri>();
                    Uri mImageUri=data.getData();
                    mArrayUri.add(mImageUri);
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();
                    // show selected images
                    num_images=1;
                    show_images(mArrayUri);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }
                         Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());


                        // show selected images
                        if(mArrayUri.size()>4){
                            Toast.makeText(this,"You are required to choose max 4 images ",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            num_images=mArrayUri.size();
                            show_images(mArrayUri);

                        }
                    }
                }

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);


    }

    public void show_images(ArrayList<Uri> mArrayUri){
        LinearLayout images_frame=findViewById(R.id.images_section);
        images_frame.removeAllViews();
        Display display = getWindowManager().getDefaultDisplay();
        img64= new ArrayList<String>();

        for(int i=0;i<mArrayUri.size();i++){
            ImageView imageView=new ImageView(this);
            int width =((display.getWidth()*23)/100);
            int height = ((display.getHeight()*15)/100);
            imageView.setPadding(5,5,5,5);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
            imageView.setLayoutParams(parms);
            imageView.setImageURI(mArrayUri.get(i));
            images_frame.addView(imageView);

        }


    }

    public void show_dates(){

        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        offer_start_date.setText(date);
        offer_end_date_text.setText(date);
        offer_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(offer_new.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date =String.valueOf(dayOfMonth)+"-"+ String.valueOf(monthOfYear) +"-"+String.valueOf(year);

                        offer_end_date_text.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
    }
    public void submit_offer(){
        EditText code=findViewById(R.id.code);
        EditText title_text=findViewById(R.id.title_text);
        CheckBox violation_checkbox,details_checkbox;
        violation_checkbox=findViewById(R.id.violation_checkbox);
        details_checkbox=findViewById(R.id.details_chechbox);

        String start_date=offer_start_date.getText().toString();
        String expire_date=offer_end_date_text.getText().toString();
        if(num_images ==0)
            Toast.makeText(getApplicationContext(),"You should select more than one image",Toast.LENGTH_SHORT).show();
        else if(code.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(),"Enter the code for the offer",Toast.LENGTH_SHORT).show();
        else if(title_text.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(),"Enter the title for the offer",Toast.LENGTH_SHORT).show();

        else if(!violation_checkbox.isChecked())
            Toast.makeText(getApplicationContext(),"Accept to the violation policies",Toast.LENGTH_SHORT).show();

        else if(!details_checkbox.isChecked())
            Toast.makeText(getApplicationContext(),"Please check-in the details confirmation box",Toast.LENGTH_SHORT).show();
        else if(amount_without_tax.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(),"Please enter the offer price ",Toast.LENGTH_SHORT).show();

        else{

            // we have images in mArrayUri arraylist
            Toast.makeText(getApplicationContext(),String.valueOf(mArrayUri.size()),Toast.LENGTH_SHORT).show();
            for(int i=0;i<mArrayUri.size();i++) {

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mArrayUri.get(i));
                } catch (IOException e) {
                    e.printStackTrace();

                }
                imageToString(bitmap);

            }

            Offerrequest offerrequest= new Offerrequest();
            offerrequest.send_data(title_text.getText().toString(),img64,start_date,expire_date,amount_without_tax.getText().toString(),code.getText().toString(),"new_offer",offer_new.this,amount_without_tax.getText().toString());
        }
        // offer end date is by default of todays date

    }
    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imBytes = byteArrayOutputStream.toByteArray();
        img64.add(Base64.encodeToString(imBytes, Base64.DEFAULT));
        //Log.d("bs64" , bs64);
        return Base64.encodeToString(imBytes, Base64.DEFAULT);
    }

}