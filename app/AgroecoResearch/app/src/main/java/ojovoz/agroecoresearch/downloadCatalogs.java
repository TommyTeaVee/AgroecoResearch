package ojovoz.agroecoresearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Eugenio on 29/03/2017.
 */
public class downloadCatalogs extends AppCompatActivity {

    public int userId;
    public int userRole;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_catalogs);
        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
    }

    public void settings(View v){
        final Context context = this;
        Intent i = new Intent(context, settings.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        startActivity(i);
        finish();
    }
}
