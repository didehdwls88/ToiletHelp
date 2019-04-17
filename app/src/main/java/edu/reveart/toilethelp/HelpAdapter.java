package edu.reveart.toilethelp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder> {

    ArrayList<Help> helpList;
    CustomItemClickListener listener;

    public HelpAdapter(ArrayList<Help> helpList, CustomItemClickListener listener) {

        this.helpList = helpList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txvMessage, txvReward, txvDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txvMessage = itemView.findViewById(R.id.lvMessage);
            txvReward = itemView.findViewById(R.id.lvReward);
            txvDate = itemView.findViewById(R.id.lvDate);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_listview, parent, false);

        final MyViewHolder holder = new MyViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, holder.getLayoutPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.MyViewHolder holder, int position) {

        Help help = helpList.get(position);

        holder.txvMessage.setText(help.getMessage());
        holder.txvReward.setText(help.getReward());
        holder.txvDate.setText(getDateFormat(helpList.get(position).getDate()));
    }

    public String getDateFormat(long ddt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"+"년 "+"MM"+"월"+"dd"+"일 "+"a "+"KK"+":"+"mm", Locale.KOREA);
        String createDate = sdf.format(ddt);

        return createDate;
    }

    @Override
    public int getItemCount() {
        return helpList.size();
    }
}
