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
        SharedPreferences agroEcoPrefs = context.getSharedPreferences("agroEcoPrefs", Context.MODE_PRIVATE);
        value = agroEcoPrefs.getString(keyName, "");
        return value;
    }

    public boolean exists(String keyName) {
        boolean ret;
        SharedPreferences agroEcoPrefs = context.getSharedPreferences("agroEcoPrefs", Context.MODE_PRIVATE);
        ret = agroEcoPrefs.contains(keyName);
        return ret;
    }

    public void savePreference(String keyName, String keyValue) {
        SharedPreferences agroEcoPrefs = context.getSharedPreferences("agroEcoPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = agroEcoPrefs.edit();
        prefEditor.putString(keyName, keyValue);
        prefEditor.commit();
    }

    public void savePreferenceBoolean(String keyName, boolean keyValue){
        SharedPreferences agroEcoPrefs = context.getSharedPreferences("agroEcoPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = agroEcoPrefs.edit();
        prefEditor.putBoolean(keyName, keyValue);
        prefEditor.commit();
    }

    public void updateUserPrefs(String keyName, String keyValue){
        String value = getPreference(keyName);
        if(value.isEmpty()){
            savePreference(keyName,keyValue);
        } else if(!value.contains(keyValue)){
            savePreference(keyName,value + ";" + keyValue);
        }
    }

    public String getUserFromPrefs(String keyName, String aliasPass){
        String ret="-1";
        String value = getPreference(keyName);
        CharSequence users[] = value.split(";");
        for(int i=0; i < users.length; i++){
            if(users[i].toString().contains(aliasPass)){
                CharSequence parts[] = users[i].toString().split(",");
                ret=parts[2]+","+parts[3];
                break;
            }
        }
        return ret;
    }
}
