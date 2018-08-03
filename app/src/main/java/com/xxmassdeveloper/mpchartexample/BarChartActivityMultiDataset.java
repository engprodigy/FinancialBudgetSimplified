
package com.xxmassdeveloper.mpchartexample;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.dianet.efd_app.BudgetAmountAdapter;
import com.dianet.efd_app.BudgetUpdateAdapter;
import com.dianet.efd_app.R;
import com.dianet.efd_app.TagDescriptionUpdateAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.LargeValueFormatter;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


class BudgetDescription3 {

    // private variables
    int _id;
    String _budget_name;
    String _budget_amount;
    String _budget_spent_amount;

    public BudgetDescription3() {

    }

    // constructor
    public BudgetDescription3(int id, String _budget_name,
                              String _budget_amount, String _budget_spent_amount) {
        this._id = id;
        this._budget_name = _budget_name;
        this._budget_amount = _budget_amount;
        this._budget_spent_amount = _budget_spent_amount;
    }

    // constructor

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    public void setBudgetName(String _budget_name) {
        this._budget_name = _budget_name;
    }

    public void setBudgetAmount(String _budget_amount) {
        this._budget_amount = _budget_amount;
    }

    public void setBudgetSpentAmount(String _budget_spent_amount) {
        this._budget_spent_amount = _budget_spent_amount;
    }

    // getting name

    public String getBudgetName() {
        return this._budget_name;
    }

    public String getBudgetAmount() {
        return this._budget_amount;
    }

    public String getBudgetSpentAmount() {
        return this._budget_spent_amount;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id",_id + "");
        map.put("_budget_name", _budget_name);
        map.put("_budget_amount","Budget(monthly):" + "  " + _budget_amount);
        map.put("_budget_spent_amount","Spent:" + "  " + _budget_spent_amount);
        map.put("_budget_amount2", _budget_amount);
        //	map.put("orders", getOrders() + "");

        return map;
    }

}


public class BarChartActivityMultiDataset extends DemoBase implements OnSeekBarChangeListener, OnChartValueSelectedListener {

    private BarChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    com.dianet.efd_app.TagDescriptionUpdateAdapter TagDescriptionUpdateAdapter;
    com.dianet.efd_app.BudgetUpdateAdapter BudgetUpdateAdapter;
    com.dianet.efd_app.BudgetAmountAdapter BudgetAmountAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_barchart);

       // tvX = (TextView) findViewById(R.id.tvXMax);
       // tvY = (TextView) findViewById(R.id.tvYMax);

       // mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
       // mSeekBarX.setOnSeekBarChangeListener(this);

       // mSeekBarY = (SeekBar) findViewById(R.id.seekBar2);
       // mSeekBarY.setOnSeekBarChangeListener(this);

        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDescription("");

        //retrieve content from database for chart
        TagDescriptionUpdateAdapter = new TagDescriptionUpdateAdapter(this);
        TagDescriptionUpdateAdapter = TagDescriptionUpdateAdapter.open();

        BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
        BudgetUpdateAdapter = BudgetUpdateAdapter.open();

        BudgetAmountAdapter = new BudgetAmountAdapter(this);
        BudgetAmountAdapter = BudgetAmountAdapter.open();
        
        // disable the drawing of values
        mChart.setDrawYValues(false);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setValueFormatter(new LargeValueFormatter());

        mChart.setDrawBarShadow(false);
        
        mChart.setDrawGridBackground(false);
        mChart.setDrawHorizontalGrid(false);
        
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // define an offset to change the original position of the marker
        // (optional)
