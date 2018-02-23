package com.example.hubson.randomuserapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hubson.randomuserapp.dummy.DummyContent;
import com.example.hubson.randomuserapp.ui.ContactAdapter;

public class ContactListActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private boolean mTwoPane;

    private static final String URL = "https://randomuser.me/api/";
    private static final int ALL_CONTACTS_TAB = 0;
    private static final int FAVOURITES_TAB = 1;
    private static final String ALL_CONTACTS_TITLE = "All contacts";
    private static final String FAVOURITES_TITLE = "Favourites";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        setUpActionBar();
        setUpRecyclerView();
        setUpTabs();


        if (findViewById(R.id.contact_detail_container) != null) {
            mTwoPane = true;
        }
        if(hasNetworkConnection() && savedInstanceState == null)
            DummyContent.downloadAndSetData(this, URL, recyclerView);
        else recyclerView.setAdapter(new ContactAdapter(this, DummyContent.getAll()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(tabLayout.getSelectedTabPosition() == FAVOURITES_TAB) {
            recyclerView.setAdapter(new ContactAdapter(this, DummyContent.getFavourites()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.refresh:
                if(hasNetworkConnection())
                    DummyContent.downloadAndSetData(this, URL, recyclerView);
                else
                    showDialogToEnableNetwork();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ContactAdapter adapter = (ContactAdapter) recyclerView.getAdapter();
                if (adapter != null) adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    public boolean isTwoPane() {
        return mTwoPane;
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        return nInfo != null && nInfo.isConnected();
    }

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.contact_list);
        recyclerView.setHasFixedSize(true);
    }

    private void setUpTabs() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_all_contacts).setText(ALL_CONTACTS_TITLE));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_favourites).setText(FAVOURITES_TITLE));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tabLayout.getSelectedTabPosition();
                switch(position) {
                    case ALL_CONTACTS_TAB:
                        recyclerView.setAdapter(new ContactAdapter(ContactListActivity.this, DummyContent.getAll()));
                        break;
                    case FAVOURITES_TAB:
                        recyclerView.setAdapter(new ContactAdapter(ContactListActivity.this, DummyContent.getFavourites()));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void showDialogToEnableNetwork() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.network_settings_title));
        alertDialog.setMessage(getResources().getString(R.string.network_settings_message));

        alertDialog.setPositiveButton(getResources().getString(R.string.network_settings_positive_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton(getResources().getString(R.string.network_settings_negative_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alertDialog.show();
    }
}
