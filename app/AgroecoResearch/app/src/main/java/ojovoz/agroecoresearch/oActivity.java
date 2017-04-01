package ojovoz.agroecoresearch;

/**
 * Created by Eugenio on 31/03/2017.
 */
public class oActivity {
    public int activityId;
    public String activityName;
    public String activityCategory;
    public int activityPeriodicity;

    oActivity(int id, String name, String category, int periodicity){
        activityId=id;
        activityName=name;
        activityCategory=category;
        activityPeriodicity=periodicity;
    }
}
