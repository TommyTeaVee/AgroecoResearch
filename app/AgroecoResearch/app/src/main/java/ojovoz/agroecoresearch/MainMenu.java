package ojovoz.agroecoresearch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Eugenio on 14/03/2017.
 */
public class mainMenu extends AppCompatActivity {

    public int userId;
    public int userRole;

    private preferenceManager prefs;
    boolean bCatalogsDownloaded=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");

        checkCatalogsDownloaded();
        createButtons();
    }

    @Override
    public void onBackPressed(){
        logout();
    }

    public void createButtons(){

        LinearLayout.LayoutParams params;
        LinearLayout layout = (LinearLayout)findViewById(R.id.menu_layout);

        for(int i=0;i<6;i++){
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.height=70;
            params.gravity=Gravity.CENTER;
            if(i>0) {
                params.topMargin = 50;
            }

            if((i<3) && bCatalogsDownloaded) {
                Button b = new Button(mainMenu.this);
                b.setBackgroundResource(R.drawable.button_background);
                b.setTextColor(Color.WHITE);
                b.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                b.setId(i);
                switch(i) {
                    case 0:
                        b.setText(R.string.registerActivityButtonText);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                addActivity();
                            }
                        });
                        layout.addView(b, params);
                        break;
                    case 1:
                        b.setText(R.string.registerInputButton);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                addInput();
                            }
                        });
                        layout.addView(b, params);
                        break;
                    case 2:
                        b.setText(R.string.registerMeasurementButtonText);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                addMeasurement();
                            }
                        });
                        layout.addView(b, params);
                        break;
                    case 3:
                        b.setText(R.string.manageDataButtonText);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                manageData();
                            }
                        });
                        layout.addView(b, params);
                        break;
                }
            } else if(i>=4) {
                Button b = new Button(mainMenu.this);
                b.setBackgroundResource(R.drawable.button_background);
                b.setTextColor(Color.WHITE);
                b.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                b.setId(i);

                switch(i){
                    case 4:
                        b.setText(R.string.settingsButtonText);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                settings();
                            }
                        });
                        layout.addView(b,params);
                        break;
                    case 5:
                        b.setText(R.string.logoutButtonText);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                doLogout();
                            }
                        });
                        layout.addView(b,params);
                        break;
                }
            }
        }
    }

    public void checkCatalogsDownloaded(){

        prefs = new preferenceManager(this);

        bCatalogsDownloaded = (prefs.exists("crops") && prefs.exists("treatments") && prefs.exists("measurements") && prefs.exists("activities") && prefs.exists("fields"));
    }

    public void addActivity(){
        final Context context = this;
        Intent i = new Intent(context, chooseFieldPlot.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        i.putExtra("task","activity");
        i.putExtra("field",-1);
        startActivity(i);
        finish();
    }

    public void addInput(){

    }

    public void addMeasurement(){

    }

    public void manageData(){

    }

    public void settings(){
        final Context context = this;
        Intent i = new Intent(context, settings.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        startActivity(i);
        finish();
    }

    public void logout(){
        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(this);
        logoutDialog.setTitle(R.string.logoutAlertTitle);
        logoutDialog.setMessage(R.string.logoutAlertString);
        logoutDialog.setNegativeButton(R.string.cancelButtonText,null);
        logoutDialog.setPositiveButton(R.string.okButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                doLogout();
            }
        });
        logoutDialog.create();
        logoutDialog.show();

    }

    public void doLogout(){
        final Context context = this;
        Intent i = new Intent(context, loginScreen.class);
        startActivity(i);
        finish();
    }
}
