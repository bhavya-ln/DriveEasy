package com.example.driveeasy;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

    public class Dashboard extends AppCompatActivity {
        ActionBarDrawerToggle toggle;
        DrawerLayout drawerLayout;
        Toolbar toolbar;
        Calendar calendar;
        public Button proceed;
        TimePicker timePicker;
        ListView listView;

        // creating a new array list.


        // creating a variable for database reference.
        FirebaseDatabase rootNode;
        DatabaseReference reference;

        ArrayAdapter arrayAdapter;
        ArrayList<String> arrayList = new ArrayList<>();



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dashboard2);
            listView = findViewById(R.id.list);
            rootNode = FirebaseDatabase.getInstance();
            reference =  rootNode.getReference("Location");


    //        listView = findViewById(R.id.list);
    //        coursesArrayList = new ArrayList<String>();
    //
    //        // calling a method to get data from
    //        // Firebase and set data to list view
    //        initializeListView();

            listView = findViewById(R.id.list);
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String value = snapshot.getValue(String.class);
                    arrayList.add(value);
                    arrayAdapter = new ArrayAdapter<String>(com.example.driveeasy.Dashboard.this,android.R.layout.simple_list_item_1,arrayList);
                    listView.setAdapter(arrayAdapter);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }








        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.option_menu, menu);
            MenuItem menuItem = menu.findItem(R.id.search);
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setQueryHint("Enter Location");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(com.example.driveeasy.Dashboard.this,query,Toast.LENGTH_SHORT).show();
                    listView.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    listView.setVisibility(View.VISIBLE);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Object listItem = listView.getItemAtPosition(position);
                            searchView.setQuery(listItem.toString(),true);
                        }
                    });
                    arrayAdapter.getFilter().filter(newText);
                    return false;

                }
            });
            return true;
        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onStart() {
            super.onStart();
            toolbar=findViewById(R.id.toolbar);
            proceed=findViewById(R.id.Proceed);
            setActionBar(toolbar);
            NavigationView nav_view= (NavigationView)findViewById(R.id.nav_view);
            nav_view.bringToFront();
            drawerLayout=findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.Open, R.string.Close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(com.example.driveeasy.Dashboard.this, Home.class);
                    startActivity(intent);
                    finish();
                }
            });

    //        TimePicker timePicker =(TimePicker)findViewById(R.id.timePicker);
    //        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
    //            @Override
    //            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
    //                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    //                    timePicker.setHour(hourOfDay);
    //
    //                    timePicker.setMinute(minute);
    //                }
    //            }
    //        });

            SeekBar seekBar = findViewById(R.id.seekBar);
            TextView text = findViewById(R.id.text);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    text.setText(""+seekBar.getProgress()+" Days");

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {


                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });



            //Selecting Calendar date and not allowing any date before current date on selection
            CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    if(System.currentTimeMillis()-calendarView.getDate()>86400000){
                        calendarView.setMinDate(System.currentTimeMillis());
                    }
                }
            });

            //Navigation menu popping out and toggling
            nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    int id = item.getItemId();
                    if(id== R.id.home)
                    {
                        Intent intent = new Intent(com.example.driveeasy.Dashboard.this, com.example.driveeasy.Home.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(id== R.id.logout)
                    {
                        Toast.makeText(com.example.driveeasy.Dashboard.this,"Logout",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(com.example.driveeasy.Dashboard.this, SignIn.class);
                        startActivity(intent);

                    }
                    else if(id == R.id.dashboard)
                    {
                        Toast.makeText(com.example.driveeasy.Dashboard.this,"Dashboard",Toast.LENGTH_SHORT).show();
                    }



                    return true;
                }
            });



    }




    //        listView = findViewById(R.id.list);
    //        coursesArrayList = new ArrayList<String>();
    //
    //        // calling a method to get data from
    //        // Firebase and set data to list view
    //        initializeListView();



        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        }
        private void reload() {
            Intent intent = new Intent(com.example.driveeasy.Dashboard.this, com.example.driveeasy.Dashboard.class);
            startActivity(intent);
            finish();

        }


    }















