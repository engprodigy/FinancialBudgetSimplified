package com.dianet.efd_app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
//import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

//import com.refresh.pos.R;
//import android.support.v4.view.PagerAdapter;


@TargetApi(11)
@SuppressLint("NewApi")
//public class MainActivity extends FragmentActivity {
	public class MainActivity extends AppCompatActivity {

	protected static final int REQUEST_CODE = 0;
	private ViewPager viewPager;
	private Spinner TransactionType;
	private Spinner EditCash;
	private EditText EditAmount;
	private EditText EditTag;
	// private EditText EditCash;
	// private EditText EditDescription;
	private TextView SaveDate;
	public String dateDayValue2 = "0";
	public String newDateStr2 = "0";
	public String newDateStr3 = "0";
	public DialogFragment newFragment;
	public String EditTagString2;
	public int EditAmountInteger2 = 0;
	public int ExpenseBudgetAmount2 = 0;
	public String EditAccountTypeString2;
	public String EditDateString2;
	public String TransactionTypeString2;
	String category = null;

	
	private static boolean SDK_SUPPORTED;
	private PagerAdapter pagerAdapter;
	private Resources res;

	AccountTypeDbAdapter accountTypeDbAdapter;
	EntryUpdateDbAdapter EntryUpdateDbAdapter;
	TagDescriptionUpdateAdapter TagDescriptionUpdateAdapter;
	BudgetUpdateAdapter BudgetUpdateAdapter;
	BudgetAmountAdapter BudgetAmountAdapter;


	
	private void initiateCoreApp() {
		
		
		DateTimeStrategy.setLocale("en", "EN");
		
	}
	
	
	@SuppressLint("NewApi")
	/**
	 * Initiate this UI.
	 */
	private void initiateActionBar() {
		if (SDK_SUPPORTED) {
			//ActionBar actionBar = getActionBar();
			ActionBar actionBar = getSupportActionBar();
			//final Tab tab = getSupportActionBar.newTab();

			//assert actionBar != null;
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

			//ActionBar.TabListener tabListener = new ActionBar.TabListener() {
				TabListener tabListener = new TabListener() {
					@Override
					public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

						viewPager.setCurrentItem(tab.getPosition());
					}

					@Override
					public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

					}

					@Override
					public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

					}

					//@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
				}

				//@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) {
					viewPager.setCurrentItem(tab.getPosition());
				}

				//@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				}
			};
		actionBar.addTab(actionBar.newTab().setText(res.getString(R.string.menu_home))
					.setTabListener(tabListener), 0, true);
			actionBar.addTab(actionBar.newTab().setText(res.getString(R.string.menu_budgets))
					.setTabListener(tabListener), 1, false);
			actionBar.
					addTab(actionBar.newTab().setText(res.getString(R.string.menu_settings))
					.setTabListener(tabListener), 2, false);
	
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				actionBar.setBackgroundDrawable(new ColorDrawable(Color
						.parseColor("#73bde5")));
			}

		}
	}
	
	

	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		res = getResources();
		setContentView(R.layout.layout_main);
		viewPager = (ViewPager) findViewById(R.id.pager);
		super.onCreate(savedInstanceState);
		SDK_SUPPORTED = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

		initiateActionBar();
		FragmentManager fragmentManager = getSupportFragmentManager();
		pagerAdapter = new PagerAdapter(fragmentManager, res);
		viewPager.setAdapter(pagerAdapter);
		viewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						if (SDK_SUPPORTED)
							//getActionBar().setSelectedNavigationItem(position);
							getSupportActionBar().setSelectedNavigationItem(position);
					}
				});
		viewPager.setCurrentItem(0);
		
		accountTypeDbAdapter = new AccountTypeDbAdapter(this);
		accountTypeDbAdapter = accountTypeDbAdapter.open() ;
		
		EntryUpdateDbAdapter = new EntryUpdateDbAdapter(this);
        EntryUpdateDbAdapter = EntryUpdateDbAdapter.open(); 
        
        TagDescriptionUpdateAdapter = new TagDescriptionUpdateAdapter(this);
        TagDescriptionUpdateAdapter = TagDescriptionUpdateAdapter.open();
        
        BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
        BudgetUpdateAdapter = BudgetUpdateAdapter.open();
        
        BudgetAmountAdapter = new BudgetAmountAdapter(this);
        BudgetAmountAdapter = BudgetAmountAdapter.open();
        
        Boolean TagStatus = accountTypeDbAdapter.confirmEntryAccountType1(1);
		if (TagStatus) {
			accountTypeDbAdapter.insertEntry("Account 1", 0);
		}
		;

		Boolean TagStatus2 = accountTypeDbAdapter.confirmEntryAccountType1(2);
		if (TagStatus2) {
			accountTypeDbAdapter.insertEntry("Account 2", 0);
		}
		;
		Intent intent10 = getIntent();
		category = intent10.getStringExtra(CategoryListAdapter.categorytype);
        
        
        
	//}
		
		
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		// final AccountTypeDbAdapter accountTypeDbAdapter;
		// EditAmount = (EditText) findViewById(R.id.edit_amount);
		// EditAmount.setText(amountDefaultValue);
		// final TextView Allaccountsvalue;
		/*
		 * @Override public boolean onCreateOptionsMenu(Menu menu) {
		 * getMenuInflater().inflate(R.menu.activity_main, menu); return true;
		 */

	/*	Spinner spinner = (Spinner) findViewById(R.id.transaction_type);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		// String TransactionTypeString;
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.transcationtype_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				// String TransactionTypeString =
				// arg0.getItemAtPosition(arg2).toString();
				// Toast.makeText(getApplicationContext(),
				// "You selected "+(CharSequence) arg0.getItemAtPosition(arg2),
				// Toast.LENGTH_SHORT).show();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		// final String dateDayValue2 = " ";
		class DatePickerFragment extends DialogFragment implements
				DatePickerDialog.OnDateSetListener {

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
				// String dateDayValue = dateDay2 + "/" + dateMonth2 + "/" +
				// dateYear2;
				String dateDayValue = dateYear2 + "-" + dateMonth2 + "-"
						+ dateDay2;
				dateDayValue2 = dateYear2 + "-" + dateMonth2 + "-" + dateDay2;
				// Toast.makeText(getApplicationContext(), dateDayValue2,
				// Toast.LENGTH_SHORT).show();
				TextView Allaccountsvalue = (TextView) findViewById(R.id.myViewDate);
				Allaccountsvalue.setText(dateDayValue);
			}

		}

		final Button button = (Button) findViewById(R.id.addnewaccount);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this,
						Accounts_viewActivity.class);

				startActivityForResult(intent, REQUEST_CODE);
				// public void showDatePickerDialog(View v) {

				// newFragment.show(getFragmentManager(), "datePicker");
				// Toast.makeText(getApplicationContext(), "here for real",
				// Toast.LENGTH_SHORT).show();
				// }

			}
		});

		final Button buttondateupdate = (Button) findViewById(R.id.dateupdate);
		buttondateupdate.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ValidFragment")
			public void onClick(View v) {

				// Intent intent = new Intent(MainActivity.this,
				// Accounts_viewActivity.class);

				// startActivity(intent);
				// public void showDatePickerDialog(View v) {
				newFragment = new DatePickerFragment();
				newFragment.show(getFragmentManager(), "datePicker");
				// }

			}
		});

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

		Boolean TagStatus = accountTypeDbAdapter.confirmEntryAccountType1(1);
		if (TagStatus) {
			accountTypeDbAdapter.insertEntry("Account 1", 0);
		}
		;

		Boolean TagStatus2 = accountTypeDbAdapter.confirmEntryAccountType1(2);
		if (TagStatus2) {
			accountTypeDbAdapter.insertEntry("Account 2", 0);
		}
		;

		Date datedisplay = new Date();
		// SimpleDateFormat postFormater = new SimpleDateFormat("dd/MM/yyyy");
		// String newDateStr = postFormater.format(datedisplay);
		// SimpleDateFormat postFormater2 = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat postFormater2 = new SimpleDateFormat("yyyy-MM-dd");
		String newDateStr = postFormater2.format(datedisplay);
		// String newDateStr = newDateStr2.toString();

		// newDateStr3 = dateDayValue2;

		// newDateStr3 = newDateStr2 ;

		int amountVal1 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(1);
		int amountVal2 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(2);
		int amountVal = amountVal1 + amountVal2;
		// String expenseAmount = EntryUpdateDbAdapter.getExpenseEntry(2);
		// String expenseAmount =
		// EntryUpdateDbAdapter.getExpenseEntrybyDate("12/05/2013");
		// int amountVal = 23000;
		String amountVal3 = Integer.toString(amountVal);
		// String expenseAmount2 = Integer.toString(expenseAmount);
		TextView Allaccountsvalue = new TextView(this);
		Allaccountsvalue = (TextView) findViewById(R.id.allaccountsvalue);
		Allaccountsvalue.setText(amountVal3);
		// Toast.makeText(getApplicationContext(), amountVal3,
		// Toast.LENGTH_SHORT).show();

		TextView MyDatevalue = new TextView(this);
		MyDatevalue = (TextView) findViewById(R.id.myViewDate);
		MyDatevalue.setText(newDateStr);

		String[] fromColumns = TagDescriptionUpdateAdapter.getTagDescription();
		String[] fromColumns2 = accountTypeDbAdapter.getTagDescription();
		// Get a reference to the AutoCompleteTextView in the layout
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.edit_tag);

		// AutoCompleteTextView textView2 = (AutoCompleteTextView)
		// findViewById(R.id.edit_cash);
		Spinner spinner2 = (Spinner) findViewById(R.id.edit_cash);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, fromColumns);
		textView.setAdapter(adapter1);

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, fromColumns2);
		spinner2.setAdapter(adapter2);

		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				// String EditCashString =
				// arg0.getItemAtPosition(arg2).toString();
				// Toast.makeText(getApplicationContext(),
				// "You selected "+(CharSequence) arg0.getItemAtPosition(arg2),
				// Toast.LENGTH_SHORT).show();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		final Button button2 = (Button) findViewById(R.id.savebutton);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				TransactionType = (Spinner) findViewById(R.id.transaction_type);

				String TransactionTypeString = TransactionType
						.getSelectedItem().toString();

				TransactionTypeString2 = TransactionTypeString;

				EditAmount = (EditText) findViewById(R.id.edit_amount);
				String EditAmountString = EditAmount.getText().toString();

				// int EditAmountInteger = 2000;

				// String EditTagString = "expenseDefault";

				// String EditAccountTypeString = "expenseDefault";

				// String EditDateString = "12/05/2013";

				EditTag = (EditText) findViewById(R.id.edit_tag);
				String EditTagString = EditTag.getText().toString();

				EditTagString2 = EditTagString;
				// EditCash = (EditText) findViewById(R.id.edit_cash);
				// String EditAccountTypeString = EditCash.getText().toString();

				EditCash = (Spinner) findViewById(R.id.edit_cash);

				String EditAccountTypeString = EditCash.getSelectedItem()
						.toString();
				EditAccountTypeString2 = EditAccountTypeString;

				SaveDate = (TextView) findViewById(R.id.myViewDate);
				String EditDateString = SaveDate.getText().toString();
				EditDateString2 = EditDateString;
				newDateStr3 = SaveDate.getText().toString();

				if (EditAmountString.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter an amount or enter 0 if none",
							Toast.LENGTH_SHORT).show();
					// Toast.makeText(getApplicationContext(), EditDateString,
					// Toast.LENGTH_SHORT).show();
				} else if (TransactionTypeString2.equals("Income")) {

					int EditAmountInteger = Integer.parseInt(EditAmountString);
					accountTypeDbAdapter.UpdateEntryAccountAmount2(
							EditAccountTypeString, EditAmountInteger);
					EditAmount.setText("");
					EditTag.setText("");
					EditCash.setTag("");
					int amountVal1 = accountTypeDbAdapter
							.getSinlgeEntryAccountAmount2(1);
					int amountVal2 = accountTypeDbAdapter
							.getSinlgeEntryAccountAmount2(2);
					int amountVal = amountVal1 + amountVal2;
					String amountVal3 = Integer.toString(amountVal);
					// String expenseAmount2 = Integer.toString(expenseAmount);
					TextView Allaccountsvalue;
					Allaccountsvalue = (TextView) findViewById(R.id.allaccountsvalue);
					Allaccountsvalue.setText(amountVal3);
					Toast.makeText(getApplicationContext(),
							"Income Transaction Completed", Toast.LENGTH_SHORT)
							.show();

				} else if (TransactionTypeString2.equals("Transfer")) {

					Toast.makeText(getApplicationContext(),
							"Transfer Transaction Selected(Not Yet Available)",
							Toast.LENGTH_SHORT).show();

				} else if (TransactionTypeString2.equals("Budgets")) {

					int EditAmountInteger = Integer.parseInt(EditAmountString);
					EditAmountInteger2 = EditAmountInteger;

					if (EditTagString.equals("")) {

						Toast.makeText(
								getApplicationContext(),
								"Please select an expense type tag or enter a new expense type tag",
								Toast.LENGTH_SHORT).show();

					} else {

						Boolean CheckTagStatus = BudgetAmountAdapter
								.getExpenseTagStatus(EditTagString);
						if (!CheckTagStatus) {

							BudgetAmountAdapter
									.insertEntryExpenseUpdateBudget2(
											EditTagString, EditAmountInteger);
						} else {

							BudgetAmountAdapter.UpdateExpenseAmountByTag(
									EditTagString, EditAmountInteger);

						}
						Boolean TagStatus = TagDescriptionUpdateAdapter
								.CheckTagDescription(EditTagString);
						if (TagStatus) {
							TagDescriptionUpdateAdapter
									.insertEntryExpenseUpdate(EditTagString);
						}
						;
						EditAmount.setText("");
						EditTag.setText("");
						EditCash.setTag("");

						Toast.makeText(getApplicationContext(),
								"Budget Updated Succesfully",
								Toast.LENGTH_SHORT).show();
					}
				}

				else if (TransactionTypeString2.equals("Balance Reset")) {

					Toast.makeText(
							getApplicationContext(),
							"Balance Reset Transaction Selected(Not Yet Available)",
							Toast.LENGTH_SHORT).show();

				}

				else if (EditTagString.equals("")) {

					Toast.makeText(
							getApplicationContext(),
							"Please select an expense type tag or enter a new expense type tag",
							Toast.LENGTH_SHORT).show();

				} else if (EditAccountTypeString.equals("")) {

					Toast.makeText(getApplicationContext(),
							"Please select an account type", Toast.LENGTH_SHORT)
							.show();

				}

				else {
					int EditAmountInteger = Integer.parseInt(EditAmountString);
					EditAmountInteger2 = EditAmountInteger;

					int expenseTotalAmount = BudgetUpdateAdapter
							.getTotalExpenseAmountByTag2(EditTagString);
					int expenseBudgetAmount = BudgetAmountAdapter
							.getExpenseAmountByTag3(EditTagString);
					ExpenseBudgetAmount2 = expenseBudgetAmount;

					int newTotalExpenseAmount = expenseTotalAmount
							+ EditAmountInteger;

					if (newTotalExpenseAmount > expenseBudgetAmount) {
						//showDialog2();
						return;

					}
					EntryUpdateDbAdapter.insertEntryExpenseUpdate(
							TransactionTypeString, EditAmountInteger,
							EditTagString, EditAccountTypeString,
							EditDateString);
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
							EditAmountInteger, newDateStr3);

					Boolean TagStatus = TagDescriptionUpdateAdapter
							.CheckTagDescription(EditTagString);
					if (TagStatus) {
						TagDescriptionUpdateAdapter
								.insertEntryExpenseUpdate(EditTagString);
					}
					;
					EditAmount.setText("");
					EditTag.setText("");
					EditCash.setTag("");
					int amountVal1 = accountTypeDbAdapter
							.getSinlgeEntryAccountAmount2(1);
					int amountVal2 = accountTypeDbAdapter
							.getSinlgeEntryAccountAmount2(2);
					int amountVal = amountVal1 + amountVal2;
					String amountVal3 = Integer.toString(amountVal);
					// String expenseAmount2 = Integer.toString(expenseAmount);
					TextView Allaccountsvalue;
					Allaccountsvalue = (TextView) findViewById(R.id.allaccountsvalue);
					Allaccountsvalue.setText(amountVal3);
					Toast.makeText(getApplicationContext(),
							"Update Successful", Toast.LENGTH_SHORT).show();

				}

			}

		});*/

	}
	
	
	
		

