package ojovoz.agroecoresearch;

import java.util.ArrayList;

/**
 * Created by Eugenio on 31/03/2017.
 */
public class oPlot {
    public int row;
    public int column;
    public oCrop primaryCrop;
    public oCrop intercroppingCrop;
    public ArrayList<oTreatment> treatments;

    oPlot(int r, int c, oCrop c1, oCrop l, ArrayList<oTreatment> t){
        row=r;
        column=c;
        primaryCrop=c1;
        intercroppingCrop=l;
        treatments=t;
    }
}
