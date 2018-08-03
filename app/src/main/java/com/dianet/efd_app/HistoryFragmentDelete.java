package com.dianet.efd_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class HistoryFragmentDelete extends Activity {

    int status1 = 0;
    private int status2 = 0;
    private int status3 = 0;
    private int status4 = 0;
    private static final int REQUEST_CODE = 0;
    public static final String EXTRA_STRING_NAME7 = "extraStringName7";
    AccountTypeDbAdapter accountTypeDbAdapter;
    EntryUpdateDbAdapter EntryUpdateDbAdapter;
    TagDescriptionUpdateAdapter TagDescriptionUpdateAdapter;
    BudgetUpdateAdapter BudgetUpdateAdapter;
    BudgetAmountAdapter BudgetAmountAdapter;
    public String EditTagString2;
    public int EditAmountInteger2 = 0;
    public int ExpenseBudgetAmount2 = 0;
    public String EditAccountTypeString2;
    public String EditDateString;
    public String TransactionTypeString2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_fragment_delete);

        EntryUpdateDbAdapter = new EntryUpdateDbAdapter(this);
        EntryUpdateDbAdapter = EntryUpdateDbAdapter.open();

        BudgetAmountAdapter = new BudgetAmountAdapter(this);
        BudgetAmountAdapter = BudgetAmountAdapter.open();

        accountTypeDbAdapter = new AccountTypeDbAdapter(this);
        accountTypeDbAdapter = accountTypeDbAdapter.open() ;

        BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
        BudgetUpdateAdapter = BudgetUpdateAdapter.open();

        TagDescriptionUpdateAdapter = new TagDescriptionUpdateAdapter(this);
        TagDescriptionUpdateAdapter = TagDescriptionUpdateAdapter.open();

       // Intent intent = getIntent();

        //int id = Integer.parseInt(intent.getStringExtra("id"));
        //String budget_type = intent.getStringExtra("budget_type");
        //String entry_amount = intent.getStringExtra("entry_amount");
        showDialog3();
    }

    @SuppressLint({ "NewApi", "NewApi", "NewApi" })
    void showDialog3() {
        DialogFragment newFragment3 = MyAlertDialogFragment3
                .newInstance(R.string.home_alert_dialog_title2);
        newFragment3.show(getFragmentManager(), "dialog3");
    }

    public void doPositiveClick(String args) {
        // Do stuff here.
        // return;
        Intent intent = getIntent();
        String budget_type = intent.getStringExtra("budget_type");
        int amountVal1 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(1);
        int amountVal2 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(2);
        int amountVal = amountVal1 + amountVal2;
        String amountVal3 = Integer.toString(amountVal);
        Intent data7 = new Intent();
        data7.putExtra(EXTRA_STRING_NAME7, amountVal3);
        // Activity finished ok, return the data
        Toast.makeText(getApplicationContext(), "Delete Transaction Cancelled", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, data7);

        super.finish();
    }


    public void doNegativeClick(String args, int args2) {
        // Do stuff here.
        Intent intent = getIntent();

        int id = Integer.parseInt(intent.getStringExtra("id"));
        String budget_type = intent.getStringExtra("budget_type");
        EntryUpdateDbAdapter.deleteExpenseEntry(id);
        boolean emptytablekey = BudgetUpdateAdapter.deleteExpenseEntry(id,
                budget_type);
        // Toast.makeText(getApplicationContext(), args + args2,
        // Toast.LENGTH_SHORT).show();
        // Log.i("FragmentAlertDialog", "Negative click!");
        if (emptytablekey) {
            TagDescriptionUpdateAdapter.deleteExpenseEntry(budget_type);
            BudgetAmountAdapter.deleteExpenseEntry(budget_type);
        }
       // finish();
       // startActivity(getIntent());
        Intent data7 = new Intent();

       // data7.putStringArrayListExtra(EXTRA_STRING_NAME7, EntryList2);
        Toast.makeText(getApplicationContext(), "Delete Transaction Completed", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, data7);
        super.finish();
    }


    @SuppressLint({ "NewApi", "NewApi" })
    public static class MyAlertDialogFragment3 extends DialogFragment {

        @SuppressLint("NewApi")
        public static MyAlertDialogFragment3 newInstance(int title) {
            MyAlertDialogFragment3 frag3 = new MyAlertDialogFragment3();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag3.setArguments(args);
            return frag3;
        }

        @SuppressLint("NewApi")
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");
            // TextView MyDatevalue = new TextView(this);

            // MyDatevalue.setText(expenseAmount2);

            return new AlertDialog.Builder(getActivity())
                    // .setIcon(R.drawable.alert_dialog_dart_icon)
                    .setTitle(title)
                            // .setView(convertView)
                            // .setView(R.layout.row)
                    .setPositiveButton(R.string.home_alert_dialog_cancel,
                            new DialogInterface.OnClickListener() {
                                // private String expenseAmount3;

                                public void onClick(DialogInterface dialog3,
                                                    int whichButton) {

                                    String expenseAmount20 = null;
                                    ((HistoryFragmentDelete) getActivity())
                                            .doPositiveClick(expenseAmount20);
                                    dopositiveClick2();
                                }
                            })
                    .setNegativeButton(R.string.home_alert_dialog_continue,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog3,
                                                    int whichButton) {
                                    String expenseAmount20 = null;
                                    int entryID20 = 0;
                                    ((HistoryFragmentDelete) getActivity())
                                            .doNegativeClick(expenseAmount20,
                                                    entryID20);
                                }
                            }).create();
        }

        protected void dopositiveClick2() {
            // TODO Auto-generated method stub

        }
    }

}
