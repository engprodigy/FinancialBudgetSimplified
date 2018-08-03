package com.dianet.efd_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ListViewLoader extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_list_view_loader, menu);
		return true;
	}
}
