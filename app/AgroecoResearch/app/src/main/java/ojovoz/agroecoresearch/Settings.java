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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
