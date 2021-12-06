package com.example.phonepeoffers.offers_list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phonepeoffers.R;
import com.example.phonepeoffers.modal.modal;
import com.example.phonepeoffers.offer_detail.offer_details;

import java.util.ArrayList;

public class offers_list_adapter extends RecyclerView.Adapter<offers_list_adapter.Holder> {

    Context context;
    ArrayList<modal> list;
    String url="https://anshul.ohari5336.in/your_code/coupons/";
    public offers_list_adapter(Context context, ArrayList<modal> listholder) {

        this.context =context;
        list=listholder;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        view = inflater.inflate(R.layout.demo_view, parent, false);
        return new Holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.expire_date.setText("Expires "+list.get(position).getExpiry_Date());
        Glide.with(context).load(url+list.get(position).getImagesAry()[0]).into(holder.img);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, offer_details.class);
                intent.putExtra("offer_id",list.get(position).getId());
                context.startActivity(intent);

            }
        });


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView expire_date, samples,status,date;
        LinearLayout list;
        ImageView img;
        CardView card;

        public Holder(@NonNull final View itemView) {
            super(itemView);

            card=itemView.findViewById(R.id.card);
            expire_date=itemView.findViewById(R.id.expire_date);
            img=itemView.findViewById(R.id.cover_image);

        }
    }
}
