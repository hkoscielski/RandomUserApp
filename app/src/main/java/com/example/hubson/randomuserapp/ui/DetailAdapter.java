package com.example.hubson.randomuserapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hubson.randomuserapp.R;
import com.example.hubson.randomuserapp.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private static List<Integer> icons = new ArrayList<>();
    private static List<String> titles = new ArrayList<>();
    private List<String> details;

    static {
        icons.add(R.drawable.ic_gender);
        icons.add(R.drawable.ic_phone);
        icons.add(R.drawable.ic_email);
        icons.add(R.drawable.ic_street);
        icons.add(R.drawable.ic_city);
        icons.add(R.drawable.ic_state);
        icons.add(R.drawable.ic_postcode);

        titles.add("Gender");
        titles.add("Phone number");
        titles.add("Email");
        titles.add("Street");
        titles.add("City");
        titles.add("State");
        titles.add("Postcode");
    }

    public DetailAdapter(DummyContent.DummyItem contact) {
        initDetailContent(contact);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_detail_content, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImage.setImageResource(icons.get(position));
        holder.mTitle.setText(titles.get(position));
        holder.mDetail.setText(details.get(position));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    private void initDetailContent(DummyContent.DummyItem contact) {
        details = new ArrayList<>();
        details.add(contact.getGender());
        details.add(contact.getPhone());
        details.add(contact.getEmail());
        details.add(contact.getStreet());
        details.add(contact.getCity());
        details.add(contact.getState());
        details.add(contact.getPostcode());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView mImage;
        final TextView mTitle;
        final TextView mDetail;

        ViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.detailImage);
            mTitle = (TextView) itemView.findViewById(R.id.detailTitleTv);
            mDetail = (TextView) itemView.findViewById(R.id.detailContentTv);
        }
    }
}


