package com.example.jamnagarwelfareschemes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SchemesStatusAdapter extends RecyclerView.Adapter<SSViewHolder> {

    Context context;
    List<SchemesStatusData> schemesStatusData;

    public SchemesStatusAdapter(Context context, List<SchemesStatusData> schemesStatusData) {
        this.context = context;
        this.schemesStatusData = schemesStatusData;
    }

    @NonNull
    @Override
    public SSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_recycle, parent, false);

        return new SSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SSViewHolder holder, int position) {

        String schemeName = schemesStatusData.get(position).getSchemeName();
        String query = schemesStatusData.get(position).getQuery();
        String catagory = schemesStatusData.get(position).getSchemeCatagory();
        String applicationId = schemesStatusData.get(position).getApplicationId();
        String transaction = schemesStatusData.get(position).getTransaction();
        String status = schemesStatusData.get(position).getStatus();

        SpannableString spannableString1 = new SpannableString(schemeName);
        spannableString1.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, schemeName.length(), 0);
        SpannableString spannableString2 = new SpannableString(query);
        spannableString2.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, query.length(), 0);
        SpannableString spannableString3 = new SpannableString(catagory);
        spannableString3.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, catagory.length(), 0);
        SpannableString spannableString4 = new SpannableString(applicationId);
        spannableString4.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, applicationId.length(), 0);
        if (transaction != null) {
            SpannableString spannableString5 = new SpannableString(transaction);
            spannableString5.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, transaction.length(), 0);
            holder.Transaction.setText(spannableString5);
        }
        SpannableString spannableString6 = new SpannableString(status);
        spannableString6.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, status.length(), 0);

        holder.SchemeName.setText(spannableString1);
        holder.Catagory.setText(spannableString3);
        holder.Status.setText(spannableString6);
        holder.Query.setText(spannableString2);
        holder.ApplicationId.setText(spannableString4);

        if (status.equals("Approved")) {
//            holder.LLQuery.setVisibility(View.INVISIBLE);
            holder.Transaction.setVisibility(View.VISIBLE);
            holder.edit.setVisibility(View.GONE);
            holder.Status.setTextColor(ContextCompat.getColor(context, R.color.green));
        }
        if (status.equals("Rejected")) {
            holder.LLTransaction.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
            holder.Status.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        if (status.equals("Submitted")) {
            holder.LLTransaction.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
            holder.Status.setTextColor(ContextCompat.getColor(context, R.color.blue));
        }
        if (status.equals("Query")) {
            holder.LLTransaction.setVisibility(View.GONE);
            holder.edit.setVisibility(View.VISIBLE);
            holder.Status.setTextColor(ContextCompat.getColor(context, R.color.query));
        }
        if (status.equals("Application Started")) {
            holder.LLTransaction.setVisibility(View.GONE);
            holder.edit.setVisibility(View.VISIBLE);
            holder.Status.setTextColor(ContextCompat.getColor(context, R.color.orange));
        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context1 = v.getContext();
                Intent intent = new Intent(context1, SchemeApplication.class);
                intent.putExtra("key", "SchemeStatusAdapter");
                intent.putExtra("schemeCatagory", schemesStatusData.get(holder.getAdapterPosition()).getSchemeCatagory());
                intent.putExtra("schemeName", schemesStatusData.get(holder.getAdapterPosition()).getSchemeName());
                intent.putExtra("applicationId", schemesStatusData.get(holder.getAdapterPosition()).getApplicationId());
                context1.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return schemesStatusData.size();
    }
}

class SSViewHolder extends RecyclerView.ViewHolder {

    LinearLayout LLQuery, LLTransaction;
    TextView SchemeName, Catagory, ApplicationId, Status, Query, Transaction;
    ImageView edit;

    public SSViewHolder(@NonNull View itemView) {
        super(itemView);

        LLQuery = itemView.findViewById(R.id.LLQuery);
        SchemeName = itemView.findViewById(R.id.txtStatusSchemeName);
        Catagory = itemView.findViewById(R.id.txtStatusSchemeCatagory);
        ApplicationId = itemView.findViewById(R.id.txtStatusAppID);
        Status = itemView.findViewById(R.id.txtAppStatus);
        Query = itemView.findViewById(R.id.txtStatusQuery);
        edit = itemView.findViewById(R.id.imgEdit);
        LLTransaction = itemView.findViewById(R.id.LLTransaction);
        Transaction = itemView.findViewById(R.id.txtStatusTransaction);
    }
}