package ojovoz.agroecoresearch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
    CharSequence categoriesArray[];
    public String measurementCategory="";

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
        categories.add(getString(R.string.otherListTest));
        categoriesArray=categories.toArray(new CharSequence[categories.size()]);

        TextView tt = (TextView)findViewById(R.id.fieldPlotText);
        tt.setText(measurementTitle);

        EditText tOther = (EditText)findViewById(R.id.measurementOtherTextValue);
        tOther.setVisibility(View.GONE);

        if(type==1 && !measurementUnits.equals("date")){
            Button cb = (Button)findViewById(R.id.measurementCategory);
            cb.setVisibility(View.GONE);
            EditText et = (EditText)findViewById(R.id.measurementUnits);
            et.setText(measurementUnits);
        } else if(type==0 && !measurementUnits.equals("date")){
            TextView vt = (TextView)findViewById(R.id.enterValueText);
            vt.setVisibility(View.GONE);
            EditText ve = (EditText)findViewById(R.id.measurementValue);
            ve.setVisibility(View.GONE);
            TextView ut = (TextView)findViewById(R.id.enterUnitsText);
            ut.setVisibility(View.GONE);
            EditText ue = (EditText)findViewById(R.id.measurementUnits);
            ue.setVisibility(View.GONE);

            Button cb = (Button)findViewById(R.id.measurementCategory);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.measurementCategory:
                            showMeasurementCategories();
                            break;
                        default:
                            break;
                    }
                }
            });
        } else if(measurementUnits.equals("date")){
            Button cb = (Button)findViewById(R.id.measurementCategory);
            cb.setVisibility(View.GONE);
            TextView vt = (TextView)findViewById(R.id.enterValueText);
            vt.setVisibility(View.GONE);
            EditText ve = (EditText)findViewById(R.id.measurementValue);
            ve.setVisibility(View.GONE);
            TextView ut = (TextView)findViewById(R.id.enterUnitsText);
            ut.setVisibility(View.GONE);
            EditText ue = (EditText)findViewById(R.id.measurementUnits);
            ue.setVisibility(View.GONE);
        }

        if(update.equals("measurement")){
            logId = getIntent().getExtras().getInt("logId");

            EditText se = (EditText)findViewById(R.id.sampleNumber);
            se.setText(String.valueOf(getIntent().getExtras().getInt("sample")));

            if(type==1 && !measurementUnits.equals("date")){
                EditText ve = (EditText)findViewById(R.id.measurementValue);
                ve.setText(String.valueOf(getIntent().getExtras().getFloat("measurementValue")));
            } else if (type==0 && !measurementUnits.equals("date")){
                Button cb = (Button)findViewById(R.id.measurementCategory);
                measurementCategory = getIntent().getExtras().getString("measurementCategory");
                if(!categories.contains(measurementCategory)){
                    EditText tOtherEdit = (EditText)findViewById(R.id.measurementOtherTextValue);
                    tOtherEdit.setVisibility(View.VISIBLE);
                    tOtherEdit.setText(measurementCategory);
                    cb.setText(getString(R.string.otherButtonText));
                } else {
                    cb.setText(getIntent().getExtras().getString("measurementCategory"));
                }
            }

            Button db = (Button)findViewById(R.id.dateButton);
            db.setText(getIntent().getExtras().getString("date"));
            measurementDate = stringToDate(getIntent().getExtras().getString("date"));

            EditText mc = (EditText)findViewById(R.id.measurementComments);
            mc.setText(getIntent().getExtras().getString("measurementComments"));

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

    public void showMeasurementCategories(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.cancelButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final ListAdapter adapter = new ArrayAdapter<>(this,R.layout.checked_list_template,categoriesArray);
        builder.setSingleChoiceItems(adapter,-1,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i>=0) {
                    Button fieldListView = (Button) findViewById(R.id.measurementCategory);
                    measurementCategory=(String)categoriesArray[i];
                    if(measurementCategory.equals(getString(R.string.otherListTest))){
                        fieldListView.setText(R.string.otherButtonText);
                        EditText tOther = (EditText)findViewById(R.id.measurementOtherTextValue);
                        tOther.setVisibility(View.VISIBLE);
                    } else {
                        fieldListView.setText(categoriesArray[i]);
                        EditText tOther = (EditText)findViewById(R.id.measurementOtherTextValue);
                        tOther.setVisibility(View.GONE);
                    }

                }
                dialogInterface.dismiss();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
        float valueNumber = 0.0f;
        String units="";
        int sampleN=0;
        boolean bProceed = true;
        EditText sample = (EditText)findViewById(R.id.sampleNumber);
        String sampleValue = String.valueOf(sample.getText());
        if(isNumeric(sampleValue) || sampleValue.isEmpty()){
            if(!sampleValue.isEmpty()){
                sampleN = Integer.parseInt(sampleValue);
            }
            if(type==1 && !measurementUnits.equals("date")){
                EditText value = (EditText)findViewById(R.id.measurementValue);
                String valueText = String.valueOf(value.getText());
                if(isNumeric(valueText)){
                    valueNumber = Float.parseFloat(valueText);
                    if(valueNumber<min || valueNumber>max){
                        Toast.makeText(this, R.string.valueOutOfRangeText, Toast.LENGTH_SHORT).show();
                        value.requestFocus();
                        bProceed = false;
                    } else {
                        EditText unitsText = (EditText)findViewById(R.id.measurementUnits);
                        units = String.valueOf(unitsText.getText());
                        if(!units.isEmpty()){
                            units = units.replaceAll(";"," ");
                            units = units.replaceAll("\\|"," ");
                        }
                    }
                } else {
                    Toast.makeText(this, R.string.enterValidNumberText, Toast.LENGTH_SHORT).show();
                    value.requestFocus();
                    bProceed=false;
                }
            } else if(type==0 && !measurementUnits.equals("date")){
                if(measurementCategory.equals("")){
                    Toast.makeText(this, R.string.enterValidCategoryText, Toast.LENGTH_SHORT).show();
                    bProceed=false;
                }
            }

            if(measurementCategory.equals(getString(R.string.otherListTest))){
                EditText tOther = (EditText)findViewById(R.id.measurementOtherTextValue);
                measurementCategory = String.valueOf(tOther.getText());
                if(!measurementCategory.isEmpty()){
                    measurementCategory = measurementCategory.replaceAll(";"," ");
                    measurementCategory = measurementCategory.replaceAll("\\|"," ");
                }
            }

            EditText comments = (EditText)findViewById(R.id.measurementComments);
            String commentsText = String.valueOf(comments.getText());

            if(!commentsText.isEmpty()){
                commentsText = commentsText.replaceAll(";"," ");
                commentsText = commentsText.replaceAll("\\|"," ");
            }

            if(update.equals("") && bProceed) {
                Intent i = new Intent(this, chooser.class);
                i.putExtra("userId", userId);
                i.putExtra("userRole", userRole);
                i.putExtra("task", task);
                i.putExtra("field", fieldId);
                i.putExtra("plot", plotN);
                i.putExtra("newMeasurement", true);
                i.putExtra("measurement", measurementId);
                i.putExtra("measurementSample",sampleN);
                i.putExtra("measurementDate", dateToString(measurementDate));
                i.putExtra("measurementValue", valueNumber);
                i.putExtra("measurementUnits", units);
                i.putExtra("measurementCategory", measurementCategory);
                i.putExtra("measurementComments", commentsText);
                startActivity(i);
                finish();
            } else if(bProceed){
                Intent i = new Intent(this, manageData.class);
                i.putExtra("userId", userId);
                i.putExtra("userRole", userRole);
                i.putExtra("task", task);
                i.putExtra("logId",logId);
                i.putExtra("update", "measurement");
                i.putExtra("measurement", measurementId);
                i.putExtra("measurementSample",sampleN);
                i.putExtra("measurementDate", dateToString(measurementDate));
                i.putExtra("measurementValue", valueNumber);
                i.putExtra("measurementUnits", units);
                i.putExtra("measurementCategory", measurementCategory);
                i.putExtra("measurementComments", commentsText);
                startActivity(i);
                finish();
            }
        } else {
            Toast.makeText(this, R.string.enterValidNumberText, Toast.LENGTH_SHORT).show();
            sample.requestFocus();
        }
    }
}
