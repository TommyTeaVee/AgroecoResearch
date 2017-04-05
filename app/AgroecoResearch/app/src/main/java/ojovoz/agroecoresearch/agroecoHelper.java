package ojovoz.agroecoresearch;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
            //TODO: sort log by date
        }
    }

    public void createCrops(){
        crops = new ArrayList<>();
        List<String[]> cropsCSV = readFile("crops");
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
        List<String[]> activitiesCSV = readFile("activities");
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

            List<String[]> activitiesAppliedCSV = readFile("activities_applied");
            if (activitiesAppliedCSV != null) {
                Iterator<String[]> iteratorApplied = activitiesAppliedCSV.iterator();
                while (iteratorApplied.hasNext()) {
                    String[] record = iteratorApplied.next();
                    addCropTreatmentToActivity(Integer.parseInt(record[0]), record[1], record[2]);
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
        List<String[]> treatmentsCSV = readFile("treatments");
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
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        log = new ArrayList<>();
        List<String[]> logCSV = readFile("log");
        if(logCSV!=null) {
            Iterator<String[]> iterator = logCSV.iterator();
            while (iterator.hasNext()) {
                String[] record = iterator.next();
                oLog tLog = new oLog();
                tLog.logFieldId = Integer.parseInt(record[0]);
                tLog.logPlotNumber = Integer.parseInt(record[1]);
                tLog.logUserId = Integer.parseInt(record[2]);
                tLog.logCropId = Integer.parseInt(record[3]);
                tLog.logTreatmentId = Integer.parseInt(record[4]);
                tLog.logMeasurementId = Integer.parseInt(record[5]);
                tLog.logActivityId = Integer.parseInt(record[6]);
                try {
                    tLog.logDate = df.parse(record[7]);
                } catch (ParseException e) {
                    tLog.logDate=null;
                }
                tLog.logNumberValue = Float.parseFloat(record[8]);
                tLog.logTextValue = record[9];
                tLog.loglabourTime = Integer.parseInt(record[10]);
                tLog.logCost = Float.parseFloat(record[11]);
                tLog.logComments = record[12];
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
        List<String[]> fieldsCSV = readFile("fields");
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
                field.rows = Integer.parseInt(grid[0]);
                field.columns = Integer.parseInt(grid[1]);
                fields.add(field);
            }
        }
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

    public ArrayList<oActivity> getActivities(){
        //TODO: add filters. Activity is returned only if: 1) has no associations or 2) is associated with relevant crop and/or treatment
        //TODO: check log for latest update to activity, add legend: "Last: n days ago" (or "Never", or "Today")
        return activities;
    }

    public List<String[]> readFile(String filename){
        List<String[]> ret = null;

        File file = new File(context.getFilesDir(), filename);
        if(file.exists()) {
            try {
                FileReader r = new FileReader(file);
                CSVReader reader = new CSVReader(r, ',', '"');
                ret = reader.readAll();
                reader.close();
            } catch (IOException e) {

            }
        }

        return ret;
    }
}