//        mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

        // set the marker to the chart
        mChart.setMarkerView(mv);
        mChart.setUnit("(N)");

       // mSeekBarX.setProgress(10);
       // mSeekBarY.setProgress(100);
        
        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        
        //Legend l = mChart.getLegend();
        //l.setPosition(LegendPosition.RIGHT_OF_CHART_INSIDE);
        //l.setTypeface(tf);
        
        XLabels xl  = mChart.getXLabels();
        xl.setCenterXLabelText(true);
        xl.setTypeface(tf);
        
        YLabels yl = mChart.getYLabels();
        yl.setTypeface(tf);
        //yl.setFormatter(new LargeValueFormatter());
        
        mChart.setValueTypeface(tf);
        setData(12, 50);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                if (mChart.isDrawYValuesEnabled())
                    mChart.setDrawYValues(false);
                else
                    mChart.setDrawYValues(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggle3D: {
                if (mChart.is3DEnabled())
                    mChart.set3DEnabled(false);
                else
                    mChart.set3DEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (mChart.isHighlightEnabled())
                    mChart.setHighlightEnabled(false);
                else
                    mChart.setHighlightEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlightArrow: {
                if (mChart.isDrawHighlightArrowEnabled())
                    mChart.setDrawHighlightArrow(false);
                else
                    mChart.setDrawHighlightArrow(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleStartzero: {
                if (mChart.isStartAtZeroEnabled())
                    mChart.setStartAtZero(false);
                else
                    mChart.setStartAtZero(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleAdjustXLegend: {
                XLabels xLabels = mChart.getXLabels();
                
                if (xLabels.isAdjustXLabelsEnabled())
                    xLabels.setAdjustXLabels(false);
                else
                    xLabels.setAdjustXLabels(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000);
                break;
            }
            case R.id.animateXY: {

                mChart.animateXY(3000, 3000);
                break;
            }
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        
       /* tvX.setText("" + (mSeekBarX.getProgress() + 1));
        tvY.setText("" + (mSeekBarY.getProgress()));

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < mSeekBarX.getProgress(); i++) {
            xVals.add((i+1990) + "");
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();
        
        float mult = mSeekBarY.getProgress() * 10000000f;

        for (int i = 0; i < mSeekBarX.getProgress(); i++) {
            float val = (float) (Math.random() * mult) + 3;
            yVals1.add(new BarEntry(val, i));
        }

        for (int i = 0; i < mSeekBarX.getProgress(); i++) {
            float val = (float) (Math.random() * mult) + 3;
            yVals2.add(new BarEntry(val, i));
        }

        for (int i = 0; i < mSeekBarX.getProgress(); i++) {
            float val = (float) (Math.random() * mult) + 3;
            yVals3.add(new BarEntry(val, i));
        }

        // create 3 datasets with different types
        BarDataSet set1 = new BarDataSet(yVals1, "Company A");
//        set1.setColors(ColorTemplate.createColors(getApplicationContext(), ColorTemplate.FRESH_COLORS));
        set1.setColor(Color.rgb(104, 241, 175));
        BarDataSet set2 = new BarDataSet(yVals2, "Company B");
        set2.setColor(Color.rgb(164, 228, 251));
        BarDataSet set3 = new BarDataSet(yVals3, "Company C");
        set3.setColor(Color.rgb(242, 247, 158));
        
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);

        BarData data = new BarData(xVals, dataSets);
        
        // add space between the dataset groups in percent of bar-width
        data.setGroupSpace(110f);

        mChart.setData(data);
        mChart.invalidate();*/
    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();



        String[] fromColumns = TagDescriptionUpdateAdapter.getTagDescription();

        @SuppressWarnings("unused")

        int expenseTaglength = fromColumns.length;


        String[] fromColumns3 = new String[expenseTaglength];
        String[] budgetAmountArray = new String[expenseTaglength];

        for (int i = 1; i < expenseTaglength + 1; i++) {
            String expenseTag2 = fromColumns[i - 1];
            budgetAmountArray[i - 1] = BudgetAmountAdapter
                    .getExpenseAmountByTag(expenseTag2);
            fromColumns3[i - 1] = Integer.toString(BudgetUpdateAdapter
                    .getTotalExpenseAmountByTag2(expenseTag2));

        }
        String[] fromColumns4 = new String[expenseTaglength];
        ArrayList<BudgetDescription2> budgetList = new ArrayList<BudgetDescription2>();
        ArrayList<String> budgetList2 = new ArrayList<String>();

        for (int i = 1; i < expenseTaglength + 1; i++) {
            String budgetAmountStatusKey = BudgetUpdateAdapter
                    .getExpenseAmountByTag(fromColumns[i - 1]);
            BudgetDescription2 budgetentry = new BudgetDescription2();
            budgetentry.setBudgetName(fromColumns[i - 1]);
            budgetentry.setBudgetAmount(budgetAmountArray[i - 1]);
            budgetentry.setBudgetSpentAmount(fromColumns3[i - 1]);
            budgetList.add(budgetentry);
            budgetList2.add(fromColumns[i - 1]);
            budgetList2.add(budgetAmountArray[i - 1]);
            budgetList2.add(fromColumns3[i - 1]);

            if (Integer.parseInt(budgetAmountStatusKey) <= 0) {
                String expense_entry_description = fromColumns[i - 1]
                        + "           " + "           " + "       " + "       ";
                fromColumns4[i - 1] = expense_entry_description;

            } else {
                String expense_entry_description = fromColumns[i - 1]
                        + "           " + "           " + fromColumns3[i - 1]
                        + "       " + "       " + fromColumns3[i - 1];
                fromColumns4[i - 1] = expense_entry_description;
            }

        }
        // ArrayList budgetList2;
        for(int i = 0; i<budgetList2.size(); i = i + 3){
            xVals.add(budgetList2.get(i));
            //xVals.add(budgetList2.get(i));
            //xVals.add(budgetList2.get(i));
            // xVals.add("cue");
          /* xVals.add("cue");
           xVals.add("cue");
           xVals.add("cue");
           xVals.add("cue");
           xVals.add("cue");
           xVals.add("cue");
           xVals.add("cue");*/
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
       // ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();

       /* float mult = mSeekBarY.getProgress() * 10000000f;

        for (int i = 0; i < mSeekBarX.getProgress(); i++) {
            float val = (float) (Math.random() * mult) + 3;
            yVals1.add(new BarEntry(val, i));
        }

        for (int i = 0; i < mSeekBarX.getProgress(); i++) {
            float val = (float) (Math.random() * mult) + 3;
            yVals2.add(new BarEntry(val, i));
        }*/

        int j = 0;
        for(int i = 1; i<budgetList2.size(); i = i + 3){
            float val = Float.parseFloat(budgetList2.get(i));

            yVals1.add(new BarEntry(val, j));
            j++;
        }

        int k = 0;
        for(int i = 2; i<budgetList2.size(); i = i + 3){
            float val = Float.parseFloat(budgetList2.get(i));

            yVals2.add(new BarEntry(val, k));
            k++;
        }

        // create 3 datasets with different types
        BarDataSet set1 = new BarDataSet(yVals1, "Budget Amount");
//        set1.setColors(ColorTemplate.createColors(getApplicationContext(), ColorTemplate.FRESH_COLORS));
        set1.setColor(Color.rgb(104, 241, 175));
        BarDataSet set2 = new BarDataSet(yVals2, "Spent");
       // set2.setColor(Color.rgb(164, 228, 251));
        set2.setColor(Color.RED);
        //BarDataSet set3 = new BarDataSet(yVals3, "Company C");
        //set3.setColor(Color.RED);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        //dataSets.add(set3);

        BarData data = new BarData(xVals, dataSets);

        // add space between the dataset groups in percent of bar-width
        data.setGroupSpace(110f);

        mChart.setData(data);
        mChart.invalidate();


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void onValueSelected(Entry e, int dataSetIndex) {
        Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + dataSetIndex);
    }
    
    @Override
    public void onNothingSelected() {
        Log.i("Activity", "Nothing selected.");
    }
}
