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
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Dashboard extends AppCompatActivity {

        //Initialising values
        ActionBarDrawerToggle toggle;
        DrawerLayout drawerLayout;
        Toolbar toolbar;
        public Button proceed;
        ListView listView;



        // creating a variable for database reference.
        FirebaseDatabase rootNode;
        DatabaseReference reference,reference2;

        //Creating array list
        ArrayAdapter arrayAdapter;
        ArrayList<String> arrayList = new ArrayList<>();



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dashboard2);
            listView = findViewById(R.id.list);
            rootNode = FirebaseDatabase.getInstance();
            reference =  rootNode.getReference("Location");
            reference2 =  rootNode.getReference("Users");

            listView = findViewById(R.id.list);
            //Adds items to listview
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







        //Implementing search function for listview
        String s = "";
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.option_menu, menu);
            MenuItem menuItem = menu.findItem(R.id.search);
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setQueryHint("Enter Location");
            //Listens to opening/closing of search bar
            MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    listView.setVisibility(VISIBLE);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Object listItem = listView.getItemAtPosition(position);
                            searchView.setQuery(listItem.toString(),true);
                            s = listItem.toString();
                        }
                    });
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    listView.setVisibility(GONE);
                    return true;
                }
            });
            //listens to text change or submit in search bar
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(com.example.driveeasy.Dashboard.this,query,Toast.LENGTH_SHORT).show();
                    listView.setVisibility(GONE);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    s="";
                    listView.setVisibility(VISIBLE);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Object listItem = listView.getItemAtPosition(position);
                            searchView.setQuery(listItem.toString(),true);
                            s = listItem.toString();
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

            //Initialising variables
            FirebaseUser user;
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            SeekBar seekBar = findViewById(R.id.seekBar);
            user = mAuth.getCurrentUser();
            toolbar=findViewById(R.id.toolbar);
            proceed=findViewById(R.id.Proceed);
            setActionBar(toolbar);

            //Navigation Drawable in dashboard
            NavigationView nav_view= (NavigationView)findViewById(R.id.nav_view);
            nav_view.bringToFront();
            drawerLayout=findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.Open, R.string.Close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //Checks if proceed button is clicked
            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Updates children in user's database
                    DashboardHelperClass HelperClass = new DashboardHelperClass(formatter.format(calendarView.getDate()),seekBar.getProgress(),s);
                    reference2.child(user.getPhoneNumber()).child("date").setValue(HelperClass.date);
                    reference2.child(user.getPhoneNumber()).child("locSet").setValue(HelperClass.locSet);
                    reference2.child(user.getPhoneNumber()).child("noD").setValue(HelperClass.noD);
                    if(s.equals("") || s.equals("Select location later"))
                    {
                        Toast.makeText(com.example.driveeasy.Dashboard.this,"Select valid location in search bar",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Goes to Home page
                        Intent intent = new Intent(com.example.driveeasy.Dashboard.this, Home.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });



            //listens to seakbar changes
            TextView text = findViewById(R.id.text);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    text.setText(""+seekBar.getProgress()+" Day(s)");

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {


                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });



            //Selecting Calendar date and not allowing any date before current date on selection

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
                        DashboardHelperClass HelperClass = new DashboardHelperClass(formatter.format(calendarView.getDate()),seekBar.getProgress(),s);
                        reference2.child(user.getPhoneNumber()).child("date").setValue(HelperClass.date);
                        reference2.child(user.getPhoneNumber()).child("locSet").setValue(HelperClass.locSet);
                        reference2.child(user.getPhoneNumber()).child("noD").setValue(HelperClass.noD);
                        if(s.equals("") || s.equals("Select location later"))
                        {
                            Toast.makeText(com.example.driveeasy.Dashboard.this,"Select valid location in search bar",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Intent intent = new Intent(com.example.driveeasy.Dashboard.this, Home.class);
                            startActivity(intent);
                            finish();
                        }
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


        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        }
    }















