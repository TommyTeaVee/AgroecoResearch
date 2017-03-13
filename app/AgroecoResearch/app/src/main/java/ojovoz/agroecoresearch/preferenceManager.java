package ojovoz.agroecoresearch;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Eugenio on 13/03/2017.
 */
public class preferenceManager {

    Context context;

    preferenceManager(Context c){
        context=c;
    }

    public String getPreference(String keyName) {
        String value = "";
        SharedPreferences agroEcoPrefs = context.getSharedPreferences("agroEcoPrefs", context.MODE_PRIVATE);
        value = agroEcoPrefs.getString(keyName, "");
        return value;
    }

    public void savePreference(String keyName, String keyValue) {
        SharedPreferences agroEcoPrefs = context.getSharedPreferences("agroEcoPrefs", context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = agroEcoPrefs.edit();
        prefEditor.putString(keyName, keyValue);
        prefEditor.commit();
    }
}
