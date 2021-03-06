package com.qladder.pinefruit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProviderSearchListAdapter extends ArrayAdapter <SessionInfo> {

    Context mContext;
    int mresource;
    List<SessionInfo> mSessionList;
    SessionInfo sessionInfo;


    public ProviderSearchListAdapter(Context context, int resource, List<SessionInfo> sessionList) {
        super(context, resource, sessionList);
        this.mContext = context;
        this.mresource = resource;
        this.mSessionList = sessionList;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.p_row,null);

        TextView sessionName = view.findViewById(R.id.sessionName);
        TextView sessionDetails = view.findViewById(R.id.sessionDetails);
        TextView fromTime = view.findViewById(R.id.fromtime);
        TextView toTime = view.findViewById(R.id.toTime);
        TextView sessionStatus = view.findViewById(R.id.sessionStatus);
        ImageView imageView = view.findViewById(R.id.providerimage);


        sessionInfo = mSessionList.get(position);
        sessionName.setText(sessionInfo.providerName);
        sessionDetails.setText(sessionInfo.sessionName);
        fromTime.setText(sessionInfo.mfromtime);
        toTime.setText(sessionInfo.mtotime);
        sessionStatus.setText(sessionInfo.sessionStatus);
        // using only a standard image for test purpose.
        // this has to be replaced by
        // image array or initial or date or session id which i have to decide later
        imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.doctor));



        return view;


    }

}
