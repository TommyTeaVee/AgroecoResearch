package ojovoz.agroecoresearch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Eugenio on 09/01/2018.
 */
public class enterHealthReport extends AppCompatActivity {

    public boolean changes=false;
    public int exitAction;

    public agroecoHelper agroHelper;

    public ArrayList<oHealthReport> healthReportItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_health_report);

        agroHelper = new agroecoHelper(this,"");
        agroHelper.createHealthReportItems();
        healthReportItems = agroHelper.healthReportItems;

        fillItemsTable();
    }

    @Override public void onBackPressed () {
        if(changes) {
            exitAction = 0;
            confirmExit();
        } else {
            goBack();
        }
    }

    public void confirmExit(){
        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(this);
        logoutDialog.setTitle(R.string.exitAlertTitle);
        logoutDialog.setMessage(R.string.exitAlertString);
        logoutDialog.setNegativeButton(R.string.cancelButtonText,null);
        logoutDialog.setPositiveButton(R.string.okButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (exitAction){
                    case 0:
                        goBack();
                        break;
                    case 1:
                        //goToDataManager();
                        break;
                    case 2:
                        //goToMainMenu();
                        break;
                }
            }
        });
        logoutDialog.create();
        logoutDialog.show();
    }

    public void goBack(){
        final Context context = this;
        Intent i = new Intent(context, enterMeasurement.class);
        startActivity(i);
        finish();
    }

    public void fillItemsTable(){
        TableLayout itemsTable = (TableLayout) findViewById(R.id.itemsTable);
        Iterator<oHealthReport> iterator = healthReportItems.iterator();
        int n=0;
        while (iterator.hasNext()) {
            oHealthReport hi = iterator.next();

            final TableRow trow = new TableRow(enterHealthReport.this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
            lp.setMargins(10, 10, 0, 10);
            if (n % 2 == 0) {
                trow.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray));
            } else {
                trow.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
            }

            CheckBox cb = new CheckBox(enterHealthReport.this);
            cb.setButtonDrawable(R.drawable.custom_checkbox);
            cb.setId(n);
            cb.setPadding(0, 10, 0, 10);
            cb.setMinWidth(60);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    view.requestFocus();
                }
            });
            trow.addView(cb, lp);

            TextView tv = new TextView(enterHealthReport.this);
            tv.setId(n);
            tv.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
            tv.setText(hi.itemName);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f);
            tv.setPadding(0,15,0,0);
            tv.setMaxWidth(200);
            trow.addView(tv, lp);

            Button sb = new Button(enterHealthReport.this);
            sb.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f);
            sb.setText(R.string.chooseValueSampleTable);
            sb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            sb.setPadding(10,10,0,10);
            sb.setId(n);
            sb.setBackgroundResource(R.drawable.button_background);
            sb.setTextColor(Color.WHITE);
            sb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //currentSampleChoiceButton = (Button) v;
                    //showMeasurementCategoriesSampleTable();
                    changes = true;
                }
            });
            sb.setVisibility(View.INVISIBLE);
            trow.addView(sb, lp);

            trow.setGravity(Gravity.CENTER_VERTICAL);
            itemsTable.addView(trow, lp);
            n++;
        }
    }
}
