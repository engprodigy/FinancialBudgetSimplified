package com.dianet.efd_app;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;









//import com.refresh.pos.ui.component.UpdatableFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
public class budgetupdatefragment extends UpdatableFragment {

	public static final String EXTRA_STRING_NAME7 = "extraStringName7";
	protected static final int REQUEST_CODE7 = 7;
	private ListView budgetListView;
	private ArrayList<ExpenseEntry> m_orders = null;
	List<Map<String, String>> BudgetList;
	List<BudgetDescription> list = null;
	ArrayList<String> list2 = null;
	//private OrderAdapter m_adapter;

	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		View view = inflater.inflate(R.layout.budget_view, container, false);
		budgetListView = (ListView) view.findViewById(R.id.budgetlistview);
		
		// Intent intent = new Intent(getActivity().getBaseContext(), BudgetViewIntent.class);

		//startActivityForResult(intent, REQUEST_CODE); 
		initUI();
		return view;
	}
	
	private void initUI() {
		
		update();
		 budgetListView.setOnItemClickListener(new OnItemClickListener() {
             public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
	           String BudgetName = BudgetList.get(position).get("_budget_name");
	         String BudgetAmount = BudgetList.get(position).get("_budget_amount2");
	           Intent newActivity = new Intent(getActivity().getBaseContext(), MonthlyBudgetAmountSetupMainActivity.class);
                     newActivity.putExtra("Value1", BudgetName);
                     newActivity.putExtra("Value2", BudgetAmount);
               startActivity(newActivity);  
 }     
});

		
	}


	public void update() {
		
		Intent intent7 = new Intent(getActivity().getBaseContext(), BudgetViewIntent.class);

		startActivityForResult(intent7, REQUEST_CODE7); 
		//search();
		
		//list = BudgetTestData();
		//showList(list);
		
	}
	
	private void showList(List<BudgetDescription> list) {

		BudgetList = new ArrayList<Map<String, String>>();
		for (BudgetDescription sale : list) {
			BudgetList.add(sale.toMap());
		}
		
		SimpleAdapter sAdap = new SimpleAdapter(getActivity().getBaseContext() , BudgetList,
				R.layout.row2, new String[] { "_budget_name", "_budget_amount", "_budget_spent_amount", "_budget_amount2"},
				new int[] { R.id.toptext, R.id.amounttext , R.id.entrydate});
		budgetListView.setAdapter(sAdap);
	}
	
	
	private void showList2(List<String> list) {

		BudgetList = new ArrayList<Map<String, String>>();
		for (String budgets : list) {
			BudgetDescription budgetentry = new BudgetDescription();
			budgetentry.setID(2);
			// expensentry.setName(cursor.getString(1));
			budgetentry.setBudgetName("Shirts");
			budgetentry.setBudgetAmount("2000");
			budgetentry.setBudgetSpentAmount("5000");
			BudgetList.add(budgetentry.toMap());
		}
		
		SimpleAdapter sAdap = new SimpleAdapter(getActivity().getBaseContext() , BudgetList,
				R.layout.row2, new String[] { "_budget_name", "_budget_amount", "_budget_spent_amount"},
				new int[] { R.id.toptext, R.id.amounttext , R.id.entrydate});
		budgetListView.setAdapter(sAdap);
	}
	
	private ArrayList<BudgetDescription> BudgetTestData() {
		
		ArrayList<BudgetDescription> budgetList = new ArrayList<BudgetDescription>();
		BudgetDescription budgetentry = new BudgetDescription();
		budgetentry.setID(2);
		// expensentry.setName(cursor.getString(1));
		budgetentry.setBudgetName("Shirts");
		budgetentry.setBudgetAmount("2000");
		budgetentry.setBudgetSpentAmount("5000");
		
		// Adding contact to list
		budgetList.add(budgetentry);
		return budgetList;
    }
	

public ArrayList<BudgetDescription> BudgetTestData2(ArrayList<String> list) {
	  
	         int j = 0;
	         int k = 0;  
	         ArrayList<BudgetDescription> budgetList = new ArrayList<BudgetDescription>();
	        
	       /* String[] budgetEntryNameArray = new String[list.size()/3];
	        String budgetBudgetAmountArray[] = new String[list.size()/3];
	        String budgetBudgetSpentamountArray[] = new String[list.size()/3];
	       
	        BudgetDescription[] budgetentry = new BudgetDescription[list.size()/3];*/
	        
	        String[] budgetEntryNameArray = new String[4];
	        String budgetBudgetAmountArray[] = new String[4];
	        String budgetBudgetSpentamountArray[] = new String[4];
	       
	        //BudgetDescription[] budgetentry = new BudgetDescription[4];
	        //BudgetDescription budgetentry = new BudgetDescription();
	
	        for(int i = 0; i<list.size(); i = i + 3){
	        	 //for(int i = 0; i< 5; i++){
	        	 
	        	        k++;
	        	        BudgetDescription budgetentry = new BudgetDescription();
	        	 String  budgetentrydata2 = list.get(i);
	        	// budgetEntryNameArray[j] = list.get(i);
	        	 //budgetentry[i].setBudgetName(budgetentrydata2);
	        		   
	        	 String  budgetentrydata3 = list.get(i + 1);
	        	// budgetBudgetAmountArray[j] = list.get(i + 1);
	        	// budgetentry[i].setBudgetAmount(budgetentrydata3);
	        		   
	        	String  budgetentrydata4 = list.get(i + 2);
	        	// budgetBudgetSpentamountArray[j]= list.get(i + 2);
	        	// budgetentry[i].setBudgetSpentAmount(budgetentrydata4);
	        		    
	        	// budgetList.add(budgetentry[i]);
	        	 j++;
	        	// i = i + 3;
	        	 budgetentry.setBudgetName(budgetentrydata2);
	     		budgetentry.setBudgetAmount(budgetentrydata3);
	     		budgetentry.setBudgetSpentAmount(budgetentrydata4);
	     		budgetList.add(budgetentry);
	        		 
              }
		
		
		return budgetList;
    }

            
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data7) {
	    // Check which request we're responding to
	    if (requestCode == REQUEST_CODE7) {
	        // Make sure the request was successful
	        if (resultCode == getActivity().RESULT_OK) {
	        	
	        	list2 = data7.getStringArrayListExtra (EXTRA_STRING_NAME7);
	        	list = BudgetTestData2(list2);
	        	showList(list);
	        	//list = BudgetTestData();
	    		//showList(list);
	        }
	    }
	    
	}
	
}