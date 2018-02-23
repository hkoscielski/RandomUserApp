package com.example.hubson.randomuserapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.hubson.randomuserapp.dummy.DummyContent;
import com.example.hubson.randomuserapp.ui.DetailAdapter;
import com.example.hubson.randomuserapp.utils.ImageUtils;
import com.example.hubson.randomuserapp.utils.TextUtils;

public class ContactDetailFragment extends Fragment {

    private Activity activity;

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_IMAGE = "item_image";

    private DummyContent.DummyItem mItem;

    public ContactDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this.getActivity();

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            setUpCollapsingToolbarLayout();
        }
        if(getArguments().containsKey(ARG_ITEM_IMAGE)) {
            setUpImage();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_detail, container, false);

        if (mItem != null) {
            ((RecyclerView) rootView.findViewById(R.id.detailsRv)).setAdapter(new DetailAdapter(mItem));
            TextView textView = (TextView) rootView.findViewById(R.id.fullnameTv);
            final ToggleButton favouriteBtn = (ToggleButton) rootView.findViewById(R.id.favouriteBtn);
            if(textView != null) {
                textView.setText(TextUtils.capitalize(mItem.getName()));
                textView.setTextSize(16 * getResources().getDisplayMetrics().density);
            }
            if(favouriteBtn != null) {
                if(mItem.isFavourite()) {
                    favouriteBtn.setChecked(true);
                }
                else favouriteBtn.setChecked(false);
                favouriteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mItem.isFavourite()) {
                            favouriteBtn.setChecked(false);
                            mItem.setFavourite(false);
                        }
                        else {
                            favouriteBtn.setChecked(true);
                            mItem.setFavourite(true);
                        }
                    }
                });
            }
            if(getArguments().containsKey(ARG_ITEM_IMAGE)) {
                ImageView detailIv = (ImageView) rootView.findViewById(R.id.detailIv);

                byte[] b = getArguments().getByteArray(ARG_ITEM_IMAGE);
                Bitmap bmp = ImageUtils.convertByteArray(b);
                if (detailIv != null) {
                    detailIv.setImageBitmap(bmp);
                }
            }
        }

        return rootView;
    }

    private void setUpCollapsingToolbarLayout() {
        mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(TextUtils.capitalize(mItem.getName()));
            appBarLayout.setCollapsedTitleTextColor(Color.WHITE);
            appBarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorAccent));
        }
    }

    private void setUpImage() {
        ImageView detailIv = (ImageView) activity.findViewById(R.id.detailIv);

        byte[] b = getArguments().getByteArray(ARG_ITEM_IMAGE);
        Bitmap bmp = ImageUtils.convertByteArray(b);
        if(detailIv != null)
            detailIv.setImageBitmap(bmp);
    }
}
