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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Eugenio on 25/04/2017.
 */
public class inputChooser extends AppCompatActivity {

    public int userId;
    public int userRole;
    public String task;

    public int fieldId;
    public int plotN;
    public oField field;

    public boolean plotHasSoilManagement=false;
    public boolean plotHasPestControl=false;

    public agroecoHelper agroHelper;
    public ArrayList<oCrop> crops;
    public ArrayList<oTreatment> treatments;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_chooser);

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");

        agroHelper = new agroecoHelper(this, "crops,fields,treatments,input_log");

        /*
        fieldId = getIntent().getExtras().getInt("field");
        field = agroHelper.getFieldFromId(fieldId);
        plotN = getIntent().getExtras().getInt("plot");
        if(plotN>=0){
            oPlot plot = field.plots.get(plotN);

            if(plot.hasSoilManagement){
                plotHasSoilManagement=true;
            }
            if(plot.hasPestControl){
                plotHasPestControl=true;
            }
        }

        TextView tt = (TextView)findViewById(R.id.tableTitle);
        if(plotN>=0) {
            tt.setText("Plot " + Integer.toString(plotN + 1));
        } else {
            tt.setText("Entire field");
        }

        CharSequence title = getTitle() + " " + task + ": " + field.fieldName + " R" + Integer.toString(field.fieldReplicationN);
        setTitle(title);
        */

        TextView tt = (TextView)findViewById(R.id.tableTitle);
        tt.setText("Choose input");

        setTitle("Register input");

        if(getIntent().getExtras().getBoolean("newCropInput")){
            agroHelper.addCropToInputLog(fieldId, plotN, userId, getIntent().getExtras().getInt("crop"),
                    getIntent().getExtras().getString("cropInputDate"), getIntent().getExtras().getInt("cropInputAge"),
                    getIntent().getExtras().getString("cropInputOrigin"), getIntent().getExtras().getFloat("cropInputQuantity"),
                    getIntent().getExtras().getFloat("cropInputCost"), getIntent().getExtras().getString("cropInputComments"));
        } else if(getIntent().getExtras().getBoolean("newTreatmentInput")){
            agroHelper.addTreatmentToInputLog(fieldId, plotN, userId, getIntent().getExtras().getInt("treatment"),
                    getIntent().getExtras().getString("treatmentInputDate"), getIntent().getExtras().getString("treatmentInputMaterial"),
                    getIntent().getExtras().getFloat("treatmentInputQuantity"), getIntent().getExtras().getString("treatmentInputMethod"),
                    getIntent().getExtras().getFloat("treatmentInputCost"), getIntent().getExtras().getString("treatmentInputComments"));
        }

        fillTable();
    }

    @Override public void onBackPressed(){
        final Context context = this;
        Intent i = new Intent(context, mainMenu.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        i.putExtra("task",task);
        //i.putExtra("field", fieldId);
        startActivity(i);
        finish();
    }

    public void fillTable() {
        TableLayout chooserTable = (TableLayout) findViewById(R.id.chooserTable);
        chooserTable.removeAllViews();

        final TableRow trowHeaderCrops = new TableRow(inputChooser.this);
        TableRow.LayoutParams lpHeaderCrops = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        lpHeaderCrops.setMargins(4, 4, 4, 4);
        trowHeaderCrops.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        TextView tv = new TextView(inputChooser.this);
        tv.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        tv.setText(R.string.cropsTableRowText);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f);
        tv.setPadding(4, 4, 4, 4);
        trowHeaderCrops.addView(tv, lpHeaderCrops);
        trowHeaderCrops.setGravity(Gravity.CENTER_VERTICAL);
        chooserTable.addView(trowHeaderCrops, lpHeaderCrops);

        /*
        if(plotN>=0){
            crops = agroHelper.getPlotCropsFromFieldId(fieldId, plotN);
        } else {
            crops = agroHelper.getCropsFromFieldId(fieldId);
        }
        */
        crops = agroHelper.getAllCrops();
        Iterator<oCrop> iterator = crops.iterator();
        int n = 0;
        while (iterator.hasNext()) {

            oCrop crop = iterator.next();
            String cropName = crop.cropName;

            final TableRow trowCrop = new TableRow(inputChooser.this);
            TableRow.LayoutParams lpCrop = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
            lpCrop.setMargins(4, 4, 4, 4);

            if (n % 2 == 0) {
                trowCrop.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray));
            } else {
                trowCrop.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
            }

            //String nDays = agroHelper.getCropInputDaysAgo(crop.cropId,plotN,fieldId);

            TextView tvCrop = new TextView(inputChooser.this);
            tvCrop.setId(n);
            tvCrop.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tvCrop.setText(cropName);
            tvCrop.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f);
            tvCrop.setPadding(4, 4, 4, 4);
            //if(!nDays.equals("0")) {
                tvCrop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseCrop(v.getId(), v);
                    }

                });
            //}

            trowCrop.addView(tvCrop, lpCrop);
            trowCrop.setGravity(Gravity.CENTER_VERTICAL);
            chooserTable.addView(trowCrop, lpCrop);

            /*
            final TableRow trowBelow = new TableRow(inputChooser.this);
            TableRow.LayoutParams lpBelow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
            lpBelow.setMargins(4,0,4,0);

            if(n%2==0){
                trowBelow.setBackgroundColor(ContextCompat.getColor(this,R.color.lightGray));
            } else {
                trowBelow.setBackgroundColor(ContextCompat.getColor(this,R.color.colorWhite));
            }

            TextView tvBelow = new TextView(inputChooser.this);
            tvBelow.setTextColor(ContextCompat.getColor(this,R.color.colorBlack));

            String daysAgo="";

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
            */

            n++;
        }

        //if(((field.hasSoilManagement || field.hasPestControl) && plotN<0) || ((plotHasSoilManagement || plotHasPestControl) && plotN>=0)) {

            /*
            if (plotN >= 0) {
                treatments = agroHelper.getInputTreatmentsFromPlotFieldId(fieldId, plotN);
            } else {
                treatments = agroHelper.getInputTreatmentsFromFieldId(fieldId);
            }
            */
            treatments = agroHelper.treatments;
            Iterator<oTreatment> iteratorT = treatments.iterator();
            n = 0;
            String category = "";
            while (iteratorT.hasNext()) {
                oTreatment t = iteratorT.next();
                if(!t.treatmentCategory.equals("Intercropping")) {
                    if (!t.treatmentCategory.equals(category)) {
                        final TableRow trowCategoryTreatments = new TableRow(inputChooser.this);
                        TableRow.LayoutParams lpCategoryTreatments = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                        lpCategoryTreatments.setMargins(4, 4, 4, 4);
                        trowCategoryTreatments.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                        TextView tvCategory = new TextView(inputChooser.this);
                        tvCategory.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                        tvCategory.setText(t.treatmentCategory);
                        tvCategory.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f);
                        tvCategory.setPadding(4, 4, 4, 4);
                        trowCategoryTreatments.addView(tvCategory, lpCategoryTreatments);
                        trowCategoryTreatments.setGravity(Gravity.CENTER_VERTICAL);
                        chooserTable.addView(trowCategoryTreatments, lpCategoryTreatments);
                        category = t.treatmentCategory;
                    }
                    final TableRow trowTreatment = new TableRow(inputChooser.this);
                    TableRow.LayoutParams lpTreatment = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                    lpTreatment.setMargins(4, 4, 4, 4);
                    if (n % 2 == 0) {
                        trowTreatment.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray));
                    } else {
                        trowTreatment.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                    }

                    //String nDays = agroHelper.getTreatmentInputDaysAgo(t.treatmentId,plotN,fieldId);

                    TextView tvTreatment = new TextView(inputChooser.this);
                    tvTreatment.setId(n);
                    tvTreatment.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    tvTreatment.setText(t.treatmentName);
                    tvTreatment.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f);
                    tvTreatment.setPadding(4, 4, 4, 4);
                    //if(!nDays.equals("0")) {
                    tvTreatment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chooseTreatment(v.getId(), v);
                        }

                    });
                    //}

                    trowTreatment.addView(tvTreatment, lpTreatment);
                    trowTreatment.setGravity(Gravity.CENTER_VERTICAL);
                    chooserTable.addView(trowTreatment, lpTreatment);

                /*
                final TableRow trowBelow = new TableRow(inputChooser.this);
                TableRow.LayoutParams lpBelow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                lpBelow.setMargins(4,0,4,0);

                if(n%2==0){
                    trowBelow.setBackgroundColor(ContextCompat.getColor(this,R.color.lightGray));
                } else {
                    trowBelow.setBackgroundColor(ContextCompat.getColor(this,R.color.colorWhite));
                }

                TextView tvBelow = new TextView(inputChooser.this);
                tvBelow.setTextColor(ContextCompat.getColor(this,R.color.colorBlack));

                String daysAgo="";

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
                */


                }
                n++;
            }
        //}
    }

    /*
    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    */

    public void chooseCrop(int id, View v){
        TextView tv = (TextView)v;
        tv.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        final Context context = this;
        Intent i = new Intent(context, chooseFieldPlot.class);
        i.putExtra("userId", userId);
        i.putExtra("userRole", userRole);
        i.putExtra("task", task);
        i.putExtra("field", -1);
        //i.putExtra("plot", plotN);
        i.putExtra("cropId", crops.get(id).cropId);
        i.putExtra("treatmentId", -1);
        i.putExtra("title",crops.get(id).cropName);
        //i.putExtra("update","");

        /*
        String inputTitle="";

        if(plotN>=0) {
            inputTitle = "Field: " + field.fieldName + " R" + Integer.toString(field.fieldReplicationN) + "\nPlot " + Integer.toString(plotN + 1) + ": " + crops.get(id).cropName + " (" + crops.get(id).cropVariety + ")";
        } else {
            inputTitle = "Field: " + field.fieldName + " R" + Integer.toString(field.fieldReplicationN) + ": " + crops.get(id).cropName + " (" + crops.get(id).cropVariety + ")";
        }

        i.putExtra("title",inputTitle);
        */
        startActivity(i);
        finish();
    }

    public void chooseTreatment(int id, View v){
        TextView tv = (TextView)v;
        tv.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        final Context context = this;
        Intent i = new Intent(context, chooseFieldPlot.class);
        i.putExtra("userId", userId);
        i.putExtra("userRole", userRole);
        i.putExtra("task", task);
        i.putExtra("field", -1);
        //i.putExtra("plot", plotN);
        i.putExtra("cropId",-1);
        i.putExtra("treatmentId", treatments.get(id).treatmentId);
        i.putExtra("title",treatments.get(id).treatmentName);
        //i.putExtra("update","");

        /*
        String inputTitle="";

        if(plotN>=0) {
            inputTitle = "Field: " + field.fieldName + " R" + Integer.toString(field.fieldReplicationN) + "\nPlot " + Integer.toString(plotN + 1) + ": " + treatments.get(id).treatmentName;
        } else {
            inputTitle = "Field: " + field.fieldName + " R" + Integer.toString(field.fieldReplicationN) + ": " + treatments.get(id).treatmentName;
        }

        i.putExtra("title",inputTitle);
        */
        startActivity(i);
        finish();
    }
}
