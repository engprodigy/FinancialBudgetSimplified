package com.dianet.efd_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import opencsv;

/**
 * A very simple CSV reader released under a commercial-friendly license.
 * 
 * @author Glen Smith
 * 
 */
class CSVReader {

	private BufferedReader br;

	private boolean hasNext = true;

	private char separator;

	private char quotechar;

	private int skipLines;

	private boolean linesSkiped;

	/** The default separator to use if none is supplied to the constructor. */
	public static final char DEFAULT_SEPARATOR = ',';

	/**
	 * The default quote character to use if none is supplied to the
	 * constructor.
	 */
	public static final char DEFAULT_QUOTE_CHARACTER = '"';

	/**
	 * The default line to start reading.
	 */
	public static final int DEFAULT_SKIP_LINES = 0;

	/**
	 * Constructs CSVReader using a comma for the separator.
	 * 
	 * @param reader
	 *            the reader to an underlying CSV source.
	 */
	public CSVReader(Reader reader) {
		this(reader, DEFAULT_SEPARATOR, DEFAULT_QUOTE_CHARACTER,
				DEFAULT_SKIP_LINES);
	}

	/**
	 * Constructs CSVReader with supplied separator and quote char.
	 * 
	 * @param reader
	 *            the reader to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param line
	 *            the line number to skip for start reading
	 */
	public CSVReader(Reader reader, char separator, char quotechar, int line) {
		this.br = new BufferedReader(reader);
		this.separator = separator;
		this.quotechar = quotechar;
		this.skipLines = line;
	}

	/**
	 * Reads the next line from the buffer and converts to a string array.
	 * 
	 * @return a string array with each comma-separated element as a separate
	 *         entry.
	 * 
	 * @throws IOException
	 *             if bad things happen during the read
	 */
	public String[] readNext() throws IOException {

		String nextLine = getNextLine();
		return hasNext ? parseLine(nextLine) : null;
	}

	/**
	 * Reads the next line from the file.
	 * 
	 * @return the next line from the file without trailing newline
	 * @throws IOException
	 *             if bad things happen during the read
	 */
	private String getNextLine() throws IOException {
		if (!this.linesSkiped) {
			for (int i = 0; i < skipLines; i++) {
				br.readLine();
			}
			this.linesSkiped = true;
		}
		String nextLine = br.readLine();
		if (nextLine == null) {
			hasNext = false;
		}
		return hasNext ? nextLine : null;
	}

	/**
	 * Parses an incoming String and returns an array of elements.
	 * 
	 * @param nextLine
	 *            the string to parse
	 * @return the comma-tokenized list of elements, or null if nextLine is null
	 * @throws IOException
	 *             if bad things happen during the read
	 */
	private String[] parseLine(String nextLine) throws IOException {

		if (nextLine == null) {
			return null;
		}

		List<String> tokensOnThisLine = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		boolean inQuotes = false;
		do {
			if (inQuotes) {
				// continuing a quoted section, reappend newline
				sb.append("\n");
				nextLine = getNextLine();
				if (nextLine == null)
					break;
			}
			for (int i = 0; i < nextLine.length(); i++) {

				char c = nextLine.charAt(i);
				if (c == quotechar) {
					// this gets complex... the quote may end a quoted block, or
					// escape another quote.
					// do a 1-char lookahead:
					if (inQuotes // we are in quotes, therefore there can be
									// escaped quotes in here.
							&& nextLine.length() > (i + 1) // there is indeed
															// another character
															// to check.
							&& nextLine.charAt(i + 1) == quotechar) { // ..and
																		// that
																		// char.
																		// is a
																		// quote
																		// also.
						// we have two quote chars in a row == one quote char,
						// so consume them both and
						// put one on the token. we do *not* exit the quoted
						// text.
						sb.append(nextLine.charAt(i + 1));
						i++;
					} else {
						inQuotes = !inQuotes;
						// the tricky case of an embedded quote in the middle:
						// a,bc"d"ef,g
						if (i > 2 // not on the begining of the line
								&& nextLine.charAt(i - 1) != this.separator // not
																			// at
																			// the
																			// begining
																			// of
																			// an
																			// escape
																			// sequence
								&& nextLine.length() > (i + 1)
								&& nextLine.charAt(i + 1) != this.separator // not
																			// at
																			// the
																			// end
																			// of
																			// an
																			// escape
																			// sequence
						) {
							sb.append(c);
						}
					}
				} else if (c == separator && !inQuotes) {
					tokensOnThisLine.add(sb.toString());
					sb = new StringBuffer(); // start work on next token
				} else {
					sb.append(c);
				}
			}
		} while (inQuotes);
		tokensOnThisLine.add(sb.toString());
		return (String[]) tokensOnThisLine.toArray(new String[0]);

	}

