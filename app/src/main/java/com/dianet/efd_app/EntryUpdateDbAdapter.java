package com.dianet.efd_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

class ExpenseEntry {

	// private variables
	int _id;
	String _name;
	String _entry_name;
	String _entry_amount;
	String _entry_date;

	// Empty constructor
	public ExpenseEntry() {

	}

	// constructor
	public ExpenseEntry(int id, String name, String _entry_name,
			String _entry_amount, String _entry_date) {
		this._id = id;
		this._name = name;
		this._entry_name = _entry_name;
		this._entry_amount = _entry_amount;
		this._entry_date = _entry_date;
	}

	// constructor
	public ExpenseEntry(String name, String _entry_amount) {
		this._name = name;
		this._entry_amount = _entry_amount;
	}

	public int getID() {
		return this._id;
	}

	// getting name
	public String getName() {
		return this._name;
	}

	public String getEntryName() {
		return this._entry_name;
	}

	public String getEntryAmount() {
		return this._entry_amount;
	}

	public String getEntryDate() {
		return this._entry_date;
	}

	// setting id
	public void setID(int id) {
		this._id = id;
	}

	// setting name
	public void setName(String name) {
		this._name = name;
	}

	public void setEntryName(String _entry_name) {
		this._entry_name = _entry_name;
	}

	public void setEntryAmount(String _entry_amount) {
		this._entry_amount = _entry_amount;
	}

	public void setEntryDate(String _entry_date) {
		this._entry_date = _entry_date;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id",_id + "");
		map.put("_name", _name);
		map.put("_entry_name", _entry_name);
		map.put("_entry_amount", _entry_amount);
	    map.put("_entry_date", _entry_date + "");
	//	map.put("orders", getOrders() + "");
		
		return map;
	}
}

public class EntryUpdateDbAdapter {
	static final String DATABASE_NAME = "entry.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;
	private static final String EFD_TABLE_NAME = "Entry_Update";

	static final String _id = "_id";
	static final String colTrasanctionType = "Transaction_Type";
	static final String colEntryAmount = "Entry_Amount";
	static final String colTag = "Tag_Description";
	static final String colAccount = "Account_Type";
	static final String colDate = "Entry_Date";
	// TODO: Create public field for each column in your table.
	// SQL Statement to create a new database.

	static final String EFD_TABLE_CREATE2 = "CREATE TABLE " + EFD_TABLE_NAME
			+ " (" + _id + " integer primary key autoincrement, "
			+ colTrasanctionType + " text not null, " + colEntryAmount
			+ " integer not null, " + colTag + " text not null, " + colAccount
			+ " text not null, " + colDate + " text not null);";

	// Variable to hold the database instance
	public SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private EfdDbAdapter2 dbHelper;

