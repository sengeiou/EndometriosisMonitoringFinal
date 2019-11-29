package com.benlefevre.endometriosismonitoring.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.models.Treatment;
import com.benlefevre.endometriosismonitoring.ui.viewholders.TreatmentViewHolder;

import java.util.List;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentViewHolder> {

    private List<Treatment> mTreatmentList;
    private View.OnClickListener mOnClickListener;

    public TreatmentAdapter(List<Treatment> treatments) {
        mTreatmentList = treatments;
    }

    @NonNull
    @Override
    public TreatmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.treatment_list_item,parent,false);
        TreatmentViewHolder viewHolder = new TreatmentViewHolder(view,context);
        viewHolder.itemView.findViewById(R.id.item_treatment_delete).setOnClickListener(v -> mOnClickListener.onClick(v));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TreatmentViewHolder holder, int position) {
        holder.updateUi(mTreatmentList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTreatmentList.size();
    }

    public void setOnClickListener(View.OnClickListener clickListener){
        mOnClickListener = clickListener;
    }
}
