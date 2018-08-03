package com.dianet.efd_app;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

//import com.refresh.pos.ui.component.UpdatableFragment;

//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentIntegratorSupportV4;
//import com.google.zxing.integration.android.IntentResult;


/**
 * UI for Inventory, shows list of Product in the ProductCatalog.
 * Also use for a sale process of adding Product into sale.
 * 
 * @author Refresh Team
 *
 */

@SuppressLint("ValidFragment")
public class HistoryFragment extends UpdatableFragment {
	
	static final int REQUEST_CODE = 0;
	static final int REQUEST_CODE2 = 1;
	//private SaleLedger saleLedger;
	List<Map<String, String>> saleList;
	private ListView budgetreportListView;
	EntryUpdateDbAdapter EntryUpdateDbAdapter;
	BudgetUpdateAdapter BudgetUpdateAdapter;
	BudgetAmountAdapter BudgetAmountAdapter;
	TagDescriptionUpdateAdapter TagDescriptionUpdateAdapter;
	private TextView totalBox;
	private TextView totalBox2;
	private Spinner spinner;
	private Button previousButton;
	private Button nextButton;
	private TextView currentBox;
	private Calendar currentTime;
	String ctime2;
	String etime2;
	private DatePickerDialog datePicker;
	
	public static final int DAILY = 0;
	public static final int WEEKLY = 1;
	public static final int MONTHLY = 2;
	public static final int YEARLY = 3;
	
	ArrayList<String> entrylist2 = null;
	ArrayList<String> incomelist2 = null;
	public static final String EXTRA_STRING_NAME7 = "extraStringName7";
	public static final String EXTRA_STRING_NAME8 = "extraStringName8";
    public static String expenseAmount2;
    public static String expenseName;
    public static int entryID;
	
	

/*	public void update() {
		// TODO Auto-generated method stub
		
		
	}*/
	
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
	
		
		
		
		View view = inflater.inflate(R.layout.layout_report, container, false);
		
		
		
		previousButton = (Button) view.findViewById(R.id.previousButton);
		nextButton = (Button) view.findViewById(R.id.nextButton);
		currentBox = (TextView) view.findViewById(R.id.currentBox);
		budgetreportListView = (ListView) view.findViewById(R.id.saleListView);
		totalBox = (TextView) view.findViewById(R.id.totalBox);
	    totalBox2 = (TextView) view.findViewById(R.id.totalBox2);
		spinner = (Spinner) view.findViewById(R.id.spinner1);
		            
	
	
	
		
		initUI();
		return view;
	}



private void initUI() {
	
	
      //  private void initiateCoreApp() {
		
		
		    DateTimeStrategy.setLocale("en", "EN");
		
	    //  }
	
	currentTime = Calendar.getInstance();
	datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int y, int m, int d) {
			currentTime.set(Calendar.YEAR, y);
			currentTime.set(Calendar.MONTH, m);
			currentTime.set(Calendar.DAY_OF_MONTH, d);
			update();
		}
	}, currentTime.get(Calendar.YEAR), currentTime.get(Calendar.MONTH), currentTime.get(Calendar.DAY_OF_MONTH));
	
	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
            R.array.period, android.R.layout.simple_spinner_item);
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	
	spinner.setAdapter(adapter);
	spinner.setSelection(0);
	spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
		
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {	
			update();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) { }
		
	});
	
	currentBox.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			datePicker.show();
		}
	});
	
	
	
	previousButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			addDate(-1);
		}
	});
	
	nextButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            addDate(1);
        }
    });
	
	budgetreportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	      public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
	    	  String id = saleList.get(position).get("_entry_name").toString();
			  String id2 = saleList.get(position).get("_name");
			  String entryAmount = saleList.get(position).get("_entry_amount");
			  //String id = saleList.get(position).get("_id");
			  Toast.makeText(getActivity().getApplicationContext(),
					  id2+ " " + entryAmount, Toast.LENGTH_SHORT)
					  .show();
	    	  Intent newActivity = new Intent(getActivity().getBaseContext(), HistoryFragmentDelete.class);
	          newActivity.putExtra("id", id);
			  newActivity.putExtra("budget_type", id2);
			  newActivity.putExtra("entry_amount", entryAmount);
	          //startActivity(newActivity);
			  startActivityForResult(newActivity, REQUEST_CODE2);
	      }     
	});


	/*budgetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
			String BudgetName = BudgetList.get(position).get("_budget_name");
			String BudgetAmount = BudgetList.get(position).get("_budget_amount2");
			Intent newActivity = new Intent(getActivity().getBaseContext(), MonthlyBudgetAmountSetupMainActivity.class);
			newActivity.putExtra("Value1", BudgetName);
			newActivity.putExtra("Value2", BudgetAmount);
			startActivity(newActivity);
		}
	});*/

  }


