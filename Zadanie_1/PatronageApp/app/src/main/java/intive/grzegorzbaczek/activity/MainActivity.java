package intive.grzegorzbaczek.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import intive.grzegorzbaczek.R;
import intive.grzegorzbaczek.db.ExpenseDataAdapter;
import intive.grzegorzbaczek.fragment.AboutFragment;
import intive.grzegorzbaczek.fragment.HomepageFragment;
import intive.grzegorzbaczek.fragment.SettingsFragment;
import intive.grzegorzbaczek.thread.DatabaseAsyncTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int currentFragment = R.id.nav_homepage;
    private int lastUsedFragment = R.id.nav_homepage;
    private String login = null;
    private String authorizationData = null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            case R.id.nav_homepage:
                fragmentClass = HomepageFragment.class;
                title = R.string.homepage;
                currentFragment = R.id.nav_homepage;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                title = R.string.settings;
                currentFragment = R.id.nav_settings;
                break;
            case R.id.nav_about:
                fragmentClass = AboutFragment.class;
                title = R.string.about;
                currentFragment = R.id.nav_about;
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder dialogBuilder;
        if (currentFragment == R.id.nav_homepage) {

            switch (item.getItemId()) {
                case R.id.toolbar_insert_option:
                    getDataFromUser("insert");
                    break;
                case R.id.toolbar_modify_option:
                    getDataFromUser("update");
                    break;
                case R.id.toolbar_delete_option:
                    DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
                    asyncTask.execute("delete", String.valueOf(ExpenseDataAdapter.selectedItemID));
                    break;
                default:
                    break;
            }
        } else {
            dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
            dialogBuilder.setMessage("Wrong fragment!");
            dialogBuilder.setNeutralButton("OK", null);
            dialogBuilder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDataFromUser(final String option) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptsView = layoutInflater.inflate(R.layout.userinput_dialogform, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(promptsView);

        final EditText nameInput = promptsView.findViewById(R.id.formName);
        final EditText dateInput = promptsView.findViewById(R.id.formDate);
        final EditText typeInput = promptsView.findViewById(R.id.formType);
        final EditText valueInput = promptsView.findViewById(R.id.formValue);

        if(option == "update"){
            nameInput.setText(ExpenseDataAdapter.selectedItemName);
            dateInput.setText(ExpenseDataAdapter.selectedItemDate);
            typeInput.setText(ExpenseDataAdapter.selectedItemType);
            valueInput.setText(ExpenseDataAdapter.selectedItemValue);
        }

        dialogBuilder
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
                        if (option == "insert") {
                            asyncTask.execute(
                                    "insert",
                                    dateInput.getText().toString(),
                                    typeInput.getText().toString(),
                                    nameInput.getText().toString(),
                                    valueInput.getText().toString());
                        } else {
                            asyncTask.execute(
                                    "update",
                                    String.valueOf(ExpenseDataAdapter.selectedItemID),
                                    dateInput.getText().toString(),
                                    typeInput.getText().toString(),
                                    nameInput.getText().toString(),
                                    valueInput.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel", null);
        dialogBuilder.create().show();
    }
}
