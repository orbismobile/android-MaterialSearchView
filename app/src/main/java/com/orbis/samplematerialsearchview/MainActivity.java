package com.orbis.samplematerialsearchview;

import android.os.Build;
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

public class MainActivity extends AppCompatActivity {

    MaterialSearchView materialSearchView;
    Button btnClick;
    Toolbar toolbar;
    CustomAdapter customAdapter;

    private List<Object> objectList = new ArrayList<>();

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

        customAdapter = new CustomAdapter();

        customAdapter.addDataList(objectList);

        materialSearchView.initFirstSetup(objectList, customAdapter);
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

            objectList.add(new AlarmEntity("CarlitosDroid"));
            objectList.add(new AlarmEntity("Jan"));
            objectList.add(new AlarmEntity("Ricardo"));
            objectList.add(new ProfileEntity("Andres"));
            objectList.add(new ProfileEntity("Gerardo"));
            objectList.add(new ProfileEntity("Carlo"));
            materialSearchView.setVisibleWithAnimation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (materialSearchView.getCurrentVisibility() == View.VISIBLE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                materialSearchView.circleReveal(1, false, false);
            } else {
                //setOnlyToolbarVisible();
            }
        } else {
            super.onBackPressed();
        }

    }
}
