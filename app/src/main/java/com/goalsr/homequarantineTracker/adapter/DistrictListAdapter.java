package com.goalsr.homequarantineTracker.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.resposemodel.DistrictModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Ram
 */

public class DistrictListAdapter extends RecyclerView.Adapter<DistrictListAdapter.HorizontalViewHolder> implements Filterable {

    public Context mContext;
    ArrayList<DistrictModel> list;
    ArrayList<DistrictModel> listmain;




    private int intColor;
    private OnClickMainView listener;


    public DistrictListAdapter(Context mContext, ArrayList<DistrictModel> list) {
        this.mContext = mContext;
        this.list = list;
        this.listmain = list;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_district, parent, false);
        return new HorizontalViewHolder(view);
    }

    public void addall(ArrayList<DistrictModel> getVendors) {
        this.list = getVendors;
        this.listmain = getVendors;
        notifyDataSetChanged();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, final int position) {



        holder.txtname.setText("" + list.get(position).getDISTRICT_NAME());

        holder.llMainFamilly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickMain(position, list.get(position));
            }
        });



    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    class HorizontalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtname)
        TextView txtname;
        @BindView(R.id.txtrelation)
        TextView txtrelation;
        @BindView(R.id.ll_main_familly)
        LinearLayout llMainFamilly;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnclickListener(OnClickMainView listener) {
        this.listener = listener;
    }

    public interface OnClickMainView {
        void onClickMain(int position, DistrictModel requestbodyVendor);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    list = listmain;
                } else {
                    ArrayList<DistrictModel> filteredList = new ArrayList<>();
                    for (DistrictModel row : listmain) {

                        if (row.getDISTRICT_NAME().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);

                        }
                    }

                    list = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<DistrictModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
