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
    public int activityId;
    public String activityTitle;
    public String activityMeasurementUnits;

    public Date activityDate;

    boolean edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_activity);

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");
        fieldId = getIntent().getExtras().getInt("field");
        plotN = getIntent().getExtras().getInt("plot");
        activityId = getIntent().getExtras().getInt("activity");
        activityTitle = getIntent().getExtras().getString("title");
        activityMeasurementUnits = getIntent().getExtras().getString("units");
        edit = getIntent().getExtras().getBoolean("edit");

        TextView tt = (TextView)findViewById(R.id.fieldPlotText);
        tt.setText(activityTitle);

        TextView tv = (TextView)findViewById(R.id.enterValueText);
        tv.setText(tv.getText()+" ("+activityMeasurementUnits+")");

        Button cb = (Button)findViewById(R.id.dateButton);
        activityDate = new Date();
        cb.setText(dateToString(activityDate));
        cb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                displayDatePicker();
            }
        });

        if(!edit){
            Button ob = (Button)findViewById(R.id.okButton);
            ob.setVisibility(View.INVISIBLE);

            Button db = (Button)findViewById(R.id.dateButton);
            db.setText(getIntent().getExtras().getString("date"));
            db.setEnabled(false);

            EditText av = (EditText)findViewById(R.id.activityValue);
            av.setText(Float.toString(getIntent().getExtras().getFloat("activityValue")));
            av.setEnabled(false);

            EditText ac = (EditText)findViewById(R.id.activityComments);
            ac.setText(getIntent().getExtras().getString("activityComments"));
            ac.setEnabled(false);
        }
    }

    @Override public void onBackPressed(){
        final Context context = this;
        if(edit) {
            Intent i = new Intent(context, chooser.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("task", task);
            i.putExtra("field", fieldId);
            i.putExtra("plot", plotN);
            i.putExtra("newActivity", false);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(context, manageData.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            startActivity(i);
            finish();
        }
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

            EditText comments = (EditText)findViewById(R.id.activityComments);
            String commentsText = String.valueOf(comments.getText());

            Intent i = new Intent(this, chooser.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("task", task);
            i.putExtra("field", fieldId);
            i.putExtra("plot", plotN);
            i.putExtra("newActivity",true);
            i.putExtra("activity",activityId);
            i.putExtra("activityDate",dateToString(activityDate));
            i.putExtra("activityValue",valueNumber);
            i.putExtra("activityComments",commentsText);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, R.string.enterValidNumberText, Toast.LENGTH_SHORT).show();
            value.requestFocus();
        }
    }
}
