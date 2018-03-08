package com.ironkaran.ironkaran.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ironkaran.ironkaran.R;
import com.ironkaran.ironkaran.models.OrderDetails;

import java.util.ArrayList;

/**
 * Created by gokulakrishnanm on 04/03/18.
 */

public class CustomHistoryAdapter extends ArrayAdapter<OrderDetails> {

    private ArrayList<OrderDetails> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView orderNumber;
        TextView price;
        TextView status;

    }

    public CustomHistoryAdapter(ArrayList<OrderDetails> data, Context context) {
        super(context, R.layout.history_row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        OrderDetails dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.history_row_item, parent, false);
            viewHolder.orderNumber = (TextView) convertView.findViewById(R.id.orderNumber);
            viewHolder.price = (TextView)convertView.findViewById(R.id.price);
            viewHolder.status = (TextView)convertView.findViewById(R.id.status);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.orderNumber.setText(dataModel.getOrderNumber()+" ");
        viewHolder.price.setText(dataModel.getPrice()+" ");
        viewHolder.status.setText(dataModel.getProcessState());
//        viewHolder.pickedup.setOnClickListener(this);
//        viewHolder.pickedup.setTag(position);

        // Return the completed view to render on screen
        return convertView;
    }
}