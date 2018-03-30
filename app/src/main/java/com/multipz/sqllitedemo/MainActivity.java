package com.multipz.sqllitedemo;

import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements ItemClickListener {
    EditText et_event;
    Button btn_submit, btn_delete;
    DatabaseHelper db;
    Context context;
    RecyclerView recyclerview;
    Adapter adapter;
    ArrayList<Model> list;
    String event_name;
    String event_title;
    TextView txt_start_date;
    private int hour, minutes;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = this;
        db = new DatabaseHelper(context);


//        et_name = (EditText) findViewById(R.id.et_name);
        et_event = (EditText) findViewById(R.id.et_event);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        list = new ArrayList<>();

        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minutes = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txt_start_date.setText(hourOfDay + "." + minute);
                            }
                        }, hour, minutes, false);
                timePickerDialog.show();
            }
        });
        try {
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String event_name = et_event.getText().toString();
                    String event_time = txt_start_date.getText().toString();
                    boolean insert = db.insertData(event_name, event_time);

                    if (insert == true) {
                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
//        btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db.deleteData(tmpStr10);
//            }
//        });

        Cursor cr = db.getAllData();

        while (cr.moveToNext()) {
            id = cr.getInt(0);
            event_name = cr.getString(1);
            event_title = cr.getString(2);

            Model model = new Model();
            model.setId(id);
            model.setEvent_name(event_name);
            model.setEvent_time(event_title);
            list.add(model);
        }
        Collections.sort(list, new Comparator<Model>() {
            public int compare(Model obj1, Model obj2) {
                return obj1.getEvent_name().compareToIgnoreCase(obj2.getEvent_name()); // To compare string values
            }
        });

        adapter = new Adapter(list, context);
        RecyclerView.LayoutManager mLayoutManagers = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(mLayoutManagers);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);
        recyclerview.setNestedScrollingEnabled(false);
        adapter.setClickListener(MainActivity.this);
        adapter.notifyDataSetChanged();


    }

    @Override
    public void itemClicked(View view, int position) {
        if (view.getId() == R.id.img_del) {
            Model model = list.get(position);
            model.getId();
            db.deleteTitle(String.valueOf(model.getId()));
        }

    }


}