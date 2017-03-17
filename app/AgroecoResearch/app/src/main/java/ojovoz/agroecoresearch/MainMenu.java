package ojovoz.agroecoresearch;

import android.content.Context;
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
public class MainMenu extends AppCompatActivity {

    public int userId;
    public int userRole;

    private preferenceManager prefs;
    boolean bFieldsCreated=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");

        checkFieldsCreated();
        createButtons();
    }

    public void createButtons(){

        LinearLayout.LayoutParams params;
        LinearLayout layout = (LinearLayout)findViewById(R.id.menu_layout);

        for(int i=0;i<5;i++){
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.height=70;
            params.gravity=Gravity.CENTER;
            params.topMargin=50;

            if((i<3) && bFieldsCreated) {
                Button b = new Button(MainMenu.this);
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
                        b.setText(R.string.registerMeasurementButtonText);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                addMeasurement();
                            }
                        });
                        layout.addView(b, params);
                        break;
                    case 2:
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
            } else if(i>=3) {
                Button b = new Button(MainMenu.this);
                b.setBackgroundResource(R.drawable.button_background);
                b.setTextColor(Color.WHITE);
                b.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                b.setId(i);

                switch(i){
                    case 3:
                        b.setText(R.string.settingsButtonText);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                settings();
                            }
                        });
                        layout.addView(b,params);
                        break;
                    case 4:
                        b.setText(R.string.logoutButtonText);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                logout();
                            }
                        });
                        layout.addView(b,params);
                        break;
                }
            }
        }
    }

    public void checkFieldsCreated(){
        String fields;

        prefs = new preferenceManager(this);
        fields = prefs.getPreference("fields");

        if(!fields.isEmpty()){
            bFieldsCreated=true;
        }
    }

    public void addActivity(){

    }

    public void addMeasurement(){

    }

    public void manageData(){

    }

    public void settings(){
        final Context context = this;
        Intent i = new Intent(context, Settings.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        startActivity(i);
        finish();
    }

    public void logout(){
        final Context context = this;
        Intent i = new Intent(context, LoginScreen.class);
        startActivity(i);
        finish();
    }
}
