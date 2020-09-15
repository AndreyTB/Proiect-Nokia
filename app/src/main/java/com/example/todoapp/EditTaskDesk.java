package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTaskDesk extends AppCompatActivity {

    EditText titleTodo, descTodo, dateTodo;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_desk);

        titleTodo = findViewById(R.id.titletodo);
        descTodo = findViewById(R.id.desctodo);
        dateTodo = findViewById(R.id.datetodo);

        btnSaveUpdate = findViewById(R.id.btnSaveUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        //get value from previous page
        titleTodo.setText(getIntent().getStringExtra("titletodo"));
        descTodo.setText(getIntent().getStringExtra("desctodo"));
        dateTodo.setText(getIntent().getStringExtra("datetodo"));
        final String keykeyTodo = getIntent().getStringExtra("keytodo");

        reference = FirebaseDatabase.getInstance().getReference().child("BoxTodo").child("Todo"+keykeyTodo);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent a = new Intent(EditTaskDesk.this, MainActivity.class);
                            startActivity(a);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Failure!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //event for button
        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titletodo").setValue(titleTodo.getText().toString());
                        dataSnapshot.getRef().child("desctodo").setValue(descTodo.getText().toString());
                        dataSnapshot.getRef().child("datetodo").setValue(dateTodo.getText().toString());
                        dataSnapshot.getRef().child("keytodo").setValue(keykeyTodo);
                        Intent a = new Intent(EditTaskDesk.this, MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
