package com.example.phonepeoffers.orders_history;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonepeoffers.Account_new.storeuserdata;
import com.example.phonepeoffers.R;
import com.example.phonepeoffers.offer_detail.offer_details;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class offers_history_adapter extends RecyclerView.Adapter<offers_history_adapter.Holder>  {
    Context context;
    ArrayList<orders_modal> list;
    JSONArray userdata=null;
    String user_id="";
    String url="https://anshul.ohari5336.in/your_code/coupons/";
    public offers_history_adapter(Context context, ArrayList<orders_modal> listholder) {

        this.context =context;
        list=listholder;
    }

    @NonNull
    @Override
    public offers_history_adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        view = inflater.inflate(R.layout.demo_orders_view, parent, false);
        return new offers_history_adapter.Holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull offers_history_adapter.Holder holder, int position) {


        try {
            userdata=new JSONArray(storeuserdata.getuserdata(context));
            user_id= (String) userdata.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(list.get(position).getCoupon_owner_id().equals(user_id)) {
            holder.history_type.setText("OWNED");
            holder.history_type.setTextColor(ContextCompat.getColor(context, R.color.red));

            holder.history_price.setText("Price    ₹ "+list.get(position).getAmount_without_tax());
            long price = Long.parseLong((list.get(position).getAmount_without_tax().toString().trim()));
            long tax= (long) (0.1*price);
            price=price+tax;
            holder.history_tax.setText("Tax    ₹ "+String.valueOf(tax));
            String sourceString = "Total Spent  :  ₹ "+"<b>" + price + "</b> ";
            holder.history_total.setText(Html.fromHtml(sourceString));

        }
        else if(list.get(position).getCoupon_buyer_id().equals(user_id)) {
            holder.history_type.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.history_type.setText("SOLD");


            long price = Long.parseLong((list.get(position).getAmount_without_tax().toString().trim()));
            long tax= (long) (0.1*price);
            price=price+tax;
            holder.history_tax.setText("Tax    ₹ "+String.valueOf(tax));
            holder.history_price.setText("Price    ₹ "+price);
            String sourceString = "Total Earned :  "+"   ₹ "+"<b>" + list.get(position).getAmount_without_tax() + "</b> ";
            holder.history_total.setText(Html.fromHtml(sourceString));

        }

        holder.history_title.setText("Coupon id :"+list.get(position).getCoupon_id());
        holder.history_date.setText(list.get(position).offer_buyed_date);


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, offer_details.class);
                intent.putExtra("offer_id",list.get(position).getCoupon_id());
                context.startActivity(intent);

            }
        });


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView history_type, history_title,history_date,history_price,history_tax,history_total;

        ImageView img;
        CardView card;

        public Holder(@NonNull final View itemView) {
            super(itemView);

            card=itemView.findViewById(R.id.card);
            img=itemView.findViewById(R.id.cover_image);
            history_type=itemView.findViewById(R.id.history_type);
            history_title=itemView.findViewById(R.id.history_title);
            history_date=itemView.findViewById(R.id.history_date);
            history_price=itemView.findViewById(R.id.history_price);
            history_tax=itemView.findViewById(R.id.history_tax);
            history_total=itemView.findViewById(R.id.history_total);

        }
    }
}
