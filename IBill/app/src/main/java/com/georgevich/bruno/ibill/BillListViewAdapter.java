package com.georgevich.bruno.ibill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by bruno on 15/03/16.
 */
public class BillListViewAdapter extends BaseAdapter {

    private ArrayList<Bill> m_bills;
    private LayoutInflater m_inflater;
    private static Comparator<Bill> billListComparator = new Comparator<Bill>() {
        @Override
        public int compare(Bill a, Bill b) {
            String lhs = a.getDueDate();
            String rhs = b.getDueDate();
            String[] l = lhs.split("/");
            String[] r = rhs.split("/");
            int d1,m1,y1;
            int d2,m2,y2;
            d1 = Integer.parseInt(l[0]);
            d2 = Integer.parseInt(r[0]);
            m1 = Integer.parseInt(l[1]);
            m2 = Integer.parseInt(r[1]);
            y1 = Integer.parseInt(l[2]);
            y2 = Integer.parseInt(r[2]);
            if(y1 > y2) return 1;
            else if(y1 < y2) return -1;
            if(m1 > m2) return 1;
            else if(m1 < m2) return -1;
            if(d1 > d2) return 1;
            else if(d1 < d2) return -1;
            return 0;
        }
    };

    public BillListViewAdapter(Context ctx) {
        m_bills = new ArrayList<>();
        m_inflater = LayoutInflater.from(ctx);
    }

    public boolean addBill(Bill b) {
        boolean addCheck = m_bills.add(b);
        Collections.sort(m_bills,billListComparator);
        notifyDataSetChanged();
        return addCheck;
    }

    public boolean removeBill(Bill b) {
        boolean removeCheck = m_bills.remove(b);
        Collections.sort(m_bills,billListComparator);
        notifyDataSetChanged();
        return removeCheck;
    }

    @Override
    public int getCount() {
        return m_bills.size();
    }

    @Override
    public Object getItem(int position) {
        return m_bills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BillSupport bSupport;
        if(convertView==null) {
            convertView = m_inflater.inflate(R.layout.bill_list_item,null);
            bSupport = new BillSupport();
            bSupport.m_value = (TextView) convertView.findViewById(R.id.value_text_view);
            bSupport.m_name = (TextView) convertView.findViewById(R.id.name_text_view);
            bSupport.m_dueDate = (TextView) convertView.findViewById(R.id.date_text_view);
            bSupport.m_everyMonth = (ImageView) convertView.findViewById(R.id.repeat_image_view);
            convertView.setTag(bSupport);
        } else {
            bSupport = (BillSupport) convertView.getTag();
        }

        Bill b = m_bills.get(position);
        bSupport.m_name.setText(b.geName());
        bSupport.m_value.setText(b.getValue());
        bSupport.m_dueDate.setText(b.getDueDate());
        bSupport.m_everyMonth.setImageResource(b.isEveryMonth() ? R.drawable.repeat : R.drawable.norepeat);

        return convertView;
    }

    private class BillSupport {
        TextView m_value;
        TextView m_name;
        TextView m_dueDate;
        ImageView m_everyMonth;
    }
}