	// public String Transaction_Type;
	// public String Tag_Description;
	public EntryUpdateDbAdapter(Context _context) {
		context = _context;
		dbHelper = new EfdDbAdapter2(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public EntryUpdateDbAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDatabaseInstance() {
		return db;
	}

	public void insertEntryExpenseUpdate(String transactiontype, int amount,
			String tagdescription, String accounttype, String date) {
		ContentValues newValues = new ContentValues();

		// Assign values for each row.
		newValues.put("Transaction_Type", transactiontype);
		newValues.put("Entry_Amount", amount);
		newValues.put("Tag_Description", tagdescription);
		newValues.put("Account_Type", accounttype);
		newValues.put("Entry_Date", date);

		// Insert the row into your table
		db.insert("Entry_Update", null, newValues);

		// /Toast.makeText(context, "Reminder Is Successfully Saved",
		// Toast.LENGTH_LONG).show();
	}

	public String getExpenseEntry(int id) {
		Cursor cursor = db.query("Entry_Update", null, " _id=?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			// return "NOT EXIST";
		}
		cursor.moveToFirst();
		String accountvalue = cursor.getString(cursor
				.getColumnIndex("Tag_Description"));
		cursor.close();
		return accountvalue;
	}

	public void deleteExpenseEntry(int id) {
		/*
		 * Cursor cursor=db.query("Entry_Update", null, " _id=?", new
		 * String[]{String.valueOf(id)}, null, null, null);
		 * if(cursor.getCount()<1) // accountName Not Exist { cursor.close(); //
		 * return "NOT EXIST"; } cursor.moveToFirst(); String accountvalue=
		 * cursor.getString(cursor.getColumnIndex("Tag_Description"));
		 * cursor.close(); return accountvalue;
		 */
		db.delete("Entry_Update", " _id=?", new String[] { String.valueOf(id) });
	}

	public Cursor getExpenseEntry2(int id) {
		Cursor cursor = db.query("Entry_Update", null, " _id=?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			// return "NOT EXIST";
		}
		cursor.moveToFirst();
		// String accountvalue=
		// cursor.getString(cursor.getColumnIndex("Tag_Description"));
		// cursor.close();
		return cursor;
	}

	public String[] getExpenseEntrybyDate(String entrydate) {
		Cursor cursor = db.query("Entry_Update", null, " Entry_Date=?",
				new String[] { String.valueOf(entrydate) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			// return "NOT EXIST";
		}
		// cursor.moveToFirst();
		cursor.moveToLast();
		// int accountvalue=
		// Integer.parseInt(cursor.getString(cursor.getColumnIndex("Entry_Amount")));
		// String accountvalue=
		// cursor.getString(cursor.getColumnIndex("Tag_Description"));
		// int accountvalue =cursor.getColumnCount();
		// String accountvalue2 = String.valueOf(accountvalue);
		String[] accountvalue2 = cursor.getColumnNames();
		cursor.close();
		return accountvalue2;
	}

	public Cursor getExpenseEntrybyDate2(String entrydate) {
		// new String[] { _id, colTrasanctionType, colEntryAmount, colTag,
		// colAccount, colDate }
		Cursor cursor = db.query("Entry_Update", null, " Entry_Date=?",
				new String[] { String.valueOf(entrydate) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			// return "NOT EXIST";
		}
		cursor.moveToFirst();
		// int accountvalue=
		// Integer.parseInt(cursor.getString(cursor.getColumnIndex("Entry_Amount")));
		// String accountvalue=
		// cursor.getString(cursor.getColumnIndex("Tag_Description"));
		// int accountvalue =cursor.getColumnCount();
		// String accountvalue2 = String.valueOf(accountvalue);
		// String[] accountvalue2 = cursor.getColumnNames();
		// cursor.close();
		return cursor;
	}

	public Cursor getExpenseEntry() {
		Cursor cursor = db.query("Entry_Update", null, null, null, null, null,
				null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			// return "NOT EXIST";
		}
		cursor.moveToFirst();
		// int accountvalue=
		// Integer.parseInt(cursor.getString(cursor.getColumnIndex("Entry_Amount")));
		// String accountvalue=
		// cursor.getString(cursor.getColumnIndex("Tag_Description"));
		// int accountvalue =cursor.getColumnCount();
		// String accountvalue2 = String.valueOf(accountvalue);
		// String[] accountvalue2 = cursor.getColumnNames();
		// cursor.close();
		return cursor;
	}

	public List<ExpenseEntry> getAllExpense() {
		List<ExpenseEntry> expenseList = new ArrayList<ExpenseEntry>();
		// Select All Query
		// String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		// SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query("Entry_Update", null, null, null, null, null,
				null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ExpenseEntry expensentry = new ExpenseEntry();
				expensentry.setID(Integer.parseInt(cursor.getString(0)));
				// expensentry.setName(cursor.getString(1));
				expensentry.setName(cursor.getString(cursor
						.getColumnIndex("Tag_Description")));
				expensentry.setEntryAmount(cursor.getString(2));
				expensentry.setEntryDate(cursor.getString(5));
				// Adding contact to list
				expenseList.add(expensentry);
			} while (cursor.moveToNext());
		}

		// return contact list
		return expenseList;
	}

	public ArrayList<ExpenseEntry> getAllExpense2() {
		ArrayList<ExpenseEntry> expenseList = new ArrayList<ExpenseEntry>();
		// Select All Query
		// String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		// SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query("Entry_Update", null, null, null, null, null,
				null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ExpenseEntry expensentry = new ExpenseEntry();
				expensentry.setID(Integer.parseInt(cursor.getString(0)));
				// expensentry.setName(cursor.getString(1));
				expensentry.setName(cursor.getString(cursor
						.getColumnIndex("Tag_Description")));
				expensentry.setEntryAmount(cursor.getString(2));
				expensentry.setEntryName(cursor.getString(3));
				expensentry.setEntryDate(cursor.getString(5));
				// Adding contact to list
				expenseList.add(expensentry);
			} while (cursor.moveToNext());
		}

		// return contact list
		return expenseList;
	}

	public ArrayList<ExpenseEntry> getAllExpense3() {
		ArrayList<ExpenseEntry> expenseList = new ArrayList<ExpenseEntry>();
		// Select All Query
		// String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		// SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT * from Entry_Update order by _id desc limit 20";
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Cursor cursor=db.query("Entry_Update", null, null, null, null, null,
		// null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ExpenseEntry expensentry = new ExpenseEntry();
				expensentry.setID(Integer.parseInt(cursor.getString(0)));
				// expensentry.setName(cursor.getString(1));
				expensentry.setName(cursor.getString(cursor
						.getColumnIndex("Tag_Description")));
				expensentry.setEntryAmount(cursor.getString(2));
				expensentry.setEntryName(cursor.getString(3));
				expensentry.setEntryDate(cursor.getString(5));
				// Adding contact to list
				expenseList.add(expensentry);
			} while (cursor.moveToNext());
		}

