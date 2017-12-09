package com.orbis.samplematerialsearchview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.orbis.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    MaterialSearchView materialSearchView;
    Button btnClick;
    Toolbar toolbar;
    CustomAdapter customAdapter;
    SearchHelper searchHelper;

    private List<Object> objectsListToHelper = new ArrayList<>();
    private List<Object> objectList1 = new ArrayList<>();

    private String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        materialSearchView = (MaterialSearchView) findViewById(R.id.sv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnClick = (Button) findViewById(R.id.btnClick);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "HI!", Toast.LENGTH_SHORT).show();
            }
        });

        objectsListToHelper.add(new AlarmEntity("CarlitosDroid"));
        objectsListToHelper.add(new AlarmEntity("Jan"));
        objectsListToHelper.add(new AlarmEntity("Ricardo1"));
        objectsListToHelper.add(new AlarmEntity("Ricardo2"));
        objectsListToHelper.add(new AlarmEntity("Ricardo3"));
        objectsListToHelper.add(new AlarmEntity("Ricardo4"));

        searchHelper = new SearchHelper();
        searchHelper.setAlarmEntities(objectsListToHelper);

        customAdapter = new CustomAdapter(objectList1);
        materialSearchView.initFirstSetup(customAdapter);
        materialSearchView.svSearch.setOnQueryTextListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            objectList1.clear();
            objectList1.addAll(searchHelper.findAlarmhByName(query));
            materialSearchView.startRevealAnimation();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        query = newText;
        objectList1.clear();
        objectList1.addAll(searchHelper.findAlarmhByName(query));
        materialSearchView.searchAdapter.notifyDataSetChanged();
        return false;
    }
}
