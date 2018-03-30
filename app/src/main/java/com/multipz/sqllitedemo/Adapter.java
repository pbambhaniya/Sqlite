package com.multipz.sqllitedemo;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Admin on 24-03-2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private List<Model> expertsList;
    private ItemClickListener clickListener;
    private Context context;
    public DatabaseHelper db;
    String delete_id;
    int id;

    public interface ClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_event_name, txt_event_time, txt_time;
        ImageView img_del;
        CheckBox cb_time;

        public MyViewHolder(View view) {
            super(view);
            txt_event_name = (TextView) view.findViewById(R.id.txt_event_name);
            txt_time = (TextView) view.findViewById(R.id.txt_time);
            txt_event_time = (TextView) view.findViewById(R.id.txt_event_time);
            img_del = (ImageView) view.findViewById(R.id.img_del);
            cb_time = (CheckBox) view.findViewById(R.id.cb_timer);
            img_del.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.itemClicked(view, getAdapterPosition());
            }
        }

    }

    public Adapter(List<Model> expertsList, Context context) {
        this.context = context;
        this.expertsList = expertsList;
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_data, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Model model = expertsList.get(position);
        holder.txt_event_name.setText(model.getEvent_name());

        holder.txt_event_time.setText(model.getEvent_time());



        double time = Double.parseDouble(model.getEvent_time().toString());


        new CountDownTimer((long) (time * 60000), 1000) {

            public void onTick(long millisUntilFinished) {


                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
//                holder.txt_time.setText(""+(millisUntilFinished / 1000));
                holder.txt_time.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec) + "");

            }

            public void onFinish() {
                holder.txt_time.setText("Done");
            }
        }.start();

        holder.cb_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    Model modell=new Model();
                    Toast.makeText(context, ""+model.getEvent_name()+model.getEvent_time(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return expertsList.size();
    }
}
