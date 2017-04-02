package ojovoz.agroecoresearch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugenio on 02/04/2017.
 */
public class chooseFieldPlot extends AppCompatActivity {

    public int userId;
    public int userRole;
    public String task;

    public agroecoHelper agroHelper;

    ArrayList<oField> fields;
    CharSequence fieldsArray[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_field_plot);
        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");

        agroHelper = new agroecoHelper(this);
        initializeVariables();

        Button fieldListView = (Button) findViewById(R.id.chooseFieldButton);
        fieldListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.chooseFieldButton:
                        showSelectFieldsDialog();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void initializeVariables(){
        fields = agroHelper.getFields();

        ArrayList<CharSequence> tf = new ArrayList<>();
        for(int i=0; i<fields.size(); i++){
            tf.add(fields.get(i).fieldName + " replication " + Integer.toString(fields.get(i).fieldReplicationN));
        }

        fieldsArray=tf.toArray(new CharSequence[tf.size()]);
    }

    public void showSelectFieldsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ListAdapter adapter = new ArrayAdapter<>(this,R.layout.checked_list_template,fieldsArray);
        builder.setSingleChoiceItems(adapter,-1,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
