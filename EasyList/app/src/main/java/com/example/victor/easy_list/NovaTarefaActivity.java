package com.example.victor.easy_list;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.william.to_do.R;


public class NovaTarefaActivity extends AppCompatActivity  {

    private TextView edtTitulo;
    private TextView edtDescricao;
    private TextView tvHora;
    private TextView tvData;
    private Button btnOk;
    final Tarefa tarefa = new Tarefa();
    private Toolbar mToolbar;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tarefa);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("Nova Tarefa");

        edtTitulo = (TextView) findViewById(R.id.editTitulo);
        edtTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTitulo.setText("");
                tarefa.setTitulo(edtTitulo.getText().toString());
            }
        });

        edtDescricao = (TextView) findViewById(R.id.editDescricao);
        edtDescricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtDescricao.setText("");
                tarefa.setDescricao(edtDescricao.getText().toString());
            }
        });

        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                FirebaseUtil fb = new FirebaseUtil();
                Tarefa t = new Tarefa();
                String titulo = edtTitulo.getText().toString();
                String descricao = edtDescricao.getText().toString();
                String hora = tvHora.getText().toString();
                String data = tvData.getText().toString();

                t.setTitulo(titulo);
                t.setDescricao(descricao);
                t.setHora(hora);
                t.setData(data);

                fb.inserirTask(t);
                finish();


            }
        });

        tvHora = (TextView) findViewById(R.id.tvHora);
        tvHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(),"TimePicker");
                tarefa.setHora(tvHora.getText().toString());

            }
        });

        tvData = (TextView) findViewById(R.id.tvData);
        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
                tarefa.setData(tvData.getText().toString());
            }
        });


    }


}










