package com.dianet.efd_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GetDatabaseData extends Activity {

	private EditText account1Edit;
	private EditText account2Edit;
	private EditText account1amount;
	private EditText account2amount;
	private String account1DefaultValue = "Account 1";
	private String account2DefaultValue = "Account 2";
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

	// TextView Allaccountsvalue2 = new TextView(this);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * @Override public boolean onCreateOptionsMenu(Menu menu) {
		 * getMenuInflater().inflate(R.menu.activity_main, menu); return true; }
		 */
		@SuppressWarnings("unused")
		Intent intent = getIntent();
		//String accounttype = intent.getStringExtra("accounttype");
		//int entryamount = intent.getIntExtra("editamount", status1);
		//int entryamountInteger = Integer.parseInt(entryamount);
		
		//String accounttype = "savings";
		//int entryamountInteger = 3000;
		//setContentView(R.layout.activity_accounts_view);

		accountTypeDbAdapter = new AccountTypeDbAdapter(this);
		accountTypeDbAdapter = accountTypeDbAdapter.open();
		
		EntryUpdateDbAdapter = new EntryUpdateDbAdapter(this);
        EntryUpdateDbAdapter = EntryUpdateDbAdapter.open(); 
        
        TagDescriptionUpdateAdapter = new TagDescriptionUpdateAdapter(this);
        TagDescriptionUpdateAdapter = TagDescriptionUpdateAdapter.open();
        
        BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
        BudgetUpdateAdapter = BudgetUpdateAdapter.open();
        
        BudgetAmountAdapter = new BudgetAmountAdapter(this);
        BudgetAmountAdapter = BudgetAmountAdapter.open();
        
       //accountTypeDbAdapter.UpdateEntryAccountAmount2(accounttype, entryamountInteger);
        
		final String accountname = accountTypeDbAdapter
				.getSinlgeEntryAccountType(1);
		final String accountname2 = accountTypeDbAdapter
				.getSinlgeEntryAccountType(2);

		int amountVal1 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(1);
		int amountVal2 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(2);
		int amountVal = amountVal1 + amountVal2;
		String amountVal3 = Integer.toString(amountVal);
		
		//String[] AllAccountName = {"dorobuchi", "doromoney"};
		String[] AllAccountName = accountTypeDbAdapter.getTagDescription();
		String[] AllTagList = TagDescriptionUpdateAdapter.getTagDescription();

		Intent data = new Intent();
		data.putExtra(EXTRA_STRING_NAME, AllTagList);
		data.putExtra(EXTRA_STRING_NAME2, AllAccountName);
		data.putExtra(EXTRA_STRING_NAME3, amountVal3);
		// // Activity finished ok, return the data
		 setResult(RESULT_OK, data);
		//Intent intent = new Intent(GetDatabaseData.this,
		//		MainActivity.class);
		//startActivity(intent);
		super.finish();
		
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
}
