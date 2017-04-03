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
 * Created by Eugenio on 02/04/2017.
 */
public class agroecoHelper {

    private Context context;
    ArrayList<oCrop> crops;
    ArrayList<oTreatment> treatments;
    ArrayList<oField> fields;

    agroecoHelper(Context context){
        this.context=context;
        createCrops();
        createTreatments();
        createFields();
    }

    public void createCrops(){
        crops = new ArrayList<>();
        List<String[]> cropsCSV = readFile("crops");
        Iterator<String[]> iterator = cropsCSV.iterator();
        while (iterator.hasNext()){
            String[] record = iterator.next();
            oCrop crop = new oCrop();
            crop.cropId=Integer.parseInt(record[0]);
            crop.cropName=record[1];
            crop.cropVariety=record[2];
            crops.add(crop);
        }
    }

    public void createTreatments(){
        treatments = new ArrayList<>();
        List<String[]> treatmentsCSV = readFile("treatments");
        Iterator<String[]> iterator = treatmentsCSV.iterator();
        while (iterator.hasNext()) {
            String[] record = iterator.next();
            oTreatment treatment = new oTreatment();
            treatment.treatmentId=Integer.parseInt(record[0]);
            treatment.treatmentName=record[1];
            treatment.treatmentCategory=record[2];
            if(!record[3].isEmpty()){
                oCrop treatmentPrimaryCrop = getCropFromId(Integer.parseInt(record[3]));
                treatment.primaryCrop = treatmentPrimaryCrop;
            } else {
                treatment.primaryCrop = null;
            }
            if(!record[4].isEmpty()){
                oCrop treatmentIntercroppingCrop = getCropFromId(Integer.parseInt(record[4]));
                treatment.intercroppingCrop = treatmentIntercroppingCrop;
            } else {
                treatment.intercroppingCrop = null;
            }
            treatments.add(treatment);
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

    public void createFields(){
        fields=new ArrayList<>();
        List<String[]> fieldsCSV = readFile("fields");

        Iterator<String[]> iterator = fieldsCSV.iterator();
        while (iterator.hasNext()){
            String[] record = iterator.next();
            oField field = new oField();
            field.fieldId=Integer.parseInt(record[0]);
            field.fieldName=record[4];
            field.fieldReplicationN=Integer.parseInt(record[5]);
            field.plots=parsePlots(record[8]);
            String[] grid = getFieldRowsColumns(record[8]);
            field.rows=Integer.parseInt(grid[0]);
            field.columns=Integer.parseInt(grid[1]);
            fields.add(field);
        }
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

    public List<String[]> readFile(String filename){
        List<String[]> ret = null;

        File file = new File(context.getFilesDir(), filename);
        try {
            FileReader r = new FileReader(file);
            CSVReader reader = new CSVReader(r, ',', '"');
            ret = reader.readAll();
            reader.close();
        } catch (IOException e) {

        }

        return ret;
    }

    public ArrayList<oPlot> getPlotsFromFieldId(int id){
        oField field;
        ArrayList<oPlot> ret = null;
        Iterator<oField> iterator = fields.iterator();
        while (iterator.hasNext()) {
            field = iterator.next();
            if(field.fieldId==id){
                ret = field.plots;
                break;
            }
        }
        return ret;
    }
}
