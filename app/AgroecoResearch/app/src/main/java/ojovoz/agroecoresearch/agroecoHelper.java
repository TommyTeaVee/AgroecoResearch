package ojovoz.agroecoresearch;

import android.content.Context;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by Eugenio on 02/04/2017.
 */
public class agroecoHelper {

    private Context context;

    agroecoHelper(Context context){
        this.context=context;
    }

    public ArrayList<oField> getFields(){
        ArrayList<oField> ret=new ArrayList<oField>();
        List<String[]> fields = readFile("fields");

        Iterator<String[]> iterator = fields.iterator();
        while (iterator.hasNext()){
            String[] record = iterator.next();
            oField field = new oField();
            field.fieldId=Integer.parseInt(record[0]);
            field.fieldName=record[4];
            field.fieldReplicationN=Integer.parseInt(record[5]);
            ret.add(field);
        }

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
}
