package ojovoz.agroecoresearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Eugenio on 05/04/2017.
 */
public class chooser extends AppCompatActivity {

    public int userId;
    public int userRole;
    public String task;
    public int fieldId;

    public oField field;
    public int plotN;
    public oPlot plot;

    public oCrop primaryCrop = new oCrop();
    public ArrayList<oActivity> activities;

    public agroecoHelper agroHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        String treatmentsTitle="";

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");

        if(task.equals("activity")) {
            agroHelper = new agroecoHelper(this, "crops,fields,treatments,activities");
        }

        fieldId = getIntent().getExtras().getInt("field");
        field = agroHelper.getFieldFromId(fieldId);
        plotN = getIntent().getExtras().getInt("plot");
        if(plotN>=0){
            plot = field.plots.get(plotN);

            primaryCrop = plot.primaryCrop;

            if(plot.intercroppingCrop!=null){
                treatmentsTitle = "\nIntercropping";
            }
            if(plot.hasSoilManagement){
                if(treatmentsTitle.isEmpty()){
                    treatmentsTitle="\nSoil management";
                } else {
                    treatmentsTitle+=", Soil management";
                }
            }
            if(plot.hasPestControl){
                if(treatmentsTitle.isEmpty()){
                    treatmentsTitle="\nPest control";
                } else {
                    treatmentsTitle+=", Pest control";
                }
            }
        } else {
            plot=null;
        }

        TextView tt = (TextView)findViewById(R.id.tableTitle);
        if(plotN>=0) {
            tt.setText("Plot " + Integer.toString(plotN + 1) + ": " + primaryCrop.cropName + " (" + primaryCrop.cropVariety + ")" + treatmentsTitle);
        } else {
            tt.setText("Entire field");
        }

        CharSequence title = getTitle() + " " + task + ": " + field.fieldName + " R" + Integer.toString(field.fieldReplicationN);
        setTitle(title);

        if(getIntent().getExtras().getBoolean("newActivity")){
            agroHelper.addActivityToLog(fieldId, plotN, userId, getIntent().getExtras().getInt("activity"),
                    getIntent().getExtras().getString("activityDate"), getIntent().getExtras().getFloat("activityValue"),
                    getIntent().getExtras().getString("activityComments"));
        }

        fillTable();
    }

    @Override public void onBackPressed(){
        final Context context = this;
        Intent i = new Intent(context, chooseFieldPlot.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        i.putExtra("task",task);
        i.putExtra("field", fieldId);
        startActivity(i);
        finish();
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public void fillTable(){
        TableLayout chooserTable = (TableLayout) findViewById(R.id.chooserTable);
        chooserTable.removeAllViews();
        if(task.equals("activity")){
            activities = agroHelper.getActivities(plot,field);
            Iterator<oActivity> iterator = activities.iterator();
            int n=0;
            while (iterator.hasNext()) {
                final TableRow trow = new TableRow(chooser.this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                lp.setMargins(4,4,4,4);

                if(n%2==0){
                    trow.setBackgroundColor(ContextCompat.getColor(this,R.color.lightGray));
                } else {
                    trow.setBackgroundColor(ContextCompat.getColor(this,R.color.colorWhite));
                }

                oActivity activity = iterator.next();
                String daysAgo = "";
                String nDays = agroHelper.getActivityDaysAgo(activity.activityId,plotN,fieldId);

                TextView tv = new TextView(chooser.this);
                tv.setId(n);
                tv.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
                tv.setText(activity.activityName);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f);
                tv.setPadding(4,4,4,4);
                if(!nDays.equals("0")) {
                    tv.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            chooseItem(v.getId(), v);
                        }

                    });
                }
                trow.addView(tv,lp);
                trow.setGravity(Gravity.CENTER_VERTICAL);
                chooserTable.addView(trow, lp);

                final TableRow trowBelow = new TableRow(chooser.this);
                TableRow.LayoutParams lpBelow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                lpBelow.setMargins(4,0,4,0);

                if(n%2==0){
                    trowBelow.setBackgroundColor(ContextCompat.getColor(this,R.color.lightGray));
                } else {
                    trowBelow.setBackgroundColor(ContextCompat.getColor(this,R.color.colorWhite));
                }

                TextView tvBelow = new TextView(chooser.this);
                tvBelow.setTextColor(ContextCompat.getColor(this,R.color.colorBlack));

                if(nDays.equals("-1")){
                    daysAgo = getString(R.string.neverText);
                } else if (nDays.equals("0")) {
                    daysAgo = getString(R.string.todayText);
                } else if (nDays.equals("1")){
                    daysAgo = getString(R.string.yesterdayText);
                } else {
                    if(isNumeric(nDays)) {
                        daysAgo = nDays + " " + getString(R.string.daysAgoText);
                    } else {
                        daysAgo = nDays;
                    }
                }
                tvBelow.setText(getString(R.string.lastText)+" : "+daysAgo);

                tvBelow.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15f);
                tvBelow.setPadding(4,0,4,0);
                trowBelow.addView(tvBelow,lpBelow);
                trowBelow.setGravity(Gravity.CENTER_VERTICAL);
                chooserTable.addView(trowBelow, lpBelow);

                n++;
            }
        }
    }

    public void chooseItem(int id, View v){
        TextView tv = (TextView)v;
        tv.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        final Context context = this;
        if(task.equals("activity")) {
            Intent i = new Intent(context, enterActivity.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("task", task);
            i.putExtra("field", fieldId);
            i.putExtra("plot", plotN);
            i.putExtra("activity", activities.get(id).activityId);
            i.putExtra("update","");

            String treatmentsTitle = "";
            if(plotN>=0) {
                plot = field.plots.get(plotN);
                primaryCrop = plot.primaryCrop;

                if (plot.intercroppingCrop != null) {
                    treatmentsTitle = "\nIntercropping";
                }
                if (plot.hasSoilManagement) {
                    if (treatmentsTitle.isEmpty()) {
                        treatmentsTitle = "\nSoil management";
                    } else {
                        treatmentsTitle += ", Soil management";
                    }
                }
                if (plot.hasPestControl) {
                    if (treatmentsTitle.isEmpty()) {
                        treatmentsTitle = "\nPest control";
                    } else {
                        treatmentsTitle += ", Pest control";
                    }
                }
            }

            String activityTitle="";

            if(plotN>=0) {
                activityTitle = "Field: " + field.fieldName + " R" + Integer.toString(field.fieldReplicationN) + "\nPlot " + Integer.toString(plotN + 1) + ": " + primaryCrop.cropName + " (" + primaryCrop.cropVariety + ")" + treatmentsTitle + "\nActivity: " + activities.get(id).activityName;
            } else {
                activityTitle = "Field: " + field.fieldName + " R" + Integer.toString(field.fieldReplicationN) + "\nActivity: " + activities.get(id).activityName;
            }

            i.putExtra("title",activityTitle);
            i.putExtra("units",activities.get(id).activityMeasurementUnits);

            startActivity(i);
            finish();
        }
    }
}
