package com.example.jamnagarwelfareschemes;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CatagorySchemesAdapter extends RecyclerView.Adapter<CSViewHolder> {

    Context context;
    List<SchemeData> schemeDataList;

    public CatagorySchemesAdapter(Context context, List<SchemeData> schemeDataList){
        this.context = context;
        this.schemeDataList = schemeDataList;
    }

    @NonNull
    @Override
    public CSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scheme_recycle,parent,false);

        return new CSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CSViewHolder holder, int position) {

        String eligibilityCriteria = schemeDataList.get(position).getEligibility();
        String schemeName = schemeDataList.get(position).getName();

        // Create a SpannableString from the text
        SpannableString spannableString1 = new SpannableString(eligibilityCriteria);
        // Apply AlignmentSpan to justify the text
        spannableString1.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, eligibilityCriteria.length(), 0);
        // Set the modified SpannableString back to TextView
        holder.eligibilitycriteria.setText(spannableString1);

        SpannableString spannableString2 = new SpannableString(schemeName);
        spannableString2.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, schemeName.length(), 0);
        holder.schemeName.setText(spannableString2);
//        holder.schemeName.setText(schemeDataList.get(position).getName());
//        holder.eligibilitycriteria.setText(schemeDataList.get(position).getEligibility());
        holder.deadline.setText(schemeDataList.get(position).getDeadline());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context1 = v.getContext();
                Intent intent = new Intent(context1, CatagorySchemeDetail.class);
                intent.putExtra("schemeName",schemeDataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("schemeDetail",schemeDataList.get(holder.getAdapterPosition()).getDetail());
                intent.putExtra("schemeEligibility",schemeDataList.get(holder.getAdapterPosition()).getEligibility());
                intent.putExtra("schemeDeadline",schemeDataList.get(holder.getAdapterPosition()).getDeadline());
                intent.putExtra("schemeCatagory",schemeDataList.get(holder.getAdapterPosition()).getCatagories());
                intent.putExtra("schemeDocument", String.valueOf(schemeDataList.get(holder.getAdapterPosition()).getDocument()));
                context1.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return schemeDataList.size();
    }
}

class CSViewHolder extends RecyclerView.ViewHolder{

    TextView schemeName, eligibilitycriteria, deadline;
    Button button;
    public CSViewHolder(@NonNull View itemView) {
        super(itemView);

        schemeName = itemView.findViewById(R.id.txtSchemeName);
        eligibilitycriteria = itemView.findViewById(R.id.txtEligibilityCriteria);
        deadline = itemView.findViewById(R.id.txtDeadline);
        button = itemView.findViewById(R.id.btnKnowMoreSchemes);
    }
}