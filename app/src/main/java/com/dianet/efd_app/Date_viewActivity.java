package com.dianet.efd_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



//import com.refresh.pos.R;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;

public class Date_viewActivity extends Activity {

	
	
	int status1 = 0;
	private int status2 = 0;
	private int status3 = 0;
	private int status4 = 0;
	public static final String EXTRA_STRING_NAME = "extraStringName";
	public static final String EXTRA_STRING_NAME2 = "extraStringName2";
	public static final String EXTRA_STRING_NAME3 = "extraStringName3";
	public static final String EXTRA_STRING_NAME4 = "extraStringName4";
	public String dateDayValue2 = "0";
	public String newDateStr2 = "0";
	public String newDateStr3 = "0";
	public DialogFragment newFragment;
	
	
	// TextView Allaccountsvalue2 = new TextView(this);
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_picker_layout);

		/*
		 * @Override public boolean onCreateOptionsMenu(Menu menu) {
		 * getMenuInflater().inflate(R.menu.activity_main, menu); return true; }
		 */
		
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

	@SuppressLint("NewApi")
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
		//TextView Allaccountsvalue = (TextView) findViewById(R.id.myViewDate);
		//Allaccountsvalue.setText(dateDayValue);
		@SuppressWarnings("unused")
		Intent intent = getIntent();
		Intent data = new Intent();
		data.putExtra(EXTRA_STRING_NAME4, dateDayValue2);
		setResult(RESULT_OK, data);
	    //super.finish();
		finish();
	}
	
}

	newFragment = new DatePickerFragment();
	newFragment.show(getFragmentManager(), "datePicker");
		
		
        
		
		}
	
}
