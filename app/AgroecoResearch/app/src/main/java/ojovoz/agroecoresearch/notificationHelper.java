package ojovoz.agroecoresearch;

import android.content.Context;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by Eugenio on 15/09/2017.
 */
public class notificationHelper {

    private Context context;
    ArrayList<oNotification> notifications;

    notificationHelper(Context context){
        this.context=context;
        createNotifications();
    }

    public void createNotifications(){
        notifications = new ArrayList<>();
        List<String[]> notificationsCSV = readCSVFile("notifications");
        if(notificationsCSV!=null) {
            Iterator<String[]> iterator = notificationsCSV.iterator();
            while (iterator.hasNext()) {
                String[] record = iterator.next();
                oNotification notification = new oNotification();
                notification.notificationSender = record[1];
                notification.notificationDate = record[2];
                notification.notificationText = record[3];
                notification.forUserId = Integer.parseInt(record[4]);
                notifications.add(notification);
            }
        }
    }

    public boolean notificationsPending(int userId){
        boolean ret = false;
        Iterator<oNotification> iterator = notifications.iterator();
        while (iterator.hasNext()) {
            oNotification n = iterator.next();
            if(n.forUserId==userId){
                ret=true;
                break;
            }
        }
        return ret;
    }

    public List<String[]> readCSVFile(String filename){
        List<String[]> ret = null;

        File file = new File(context.getFilesDir(), filename);
        if(file.exists()) {
            try {
                FileReader r = new FileReader(file);
                CSVReader reader = new CSVReader(r, ',', '"');
                ret = reader.readAll();
            } catch (IOException e) {

            } finally {
                return ret;
            }
        }

        return ret;
    }
}
