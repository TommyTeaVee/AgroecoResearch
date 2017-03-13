package ojovoz.agroecoresearch;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.io.File;

public class LoginScreen extends AppCompatActivity implements httpConnection.AsyncResponse {

    private String server = "";
    private fileHandler fh;
    private httpConnection http;
    private promptDialog dlg = null;
    private preferenceManager prefs;
    private boolean bConnecting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        http = new httpConnection(this,this);

        fh = new fileHandler();
        fh.createDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "agroeco" + File.separator);

        initializeVariables();
    }

    private void initializeVariables(){
        prefs = new preferenceManager(this);
        server = prefs.getPreference("server");
        if (server.equals("")) {
            defineServer("");
        }
    }

    public void defineServer(String current) {
        dlg = new promptDialog(this, R.string.emptyString, R.string.defineServerLabel, current) {
            @Override
            public boolean onOkClicked(String input) {
                if(!input.startsWith("http://")){
                    input="http://"+input;
                }
                LoginScreen.this.server = input;
                prefs.savePreference("server", input);
                return true;
            }
        };
        dlg.show();
    }

    public void validateUser(){

        EditText uA = (EditText)findViewById(R.id.userAlias);
        String uAS = uA.getText().toString();
        EditText uP = (EditText)findViewById(R.id.userPassword);
        String uPS = uP.getText().toString();
        if(!uAS.equals("") && !uPS.equals("")) {
            if (http.isOnline()) {
                if (!bConnecting) {
                    bConnecting = true;
                    http.execute(server + "/mobile/validate_user.php?user_alias=" + uAS + "&user_password=" + uPS);
                }
            } else {

            }
        }
    }

    @Override
    public void processFinish(String output){
        bConnecting=false;
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
    }
}
