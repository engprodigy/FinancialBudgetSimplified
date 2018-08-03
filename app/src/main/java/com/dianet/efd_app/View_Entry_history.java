package com.dianet.efd_app;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@TargetApi(11)
public class View_Entry_history extends Activity {

	EntryUpdateDbAdapter EntryUpdateDbAdapter;

	// private String Entry_Amount;
	// private String Tag_Description;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_view);

		EntryUpdateDbAdapter = new EntryUpdateDbAdapter(this);
		EntryUpdateDbAdapter = EntryUpdateDbAdapter.open();

		Cursor myStringArray = EntryUpdateDbAdapter
				.getExpenseEntrybyDate2("12/06/2013");
		// Cursor myStringArray = EntryUpdateDbAdapter.getExpenseEntry();
		// Cursor myStringArray = EntryUpdateDbAdapter.getExpenseEntry2(6);
		List<ExpenseEntry> expenseList = EntryUpdateDbAdapter.getAllExpense();
		String[] fromColumns2 = EntryUpdateDbAdapter.getTagDescription();
		String[] fromColumns3 = EntryUpdateDbAdapter.getTagAmount();
		String[] fromColumns4 = EntryUpdateDbAdapter.getTagDate();

		// String accountvalue=
		// myStringArray.getString(myStringArray.getColumnIndex("Tag_Description"));
		// String[] myStringArray =
		// EntryUpdateDbAdapter.getExpenseEntrybyDate("12/05/2013");3/06/2013
		// String[] myStringArray1 = new String[]{"mahendra", accountvalue,
		// "girish", "komal"};

		// ge

		// @SuppressWarnings("rawtypes")

		// ListView listView = (ListView) findViewById(R.id.listview);

		// listView.setAdapter(adapter);

		// TextView display_name = new TextView(this);
		// TextView phone_number = new TextView(this);

		String[] fromColumns = {
				"Date:"
						+ " "
						+ " "
						+ " "
						+ myStringArray.getString(myStringArray
								.getColumnIndex("Entry_Date")),
				"Expense Tag:"
						+ " "
						+ " "
						+ " "
						+ myStringArray.getString(myStringArray
								.getColumnIndex("Tag_Description")),
				"Amount:"
						+ " "
						+ " "
						+ " "
						+ myStringArray.getString(myStringArray
								.getColumnIndex("Entry_Amount")),
				"Transaction Type:"
						+ " "
						+ " "
						+ " "
						+ myStringArray.getString(myStringArray
								.getColumnIndex("Transaction_Type")),
				"Account:"
						+ " "
						+ " "
						+ " "
						+ myStringArray.getString(myStringArray
								.getColumnIndex("Account_Type")) };

		int expenseTaglength = fromColumns2.length;
		String expenseTaglength3 = Integer.toString(expenseTaglength);
		String[] fromColumns5 = new String[expenseTaglength];

		for (int i = 1; i < expenseTaglength + 1; i++) {
			String expense_entry_description = fromColumns2[i - 1]
					+ "           " + "           " + fromColumns3[i - 1]
					+ "       " + "       " + fromColumns4[i - 1];
			fromColumns5[i - 1] = expense_entry_description;

		}
		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, fromColumns5);
		// ArrayAdapter<ExpenseEntry> adapter = new
		// ArrayAdapter<ExpenseEntry>(this,
		// android.R.layout.simple_list_item_1, expenseList);
		ListView listView = (ListView) findViewById(R.id.expense_view);
		// ListView listView = new ListView(this);
		listView.setAdapter(adapter);
		// this.setContentView(listView);
		/*
		 * ArrayAdapter adapter2 = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, fromColumns3);
		 * 
		 * ListView listView2 = (ListView)
		 * findViewById(R.id.expense_tag_amount);
		 * 
		 * listView2.setAdapter(adapter2);
		 * 
		 * ArrayAdapter adapter3 = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, fromColumns4);
		 * 
		 * ListView listView3 = (ListView) findViewById(R.id.expense_tag_date);
		 * 
		 * listView3.setAdapter(adapter3);
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_view__entry_history, menu);
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, View_Entry_history.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case R.id.menu_home:
			Intent intent1 = new Intent(this, MainActivity.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent1);
			return true;
		case R.id.menu_budgets:
			Intent intent3 = new Intent(this, MainActivity.class);
			intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent3);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
