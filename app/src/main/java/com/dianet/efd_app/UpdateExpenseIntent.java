package com.dianet.efd_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateExpenseIntent extends Activity {

	
	
	int status1 = 0;
	private int status2 = 0;
	private int status3 = 0;
	private int status4 = 0;
	public static final String EXTRA_STRING_NAME = "extraStringName";
	public static final String EXTRA_STRING_NAME2 = "extraStringName2";
	public static final String EXTRA_STRING_NAME3 = "extraStringName3";
	public static final String EXTRA_STRING_NAME4 = "extraStringName4";
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
	
	// TextView Allaccountsvalue2 = new TextView(this);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_picker_layout);

		/*
		 * @Override public boolean onCreateOptionsMenu(Menu menu) {
		 * getMenuInflater().inflate(R.menu.activity_main, menu); return true; }
		 */
		
		BudgetAmountAdapter = new BudgetAmountAdapter(this);
        BudgetAmountAdapter = BudgetAmountAdapter.open();
        
        TagDescriptionUpdateAdapter = new TagDescriptionUpdateAdapter(this);
        TagDescriptionUpdateAdapter = TagDescriptionUpdateAdapter.open();
        
       
        
        BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
        BudgetUpdateAdapter = BudgetUpdateAdapter.open();
        
        accountTypeDbAdapter = new AccountTypeDbAdapter(this);
		accountTypeDbAdapter = accountTypeDbAdapter.open() ;
		
		EntryUpdateDbAdapter = new EntryUpdateDbAdapter(this);
        EntryUpdateDbAdapter = EntryUpdateDbAdapter.open();
        
		@SuppressWarnings("unused")
		Intent intent = getIntent();
		
		String TransactionTypeString = intent.getStringExtra("transactiontype");
		String EditAccountTypeString = intent.getStringExtra("EditAccountTypeString2");
		String EditTagString = intent.getStringExtra("tag");
		String EditDateString2 = intent.getStringExtra("expensedate");
		int EditAmountInteger = intent.getIntExtra("editamount", status1);
		//String EditDateString = intent.getStringExtra("expensedate");
		
		EditAmountInteger2 = EditAmountInteger;
		EditTagString2 = EditTagString;
		EditAccountTypeString2 = EditAccountTypeString;
		 EditDateString = EditDateString2;
		 TransactionTypeString2 = TransactionTypeString;
		
		/*String TransactionTypeString ="Expense" ;
		String EditAccountTypeString = "savings";
		String EditTagString = "recharge card";
		int EditAmountInteger = 2000;*/
		//String EditDateString2 = "2013-05-15";
		
		
		
		
		
		int expenseTotalAmount = BudgetUpdateAdapter
				.getTotalExpenseAmountByTag2(EditTagString);
		int expenseBudgetAmount = BudgetAmountAdapter
				.getExpenseAmountByTag3(EditTagString);
		//ExpenseBudgetAmount2 = expenseBudgetAmount;

		int newTotalExpenseAmount = expenseTotalAmount
				+ EditAmountInteger;
		
		//showDialog2();

		if (newTotalExpenseAmount > expenseBudgetAmount) {
			
			showDialog2();
			
			//return;

		}else{
		
		EntryUpdateDbAdapter.insertEntryExpenseUpdate(
				TransactionTypeString, EditAmountInteger,
				EditTagString, EditAccountTypeString,
				EditDateString2);
		accountTypeDbAdapter.UpdateEntryAccountAmount(
				EditAccountTypeString, EditAmountInteger);
		Boolean CheckTagStatus = BudgetAmountAdapter
				.getExpenseTagStatus(EditTagString);
		if (!CheckTagStatus) {

			BudgetAmountAdapter.insertEntryExpenseUpdateBudget(
					EditTagString, EditAmountInteger);
		}

		BudgetUpdateAdapter.insertEntryExpenseUpdateBudget(
				EditTagString, expenseBudgetAmount,
				EditAmountInteger, EditDateString2);

		Boolean TagStatus = TagDescriptionUpdateAdapter
				.CheckTagDescription(EditTagString);
		if (TagStatus) {
			TagDescriptionUpdateAdapter
					.insertEntryExpenseUpdate(EditTagString);
		}
		;
		int amountVal1 = accountTypeDbAdapter
				.getSinlgeEntryAccountAmount2(1);
		int amountVal2 = accountTypeDbAdapter
				.getSinlgeEntryAccountAmount2(2);
		int amountVal = amountVal1 + amountVal2;
		String amountVal3 = Integer.toString(amountVal);
		//String amountVal3 = Integer.toString(expenseTotalAmount);
		//String amountVal3 = TransactionTypeString + EditAccountTypeString + EditTagString;
		
		
		
		
		
		

		Intent data = new Intent();
		data.putExtra(EXTRA_STRING_NAME4, amountVal3);
		
		
		
		// // Activity finished ok, return the data
		 setResult(RESULT_OK, data);
		
		super.finish();
		 }
		}
	
	
	
	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	void showDialog2() {
		DialogFragment newFragment2 = MyAlertDialogFragment2
				.newInstance(R.string.home_alert_dialog_title);
		newFragment2.show(getFragmentManager(), "dialog2");
	}

	public void doPositiveClick(String args) {
		// Do stuff here.
		// return;
		int amountVal1 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(1);
		int amountVal2 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(2);
		int amountVal = amountVal1 + amountVal2;
		String amountVal3 = Integer.toString(amountVal);
		Intent data = new Intent();
		data.putExtra(EXTRA_STRING_NAME4, amountVal3);
		 // Activity finished ok, return the data
		 setResult(RESULT_OK, data);
		
		super.finish();
	}

	
	public void doNegativeClick(String args, int args2) {
		// Do stuff here.

		EntryUpdateDbAdapter.insertEntryExpenseUpdate(TransactionTypeString2,
				EditAmountInteger2, EditTagString2, EditAccountTypeString2,
				EditDateString);
		accountTypeDbAdapter.UpdateEntryAccountAmount(EditAccountTypeString2,
				EditAmountInteger2);
		Boolean CheckTagStatus = BudgetAmountAdapter
				.getExpenseTagStatus(EditTagString2);
		if (!CheckTagStatus) {

			BudgetAmountAdapter.insertEntryExpenseUpdateBudget(EditTagString2,
					EditAmountInteger2);
		}

		BudgetUpdateAdapter.insertEntryExpenseUpdateBudget(EditTagString2,
				ExpenseBudgetAmount2, EditAmountInteger2, EditDateString);

		Boolean TagStatus = TagDescriptionUpdateAdapter
				.CheckTagDescription(EditTagString2);
		if (TagStatus) {
			TagDescriptionUpdateAdapter
					.insertEntryExpenseUpdate(EditTagString2);
		}
		;
		
		int amountVal1 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(1);
		int amountVal2 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(2);
		int amountVal = amountVal1 + amountVal2;
		String amountVal3 = Integer.toString(amountVal);
		// String expenseAmount2 = Integer.toString(expenseAmount);
		
		Intent data = new Intent();
		data.putExtra(EXTRA_STRING_NAME4, amountVal3);
		
		
		
		// // Activity finished ok, return the data
		 setResult(RESULT_OK, data);
		
		super.finish();
	}
 
 
	@SuppressLint({ "NewApi", "NewApi" })
	public static class MyAlertDialogFragment2 extends DialogFragment {

		@SuppressLint("NewApi")
		public static MyAlertDialogFragment2 newInstance(int title) {
			MyAlertDialogFragment2 frag2 = new MyAlertDialogFragment2();
			Bundle args = new Bundle();
			args.putInt("title", title);
			frag2.setArguments(args);
			return frag2;
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

								public void onClick(DialogInterface dialog2,
										int whichButton) {

									String expenseAmount20 = null;
									((UpdateExpenseIntent) getActivity())
											.doPositiveClick(expenseAmount20);
									dopositiveClick2();
								}
							})
					.setNegativeButton(R.string.home_alert_dialog_continue,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog2,
										int whichButton) {
									String expenseAmount20 = null;
									int entryID20 = 0;
									((UpdateExpenseIntent) getActivity())
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
