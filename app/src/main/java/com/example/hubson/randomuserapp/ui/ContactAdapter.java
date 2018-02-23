package com.example.hubson.randomuserapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hubson.randomuserapp.ContactDetailActivity;
import com.example.hubson.randomuserapp.ContactDetailFragment;
import com.example.hubson.randomuserapp.ContactListActivity;
import com.example.hubson.randomuserapp.R;
import com.example.hubson.randomuserapp.dummy.DummyContent;
import com.example.hubson.randomuserapp.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<DummyContent.DummyItem> contacts;
    private List<DummyContent.DummyItem> filteredContacts;
    private ItemFilter filters;

    public ContactAdapter(Context context, List<DummyContent.DummyItem> contacts) {
        this.context = context;
        this.contacts = contacts;
        this.filteredContacts = contacts;
        filters = new ItemFilter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_content, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final DummyContent.DummyItem contact = filteredContacts.get(position);
        Glide.with(context).load(contact.getPhotoURL()).into(holder.mImage);
        holder.mName.setText(contact.getName());
        holder.mGender.setText(contact.getGender());
        holder.mEmail.setText(contact.getEmail());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactListActivity context = (ContactListActivity) v.getContext();
                Drawable image = holder.mImage.getDrawable();

                if (context.isTwoPane()) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ContactDetailFragment.ARG_ITEM_ID, contact.getId());
                    arguments.putByteArray(ContactDetailFragment.ARG_ITEM_IMAGE, ImageUtils.convertDrawable(image));
                    ContactDetailFragment fragment = new ContactDetailFragment();
                    fragment.setArguments(arguments);
                    context.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contact_detail_container, fragment)
                            .commit();
                }
                else {
                    Intent intent = new Intent(context, ContactDetailActivity.class);
                    intent.putExtra(ContactDetailFragment.ARG_ITEM_ID, contact.getId());
                    intent.putExtra(ContactDetailFragment.ARG_ITEM_IMAGE, ImageUtils.convertDrawable(image));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredContacts.size();
    }

    @Override
    public Filter getFilter() {
        return filters;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView mImage;
        final TextView mName;
        final TextView mGender;
        final TextView mEmail;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mImage = (ImageView) itemView.findViewById(R.id.imageView);
            mName = (TextView) itemView.findViewById(R.id.nameTv);
            mGender = (TextView) itemView.findViewById(R.id.genderTv);
            mEmail = (TextView) itemView.findViewById(R.id.emailTv);
        }
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String query = charSequence.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<DummyContent.DummyItem> original = contacts;
            final List<DummyContent.DummyItem> filtered = new ArrayList<>(original.size());
            for(DummyContent.DummyItem contact : original) {
                if (contact.getName().toLowerCase().contains(query)
                        || contact.getGender().toLowerCase().contains(query)
                        || contact.getEmail().toLowerCase().contains(query)) {
                    filtered.add(contact);
                }
            }
            results.values = filtered;
            results.count = filtered.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredContacts = (List<DummyContent.DummyItem>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
