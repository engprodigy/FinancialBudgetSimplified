package com.dianet.efd_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class UpdateBudgetIntent extends Activity {

	
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
		
		BudgetAmountAdapter = new BudgetAmountAdapter(this);
        BudgetAmountAdapter = BudgetAmountAdapter.open();
        
        TagDescriptionUpdateAdapter = new TagDescriptionUpdateAdapter(this);
        TagDescriptionUpdateAdapter = TagDescriptionUpdateAdapter.open();
        
		@SuppressWarnings("unused")
		Intent intent = getIntent();
		
		String[] accounttype = intent.getStringArrayExtra("accounttype");
		
		int entryamount = intent.getIntExtra("editamount", status1);
		
		
		
		
		Boolean CheckTagStatus = BudgetAmountAdapter
				.getExpenseTagStatus(accounttype[0]);
		if (!CheckTagStatus) {

			BudgetAmountAdapter
					.insertEntryExpenseUpdateBudget2(
							accounttype[0], entryamount);
		} else {

			BudgetAmountAdapter.UpdateExpenseAmountByTag(
					accounttype[0], entryamount);

		}
		Boolean TagStatus = TagDescriptionUpdateAdapter
				.CheckTagDescription(accounttype[0]);
		if (TagStatus) {
			TagDescriptionUpdateAdapter
					.insertEntryExpenseUpdate(accounttype[0]);
		}
		;
		
		//String[] AllAccountName = accountTypeDbAdapter.getTagDescription();
		

		Intent data = new Intent();
		
		
		
		
		// // Activity finished ok, return the data
		 setResult(RESULT_OK, data);
		//Intent intent3 = new Intent(getActivity().getBaseContext(), MainActivity.class);
		//startActivityForResult(intent3, REQUEST_CODE3);
		//startActivity(intent);
		super.finish();
		}
	
}
