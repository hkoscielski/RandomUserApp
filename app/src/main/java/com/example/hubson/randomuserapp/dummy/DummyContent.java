package com.example.hubson.randomuserapp.dummy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.hubson.randomuserapp.json.JSONDownloader;

public class DummyContent {

    public static final List<DummyItem> ITEMS = new ArrayList<>();
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<>();

    public static void downloadAndSetData(Context context, String jsonUrl, RecyclerView rv) {
        new JSONDownloader(context, jsonUrl, rv).execute();
    }

    public static void clearData() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static List<DummyItem> getAll() {
        return ITEMS;
    }

    public static List<DummyItem> getFavourites() {
        List<DummyContent.DummyItem> favourites = new ArrayList<>(DummyContent.ITEMS.size());
        for(DummyContent.DummyItem contact : DummyContent.ITEMS)
            if(contact.isFavourite())
                favourites.add(contact);
        return favourites;
    }

    public static class DummyItem {
        private final String id;
        private final String name;
        private final String gender;
        private final String phone;
        private final String email;
        private final String street;
        private final String city;
        private final String state;
        private final String postcode;
        private final String photoURL;
        private boolean isFavourite;

        public DummyItem(String id, String name, String gender, String phone, String email,
                         String street, String city, String state, String postcode, String photoURL) {
            this.id = id;
            this.name = name;
            this.gender = gender;
            this.phone = phone;
            this.email = email;
            this.street = street;
            this.city = city;
            this.state = state;
            this.postcode = postcode;
            this.photoURL = photoURL;
            this.isFavourite = false;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getStreet() {
            return street;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getPostcode() {
            return postcode;
        }

        public String getPhotoURL() {
            return photoURL;
        }

        public boolean isFavourite() {
            return isFavourite;
        }

        public void setFavourite(boolean favourite) {
            isFavourite = favourite;
        }
    }
}