		// return contact list
		return expenseList;
	}

	public ArrayList<ExpenseEntry> getAllExpenseBetweenDate(String StartDate,
			String EndDate) {
		ArrayList<ExpenseEntry> expenseList = new ArrayList<ExpenseEntry>();

		Cursor cursor3 = db.rawQuery("select strftime('%s', '" + StartDate
				+ "')", null);
		cursor3.moveToFirst();
		int Startdate2 = Integer.parseInt(cursor3.getString(0));
		cursor3.close();

		Cursor cursor2 = db.rawQuery(
				"select strftime('%s', '" + EndDate + "')", null);
		cursor2.moveToFirst();
		int Enddate2 = Integer.parseInt(cursor2.getString(0));
		cursor2.close();

		String selectQuery = "SELECT * from Entry_Update where Budget_Julian_Entrydate between '"
				+ Startdate2 + "' and '" + Enddate2 + "'  ";
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				ExpenseEntry expensentry = new ExpenseEntry();
				expensentry.setID(Integer.parseInt(cursor.getString(0)));
				// expensentry.setName(cursor.getString(1));
				expensentry.setName(cursor.getString(cursor
						.getColumnIndex("Tag_Description")));
				expensentry.setEntryAmount(cursor.getString(2));
				expensentry.setEntryName(cursor.getString(3));
				expensentry.setEntryDate(cursor.getString(5));
				// Adding contact to list
				expenseList.add(expensentry);
			} while (cursor.moveToNext());
		}

		// return contact list
		return expenseList;
	}
	
	
	public ArrayList<String> getAllExpenseBetweenDate2(String StartDate,
			String EndDate) {
		ArrayList<ExpenseEntry> expenseList = new ArrayList<ExpenseEntry>();
		
		ArrayList<String> expenseList2 = new ArrayList<String>();

		Cursor cursor3 = db.rawQuery("select strftime('%s', '" + StartDate
				+ "')", null);
		cursor3.moveToFirst();
		int Startdate2 = Integer.parseInt(cursor3.getString(0));
		cursor3.close();

		Cursor cursor2 = db.rawQuery(
				"select strftime('%s', '" + EndDate + "')", null);
		cursor2.moveToFirst();
		int Enddate2 = Integer.parseInt(cursor2.getString(0));
		cursor2.close();

		String selectQuery = "SELECT * from Entry_Update where Budget_Julian_Entrydate between '"
				+ Startdate2 + "' and '" + Enddate2 + "'  ";
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				ExpenseEntry expensentry = new ExpenseEntry();
				expensentry.setID(Integer.parseInt(cursor.getString(0)));
				// expensentry.setName(cursor.getString(1));
				expensentry.setName(cursor.getString(cursor
						.getColumnIndex("Tag_Description")));
				expensentry.setEntryAmount(cursor.getString(2));
				expensentry.setEntryName(cursor.getString(3));
				expensentry.setEntryDate(cursor.getString(5));
				// Adding contact to list
				expenseList.add(expensentry);
				expenseList2.add(cursor.getString(cursor
						.getColumnIndex("Tag_Description")));
				expenseList2.add(cursor.getString(2));
				expenseList2.add(cursor.getString(3));
				expenseList2.add(cursor.getString(5));
			} while (cursor.moveToNext());
		}else {
			
			expenseList2.add("0");
			expenseList2.add("0");
			expenseList2.add("0");
			expenseList2.add("0");
		}

		// return contact list
		return expenseList2;
	}

	public String[] getTagDescription() {
		Cursor cursor = db.query("Entry_Update", null, null, null, null, null,
				null);
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

	public String[] getTagAmount() {
		Cursor cursor = db.query("Entry_Update", null, null, null, null, null,
				null);
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
						.getColumnIndex("Entry_Amount"));
				i++;
			} while (cursor.moveToNext());
		}
		// String[] accountvalue2 = cursor.getColumnNames();
		cursor.close();
		return accountvalue2;
	}

	public String[] getTagDate() {
		Cursor cursor = db.query("Entry_Update", null, null, null, null, null,
				null);
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
						.getColumnIndex("Entry_Date"));
				i++;
			} while (cursor.moveToNext());
		}
		// String[] accountvalue2 = cursor.getColumnNames();
		cursor.close();
		return accountvalue2;
	}

	public void deleteExpenseEntry2(String ExpenseTag) {
		db.delete("Entry_Update", " Tag_Description=?",
				new String[] { String.valueOf(ExpenseTag) });

		// db.delete("Budget_Tracking_Amount"," Budget_Type=?", new
		// String[]{ExpenseTag});

		// String selectQuery =
		// "DELETE FROM Entry_Update where Tag_Description = '" + ExpenseTag +
		// "'";
		// Cursor cursor = db.rawQuery(selectQuery, null);
		// cursor.close();

	}
}