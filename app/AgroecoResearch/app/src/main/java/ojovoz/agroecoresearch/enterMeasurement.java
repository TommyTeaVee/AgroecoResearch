package ojovoz.agroecoresearch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Eugenio on 21/04/2017.
 */
public class enterMeasurement extends AppCompatActivity {

    public int userId;
    public int userRole;
    public String task;
    public int logId;
    public int fieldId;
    public int plotN;
    public int measurementId;
    public String measurementTitle;
    public String measurementUnits;
    public int type;
    public float min;
    public float max;
    ArrayList<CharSequence> categories;

    public Date measurementDate;

    public String update;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_measurement);

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");
        fieldId = getIntent().getExtras().getInt("field");
        plotN = getIntent().getExtras().getInt("plot");
        measurementId = getIntent().getExtras().getInt("measurement");
        measurementTitle = getIntent().getExtras().getString("title");
        measurementUnits = getIntent().getExtras().getString("units");
        type = getIntent().getExtras().getInt("type");
        min = getIntent().getExtras().getFloat("min");
        max = getIntent().getExtras().getFloat("max");
        String categoriesString = getIntent().getExtras().getString("categories");
        update = getIntent().getExtras().getString("update");

        categories = new ArrayList<>();
        String[] categoriesParts = categoriesString.split(",");
        for(int i=0; i<categoriesParts.length; i++){
            categories.add(categoriesParts[i]);
        }

        TextView tt = (TextView)findViewById(R.id.fieldPlotText);
        tt.setText(measurementTitle);

        if(type==1 && !measurementUnits.equals("date")){
            Button cb = (Button)findViewById(R.id.measurementCategory);
            cb.setVisibility(View.GONE);
            TextView tv = (TextView)findViewById(R.id.enterValueText);
            tv.setText(tv.getText()+" ("+measurementUnits+")");
        } else if(type==0 && !measurementUnits.equals("date")){
            TextView vt = (TextView)findViewById(R.id.enterValueText);
            vt.setVisibility(View.GONE);
            EditText ve = (EditText)findViewById(R.id.measurementValue);
            ve.setVisibility(View.GONE);
        } else if(measurementUnits.equals("date")){
            Button cb = (Button)findViewById(R.id.measurementCategory);
            cb.setVisibility(View.GONE);
            TextView vt = (TextView)findViewById(R.id.enterValueText);
            vt.setVisibility(View.GONE);
            EditText ve = (EditText)findViewById(R.id.measurementValue);
            ve.setVisibility(View.GONE);
        }

        if(update.equals("measurement")){
            logId = getIntent().getExtras().getInt("logId");

            Button db = (Button)findViewById(R.id.dateButton);
            db.setText(getIntent().getExtras().getString("date"));
            measurementDate = stringToDate(getIntent().getExtras().getString("date"));

            Button rb = (Button)findViewById(R.id.okButton);
            rb.setText(R.string.editButtonText);
        } else {
            measurementDate = new Date();
        }

        Button cb = (Button)findViewById(R.id.dateButton);
        cb.setText(dateToString(measurementDate));
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
            Intent i = new Intent(context, chooser.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("task", task);
            i.putExtra("field", fieldId);
            i.putExtra("plot", plotN);
            i.putExtra("newMeasurement", false);
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

    public void displayDatePicker(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_datepicker);

        DatePicker dp = (DatePicker) dialog.findViewById(R.id.datePicker);
        Calendar calActivity = Calendar.getInstance();
        calActivity.setTime(measurementDate);
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

                measurementDate = calendar.getTime();

                Button cb = (Button)findViewById(R.id.dateButton);
                cb.setText(dateToString(measurementDate));
                dialog.dismiss();
            }
        });
        dialog.show();
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

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public String dateToString(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(d);
    }

    public void registerMeasurement(View v){

    }
}
