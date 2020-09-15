package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    Button btnAddNew;


    DatabaseReference reference;
    RecyclerView ourtodo;
    ArrayList<MyTodo> list;
    TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddNew = findViewById(R.id.btnAddNew);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, NewTaskAct.class);
                startActivity(a);
            }
        });

        //work with data
        ourtodo = findViewById(R.id.ourtodo);
        ourtodo.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyTodo>();

        //get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("BoxTodo");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // set code to retrieve data
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                    {
                        MyTodo p = dataSnapshot1.getValue(MyTodo.class);
                        list.add(p);
                    }
                todoAdapter = new TodoAdapter(MainActivity.this, list);
                ourtodo.setAdapter(todoAdapter);
                todoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // set code to show error
                Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
