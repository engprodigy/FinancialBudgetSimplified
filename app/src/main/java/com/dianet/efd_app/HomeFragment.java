package com.dianet.efd_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

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
public class HomeFragment extends UpdatableFragment {

	
	protected static final int REQUEST_CODE = 0;
	protected static final int REQUEST_CODE2 = 1;
	protected static final int REQUEST_CODE3 = 2;
	protected static final int REQUEST_CODE4 = 3;
	protected static final int REQUEST_CODE5 = 4;
	protected static final int REQUEST_CODE6 = 5;
	private Spinner spinner;
	private Spinner spinner2;
	private Button button;
	private Button button2;
	private Button button3;
	private Button datepickerbutton;
	private Button tagsbycategory;
	private ArrayAdapter<String> adapter2;
	private ArrayAdapter<String> adapter1;
	private EditText EditTag;
	private EditText EditAmount;
	private AutoCompleteTextView textView;
	private TextView Allaccountsvalue;
	private TextView datevalue;
	AccountTypeDbAdapter accountTypeDbAdapter;
	GetDatabaseData getdatabasedata;
	CharSequence getdatabase = "good";
	String zData = "good";
	public String[] allaccountnames;
	public String[] alltaglist;
	//String allaccountnames [];
	//String allaccountnames [] = {"dorobicci", "dorobuchi"};
	public static final String EXTRA_STRING_NAME = "extraStringName";
	public static final String EXTRA_STRING_NAME2 = "extraStringName2";
	public static final String EXTRA_STRING_NAME3 = "extraStringName3";
	public static final String EXTRA_STRING_NAME4 = "extraStringName4";
	String category = null;

	
	//getdatabase =Integer.toString( getdatabasedata.status1);
	
	
	
	
	public void update() {
		// Intent intent = new Intent(getActivity().getBaseContext(), GetDatabaseData.class);

			//startActivityForResult(intent, REQUEST_CODE);
		//search();
		//getdatabase =Integer.toString( getdatabasedata.status1);
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		View view = inflater.inflate(R.layout.activity_main, container, false);

		spinner = (Spinner) view.findViewById(R.id.transaction_type);
		spinner2 = (Spinner) view.findViewById(R.id.edit_cash);
		button = (Button) view.findViewById(R.id.addnewaccount);
	    button2 = (Button) view.findViewById(R.id.savebutton);
		button3 = (Button) view.findViewById(R.id.navigationmenu);
	    datepickerbutton = (Button) view.findViewById(R.id.dateupdate);
	    EditTag = (EditText) view.findViewById(R.id.edit_tag);
	    EditAmount = (EditText) view.findViewById(R.id.edit_amount);
	    textView = (AutoCompleteTextView) view.findViewById(R.id.edit_tag);
	    Allaccountsvalue = (TextView) view.findViewById(R.id.allaccountsvalue);
	    datevalue = (TextView) view.findViewById(R.id.myViewDate);
		tagsbycategory = (Button) view.findViewById(R.id.category_tag);
	    
	    Date datedisplay = new Date();
		
		SimpleDateFormat postFormater2 = new SimpleDateFormat("yyyy-MM-dd");
		String newDateStr = postFormater2.format(datedisplay);
		
		datevalue.setText(newDateStr);
	    
	    
	    Intent intent = new Intent(getActivity().getBaseContext(), GetDatabaseData.class);

		startActivityForResult(intent, REQUEST_CODE);


	   

		//String [] accountnames = null;
		initUI();
		return view;
	}
	
	
	
	

