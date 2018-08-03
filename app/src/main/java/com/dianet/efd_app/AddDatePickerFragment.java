package com.dianet.efd_app;

import java.util.Calendar;







import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


@SuppressLint("NewApi")
public class AddDatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	
	private UpdatableFragment fragment;	
	
public AddDatePickerFragment(UpdatableFragment fragment) {
		
		super();
		this.fragment = fragment;
	}


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
		//dateDayValue = dateYear2 + "-" + dateMonth2 + "-" + dateDay2;
		//Toast.makeText(getApplicationContext(), dateDayValue3,
		//		Toast.LENGTH_SHORT).show();
		//TextView StartDatevalue = (TextView) findViewById(R.id.StartDateView);
		//StartDatevalue.setText(dateDayValue);
	}

}