package ojovoz.agroecoresearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Eugenio on 15/03/2017.
 */
public class Settings extends AppCompatActivity {

    public int userId;
    public int userRole;
    public String server = "";

    private promptDialog dlg = null;
    private preferenceManager prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeVariables();
    }

    private void initializeVariables(){
        prefs = new preferenceManager(this);
        server = prefs.getPreference("server");
        if (server.equals("")) {
            defineServer("");
        }
    }

    public void defineServerButton(View v){
        defineServer(server);
    }

    public void defineServer(String current) {
        dlg = new promptDialog(this, R.string.emptyString, R.string.defineServerLabel, current) {
            @Override
            public boolean onOkClicked(String input) {
                if(!input.startsWith("http://")){
                    input="http://"+input;
                }
                Settings.this.server = input;
                prefs.savePreference("server", input);
                return true;
            }
        };
        dlg.show();
    }

    public void logout(View v){
        final Context context = this;
        Intent i = new Intent(context, LoginScreen.class);
        startActivity(i);
        finish();
    }

    public void mainMenu(View v){
        final Context context = this;
        Intent i = new Intent(context, MainMenu.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        startActivity(i);
        finish();
    }
}
