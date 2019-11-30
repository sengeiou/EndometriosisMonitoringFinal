package com.benlefevre.endometriosismonitoring.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.models.Commentary;
import com.benlefevre.endometriosismonitoring.ui.viewholders.CommentaryViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommentaryAdapter extends RecyclerView.Adapter<CommentaryViewHolder> {

    private List<Commentary> mCommentaryList;
    private SimpleDateFormat mDateFormat;

    public CommentaryAdapter(List<Commentary> commentaryList) {
        mCommentaryList = commentaryList;
        mDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
    }

    @NonNull
    @Override
    public CommentaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.commentary_list_item,parent,false);
        return new CommentaryViewHolder(view, context,mDateFormat);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentaryViewHolder holder, int position) {
        holder.updateUi(mCommentaryList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCommentaryList.size();
    }
}
