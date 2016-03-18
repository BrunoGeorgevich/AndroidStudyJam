package com.georgevich.bruno.ibill;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    ListView billListView;
    TextView totalTextView;
    BillListViewAdapter adapterBillListView;
    public static final int RQ_NEWBILLACTIVITY = 1;
    public static final int RQ_DETAILEDBILLACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        adapterBillListView.addBill(new Bill("12.5", "Agua", "12/03/2016"));
        adapterBillListView.addBill(new Bill("80.5", "Taxi", "22/01/2016"));
        adapterBillListView.addBill(new Bill("121", "Darksouls", "14/05/2016"));
    }

    public void goAddBillActivity(View view) {
        Intent it = new Intent(this, NewBillActivity.class);
        startActivityForResult(it, RQ_NEWBILLACTIVITY);
    }

    private void deleteMessage(Bill b) {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want delete these bill?");
        final Bill fb = b;

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                adapterBillListView.removeBill(fb);
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void initViews() {
        totalTextView = (TextView) findViewById(R.id.total_text_view);
        adapterBillListView = new BillListViewAdapter(this,totalTextView);
        billListView = (ListView) findViewById(R.id.bill_list_view);
        billListView.setAdapter(adapterBillListView);
        billListView.setOnItemClickListener(new BillListItemListener());
        billListView.setOnItemLongClickListener(new BillListItemListener());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != RQ_NEWBILLACTIVITY || resultCode != RESULT_OK) {
            System.out.println("ERROR RESULT ACTIVITY MAIN ACTIVITY");
            return;
        }
        Bill b = (Bill) data.getSerializableExtra("bill");
        adapterBillListView.addBill(b);
    }

    public void sendCostFromEmail(View view) {
        Intent it = adapterBillListView.generateEmailData();
        startActivity(it);
    }

    private class BillListItemListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent it = new Intent(getBaseContext(), DetailedBillActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("bill",(Bill) parent.getItemAtPosition(position));
            it.putExtras(b);
            startActivityForResult(it,RQ_DETAILEDBILLACTIVITY);
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            deleteMessage((Bill) parent.getItemAtPosition(position));
            return true;
        }
    }
}
