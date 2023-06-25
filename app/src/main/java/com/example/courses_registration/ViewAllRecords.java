package com.example.courses_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.courses_registration.model.Course;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewAllRecords extends AppCompatActivity {

    ListView listRecords;
    List<String> listTitles = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_records);

        System.out.println("1er Acces a la 2eme page");

        SQLiteDatabase db = openOrCreateDatabase("mySqLiteDb", Context.MODE_PRIVATE,null);

        System.out.println("ouverture de la BD...........");

        listRecords = findViewById(R.id.list1);
        //lecture ligne par ligne
        final Cursor c = db.rawQuery("SELECT * FROM recordsTable",null);
        int id = c.getColumnIndex("id");
        int name = c.getColumnIndex("name");
        int course = c.getColumnIndex("course");
        int fee = c.getColumnIndex("fee");

        //**************************************************
        System.out.println("***************");
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listTitles);
        listRecords.setAdapter(arrayAdapter);

        final ArrayList<Course> courses = new ArrayList<>();

        if (c.moveToFirst()){
            do{
                Course coursei = new Course();
                coursei.id = c.getString(id);
                coursei.name = c.getString(name);
                coursei.course = c.getString(course);
                coursei.fee = c.getString(fee);

                courses.add(coursei);
                listTitles.add(coursei.id+"\t\t\t"+coursei.name+"\t\t\t"+coursei.course+"\t\t\t"+coursei.fee);
            }while (c.moveToNext());

            arrayAdapter.notifyDataSetChanged();
            listRecords.invalidateViews();
        }

        //Lors du click sur un element de la list view, acceder a une vue de modification
        listRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //listTitles: contenue de la ligne
                //String temp = listTitles.get(i).toString();
                //Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), EditCourse.class);

                //System.out.println("****////////////// "+courses.get(i)+" ///////////***");

                intent.putExtra("courseToEdit", courses.get(i));
                startActivity(intent);
                finish();
            }
        });
    }
}