/**
 * Show list.
 * @param list
 */
private void showList(List<ExpenseEntry> list) {

	saleList = new ArrayList<Map<String, String>>();
	for (ExpenseEntry sale : list) {
		saleList.add(sale.toMap());
	}
	
	SimpleAdapter sAdap = new SimpleAdapter(getActivity().getBaseContext() , saleList,
			R.layout.listview_report, new String[] { "_name", "_entry_date", "_entry_amount"},
			new int[] { R.id.sid, R.id.startTime , R.id.total});
	budgetreportListView.setAdapter(sAdap);
}


@Override
public void update() {
	
	/*EntryUpdateDbAdapter = EntryUpdateDbAdapter.open();
	BudgetUpdateAdapter = BudgetUpdateAdapter.open();
	BudgetAmountAdapter = BudgetAmountAdapter.open();
	TagDescriptionUpdateAdapter = TagDescriptionUpdateAdapter.open();*/
	
	int period = spinner.getSelectedItemPosition();
	//List<ExpenseEntry> list = null;
	//ArrayList<ExpenseEntry> expenseList = new ArrayList<ExpenseEntry>();
	//ExpenseEntry expensentry = new ExpenseEntry(3, "food", "food", "2000", "12/12/15");
	//expenseList.add(expensentry);
	Calendar cTime = (Calendar) currentTime.clone();
	Calendar eTime = (Calendar) currentTime.clone();
	
	if(period == DAILY){
		currentBox.setText(" [" + DateTimeStrategy.getSQLDateFormat(currentTime) +  "] ");
		currentBox.setTextSize(16);
		ctime2 = DateTimeStrategy.getSQLDateFormat(currentTime);
		//eTime.add(Calendar.DATE, 1 * -1);
		//etime2 = DateTimeStrategy.getSQLDateFormat(eTime);
		etime2 = DateTimeStrategy.getSQLDateFormat(currentTime);
		
	} else if (period == WEEKLY){
		while(cTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			cTime.add(Calendar.DATE, -1);
			ctime2 = DateTimeStrategy.getSQLDateFormat(cTime);
		}
		
		String toShow = " [" + DateTimeStrategy.getSQLDateFormat(cTime) +  "] ~ [";
		eTime = (Calendar) cTime.clone();
		eTime.add(Calendar.DATE, 7);
		toShow += DateTimeStrategy.getSQLDateFormat(eTime) +  "] ";
		currentBox.setTextSize(16);
		currentBox.setText(toShow);
		etime2 = DateTimeStrategy.getSQLDateFormat(eTime);
	} else if (period == MONTHLY){
		cTime.set(Calendar.DATE, 1);
		eTime = (Calendar) cTime.clone();
		eTime.add(Calendar.MONTH, 1);
		eTime.add(Calendar.DATE, -1);
		currentBox.setTextSize(18);
		currentBox.setText(" [" + currentTime.get(Calendar.YEAR) + "-" + (currentTime.get(Calendar.MONTH)+1) + "] ");
		ctime2 = DateTimeStrategy.getSQLDateFormat(cTime);
		etime2 = DateTimeStrategy.getSQLDateFormat(eTime);
	} else if (period == YEARLY){
		cTime.set(Calendar.DATE, 1);
		cTime.set(Calendar.MONTH, 0);
		eTime = (Calendar) cTime.clone();
		eTime.add(Calendar.YEAR, 1);
		eTime.add(Calendar.DATE, -1);
		currentBox.setTextSize(20);
		currentBox.setText(" [" + currentTime.get(Calendar.YEAR) +  "] ");
		ctime2 = DateTimeStrategy.getSQLDateFormat(cTime);
		etime2 = DateTimeStrategy.getSQLDateFormat(eTime);
	}
	currentTime = cTime;
	Intent intent = new Intent(getActivity().getBaseContext(), HistoryViewIntent.class);
	intent.putExtra("Value1", ctime2);
	intent.putExtra("Value2", etime2);
	//intent.putExtra("Value1", etime2);
	//intent.putExtra("Value2", ctime2);
	
	startActivityForResult(intent, REQUEST_CODE);
	//list = saleLedger.getAllSaleDuring(cTime, eTime);
	//list = EntryUpdateDbAdapter.getAllExpense2();
	/*double total = 0;
	for (Sale sale : list)
		total += sale.getTotal();
	
	totalBox.setText(total + "");*/
	//showList(expenseList);
}

