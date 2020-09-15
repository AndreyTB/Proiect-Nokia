package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyTodo> myTodos;

    public TodoAdapter(Context c, ArrayList<MyTodo> p){
        context = c;
        myTodos = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_todo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.titletodo.setText(myTodos.get(i).getTitletodo());
        myViewHolder.desctodo.setText(myTodos.get(i).getDesctodo());
        myViewHolder.datetodo.setText(myTodos.get(i).getDatetodo());

        final String getTitleTodo = myTodos.get(i).getTitletodo();
        final String getDescTodo = myTodos.get(i).getDesctodo();
        final String getDateTodo = myTodos.get(i).getDatetodo();
        final String getKeyTodo = myTodos.get(i).getKeytodo();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(context, EditTaskDesk.class);
                aa.putExtra("titletodo", getTitleTodo);
                aa.putExtra("desctodo", getDescTodo);
                aa.putExtra("datetodo", getDateTodo);
                aa.putExtra("keytodo", getKeyTodo);
                context.startActivity(aa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTodos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titletodo, desctodo, datetodo, keytodo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titletodo = (TextView) itemView.findViewById(R.id.tittletodo);
            desctodo = (TextView) itemView.findViewById(R.id.desctodo);
            datetodo = (TextView) itemView.findViewById(R.id.datetodo);
        }
    }
}
