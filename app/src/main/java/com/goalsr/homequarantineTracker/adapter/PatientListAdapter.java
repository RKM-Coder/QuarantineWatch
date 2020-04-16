package com.goalsr.homequarantineTracker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.resposemodel.DistrictModel;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Murugeshwaran M on 31-03-2020
 */
public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ListViewHolder> implements Filterable {

    private ArrayList<ResPatientInfo> listString;
    private ArrayList<ResPatientInfo> listmain;
    private Context context;
    private CheckedListener listener;
    List<String> famillyrelation = new ArrayList<>();
    public PatientListAdapter(Context context, ArrayList<ResPatientInfo> listString){
        this.context = context;
        this.listString = listString;
        this.listmain = listString;
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
        holder.tvRelation.setText(listString.get(position).getMobile());
        Log.e("INFOMOBILE",""+listString.get(position).getMobile());

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
        void onItemCheckedFamilly(int position, ResPatientInfo item);
    }

    public void setValue(ArrayList<ResPatientInfo> list){
        this.listString.clear();
        this.listmain.clear();
        //this.listString.addAll(list);
        this.listmain.addAll(list);
//        Log.e("PatientList--",listString.size()+"");
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listString = listmain;
                } else {
                    ArrayList<ResPatientInfo> filteredList = new ArrayList<>();
                    for (ResPatientInfo row : listmain) {

                        if (row.getName()!=null || row.getMobile()!=null) {

                            if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getMobile().contains(charString)) {
                                filteredList.add(row);

                            }
                        }

                    }

                    listString = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listString;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listString = (ArrayList<ResPatientInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
