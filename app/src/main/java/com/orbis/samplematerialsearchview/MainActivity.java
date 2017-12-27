package com.orbis.samplematerialsearchview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.orbis.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MaterialSearchView.OnQueryTextListener,
        MaterialSearchView.OnVoiceClickListener,
        CustomAdapter.OnSuggestionClickListener {

    private MaterialSearchView materialSearchView;
    private Button btnClick;
    private Toolbar toolbar;
    private CustomAdapter customAdapter;
    private SearchHelper searchHelper;

    private final List<Object> objectsListToHelper = new ArrayList<>();
    private final List<Object> objectList1 = new ArrayList<>();

    private String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        materialSearchView = findViewById(R.id.sv);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnClick = findViewById(R.id.btnClick);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "HI!", Toast.LENGTH_SHORT).show();
            }
        });

        objectsListToHelper.add(new AlarmEntity("CarlitosDroid"));
        objectsListToHelper.add(new AlarmEntity("Nodejs"));
        objectsListToHelper.add(new AlarmEntity("Android"));
        objectsListToHelper.add(new AlarmEntity("Kotlin"));
        objectsListToHelper.add(new AlarmEntity("Docker"));
        objectsListToHelper.add(new AlarmEntity("Oreo"));

        searchHelper = new SearchHelper();
        searchHelper.setAlarmEntities(objectsListToHelper);

        customAdapter = new CustomAdapter(objectList1);

        materialSearchView.initFirstSetup(customAdapter);

        customAdapter.setOnSuggestionClickListener(this);
        materialSearchView.setOnQueryTextListener(this);
        materialSearchView.setmOnVoiceClickListener(this);
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
            materialSearchView.startSearcherAnimation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        materialSearchView.setVisibility(View.INVISIBLE);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        query = newText;
        objectList1.clear();
        objectList1.addAll(searchHelper.findAlarmhByName(query));
        customAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public void onSuggestionClick(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        materialSearchView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onVoiceClick() {
        Toast.makeText(this, "VOICE CLICKED", Toast.LENGTH_SHORT).show();
    }

}
