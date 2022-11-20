package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.R;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Patient;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class PatientRecyclerViewAdapter extends RecyclerView.Adapter<PatientRecyclerViewAdapter.ViewHolder> {

    private final List<Patient> patientArrayList;
    private final Context context;
    private static OnItemClickListener itemClickListener = null;
    public PatientRecyclerViewAdapter(Context context, List<Patient> patientArrayList,
                                      OnItemClickListener itemClickListener) {
        this.context = context;
        this.patientArrayList = patientArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public PatientRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientRecyclerViewAdapter.ViewHolder holder, int position) {
        Patient patient = patientArrayList.get(position);
        holder.patientIDText.setText(String.valueOf(patient.getPatientID()));
        holder.patientFirstNameText.setText(patient.getFirstName());
        holder.patientLastNameText.setText(patient.getLastName());
    }

    @Override
    public int getItemCount() {
        return patientArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView patientIDText;
        TextView patientFirstNameText;
        TextView patientLastNameText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            patientIDText = itemView.findViewById(R.id.card_patient_id);
            patientFirstNameText = itemView.findViewById(R.id.card_patient_first_name);
            patientLastNameText = itemView.findViewById(R.id.card_patient_last_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemClickListener != null)
                itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
