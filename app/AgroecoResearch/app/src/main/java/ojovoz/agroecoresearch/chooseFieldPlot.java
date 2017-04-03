package ojovoz.agroecoresearch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
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
import java.util.List;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_field_plot);

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");


        CharSequence title = task + ": " + getTitle();
        setTitle(title);

        prefs = new preferenceManager(this);

        agroHelper = new agroecoHelper(this);

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
                    Button fieldListView = (Button) findViewById(R.id.chooseFieldButton);
                    fieldListView.setText(field.fieldName + " r" + field.fieldReplicationN);
                    msg=getString(R.string.choosePlotPrompt);
                    prefs.savePreference("currentField",Integer.toString(field.fieldId));
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

        ArrayList<oPlot> plots = agroHelper.getPlotsFromFieldId(field.fieldId);

        TableLayout plotsGrid = (TableLayout) findViewById(R.id.plotsGrid);
        plotsGrid.removeAllViews();

        int n=0;
        ArrayList cropList = new ArrayList();
        int currentCropN;
        for(int i=0;i<field.rows;i++){
            final TableRow trow = new TableRow(chooseFieldPlot.this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
            lp.setMargins(2,2,2,2);
            for(int j=0;j<field.columns;j++){
                oPlot plot = plots.get(n);
                Button b = new Button(chooseFieldPlot.this);
                b.setId(n);
                b.setPadding(3,3,3,3);
                if(!plot.hasPestControl && !plot.hasSoilManagement){
                    b.setBackgroundColor(ContextCompat.getColor(this, R.color.noTreatments));
                } else if(!plot.hasPestControl && plot.hasSoilManagement){
                    b.setBackgroundColor(ContextCompat.getColor(this, R.color.soilManagement));
                } else if(plot.hasPestControl && !plot.hasSoilManagement){
                    b.setBackgroundColor(ContextCompat.getColor(this, R.color.pestControl));
                } else {
                    b.setBackgroundColor(ContextCompat.getColor(this, R.color.bothTreatments));
                }

                b.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));

                oCrop pc = plot.primaryCrop;
                int pcId = pc.cropId;
                if(!cropList.contains(pcId)){
                    cropList.add(pcId);
                    currentCropN=cropList.size();
                } else {
                    currentCropN=cropList.indexOf(pcId)+1;
                }
                String cropsInPlot = "C" + Integer.toString(currentCropN);

                oCrop ic = plot.intercroppingCrop;
                if(ic!=null){
                    cropsInPlot += "+L";
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
    }

    void choosePlot(int n, View v){
        Button b = (Button)v;
        b.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
    }
}
