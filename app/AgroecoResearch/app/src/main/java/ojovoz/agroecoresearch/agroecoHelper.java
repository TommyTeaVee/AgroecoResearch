package ojovoz.agroecoresearch;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by Eugenio on 02/04/2017.
 */
public class agroecoHelper {

    private Context context;
    ArrayList<oCrop> crops;
    ArrayList<oTreatment> treatments;
    ArrayList<oField> fields;
    ArrayList<oActivity> activities;
    ArrayList<oActivityCalendar> activitiesCalendar;

    ArrayList<oLog> log;

    agroecoHelper(Context context, String catalogsNeeded){
        this.context=context;
        if(catalogsNeeded.contains("crops")) {
            createCrops();
        }
        if(catalogsNeeded.contains("treatments")) {
            createTreatments();
        }
        if(catalogsNeeded.contains("fields")) {
            createFields();
        }
        if(catalogsNeeded.contains("activities")) {
            createActivities();
        }
        if(catalogsNeeded.contains("log")) {
            createLog();
        }
    }

    public void createCrops(){
        crops = new ArrayList<>();
        List<String[]> cropsCSV = readCSVFile("crops");
        if(cropsCSV!=null) {
            Iterator<String[]> iterator = cropsCSV.iterator();
            while (iterator.hasNext()) {
                String[] record = iterator.next();
                oCrop crop = new oCrop();
                crop.cropId = Integer.parseInt(record[0]);
                crop.cropName = record[1];
                crop.cropVariety = record[2];
                crops.add(crop);
            }
        }
    }

    public void createActivities(){
        activities = new ArrayList<>();
        activitiesCalendar = new ArrayList<>();
        List<String[]> activitiesCSV = readCSVFile("activities");
        List<String[]> activitiesAppliedCSV = readCSVFile("activities_applied");
        String activitiesCalendarFile = readFromFile("activities_calendar");
        if(activitiesCSV!=null) {
            Iterator<String[]> iterator = activitiesCSV.iterator();
            while (iterator.hasNext()) {
                String[] record = iterator.next();
                oActivity activity = new oActivity();
                activity.activityId = Integer.parseInt(record[0]);
                activity.activityName = record[1];
                activity.activityCategory = record[2];
                activity.activityPeriodicity = Integer.parseInt(record[3]);
                activity.activityMeasurementUnits = record[4];
                activities.add(activity);
            }

            if (activitiesAppliedCSV != null) {
                Iterator<String[]> iteratorApplied = activitiesAppliedCSV.iterator();
                while (iteratorApplied.hasNext()) {
                    String[] record = iteratorApplied.next();
                    addCropTreatmentToActivity(Integer.parseInt(record[0]), record[1], record[2]);
                }
            }

            if(!activitiesCalendarFile.isEmpty()){
                String[] activitiesCalendarLines = activitiesCalendarFile.split(";");
                for(int i=0;i<activitiesCalendarLines.length;i++){
                    oActivityCalendar aC = new oActivityCalendar();
                    String[] activitiesCalendarParts = activitiesCalendarLines[i].split(",");
                    aC.activityId=Integer.parseInt(activitiesCalendarParts[0]);
                    aC.plotN=Integer.parseInt(activitiesCalendarParts[1]);
                    aC.fieldId=Integer.parseInt(activitiesCalendarParts[2]);
                    aC.date=activitiesCalendarParts[3];
                    activitiesCalendar.add(aC);
                }
            }
        }
    }

