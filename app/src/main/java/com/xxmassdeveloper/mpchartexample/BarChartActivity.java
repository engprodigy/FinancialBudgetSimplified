
package com.xxmassdeveloper.mpchartexample;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.dianet.efd_app.BudgetAmountAdapter;
import com.dianet.efd_app.BudgetUpdateAdapter;
import com.dianet.efd_app.R;
import com.dianet.efd_app.TagDescriptionUpdateAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.filter.Approximator;
import com.github.mikephil.charting.data.filter.Approximator.ApproximatorType;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels;
import com.github.mikephil.charting.utils.YLabels.YLabelPosition;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class BudgetDescription2 {

    // private variables
    int _id;
    String _budget_name;
    String _budget_amount;
    String _budget_spent_amount;

    public BudgetDescription2() {

    }

    // constructor
    public BudgetDescription2(int id, String _budget_name,
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

public class BarChartActivity extends DemoBase implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    protected BarChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    TagDescriptionUpdateAdapter TagDescriptionUpdateAdapter;
    BudgetUpdateAdapter BudgetUpdateAdapter;
    BudgetAmountAdapter BudgetAmountAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_barchart);

       // tvX = (TextView) findViewById(R.id.tvXMax);
        //tvY = (TextView) findViewById(R.id.tvYMax);

       // mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
       // mSeekBarY = (SeekBar) findViewById(R.id.seekBar2);

        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        //retrieve content from database for chart
        TagDescriptionUpdateAdapter = new TagDescriptionUpdateAdapter(this);
        TagDescriptionUpdateAdapter = TagDescriptionUpdateAdapter.open();

        BudgetUpdateAdapter = new BudgetUpdateAdapter(this);
        BudgetUpdateAdapter = BudgetUpdateAdapter.open();

        BudgetAmountAdapter = new BudgetAmountAdapter(this);
        BudgetAmountAdapter = BudgetAmountAdapter.open();


        // enable the drawing of values
        mChart.setDrawYValues(true);
        
        mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // disable 3D
        mChart.set3DEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        //mChart.setUnit(" €");
        mChart.setUnit(" N");
        
        // mChart.setDrawXLabels(false);

        mChart.setDrawGridBackground(false);
        mChart.setDrawHorizontalGrid(true);
        mChart.setDrawVerticalGrid(false);
        // mChart.setDrawYLabels(false);

        // sets the text size of the values inside the chart
        mChart.setValueTextSize(10f);

        mChart.setDrawBorder(false);
        // mChart.setBorderPositions(new BorderPosition[] {BorderPosition.LEFT,
        // BorderPosition.RIGHT});

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XLabels xl = mChart.getXLabels();
        xl.setPosition(XLabelPosition.BOTTOM);
        xl.setCenterXLabelText(true);
        xl.setTypeface(tf);

        YLabels yl = mChart.getYLabels();
        yl.setTypeface(tf);
        yl.setLabelCount(8);
        yl.setPosition(YLabelPosition.BOTH_SIDED);

        mChart.setValueTypeface(tf);

        setData(12, 50);

        // setting data
       // mSeekBarY.setProgress(50);
       // mSeekBarX.setProgress(12);

       // mSeekBarY.setOnSeekBarChangeListener(this);
      //  mSeekBarX.setOnSeekBarChangeListener(this);

        //Legend l = mChart.getLegend();
      //  l.setPosition(LegendPosition.BELOW_CHART_LEFT);
       // l.setFormSize(8f);
       // l.setXEntrySpace(4f);

        // mChart.setDrawLegend(false);
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
            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

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
            case R.id.actionToggleAdjustXLegend: {
                XLabels xLabels = mChart.getXLabels();

                if (xLabels.isAdjustXLabelsEnabled())
                    xLabels.setAdjustXLabels(false);
                else
                    xLabels.setAdjustXLabels(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleFilter: {

                Approximator a = new Approximator(ApproximatorType.DOUGLAS_PEUCKER, 25);

                if (!mChart.isFilteringEnabled()) {
                    mChart.enableFiltering(a);
                } else {
                    mChart.disableFiltering();
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                if (mChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();
                break;
            }
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

       // tvX.setText("" + (mSeekBarX.getProgress() + 1));
       // tvY.setText("" + (mSeekBarY.getProgress()));

       // setData(mSeekBarX.getProgress(), mSeekBarY.getProgress());
       // mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        //for (int i = 0; i < count; i++) {
        //    xVals.add(mMonths[i % 12]);
        //}
        //retrive content from database for chart
        String[] fromColumns = TagDescriptionUpdateAdapter.getTagDescription();

        @SuppressWarnings("unused")
        //Intent intent = getIntent();

                //String starttime = intent.getExtras().getString("Value1");
                //String endtime = intent.getExtras().getString("Value2");

                int expenseTaglength = fromColumns.length;
        // int expenseTaglength = 2;

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

      //  for (int i = 0; i < count; i++) {
       //     float mult = (range + 1);
       //     float val = (float) (Math.random() * mult);
        //    yVals1.add(new BarEntry(val, i));
       // }
        int j = 0;
        for(int i = 2; i<budgetList2.size(); i = i + 3){
            float val = Float.parseFloat(budgetList2.get(i));

            yVals1.add(new BarEntry(val, j));

            /*yVals1.add(new BarEntry(val, 1));
            yVals1.add(new BarEntry(val, 2));
            yVals1.add(new BarEntry(val, 3));
            yVals1.add(new BarEntry(val, 4));
            yVals1.add(new BarEntry(val, 5));
            yVals1.add(new BarEntry(val, 6));
            yVals1.add(new BarEntry(val, 7));*/
             j++;
        }


        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);

        mChart.setData(data);
    }

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, int dataSetIndex) {

        if (e == null)
            return;

        RectF bounds = mChart.getBarBounds((BarEntry) e);
        PointF position = mChart.getPosition(e);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());
    }

    public void onNothingSelected() {
    };
}
