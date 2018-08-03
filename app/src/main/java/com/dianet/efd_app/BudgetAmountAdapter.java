package com.dianet.efd_app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BudgetAmountAdapter {
	static final String DATABASE_NAME = "_entry_amount_budget.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;

	private static final String EFD_TABLE_NAME5 = "Budget_Amount";

	static final String _id = "_id";
	static final String colTrasanctionType = "Transaction_Type";
	static final String colBudget = "Budget_Type";
	static final String colAmount = "Budget_Amount";
	// static final String colAmount2="Budget_Spent_Amount";
	// static final String colDate="Entry_Date";
	// static final String colBudgetID="Budget_ID";
	// TODO: Create public field for each column in your table.
	// SQL Statement to create a new database.

	static final String EFD_TABLE_CREATE5 = "CREATE TABLE " + EFD_TABLE_NAME5
			+ " (" + _id + " integer primary key autoincrement, " + colBudget
			+ " text not null, " + colAmount + "  integer not null);";

	// Variable to hold the database instance
	public SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private EfdDbAdapter5 dbHelper;

	// public String Transaction_Type;
	// public String Tag_Description;
	public BudgetAmountAdapter(Context _context) {
		context = _context;
		dbHelper = new EfdDbAdapter5(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public BudgetAmountAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDatabaseInstance() {
		return db;
	}

	public void insertEntryExpenseUpdateBudget(String budget, int BudgetAmount) {

		ContentValues newBudgetEntry = new ContentValues();
		// Assign values for each row.

		int budgetAmount = 0;
		newBudgetEntry.put("Budget_Type", budget);
		newBudgetEntry.put("Budget_Amount", budgetAmount);

		// Insert the row into your table

		db.insert("Budget_Amount", null, newBudgetEntry);
		// /Toast.makeText(context, "Reminder Is Successfully Saved",
		// Toast.LENGTH_LONG).show();
	}

	public void insertEntryExpenseUpdateBudget2(String budget, int BudgetAmount) {

		ContentValues newBudgetEntry = new ContentValues();
		// Assign values for each row.

		// int budgetAmount = 0;
		newBudgetEntry.put("Budget_Type", budget);
		newBudgetEntry.put("Budget_Amount", BudgetAmount);

		// Insert the row into your table

		db.insert("Budget_Amount", null, newBudgetEntry);
		// /Toast.makeText(context, "Reminder Is Successfully Saved",
		// Toast.LENGTH_LONG).show();
	}

	public void UpdateExpenseAmountByTag(String expenseTag, int expenseAmount) {

		ContentValues newBudgetEntry = new ContentValues();
		// Assign values for each row.
		newBudgetEntry.put("Budget_Amount", expenseAmount);

		db.update("Budget_Amount", newBudgetEntry, " Budget_Type=?",
				new String[] { String.valueOf(expenseTag) });

	}

	public Boolean getExpenseTagStatus(String expenseTag) {
		Cursor cursor = db.query("Budget_Amount", null, " Budget_Type=?",
				new String[] { String.valueOf(expenseTag) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			return false;
		} else {
			return true;
		}
	}

	public String[] getTagDescription() {
		Cursor cursor = db.query("TagDescription_Update", null, null, null,
				null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			// return "NOT EXIST";
		}
		// cursor.moveToFirst();
		// cursor.moveToLast();
		// int accountvalue=
		// Integer.parseInt(cursor.getString(cursor.getColumnIndex("Entry_Amount")));
		// String accountvalue=
		// cursor.getString(cursor.getColumnIndex("Tag_Description"));
		// int accountvalue =cursor.getColumnCount();
		// String accountvalue2 = String.valueOf(accountvalue);
		int i = 0;
		int j = 0;

		if (cursor.moveToFirst()) {
			do {
				j++;
			} while (cursor.moveToNext());
		}

		String[] accountvalue2 = new String[j];
		if (cursor.moveToFirst()) {
			do {

				accountvalue2[i] = cursor.getString(cursor
						.getColumnIndex("Tag_Description"));
				i++;
			} while (cursor.moveToNext());
		}
		// String[] accountvalue2 = cursor.getColumnNames();
		cursor.close();
		return accountvalue2;
	}

	public Boolean CheckTagDescription(String entrytransaction) {
		Cursor cursor = db.query("TagDescription_Update", null,
				"Tag_Description=?",
				new String[] { String.valueOf(entrytransaction) }, null, null,
				null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			return true;
		} else {
			return false;
		}
	}

	public String getExpenseEntry(int id) {
		Cursor cursor = db.query("Budget_Tracking_Amount", null, " _id=?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			// return "NOT EXIST";
		}
		cursor.moveToFirst();
		String accountvalue = cursor.getString(cursor
				.getColumnIndex("Budget_Amount"));
		cursor.close();
		return accountvalue;
	}

	public String getExpenseAmountByTag(String expenseTag) {
		Cursor cursor = db.query("Budget_Amount", null, " Budget_Type=?",
				new String[] { String.valueOf(expenseTag) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			String accountvalue = "0";
			return accountvalue;
		}
		cursor.moveToFirst();
		String accountvalue = cursor.getString(cursor
				.getColumnIndex("Budget_Amount"));
		cursor.close();
		return accountvalue;
	}

	public String getExpenseAmountByTag2(String expenseTag) {

		// String selectQuery = "SELECT  * FROM " + EFD_TABLE_NAME5;
		// SQLiteDatabase db = this.getWritableDatabase();
		// Cursor cursor = db.rawQuery(selectQuery, null);
		Cursor cursor = db.query("Budget_Amount",
				new String[] { "Budget_Amount" }, " Budget_Type=?",
				new String[] { String.valueOf(expenseTag) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			// return "NOT EXIST";
		}
		cursor.moveToFirst();
		String accountvalue = cursor.getString(cursor
				.getColumnIndex("Budget_Amount"));
		cursor.close();
		return accountvalue;
	}

	public int getExpenseAmountByTag3(String expenseTag) {
		Cursor cursor = db.query("Budget_Amount", null, " Budget_Type=?",
				new String[] { String.valueOf(expenseTag) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			int accountvalue = 0;
			return 0;
		}
		cursor.moveToFirst();
		int accountvalue = Integer.parseInt(cursor.getString(cursor
				.getColumnIndex("Budget_Amount")));
		cursor.close();
		return accountvalue;
	}

	public void deleteExpenseEntry(String entryname) {
		db.delete("Budget_Amount", "Budget_Type=?",
				new String[] { String.valueOf(entryname) });

	}

}