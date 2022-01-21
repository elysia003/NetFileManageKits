package com.example.nasmanage.aa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.nasmanage.GloabValue;
import com.example.nasmanage.R;
import com.example.nasmanage.databinding.ActivityMain22Binding;
import com.example.nasmanage.databinding.MainActivityBinding;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;


public class MainActivity22 extends AppCompatActivity {
    public Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
    public ActivityMain22Binding binding;
    public void setTitle(String t){
        toolbar.setTitle(t);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GloabValue gv = new GloabValue();
        gv.init(this);
        binding = ActivityMain22Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar tb = binding.appBarMain22.toolbar;
        tb.inflateMenu(R.menu.main_activity22);
        setSupportActionBar(tb);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        toolbar=tb;

        View headerView = navigationView.getHeaderView(0);
        ((TextView)headerView.findViewById(R.id.t1)).setText("Files");
        ((TextView)headerView.findViewById(R.id.textView)).setText(GloabValue.base_vedio_url);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main,
                R.id.nav_gallery,
                R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main22);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Toolbar 必须在onCreate()之后设置标题文本，否则默认标签将覆盖我们的设置
        if (toolbar != null) {
            toolbar.setTitle("/");
            //toolbar.setSubtitle("微信安全支付");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity22, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*
            case R.id.app_bar_switch:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String now = prefs.getString("buju", "null");
                if ("null".equals(now)) {
                    prefs.edit().putString("buju", "list").commit();
                } else {
                    if ("list".equals(now)) {
                        prefs.edit().remove("buju").commit();
                        prefs.edit().putString("buju", "bigimg").commit();
                        item.setIcon(R.drawable.exo_controls_shuffle_off);
                    } else if ("bigimg".equals(now)) {
                        prefs.edit().remove("buju").commit();
                        prefs.edit().putString("buju", "list").commit();
                        item.setIcon(R.drawable.exo_controls_fastforward);
                    }
                }
                break;

             */
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main22);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}