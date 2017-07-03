package com.example.victor.easy_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.william.to_do.R;

import java.util.ArrayList;

public class MostrarTarefaActivity extends AppCompatActivity {

//    @InjectView(R.id.link_signup)
//    private Context context;

    private Button buttonApagar;
    final Tarefa tarefa = new Tarefa();

    private Toolbar mToolbar;
    private ArrayList<Tarefa> lista;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_tarefa);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("Tarefa");


        buttonApagar = (Button) findViewById(R.id.buttonApagar);
        buttonApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                FirebaseUtil fb = new FirebaseUtil();
//
//                fb.removerTaskDone(tarefa.getKey());
            }
        });
//        FirebaseUtil fb = new FirebaseUtil();
//        buttonApagar.setImageResource(R.drawable.);
//        Toast t = Toast.makeText(context, "Tarefa deletada", Toast.LENGTH_SHORT);
//        t.show();
//        fb.removerTask(tarefa.getKey());
//        fb.onChildRemoved(lista, tarefa.getKey());
//        fb.removerTaskDone(tarefa.getKey());
//        notifyDataSetChanged();


    }

}
