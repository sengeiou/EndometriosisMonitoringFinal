package com.benlefevre.endometriosismonitoring.ui.viewholders;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.models.Commentary;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentaryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.comment_author_photo)
    ImageView mPhoto;
    @BindView(R.id.comment_author_name)
    MaterialTextView mName;
    @BindView(R.id.comment_date)
    MaterialTextView mDate;
    @BindView(R.id.comment_txt)
    MaterialTextView mCommentText;
    @BindView(R.id.commentary_star1)
    ImageView mStar1;
    @BindView(R.id.commentary_star2)
    ImageView mStar2;
    @BindView(R.id.commentary_star3)
    ImageView mStar3;
    @BindView(R.id.commentary_star4)
    ImageView mStar4;
    @BindView(R.id.commentary_star5)
    ImageView mStar5;

    private Context mContext;
    private SimpleDateFormat mDateFormat;

    public CommentaryViewHolder(@NonNull View itemView, Context context, SimpleDateFormat dateFormat) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
        mDateFormat = dateFormat;
    }

    public void updateUi(Commentary commentary) {
        if (commentary.getAuthorPhoto() != null) {
            Glide.with(mContext).load(Uri.parse(commentary.getAuthorPhoto()))
                    .apply(RequestOptions.circleCropTransform()).into(mPhoto);
        } else
            Glide.with(mContext).load(R.drawable.girl2)
                    .apply(RequestOptions.circleCropTransform()).into(mPhoto);
        mName.setText(commentary.getAuthorName());
        mDate.setText(mDateFormat.format(commentary.getDate()));
        mCommentText.setText(commentary.getUserInput());
        setRatingStar(commentary.getRating());
    }

    private void setRatingStar(int rating) {
        switch (rating) {
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
