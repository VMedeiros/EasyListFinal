package com.example.victor.easy_list;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.william.to_do.R;
import com.firebase.client.Firebase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private boolean viewIsAtHome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Firebase.setAndroidContext(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), NovaTarefaActivity.class);
                startActivity(it);

            }
        });

        displayView(R.id.nav_todo);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        ListView toDoList = (ListView) findViewById(R.id.lvToDo);
//        toDoList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Tarefa tarefaClicada = (Tarefa) parent.getAdapter().getItem(position);
//                Intent it = new Intent(view.getContext(),MostrarTarefaActivity.class);
//                Bundle dados = new Bundle();
//                dados.putParcelable("tarefa",tarefaClicada);
//                it.putExtras(dados);
//                startActivity(it);
//            }
//
//        });

//        toDoList = (ListView) findViewById(R.id.lvToDo);
//        toDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Tarefa t = (Tarefa) parent.getAdapter().getItem(position);
//                gerarNotificacao(view.getContext(), new Intent(view.getContext(), MainActivity.class), t);
//
//            }
//        });


        //lança a notificação automatica, quando a classe BroadCast nao estiver comentada.
        //Mas terá que ser feita uma comparação com as datas das tarefas cadastradas com a hora do sistema
        //para poder lançar a notificaçao. Do jeito que está ele lança após um determinado tempo sem buscar nenhum dado da tarefa
        Intent intent = new Intent("ALARME_DISPARADO");
        PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND, 3);

        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarme.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), p);


    }


    @Override
    public void onDestroy(){
        super.onDestroy();

        Intent intent = new Intent("ALARME_DISPARADO");
        PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarme.cancel(p);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if(!viewIsAtHome){
            displayView(R.id.nav_todo);
        }else {
            moveTaskToBack(true);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayView(item.getItemId());
        return true;
    }

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_todo:
                Log.e("aaaaaaaaaaaaaaaaaaaa","UI");
                fragment = new FragmentToDo();
                FirebaseUtil fb = new FirebaseUtil();
                fb.iniciarFirebase();
                Bundle bundle = new Bundle();
                //NA HORA QUE FOR LISTAR VERIFICANDO SE O OWNER É QUEM ESTA LOGADO
                bundle.putParcelableArrayList("listaTarefas", fb.obterListagemTarefas());
                Log.e("aaaaaaaaaaaaaaaaaaaa",""+fb.obterListagemTarefas());
                fragment.setArguments(bundle);
                title  = "To Do";
                viewIsAtHome = true;
                break;
            case R.id.nav_done:
                Log.e("aaaaaaaaaaaaaaaaaaaa","UI DONEEEE");
                fragment = new FragmentDone();
                FirebaseUtil fb2 = new FirebaseUtil();
                fb2.iniciarFirebase();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelableArrayList("listaDone", fb2.obterListagemTarefasRealizadas());
                Log.e("DOOOONEEEEE",""+fb2.obterListagemTarefasRealizadas());
                fragment.setArguments(bundle2);
                title  = "Done";
                viewIsAtHome = true;
                break;
            case R.id.nav_logout:
                viewIsAtHome = false;
                Intent it = new Intent(this, LoginActivity.class);
                startActivity(it);
                break;
            case R.id.nav_info:
                fragment = new FragmentSobre();
                title = "Sobre";
                viewIsAtHome = false;
                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragments_layout, fragment);
            ft.commit();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    public void gerarNotificacao(Context context, Intent intent, Tarefa t) {

        String titulo = t.getTitulo();
        String descricao = t.getDescricao();
        t.setTitulo(titulo);
        t.setDescricao(descricao);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker("Voce tem uma tarefa agendada para hoje!");
        builder.setContentTitle(t.getTitulo());
        builder.setContentText(t.getDescricao());
        builder.setSmallIcon(R.drawable.ic_not_small);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_assignment_late_red_24dp));
        builder.setContentIntent(p);

        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 600};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.drawable.ic_not_small, n);

        try {
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            MediaPlayer mp = MediaPlayer.create(context, som);
            mp.start();
        } catch (Exception e) {
        }

        Toast toast = Toast.makeText(this, "Clicou em " + titulo, Toast.LENGTH_SHORT);
        toast.show();

    }







}