    public void addCropTreatmentToActivity(int aId, String cId, String tId){
        Iterator<oActivity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            oActivity activity = iterator.next();
            if(activity.activityId==aId){
                if(!cId.isEmpty()){
                    oCrop aC = getCropFromId(Integer.parseInt(cId));
                    activity.activityAppliesToCrops.add(aC);
                }
                if(!tId.isEmpty()){
                    oTreatment aT = getTreatmentFromId(Integer.parseInt(tId));
                    activity.activityAppliesToTreatments.add(aT);
                }
                break;
            }
        }
    }

    public void createTreatments(){
        treatments = new ArrayList<>();
        List<String[]> treatmentsCSV = readCSVFile("treatments");
        if(treatmentsCSV!=null) {
            Iterator<String[]> iterator = treatmentsCSV.iterator();
            while (iterator.hasNext()) {
                String[] record = iterator.next();
                oTreatment treatment = new oTreatment();
                treatment.treatmentId = Integer.parseInt(record[0]);
                treatment.treatmentName = record[1];
                treatment.treatmentCategory = record[2];
                if (!record[3].isEmpty()) {
                    oCrop treatmentPrimaryCrop = getCropFromId(Integer.parseInt(record[3]));
                    treatment.primaryCrop = treatmentPrimaryCrop;
                } else {
                    treatment.primaryCrop = null;
                }
                if (!record[4].isEmpty()) {
                    oCrop treatmentIntercroppingCrop = getCropFromId(Integer.parseInt(record[4]));
                    treatment.intercroppingCrop = treatmentIntercroppingCrop;
                } else {
                    treatment.intercroppingCrop = null;
                }
                treatments.add(treatment);
            }
        }
    }

    public void createLog(){
        log = new ArrayList<>();
        String logString = readFromFile("log");
        if(!logString.isEmpty()) {
            String[] logItems = logString.split("\\|");
            for(int i=0;i<logItems.length;i++) {
                String[] logItemParts = logItems[i].split(";");
                oLog tLog = new oLog();
                tLog.logFieldId = Integer.parseInt(logItemParts[0]);
                tLog.logPlotNumber = Integer.parseInt(logItemParts[1]);
                tLog.logUserId = Integer.parseInt(logItemParts[2]);
                tLog.logCropId = Integer.parseInt(logItemParts[3]);
                tLog.logTreatmentId = Integer.parseInt(logItemParts[4]);
                tLog.logMeasurementId = Integer.parseInt(logItemParts[5]);
                tLog.logActivityId = Integer.parseInt(logItemParts[6]);
                tLog.logDate = stringToDate(logItemParts[7]);
                tLog.logNumberValue = Float.parseFloat(logItemParts[8]);
                tLog.logTextValue = logItemParts[9];
                tLog.loglabourTime = Integer.parseInt(logItemParts[10]);
                tLog.logCost = Float.parseFloat(logItemParts[11]);
                tLog.logComments = logItemParts[12];
                log.add(tLog);
            }
        }
    }

    public void sortLog(){
        boolean sort=true;
        oLog tempLog1;
        oLog tempLog2;

        while(sort){
            sort=false;
            for(int i=0;i<log.size()-1;i++){
                tempLog1=log.get(i);
                tempLog2=log.get(i+1);
                if(tempLog1.logDate.after(tempLog2.logDate)){
                    log.remove(i);
                    log.add(i,tempLog2);
                    log.remove(i+1);
                    log.add(i+1,tempLog1);
                    sort=true;
                }
            }
        }
    }

    public oCrop getCropFromId(int id){
        oCrop ret=null;
        Iterator<oCrop> iterator = crops.iterator();
        while(iterator.hasNext()){
            oCrop c = iterator.next();
            if(c.cropId==id){
                ret=c;
                break;
            }
        }
        return ret;
    }

    public oTreatment getTreatmentFromId(int id){
        oTreatment ret=null;
        Iterator<oTreatment> iterator = treatments.iterator();
        while(iterator.hasNext()){
            oTreatment t = iterator.next();
            if(t.treatmentId==id){
                ret=t;
                break;
            }
        }
        return ret;
    }

    public void createFields(){
        fields=new ArrayList<>();
        List<String[]> fieldsCSV = readCSVFile("fields");
        if(fieldsCSV!=null) {
            Iterator<String[]> iterator = fieldsCSV.iterator();
            while (iterator.hasNext()) {
                String[] record = iterator.next();
                oField field = new oField();
                field.fieldId = Integer.parseInt(record[0]);
                field.fieldName = record[4];
                field.fieldReplicationN = Integer.parseInt(record[5]);
                field.plots = parsePlots(record[8]);
                String[] grid = getFieldRowsColumns(record[8]);
                boolean[] fieldTreatments = getFieldTreatments(record[8]);
                field.hasIntercropping = fieldTreatments[0];
                field.hasSoilManagement = fieldTreatments[1];
                field.hasPestControl = fieldTreatments[2];
                field.rows = Integer.parseInt(grid[0]);
                field.columns = Integer.parseInt(grid[1]);
                fields.add(field);
            }
        }
    }

    public boolean[] getFieldTreatments(String d){
        String[] plotParts = d.split(";");
        String sub = plotParts[0].substring(3,10);
        String[] defParts = sub.split(",");
        boolean ret[] = {(!defParts[0].equals("0")),(defParts[1].equals("1")),(defParts[2].equals("1"))};
        return ret;
    }

    public oField getFieldFromId(int id){
        oField ret=null;
        Iterator<oField> iterator = fields.iterator();
        while (iterator.hasNext()) {
            oField field = iterator.next();
            if(field.fieldId==id){
                ret=field;
                break;
            }
        }
        return ret;
    }

    public String[] getFieldRowsColumns(String plotsString){
        String[] plotParts = plotsString.split(";");
        String sub = plotParts[1].substring(3,6);
        String[] ret = sub.split(",");
        return ret;
    }

    public ArrayList<oPlot> parsePlots(String plotsString){
        ArrayList<oPlot> ret=new ArrayList<>();
        String[] plotParts = plotsString.split(";");

        for(int i=2;i<plotParts.length;i++){
            String[] plotElements = parsePlotElement(plotParts[i]);
            oPlot plot = new oPlot();
            plot.plotNumber=i-1;
            oCrop plotPrimaryCrop = getCropFromId(Integer.parseInt(plotElements[0]));
            plot.primaryCrop=plotPrimaryCrop;
            if(Integer.parseInt(plotElements[1])==0){
                plot.intercroppingCrop=null;
            } else {
                oCrop plotIntercroppingCrop = getCropFromId(Integer.parseInt(plotElements[1]));
                plot.intercroppingCrop = plotIntercroppingCrop;
            }
            plot.hasSoilManagement=(Integer.parseInt(plotElements[2])==1);
            plot.hasPestControl=(Integer.parseInt(plotElements[3])==1);
            ret.add(plot);
        }
        return ret;
    }

    public String[] parsePlotElement(String plotString){
        String[] ret;
        String sub = plotString.substring(3,10);
        ret = sub.split(",");
        return ret;
    }

    public ArrayList<oActivity> getActivities(oPlot plot, oField field){
        ArrayList<oActivity> ret = new ArrayList<>();
        if(plot!=null) {
            Iterator<oActivity> iterator = activities.iterator();
            while (iterator.hasNext()) {
                oActivity activity = iterator.next();
                if (activity.activityAppliesToCrops.size() == 0 && activity.activityAppliesToTreatments.size() == 0) {
                    ret.add(activity);
                } else {
                    oCrop plotCrop = plot.primaryCrop;
                    Iterator<oCrop> iteratorCrop = activity.activityAppliesToCrops.iterator();
                    while (iteratorCrop.hasNext()) {
                        oCrop aC = iteratorCrop.next();
                        if (aC.cropId == plotCrop.cropId) {
                            if (!ret.contains(activity)) {
                                ret.add(activity);
                            }
                        }
                    }

                    Iterator<oTreatment> iteratorTreatment = activity.activityAppliesToTreatments.iterator();
                    while (iteratorTreatment.hasNext()) {
                        oTreatment aT = iteratorTreatment.next();
                        if ((plot.intercroppingCrop != null && aT.treatmentCategory.equals("Intercropping"))
                                || (plot.hasSoilManagement && aT.treatmentCategory.equals("Soil management"))
                                || (plot.hasPestControl && aT.treatmentCategory.equals("Pest control"))) {
                            if (!ret.contains(activity)) {
                                ret.add(activity);
                            }
                        }
                    }
                }
            }
        } else if(field!=null){
            Iterator<oActivity> iterator = activities.iterator();
            while (iterator.hasNext()) {
                oActivity activity = iterator.next();
                if (activity.activityAppliesToTreatments.size() == 0) {
                    ret.add(activity);
                } else {
                    Iterator<oTreatment> iteratorTreatment = activity.activityAppliesToTreatments.iterator();
                    while (iteratorTreatment.hasNext()) {
                        oTreatment aT = iteratorTreatment.next();
                        if((field.hasIntercropping && aT.treatmentCategory.equals("Intercropping"))
                            || (field.hasSoilManagement && aT.treatmentCategory.equals("Soil management"))
                            || (field.hasPestControl && aT.treatmentCategory.equals("Pest control"))) {
                            if(!ret.contains(activity)) {
                                ret.add(activity);
                            }
                        }
                    }
                }
            }
        } else {
            ret=activities;
        }
        return ret;
    }

    public int getDaysAgo(Date d){
        Calendar thisDate = Calendar.getInstance();
        thisDate.setTime(new Date());
        Calendar pastDate = Calendar.getInstance();
        pastDate.setTime(d);
        long msDiff = thisDate.getTimeInMillis() - pastDate.getTimeInMillis();
        float dayCount = (float) msDiff / (24 * 60 * 60 * 1000);
        int ret = (int)dayCount;
        return ret;
    }

    public Date stringToDate(String d){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        try {
            date = sdf.parse(d);
        } catch (ParseException e) {

        }
        return date;
    }

    public String dateToString(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(d);
    }

    public void addActivityToLog(int fieldId, int plotN, int userId, int activityId, String date, float numberValue, String comments){
        /*
        Toast.makeText(context,Integer.toString(fieldId)+","+Integer.toString(plotN)+","+Integer.toString(userId)+","+Integer.toString(activityId)
                +","+date+","+Float.toString(numberValue)+","+comments,Toast.LENGTH_SHORT).show();
                */
        updateActivityDaysAgo(activityId, plotN, fieldId, date);
        createLog();
        oLog newEntry = new oLog();
        newEntry.logFieldId = fieldId;
        newEntry.logPlotNumber = plotN;
        newEntry.logUserId = userId;
        newEntry.logActivityId = activityId;
        newEntry.logDate = stringToDate(date);
        newEntry.logNumberValue = numberValue;
        if(!comments.isEmpty()){
            comments = comments.replaceAll("\\;\\|"," ");
        }
        newEntry.logComments = comments;
        log.add(newEntry);
        sortLog();
        writeLog();
    }

    public void updateActivityDaysAgo(int id, int pN, int fId, String d){
        boolean acFound=false;
        Iterator<oActivityCalendar> iteratorAC = activitiesCalendar.iterator();
        while (iteratorAC.hasNext()) {
            oActivityCalendar aC = iteratorAC.next();
            if(id==aC.activityId && pN==aC.plotN && fId==aC.fieldId && d.equals(aC.date)){
                acFound=true;
                break;
            }
        }
        if(!acFound){
            oActivityCalendar aC = new oActivityCalendar();
            aC.activityId=id;
            aC.plotN=pN;
            aC.fieldId=fId;
            aC.date=d;
            activitiesCalendar.add(aC);
        }
        writeActivitiesCalendarFile();
    }

    public String getActivityDaysAgo(int activityId, int plotN, int fieldId){
        String ret="-1";
        Iterator<oActivityCalendar> iteratorAC = activitiesCalendar.iterator();
        while (iteratorAC.hasNext()) {
            oActivityCalendar aC = iteratorAC.next();
            if(activityId==aC.activityId && plotN==aC.plotN && fieldId==aC.fieldId){
                int daysAgo = getDaysAgo(stringToDate(aC.date));
                if(daysAgo>15){
                    ret=aC.date;
                } else {
                    ret=Integer.toString(daysAgo);
                }
                break;
            }
        }
        return ret;
    }

    public oLog getActivityToday(int activityId, int plotN, int fieldId){
        oLog ret = new oLog();
        String today = dateToString(new Date());
        createLog();
        Iterator<oLog> iterator = log.iterator();
        while (iterator.hasNext()) {
            oLog l = iterator.next();
            if(dateToString(l.logDate).equals(today) && l.logActivityId==activityId && l.logPlotNumber==plotN && l.logFieldId==fieldId){
                ret=l;
                break;
            }
        }
        return ret;
    }

    public void writeActivitiesCalendarFile(){
        String data="";
        Iterator<oActivityCalendar> iteratorAC = activitiesCalendar.iterator();
        while (iteratorAC.hasNext()) {
            oActivityCalendar aC = iteratorAC.next();
            data+=Integer.toString(aC.activityId)+","+Integer.toString(aC.plotN)+","+Integer.toString(aC.fieldId)+","+aC.date+";";
        }
        writeToFile(data,"activities_calendar");
    }

    public List<String[]> readCSVFile(String filename){
        List<String[]> ret = null;

        File file = new File(context.getFilesDir(), filename);
        if(file.exists()) {
            try {
                FileReader r = new FileReader(file);
                CSVReader reader = new CSVReader(r, ',', '"');
                ret = reader.readAll();
                reader.close();
                r.close();
            } catch (IOException e) {

            }
        }

        return ret;
    }

    private void writeToFile(String data, String filename) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {

        }
    }

    private String readFromFile(String filename) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return ret;
    }

    private void writeLog(){
        String data="";
        Iterator<oLog> iterator = log.iterator();
        while (iterator.hasNext()) {
            oLog l = iterator.next();
            data+=Integer.toString(l.logFieldId)+";"+Integer.toString(l.logPlotNumber)+";"+Integer.toString(l.logUserId)+";"+Integer.toString(l.logCropId)
                    +";"+Integer.toString(l.logTreatmentId)+";"+Integer.toString(l.logMeasurementId)+";"+Integer.toString(l.logActivityId)
                    +";"+dateToString(l.logDate)+";"+Float.toString(l.logNumberValue)+";"+l.logTextValue+";"+Integer.toString(l.loglabourTime)
                    +";"+Float.toString(l.logCost)+";"+l.logComments+"|";
        }
        writeToFile(data,"log");
    }

}
