package com.goalsr.homequarantineTracker.ui;

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

import java.util.ArrayList;

/**
 * Created by Murugeshwaran M on 31-03-2020
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private ArrayList<SymtomModel> listString;
    private Context context;
    private CheckedListener listener;

    public ListAdapter(Context context, ArrayList<SymtomModel> listString){
        this.context = context;
        this.listString = listString;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.content_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, final int position) {
        holder.tvView.setText(listString.get(position).getStrname());
        holder.imageicon.setImageResource(listString.get(position).getIconamker());
        /*if (listString.get(position).equalsIgnoreCase("Fever")){
            holder.imageicon.setImageResource(R.mipmap.ic_fever);
        }else  if (listString.get(position).equalsIgnoreCase("Cough")){
            holder.imageicon.setImageResource(R.mipmap.ic_fever);
        }else  if (listString.get(position).equalsIgnoreCase("cold")){
            holder.imageicon.setImageResource(R.mipmap.ic_fever);
        }else  if (listString.get(position).equalsIgnoreCase("Tiredness")){
            holder.imageicon.setImageResource(R.mipmap.ic_fever);
        }else  if (listString.get(position).equalsIgnoreCase("Other")){
            holder.imageicon.setImageResource(R.mipmap.ic_fever);
            if

        }*/

        holder.tvView.setTextColor(holder.chkBox.isChecked()? ContextCompat.getColor(context, R.color.colorselect):ContextCompat.getColor(context, R.color.colornormal));
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
        });
    }

    @Override
    public int getItemCount() {
        return listString.size();
    }

    public void setListener(CheckedListener listener) {
        this.listener = listener;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        CheckBox chkBox;
        TextView tvView;
        LinearLayout llChk;
        ImageView imageicon;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            chkBox = itemView.findViewById(R.id.chk_box);
            tvView = itemView.findViewById(R.id.chk_box_txt);
            llChk = itemView.findViewById(R.id.ll_chk);
            imageicon=itemView.findViewById(R.id.imageicon);

        }
    }

    public interface CheckedListener{
        void onItemChecked(View v, int position, SymtomModel item, ArrayList<SymtomModel> listString, boolean isChecked);
    }

    public void setValue(ArrayList<SymtomModel> list){
        this.listString.clear();
        this.listString.addAll(list);
        notifyDataSetChanged();
    }
}
