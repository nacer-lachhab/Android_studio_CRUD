package com.example.courses_registration;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText ed1, ed2, ed3;
    Button buttonSave, buttonView;

    public void insertInDb(){
        try {
            String name = ed1.getText().toString();
            String course = ed2.getText().toString();
            String fee = ed3.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("mySqLiteDb", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS recordsTable(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, course VARCHAR, fee VARCHAR)");

            String sqlInsertReq = "INSERT INTO recordsTable(name,course,fee) VALUES (?,?,?)";
            SQLiteStatement statement = db.compileStatement(sqlInsertReq);
            statement.bindString(1,name);
            statement.bindString(2,course);
            statement.bindString(3,fee);
            statement.execute();

            //pop up temporaire qui remonte afficher un msg
            //this: peut etre remplacé par getApplicationContext()
            Toast.makeText(this,"Record added successfuly.....",Toast.LENGTH_LONG).show();

            ed1.setText("");
            ed2.setText("");
            ed3.setText("");

            //renvoie le curseur sur ce champ, en attente de saisie utilisateur
            ed1.requestFocus();
        }catch (Exception ex){
            Toast.makeText(this,"Record Failed!!!!!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = findViewById(R.id.name);
        ed2 = findViewById(R.id.course);
        ed3 = findViewById(R.id.fee);

        buttonSave = findViewById(R.id.btnSave);
        buttonView = findViewById(R.id.btnView);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertInDb();
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewAllRecords.class);
                startActivity(intent);
                finish();
            }
        });
    }


}