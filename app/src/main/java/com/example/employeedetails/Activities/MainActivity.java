package com.example.employeedetails.Activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.employeedetails.AppSession;
import com.example.employeedetails.Fragments.Calculator.CalculatorFragment;
import com.example.employeedetails.Fragments.EmployeeDetails;
import com.example.employeedetails.Fragments.GuideLinesFragment;
import com.example.employeedetails.Fragments.HomeFragment;
import com.example.employeedetails.Fragments.ITDF.DeclarationFragment;
import com.example.employeedetails.Fragments.ITDF.IncomeFragment;
import com.example.employeedetails.Fragments.ITDF.OldRegimeFragment;
import com.example.employeedetails.Fragments.NewTaxSlabsFragment;
import com.example.employeedetails.Fragments.SettingsFragment;
import com.example.employeedetails.Fragments.SlabsFragment;
//import com.example.employeedetails.Networkservice;
import com.example.employeedetails.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnGridviewItemSelectedListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle t;
    private NavigationView navigationView;
    AppSession session;

//    public static final String BROADCAST = "checkinternet";
//    IntentFilter intentFilter;
    FragmentManager fragmentManager=getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session=new AppSession(this);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
fragmentManager.beginTransaction().addToBackStack("firstOne").replace(R.id.frameLayoutContainer,new HomeFragment()).commit();


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        intentFilter = new IntentFilter();
//        intentFilter.addAction(BROADCAST);
//        Intent serviceIntent = new Intent(this,Networkservice.class);
//        startService(serviceIntent);
//        if (Networkservice.isOnline(getApplicationContext())){
//            Toast.makeText(getApplicationContext(),"true",Toast.LENGTH_SHORT).show();
//        }else
//            Toast.makeText(getApplicationContext(),"false",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.slabs, menu);
        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
   int id = item.getItemId();
       Fragment fragment=null;
       switch(id)
       {
           case R.id.taxSlabs:
               fragment=new SlabsFragment();
               break;
           case R.id.newtax:
               fragment =new NewTaxSlabsFragment();
               break;
           case R.id.home:
               fragment=new HomeFragment();
               break;
       }

       fragmentManager.beginTransaction().addToBackStack("First").replace(R.id.frameLayoutContainer,fragment).commit();
       item.setChecked(true);
       drawer.closeDrawer(GravityCompat.START);
       return super.onOptionsItemSelected(item);
   }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        onItemClicked(id);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onItemClicked(int id){
        Fragment fragment=null;
        switch(id)
        {
            case R.id.details:
                if(session.getRegimeType()==null) {
                    fragment = new DeclarationFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("fromMain","yes");
                    fragment.setArguments(bundle);
                }
                else if(session.getRegimeType().equals("oldRegime")) fragment=new OldRegimeFragment();
                else fragment=new IncomeFragment();
//                Toast.makeText(MainActivity.this, "In Employee details",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Home:

                fragment=new HomeFragment();
                break;
            case R.id.profile:
                fragment=new EmployeeDetails();
                break;
            case R.id.nav_calculator:
                fragment=new CalculatorFragment();
                break;
            case R.id.settings:
                fragment=new SettingsFragment();
                break;
            case R.id.logout:
                showPopup();
                break;
        }
        if(id!=R.id.logout) {
//            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack("First").replace(R.id.frameLayoutContainer, fragment).commit();
//            item.setChecked(true);
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
//        registerReceiver(broadcastReceiver,intentFilter);
        List<Fragment> list= getSupportFragmentManager().getFragments();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutContainer,list.get(list.size()-1)).commit();
    }
    private void showPopup(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setMessage("Are you sure?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    private void logout() {
        session.logOut();

//        .getInstance().signOut(this).addOnComplete();



        Intent i=new Intent(this,Login_page.class);
        startActivity(i);
//        FirebaseAuth.getInstance()
//                .signOut();
        finish();
    }

    @Override
    public void onGridviewItemPicked(String item) {
    switch(item){
        case "MyProfile":onItemClicked(R.id.profile);
        break;
        case "DeclarationForm":onItemClicked(R.id.details);
        break;
        case "taxCalculator":
            onItemClicked(R.id.nav_calculator);
            break;
    }
    }

}