	private void initUI() {
		if(CategoryListAdapter.categorytype != null){

		EditTag.setText(CategoryListAdapter.categorytype);

	    }
		//Context context = getContext();
		//Intent intent10 = context.getApplicationContext();
		//category = intent10.getStringExtra(CategoryListAdapter.categorytype);
		//Toast.makeText(getActivity().getApplicationContext(), zData, Toast.LENGTH_SHORT).show(); 
		
		
		
		 ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
		         R.array.transcationtype_array, android.R.layout.simple_spinner_item);
		 
		 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 // Apply the adapter to the spinner
		 spinner.setAdapter(adapter);
		 spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			 public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
			  
			   }
			 public void onNothingSelected(AdapterView<?> arg0) {
			  // TODO Auto-generated method stub
			   }
			  });
		 
		
		// Intent intent = new Intent(getActivity().getBaseContext(), GetDatabaseData.class);

		//	startActivityForResult(intent, REQUEST_CODE);     
		//Intent intent10 = getIntent();
		//category = intent10.getStringExtra(CategoryListAdapter.categorytype);
		 
		 button.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {


					String EditDateString4 = datevalue.getText().toString();

					Intent intent = new Intent(getActivity().getBaseContext(), Accounts_viewActivity.class);
					//Intent intent = new Intent(getActivity().getBaseContext(), Navigation_drawer_start.class);
					//Intent intent = new Intent(getActivity().getBaseContext(), BarChartActivity.class);
					intent.putExtra("expensedate", EditDateString4);
					startActivityForResult(intent, REQUEST_CODE);
					
					
					
					
				
				}
			});

		button3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				//Intent intent = new Intent(getActivity().getBaseContext(), Accounts_viewActivity.class);
				Intent intent = new Intent(getActivity().getBaseContext(), Navigation_drawer_start.class);
				//Intent intent = new Intent(getActivity().getBaseContext(), BarChartActivity.class);

				startActivityForResult(intent, REQUEST_CODE);





			}
		});
		 
		 
		 /*datepickerbutton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					Intent intent4 = new Intent(getActivity().getBaseContext(), Date_viewActivity.class);
					startActivityForResult(intent4, REQUEST_CODE5);
					Toast.makeText(getActivity().getApplicationContext(),"Date Inserted Successfully", Toast.LENGTH_SHORT).show();
					
				}
			});*/
		 
		
			
			
            button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	//TransactionType = (Spinner) view.findViewById(R.id.transaction_type);
            	
            	//showPopup(v);
            	
            	String TransactionTypeString = spinner.getSelectedItem().toString();

            	String TransactionTypeString2 = TransactionTypeString;
            	
            	//EditAmount = (EditText) findViewById(R.id.edit_amount);	
            	String EditAmountString = EditAmount.getText().toString();
                  String EditTagString = EditTag.getText().toString();
            	
            	String EditTagString2 = EditTagString;
            	String EditDateString = datevalue.getText().toString();
            	
            	String EditAccountTypeString = spinner2.getSelectedItem().toString();
            	String EditAccountTypeString2 = EditAccountTypeString;
            	
            	String transactiontype2 = "income";
            	String transactiontype3 = "budget";
            	
            	//intentdata[0] = EditAccountTypeString2;
            	//intentdata[1] = transactiontype2;
            	
            		
            		if (EditAmountString.equals("")) {
    					Toast.makeText(getActivity().getApplicationContext(),
    							"Please enter an amount or enter 0 if none",
    							Toast.LENGTH_SHORT).show();
    					// Toast.makeText(getApplicationContext(), EditDateString,
    					// Toast.LENGTH_SHORT).show();
    				} else if (TransactionTypeString2.equals("Income")) {

    					int EditAmountInteger = Integer.parseInt(EditAmountString);
    					
    					String [] intentdata = {EditAccountTypeString2,transactiontype2};
    					Intent intent2 = new Intent(getActivity().getBaseContext(), UpdateAccountIntent.class);
    					//intent2.putExtra("accounttype", EditAccountTypeString2);
						intent2.putExtra("transactiontype",TransactionTypeString);
    					intent2.putExtra("accounttype", intentdata);
    					intent2.putExtra("transactiontype", transactiontype2);
    					intent2.putExtra("editamount", EditAmountInteger);
						intent2.putExtra("expensedate", EditDateString);
    					
    					startActivityForResult(intent2, REQUEST_CODE2);
    					
    					
    					EditAmount.setText("");
    					EditTag.setText("");
    					spinner2.setTag("");
    					

    				}else if (TransactionTypeString2.equals("Transfer")) {

    					Toast.makeText(getActivity().getApplicationContext(),
    							"Transfer Transaction Selected(Not Yet Available)",
    							Toast.LENGTH_SHORT).show();

    				} else if (TransactionTypeString2.equals("Budgets")) {

    					int EditAmountInteger = Integer.parseInt(EditAmountString);
    					//EditAmountInteger2 = EditAmountInteger;

    					if (EditTagString.equals("")) {

    						Toast.makeText(
    								getActivity().getApplicationContext(),
    								"Please select an expense type tag or enter a new expense type tag",
    								Toast.LENGTH_SHORT).show();

    					} else {
    						
    						
    						String [] intentdata = {EditTagString2,transactiontype3};
    						Intent intent2 = new Intent(getActivity().getBaseContext(), UpdateBudgetIntent.class);
    						intent2.putExtra("accounttype",intentdata);
    						intent2.putExtra("transactiontype", transactiontype3);
    						intent2.putExtra("editamount", EditAmountInteger);
        					
        					startActivityForResult(intent2, REQUEST_CODE3);

    						EditAmount.setText("");
    						EditTag.setText("");

							//Intent intent3 = new Intent(getActivity().getBaseContext(), MainActivity.class);
							//startActivityForResult(intent3, REQUEST_CODE3);
    					
    					}
    				}else if (TransactionTypeString2.equals("Balance Reset")) {

    					Toast.makeText(
    							getActivity().getApplicationContext(),
    							"Balance Reset Transaction Selected(Not Yet Available)",
    							Toast.LENGTH_SHORT).show();

    				}

    				else if (EditTagString.equals("")) {

    					Toast.makeText(
    							getActivity().getApplicationContext(),
    							"Please select an expense type tag or enter a new expense type tag",
    							Toast.LENGTH_SHORT).show();

    				} else if (EditAccountTypeString.equals("")) {

    					Toast.makeText(getActivity().getApplicationContext(),
    							"Please select an account type", Toast.LENGTH_SHORT)
    							.show();

    				}
    				else {
    					int EditAmountInteger = Integer.parseInt(EditAmountString);
    					int EditAmountInteger2 = EditAmountInteger;
    					Intent intent3 = new Intent(getActivity().getBaseContext(), UpdateExpenseIntent.class);
    					intent3.putExtra("transactiontype",TransactionTypeString);
    					intent3.putExtra("EditAccountTypeString2",EditAccountTypeString);
						intent3.putExtra("tag", EditTagString);
						intent3.putExtra("expensedate", EditDateString);
						intent3.putExtra("editamount", EditAmountInteger);
    					
    					startActivityForResult(intent3, REQUEST_CODE4);

    					EditAmount.setText("");
						EditTag.setText("");

    				}

            		
            	
               }
			});


		tagsbycategory.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ValidFragment")
			public void onClick(View v) {

				Intent intent6 = new Intent(getActivity().getBaseContext(), TagsByCategory.class);
				startActivityForResult(intent6, REQUEST_CODE6);
				//startActivity(intent6);


			}
		});


            
            datepickerbutton.setOnClickListener(new View.OnClickListener() {
    			@SuppressLint("ValidFragment")
    			public void onClick(View v) {
            
    				Intent intent4 = new Intent(getActivity().getBaseContext(), Date_viewActivity.class);
					startActivityForResult(intent4, REQUEST_CODE5);	
                  
                    
         }
     });
		
            
              
            
          
             
            
	}

	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == REQUEST_CODE) {
	        // Make sure the request was successful
	        if (resultCode == getActivity().RESULT_OK) {
	            // The user picked a contact.
	            // The Intent's data Uri identifies which contact was selected.

	            // Do something with the contact here (bigger example below)
	        	//final String zData = data.getExtras().getString( GetDatabaseData.EXTRA_STRING_NAME );
	        	 //zData = data.getExtras().getString( GetDatabaseData.EXTRA_STRING_NAME );
	        	 //allaccountnames = data.getExtras().getStringArray( GetDatabaseData.EXTRA_STRING_NAME2 );
	        	alltaglist =data.getStringArrayExtra(EXTRA_STRING_NAME);
	        	 allaccountnames =data.getStringArrayExtra(EXTRA_STRING_NAME2);
	        	 String amountVal3 = data.getStringExtra(EXTRA_STRING_NAME3);
	        	 //zData = data.getStringArrayExtra(EXTRA_STRING_NAME3);
	        	//Toast.makeText(getActivity().getApplicationContext(), allaccountnames[0], Toast.LENGTH_SHORT).show();
	        
	        	//String myData = "savings";
	     		// String myData = zData;
	     		 //getdatabase =Integer.toString( getdatabasedata.status1);
	     		 
	     		 //String[] fromColumns2 = allaccountnames;
	     		//String[] fromColumns = {"savings", "current"};
	     		//String[] fromColumns = {"savings", "current"};
	     		    // fromColumns = allaccountnames;
	     		 adapter2 = new ArrayAdapter<String>(getActivity().getBaseContext(),
	     					android.R.layout.simple_list_item_1, allaccountnames);
	     			spinner2.setAdapter(adapter2);
	     			
	     			//String[] fromColumns = TagDescriptionUpdateAdapter.getTagDescription();
	     			//String[] fromColumns2 = accountTypeDbAdapter.getTagDescription();
	     			// Get a reference to the AutoCompleteTextView in the layout
	     			//textView = (AutoCompleteTextView) textView.findViewById(R.id.edit_tag);

	     			// AutoCompleteTextView textView2 = (AutoCompleteTextView)
	     			// findViewById(R.id.edit_cash);
	     			//Spinner spinner2 = (Spinner) findViewById(R.id.edit_cash);
	     			 adapter1 = new ArrayAdapter<String>(getActivity().getBaseContext(),
	     					android.R.layout.simple_list_item_1, alltaglist);
	     			textView.setAdapter(adapter1);
	     			
	     			Allaccountsvalue.setText(amountVal3);
	     			//Toast.makeText(getActivity().getApplicationContext(),"Transaction Completed", Toast.LENGTH_SHORT).show();
	        
	        }
	    }
	    
	    
	    if (requestCode == REQUEST_CODE2) {
	        // Make sure the request was successful
	        if (resultCode == getActivity().RESULT_OK) {
	           
	        	// zData = data.getExtras().getString( GetDatabaseData.EXTRA_STRING_NAME );
	        	 
	        	// allaccountnames =data.getStringArrayExtra(EXTRA_STRING_NAME2);
	        	 String amountVal3 = data.getStringExtra(EXTRA_STRING_NAME3);
	        	
	     		/* adapter2 = new ArrayAdapter<String>(getActivity().getBaseContext(),
	     					android.R.layout.simple_list_item_1, allaccountnames);
	     			spinner2.setAdapter(adapter2);
	     			
	     			
	     			 adapter1 = new ArrayAdapter<String>(getActivity().getBaseContext(),
	     					android.R.layout.simple_list_item_1, allaccountnames);
	     			textView.setAdapter(adapter1);*/
	     			
	     			Allaccountsvalue.setText(amountVal3);
	     			Toast.makeText(getActivity().getApplicationContext(),"Income Transaction Completed", Toast.LENGTH_SHORT).show();
	        
	        }
	    }
	    
	    if (requestCode == REQUEST_CODE3) {
	        // Make sure the request was successful
	        if (resultCode == getActivity().RESULT_OK) {
	           
	        	
	     			Toast.makeText(getActivity().getApplicationContext(),"Budget Update Transaction Completed", Toast.LENGTH_SHORT).show();
				Intent intent3 = new Intent(getActivity().getBaseContext(), MainActivity.class);
				startActivityForResult(intent3, REQUEST_CODE3);
	        
	        }
	    }
	    
	    if (requestCode == REQUEST_CODE4) {
	        // Make sure the request was successful
	        if (resultCode == getActivity().RESULT_OK) {
	           
	        	String amountVal3 = data.getStringExtra(EXTRA_STRING_NAME4);
	        	Allaccountsvalue.setText(amountVal3);
	     			Toast.makeText(getActivity().getApplicationContext(),"Expense Entry Transaction Completed", Toast.LENGTH_SHORT).show();
	        
	     			Intent intent5 = new Intent(getActivity().getBaseContext(), MainActivity.class);
	     			startActivityForResult(intent5, REQUEST_CODE);
	        }
	    }
	    
	    if (requestCode == REQUEST_CODE5) {
	        // Make sure the request was successful
	        if (resultCode == getActivity().RESULT_OK) {
	           
	        	String amountVal3 = data.getStringExtra(EXTRA_STRING_NAME4);
	        	datevalue.setText(amountVal3);
				EditTag.setText(amountVal3);
	     			Toast.makeText(getActivity().getApplicationContext(),"Date Inserted Successfully", Toast.LENGTH_SHORT).show();
	        
	        }
	    }

		if (requestCode == REQUEST_CODE6) {
			// Make sure the request was successful
			if (resultCode == getActivity().RESULT_OK) {

				String category = data.getStringExtra(EXTRA_STRING_NAME4);
				//datevalue.setText(amountVal3);
				EditTag.setText(category);

				Toast.makeText(getActivity().getApplicationContext(),"Date Inserted Successfully", Toast.LENGTH_SHORT).show();

			}
		}
	   
	    
	    
	}
	
	
	
	
	
	

}




