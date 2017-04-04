package ojovoz.agroecoresearch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

public class loginScreen extends AppCompatActivity implements httpConnection.AsyncResponse {

    public String server = "";
    private promptDialog dlg = null;
    private preferenceManager prefs;
    private boolean bConnecting = false;

    public int userId;
    public int userRole;

    public String uAS;
    public String uPS;

    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        initializeVariables();
        tv = (TextView)findViewById(R.id.loginMessage);
    }

    private void initializeVariables(){
        prefs = new preferenceManager(this);
        server = prefs.getPreference("server");
        if (server.equals("")) {
            defineServer("");
        }

        prefs.savePreference("currentField","-1");
        prefs.savePreference("currentPlot","-1");
    }

    public void defineServer(String current) {
        dlg = new promptDialog(this, R.string.emptyString, R.string.defineServerLabel, current) {
            @Override
            public boolean onOkClicked(String input) {
                if(!input.startsWith("http://")){
                    input="http://"+input;
                }
                loginScreen.this.server = input;
                prefs.savePreference("server", input);
                return true;
            }
        };
        dlg.show();
    }

    public void validateUser(View v){
        httpConnection http = new httpConnection(this,this);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        tv.setVisibility(View.VISIBLE);
        EditText uA = (EditText)findViewById(R.id.userAlias);
        uAS = uA.getText().toString();
        EditText uP = (EditText)findViewById(R.id.userPassword);
        uPS = uP.getText().toString();
        if(!uAS.equals("") && !uPS.equals("")) {
            if(uAS.equals("admin") && uPS.equals("admin")){
                defineServer(server);
            } else {
                if (http.isOnline()) {
                    if (!bConnecting) {
                        tv.setText(R.string.connectingMessage);
                        bConnecting = true;
                        http.execute(server + "/mobile/validate_user.php?user_alias=" + uAS + "&user_password=" + uPS,"");
                    }
                } else {
                    String u = prefs.getUserFromPrefs("users","*"+ uAS + "," + uPS + "*");
                    if(u.equals("-1")){
                        tv.setVisibility(View.VISIBLE);
                        tv.setText(R.string.invalidUserMessage);
                    } else {
                        CharSequence parts[] = u.split(",");
                        userId=Integer.parseInt(parts[0].toString());
                        userRole=Integer.parseInt(parts[1].toString());
                        launchMainMenu();
                    }
                }
            }
        } else {
            //this code for test purposes only
            //delete following block
            //begin
            userId=1;
            userRole=2;
            launchMainMenu();
            //end
        }
    }

    @Override
    public void processFinish(String output){
        bConnecting=false;
        CharSequence parts[];
        if(TextUtils.isEmpty(output)){
            tv.setText(R.string.checkConnectionMessage);
        } else if(output.equals("-1")){
            tv.setText(R.string.invalidUserMessage);
        } else {
            parts=output.split(",");
            if(parts.length==2){
                userId=Integer.parseInt(parts[0].toString());
                userRole=Integer.parseInt(parts[1].toString());
                prefs.updateUserPrefs("users","*"+ uAS + "," + uPS + "*," + userId + "," + userRole);
                launchMainMenu();
            } else {
                tv.setText(R.string.invalidUserMessage);
            }
        }
    }

    public void launchMainMenu(){
        final Context context = this;
        Intent i = new Intent(context, mainMenu.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        startActivity(i);
        finish();
    }
}
