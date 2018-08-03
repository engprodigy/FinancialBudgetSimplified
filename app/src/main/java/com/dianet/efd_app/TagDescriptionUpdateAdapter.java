package com.dianet.efd_app;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TagDescriptionUpdateAdapter {
	static final String DATABASE_NAME = "tag_entry.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;

	private static final String EFD_TABLE_NAME2 = "TagDescription_Update";

	static final String _id = "_id";
	static final String colTrasanctionType = "Transaction_Type";
	static final String colEntryAmount = "Entry_Amount";
	static final String colTag = "Tag_Description";
	static final String colAccount = "Account_Type";
	static final String colDate = "Entry_Date";
	// TODO: Create public field for each column in your table.
	// SQL Statement to create a new database.

	static final String EFD_TABLE_CREATE3 = "CREATE TABLE " + EFD_TABLE_NAME2
			+ " (" + _id + " integer primary key autoincrement, " + colTag
			+ " text not null);";

	// Variable to hold the database instance
	public SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private EfdDbAdapter3 dbHelper;

	// public String Transaction_Type;
	// public String Tag_Description;
	public TagDescriptionUpdateAdapter(Context _context) {
		context = _context;
		dbHelper = new EfdDbAdapter3(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public TagDescriptionUpdateAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDatabaseInstance() {
		return db;
	}

	public void insertEntryExpenseUpdate(String tagdescription) {

		ContentValues newTagDescription = new ContentValues();
		// Assign values for each row.

		newTagDescription.put("Tag_Description", tagdescription);

		// Insert the row into your table

		db.insert("TagDescription_Update", null, newTagDescription);
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

	public String getExpenseEntryID(String expense) {
		Cursor cursor = db.query("TagDescription_Update", null,
				" Tag_Description=?", new String[] { String.valueOf(expense) },
				null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			// return "NOT EXIST";
		}
		cursor.moveToFirst();
		String accountvalue = cursor.getString(cursor.getColumnIndex("_id"));
		cursor.close();
		return accountvalue;
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

	public void deleteExpenseEntry(String entryname) {
		db.delete("TagDescription_Update", "Tag_Description=?",
				new String[] { String.valueOf(entryname) });

	}
}