package com.benlefevre.endometriosismonitoring.ui.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.models.Doctor;
import com.google.android.material.textview.MaterialTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoctorViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.doctor_item_name)
    MaterialTextView mDoctorName;
    @BindView(R.id.doctor_item_specialisation)
    MaterialTextView mDoctorSpec;
    @BindView(R.id.doctor_item_address)
    MaterialTextView mDoctorAddress;
    @BindView(R.id.doctor_item_telephone)
    MaterialTextView mDoctorTelephone;
    @BindView(R.id.doctor_item_convention)
    MaterialTextView mDoctorConvention;
    @BindView(R.id.doctor_item_commentary)
    MaterialTextView mDoctorCommentary;
    @BindView(R.id.doctor_item_btn)
    MaterialTextView mDetailsText;
    @BindView(R.id.doctor_star1)
    ImageView mStar1;
    @BindView(R.id.doctor_star2)
    ImageView mStar2;
    @BindView(R.id.doctor_star3)
    ImageView mStar3;
    @BindView(R.id.doctor_star4)
    ImageView mStar4;
    @BindView(R.id.doctor_star5)
    ImageView mStar5;

    private Context mContext;

    public DoctorViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        mContext = context;
        itemView.setTag(this);
    }

    public void updateUi(Doctor doctor){
        mDetailsText.setVisibility(View.GONE);
        mDoctorName.setText(doctor.getName());
        mDoctorSpec.setText(doctor.getSpec());
        mDoctorAddress.setText(doctor.getAddress());
        mDoctorConvention.setText(doctor.getConvention());
        if (doctor.getPhone() != null){
            mDoctorTelephone.setVisibility(View.VISIBLE);
            mDoctorTelephone.setText(doctor.getPhone());
        }else
            mDoctorTelephone.setVisibility(View.GONE);

        if (doctor.getCommentaries() != 0){
            mDoctorCommentary.setText(mContext.getString(R.string.comment_doctor, doctor.getCommentaries()));
            setRatingStar(doctor);
        }else {
            mDoctorCommentary.setText(mContext.getString(R.string.no_commentaries));
            mStar1.setVisibility(View.GONE);
            mStar2.setVisibility(View.GONE);
            mStar3.setVisibility(View.GONE);
            mStar4.setVisibility(View.GONE);
            mStar5.setVisibility(View.GONE);
        }
    }

    public void setDetailsButtonVisibility(){
        mDetailsText.setVisibility(View.VISIBLE);
    }

    private void setRatingStar(Doctor doctor){
        mStar1.setVisibility(View.VISIBLE);
        mStar2.setVisibility(View.VISIBLE);
        mStar3.setVisibility(View.VISIBLE);
        mStar4.setVisibility(View.VISIBLE);
        mStar5.setVisibility(View.VISIBLE);
        int average = doctor.getRating() / doctor.getCommentaries();
        switch (average) {
            case 0:
                mStar1.setImageResource(R.drawable.star_empty);
                mStar2.setImageResource(R.drawable.star_empty);
                mStar3.setImageResource(R.drawable.star_empty);
                mStar4.setImageResource(R.drawable.star_empty);
                mStar5.setImageResource(R.drawable.star_empty);
                break;
            case 1:
                mStar1.setImageResource(R.drawable.star_full);
                mStar2.setImageResource(R.drawable.star_empty);
                mStar3.setImageResource(R.drawable.star_empty);
                mStar4.setImageResource(R.drawable.star_empty);
                mStar5.setImageResource(R.drawable.star_empty);
                break;
            case 2:
                mStar1.setImageResource(R.drawable.star_full);
                mStar2.setImageResource(R.drawable.star_full);
                mStar3.setImageResource(R.drawable.star_empty);
                mStar4.setImageResource(R.drawable.star_empty);
                mStar5.setImageResource(R.drawable.star_empty);
                break;
            case 3:
                mStar1.setImageResource(R.drawable.star_full);
                mStar2.setImageResource(R.drawable.star_full);
                mStar3.setImageResource(R.drawable.star_full);
                mStar4.setImageResource(R.drawable.star_empty);
                mStar5.setImageResource(R.drawable.star_empty);
                break;
            case 4:
                mStar1.setImageResource(R.drawable.star_full);
                mStar2.setImageResource(R.drawable.star_full);
                mStar3.setImageResource(R.drawable.star_full);
                mStar4.setImageResource(R.drawable.star_full);
                mStar5.setImageResource(R.drawable.star_empty);
                break;
            case 5:
                mStar1.setImageResource(R.drawable.star_full);
                mStar2.setImageResource(R.drawable.star_full);
                mStar3.setImageResource(R.drawable.star_full);
                mStar4.setImageResource(R.drawable.star_full);
                mStar5.setImageResource(R.drawable.star_full);
                break;
        }
    }
}
