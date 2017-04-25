package ojovoz.agroecoresearch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Eugenio on 25/04/2017.
 */
public class enterCropInput extends AppCompatActivity {

    public int userId;
    public int userRole;
    public String task;
    public int inputLogId;
    public int fieldId;
    public int plotN;
    public int cropId;
    public String inputTitle;
    public String update;

    public Date cropInputDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_crop_input);

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");
        fieldId = getIntent().getExtras().getInt("field");
        plotN = getIntent().getExtras().getInt("plot");
        cropId = getIntent().getExtras().getInt("cropId");
        inputTitle = getIntent().getExtras().getString("title");
        update = getIntent().getExtras().getString("update");

        TextView tt = (TextView)findViewById(R.id.cropInputTitle);
        tt.setText(inputTitle);

        if(update.equals("crop")){

        } else {
            cropInputDate = new Date();
        }

        Button cb = (Button)findViewById(R.id.dateButton);
        cb.setText(dateToString(cropInputDate));
        cb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                displayDatePicker();
            }
        });
    }

    @Override
    public void onBackPressed(){
        final Context context = this;
        if(update.equals("")) {
            Intent i = new Intent(context, inputChooser.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("task", task);
            i.putExtra("field", fieldId);
            i.putExtra("plot", plotN);
            i.putExtra("newCropInput", false);
            startActivity(i);
            finish();
        } else {

        }
    }

    public void displayDatePicker(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_datepicker);

        DatePicker dp = (DatePicker) dialog.findViewById(R.id.datePicker);
        Calendar calActivity = Calendar.getInstance();
        calActivity.setTime(cropInputDate);
        dp.init(calActivity.get(Calendar.YEAR), calActivity.get(Calendar.MONTH), calActivity.get(Calendar.DAY_OF_MONTH),null);

        Calendar calMax = Calendar.getInstance();
        calMax.setTime(new Date());

        dp.setMaxDate(calMax.getTimeInMillis());

        Button dialogButton = (Button) dialog.findViewById(R.id.okButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker dp = (DatePicker) dialog.findViewById(R.id.datePicker);
                int day = dp.getDayOfMonth();
                int month = dp.getMonth();
                int year =  dp.getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                cropInputDate = calendar.getTime();

                Button cb = (Button)findViewById(R.id.dateButton);
                cb.setText(dateToString(cropInputDate));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public String dateToString(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(d);
    }
}
