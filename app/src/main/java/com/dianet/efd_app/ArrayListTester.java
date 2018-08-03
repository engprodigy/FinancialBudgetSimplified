package com.dianet.efd_app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

@TargetApi(11)
public class ArrayListTester extends ListActivity {

	EntryUpdateDbAdapter EntryUpdateDbAdapter;
	BudgetUpdateAdapter BudgetUpdateAdapter;
	BudgetAmountAdapter BudgetAmountAdapter;
	TagDescriptionUpdateAdapter TagDescriptionUpdateAdapter;
	private ProgressDialog m_ProgressDialog = null;
	private ArrayList<ExpenseEntry> m_orders = null;
	private OrderAdapter m_adapter;
	private Runnable viewOrders;
	public String dateDayValue2 = "0";
	public String dateDayValue;
	public static String expenseAmount2;
	public static String expenseName;
	public static int entryID;
	public static TextView MyDatevalue;
	public static View convertView;

	@SuppressLint("ValidFragment")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arraylist_test);
		EntryUpdateDbAdapter = new EntryUpdateDbAdapter(this);
		EntryUpdateDbAdapter = EntryUpdateDbAdapter.open();

		BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
		BudgetUpdateAdapter = BudgetUpdateAdapter.open();

		BudgetAmountAdapter = new BudgetAmountAdapter(this);
		BudgetAmountAdapter = BudgetAmountAdapter.open();

		TagDescriptionUpdateAdapter = new TagDescriptionUpdateAdapter(this);
		TagDescriptionUpdateAdapter = TagDescriptionUpdateAdapter.open();

		MyDatevalue = (TextView) findViewById(R.id.myViewDate);

		// View v = convertView;
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.row, null);
		}

		ArrayList<ExpenseEntry> m_orders = EntryUpdateDbAdapter.getAllExpense3();
		// ArrayList<ExpenseEntry> m_orders =
		// BudgetUpdateAdapter.getAllExpense3();
		// m_orders = new ArrayList<Order>();
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
		m_ProgressDialog = ProgressDialog.show(ArrayListTester.this,
				"Please wait...", "Retrieving data ...", true);

		@SuppressLint("NewApi")
		class DatePickerFragment extends DialogFragment implements
				DatePickerDialog.OnDateSetListener {

			@SuppressLint("NewApi")
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				// Use the current date as the default date in the picker
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);

				// Create a new instance of DatePickerDialog and return it
				return new DatePickerDialog(getActivity(), this, year, month,
						day);
			}

			public void onDateSet(DatePicker view, int year, int month, int day) {
				// Do something with the date chosen by the user
				String dateYear2 = Integer.toString(year);
				month++;
				String dateMonth2 = Integer.toString(month);
				if (month < 10) {
					dateMonth2 = "0" + dateMonth2;
				}
				String dateDay2 = Integer.toString(day);
				if (day < 10) {
					dateDay2 = "0" + dateDay2;
				}
				String dateDayValue3 = dateDay2 + "/" + dateMonth2 + "/"
						+ dateYear2;
				// String dateDayValue = dateYear2 + "-" + dateMonth2 + "-" +
				// dateDay2;
				dateDayValue = dateYear2 + "-" + dateMonth2 + "-" + dateDay2;
				Toast.makeText(getApplicationContext(), dateDayValue3,
						Toast.LENGTH_SHORT).show();
				TextView StartDatevalue = (TextView) findViewById(R.id.StartDateView);
				StartDatevalue.setText(dateDayValue);
			}

		}

		final Button buttondateupdate = (Button) findViewById(R.id.StartDate);
		buttondateupdate.setOnClickListener(new View.OnClickListener() {
			@SuppressLint({ "NewApi", "NewApi", "ValidFragment" })
			public void onClick(View v) {

				// Intent intent = new Intent(MainActivity.this,
				// Accounts_viewActivity.class);

				// startActivity(intent);
				// public void showDatePickerDialog(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getFragmentManager(), "datePicker");
				// Toast.makeText(getApplicationContext(), "here for real",
				// Toast.LENGTH_SHORT).show();
				// }

			}
		});

		@SuppressLint("NewApi")
		class DatePickerFragment2 extends DialogFragment implements
				DatePickerDialog.OnDateSetListener {

			@SuppressLint("NewApi")
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				// Use the current date as the default date in the picker
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);

				// Create a new instance of DatePickerDialog and return it
				return new DatePickerDialog(getActivity(), this, year, month,
						day);
			}

			public void onDateSet(DatePicker view, int year, int month, int day) {
				// Do something with the date chosen by the user
				String dateYear2 = Integer.toString(year);
				month++;
				String dateMonth2 = Integer.toString(month);
				if (month < 10) {
					dateMonth2 = "0" + dateMonth2;
				}
				String dateDay2 = Integer.toString(day);
				if (day < 10) {
					dateDay2 = "0" + dateDay2;
				}
				String dateDayValue4 = dateDay2 + "/" + dateMonth2 + "/"
						+ dateYear2;
				// String dateDayValue = dateYear2 + "-" + dateMonth2 + "-" +
				// dateDay2;
				dateDayValue2 = dateYear2 + "-" + dateMonth2 + "-" + dateDay2;
				Toast.makeText(getApplicationContext(), dateDayValue4,
						Toast.LENGTH_SHORT).show();
				TextView EndDatevalue = (TextView) findViewById(R.id.endDateView);
				EndDatevalue.setText(dateDayValue2);
			}

		}

		final Button buttondateupdate2 = (Button) findViewById(R.id.EndDate);
		buttondateupdate2.setOnClickListener(new View.OnClickListener() {
			@SuppressLint({ "NewApi", "NewApi", "ValidFragment" })
			public void onClick(View v) {

				// Intent intent = new Intent(MainActivity.this,
				// Accounts_viewActivity.class);

				// startActivity(intent);
				// public void showDatePickerDialog(View v) {
				DialogFragment newFragment = new DatePickerFragment2();
				newFragment.show(getFragmentManager(), "datePicker");
				// Toast.makeText(getApplicationContext(), "here for real",
				// Toast.LENGTH_SHORT).show();
				// }

			}
		});

		final Button buttondateupdate3 = (Button) findViewById(R.id.entryRetrieve);
		buttondateupdate3.setOnClickListener(new View.OnClickListener() {
			@SuppressLint({ "NewApi", "NewApi", "ValidFragment" })
			public void onClick(View v) {

				TextView StartDatevalue = (TextView) findViewById(R.id.StartDateView);
				String StartDatevalue2 = StartDatevalue.getText().toString();

				//

				TextView EndDatevalue = (TextView) findViewById(R.id.endDateView);
				String EndDatevalue2 = EndDatevalue.getText().toString();

				//

				if (StartDatevalue2.equals(" ")) {
					Toast.makeText(getApplicationContext(),
							"Please Select A Start Date", Toast.LENGTH_SHORT)
							.show();
				} else if (EndDatevalue2.equals(" ")) {
					Toast.makeText(getApplicationContext(),
							"Please Select A End Date", Toast.LENGTH_SHORT)
							.show();
				} else {
					int EndDatevalue3 = BudgetUpdateAdapter
							.getJulianEntryDate(EndDatevalue2);
					int StartDatevalue3 = BudgetUpdateAdapter
							.getJulianEntryDate(StartDatevalue2);
					if (StartDatevalue3 > EndDatevalue3) {
						Toast.makeText(
								getApplicationContext(),
								"Invalid Date Range Error: Start Date is Greater than End Date",
								Toast.LENGTH_SHORT).show();
					} else {
						Intent intent = new Intent(ArrayListTester.this,
								BudgetDateRangeView.class);
						intent.putExtra("Value1", StartDatevalue2);
						intent.putExtra("Value2", EndDatevalue2);
						startActivity(intent);
					}
				}

			}
		});

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
			ArrayList<ExpenseEntry> m_orders = EntryUpdateDbAdapter
					.getAllExpense2();
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
		expenseName = ExpenseEntry.getEntryName();
		entryID = ExpenseEntry.getID();
		expenseAmount2 = expenseAmount;
		// Toast.makeText(getApplicationContext(), expenseAmount,
		// Toast.LENGTH_SHORT).show();
		showDialog(expenseAmount);
	}

	public void onItemLongClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub

	}

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

	void showDialog(String arg2) {
		DialogFragment newFragment = MyAlertDialogFragment
				.newInstance(R.string.alert_dialog_title);
		newFragment.show(getFragmentManager(), "dialog");
	}

	public void doPositiveClick(String args) {
		// Do stuff here.

		Log.i("FragmentAlertDialog", "Positive click!");
	}

	public void doNegativeClick(String args, int args2, String args3) {
		// Do stuff here.

		EntryUpdateDbAdapter.deleteExpenseEntry(args2);
		boolean emptytablekey = BudgetUpdateAdapter.deleteExpenseEntry(args2,
				args3);
		// Toast.makeText(getApplicationContext(), args + args2,
		// Toast.LENGTH_SHORT).show();
		// Log.i("FragmentAlertDialog", "Negative click!");
		if (emptytablekey) {
			TagDescriptionUpdateAdapter.deleteExpenseEntry(args3);
			BudgetAmountAdapter.deleteExpenseEntry(args3);
		}
		finish();
		startActivity(getIntent());
	}

	public static class MyAlertDialogFragment extends DialogFragment {

		public static MyAlertDialogFragment newInstance(int title) {
			MyAlertDialogFragment frag = new MyAlertDialogFragment();
			Bundle args = new Bundle();
			args.putInt("title", title);
			frag.setArguments(args);
			return frag;
		}

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
					.setPositiveButton(R.string.alert_dialog_cancel,
							new DialogInterface.OnClickListener() {
								// private String expenseAmount3;

								public void onClick(DialogInterface dialog,
										int whichButton) {

									((ArrayListTester) getActivity())
											.doPositiveClick(expenseAmount2);
								}
							})
					.setNegativeButton(R.string.alert_dialog_delete,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									((ArrayListTester) getActivity())
											.doNegativeClick(expenseAmount2,
													entryID, expenseName);
								}
							}).create();
		}
	}

}
