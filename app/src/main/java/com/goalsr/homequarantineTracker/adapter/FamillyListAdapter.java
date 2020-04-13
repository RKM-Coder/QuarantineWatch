package com.goalsr.homequarantineTracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.ui.SymtomModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Murugeshwaran M on 31-03-2020
 */
public class FamillyListAdapter extends RecyclerView.Adapter<FamillyListAdapter.ListViewHolder> {

    private ArrayList<ResPatientFamilyInfo> listString;
    private Context context;
    private CheckedListener listener;
    List<String> famillyrelation = new ArrayList<>();
    public FamillyListAdapter(Context context, ArrayList<ResPatientFamilyInfo> listString){
        this.context = context;
        this.listString = listString;
        String genderarray[] = context.getResources().getStringArray(R.array.relation);

        famillyrelation = new ArrayList<>(Arrays.asList(genderarray));
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.list_item_familly, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        holder.tvViewNAme.setText(listString.get(position).getName());
        if (listString.get(position).getRelationId()==0){
            holder.tvRelation.setText("No relation available");
        }else {
            holder.tvRelation.setText(famillyrelation.get(listString.get(position).getRelationId()));
        }


       /* holder.tvView.setTextColor(holder.chkBox.isChecked()? ContextCompat.getColor(context, R.color.colorselect):ContextCompat.getColor(context, R.color.colornormal));
        holder.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.tvView.setTextColor(holder.chkBox.isChecked()? ContextCompat.getColor(context, R.color.colorselect):ContextCompat.getColor(context, R.color.colornormal));
                listener.onItemChecked(buttonView, position, listString.get(position), listString, isChecked);
            }
        });
        holder.llChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvView.setTextColor(holder.chkBox.isChecked()? ContextCompat.getColor(context, R.color.colorselect):ContextCompat.getColor(context, R.color.colornormal));
                holder.chkBox.setChecked(!holder.chkBox.isChecked());
            }
        });*/

        holder.ll_main_familly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemCheckedFamilly(position,listString.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listString != null) {
            return listString.size();
        } else {
            return 0;
        }
    }

    public void setListener(CheckedListener listener) {
        this.listener = listener;
    }

   public class ListViewHolder extends RecyclerView.ViewHolder {


        TextView tvViewNAme,tvRelation;
        LinearLayout ll_main_familly;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvViewNAme = itemView.findViewById(R.id.txtname);
            tvRelation = itemView.findViewById(R.id.txtrelation);
            ll_main_familly = itemView.findViewById(R.id.ll_main_familly);


        }
    }

    public interface CheckedListener{
        void onItemCheckedFamilly(int position, ResPatientFamilyInfo item );
    }

    public void setValue(ArrayList<ResPatientFamilyInfo> list){
        this.listString.clear();
        this.listString.addAll(list);
        notifyDataSetChanged();
    }
}
