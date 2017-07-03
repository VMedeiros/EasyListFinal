package com.example.victor.easy_list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.william.to_do.R;

import java.util.ArrayList;

public class FragmentToDo extends Fragment {

    private ArrayList<Tarefa> minhasTarefas = new ArrayList<>();
    private TarefaAdapter tarefaAdapter;
    private ListView toDoList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        toDoList = (ListView) view.findViewById(R.id.lvToDo);

        Bundle bundle = getArguments();

        minhasTarefas = bundle.getParcelableArrayList("listaTarefas");

        tarefaAdapter = new TarefaAdapter(getContext(), minhasTarefas);

        toDoList.setAdapter(tarefaAdapter);
        tarefaAdapter.notifyDataSetChanged();

        return view;
    }
}