	/**
	 * Closes the underlying reader.
	 * 
	 * @throws IOException
	 *             if the close fails
	 */
	public void close() throws IOException {
		br.close();
	}

}

/**
 * A very simple CSV writer released under a commercial-friendly license.
 * 
 * @author Glen Smith
 * 
 */
class CSVWriter {

	private PrintWriter pw;

	private char separator;

	private char quotechar;

	private char escapechar;

	private String lineEnd;

	/** The character used for escaping quotes. */
	public static final char DEFAULT_ESCAPE_CHARACTER = '"';

	/** The default separator to use if none is supplied to the constructor. */
	public static final char DEFAULT_SEPARATOR = ',';

	/**
	 * The default quote character to use if none is supplied to the
	 * constructor.
	 */
	public static final char DEFAULT_QUOTE_CHARACTER = '"';

	/** The quote constant to use when you wish to suppress all quoting. */
	public static final char NO_QUOTE_CHARACTER = '\u0000';

	/** The escape constant to use when you wish to suppress all escaping. */
	public static final char NO_ESCAPE_CHARACTER = '\u0000';

	/** Default line terminator uses platform encoding. */
	public static final String DEFAULT_LINE_END = "\n";

	/**
	 * Constructs CSVWriter using a comma for the separator.
	 * 
	 * @param writer
	 *            the writer to an underlying CSV source.
	 */
	public CSVWriter(Writer writer) {
		this(writer, DEFAULT_SEPARATOR, DEFAULT_QUOTE_CHARACTER,
				DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END);
	}

	/**
	 * Constructs CSVWriter with supplied separator, quote char, escape char and
	 * line ending.
	 * 
	 * @param writer
	 *            the writer to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param escapechar
	 *            the character to use for escaping quotechars or escapechars
	 * @param lineEnd
	 *            the line feed terminator to use
	 */
	public CSVWriter(Writer writer, char separator, char quotechar,
			char escapechar, String lineEnd) {
		this.pw = new PrintWriter(writer);
		this.separator = separator;
		this.quotechar = quotechar;
		this.escapechar = escapechar;
		this.lineEnd = lineEnd;
	}

	/**
	 * Writes the next line to the file.
	 * 
	 * @param nextLine
	 *            a string array with each comma-separated element as a separate
	 *            entry.
	 */
	public void writeNext(String[] nextLine) {

		if (nextLine == null)
			return;

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < nextLine.length; i++) {

			if (i != 0) {
				sb.append(separator);
			}

			String nextElement = nextLine[i];
			if (nextElement == null)
				continue;
			if (quotechar != NO_QUOTE_CHARACTER)
				sb.append(quotechar);
			for (int j = 0; j < nextElement.length(); j++) {
				char nextChar = nextElement.charAt(j);
				if (escapechar != NO_ESCAPE_CHARACTER && nextChar == quotechar) {
					sb.append(escapechar).append(nextChar);
				} else if (escapechar != NO_ESCAPE_CHARACTER
						&& nextChar == escapechar) {
					sb.append(escapechar).append(nextChar);
				} else {
					sb.append(nextChar);
				}
			}
			if (quotechar != NO_QUOTE_CHARACTER)
				sb.append(quotechar);
		}

		sb.append(lineEnd);
		pw.write(sb.toString());

	}

	/**
	 * Flush underlying stream to writer.
	 * 
	 * @throws IOException
	 *             if bad things happen
	 */
	public void flush() throws IOException {

		pw.flush();

	}

	/**
	 * Close the underlying stream writer flushing any buffered content.
	 * 
	 * @throws IOException
	 *             if bad things happen
	 * 
	 */
	public void close() throws IOException {
		pw.flush();
		pw.close();
	}

}

