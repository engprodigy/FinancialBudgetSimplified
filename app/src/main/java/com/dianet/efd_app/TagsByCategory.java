package com.dianet.efd_app;

/**
 * Created by BELLO on 13/02/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ExpandableListView;

public class TagsByCategory extends Activity {

    public static final String EXTRA_STRING_NAME4 = "extraStringName4";
    String category = null;
    final String children = null;
    // more efficient than HashMap for mapping integers to objects
    SparseArray<Group> groups = new SparseArray<Group>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_main_activity);
       // Intent intent = getIntent();
        //category = intent.getStringExtra(CategoryListAdapter.categorytype);
       /* if(category != null) {
            Intent data = new Intent();
            data.putExtra(EXTRA_STRING_NAME4, category);
            setResult(RESULT_OK, data);
            //super.finish();
            finish();
        }*/
        createData();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
        CategoryListAdapter adapter = new CategoryListAdapter(this,
                groups);
        listView.setAdapter(adapter);
       // ViewGroup layout = (ViewGroup) findViewById(R.id.activity_main);
       // EditText EditTag = (EditText) findViewById(R.id.edit_tag);
       // Intent intent = getIntent();
       // category = intent.getStringExtra(CategoryListAdapter.categorytype);
       // Toast.makeText(getApplicationContext(), children,
            //  Toast.LENGTH_SHORT).show();

      /*  if(category != null) {
            Intent data = new Intent();
            data.putExtra(EXTRA_STRING_NAME4, category);
            setResult(RESULT_OK, data);
            //super.finish();
            finish();
        }*/
        //Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
       // intent3.putExtra("categorytype",children);
       // startActivity(intent3);
    }

    public void createData() {
        for (int j = 0; j < 14; j++) {
            Group group = new Group();
            if(j == 0){
                group.string = "Tithes";
        }
            else if(j == 1){
                group.string = "Taxes";
            }
            else if(j == 2){
                group.string = "Rent/Mortgage";
            }
            else if(j == 3){
                group.string = "Savings";
            }
            else if(j == 4){
                group.string = "Home Maintenance";


                for (int i = 0; i < 5; i++) {

                    if(i == 0) {
                        group.children.add("Electricity");
                    }
                    else if(i == 1){
                        group.children.add("Water");
                    }
                    else if(i == 2){
                        group.children.add("Telephone");
                    }
                    else if(i == 3){
                        group.children.add("Internet");
                    }
                    else if(i == 4){
                        group.children.add("Others");
                    }

                }
            }
            else if(j == 5){
                group.string = "Food";
            }
            else if(j == 6){
                group.string = "Transport";
                for (int i = 0; i < 5; i++) {

                    if(i == 0) {
                        group.children.add("Car loan repayment");
                    }
                    else if(i == 1){
                        group.children.add("Fuel");
                    }
                    else if(i == 2){
                        group.children.add("Insurance");
                    }
                    else if(i == 3){
                        group.children.add("Maintenance");
                    }
                    else if(i == 4){
                        group.children.add("Others");
                    }


                }
            }
            else if(j == 7){
                group.string = "School/Childcare";
            }
            else if(j == 8){
                group.string = "Entertainment";
            }
            else if(j == 9){
                group.string = "Pets";
                for (int i = 0; i < 4; i++) {

                    if(i == 0) {
                        group.children.add("Food");
                    }
                    else if(i == 1){
                        group.children.add("Vitamins");
                    }
                    else if(i == 2){
                        group.children.add("Verterinary");
                    }
                    else if(i == 3){
                        group.children.add("Others");
                    }


                }
            }
            else if(j == 10){
                group.string = "Debts";
            }
            else if(j == 11){
                group.string = "Clothing";
            }
            else if(j == 12){
                group.string = "Medical";
            }
            else if(j == 13){
                group.string = "Miscellaneous";
                for (int i = 0; i < 4; i++) {

                    if(i == 0) {
                        group.children.add("Costmetics");
                    }
                    else if(i == 1){
                        group.children.add("Laundry");
                    }
                    else if(i == 2){
                        group.children.add("Barbing");
                    }
                    else if(i == 3){
                        group.children.add("Gift");
                    }


                }
            }
            else {
               group.string = "Test " + j;

            for (int i = 0; i < 5; i++) {

                    group.children.add("Sub Item" + i);

                        }
            }
            groups.append(j, group);
        }
    }

}
