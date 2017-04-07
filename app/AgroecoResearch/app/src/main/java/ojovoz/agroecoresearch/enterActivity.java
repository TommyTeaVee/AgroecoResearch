package ojovoz.agroecoresearch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Eugenio on 06/04/2017.
 */
public class enterActivity extends AppCompatActivity {

    public int userId;
    public int userRole;
    public String task;
    public int fieldId;
    public int plotN;

    public oField field;
    public oPlot plot;
    public oCrop primaryCrop = new oCrop();
    public oActivity activity = new oActivity();

    public agroecoHelper agroHelper;

    public Date activityDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_activity);

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");
        fieldId = getIntent().getExtras().getInt("field");
        plotN = getIntent().getExtras().getInt("plot");
        int activityId = getIntent().getExtras().getInt("activity");

        agroHelper = new agroecoHelper(this, "crops,fields,treatments,activities,log");

        field = agroHelper.getFieldFromId(fieldId);
        activity = agroHelper.getActivityFromId(activityId);

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

        TextView tt = (TextView)findViewById(R.id.fieldPlotText);
        if(plotN>=0) {
            tt.setText("Field: " + field.fieldName + " R" + Integer.toString(field.fieldReplicationN) + "\nPlot " + Integer.toString(plotN + 1) + ": " + primaryCrop.cropName + " (" + primaryCrop.cropVariety + ")" + treatmentsTitle + "\nActivity: " + activity.activityName);
        } else {
            tt.setText("Field: " + field.fieldName + " R" + Integer.toString(field.fieldReplicationN) + "\nActivity: " + activity.activityName);
        }

        TextView tv = (TextView)findViewById(R.id.enterValueText);
        tv.setText(tv.getText()+" ("+activity.activityMeasurementUnits+")");

        Button cb = (Button)findViewById(R.id.dateButton);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        activityDate = new Date();
        cb.setText(sdf.format(activityDate));
        cb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                displayDatePicker();
            }
        });
    }

    @Override public void onBackPressed(){
        final Context context = this;
        Intent i = new Intent(context, chooser.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        i.putExtra("task",task);
        i.putExtra("field", fieldId);
        i.putExtra("plot", plotN);
        startActivity(i);
        finish();
    }

    public void displayDatePicker(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_datepicker);

        DatePicker dp = (DatePicker) dialog.findViewById(R.id.datePicker);
        Calendar calActivity = Calendar.getInstance();
        calActivity.setTime(activityDate);
        dp.init(calActivity.get(Calendar.YEAR), calActivity.get(Calendar.MONTH), calActivity.get(Calendar.DAY_OF_MONTH),null);

        Calendar calMax = Calendar.getInstance();
        calMax.setTime(new Date());

        dp.setMaxDate(calMax.getTimeInMillis());

        Button dialogButton = (Button) dialog.findViewById(R.id.okButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker dp = (DatePicker) dialog.findViewById(R.id.datePicker);
                int day = dp.getDayOfMonth();
                int month = dp.getMonth();
                int year =  dp.getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setTimeZone(TimeZone.getDefault());
                activityDate = calendar.getTime();

                Button cb = (Button)findViewById(R.id.dateButton);
                cb.setText(sdf.format(activityDate));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void registerActivity(View v){
        //TODO: get values
        agroHelper.addActivityToLog();
        Intent i = new Intent(this, chooser.class);
        i.putExtra("userId", userId);
        i.putExtra("userRole", userRole);
        i.putExtra("task", task);
        i.putExtra("field", fieldId);
        i.putExtra("plot", plotN);
        startActivity(i);
        finish();
    }
}
