package com.benlefevre.endometriosismonitoring.ui.viewholders;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.models.Treatment;
import com.google.android.material.textview.MaterialTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TreatmentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_treatment_name)
    MaterialTextView mName;
    @BindView(R.id.item_treatment_dosage)
    MaterialTextView mDosage;
    @BindView(R.id.item_treatment_duration)
    MaterialTextView mDuration;
    @BindView(R.id.item_treatment_hour)
    MaterialTextView mHours;
    @BindView(R.id.item_treatment_delete)
    ImageButton mDeleteBtn;

    private Context mContext;

    public TreatmentViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        mContext = context;
        mDeleteBtn.setTag(this);
    }

    public void updateUi(Treatment treatment){
        String duration = treatment.getDuration();
        String hours = "";
        mName.setText(treatment.getName());
        mDosage.setText(mContext.getString(R.string.dosage_condi,treatment.getDosage(),treatment.getConditioning()));
        if (!TextUtils.isEmpty(duration)){
            mDuration.setText(mContext.getString(R.string.until_the,duration));
        }else
            mDuration.setText(duration);
        if (!treatment.getMorningHour().equals(mContext.getString(R.string.morning)))
            hours += treatment.getMorningHour() + " / ";
        if (!treatment.getNoonHour().equals(mContext.getString(R.string.noon)))
            hours += treatment.getNoonHour() + " / ";
        if (!treatment.getAfternoonHour().equals(mContext.getString(R.string.afternoon)))
            hours += treatment.getAfternoonHour() + " / ";
        if (!treatment.getEveningHour().equals(mContext.getString(R.string.evening)))
            hours +=  treatment.getEveningHour();
        mHours.setText(hours);
    }
}
