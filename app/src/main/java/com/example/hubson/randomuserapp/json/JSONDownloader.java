package com.example.hubson.randomuserapp.json;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class JSONDownloader extends AsyncTask<Void, Void, List<String>> {

    private Context context;
    private String jsonURL;
    private RecyclerView rv;
    private ProgressDialog pDialog;

    public JSONDownloader(Context context, String jsonURL, RecyclerView rv) {
        this.context = context;
        this.jsonURL = jsonURL;
        this.rv = rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Download contacts..");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        List<String> jsonDatas = new ArrayList<>();
        for(int i=0; i<20; i++) {
            jsonDatas.add(downloadJSON());
        }
        return jsonDatas;
    }

    @Override
    protected void onPostExecute(List<String> jsonData) {
        super.onPostExecute(jsonData);
        if(pDialog.isShowing()) {
            pDialog.dismiss();
        }
        new JSONParser(context, jsonData, rv).execute();
    }

    private String downloadJSON() {
        Object connector = HttpConnector.connect(jsonURL);
        if(connector.toString().startsWith("Error"))
            return connector.toString();
        try {
            StringBuilder jsonData = new StringBuilder();
            HttpURLConnection connection = (HttpURLConnection) connector;
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is=new BufferedInputStream(connection.getInputStream());
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                String line;

                while ((line=br.readLine()) != null) {
                    jsonData.append(line).append("\n");
                }

                try {
                    br.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return jsonData.toString();
            }
            else return "Error "+ connection.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error "+ e.getMessage();
        }
    }
}
