package com.example.jamnagarwelfareschemes;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HViewHolder> {
    Context context;
    List<SchemesStatusData> schemesStatusData;

    public HistoryAdapter(Context context, List<SchemesStatusData> schemesStatusData){
        this.context = context;
        this.schemesStatusData = schemesStatusData;
    }
    @NonNull
    @Override
    public HViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycle,parent,false);

        return new HViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HViewHolder holder, int position) {

        String schemeName = schemesStatusData.get(position).getSchemeName();
        String query = schemesStatusData.get(position).getQuery();
        String catagory = schemesStatusData.get(position).getSchemeCatagory();
        String applicationId = schemesStatusData.get(position).getApplicationId();
        String transaction = schemesStatusData.get(position).getTransaction();
        String status = schemesStatusData.get(position).getStatus();
        String doa = schemesStatusData.get(position).getDateOfApplication();

        SpannableString spannableString1 = new SpannableString(schemeName);
        spannableString1.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, schemeName.length(), 0);
        SpannableString spannableString2 = new SpannableString(query);
        spannableString2.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, query.length(), 0);
        SpannableString spannableString3 = new SpannableString(catagory);
        spannableString3.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, catagory.length(), 0);
        SpannableString spannableString4 = new SpannableString(applicationId);
        spannableString4.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, applicationId.length(), 0);
        if(transaction!=null) {
            SpannableString spannableString5 = new SpannableString(transaction);
            spannableString5.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, transaction.length(), 0);
            holder.Transaction.setText(spannableString5);
        }
        SpannableString spannableString6 = new SpannableString(doa);
        spannableString6.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, doa.length(), 0);

        holder.SchemeName.setText(spannableString1);
        holder.Catagory.setText(spannableString3);
        holder.DOA.setText(spannableString6);
        holder.Query.setText(spannableString2);
        holder.ApplicationId.setText(spannableString4);

        if(status.equals("Approved")){
            holder.Transaction.setVisibility(View.VISIBLE);
        }
        if (status.equals("Rejected")){
            holder.LLTransaction.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return schemesStatusData.size();
    }

    public void clearData() {
        schemesStatusData.clear();
        notifyDataSetChanged();
    }

//    public void setData(List<SchemesStatusData> data) {
//        schemesStatusData.clear();
//        schemesStatusData.addAll(data);
//        notifyDataSetChanged();
//    }
}

class HViewHolder extends RecyclerView.ViewHolder{

    LinearLayout LLQuery, LLTransaction;
    TextView SchemeName, Catagory, ApplicationId, DOA, Query, Transaction;
    public HViewHolder(@NonNull View itemView) {
        super(itemView);

        LLQuery = itemView.findViewById(R.id.LLHistoryQuery);
        SchemeName = itemView.findViewById(R.id.txtHistorySchemeName);
        Catagory = itemView.findViewById(R.id.txtHistorySchemeCatagory);
        ApplicationId = itemView.findViewById(R.id.txtHistoryAppID);
        DOA = itemView.findViewById(R.id.txtDOAHistory);
        Query = itemView.findViewById(R.id.txtHistoryQuery);
        LLTransaction = itemView.findViewById(R.id.LLHistoryTransaction);
        Transaction = itemView.findViewById(R.id.txtHistoryTransaction);
    }

}
