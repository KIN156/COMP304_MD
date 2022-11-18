package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.R;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Test;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.interfaces.OnItemClickListener;

import java.util.List;

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Test> testList;
    private static OnItemClickListener itemClickListener = null;

    public TestRecyclerViewAdapter(Context context, List<Test> testList,
                                   OnItemClickListener itemClickListener) {
        this.context = context;
        this.testList = testList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public TestRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestRecyclerViewAdapter.ViewHolder holder, int position) {
        Test test = testList.get(position);
        holder.bplText.setText(String.valueOf(test.getBpl()));
        holder.bphText.setText(String.valueOf(test.getBph()));
        holder.temperatureText.setText(String.valueOf(test.getTemperature()));
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView bplText;
        TextView bphText;
        TextView temperatureText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bplText = itemView.findViewById(R.id.card_test_bpl);
            bphText = itemView.findViewById(R.id.card_test_bph);
            temperatureText = itemView.findViewById(R.id.card_test_temp);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(itemClickListener != null)
                itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
