package com.example.driveeasy;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    //Declaring necessary variables
    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<Car> cars;
    CarAdapter myAdapter;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialising Recyclerview list for cars
        recyclerView = findViewById(R.id.recylerView);
        reference = FirebaseDatabase.getInstance().getReference("Cars");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cars = new ArrayList<>();
        myAdapter = new CarAdapter(this,cars);
        recyclerView.setAdapter(myAdapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Car car = dataSnapshot.getValue(Car.class);
                    cars.add(car);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Navigation view drawable for home screen
        NavigationView nav_view= (NavigationView)findViewById(R.id.nav_view);
        nav_view.bringToFront();
        drawerLayout=findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if(id== R.id.home)
                {
                    Toast.makeText(com.example.driveeasy.Home.this,"Home",Toast.LENGTH_SHORT).show();
                }
                else if(id== R.id.logout)
                {
                    Toast.makeText(com.example.driveeasy.Home.this,"Logout",Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(com.example.driveeasy.Home.this, SignIn.class);
                    startActivity(intent);

                }
                else if(id == R.id.dashboard)
                {
                    Intent intent = new Intent(com.example.driveeasy.Home.this, com.example.driveeasy.Dashboard.class);
                    startActivity(intent);
                    finish();
                }



                return true;
            }
        });


    }

    //Implementing sorting using search bar
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homesearch, menu);
        MenuItem menuItem = menu.findItem(R.id.searchhome);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Enter Car Name");

        //Listening to search collapse
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                cars.clear();
                recyclerView.setAdapter(myAdapter);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Car car = dataSnapshot.getValue(Car.class);
                            cars.add(car);
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                }
                );
                return true;
            }
        });

        //Listening to keystrokes
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (true) {
                    myAdapter.getFilter().filter(newText);
                }
                return false;

            }
        });
        return true;
    }

    //Adding functionality to a menu icon button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}