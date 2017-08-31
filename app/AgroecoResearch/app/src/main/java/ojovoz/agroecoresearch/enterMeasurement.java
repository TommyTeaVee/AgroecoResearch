package ojovoz.agroecoresearch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
    public String plots;
    public int measurementId;
    public boolean hasSamples;
    public String measurementTitle;
    public String shortTitle;
    public String measurementChosenCategory;
    public String measurementUnits;
    public int type;
    public float min;
    public float max;
    ArrayList<CharSequence> categories;
    CharSequence categoriesArray[];
    public String measurementCategory = "";

    public Date measurementDate;

    public String update;

    public ArrayList<oSampleHelper> samples;
    public int maxSampleNumber = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_measurement);

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");
        fieldId = getIntent().getExtras().getInt("field");
        plots = getIntent().getExtras().getString("plots");
        measurementId = getIntent().getExtras().getInt("measurement");
        hasSamples = getIntent().getExtras().getBoolean("hasSamples");
        measurementTitle = getIntent().getExtras().getString("title");
        shortTitle = getIntent().getExtras().getString("shortTitle");
        measurementChosenCategory = getIntent().getExtras().getString("measurementChosenCategory");
        measurementUnits = getIntent().getExtras().getString("units");
        type = getIntent().getExtras().getInt("type");
        min = getIntent().getExtras().getFloat("min");
        max = getIntent().getExtras().getFloat("max");
        String categoriesString = getIntent().getExtras().getString("categories");
        update = getIntent().getExtras().getString("update");

        categories = new ArrayList<>();
        String[] categoriesParts = categoriesString.split(",");
        for (int i = 0; i < categoriesParts.length; i++) {
            categories.add(categoriesParts[i]);
        }
        categories.add(getString(R.string.otherListTest));
        categoriesArray = categories.toArray(new CharSequence[categories.size()]);

        TextView tt = (TextView) findViewById(R.id.fieldPlotText);
        tt.setText(measurementTitle);

        EditText tOther = (EditText) findViewById(R.id.measurementOtherTextValue);
        tOther.setVisibility(View.GONE);

        if (hasSamples) {
            TextView vt = (TextView) findViewById(R.id.enterValueText);
            vt.setVisibility(View.GONE);
            EditText ve = (EditText) findViewById(R.id.measurementValue);
            ve.setVisibility(View.GONE);
            TextView ut = (TextView) findViewById(R.id.enterUnitsText);
            ut.setVisibility(View.GONE);
            EditText ue = (EditText) findViewById(R.id.measurementUnits);
            ue.setVisibility(View.GONE);
            Button cb = (Button) findViewById(R.id.measurementCategory);
            cb.setVisibility(View.GONE);
        } else {
            TableLayout tl = (TableLayout) findViewById(R.id.samples);
            tl.setVisibility(View.GONE);

            Button b = (Button) findViewById(R.id.addSample);
            b.setVisibility(View.GONE);

            if (type == 1 && !measurementUnits.equals("date")) {
                Button cb = (Button) findViewById(R.id.measurementCategory);
                cb.setVisibility(View.GONE);
                EditText et = (EditText) findViewById(R.id.measurementUnits);
                et.setText(measurementUnits);
            } else if (type == 0 && !measurementUnits.equals("date")) {
                TextView vt = (TextView) findViewById(R.id.enterValueText);
                vt.setVisibility(View.GONE);
                EditText ve = (EditText) findViewById(R.id.measurementValue);
                ve.setVisibility(View.GONE);
                TextView ut = (TextView) findViewById(R.id.enterUnitsText);
                ut.setVisibility(View.GONE);
                EditText ue = (EditText) findViewById(R.id.measurementUnits);
                ue.setVisibility(View.GONE);

                Button cb = (Button) findViewById(R.id.measurementCategory);
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
            } else if (measurementUnits.equals("date")) {
                Button cb = (Button) findViewById(R.id.measurementCategory);
                cb.setVisibility(View.GONE);
                TextView vt = (TextView) findViewById(R.id.enterValueText);
                vt.setVisibility(View.GONE);
                EditText ve = (EditText) findViewById(R.id.measurementValue);
                ve.setVisibility(View.GONE);
                TextView ut = (TextView) findViewById(R.id.enterUnitsText);
                ut.setVisibility(View.GONE);
                EditText ue = (EditText) findViewById(R.id.measurementUnits);
                ue.setVisibility(View.GONE);
            }
        }

        if (update.equals("measurement")) {
            logId = getIntent().getExtras().getInt("logId");

            if (type == 1 && !measurementUnits.equals("date")) {
                EditText ve = (EditText) findViewById(R.id.measurementValue);
                ve.setText(String.valueOf(getIntent().getExtras().getFloat("measurementValue")));
            } else if (type == 0 && !measurementUnits.equals("date")) {
                Button cb = (Button) findViewById(R.id.measurementCategory);
                measurementCategory = getIntent().getExtras().getString("measurementCategory");
                if (!categories.contains(measurementCategory)) {
                    EditText tOtherEdit = (EditText) findViewById(R.id.measurementOtherTextValue);
                    tOtherEdit.setVisibility(View.VISIBLE);
                    tOtherEdit.setText(measurementCategory);
                    cb.setText(getString(R.string.otherButtonText));
                } else {
                    cb.setText(getIntent().getExtras().getString("measurementCategory"));
                }
            }

            Button db = (Button) findViewById(R.id.dateButton);
            db.setText(getIntent().getExtras().getString("date"));
            measurementDate = stringToDate(getIntent().getExtras().getString("date"));

            EditText mc = (EditText) findViewById(R.id.measurementComments);
            mc.setText(getIntent().getExtras().getString("measurementComments"));

            Button rb = (Button) findViewById(R.id.okButton);
            rb.setText(R.string.editButtonText);
        } else {
            measurementDate = new Date();

            if (hasSamples) {
                initializeSampleTable();
                Button as = (Button) findViewById(R.id.addSample);
                as.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addSample();
                    }
                });

                TableLayout st = (TableLayout)findViewById(R.id.samplesTable);
                st.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        findViewById(R.id.childScrollView).getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });

                ScrollView svp = (ScrollView) findViewById(R.id.parentScrollView);
                svp.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        findViewById(R.id.childScrollView).getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                });

            }

        }

        Button cb = (Button) findViewById(R.id.dateButton);
        cb.setText(dateToString(measurementDate));
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatePicker();
            }
        });
    }

    @Override public void onBackPressed () {
        final Context context = this;
        if (update.equals("")) {
            Intent i = new Intent(context, chooseFieldPlot.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("task", task);
            i.putExtra("field", fieldId);
            i.putExtra("plots", plots);
            i.putExtra("newMeasurement", false);
            i.putExtra("measurement", measurementId);
            i.putExtra("title", shortTitle);
            i.putExtra("measurementChosenCategory", measurementChosenCategory);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(context, manageData.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("update", "");
            startActivity(i);
            finish();
        }
    }

    public void initializeSampleTable() {

        samples = new ArrayList<>();
        oSampleHelper sh = new oSampleHelper();
        sh.sampleNumber = maxSampleNumber;
        sh.value = "";
        samples.add(sh);

        fillSampleTable();

    }

    public void fillSampleTable() {

        int n = 0;
        TableLayout samplesTable = (TableLayout) findViewById(R.id.samplesTable);
        samplesTable.removeAllViews();
        Iterator<oSampleHelper> iterator = samples.iterator();
        while (iterator.hasNext()) {
            oSampleHelper sh = iterator.next();

            final TableRow trow = new TableRow(enterMeasurement.this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
            lp.setMargins(10, 10, 0, 10);
            if (n % 2 == 0) {
                trow.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray));
            } else {
                trow.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
            }

            CheckBox cb = new CheckBox(enterMeasurement.this);
            cb.setButtonDrawable(R.drawable.delete_checkbox);
            cb.setId(n);
            cb.setPadding(10, 10, 10, 10);
            cb.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    findViewById(R.id.childScrollView).getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
            trow.addView(cb, lp);

            EditText sn = new EditText(enterMeasurement.this);
            sn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f);
            sn.setText(String.valueOf(sh.sampleNumber));
            sn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            sn.setInputType(InputType.TYPE_CLASS_NUMBER);
            sn.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            sn.setPadding(0, 10, 0, 10);
            sn.setId(n);
            sn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        updateSampleNumber(view);
                    }
                }
            });
            sn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    findViewById(R.id.childScrollView).getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
            trow.addView(sn, lp);

            if (type == 0) {
                //chooseValueSampleTable

            } else {
                EditText sv = new EditText(enterMeasurement.this);
                sv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f);
                sv.setText(sh.value);
                sv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                sv.setPadding(0, 10, 0, 10);
                sv.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                sv.setImeOptions(EditorInfo.IME_ACTION_DONE);
                sv.setId(n);
                sv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (!b) {
                            updateSampleValue(view);
                        }
                    }
                });
                sv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        findViewById(R.id.childScrollView).getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });
                trow.addView(sv, lp);
            }

            trow.setGravity(Gravity.CENTER_VERTICAL);
            samplesTable.addView(trow, lp);
            n++;
        }
    }

    public void updateSampleValue(View view) {
        EditText e = (EditText) view;
        int id = e.getId();
        String value = String.valueOf(e.getText());

        oSampleHelper sh = samples.get(id);
        sh.value = value;
    }

    public void updateSampleNumber(View view) {
        EditText e = (EditText) view;
        int id = e.getId();
        int number = Integer.valueOf(String.valueOf(e.getText()));

        oSampleHelper sh = samples.get(id);
        sh.sampleNumber = number;
    }

    public void addSample() {
        maxSampleNumber++;
        oSampleHelper sh = new oSampleHelper();
        sh.sampleNumber = maxSampleNumber;
        sh.value = "";
        samples.add(sh);

        fillSampleTable();

        final ScrollView sv = (ScrollView)findViewById(R.id.childScrollView);
        sv.postDelayed(new Runnable() { @Override public void run() { sv.fullScroll(View.FOCUS_DOWN); } }, 500);
    }

    public void showMeasurementCategories() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.cancelButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final ListAdapter adapter = new ArrayAdapter<>(this, R.layout.checked_list_template, categoriesArray);
        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i >= 0) {
                    Button fieldListView = (Button) findViewById(R.id.measurementCategory);
                    measurementCategory = (String) categoriesArray[i];
                    if (measurementCategory.equals(getString(R.string.otherListTest))) {
                        fieldListView.setText(R.string.otherButtonText);
                        EditText tOther = (EditText) findViewById(R.id.measurementOtherTextValue);
                        tOther.setVisibility(View.VISIBLE);
                    } else {
                        fieldListView.setText(categoriesArray[i]);
                        EditText tOther = (EditText) findViewById(R.id.measurementOtherTextValue);
                        tOther.setVisibility(View.GONE);
                    }

                }
                dialogInterface.dismiss();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void displayDatePicker() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_datepicker);

        DatePicker dp = (DatePicker) dialog.findViewById(R.id.datePicker);
        Calendar calActivity = Calendar.getInstance();
        calActivity.setTime(measurementDate);
        dp.init(calActivity.get(Calendar.YEAR), calActivity.get(Calendar.MONTH), calActivity.get(Calendar.DAY_OF_MONTH), null);

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
                int year = dp.getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                measurementDate = calendar.getTime();

                Button cb = (Button) findViewById(R.id.dateButton);
                cb.setText(dateToString(measurementDate));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public Date stringToDate(String d) {
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

    public String dateToString(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(d);
    }

    public void registerMeasurement(View v) {
        float valueNumber = 0.0f;
        String units = "";
        boolean bProceed = true;

        if (type == 1 && !measurementUnits.equals("date")) {
            EditText value = (EditText) findViewById(R.id.measurementValue);
            String valueText = String.valueOf(value.getText());
            if (isNumeric(valueText)) {
                valueNumber = Float.parseFloat(valueText);
                if (valueNumber < min || valueNumber > max) {
                    Toast.makeText(this, R.string.valueOutOfRangeText, Toast.LENGTH_SHORT).show();
                    value.requestFocus();
                    bProceed = false;
                } else {
                    EditText unitsText = (EditText) findViewById(R.id.measurementUnits);
                    units = String.valueOf(unitsText.getText());
                    if (!units.isEmpty()) {
                        units = units.replaceAll(";", " ");
                        units = units.replaceAll("\\|", " ");
                    }
                }
            } else {
                Toast.makeText(this, R.string.enterValidNumberText, Toast.LENGTH_SHORT).show();
                value.requestFocus();
                bProceed = false;
            }
        } else if (type == 0 && !measurementUnits.equals("date")) {
            if (measurementCategory.equals("")) {
                Toast.makeText(this, R.string.enterValidCategoryText, Toast.LENGTH_SHORT).show();
                bProceed = false;
            }
        }

        if (measurementCategory.equals(getString(R.string.otherListTest))) {
            EditText tOther = (EditText) findViewById(R.id.measurementOtherTextValue);
            measurementCategory = String.valueOf(tOther.getText());
            if (!measurementCategory.isEmpty()) {
                measurementCategory = measurementCategory.replaceAll(";", " ");
                measurementCategory = measurementCategory.replaceAll("\\|", " ");
            }
        }

        EditText comments = (EditText) findViewById(R.id.measurementComments);
        String commentsText = String.valueOf(comments.getText());

        if (!commentsText.isEmpty()) {
            commentsText = commentsText.replaceAll(";", " ");
            commentsText = commentsText.replaceAll("\\|", " ");
        }

        if (update.equals("") && bProceed) {
            Intent i = new Intent(this, chooseFieldPlot.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("task", task);
            i.putExtra("title", shortTitle);
            i.putExtra("measurementChosenCategory", measurementChosenCategory);
            i.putExtra("field", fieldId);
            i.putExtra("plots", plots);
            i.putExtra("newMeasurement", true);
            i.putExtra("measurement", measurementId);
            i.putExtra("measurementDate", dateToString(measurementDate));
            i.putExtra("measurementValue", valueNumber);
            i.putExtra("measurementUnits", units);
            i.putExtra("measurementCategory", measurementCategory);
            i.putExtra("measurementComments", commentsText);
            startActivity(i);
            finish();
        } else if (bProceed) {
            Intent i = new Intent(this, manageData.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("task", task);
            i.putExtra("logId", logId);
            i.putExtra("update", "measurement");
            i.putExtra("measurement", measurementId);
            i.putExtra("measurementDate", dateToString(measurementDate));
            i.putExtra("measurementValue", valueNumber);
            i.putExtra("measurementUnits", units);
            i.putExtra("measurementCategory", measurementCategory);
            i.putExtra("measurementComments", commentsText);
            startActivity(i);
            finish();
        }
    }

}
