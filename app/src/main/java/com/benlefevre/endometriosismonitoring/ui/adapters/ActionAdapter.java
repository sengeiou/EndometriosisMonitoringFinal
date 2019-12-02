package com.benlefevre.endometriosismonitoring.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.models.Action;
import com.benlefevre.endometriosismonitoring.ui.viewholders.ActionViewHolder;

import java.util.List;

public class ActionAdapter extends RecyclerView.Adapter<ActionViewHolder> {
    private List<Action> mActionList;

    public ActionAdapter(List<Action> actionList) {
        mActionList = actionList;
    }

    @NonNull
    @Override
    public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.action_list_item,parent,false);
        return new ActionViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {
        holder.updateUI(mActionList.get(position));
    }

    @Override
    public int getItemCount() {
        return mActionList.size();
    }
}
