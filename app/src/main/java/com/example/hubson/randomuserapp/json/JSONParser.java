package com.example.hubson.randomuserapp.json;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.hubson.randomuserapp.dummy.DummyContent;
import com.example.hubson.randomuserapp.ui.ContactAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JSONParser extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private List<String> jsonData;
    private RecyclerView rv;
    private ProgressDialog pDialog;

    public JSONParser(Context context, List<String> jsonData, RecyclerView rv) {
        this.context = context;
        this.jsonData = jsonData;
        this.rv = rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading contacts..");
        pDialog.show();
        DummyContent.clearData();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return parse();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);
        if(pDialog.isShowing()) {
            pDialog.dismiss();
        }
        if(isParsed) {
            ContactAdapter adapter = new ContactAdapter(context, DummyContent.ITEMS);
            rv.setAdapter(adapter);
        }
        else {
            Toast.makeText(context, "Unable To Parse,Check Your Log output", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean parse() {
        try {
            for(int j = 0; j < jsonData.size(); j++) {
                JSONObject jsonObject = new JSONObject(jsonData.get(j));
                JSONArray contacts = jsonObject.getJSONArray("results");

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String gender = c.getString("gender");
                    String phone = c.getString("phone");
                    String email = c.getString("email");

                    JSONObject name = c.getJSONObject("name");
                    String title = name.getString("title");
                    String first = name.getString("first");
                    String last = name.getString("last");
                    String fullname = title + " " + first + " " + last;

                    JSONObject location = c.getJSONObject("location");
                    String street = location.getString("street");
                    String city = location.getString("city");
                    String state = location.getString("state");
                    String postcode = location.getString("postcode");

                    JSONObject picture = c.getJSONObject("picture");
                    String large = picture.getString("large");

                    DummyContent.DummyItem contact = new DummyContent.DummyItem(String.valueOf(j + 1), fullname,
                            gender, phone, email, street, city, state, postcode, large);

                    DummyContent.addItem(contact);
                }
            }
            return true;
        } catch (final JSONException e) {
            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,
                            "JSON parsing error: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        }
    }
    }


