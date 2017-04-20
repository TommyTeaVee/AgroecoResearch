package ojovoz.agroecoresearch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Eugenio on 17/04/2017.
 */
public class manageData extends AppCompatActivity implements httpConnection.AsyncResponse {

    public int userId;
    public int userRole;
    public String update;
    public String server;

    public agroecoHelper agroHelper;

    public ArrayList<CheckBox> checkboxes;

    private preferenceManager prefs;

    private Context context;
    private ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data);

        context = this;

        prefs = new preferenceManager(this);
        server = prefs.getPreference("server");

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
                sendSelectedEntries();
                break;
            case 1:
                deleteSelectedEntries();
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

    public void sendSelectedEntries(){
        String selected="";
        int nSelected=0;
        Iterator<CheckBox> iterator = checkboxes.iterator();
        while(iterator.hasNext()) {
            CheckBox cb = iterator.next();
            if(cb.isChecked()){
                selected+=Integer.toString(cb.getId())+",";
                nSelected++;
            }
        }
        if(!selected.equals("")){
            httpConnection http = new httpConnection(this,this);
            if(http.isOnline()) {
                final String mail = prefs.getPreference("mail");
                final String password = prefs.getPreference("password");
                final String smtpServer = prefs.getPreference("smtpServer");
                final String smtpPort = prefs.getPreference("smtpPort");

                if(!mail.equals("") && !password.equals("") && !smtpServer.equals("") && !smtpPort.equals("")) {

                    final boolean allSelected = (nSelected==checkboxes.size());

                    final String body = agroHelper.getSelectedLogItemsAsString(selected);
                    final String finalSelected = selected;
                    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected void onPreExecute() {
                            pd = new ProgressDialog(context);
                            pd.setTitle(R.string.sendingDataMessage);
                            pd.setMessage(getString(R.string.pleaseWaitMessage));
                            pd.setCancelable(false);
                            pd.setIndeterminate(true);
                            pd.show();
                        }

                        @Override
                        protected Void doInBackground(Void... arg0) {
                            try {
                                Mail m = new Mail(mail, password, smtpServer, smtpPort);
                                String[] toArr = {mail};
                                m.setTo(toArr);
                                m.setFrom(mail);
                                m.setSubject("pA439urcjLVk6szA");
                                m.setBody(body);
                                try {
                                    m.send();
                                } catch (Exception e) {
                                    Toast.makeText(context, R.string.unableToSendMessage, Toast.LENGTH_SHORT).show();
                                    if (pd != null) {
                                        pd.dismiss();
                                    }
                                }
                            } catch (Exception e) {

                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void result) {
                            if (pd!=null) {
                                pd.dismiss();
                            }
                            cleanUp(finalSelected,allSelected);
                        }
                    };
                    task.execute((Void[])null);
                } else {
                    Toast.makeText(this,R.string.invalidParametersMessage,Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this,R.string.pleaseConnectMessage,Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,R.string.noEntriesSelectedText,Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteSelectedEntries(){
        String selected="";
        Iterator<CheckBox> iterator = checkboxes.iterator();
        while(iterator.hasNext()) {
            CheckBox cb = iterator.next();
            if(cb.isChecked()){
                selected+=Integer.toString(cb.getId())+",";
            }
        }
        if(!selected.equals("")){
            final String finalSelected = selected;
            AlertDialog.Builder logoutDialog = new AlertDialog.Builder(this);
            logoutDialog.setTitle(R.string.deleteAlertTitle);
            logoutDialog.setMessage(R.string.deleteAlertString);
            logoutDialog.setNegativeButton(R.string.cancelButtonText,null);
            logoutDialog.setPositiveButton(R.string.okButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    doDelete(finalSelected);
                }
            });
            logoutDialog.create();
            logoutDialog.show();
        } else {
            Toast.makeText(this,R.string.noEntriesSelectedText,Toast.LENGTH_SHORT).show();
        }
    }

    public void doDelete(String selected){
        agroHelper.deleteLogEntries(selected);
        fillTable();
    }

    public void cleanUp(String selected, boolean allSelected){
        agroHelper.deleteLogEntries(selected);
        if(allSelected){
            final Context context = this;
            Intent i = new Intent(context, mainMenu.class);
            i.putExtra("userId",userId);
            i.putExtra("userRole",userRole);
            startActivity(i);
            finish();
        } else {
            fillTable();
        }
    }

    public void fillTable(){
        checkboxes = new ArrayList<>();
        TableLayout logTable = (TableLayout) findViewById(R.id.logTable);
        logTable.removeAllViews();

        ArrayList<oLog> filteredLog = agroHelper.log;
        //TODO: apply filters

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

    @Override
    public void processFinish(String output){

    }

    @Override
    protected void onDestroy() {
        if (pd!=null) {
            pd.dismiss();
        }
        super.onDestroy();
    }
}
