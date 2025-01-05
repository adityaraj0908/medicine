package com.example.medicine;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText medname, meddate;
    Button insert, fetch;
    TextView medtext;
    Spinner day;
    Switch switch1;
    DataBaseConn dbconnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        medname=(EditText)findViewById(R.id.medicinename);
        meddate=(EditText)findViewById(R.id.date);
        insert=(Button)findViewById(R.id.insert);
        fetch=(Button)findViewById(R.id.fetch);
        day=(Spinner)findViewById(R.id.spinner);
        switch1=(Switch)findViewById(R.id.switch1);
        medtext=(TextView)findViewById(R.id.medtext);
        dbconnection = new DataBaseConn(this);

        fetch.setVisibility(View.INVISIBLE);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    fetch.setVisibility(View.INVISIBLE);
                    insert.setVisibility(View.VISIBLE);
                    medname.setVisibility(View.VISIBLE);
                    medtext.setVisibility(View.VISIBLE);
                }
                else
                {
                    fetch.setVisibility(View.VISIBLE);
                    insert.setVisibility(View.INVISIBLE);
                    medname.setVisibility(View.INVISIBLE);
                    medtext.setVisibility(View.INVISIBLE);
                }
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = medname.getText().toString();
                String date = meddate.getText().toString();
                String time = day.getSelectedItem().toString();

                if(name.isEmpty() || date.isEmpty() || time.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Fill in all the fields",Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"Data not inserted",Toast.LENGTH_LONG).show();
                }
                boolean insert = dbconnection.insertvalues(name,date,time);
                if(insert == true)
                {
                    Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_LONG).show();
                    medname.setText("");
                    meddate.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Data Not Inserted", Toast.LENGTH_LONG).show();

                }
            }
        });

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = meddate.getText().toString();
                String time = day.getSelectedItem().toString();
                String med ="";
                Cursor c = dbconnection.fetchdata(date,time);
                if(c.moveToFirst())
                {
                    do
                    {
                        med = med+(String.valueOf(c.getString(c.getColumnIndexOrThrow("MedicineName"))));
                        med+="\n";
                    }while(c.moveToNext());
                    Toast.makeText(getApplicationContext(),med,Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No Entries in Database", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}