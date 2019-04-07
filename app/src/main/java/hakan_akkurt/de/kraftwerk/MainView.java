package hakan_akkurt.de.kraftwerk;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainView extends AppCompatActivity {

    private ListView listView;
    private KraftwerkOverviewListAdapter adapter;
    private List<Kraftwerk> dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        TextView message = findViewById(R.id.message);

        //Willkommensnachricht mit Virtuelle Kraftwerk ID ausgeben

        message.setText("Kraftwerk: " + Globals.getvKraftwerkId());
        this.listView = findViewById(R.id.ListView_Tasks);

        this.dataSource = KraftwerkDatabase.getInstance(this).readAllKraftwerke("NONE");

        this.adapter = new KraftwerkOverviewListAdapter(this, dataSource);

        this.listView.setAdapter(new KraftwerkOverviewListAdapter(this, dataSource));

        //Ein Listener, um festzustellen, ob der Benutzer ein Eintrag in der Liste angeklickt hat.
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, int position, long id) {
                Object element = adapterView.getAdapter().getItem(position);

                if(element instanceof Kraftwerk){
                    Kraftwerk kraftwerk = (Kraftwerk) element;

                    Intent intent = new Intent(MainView.this, KraftwerkDetailActivity.class);
                    intent.putExtra(KraftwerkDetailActivity.KRAFTWEK_ID_KEY, kraftwerk.getId());

                    startActivity(intent);
                }
                Log.e("ClickOnList", element.toString());
            }
        });

        //Ein Listener, der beim langen Drücken auf einen Eintrag in der Liste ein Dialog aufruft
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int position, long id) {
                Object element = adapterView.getAdapter().getItem(position);

                if(element instanceof Kraftwerk){
                    final Kraftwerk kraftwerk = (Kraftwerk)element;
                    new AlertDialog.Builder(MainView.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Sind Sie sicher?")
                            .setMessage("Möchten Sie diesen Eintrag löschen?")
                            .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    KraftwerkDatabase.getInstance(MainView.this).deleteKraftwerk(kraftwerk);
                                    refreshListView();
                                }
                            })
                            .setNegativeButton("Nein", null)
                            .show();
                }
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

//    @Override
//    public void onBackPressed() {
//        moveTaskToBack(true);
//    }

    public void refreshListView() {
        dataSource.clear();
        dataSource.addAll(KraftwerkDatabase.getInstance(this).readAllKraftwerke("NONE"));
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    //Hier werden die einzelnen Buttons mit Methoden verbunden
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_new_kraftwerk:
                this.newKraftwerk();
                return true;
            case R.id.sortDate:
                this.sortDate();
                return true;
            case R.id.sortLeistung:
                this.sortLeistung();
                return true;
            case R.id.berechneGesamtleistung:
                this.berechneGesamtleistung();
                return true;
            case R.id.clearAll:
                this.clearAll();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void newKraftwerk(){
        Intent i = new Intent(MainView.this, KraftwerkCreate.class);
        startActivity(i);
    }

    public void sortDate() {
        dataSource.clear();
        dataSource.addAll(KraftwerkDatabase.getInstance(this).readAllKraftwerke("DATE"));
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();
    }

    public void sortLeistung() {
        dataSource.clear();
        dataSource.addAll(KraftwerkDatabase.getInstance(this).readAllKraftwerke("LEISTUNG"));
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();
    }

    public void berechneGesamtleistung() {

        Cursor res = KraftwerkDatabase.getInstance(this).berechneSumme();
        if(res.getCount() == 0) {

            Toast.makeText(MainView.this, "Nothing found", Toast.LENGTH_LONG).show();
            return;
        }
        while (res.moveToNext()) {
            Toast.makeText(MainView.this, "Gesamtleistung ist " + res.getString(0) + " KW.", Toast.LENGTH_LONG).show();
        }
    }

    private void clearAll() {
        KraftwerkDatabase database = KraftwerkDatabase.getInstance(MainView.this);
        database.deleteAllKraftwerke();
        refreshListView();
    }

}





