package com.goalsr.homequarantineTracker.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.resposemodel.DistrictModel;
import com.goalsr.homequarantineTracker.resposemodel.TalukModel;


import java.util.ArrayList;

/**
 * Created by ramkrishna on .
 */

public class SpinAdapter<T> extends ArrayAdapter<T> /*extends ArrayAdapter<CategoryList>*/ {
    private Context context;
    private ArrayList<T> objectsList;

    public SpinAdapter(@NonNull Context context,
                       int resource,
                       int textViewResourceId,
                       @NonNull ArrayList<T> objectsList) {
        super(context, resource, textViewResourceId, objectsList);
        this.context=context;
        this.objectsList=objectsList;
    }

    public int getCount() {
        return objectsList.size();
    }

    public T getItem(int position) {
        return objectsList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        label.setTextSize(16);
        label.setPadding(0,10,0,10);

        if(objectsList.get(position) instanceof String){
            label.setText(""+objectsList.get(position));
        }

        if(objectsList.get(position) instanceof DistrictModel){
            label.setText(""+((DistrictModel) objectsList.get(position)).getDISTRICT_NAME());
        } if(objectsList.get(position) instanceof TalukModel){
            label.setText(""+((TalukModel) objectsList.get(position)).getTALUKA_NAME());
        }
        /*if(objectsList.get(position) instanceof GetCategoriesItem){
            label.setText(""+((GetCategoriesItem) objectsList.get(position)).getName());
        }

        if(objectsList.get(position) instanceof BankAccountResponsesItem){
            label.setText(""+((BankAccountResponsesItem) objectsList.get(position)).getName());
        }

        if(objectsList.get(position) instanceof GetLineOfBusinessListResponsesItem){
            label.setText(""+((GetLineOfBusinessListResponsesItem) objectsList.get(position)).getName());
        }
        if(objectsList.get(position) instanceof PondTypeData){
            label.setText(""+((PondTypeData) objectsList.get(position)).getPondType());
        }
        if(objectsList.get(position) instanceof GetAllBanksItem){
            if (position==0) {
                label.setText("" + ((GetAllBanksItem) objectsList.get(position)).getAccountHolderName());
            }else {
                label.setText("" + ((GetAllBanksItem) objectsList.get(position)).getAccountHolderName() + "," + ((GetAllBanksItem) objectsList.get(position)).getAccountNumber());
            }
        }
        if(objectsList.get(position) instanceof Data){
            label.setText(""+((Data) objectsList.get(position)).getSupplierName());
        }
        if(objectsList.get(position) instanceof RequestbodyVendor){
            label.setText(""+((RequestbodyVendor) objectsList.get(position)).getVendorName());
        }
        if(objectsList.get(position) instanceof GetProductsItem){
            label.setText(""+((GetProductsItem) objectsList.get(position)).getProductName());
        }*/


        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {


        View row= View.inflate(context, R.layout.spinnerdropdown_view,null);
        TextView label = row.findViewById(R.id.sp_heading);
        /*label.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        label.setTextColor(Color.BLACK);
        label.setTextSize(14);
        label.setPadding(20,25,0,25);*/
        /*label.setMinHeight(50);
        label.setMinWidth(200);*/

       //label.setBackgroundColor(context.getResources().getColor(R.color.colorsemiwhite));
       /* label.setText(objectsList.toArray(new Object[objectsList.size()])[position]
                .toString());*/

        if(objectsList.get(position) instanceof String){
            label.setText(""+objectsList.get(position));
        }

        if(objectsList.get(position) instanceof DistrictModel){
            label.setText(""+((DistrictModel) objectsList.get(position)).getDISTRICT_NAME());
        }

        if(objectsList.get(position) instanceof TalukModel){
            label.setText(""+((TalukModel) objectsList.get(position)).getTALUKA_NAME());
        }
       /* if(objectsList.get(position) instanceof GetCategoriesItem){
            label.setText(""+((GetCategoriesItem) objectsList.get(position)).getName());
        }

        if(objectsList.get(position) instanceof BankAccountResponsesItem){
            label.setText(""+((BankAccountResponsesItem) objectsList.get(position)).getName());
        }
        if(objectsList.get(position) instanceof GetLineOfBusinessListResponsesItem){
            label.setText(""+((GetLineOfBusinessListResponsesItem) objectsList.get(position)).getName());
        }
        if(objectsList.get(position) instanceof PondTypeData){
            label.setText(""+((PondTypeData) objectsList.get(position)).getPondType());
        }
        if(objectsList.get(position) instanceof GetAllBanksItem){
            if (position==0) {
                label.setText("" + ((GetAllBanksItem) objectsList.get(position)).getAccountHolderName());
            }else {
                label.setText("" + ((GetAllBanksItem) objectsList.get(position)).getAccountHolderName() + "," + ((GetAllBanksItem) objectsList.get(position)).getAccountNumber());
            }
        }
        if(objectsList.get(position) instanceof Data){
            label.setText(""+((Data) objectsList.get(position)).getSupplierName());
        }
        if(objectsList.get(position) instanceof RequestbodyVendor){
            label.setText(""+((RequestbodyVendor) objectsList.get(position)).getVendorName());
        }

        if(objectsList.get(position) instanceof GetProductsItem){
            label.setText(""+((GetProductsItem) objectsList.get(position)).getProductName());
        }*/



        return label;
    }
}