//	@Override
	/*public boolean onCreateOptionsMenu(Menu menu) {
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
			// Intent intent3 = new Intent(this, BudgetSetup.class);
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
	
	
	
	

	@SuppressLint({ "ValidFragment", "ValidFragment" })
	void showDialog2() {
		DialogFragment newFragment2 = MyAlertDialogFragment2
				.newInstance(R.string.home_alert_dialog_title);
		newFragment2.show(getFragmentManager(), "dialog2");
	}

	public void doPositiveClick(String args) {
		// Do stuff here.
		// return;
	}

	
	public void doNegativeClick(String args, int args2) {
		// Do stuff here.

		EntryUpdateDbAdapter.insertEntryExpenseUpdate(TransactionTypeString2,
				EditAmountInteger2, EditTagString2, EditAccountTypeString2,
				EditDateString2);
		accountTypeDbAdapter.UpdateEntryAccountAmount(EditAccountTypeString2,
				EditAmountInteger2);
		Boolean CheckTagStatus = BudgetAmountAdapter
				.getExpenseTagStatus(EditTagString2);
		if (!CheckTagStatus) {

			BudgetAmountAdapter.insertEntryExpenseUpdateBudget(EditTagString2,
					EditAmountInteger2);
		}

		BudgetUpdateAdapter.insertEntryExpenseUpdateBudget(EditTagString2,
				ExpenseBudgetAmount2, EditAmountInteger2, newDateStr3);

		Boolean TagStatus = TagDescriptionUpdateAdapter
				.CheckTagDescription(EditTagString2);
		if (TagStatus) {
			TagDescriptionUpdateAdapter
					.insertEntryExpenseUpdate(EditTagString2);
		}
		;
		EditAmount.setText("");
		EditTag.setText("");
		EditCash.setTag("");
		int amountVal1 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(1);
		int amountVal2 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(2);
		int amountVal = amountVal1 + amountVal2;
		String amountVal3 = Integer.toString(amountVal);
		// String expenseAmount2 = Integer.toString(expenseAmount);
		TextView Allaccountsvalue;
		Allaccountsvalue = (TextView) findViewById(R.id.allaccountsvalue);
		Allaccountsvalue.setText(amountVal3);
		Toast.makeText(getApplicationContext(), "Update Successful",
				Toast.LENGTH_SHORT).show();

		// finish();
		// startActivity(getIntent());
	}
 
 
	public static class MyAlertDialogFragment2 extends DialogFragment {

		public static MyAlertDialogFragment2 newInstance(int title) {
			MyAlertDialogFragment2 frag2 = new MyAlertDialogFragment2();
			Bundle args = new Bundle();
			args.putInt("title", title);
			frag2.setArguments(args);
			return frag2;
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
					.setPositiveButton(R.string.home_alert_dialog_cancel,
							new DialogInterface.OnClickListener() {
								// private String expenseAmount3;

								public void onClick(DialogInterface dialog2,
										int whichButton) {

									String expenseAmount20 = null;
									((MainActivity) getActivity())
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
									((MainActivity) getActivity())
											.doNegativeClick(expenseAmount20,
													entryID20);
								}
							}).create();
		}

		protected void dopositiveClick2() {
			// TODO Auto-generated method stub

		}
	}*/
	
	



	/**
	 * Get view-pager
	 * @return
	 */
	public ViewPager getViewPager() {
		return viewPager;
	}







	public void doNegativeClick(String expenseAmount20, int entryID20) {
		// TODO Auto-generated method stub
		
	}







	public void doPositiveClick(String expenseAmount20) {
		// TODO Auto-generated method stub
		
	}



	
}

