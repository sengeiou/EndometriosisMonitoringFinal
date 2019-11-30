package com.benlefevre.endometriosismonitoring.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.models.Doctor;
import com.benlefevre.endometriosismonitoring.ui.viewholders.DoctorViewHolder;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorViewHolder> {

    private List<Doctor> mDoctorList;
    private View.OnClickListener mOnClickListener;

    public DoctorAdapter(List<Doctor> doctorList) {
        mDoctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_list_item,parent,false);
        DoctorViewHolder doctorViewHolder = new DoctorViewHolder(view, context);
        doctorViewHolder.itemView.setOnClickListener(view1 -> mOnClickListener.onClick(view1));
        return doctorViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = mDoctorList.get(position);
        holder.updateUi(doctor);
    }

    @Override
    public int getItemCount() {
        return mDoctorList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        mOnClickListener = listener;
    }
}
