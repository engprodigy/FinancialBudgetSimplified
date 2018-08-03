package com.dianet.efd_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;


// public class BudgetSetup extends Activity {
public class HistoryViewIntent extends Activity {

	private static final int REQUEST_CODE = 0;
	public static final String EXTRA_STRING_NAME7 = "extraStringName7";
	public static final String EXTRA_STRING_NAME8 = "extraStringName8";

	

	TagDescriptionUpdateAdapter TagDescriptionUpdateAdapter;
	BudgetUpdateAdapter BudgetUpdateAdapter;
	BudgetAmountAdapter BudgetAmountAdapter;
	EntryUpdateDbAdapter EntryUpdateDbAdapter;

	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_budget_setup);
		//setContentView(R.layout.budget_view);

		TagDescriptionUpdateAdapter = new TagDescriptionUpdateAdapter(this);
		TagDescriptionUpdateAdapter = TagDescriptionUpdateAdapter.open();

		BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
		BudgetUpdateAdapter = BudgetUpdateAdapter.open();

		BudgetAmountAdapter = new BudgetAmountAdapter(this);
		BudgetAmountAdapter = BudgetAmountAdapter.open();
		
		EntryUpdateDbAdapter = new EntryUpdateDbAdapter(this);
		EntryUpdateDbAdapter = EntryUpdateDbAdapter.open();

		String[] fromColumns = TagDescriptionUpdateAdapter.getTagDescription();
		
		@SuppressWarnings("unused")
		Intent intent = getIntent();

		String StartDate = intent.getStringExtra("Value1");
		String EndDate = intent.getStringExtra("Value2");
		
		
	
		//ArrayList<ExpenseEntry> BudgetList = BudgetUpdateAdapter.getAllExpenseBetweenDate(StartDate,EndDate);
		
		//ArrayList<ExpenseEntry> EntryList = EntryUpdateDbAdapter.getAllExpenseBetweenDate(StartDate,EndDate);
		//ArrayList<String> EntryList2 = EntryUpdateDbAdapter.getAllExpenseBetweenDate2(StartDate,EndDate);
		ArrayList<String> EntryList2 = BudgetUpdateAdapter.getAllExpenseBetweenDate2(StartDate,EndDate);
		ArrayList<String> IncomeList2 = BudgetUpdateAdapter.getAllIncomeBetweenDate2(StartDate, EndDate);
		//data7.putStringArrayListExtra(EXTRA_STRING_NAME7, budgetList2);
		// setResult(RESULT_OK, data7);
		//super.finish();
		
		/*ArrayList<String> EntryList2 = new ArrayList<String>();
		
		for(int i = 0; i<EntryList.size(); i = i + 3){
			
		}*/
		
		 Intent data7 = new Intent();
			
	     data7.putStringArrayListExtra(EXTRA_STRING_NAME7, EntryList2);
		 data7.putStringArrayListExtra(EXTRA_STRING_NAME8, IncomeList2);
		 setResult(RESULT_OK, data7);
		 super.finish();

		

	}

	
}
