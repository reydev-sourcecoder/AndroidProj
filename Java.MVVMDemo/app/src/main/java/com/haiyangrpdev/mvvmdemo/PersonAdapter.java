package com.haiyangrpdev.mvvmdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {

    private List<Person> allPersons = new ArrayList<>();

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.person_item, parent, false);
        return new PersonHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
        Person currentPerson = allPersons.get(position);
        holder.tvName.setText(currentPerson.getName());
        holder.tvEmail.setText(currentPerson.getEmail());
    }

    @Override
    public int getItemCount() {
        return allPersons.size();
    }

    public void setPersons(List<Person> persons) {
        this.allPersons = persons;
        notifyDataSetChanged();
    }

    public Person getPersonAt(int position) {
        return allPersons.get(position);
    }

    class PersonHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvEmail;

        public PersonHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}
