package com.benlefevre.endometriosismonitoring.ui.viewholders;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.models.Action;
import com.google.android.material.textview.MaterialTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.action_item_name)
    MaterialTextView mActionName;
    @BindView(R.id.action_item_duration)
    MaterialTextView mActionDuration;
    @BindView(R.id.action_item_intensity)
    MaterialTextView mActionIntensity;
    @BindView(R.id.action_item_duration_legend)
    MaterialTextView mDurationLegend;
    @BindView(R.id.action_item_intensity_legend)
    MaterialTextView mIntensityLengend;
    @BindView(R.id.action_item_name_legend)
    MaterialTextView mNameLegend;

    private Context mContext;

    public ActionViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    public void updateUI(Action action) {
        setupFields(action.getName());
        if (action.getName() != null)
            mActionName.setText(action.getName());
        if (action.getDuration() != 0)
            mActionDuration.setText(String.valueOf(action.getDuration()));
        if (action.getIntensity() != 0)
            mActionIntensity.setText(String.valueOf(action.getIntensity()));
    }

    private void setupFields(String actionName) {
        if (actionName.equals(mContext.getString(R.string.sleep)) || actionName.equals(mContext.getString(R.string.stress))) {
            mDurationLegend.setVisibility(View.GONE);
            mActionDuration.setVisibility(View.GONE);
            mIntensityLengend.setVisibility(View.VISIBLE);
            mActionIntensity.setVisibility(View.VISIBLE);
        }else if( actionName.equals(mContext.getString(R.string.relaxation)) || actionName.equals(mContext.getString(R.string.sex))){
            mDurationLegend.setVisibility(View.VISIBLE);
            mActionDuration.setVisibility(View.VISIBLE);
            mIntensityLengend.setVisibility(View.GONE);
            mActionIntensity.setVisibility(View.GONE);
        }
        else {
            mDurationLegend.setVisibility(View.VISIBLE);
            mActionDuration.setVisibility(View.VISIBLE);
            mIntensityLengend.setVisibility(View.VISIBLE);
            mActionIntensity.setVisibility(View.VISIBLE);
        }
        if (actionName.equals(mContext.getString(R.string.sleep)))
            mIntensityLengend.setText(mContext.getString(R.string.sleep_quality));
        else if (actionName.equals(mContext.getString(R.string.stress)))
            mIntensityLengend.setText(mContext.getString(R.string.stress_intensity));
        else
            mIntensityLengend.setText(mContext.getString(R.string.activity_intensity));
    }
}
