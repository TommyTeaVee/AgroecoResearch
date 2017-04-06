package ojovoz.agroecoresearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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
}
