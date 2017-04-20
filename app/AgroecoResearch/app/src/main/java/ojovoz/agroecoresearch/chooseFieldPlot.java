package ojovoz.agroecoresearch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Eugenio on 02/04/2017.
 */
public class chooseFieldPlot extends AppCompatActivity {

    public int userId;
    public int userRole;
    public String task;

    private preferenceManager prefs;

    public agroecoHelper agroHelper;

    ArrayList<oField> fields;
    CharSequence fieldsArray[];
    oField field;

    public String legend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_field_plot);

        prefs = new preferenceManager(this);

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");
        int fieldId = getIntent().getExtras().getInt("field");
        if(!(fieldId>=0)){
            fieldId = Integer.parseInt(prefs.getPreference("field"));
        }

        CharSequence title = getTitle() + " " + task;
        setTitle(title);

        agroHelper = new agroecoHelper(this,"crops,fields");
        fields = agroHelper.fields;


        ArrayList<CharSequence> tf = new ArrayList<>();
        for(int i=0; i<fields.size(); i++){
            tf.add(fields.get(i).fieldName + " replication " + Integer.toString(fields.get(i).fieldReplicationN));
        }
        fieldsArray=tf.toArray(new CharSequence[tf.size()]);

        Button fieldListView = (Button) findViewById(R.id.chooseFieldButton);

        fieldListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.chooseFieldButton:
                        showSelectFieldsDialog();
                        break;
                    default:
                        break;
                }
            }
        });

        if(fieldId>=0){
            field = agroHelper.getFieldFromId(fieldId);
            fieldListView = (Button) findViewById(R.id.chooseFieldButton);
            fieldListView.setText(field.fieldName + " r" + field.fieldReplicationN);
            String msg=getString(R.string.choosePlotPrompt);
            TextView choosePlotMessage = (TextView) findViewById(R.id.choosePlotMessage);
            choosePlotMessage.setText(msg);
            drawPlots();
        }

    }

    @Override public void onBackPressed(){
        final Context context = this;
        Intent i = new Intent(context, mainMenu.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        startActivity(i);
        finish();
    }

    public void showSelectFieldsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.cancelButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final ListAdapter adapter = new ArrayAdapter<>(this,R.layout.checked_list_template,fieldsArray);
        builder.setSingleChoiceItems(adapter,-1,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String msg="";
                if(i>=0) {
                    field = fields.get(i);
                    prefs.savePreference("field",Integer.toString(field.fieldId));
                    Button fieldListView = (Button) findViewById(R.id.chooseFieldButton);
                    fieldListView.setText(field.fieldName + " r" + field.fieldReplicationN);
                    msg=getString(R.string.choosePlotPrompt);
                    drawPlots();
                }
                TextView choosePlotMessage = (TextView) findViewById(R.id.choosePlotMessage);
                choosePlotMessage.setText(msg);
                dialogInterface.dismiss();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void drawPlots(){

        ArrayList<oPlot> plots = field.plots;

        TableLayout plotsGrid = (TableLayout) findViewById(R.id.plotsGrid);
        plotsGrid.removeAllViews();

        int n=0;
        ArrayList cropList = new ArrayList();
        int currentCropN;
        String cropsInLegend="";
        String intercropInLegend="";
        String[] treatmentNames = {"Control treatment","Soil management","Pest control","Soil management and pest control"};
        ArrayList<String> treatmentLegends = new ArrayList<>();
        for(int i=0;i<field.rows;i++){
            final TableRow trow = new TableRow(chooseFieldPlot.this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
            lp.setMargins(2,2,2,2);
            for(int j=0;j<field.columns;j++){
                oPlot plot = plots.get(n);
                Button b = new Button(chooseFieldPlot.this);
                b.setId(n);
                b.setPadding(3,3,3,3);
                if(!plot.hasPestControl && !plot.hasSoilManagement){
                    b.setBackgroundColor(ContextCompat.getColor(this, R.color.noTreatments));
                    if(!treatmentLegends.contains(treatmentNames[0])){
                        treatmentLegends.add(treatmentNames[0]);
                    }
                } else if(!plot.hasPestControl && plot.hasSoilManagement){
                    b.setBackgroundColor(ContextCompat.getColor(this, R.color.soilManagement));
                    if(!treatmentLegends.contains(treatmentNames[1])){
                        treatmentLegends.add(treatmentNames[1]);
                    }
                } else if(plot.hasPestControl && !plot.hasSoilManagement){
                    b.setBackgroundColor(ContextCompat.getColor(this, R.color.pestControl));
                    if(!treatmentLegends.contains(treatmentNames[2])){
                        treatmentLegends.add(treatmentNames[2]);
                    }
                } else {
                    b.setBackgroundColor(ContextCompat.getColor(this, R.color.bothTreatments));
                    if(!treatmentLegends.contains(treatmentNames[3])){
                        treatmentLegends.add(treatmentNames[3]);
                    }
                }

                b.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));

                oCrop pc = plot.primaryCrop;
                int pcId = pc.cropId;
                if(!cropList.contains(pcId)){
                    cropList.add(pcId);
                    currentCropN=cropList.size();
                    if(cropsInLegend.isEmpty()){
                        cropsInLegend="C" + Integer.toString(currentCropN) + ": " + pc.cropName + " (" + pc.cropVariety + ")";
                    } else {
                        cropsInLegend+="\nC" + Integer.toString(currentCropN) + ": " + pc.cropName + " (" + pc.cropVariety + ")";
                    }
                } else {
                    currentCropN=cropList.indexOf(pcId)+1;
                }
                String cropsInPlot = "C" + Integer.toString(currentCropN);

                oCrop ic = plot.intercroppingCrop;
                if(ic!=null){
                    cropsInPlot += "+L";
                    intercropInLegend="\nL: " + ic.cropName + " (" + ic.cropVariety + ")";
                }

                b.setText(cropsInPlot);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        choosePlot(v.getId(),v);
                    }

                });
                trow.addView(b,lp);
                n++;
            }
            trow.setGravity(Gravity.CENTER_VERTICAL);
            plotsGrid.addView(trow, lp);
        }
        Button b = (Button)findViewById(R.id.chooseEntireFieldButton);
        b.setVisibility(View.VISIBLE);

        legend=cropsInLegend+intercropInLegend;

        TextView l = (TextView)findViewById(R.id.fieldLegend);
        l.setVisibility(View.VISIBLE);
        l.setText(legend);

        int i=0;
        Iterator<String> iterator = treatmentLegends.iterator();
        while (iterator.hasNext()) {
            TextView tl= new TextView(this);
            String record = iterator.next();
            switch(i){
                case 0:
                    tl = (TextView)findViewById(R.id.treatment1Legend);
                    break;
                case 1:
                    tl = (TextView)findViewById(R.id.treatment2Legend);
                    break;
                case 2:
                    tl = (TextView)findViewById(R.id.treatment3Legend);
                    break;
                case 3:
                    tl = (TextView)findViewById(R.id.treatment4Legend);
                    break;
            }
            tl.setVisibility(View.VISIBLE);
            if(record.equals(treatmentNames[0])) {
                tl.setTextColor(ContextCompat.getColor(this, R.color.noTreatments));
            } else if(record.equals(treatmentNames[1])) {
                tl.setTextColor(ContextCompat.getColor(this, R.color.soilManagement));
            } else if(record.equals(treatmentNames[2])) {
                tl.setTextColor(ContextCompat.getColor(this, R.color.pestControl));
            } else if(record.equals(treatmentNames[3])) {
                tl.setTextColor(ContextCompat.getColor(this, R.color.bothTreatments));
            }
            tl.setText(record);
            i++;
        }
    }

    void choosePlot(int n, View v){
        Button b = (Button)v;
        b.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        final Context context = this;
        Intent i = new Intent(context, chooser.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        i.putExtra("task",task);
        i.putExtra("field",field.fieldId);
        i.putExtra("plot",n);

        startActivity(i);
        finish();
    }

    public void chooseEntireField(View v){
        final Context context = this;
        Intent i = new Intent(context, chooser.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        i.putExtra("task",task);
        i.putExtra("field",field.fieldId);
        i.putExtra("plot",-1);

        startActivity(i);
        finish();
    }
}
