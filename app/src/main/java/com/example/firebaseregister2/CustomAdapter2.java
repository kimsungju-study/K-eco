package com.example.firebaseregister2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.CustomViewHolder> {
    private ArrayList<Kangwon> kList;
    private Context context;

    public CustomAdapter2(ArrayList<Kangwon> kList, Context context) {
        this.kList = kList;
        this.context = context;
    }
    @NonNull
    @Override
    //리스트 아이템에 대한 뷰 생성
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kangwon_list_item,parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    //각 아이템에 대한 매칭
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(kList.get(position).getProfile())
                .into(holder.iv_profile);

        if (kList != null) {
            holder.tv_data_bak_menu_1.setText(kList.get(position).getData_bak_menu_1());
            holder.tv_data_bak_menu_2.setText(kList.get(position).getData_bak_menu_2());
            holder.tv_data_bak_tickets.setText(String.valueOf(kList.get(position).getData_bak_tickets()));

            holder.tv_data_dup_menu_1.setText(kList.get(position).getData_dup_menu_1());
            holder.tv_data_dup_menu_2.setText(kList.get(position).getData_dup_menu_2());
            holder.tv_data_dup_menu_3.setText(kList.get(position).getData_dup_menu_3());
            holder.tv_data_dup_menu_4.setText(kList.get(position).getData_dup_menu_4());
            holder.tv_data_dup_tickets.setText(String.valueOf(kList.get(position).getData_bup_tickets()));

            holder.tv_data_sp_menu_1.setText(kList.get(position).getData_sp_menu_1());
            holder.tv_data_sp_menu_2.setText(kList.get(position).getData_sp_menu_2());
            holder.tv_foodwaste.setText(String.valueOf(kList.get(position).getFoodwaste()));
            holder.tv_day.setText(kList.get(position).getDay());
        }
    }

    @Override
    public int getItemCount() {
        //3항 연산
        return(kList != null ? kList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_profile;
        TextView tv_data_bak_menu_1;
        TextView tv_data_bak_menu_2;

        TextView tv_data_dup_menu_1;
        TextView tv_data_dup_menu_2;
        TextView tv_data_dup_menu_3;
        TextView tv_data_dup_menu_4;

        TextView tv_data_sp_menu_1;
        TextView tv_data_sp_menu_2;

        TextView tv_data_bak_tickets;
        TextView tv_data_dup_tickets;

        TextView tv_foodwaste;
        TextView tv_day;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_data_bak_menu_1 = itemView.findViewById(R.id.tv_data_bak_menu_1);
            this.tv_data_bak_menu_2 = itemView.findViewById(R.id.tv_data_bak_menu_2);
            this.tv_data_dup_menu_1 = itemView.findViewById(R.id.tv_data_dup_menu_1);
            this.tv_data_dup_menu_2 = itemView.findViewById(R.id.tv_data_dup_menu_2);
            this.tv_data_dup_menu_3 = itemView.findViewById(R.id.tv_data_dup_menu_3);
            this.tv_data_dup_menu_4 = itemView.findViewById(R.id.tv_data_dup_menu_4);
            this.tv_data_sp_menu_1 = itemView.findViewById(R.id.tv_data_sp_menu_1);
            this.tv_data_sp_menu_1 = itemView.findViewById(R.id.tv_data_sp_menu_1);
            this.tv_data_bak_tickets = itemView.findViewById(R.id.tv_data_bak_tickets);
            this.tv_data_dup_tickets = itemView.findViewById(R.id.tv_data_dup_tickets);
            this.tv_day = itemView.findViewById(R.id.tv_day);


        }
    }
}
