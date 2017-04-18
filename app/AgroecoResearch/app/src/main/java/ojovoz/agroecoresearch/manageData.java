package ojovoz.agroecoresearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Eugenio on 17/04/2017.
 */
public class manageData extends AppCompatActivity {

    public int userId;
    public int userRole;
    public String update;

    public agroecoHelper agroHelper;

    public ArrayList<CheckBox> checkboxes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data);

        agroHelper = new agroecoHelper(this, "crops,fields,treatments,activities,measurements,log");

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        update = getIntent().getExtras().getString("update");

        if(update.equals("activity")){
            int logId = getIntent().getExtras().getInt("logId");
            String aD = getIntent().getExtras().getString("activityDate");
            Float aV = getIntent().getExtras().getFloat("activityValue");
            String aC = getIntent().getExtras().getString("activityComments");
            agroHelper.updateLogActivityEntry(logId, aD, aV, aC);
        }

        TextView tt = (TextView)findViewById(R.id.logTableTitle);
        tt.setText(R.string.logTableTitle);

        checkboxes = new ArrayList<>();

        fillTable();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0, R.string.sendMenuText);
        menu.add(1, 1, 1, R.string.deleteMenuText);
        menu.add(2, 2, 2, R.string.filtersMenuText);
        menu.add(3, 3, 3, R.string.manageInputsMenuText);
        menu.add(4, 4, 4, R.string.invertSelectedMenuText);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                //send
                break;
            case 1:
                //delete
                break;
            case 2:
                //filters
                break;
            case 3:
                //inputs
                break;
            case 4:
                invertSelectedCheckboxes();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onBackPressed(){
        final Context context = this;
        Intent i = new Intent(context, mainMenu.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        startActivity(i);
        finish();
    }

    public void invertSelectedCheckboxes(){
        Iterator<CheckBox> iterator = checkboxes.iterator();
        while(iterator.hasNext()) {
            CheckBox cb = iterator.next();
            cb.setChecked(!cb.isChecked());
        }
    }

    public void fillTable(){
        TableLayout logTable = (TableLayout) findViewById(R.id.logTable);
        logTable.removeAllViews();

        ArrayList<oLog> filteredLog = agroHelper.log;

        int n=0;
        Iterator<oLog> logIterator = filteredLog.iterator();
        while(logIterator.hasNext()){
            oLog logElement = logIterator.next();
            final TableRow trow = new TableRow(manageData.this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
            lp.setMargins(10,10,0,10);

            if(n%2==0){
                trow.setBackgroundColor(ContextCompat.getColor(this,R.color.lightGray));
            } else {
                trow.setBackgroundColor(ContextCompat.getColor(this,R.color.colorWhite));
            }

            String itemName="";
            if(logElement.logActivityId>=0){
                itemName=agroHelper.getActivityNameFromId(logElement.logActivityId);
            }
            String tvText=agroHelper.dateToString(logElement.logDate)+", "+agroHelper.getFieldNameFromId(logElement.logFieldId)+"\n"+itemName;

            CheckBox cb = new CheckBox(manageData.this);
            cb.setButtonDrawable(R.drawable.custom_checkbox);
            cb.setId(logElement.logId);
            cb.setPadding(10,10,10,10);
            cb.setChecked(true);
            checkboxes.add(cb);
            trow.addView(cb,lp);

            TextView tv = new TextView(manageData.this);
            tv.setId(logElement.logId);
            tv.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f);
            tv.setText(tvText);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            tv.setPadding(0,10,0,10);
            tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    viewItem(v,v.getId());
                }

            });
            trow.addView(tv,lp);

            trow.setGravity(Gravity.CENTER_VERTICAL);
            logTable.addView(trow, lp);

            n++;
        }
        if(n==0){
            TextView tt = (TextView)findViewById(R.id.logTableTitle);
            tt.setText(R.string.noLoggedDataMessage);
        }
    }

    public void viewItem(View v,int id){
        TextView tv = (TextView)v;
        tv.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        oLog logItem = agroHelper.getLogItemFromId(id);
        oCrop primaryCrop=null;

        if(logItem.logActivityId>=0) {
            oField lf = agroHelper.getFieldFromId(logItem.logFieldId);

            String treatmentsTitle = "";
            if(logItem.logPlotNumber>=0) {
                oPlot plot = lf.plots.get(logItem.logPlotNumber);
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

            if(logItem.logPlotNumber>=0) {
                activityTitle = "Field: " + lf.fieldName + " R" + Integer.toString(lf.fieldReplicationN) + "\nPlot " + Integer.toString(logItem.logPlotNumber + 1) + ": " + primaryCrop.cropName + " (" + primaryCrop.cropVariety + ")" + treatmentsTitle + "\nActivity: " + agroHelper.getActivityNameFromId(logItem.logActivityId);
            } else {
                activityTitle = "Field: " + lf.fieldName + " R" + Integer.toString(lf.fieldReplicationN) + "\nActivity: " + agroHelper.getActivityNameFromId(logItem.logActivityId);
            }

            final Context context = this;
            Intent i = new Intent(context, enterActivity.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("logId",logItem.logId);
            i.putExtra("fieldId", logItem.logFieldId);
            i.putExtra("plot", logItem.logPlotNumber);
            i.putExtra("activity",logItem.logActivityId);
            i.putExtra("update", "activity");
            i.putExtra("title",activityTitle);
            i.putExtra("units",agroHelper.getActivityMeasurementUnitsFromId(logItem.logActivityId));
            i.putExtra("date",agroHelper.dateToString(logItem.logDate));
            i.putExtra("activityValue",logItem.logNumberValue);
            i.putExtra("activityComments",logItem.logComments);
            startActivity(i);
            finish();
        }
    }
}
