package com.example.courses_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.courses_registration.model.Course;

import java.io.Serializable;

public class EditCourse extends AppCompatActivity {

    EditText ed1, ed2, ed3;
    Button buttonEdit, buttonDelete;

    public Course editRecord(String courseId){

        Course c = new Course();

        try {
            String name = ed1.getText().toString();
            String course = ed2.getText().toString();
            String fee = ed3.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("mySqLiteDb", Context.MODE_PRIVATE,null);

            String sqlInsertReq = "UPDATE recordsTable SET name= ?, course= ?, fee= ? where id= ?";
            SQLiteStatement statement = db.compileStatement(sqlInsertReq);
            statement.bindString(1,name);
            statement.bindString(2,course);
            statement.bindString(3,fee);
            statement.bindString(4,courseId);
            statement.execute();

            //pop up temporaire qui remonte afficher un msg
            //this: peut etre remplacé par getApplicationContext()
            Toast.makeText(this,"Record Updated successfuly.....",Toast.LENGTH_LONG).show();


            c.id = courseId;
            c.name = name;
            c.course = course;
            c.fee = fee;

            return c;

        }catch (Exception ex){
            Toast.makeText(this,"Record Failed!!!!!",Toast.LENGTH_LONG).show();
        }
        return c;
    }

    public void removeRecord(String courseId){

        SQLiteDatabase db = openOrCreateDatabase("mySqLiteDb", Context.MODE_PRIVATE,null);
        String sqlInsertReq = "DELETE FROM recordsTable  where id= ?";
        SQLiteStatement statement = db.compileStatement(sqlInsertReq);
        statement.bindString(1,courseId);
        statement.execute();

        Toast.makeText(this,"Record with id="+courseId+" Deleted successfuly.....",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        Intent intent = getIntent();
        Course c = (Course) intent.getSerializableExtra("courseToEdit");

        ed1 = findViewById(R.id.name); //pour pointer sur la zone et eviter le null
        ed1.setText(c.name);

        ed2 = findViewById(R.id.course);
        ed2.setText(c.course);

        ed3 = findViewById(R.id.fee);
        ed3.setText(c.fee);

        buttonEdit = findViewById(R.id.btnEdit);
        buttonDelete = findViewById(R.id.btnDelete);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course courseUpdated = editRecord(c.id);
                Intent intent = new Intent(getApplicationContext(), EditCourse.class);
                intent.putExtra("courseToEdit", courseUpdated);
                startActivity(intent);
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeRecord(c.id);
                Intent intent = new Intent(getApplicationContext(), ViewAllRecords.class);
                startActivity(intent);
                finish();
            }
        });

    }
}