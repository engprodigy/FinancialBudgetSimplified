package com.dianet.efd_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


// public class BudgetSetup extends Activity {
public class BudgetViewIntent extends Activity {

	private static final int REQUEST_CODE = 0;
	public static final String EXTRA_STRING_NAME7 = "extraStringName7";

	

	TagDescriptionUpdateAdapter TagDescriptionUpdateAdapter;
	BudgetUpdateAdapter BudgetUpdateAdapter;
	BudgetAmountAdapter BudgetAmountAdapter;

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

		String[] fromColumns = TagDescriptionUpdateAdapter.getTagDescription();
		
		@SuppressWarnings("unused")
		Intent intent = getIntent();
		
		//String starttime = intent.getExtras().getString("Value1");
		//String endtime = intent.getExtras().getString("Value2");

		int expenseTaglength = fromColumns.length;
		// int expenseTaglength = 2;

		String[] fromColumns3 = new String[expenseTaglength];
		String[] budgetAmountArray = new String[expenseTaglength];

		for (int i = 1; i < expenseTaglength + 1; i++) {
			String expenseTag2 = fromColumns[i - 1];
			budgetAmountArray[i - 1] = BudgetAmountAdapter
					.getExpenseAmountByTag(expenseTag2);
			fromColumns3[i - 1] = Integer.toString(BudgetUpdateAdapter
					.getTotalExpenseAmountByTag2(expenseTag2));

		}

		String[] fromColumns4 = new String[expenseTaglength];
		ArrayList<BudgetDescription> budgetList = new ArrayList<BudgetDescription>();
		ArrayList<String> budgetList2 = new ArrayList<String>();

		for (int i = 1; i < expenseTaglength + 1; i++) {
			String budgetAmountStatusKey = BudgetUpdateAdapter
					.getExpenseAmountByTag(fromColumns[i - 1]);
			BudgetDescription budgetentry = new BudgetDescription();
			budgetentry.setBudgetName(fromColumns[i - 1]);
			budgetentry.setBudgetAmount(budgetAmountArray[i - 1]);
			budgetentry.setBudgetSpentAmount(fromColumns3[i - 1]);
			budgetList.add(budgetentry);
			budgetList2.add(fromColumns[i - 1]);
			budgetList2.add(budgetAmountArray[i - 1]);
			budgetList2.add(fromColumns3[i - 1]);
			
			if (Integer.parseInt(budgetAmountStatusKey) <= 0) {
				String expense_entry_description = fromColumns[i - 1]
						+ "           " + "           " + "       " + "       ";
				fromColumns4[i - 1] = expense_entry_description;

			} else {
				String expense_entry_description = fromColumns[i - 1]
						+ "           " + "           " + fromColumns3[i - 1]
						+ "       " + "       " + fromColumns3[i - 1];
				fromColumns4[i - 1] = expense_entry_description;
			}

		}
		
           Intent data7 = new Intent();
		
		
		data7.putStringArrayListExtra(EXTRA_STRING_NAME7, budgetList2);
		 setResult(RESULT_OK, data7);
		super.finish();

		

	}

	
}
