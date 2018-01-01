package intive.grzegorzbaczek;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int lastUsedFragment = R.id.nav_homepage;
    private String login = null;
    private String authorizationData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState != null) {
            lastUsedFragment = savedInstanceState.getInt("_lastUsedFragment");
            displayView(savedInstanceState.getInt("_lastUsedFragment"));
        } else {
            displayView(R.id.nav_homepage);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("_lastUsedFragment", lastUsedFragment);
        outState.putString("_login", login);
        outState.putString("_authorizationData", authorizationData);
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

    public void displayView(int viewId) {

        int title = 0;
        Fragment fragment = null;
        Class fragmentClass = null;

        switch (viewId) {
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                title = R.string.settings;
                break;
            case R.id.nav_about:
                fragmentClass = AboutFragment.class;
                title = R.string.about;
                break;
            case R.id.nav_exit:
                finishAndRemoveTask();
                System.exit(0);
                break;
            default:
                fragmentClass = HomepageFragment.class;
                title = R.string.homepage;
                break;
        }

        if (fragmentClass != null) {
            getSupportActionBar().setTitle(getString(title));

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contentPanel, fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        lastUsedFragment = item.getItemId();
        displayView(item.getItemId());
        return true;
    }
}
