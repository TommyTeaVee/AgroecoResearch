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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_chooser);

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");

        agroHelper = new agroecoHelper(this, "crops,fields,treatments");

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

        if(plotN>=0){
            crops = agroHelper.getPlotCropsFromFieldId(fieldId, plotN);
        } else {
            crops = agroHelper.getCropsFromFieldId(fieldId);
        }
        Iterator<oCrop> iterator = crops.iterator();
        int n = 0;
        while (iterator.hasNext()) {

            oCrop crop = iterator.next();
            String cropName = crop.cropName + " (" + crop.cropVariety + ")";

            final TableRow trowCrop = new TableRow(inputChooser.this);
            TableRow.LayoutParams lpCrop = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
            lpCrop.setMargins(4, 4, 4, 4);

            if (n % 2 == 0) {
                trowCrop.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray));
            } else {
                trowCrop.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
            }

            TextView tvCrop = new TextView(inputChooser.this);
            tvCrop.setId(n);
            tvCrop.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tvCrop.setText(cropName);
            tvCrop.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f);
            tvCrop.setPadding(4, 4, 4, 4);
            tvCrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseCrop(v.getId(), v);
                }

            });

            trowCrop.addView(tvCrop, lpCrop);
            trowCrop.setGravity(Gravity.CENTER_VERTICAL);
            chooserTable.addView(trowCrop, lpCrop);

            n++;
        }

        if(((field.hasSoilManagement || field.hasPestControl) && plotN<0) || ((plotHasSoilManagement || plotHasPestControl) && plotN>=0)){
            final TableRow trowHeaderTreatments = new TableRow(inputChooser.this);
            TableRow.LayoutParams lpHeaderTreatments = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
            lpHeaderTreatments.setMargins(4, 4, 4, 4);
            trowHeaderTreatments.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            TextView tv2 = new TextView(inputChooser.this);
            tv2.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            tv2.setText(R.string.treatmentsTableRowText);
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f);
            tv2.setPadding(4, 4, 4, 4);
            trowHeaderTreatments.addView(tv2, lpHeaderTreatments);
            trowHeaderTreatments.setGravity(Gravity.CENTER_VERTICAL);
            chooserTable.addView(trowHeaderTreatments, lpHeaderTreatments);

            int nTreatments=0;
            if((plotHasPestControl && plotN>=0) || (field.hasPestControl && plotN<0)) {
                //TODO: get treatments where category = Pest control
                final TableRow trowTreatment = new TableRow(inputChooser.this);
                TableRow.LayoutParams lpTreatment = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                lpTreatment.setMargins(4, 4, 4, 4);
                trowTreatment.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray));
                TextView tvTreatment = new TextView(inputChooser.this);
                tvTreatment.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                tvTreatment.setText("Pest control");
                tvTreatment.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f);
                tvTreatment.setPadding(4, 4, 4, 4);
                tvTreatment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseTreatment(v.getId(), v);
                    }

                });

                trowTreatment.addView(tvTreatment, lpTreatment);
                trowTreatment.setGravity(Gravity.CENTER_VERTICAL);
                chooserTable.addView(trowTreatment, lpTreatment);
                nTreatments=1;
            }

            if((plotHasSoilManagement && plotN>=0) || (field.hasSoilManagement && plotN<0)) {
                //TODO: get treatments where category = Soil management
                final TableRow trowTreatment = new TableRow(inputChooser.this);
                TableRow.LayoutParams lpTreatment = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                lpTreatment.setMargins(4, 4, 4, 4);
                if(nTreatments==0) {
                    trowTreatment.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray));
                } else {
                    trowTreatment.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                }
                TextView tvTreatment = new TextView(inputChooser.this);
                tvTreatment.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                tvTreatment.setText("Soil management");
                tvTreatment.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f);
                tvTreatment.setPadding(4, 4, 4, 4);
                tvTreatment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseTreatment(v.getId(), v);
                    }

                });

                trowTreatment.addView(tvTreatment, lpTreatment);
                trowTreatment.setGravity(Gravity.CENTER_VERTICAL);
                chooserTable.addView(trowTreatment, lpTreatment);
            }
        }
    }

    public void chooseCrop(int id, View v){
        TextView tv = (TextView)v;
        tv.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        final Context context = this;
        Intent i = new Intent(context, enterCropInput.class);
        i.putExtra("userId", userId);
        i.putExtra("userRole", userRole);
        i.putExtra("task", task);
        i.putExtra("field", fieldId);
        i.putExtra("plot", plotN);
        i.putExtra("cropId", crops.get(id).cropId);
        i.putExtra("update","");

        String inputTitle="";

        if(plotN>=0) {
            inputTitle = "Field: " + field.fieldName + " R" + Integer.toString(field.fieldReplicationN) + "\nPlot " + Integer.toString(plotN + 1) + ": " + crops.get(id).cropName + " (" + crops.get(id).cropVariety + ")";
        } else {
            inputTitle = "Field: " + field.fieldName + " R" + Integer.toString(field.fieldReplicationN) + ": " + crops.get(id).cropName + " (" + crops.get(id).cropVariety + ")";
        }

        i.putExtra("title",inputTitle);
        startActivity(i);
        finish();
    }

    public void chooseTreatment(int id, View v){

    }
}
