package com.dianet.efd_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Accounts_viewActivity extends Activity {

	private EditText account1Edit;
	private EditText account2Edit;
	private EditText account1amount;
	private EditText account2amount;
	private String account1DefaultValue = "Account 1";
	private String account2DefaultValue = "Account 2";
	AccountTypeDbAdapter accountTypeDbAdapter;
	BudgetUpdateAdapter BudgetUpdateAdapter;
	private int status1 = 0;
	private int status2 = 0;
	private int status3 = 0;
	private int status4 = 0;

	// TextView Allaccountsvalue2 = new TextView(this);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * @Override public boolean onCreateOptionsMenu(Menu menu) {
		 * getMenuInflater().inflate(R.menu.activity_main, menu); return true; }
		 */
		@SuppressWarnings("unused")
		Intent intent = getIntent();
		setContentView(R.layout.activity_accounts_view);

		final String EditDateString2 = intent.getStringExtra("expensedate");

		accountTypeDbAdapter = new AccountTypeDbAdapter(this);
		accountTypeDbAdapter = accountTypeDbAdapter.open();

		BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
		BudgetUpdateAdapter = BudgetUpdateAdapter.open();

		final String accountname = accountTypeDbAdapter
				.getSinlgeEntryAccountType(1);
		final String accountname2 = accountTypeDbAdapter
				.getSinlgeEntryAccountType(2);

		final int amountVal = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(1);
		final int amountVal2 = accountTypeDbAdapter.getSinlgeEntryAccountAmount2(2);

		final String amountVal3 = Integer.toString(amountVal);
		final String amountVal4 = Integer.toString(amountVal2);

		account1Edit = (EditText) findViewById(R.id.account1nameentry);
		account1Edit.setHint(accountname);
		// account1Edit.setText(accountname);
		account1Edit.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// account1Edit.setHint(accountname);
				status1 = 1;
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

		account2Edit = (EditText) findViewById(R.id.account2nameentry);
		account2Edit.setHint(accountname2);
		// account2Edit.setText(accountname2);
		account2Edit.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// account1Edit.setHint(accountname);
				status2 = 1;
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

		account1amount = (EditText) findViewById(R.id.account1amountentry);
		account1amount.setHint(amountVal3);
		// account1amount.setText(amountVal3);
		account1amount.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// account1Edit.setHint(accountname);
				status3 = 1;

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});
		account2amount = (EditText) findViewById(R.id.account2amountentry);
		account2amount.setHint(amountVal4);
		// account2amount.setText(amountVal4);
		account2amount.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// account1Edit.setHint(accountname);
				status4 = 1;
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
			}
		});

		Toast.makeText(getApplicationContext(), accountname, Toast.LENGTH_SHORT)
				.show();

		final Button button = (Button) findViewById(R.id.accounttypesavebutton);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				account1Edit = (EditText) findViewById(R.id.account1nameentry);

				account2Edit = (EditText) findViewById(R.id.account2nameentry);

				account1amount = (EditText) findViewById(R.id.account1amountentry);
				account2amount = (EditText) findViewById(R.id.account2amountentry);

				String accountName1 = account1Edit.getText().toString();
				String accountName2 = account2Edit.getText().toString();

				if (status1 == 0) {
					account1Edit.setText(accountname);
					accountName1 = account1Edit.getText().toString();
				}

				if (status2 == 0) {
					account2Edit.setText(accountname2);
					accountName2 = account2Edit.getText().toString();
				}

				// String accountName1 = "Account 1";

				if (accountName1.equals("")) {
					accountName1 = "Account 1";
				}
				if (accountName2.equals("")) {
					accountName2 = "Account 2";
				}

				int AmountAccount1 = 0;
				String Amount3 = account1amount.getText().toString();
				if (Amount3.equals("")) {
					AmountAccount1 = 0;
				} else {
					AmountAccount1 = Integer.parseInt(account1amount.getText()
							.toString());
				}

				int AmountAccount2 = 0;
				String Amount4 = account2amount.getText().toString();
				if (Amount4.equals("")) {
					AmountAccount2 = 0;
				} else {
					AmountAccount2 = Integer.parseInt(account2amount.getText()
							.toString());
				}

				String transactiontype = "income";

				if (status3 == 0) {
					account1amount.setText(amountVal3);
					String amountVal6 = account1amount.getText().toString();
					AmountAccount1 = Integer.parseInt(amountVal6);
					accountTypeDbAdapter.updateEntryAccount1(accountName1,
							AmountAccount1, 1);
				} else {

					if (AmountAccount1 == 0) {

						AmountAccount1 = 0;
						accountTypeDbAdapter.updateEntryAccount1(accountName1,
								AmountAccount1, 1);

					} else {


						BudgetUpdateAdapter.insertEntryExpenseUpdateBudget(
								transactiontype, AmountAccount1,
								AmountAccount1, EditDateString2);

						AmountAccount1 = AmountAccount1 + amountVal;

						accountTypeDbAdapter.updateEntryAccount1(accountName1,
								AmountAccount1, 1);
						account1amount.setText(Integer.toString(AmountAccount1));


					}

				}

				if (status4 == 0) {
					account2amount.setText(amountVal4);
					String amountVal5 = account2amount.getText().toString();
					AmountAccount2 = Integer.parseInt(amountVal5);
					accountTypeDbAdapter.updateEntryAccount1(accountName2,
							AmountAccount2, 2);
				} else {

					if (AmountAccount2 == 0) {
						AmountAccount2 = 0;
						accountTypeDbAdapter.updateEntryAccount1(accountName2,
								AmountAccount2, 2);

					} else {


						BudgetUpdateAdapter.insertEntryExpenseUpdateBudget(
								transactiontype, AmountAccount1,
								AmountAccount2, EditDateString2);

						AmountAccount2 = AmountAccount2 + amountVal2;

						accountTypeDbAdapter.updateEntryAccount1(accountName2,
								AmountAccount2, 2);
						account2amount.setText(Integer.toString(AmountAccount2));

					}


				}


				// Allaccountsvalue2 = (TextView)
				// findViewById(R.id.allaccountsvalue);
				// Allaccountsvalue.setText(accountName1);
				Toast.makeText(getApplicationContext(), "UPDATE SUCCESSFUL",
						Toast.LENGTH_SHORT).show();

			}
		});

	}

	@Override
	public void finish() {
		// Prepare data intent
		// Intent data = new Intent();
		// data.putExtra("returnKey1", "Swinging on a star. ");
		// data.putExtra("returnKey2", "You could be better then you are. ");
		// // Activity finished ok, return the data
		// setResult(RESULT_OK, data);
		Intent intent = new Intent(Accounts_viewActivity.this,
				MainActivity.class);
		startActivity(intent);
		super.finish();
	}
}
