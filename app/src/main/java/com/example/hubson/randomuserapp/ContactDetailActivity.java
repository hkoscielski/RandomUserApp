package com.example.hubson.randomuserapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.hubson.randomuserapp.dummy.DummyContent;

public class ContactDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        setUpActionBar();
        setUpStatusBar();
        setUpAppBar();
        setUpFavouritesFab();

        if (savedInstanceState == null) {
            prepareDetailFragment();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpStatusBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    private void setUpAppBar() {
        AppBarLayout appBar = (AppBarLayout) findViewById(R.id.app_bar);
        if(appBar != null) {
            float height = getResources().getDisplayMetrics().widthPixels;
            appBar.setLayoutParams(new CoordinatorLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, Math.round(height)));
        }
    }

    private void setUpFavouritesFab() {
        final DummyContent.DummyItem mItem = DummyContent.ITEM_MAP.get(getIntent().getStringExtra(ContactDetailFragment.ARG_ITEM_ID));
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(mItem.isFavourite())
            fab.setImageResource(R.drawable.ic_star_favourite);
        else
            fab.setImageResource(R.drawable.ic_star_not_favourite);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItem.isFavourite()) {
                    mItem.setFavourite(false);
                    fab.setImageResource(R.drawable.ic_star_not_favourite);
                }
                else {
                    mItem.setFavourite(true);
                    fab.setImageResource(R.drawable.ic_star_favourite);
                }
            }
        });
    }

    private void prepareDetailFragment() {
        Bundle arguments = new Bundle();
        arguments.putString(ContactDetailFragment.ARG_ITEM_ID,
                getIntent().getStringExtra(ContactDetailFragment.ARG_ITEM_ID));
        arguments.putByteArray(ContactDetailFragment.ARG_ITEM_IMAGE,
                getIntent().getByteArrayExtra(ContactDetailFragment.ARG_ITEM_IMAGE));
        ContactDetailFragment fragment = new ContactDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.contact_detail_container, fragment)
                .commit();
    }
}
