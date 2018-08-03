package com.dianet.efd_app;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BudgetDateRangeView extends ListActivity {

	BudgetUpdateAdapter BudgetUpdateAdapter;
	EntryUpdateDbAdapter EntryUpdateDbAdapter;
	private ProgressDialog m_ProgressDialog = null;
	private ArrayList<ExpenseEntry> m_orders = null;
	private OrderAdapter m_adapter;
	private Runnable viewOrders;
	public String dateDayValue2 = "0";
	public String dateDayValue;
	public String StartDate1;
	public String EndData2;

	@SuppressLint("ValidFragment")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.budget_view);

		Intent sender = getIntent();
		StartDate1 = sender.getExtras().getString("Value1");
		EndData2 = sender.getExtras().getString("Value2");
		// Toast.makeText(getApplicationContext(), StartDate1 + EndData2 ,
		// Toast.LENGTH_SHORT).show();
		BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
		BudgetUpdateAdapter = BudgetUpdateAdapter.open();
		ArrayList<ExpenseEntry> m_orders = BudgetUpdateAdapter
				.getAllExpenseBetweenDate(StartDate1, EndData2);

		/*
		 * EntryUpdateDbAdapter = new EntryUpdateDbAdapter(this);
		 * EntryUpdateDbAdapter = EntryUpdateDbAdapter.open();
		 * 
		 * ArrayList<ExpenseEntry> m_orders =
		 * EntryUpdateDbAdapter.getAllExpense3();
		 */
		this.m_adapter = new OrderAdapter(this, R.layout.arraylist_test,
				m_orders);
		setListAdapter(this.m_adapter);

		viewOrders = new Runnable() {
			@Override
			public void run() {
				getOrders();
			}
		};
		Thread thread = new Thread(null, viewOrders, "MagentoBackground");
		thread.start();
		m_ProgressDialog = ProgressDialog.show(BudgetDateRangeView.this,
				"Please wait...", "Retrieving data ...", true);

	}

	private Runnable returnRes = new Runnable() {

		@Override
		public void run() {
			if (m_orders != null && m_orders.size() > 0) {
				m_adapter.notifyDataSetChanged();
				for (int i = 0; i < m_orders.size(); i++)
					m_adapter.add(m_orders.get(i));
			}
			m_ProgressDialog.dismiss();
			m_adapter.notifyDataSetChanged();
		}
	};

	private void getOrders() {
		try {

			// m_orders = new ArrayList<ExpenseEntry>();
			ArrayList<ExpenseEntry> m_orders = BudgetUpdateAdapter
					.getAllExpenseBetweenDate(StartDate1, EndData2);
			// ArrayList<ExpenseEntry> m_orders =
			// EntryUpdateDbAdapter.getAllExpense3();
			// ExpenseEntry expensentry = new ExpenseEntry();
			/*
			 * Order o1 = new Order(); o1.setOrderName("SF services");
			 * o1.setOrderStatus("Pending"); Order o2 = new Order();
			 * o2.setOrderName("SF Advertisement");
			 * o2.setOrderStatus("Completed"); m_orders.add(o1);
			 * m_orders.add(o2);
			 */
			Thread.sleep(500);
			Log.i("ARRAY", "" + m_orders.size());
		} catch (Exception e) {
			Log.e("BACKGROUND_PROC", e.getMessage());
		}
		runOnUiThread(returnRes);
	}

	private class OrderAdapter extends ArrayAdapter<ExpenseEntry> {

		private ArrayList<ExpenseEntry> items;

		public OrderAdapter(Context context, int textViewResourceId,
				ArrayList<ExpenseEntry> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			ExpenseEntry o = items.get(position);
			// TextView tt = (TextView) v.findViewById(R.id.toptext);

			// tt.setText("Name:Kolade");
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.amounttext);
				TextView dt = (TextView) v.findViewById(R.id.entrydate);
				if (tt != null) {
					tt.setText(o.getEntryName());
					// tt.setText("Name:Kolade");
				}
				if (bt != null) {
					bt.setText("Amount: " + o.getEntryAmount());
				}
				if (dt != null) {
					dt.setText(o.getEntryDate());
				}
			}
			return v;
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something with the data
		ExpenseEntry ExpenseEntry = (com.dianet.efd_app.ExpenseEntry) getListView()
				.getItemAtPosition(position);
		String expenseAmount = ExpenseEntry.getEntryAmount();
		Toast.makeText(getApplicationContext(), expenseAmount,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_budget_setup, menu);
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
		case R.id.export_budgets:
			BudgetUpdateAdapter.excellexport(StartDate1, EndData2);
			Toast.makeText(getApplicationContext(),
					"File Exported Successful To Download Folder",
					Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}