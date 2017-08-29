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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
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
    public int logId;
    public int fieldId;
    public String plots;
    public int activityId;
    public String activityTitle;
    public String shortTitle;
    public String activityMeasurementUnits;

    public Date activityDate;

    public String update;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_activity);

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");
        fieldId = getIntent().getExtras().getInt("field");
        plots = getIntent().getExtras().getString("plots");
        activityId = getIntent().getExtras().getInt("activity");
        activityTitle = getIntent().getExtras().getString("title");
        shortTitle = getIntent().getExtras().getString("shortTitle");
        activityMeasurementUnits = getIntent().getExtras().getString("units");
        update = getIntent().getExtras().getString("update");

        TextView tt = (TextView)findViewById(R.id.fieldPlotText);
        tt.setText(activityTitle);

        EditText et = (EditText)findViewById(R.id.activityUnits);
        et.setText(activityMeasurementUnits);

        if(update.equals("activity")){
            logId = getIntent().getExtras().getInt("logId");

            Button ob = (Button)findViewById(R.id.okButton);
            ob.setText(R.string.editButtonText);

            Button db = (Button)findViewById(R.id.dateButton);
            db.setText(getIntent().getExtras().getString("date"));
            activityDate = stringToDate(getIntent().getExtras().getString("date"));

            EditText av = (EditText)findViewById(R.id.activityValue);
            av.setText(Float.toString(getIntent().getExtras().getFloat("activityValue")));

            EditText ac = (EditText)findViewById(R.id.activityComments);
            ac.setText(getIntent().getExtras().getString("activityComments"));
        } else {
            activityDate = new Date();
        }

        Button cb = (Button)findViewById(R.id.dateButton);
        cb.setText(dateToString(activityDate));
        cb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                displayDatePicker();
            }
        });
    }

    @Override public void onBackPressed(){
        final Context context = this;
        if(update.equals("")) {
            Intent i = new Intent(context, chooseFieldPlot.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("task", task);
            i.putExtra("field", fieldId);
            i.putExtra("plots", plots);
            i.putExtra("newActivity", false);
            i.putExtra("activity",activityId);
            i.putExtra("title",shortTitle);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(context, manageData.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("update","");
            startActivity(i);
            finish();
        }
    }

    public Date stringToDate(String d){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        try {
            date = sdf.parse(d);
        } catch (ParseException e) {

        }
        return date;
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

                activityDate = calendar.getTime();

                Button cb = (Button)findViewById(R.id.dateButton);
                cb.setText(dateToString(activityDate));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public String dateToString(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(d);
    }

    public void registerActivity(View v){
        EditText value = (EditText)findViewById(R.id.activityValue);
        String valueText = String.valueOf(value.getText());
        if(isNumeric(valueText)){
            float valueNumber = Float.parseFloat(valueText);

            EditText units = (EditText)findViewById(R.id.activityUnits);
            String unitsText = String.valueOf(units.getText());

            if(!unitsText.isEmpty()){
                unitsText = unitsText.replaceAll(";"," ");
                unitsText = unitsText.replaceAll("\\|"," ");
            }

            EditText laborers = (EditText)findViewById(R.id.activityLaborers);
            String laborersText = String.valueOf(laborers.getText());
            if(isNumeric(laborersText)) {
                int laborersNumber = Integer.parseInt(laborersText);

                EditText cost = (EditText)findViewById(R.id.activityCost);
                String costText = String.valueOf(cost.getText());

                if(isNumeric(costText)) {
                    float costValue = Float.parseFloat(costText);

                    EditText comments = (EditText) findViewById(R.id.activityComments);
                    String commentsText = String.valueOf(comments.getText());

                    if (!commentsText.isEmpty()) {
                        commentsText = commentsText.replaceAll(";", " ");
                        commentsText = commentsText.replaceAll("\\|", " ");
                    }

                    if (update.equals("")) {
                        Intent i = new Intent(this, chooseFieldPlot.class);
                        i.putExtra("userId", userId);
                        i.putExtra("userRole", userRole);
                        i.putExtra("task", task);
                        i.putExtra("title", shortTitle);
                        i.putExtra("field", fieldId);
                        i.putExtra("plots", plots);
                        i.putExtra("newActivity", true);
                        i.putExtra("activity", activityId);
                        i.putExtra("activityDate", dateToString(activityDate));
                        i.putExtra("activityValue", valueNumber);
                        i.putExtra("activityUnits", unitsText);
                        i.putExtra("activityLaborers", laborersNumber);
                        i.putExtra("activityCost", costValue);
                        i.putExtra("activityComments", commentsText);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(this, manageData.class);
                        i.putExtra("userId", userId);
                        i.putExtra("userRole", userRole);
                        i.putExtra("task", task);
                        i.putExtra("logId", logId);
                        i.putExtra("update", "activity");
                        i.putExtra("activity", activityId);
                        i.putExtra("activityDate", dateToString(activityDate));
                        i.putExtra("activityValue", valueNumber);
                        i.putExtra("activityUnits", unitsText);
                        i.putExtra("activityLaborers", laborersNumber);
                        i.putExtra("activityCost", costValue);
                        i.putExtra("activityComments", commentsText);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Toast.makeText(this, R.string.enterValidNumberText, Toast.LENGTH_SHORT).show();
                    cost.requestFocus();

                }
            } else {
                Toast.makeText(this, R.string.enterValidNumberText, Toast.LENGTH_SHORT).show();
                laborers.requestFocus();
            }
        } else {
            Toast.makeText(this, R.string.enterValidNumberText, Toast.LENGTH_SHORT).show();
            value.requestFocus();
        }
    }
}
