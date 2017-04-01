package ojovoz.agroecoresearch;

/**
 * Created by Eugenio on 31/03/2017.
 */
public class oMeasurement {
    public int measurementId;
    public String measurementName;
    public String measurementCategory;
    public String measurementSubCategory;
    public int measurementType;
    public String measurementCategories;
    public float measurementMin;
    public float measurementMax;
    public String measurementUnits;
    public int measurementPeriodicity;

    oMeasurement(int id, String name, String category, String subcategory, int type, String categories, float min, float max, String units, int periodicity){
        measurementId=id;
        measurementName=name;
        measurementCategory=category;
        measurementSubCategory=subcategory;
        measurementType=type;
        measurementCategories=categories;
        measurementMin=min;
        measurementMax=max;
        measurementUnits=units;
        measurementPeriodicity=periodicity;
    }
}
