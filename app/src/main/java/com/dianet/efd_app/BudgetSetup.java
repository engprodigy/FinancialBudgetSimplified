package com.dianet.efd_app;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class BudgetDescription {

	// private variables
	int _id;
	String _budget_name;
	String _budget_amount;
	String _budget_spent_amount;

	public BudgetDescription() {

	}

	// constructor
	public BudgetDescription(int id, String _budget_name,
			String _budget_amount, String _budget_spent_amount) {
		this._id = id;
		this._budget_name = _budget_name;
		this._budget_amount = _budget_amount;
		this._budget_spent_amount = _budget_spent_amount;
	}

	// constructor

	// setting id
	public void setID(int id) {
		this._id = id;
	}

	public void setBudgetName(String _budget_name) {
		this._budget_name = _budget_name;
	}

	public void setBudgetAmount(String _budget_amount) {
		this._budget_amount = _budget_amount;
	}

	public void setBudgetSpentAmount(String _budget_spent_amount) {
		this._budget_spent_amount = _budget_spent_amount;
	}

	// getting name

	public String getBudgetName() {
		return this._budget_name;
	}

	public String getBudgetAmount() {
		return this._budget_amount;
	}

	public String getBudgetSpentAmount() {
		return this._budget_spent_amount;
	}
	
	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id",_id + "");
		map.put("_budget_name", _budget_name);
		map.put("_budget_amount","Budget(monthly):" + "  " + _budget_amount);
		map.put("_budget_spent_amount","Spent:" + "  " + _budget_spent_amount);
		map.put("_budget_amount2", _budget_amount);
	//	map.put("orders", getOrders() + "");
		
		return map;
	}

}

// public class BudgetSetup extends Activity {
public class BudgetSetup extends ListActivity {

	private static final int REQUEST_CODE = 0;

	private OrderAdapter m_adapter;