/**
 * 
 * @author Refresh team
 *
 */
class PagerAdapter extends FragmentStatePagerAdapter {

	private UpdatableFragment[] fragments;
	private String[] fragmentNames;

	/**
	 * Construct a new PagerAdapter.
	 * @param fragmentManager
	 * @param res
	 */
	public PagerAdapter(FragmentManager fragmentManager, Resources res) {
		
		super(fragmentManager);
		
		UpdatableFragment budgetupdatefragment = new budgetupdatefragment();
		UpdatableFragment HistoryFragment = new HistoryFragment();
		UpdatableFragment homeFragment = new HomeFragment();
		//		saleFragment);

		fragments = new UpdatableFragment[] { homeFragment, budgetupdatefragment, HistoryFragment 
				};
		fragmentNames = new String[] { res.getString(R.string.menu_home),
				res.getString(R.string.menu_budgets),  res.getString(R.string.menu_settings)
				 };

	}
	
	
	@Override
	public Fragment getItem(int i) {
		return fragments[i];
	}

	@Override
	public int getCount() {
		return fragments.length;
	}

	@Override
	public CharSequence getPageTitle(int i) {
		return fragmentNames[i];
	}

	/**
	 * Update
	 * @param index
	 */
	public void update(int index) {
		fragments[index].update();
	}
	
}
