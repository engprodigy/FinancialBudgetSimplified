package com.dianet.efd_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class UpdateAccountIntent extends Activity {

	
	AccountTypeDbAdapter accountTypeDbAdapter;
	int status1 = 0;
	private int status2 = 0;
	private int status3 = 0;
	private int status4 = 0;
	public static final String EXTRA_STRING_NAME = "extraStringName";
	public static final String EXTRA_STRING_NAME2 = "extraStringName2";
	public static final String EXTRA_STRING_NAME3 = "extraStringName3";
	EntryUpdateDbAdapter EntryUpdateDbAdapter;
	TagDescriptionUpdateAdapter TagDescriptionUpdateAdapter;
	BudgetUpdateAdapter BudgetUpdateAdapter;
	BudgetAmountAdapter BudgetAmountAdapter;
	public String EditDateString;
	
	// TextView Allaccountsvalue2 = new TextView(this);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * @Override public boolean onCreateOptionsMenu(Menu menu) {
		 * getMenuInflater().inflate(R.menu.activity_main, menu); return true; }
		 */
		
		accountTypeDbAdapter = new AccountTypeDbAdapter(this);
		accountTypeDbAdapter = accountTypeDbAdapter.open();

		EntryUpdateDbAdapter = new EntryUpdateDbAdapter(this);
		EntryUpdateDbAdapter = EntryUpdateDbAdapter.open();

		BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
		BudgetUpdateAdapter = BudgetUpdateAdapter.open();

		@SuppressWarnings("unused")
		Intent intent = getIntent();
		//String accounttype = intent.getStringExtra("accounttype");
		String[] accounttype = intent.getStringArrayExtra("accounttype");
		String transactiontype = intent.getStringExtra("transactiontype");
		String EditDateString2 = intent.getStringExtra("expensedate");
		int entryamount = intent.getIntExtra("editamount", status1);

		EditDateString = EditDateString2;
		
		//String transactiontype = accounttype[1];
		//String entryvalue = 'income';
		
		//if(accounttype[0] =="income"){
		
		
	    accountTypeDbAdapter.UpdateEntryAccountAmount2(accounttype[0], entryamount);
        
		//final String accountname = accountTypeDbAdapter.getSinlgeEntryAccountType(1);
		//final String accountname2 = accountTypeDbAdapter.getSinlgeEntryAccountType(2);

		int amountVal1 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(1);
		int amountVal2 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(2);
		int amountVal = amountVal1 + amountVal2;
		String amountVal3 = Integer.toString(amountVal);
		
		//String[] AllAccountName = accountTypeDbAdapter.getTagDescription();
		//EntryUpdateDbAdapter.insertEntryExpenseUpdate(
			//	transactiontype, entryamount,
			//	transactiontype, accounttype[0],
			//	EditDateString2);

		BudgetUpdateAdapter.insertEntryExpenseUpdateBudget(
				transactiontype, entryamount,
				entryamount, EditDateString2);
		

		Intent data = new Intent();
		
		//data.putExtra(EXTRA_STRING_NAME, accountname);
		//data.putExtra(EXTRA_STRING_NAME2, AllAccountName);
		data.putExtra(EXTRA_STRING_NAME3, amountVal3);
		
		
		// // Activity finished ok, return the data
		 setResult(RESULT_OK, data);
		//Intent intent = new Intent(GetDatabaseData.this,
		//		MainActivity.class);
		//startActivity(intent);
		super.finish();
		}/*else{
			
			Intent data = new Intent();
			
			accountTypeDbAdapter.UpdateEntryAccountAmount2("savings", 250);
			int amountVal1 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(1);
			int amountVal2 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(2);
			int amountVal = amountVal1 + amountVal2;
			String amountVal3 = Integer.toString(amountVal);
			data.putExtra(EXTRA_STRING_NAME3, amountVal3);
			
			
			// // Activity finished ok, return the data
			 setResult(RESULT_OK, data);
			//Intent intent = new Intent(GetDatabaseData.this,
			//		MainActivity.class);
			//startActivity(intent);
			super.finish();
			
		}*/
		}
		
	

	/*
	@Override
	public void finish() {
		// Prepare data intent
		 Intent data = new Intent();
		// data.putExtra("returnKey1", "Swinging on a star. ");
		// data.putExtra("returnKey2", "You could be better then you are. ");
		// // Activity finished ok, return the data
		// setResult(RESULT_OK, data);
		//Intent intent = new Intent(GetDatabaseData.this,
		//		MainActivity.class);
		//startActivity(intent);
		super.finish();
	}
	*/

