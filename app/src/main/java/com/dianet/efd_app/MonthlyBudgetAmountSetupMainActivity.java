package com.dianet.efd_app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MonthlyBudgetAmountSetupMainActivity extends Activity {

	BudgetAmountAdapter BudgetAmountAdapter;
	BudgetUpdateAdapter BudgetUpdateAdapter;
	TagDescriptionUpdateAdapter TagDescriptionUpdateAdapter;
	EntryUpdateDbAdapter EntryUpdateDbAdapter;
	public static String tagdescriptionfinal;
	public int BudgetAmountFinal;
	private EditText BudgetAmountViewInt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monthly_budget_amount_setup_main);

		BudgetAmountAdapter = new BudgetAmountAdapter(this);
		BudgetAmountAdapter = BudgetAmountAdapter.open();

		TagDescriptionUpdateAdapter = new TagDescriptionUpdateAdapter(this);
		TagDescriptionUpdateAdapter = TagDescriptionUpdateAdapter.open();

		BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
		BudgetUpdateAdapter = BudgetUpdateAdapter.open();

		EntryUpdateDbAdapter = new EntryUpdateDbAdapter(this);
		EntryUpdateDbAdapter = EntryUpdateDbAdapter.open();

		Intent sender = getIntent();
		String extraData1 = sender.getExtras().getString("Value1");
		String extraData2 = sender.getExtras().getString("Value2");

		tagdescriptionfinal = extraData1;

		// int extraData2int = Integer.parseInt(extraData2);

		// Toast.makeText(this, extraData1, Toast.LENGTH_SHORT).show();

		TextView Allaccountsvalue = (TextView) findViewById(R.id.BudgetAmountView);
		Allaccountsvalue.setText(extraData1);

		BudgetAmountViewInt = (EditText) findViewById(R.id.BudgetEditAmount);
		BudgetAmountViewInt.setText(extraData2);

		final Button button = (Button) findViewById(R.id.BudgetEditButton);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// Intent intent = new Intent(MainActivity.this,
				// Accounts_viewActivity.class);

				// startActivity(intent);

				BudgetAmountFinal = Integer.parseInt(BudgetAmountViewInt
						.getText().toString());

				BudgetAmountAdapter.UpdateExpenseAmountByTag(
						tagdescriptionfinal, BudgetAmountFinal);

				Toast.makeText(getApplicationContext(), "Update Successful",
						Toast.LENGTH_SHORT).show();

			}
		});

		final Button button2 = (Button) findViewById(R.id.BudgetDeleteButton);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				showDialog(tagdescriptionfinal);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(
				R.menu.activity_monthly_budget_amount_setup_main, menu);
		return true;
	}

	@Override
	public void finish() {
		// Prepare data intent
		// Intent data = new Intent();
		// data.putExtra("returnKey1", "Swinging on a star. ");
		// data.putExtra("returnKey2", "You could be better then you are. ");
		// // Activity finished ok, return the data
		// setResult(RESULT_OK, data);
		Intent intent = new Intent(MonthlyBudgetAmountSetupMainActivity.this,
				MainActivity.class);
		startActivity(intent);
		super.finish();
	}

	@SuppressLint("NewApi")
	@TargetApi(11)
	void showDialog(String arg2) {
		DialogFragment newFragment = MyAlertDialogFragment
				.newInstance(R.string.alert_dialog_title);
		newFragment.show(getFragmentManager(), "dialog");
	}

	public void doPositiveClick(String args) {
		// Do stuff here.

		Log.i("FragmentAlertDialog", "Positive click!");
	}

	public void doNegativeClick(String args) {
		// Do stuff here.
		// Log.i("FragmentAlertDialog", "Negative click!");

		TagDescriptionUpdateAdapter.deleteExpenseEntry(args);
		BudgetAmountAdapter.deleteExpenseEntry(args);
		BudgetUpdateAdapter.deleteExpenseEntry2(args);
		EntryUpdateDbAdapter.deleteExpenseEntry2(args);

		// finish();
		Intent intent = new Intent(MonthlyBudgetAmountSetupMainActivity.this,
				MainActivity.class);

		startActivity(intent);

	}

	public static class MyAlertDialogFragment extends DialogFragment {

		@SuppressLint("NewApi")
		public static MyAlertDialogFragment newInstance(int title) {
			MyAlertDialogFragment frag = new MyAlertDialogFragment();
			Bundle args = new Bundle();
			args.putInt("title", title);
			frag.setArguments(args);
			return frag;
		}

		@TargetApi(11)
		@SuppressLint("NewApi")
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

									((MonthlyBudgetAmountSetupMainActivity) getActivity())
											.doPositiveClick(tagdescriptionfinal);
								}
							})
					.setNegativeButton(R.string.alert_dialog_delete,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									((MonthlyBudgetAmountSetupMainActivity) getActivity())
											.doNegativeClick(tagdescriptionfinal);
								}
							}).create();
		}
	}
}