public class BudgetUpdateAdapter {
	static final String DATABASE_NAME = "entry_budget.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;

	private static final String EFD_TABLE_NAME4 = "Budget_Tracking_Amount";

	static final String _id = "_id";
	static final String colTrasanctionType = "Transaction_Type";
	static final String colBudget = "Budget_Type";
	static final String colAmount = "Budget_Amount";
	static final String colAmount2 = "Budget_Spent_Amount";
	static final String colDate = "Entry_Date";
	static final String colBudgetID = "Budget_Julian_Entrydate";
	static final int EntryDateValue = 0;
	public int ExpenseAmount;
	// TODO: Create public field for each column in your table.
	// SQL Statement to create a new database.

	static final String EFD_TABLE_CREATE4 = "CREATE TABLE " + EFD_TABLE_NAME4
			+ " (" + _id + " integer primary key autoincrement, " + colBudget
			+ " text not null, " + colAmount + "  integer not null, "
			+ colAmount2 + " integer not null, " + colDate + " text not null, "
			+ colBudgetID + " integer);";

	// Variable to hold the database instance
	public SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private EfdDbAdapter4 dbHelper;
	BudgetAmountAdapter BudgetAmountAdapter;

	// public String Transaction_Type;
	// public String Tag_Description;
	public BudgetUpdateAdapter(Context _context) {
		context = _context;
		dbHelper = new EfdDbAdapter4(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public BudgetUpdateAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDatabaseInstance() {
		return db;
	}

	public void insertEntryExpenseUpdateBudget(String budget, int budgetAmount,
			int SpentAmount, String EntryDate) {
		// int budgetAmount = 0;
		// Cursor cursor2 = db.rawQuery("select julianday( '" + EntryDate +
		// "')", null);
		Cursor cursor2 = db.rawQuery("select strftime('%s', '" + EntryDate
				+ "')", null);
		cursor2.moveToFirst();
		int EntryDate2 = Integer.parseInt(cursor2.getString(0));
		cursor2.close();
		/*
		 * String selectQuery =
		 * "Insert INTO Budget_Tracking_Amount (Budget_Type,Budget_Spent_Amount,Budget_Amount,Entry_Date,Budget_Julian_Entrydate) VALUES ( '"
		 * + budget + "','" + SpentAmount + "','" + budgetAmount + "','" +
		 * EntryDate + "','" + EntryDate2 + "')";
		 * 
		 * Cursor cursor = db.rawQuery(selectQuery, null); cursor.close();
		 */
		ContentValues newBudgetEntry = new ContentValues();
		// Assign values for each row.

		// int budgetAmount = 0;
		newBudgetEntry.put("Budget_Type", budget);
		newBudgetEntry.put("Budget_Spent_Amount", SpentAmount);
		newBudgetEntry.put("Budget_Amount", budgetAmount);
		newBudgetEntry.put("Entry_Date", EntryDate);
		newBudgetEntry.put("Budget_Julian_Entrydate", EntryDate2);

		// Insert the row into your table

		db.insert("Budget_Tracking_Amount", null, newBudgetEntry);
		// /Toast.makeText(context, "Reminder Is Successfully Saved",
		// Toast.LENGTH_LONG).show();
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
		Cursor cursor = db.query("Budget_Tracking_Amount", null,
				" Budget_Type=?", new String[] { String.valueOf(expenseTag) },
				null, null, null);
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

	public int getTotalExpenseAmountByTag2(String expenseTag) {
		// Date datedisplay = new Date();
		// SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");
		// String newDateStr = postFormater.format(datedisplay);
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int month2 = month + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);

		String NewYear = Integer.toString(year);
		String NewMonth = Integer.toString(month2);
		String NewMonth2;
		if (month2 > 9) {
			NewMonth2 = NewMonth;
		} else {
			NewMonth2 = "0" + NewMonth;
		}

		String MinDay = Integer.toString(1);
		String MinDay2 = "0" + MinDay;
		// String MaxDay = Integer.toString(31);
		String MaxDay = "3" + "1";

		String Maxdate = NewYear + "-" + NewMonth2 + "-" + MaxDay + " "
				+ "23:59:59";

		String Mindate = NewYear + "-" + NewMonth2 + "-" + MinDay2 + " "
				+ "00:00:00";

		int TotalBudgetSpentAmount = 0;

		Cursor cursor3 = db.rawQuery(
				"select strftime('%s', '" + Maxdate + "')", null);
		// Cursor cursor3 = db.rawQuery("select strftime('%s', 'now')", null);
		cursor3.moveToFirst();
		int Maxdate2 = Integer.parseInt(cursor3.getString(0));
		cursor3.close();

		// int amonthsec = 226784000;
		// int Maxdate2 = 226784000;
		// int Maxdate2 = Mindate2 + amonthsec;

		Cursor cursor2 = db.rawQuery(
				"select strftime('%s', '" + Mindate + "')", null);
		cursor2.moveToFirst();
		int Mindate2 = Integer.parseInt(cursor2.getString(0));
		cursor2.close();

		// String selectQuery =
		// "SELECT  Budget_Spent_Amount FROM Budget_Tracking_Amount Where Budget_Type = '"
		// + expenseTag + "' ";
		// + / + expenseTag
		String selectQuery = "SELECT  Budget_Spent_Amount FROM Budget_Tracking_Amount where Budget_Julian_Entrydate between '"
				+ Mindate2
				+ "' and '"
				+ Maxdate2
				+ "'  and Budget_Type = '"
				+ expenseTag + "'  ";
		// date()
		// And Budget_Type =" + expenseTag;
		// SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Cursor cursor=db.query("Budget_Tracking_Amount", new String[] {
		// "Budget_Spent_Amount"
		// }, colDate + " BETWEEN ? AND ?" + "Budget_Spent_Amount=?", new
		// String[]{ Mindate + " 00:00:00", Maxdate +
		// " 23:59:59",String.valueOf(expenseTag)}, null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			TotalBudgetSpentAmount = 0;
			return 0;
		}
		if (cursor.moveToFirst()) {
			do {

				int BudgetSpentAmount = Integer
						.parseInt(cursor.getString(cursor
								.getColumnIndex("Budget_Spent_Amount")));
				TotalBudgetSpentAmount = TotalBudgetSpentAmount
						+ BudgetSpentAmount;
			} while (cursor.moveToNext());
			// int BudgetSpentAmount=
			// Integer.parseInt(cursor.getString(cursor.getColumnIndex("Total")));
			// TotalBudgetSpentAmount = BudgetSpentAmount;
			// TotalBudgetSpentAmount = 2000;

		}

		cursor.close();
		// String accountvalue3 = null;
		return TotalBudgetSpentAmount;
	}

	public void UpdateExpenseAmountByTag(String expenseTag, int expenseAmount) {
		/*
		 * Cursor cursor=db.query("Budget_Tracking_Amount", null,
		 * " Budget_Type=?", new String[]{String.valueOf(expenseTag)}, null,
		 * null, null); if(cursor.getCount()<1) // accountName Not Exist {
		 * cursor.close(); // return "NOT EXIST"; } cursor.moveToFirst(); String
		 * accountvalue=
		 * cursor.getString(cursor.getColumnIndex("Budget_Amount"));
		 * cursor.close(); //return accountvalue; ContentValues newBudgetEntry =
		 * new ContentValues(); // Assign values for each row.
		 */

		ContentValues newBudgetEntry = new ContentValues();
		// Assign values for each row.
		newBudgetEntry.put("Budget_Spent_Amount", expenseAmount);

		db.update("Budget_Tracking_Amount", newBudgetEntry, " Budget_Type=?",
				new String[] { String.valueOf(expenseTag) });

	}

	public Boolean getExpenseTagStatus(String expenseTag) {
		Cursor cursor = db.query("Budget_Tracking_Amount", null,
				" Budget_Type=?", new String[] { String.valueOf(expenseTag) },
				null, null, null);
		if (cursor.getCount() < 1) // accountName Not Exist
		{
			cursor.close();
			return false;
		} else {
			return true;
		}
	}

	public ArrayList<ExpenseEntry> getAllExpense3() {
		ArrayList<ExpenseEntry> expenseList = new ArrayList<ExpenseEntry>();
		// Select All Query
		// String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		// SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT * from Budget_Tracking_Amount order by _id desc limit 20";
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
						.getColumnIndex("Budget_Type")));
				expensentry.setEntryAmount(cursor.getString(3));
				expensentry.setEntryName(cursor.getString(1));
				expensentry.setEntryDate(cursor.getString(4));
				// Adding contact to list
				expenseList.add(expensentry);
			} while (cursor.moveToNext());
		}

		// return contact list
		return expenseList;
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

		String selectQuery = "SELECT * FROM Budget_Tracking_Amount where Budget_Julian_Entrydate between '"
				+ Startdate2 + "' and '" + Enddate2 + "' ";
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				ExpenseEntry expensentry = new ExpenseEntry();
				expensentry.setID(Integer.parseInt(cursor.getString(0)));
				// expensentry.setName(cursor.getString(1));
				expensentry.setName(cursor.getString(cursor
						.getColumnIndex("Budget_Type")));
				expensentry.setEntryAmount(cursor.getString(3));
				expensentry.setEntryName(cursor.getString(1));
				expensentry.setEntryDate(cursor.getString(4));
				// Adding contact to list
				expenseList.add(expensentry);
			} while (cursor.moveToNext());
		}

		// return contact list
		return expenseList;
	}
	
	public ArrayList<String> getAllExpenseBetweenDate2(String StartDate,
			String EndDate) {
		String expenseTag= " ";
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
		
		String selectQuery = "";
		
		if(Startdate2 == Enddate2){
		
			selectQuery = "SELECT * FROM Budget_Tracking_Amount where Budget_Julian_Entrydate = '"
					+ Startdate2 + "' and Budget_Type != '" + expenseTag + "'  ";
			//Cursor cursor = db.rawQuery(selectQuery, null);
			
		}else{
			
			selectQuery = "SELECT * FROM Budget_Tracking_Amount where Budget_Julian_Entrydate between '"
					+ Startdate2 + "' and '" + Enddate2 + "' and Budget_Type != '" + expenseTag + "'  ";
			
			
		}

		
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				ExpenseEntry expensentry = new ExpenseEntry();
				expensentry.setID(Integer.parseInt(cursor.getString(0)));
				// expensentry.setName(cursor.getString(1));
				expensentry.setName(cursor.getString(cursor
						.getColumnIndex("Budget_Type")));
				expensentry.setEntryAmount(cursor.getString(3));
				expensentry.setEntryName(cursor.getString(1));
				expensentry.setEntryDate(cursor.getString(4));
				// Adding contact to list
				expenseList.add(expensentry);
				//expensentry.setID(Integer.parseInt(cursor.getString(0)));
				// expensentry.setName(cursor.getString(1));
				//expenseList2.add(cursor.getString(cursor.getColumnIndex("Budget_Type")));
				expenseList2.add(cursor.getString(0));
				expenseList2.add(cursor.getString(3));
				expenseList2.add(cursor.getString(1));
				expenseList2.add(cursor.getString(4));
			} while (cursor.moveToNext());
		}

		// return contact list
		return expenseList2;
	}

	public ArrayList<String> getAllIncomeBetweenDate2(String StartDate, String EndDate) {

		String expenseTag= "income";
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

		String selectQuery = "";

		if(Startdate2 == Enddate2){

			selectQuery = "SELECT * FROM Budget_Tracking_Amount where Budget_Julian_Entrydate = '"
					+ Startdate2 + "' and Budget_Type = '" + expenseTag + "'  ";
			//Cursor cursor = db.rawQuery(selectQuery, null);

		}else{

			selectQuery = "SELECT * FROM Budget_Tracking_Amount where Budget_Julian_Entrydate between '"
					+ Startdate2 + "' and '" + Enddate2 + "' and Budget_Type = '" + expenseTag + "'  ";


		}


		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				ExpenseEntry expensentry = new ExpenseEntry();
				expensentry.setID(Integer.parseInt(cursor.getString(0)));
				// expensentry.setName(cursor.getString(1));
				expensentry.setName(cursor.getString(cursor
						.getColumnIndex("Budget_Type")));
				expensentry.setEntryAmount(cursor.getString(3));
				expensentry.setEntryName(cursor.getString(1));
				expensentry.setEntryDate(cursor.getString(4));
				// Adding contact to list
				expenseList.add(expensentry);
				//expensentry.setID(Integer.parseInt(cursor.getString(0)));
				// expensentry.setName(cursor.getString(1));
				//expenseList2.add(cursor.getString(cursor.getColumnIndex("Budget_Type")));
				expenseList2.add(cursor.getString(0));
				expenseList2.add(cursor.getString(3));
				expenseList2.add(cursor.getString(1));
				expenseList2.add(cursor.getString(4));
			} while (cursor.moveToNext());
		}

		// return contact list
		return expenseList2;
	}


	public void excellexport(String StartDate, String EndDate) {
		// ArrayList<ExpenseEntry> expenseList = new ArrayList<ExpenseEntry>();

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

		CSVWriter csvWrite = null;

		// File exportDir = new File(Environment.getExternalStorageDirectory(),
		// "");
		File exportDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
				"");

		if (!exportDir.exists())

		{
			exportDir.mkdirs();
		}

		File file = new File(exportDir, "EfdExportDATA.csv");

		try {
			if (file.createNewFile()) {
				System.out.println("File is created!");
				System.out.println("myfile.csv " + file.getAbsolutePath());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			csvWrite = new CSVWriter(new FileWriter(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String selectQuery = "SELECT * FROM Budget_Tracking_Amount where Budget_Julian_Entrydate between '"
				+ Startdate2 + "' and '" + Enddate2 + "' ";
		Cursor curCSV = db.rawQuery(selectQuery, null);

		// Cursor curCSV=db.getdb().rawQuery("select * from " +
		// db.TABLE_NAME,null);

		csvWrite.writeNext(curCSV.getColumnNames());

		while (curCSV.moveToNext())

		{

			// ExpenseAmount =
			// BudgetAmountAdapter.getExpenseAmountByTag3("transport");

			String arrStr[] = { curCSV.getString(0), curCSV.getString(1),
					curCSV.getString(2), curCSV.getString(3),
					curCSV.getString(4) };

			/* curCSV.getString(3),curCSV.getString(4)}; */

			csvWrite.writeNext(arrStr);

		}

		try {
			csvWrite.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		curCSV.close();

		/*
		 * if (cursor.moveToFirst()) { do { ExpenseEntry expensentry = new
		 * ExpenseEntry();
		 * expensentry.setID(Integer.parseInt(cursor.getString(0)));
		 * //expensentry.setName(cursor.getString(1));
		 * expensentry.setName(cursor
		 * .getString(cursor.getColumnIndex("Budget_Type")));
		 * expensentry.setEntryAmount(cursor.getString(3));
		 * expensentry.setEntryName(cursor.getString(1));
		 * expensentry.setEntryDate(cursor.getString(4)); // Adding contact to
		 * list expenseList.add(expensentry); } while (cursor.moveToNext()); }
		 */

		// return contact list
		// return expenseList;
	}

	public int getJulianEntryDate(String entryDate) {

		Cursor cursor3 = db.rawQuery("select strftime('%s', '" + entryDate
				+ "')", null);
		cursor3.moveToFirst();
		int NewEntryDate = Integer.parseInt(cursor3.getString(0));
		cursor3.close();
		return NewEntryDate;

	}

	public boolean deleteExpenseEntry(int id, String ExpenseTag) {
		db.delete("Budget_Tracking_Amount", " _id=?",
				new String[] { String.valueOf(id) });

		String selectQuery = "SELECT * FROM Budget_Tracking_Amount where Budget_Type = '"
				+ ExpenseTag + "'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Cursor cursor=db.query("Budget_Tracking_Amount", null, null, null,
		// null, null, null);
		if (cursor.getCount() < 1) // empty table
		{
			cursor.close();
			return true;
		} else {

			return false;
		}

	}

	public void deleteExpenseEntry2(String ExpenseTag) {
		db.delete("Budget_Tracking_Amount", " Budget_Type=?",
				new String[] { String.valueOf(ExpenseTag) });

		// db.delete("Budget_Tracking_Amount"," Budget_Type=?", new
		// String[]{ExpenseTag});

		// String selectQuery =
		// "DELETE FROM Budget_Tracking_Amount where Budget_Type = '" +
		// ExpenseTag + "'";
		// Cursor cursor = db.rawQuery(selectQuery, null);
		// cursor.close();

	}

}