	TagDescriptionUpdateAdapter TagDescriptionUpdateAdapter;
	BudgetUpdateAdapter BudgetUpdateAdapter;
	BudgetAmountAdapter BudgetAmountAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_budget_setup);
		setContentView(R.layout.budget_view);

		TagDescriptionUpdateAdapter = new TagDescriptionUpdateAdapter(this);
		TagDescriptionUpdateAdapter = TagDescriptionUpdateAdapter.open();

		BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
		BudgetUpdateAdapter = BudgetUpdateAdapter.open();

		BudgetAmountAdapter = new BudgetAmountAdapter(this);
		BudgetAmountAdapter = BudgetAmountAdapter.open();

		String[] fromColumns = TagDescriptionUpdateAdapter.getTagDescription();
		/*
		 * String[] fromColumns = new String[2]; fromColumns[0] = "shoe";
		 * fromColumns[1] = "new phone";
		 */
		// String budgetID =
		// TagDescriptionUpdateAdapter.getExpenseEntryID(fromColumns[1]);

		// String budgetID2 = BudgetUpdateAdapter.getExpenseEntry(1);
		// String expenseTag = "transports";
		// String budgetID3 =
		// BudgetUpdateAdapter.getExpenseAmountByTag(expenseTag);

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

		// String[] fromColumns2 = new String[3];
		// fromColumns2[0] = budgetID;
		// fromColumns2[1] = expenseTaglength3;
		// fromColumns2[2] = budgetID;

		String[] fromColumns4 = new String[expenseTaglength];
		ArrayList<BudgetDescription> budgetList = new ArrayList<BudgetDescription>();

		for (int i = 1; i < expenseTaglength + 1; i++) {
			String budgetAmountStatusKey = BudgetUpdateAdapter
					.getExpenseAmountByTag(fromColumns[i - 1]);
			BudgetDescription budgetentry = new BudgetDescription();
			budgetentry.setBudgetName(fromColumns[i - 1]);
			budgetentry.setBudgetAmount(budgetAmountArray[i - 1]);
			budgetentry.setBudgetSpentAmount(fromColumns3[i - 1]);
			budgetList.add(budgetentry);
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

		this.m_adapter = new OrderAdapter(this, R.layout.arraylist_test,
				budgetList);
		setListAdapter(this.m_adapter);

		// @SuppressWarnings("rawtypes")
		/*
		 * ArrayAdapter adapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, fromColumns4);
		 * 
		 * ListView listView = (ListView) findViewById(R.id.budget_view);
		 * 
		 * listView.setAdapter(adapter);
		 */

		/*
		 * ArrayAdapter adapter2 = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, fromColumns3);
		 * 
		 * ListView listView2 = (ListView)
		 * findViewById(R.id.budget_amount_spent);
		 * 
		 * listView2.setAdapter(adapter2);
		 * 
		 * 
		 * ArrayAdapter adapter3 = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, fromColumns2);
		 * 
		 * ListView listView3 = (ListView) findViewById(R.id.budget_amount);
		 * 
		 * listView3.setAdapter(adapter3);
		 */

	}

	private class OrderAdapter extends ArrayAdapter<BudgetDescription> {

		private ArrayList<BudgetDescription> items;

		public OrderAdapter(Context context, int textViewResourceId,
				ArrayList<BudgetDescription> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row2, null);
			}
			BudgetDescription o = items.get(position);
			// TextView tt = (TextView) v.findViewById(R.id.toptext);

			// tt.setText("Name:Kolade");
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.amounttext);
				TextView dt = (TextView) v.findViewById(R.id.entrydate);
				if (tt != null) {
					tt.setText(o.getBudgetName());
					// tt.setText("Name:Kolade");
				}
				if (bt != null) {
					bt.setText("Budget: " + o.getBudgetAmount());
				}
				if (dt != null) {
					dt.setText("Spent: " + o.getBudgetSpentAmount());
				}
			}
			return v;
		}
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something with the data
		BudgetDescription BudgetDescription = (com.dianet.efd_app.BudgetDescription) getListView()
				.getItemAtPosition(position);
		String BudgetName = BudgetDescription.getBudgetName();
		String BudgetAmount = BudgetDescription.getBudgetAmount();
		// Toast.makeText(getApplicationContext(), BudgetName + BudgetAmount,
		// Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(BudgetSetup.this,
				MonthlyBudgetAmountSetupMainActivity.class);
		intent.putExtra("Value1", BudgetName);
		intent.putExtra("Value2", BudgetAmount);
		// Set the request code to any code you like, you can identify the
		// callback via this code
		startActivityForResult(intent, REQUEST_CODE);
	}

	/*
	 * public void setOnItemLongClickListener(ListView l, View v, int position,
	 * long id) {
	 * 
	 * BudgetDescription BudgetDescription = (com.example.efd.BudgetDescription)
	 * getListView().getItemAtPosition(position); String BudgetName =
	 * BudgetDescription.getBudgetName(); String BudgetAmount =
	 * BudgetDescription.getBudgetAmount();
	 * Toast.makeText(getApplicationContext(), BudgetName + BudgetAmount,
	 * Toast.LENGTH_SHORT).show();
	 * 
	 * }
	 */

	/*
	 * public void onListItemLongClick(ListView l, View v, int position, long
	 * id) {
	 * 
	 * // Do something with the data BudgetDescription BudgetDescription =
	 * (com.example.efd.BudgetDescription)
	 * getListView().getItemAtPosition(position); String BudgetName =
	 * BudgetDescription.getBudgetName(); String BudgetAmount =
	 * BudgetDescription.getBudgetAmount();
	 * //Toast.makeText(getApplicationContext(), BudgetName + BudgetAmount,
	 * Toast.LENGTH_SHORT).show(); Intent intent = new Intent(BudgetSetup.this,
	 * MonthlyBudgetAmountSetupMainActivity.class); intent.putExtra("Value1",
	 * BudgetName); intent.putExtra("Value2", BudgetAmount); // Set the request
	 * code to any code you like, you can identify the // callback via this code
	 * startActivityForResult(intent, REQUEST_CODE);
	 * 
	 * }
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, ArrayListTester.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case R.id.menu_home:
			Intent intent1 = new Intent(this, MainActivity.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent1);
			return true;
		case R.id.menu_budgets:
			Intent intent3 = new Intent(this, BudgetSetup.class);
			intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent3);
			return true;
		case R.id.menu_settings2:
			// app icon in action bar clicked; go home
			Intent intent4 = new Intent(this, ArrayListTester.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent4);
			return true;
		case R.id.menu_home2:
			Intent intent5 = new Intent(this, MainActivity.class);
			intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent5);
			return true;
		case R.id.menu_budgets2:
			// Intent intent3 = new Intent(this, BudgetSetup.class);
			Intent intent6 = new Intent(this, BudgetSetup.class);
			intent6.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent6);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			if (data.hasExtra("returnKey1")) {
				// Toast.makeText(this,
				// data.getExtras().getString("returnKey1"),
				// Toast.LENGTH_SHORT).show();
			}
		}
	}
}