@Override
public void onResume() {
	super.onResume();
	// update();
	// it shouldn't call update() anymore. Because super.onResume() 
	// already fired the action of spinner.onItemSelected()
}

/**
 * Add date.
 * @param increment
 */
private void addDate(int increment) {
	int period = spinner.getSelectedItemPosition();
	if (period == DAILY){
		currentTime.add(Calendar.DATE, 1 * increment);
	} else if (period == WEEKLY){
		currentTime.add(Calendar.DATE, 7 * increment);
	} else if (period == MONTHLY){
		currentTime.add(Calendar.MONTH, 1 * increment);
	} else if (period == YEARLY){
		currentTime.add(Calendar.YEAR, 1 * increment);
	}
	update();
}

public void onActivityResult(int requestCode, int resultCode, Intent data) {
    // Check which request we're responding to
    if (requestCode == REQUEST_CODE) {
        // Make sure the request was successful
        if (resultCode == getActivity().RESULT_OK) {
        	
        	entrylist2 = data.getStringArrayListExtra (EXTRA_STRING_NAME7);
			incomelist2 = data.getStringArrayListExtra (EXTRA_STRING_NAME8);
        	
        	ArrayList<ExpenseEntry> expenseList = new ArrayList<ExpenseEntry>();
			ArrayList<ExpenseEntry> IncomeList = new ArrayList<ExpenseEntry>();
        	//ExpenseEntry expensentry = new ExpenseEntry(3, "food", "food", "2000", "12/12/15");
        	//expenseList.add(expensentry);
        	double total = 0;
			double incometotal = 0;
			double subtotal = 0;
        	
        	for(int i = 0; i<entrylist2.size(); i = i + 4){
	        	 //for(int i = 0; i< 5; i++){
	        	 
	        	       
        		 ExpenseEntry expensentry = new ExpenseEntry();
				String  budgetentrydata1 = entrylist2.get(i);

	        	 String  budgetentrydata2 = entrylist2.get(i + 1);
	        	
	        		   
	        	 String  budgetentrydata3 = entrylist2.get(i + 2);
	        	
	        		   
	        	 String  budgetentrydata4 = entrylist2.get(i + 3);
	        	
	        		    
	        	 int total2 = Integer.parseInt(budgetentrydata2);
	        	     total = total + total2;
	        	 
	        	 expensentry.setID(Integer.parseInt(budgetentrydata1));
	        	 expensentry.setName(budgetentrydata3);
	        	 expensentry.setEntryName(budgetentrydata1);
	        	 expensentry.setEntryAmount(budgetentrydata2);
	        	 expensentry.setEntryDate(budgetentrydata4);
	        	 expenseList.add(expensentry);
	        		 
             }


			for(int i = 0; i<incomelist2.size(); i = i + 4){
				//for(int i = 0; i< 5; i++){


				ExpenseEntry expensentry = new ExpenseEntry();
				String  budgetentrydata1 = incomelist2.get(i);

				String  budgetentrydata2 = incomelist2.get(i + 1);


				String  budgetentrydata3 = incomelist2.get(i + 2);


				String  budgetentrydata4 = incomelist2.get(i + 3);


				int total2 = Integer.parseInt(budgetentrydata2);
				incometotal = incometotal + total2;

				expensentry.setID(Integer.parseInt(budgetentrydata1));
				expensentry.setName(budgetentrydata3);
				expensentry.setEntryName(budgetentrydata1);
				expensentry.setEntryAmount(budgetentrydata2);
				expensentry.setEntryDate(budgetentrydata4);
				IncomeList.add(expensentry);

			}

        	
        	showList(expenseList);
        	
        	/*double total = 0;
        	for (Sale sale : list)
        		total += sale.getTotal();*/
			subtotal = total-incometotal;
        	totalBox.setText(subtotal + "");
			totalBox2.setText(incometotal + "");
        	
                 }
              }

	if (requestCode == REQUEST_CODE2) {

		//Toast.makeText(getActivity().getApplicationContext(),"Delete Transaction Completed", Toast.LENGTH_SHORT).show();

		Intent intent5 = new Intent(getActivity().getBaseContext(), MainActivity.class);
		startActivityForResult(intent5, REQUEST_CODE);

	           }
    
         }




}






