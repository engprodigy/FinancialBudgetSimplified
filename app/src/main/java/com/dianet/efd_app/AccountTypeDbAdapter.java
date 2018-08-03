package com.dianet.efd_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AccountTypeDbAdapter {
	static final String DATABASE_NAME = "login.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;
	private static final String EFD_TABLE_NAME = "Account_type";
	// private static final String EFD_TABLE_NAME2 = "Entry_Update";
	static final String colID = "Account_ID";
	static final String colName = "Account_Name";
	static final String colAmount = "Amount";

	// TODO: Create public field for each column in your table.
	// SQL Statement to create a new database.
	static final String EFD_TABLE_CREATE = "CREATE TABLE " + EFD_TABLE_NAME
			+ " (" + colID + " integer primary key autoincrement, " + colName
			+ " text not null, " + colAmount + " integer not null);";

	// Variable to hold the database instance
	public SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private EfdDbAdapter dbHelper;
	
	//private HomeFragment homeFragment;

	public AccountTypeDbAdapter(Context _context) {
		context = _context;
		dbHelper = new EfdDbAdapter(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public AccountTypeDbAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDatabaseInstance() {
		return db;
	}

	public void insertEntry(String accountName, int amount) {
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("Account_Name", accountName);
		newValues.put("Amount", amount);

		// Insert the row into your table
		db.insert("Account_type", null, newValues);
		// /Toast.makeText(context, "Reminder Is Successfully Saved",
		// Toast.LENGTH_LONG).show();
	}

	public int deleteEntry(String accountName) {
		// String id=String.valueOf(ID);
		String where = "accountName=?";
		int numberOFEntriesDeleted = db.delete("Account_type", where,
				new String[] { accountName });
		// Toast.makeText(context,
		// "Number for Entry Deleted Successfully : "+numberOFEntriesDeleted,
		// Toast.LENGTH_LONG).show();
		return numberOFEntriesDeleted;
	}

	public String getSinlgeEntryAccountType(int accountID) {
		Cursor cursor = db.query("Account_type", null, " Account_ID=?",
				new String[] { String.valueOf(accountID) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		String accouttypename = cursor.getString(cursor
				.getColumnIndex("Account_Name"));
		cursor.close();
		return accouttypename;
	}

	public Boolean confirmEntryAccountType1(int accountID) {
		Cursor cursor = db.query("Account_type", null, " Account_ID=?",
				new String[] { String.valueOf(accountID) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			return true;
		} else {
			return false;
		}
	}

	public Boolean confirmEntryAccountType2(int accountID) {
		Cursor cursor = db.query("Account_type", null, " Account_ID=?",
				new String[] { String.valueOf(accountID) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			return true;
		} else {
			return false;
		}
	}

	public int getSinlgeEntryAccountAmount2(int accountID) {
		Cursor cursor = db.query("Account_type", null, " Account_ID=?",
				new String[] { String.valueOf(accountID) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			return 0;
		}
		cursor.moveToFirst();
		int accountvalue = Integer.parseInt(cursor.getString(cursor
				.getColumnIndex("Amount")));
		cursor.close();
		return accountvalue;
	}

	public void updateEntryAccount1(String accountName, int amount,
			int accountID) {
		// Define the updated row content.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		updatedValues.put("Account_Name", accountName);
		updatedValues.put("Amount", amount);

		String where = "Account_ID=?";
		db.update("Account_type", updatedValues, where,
				new String[] { String.valueOf(accountID) });
	}

	public void updateEntryAccount2(String accountName, int amount,
			int accountID) {
		// Define the updated row content.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		updatedValues.put("Account_Name", accountName);
		updatedValues.put("Amount", amount);

		String where = "Account_ID=?";
		db.update("Account_type", updatedValues, where,
				new String[] { String.valueOf(accountID) });
	}

	public String[] getTagDescription() {
		Cursor cursor = db.query("Account_type", null, null, null, null, null,
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
						.getColumnIndex("Account_Name"));
				i++;
			} while (cursor.moveToNext());
		}
		// String[] accountvalue2 = cursor.getColumnNames();
		cursor.close();
		return accountvalue2;
	}

	public void UpdateEntryAccountAmount(String accountName, int amount) {
		Cursor cursor = db.query("Account_type", null, " Account_Name=?",
				new String[] { String.valueOf(accountName) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			// return "NOT EXIST";
		}
		cursor.moveToFirst();
		String accouttypeid = cursor.getString(cursor
				.getColumnIndex("Account_ID"));
		int amountbalance = Integer.parseInt(cursor.getString(cursor
				.getColumnIndex("Amount")));
		cursor.close();
		int newbalance = amountbalance - amount;
		// Define the updated row content.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		// updatedValues.put("Account_Name", accountName);
		updatedValues.put("Amount", newbalance);

		String where = "Account_ID=?";
		db.update("Account_type", updatedValues, where,
				new String[] { String.valueOf(accouttypeid) });
	}

	public void UpdateEntryAccountAmount2(String accountName, int amount) {
		Cursor cursor = db.query("Account_type", null, " Account_Name=?",
				new String[] { String.valueOf(accountName) }, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			// return "NOT EXIST";
		}
		cursor.moveToFirst();
		String accouttypeid = cursor.getString(cursor
				.getColumnIndex("Account_ID"));
		int amountbalance = Integer.parseInt(cursor.getString(cursor
				.getColumnIndex("Amount")));
		cursor.close();
		int newbalance = amountbalance + amount;
		// Define the updated row content.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		// updatedValues.put("Account_Name", accountName);
		updatedValues.put("Amount", newbalance);

		String where = "Account_ID=?";
		db.update("Account_type", updatedValues, where,
				new String[] { String.valueOf(accouttypeid) });
	}
}