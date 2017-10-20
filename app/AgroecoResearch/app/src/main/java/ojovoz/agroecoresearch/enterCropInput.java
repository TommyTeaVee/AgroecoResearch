package ojovoz.agroecoresearch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
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
    public String subTask;
    public int inputLogId;
    public int fieldId;
    public String plots;
    public int cropId;
    public String inputTitle;
    public String shortTitle;
    public String update;

    public Date cropInputDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_crop_input);

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");
        subTask = getIntent().getExtras().getString("subTask");
        fieldId = getIntent().getExtras().getInt("field");
        plots = getIntent().getExtras().getString("plots");
        cropId = getIntent().getExtras().getInt("taskId");
        inputTitle = getIntent().getExtras().getString("title");
        shortTitle = getIntent().getExtras().getString("shortTitle");
        update = getIntent().getExtras().getString("update");

        TextView tt = (TextView)findViewById(R.id.cropInputTitle);
        tt.setText(inputTitle);

        if(update.equals("crop")){
            inputLogId = getIntent().getExtras().getInt("inputLogId");

            Button ob = (Button)findViewById(R.id.okButton);
            ob.setText(R.string.editButtonText);

            Button db = (Button)findViewById(R.id.dateButton);
            db.setText(getIntent().getExtras().getString("cropInputDate"));
            cropInputDate = stringToDate(getIntent().getExtras().getString("cropInputDate"));

            EditText age = (EditText) findViewById(R.id.cropAge);
            age.setText(getIntent().getExtras().getString("cropInputAge"));

            EditText quantity = (EditText) findViewById(R.id.cropQuantity);
            quantity.setText(Float.toString(getIntent().getExtras().getFloat("cropInputQuantity")));

            EditText cost = (EditText) findViewById(R.id.cropCost);
            cost.setText(getIntent().getExtras().getString("cropInputCost"));

            EditText origin = (EditText) findViewById(R.id.cropOrigin);
            origin.setText(getIntent().getExtras().getString("cropInputOrigin"));

            EditText comments = (EditText) findViewById(R.id.inputComments);
            comments.setText(getIntent().getExtras().getString("cropInputComments"));
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
            Intent i = new Intent(context, chooseFieldPlot.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("task", task);
            i.putExtra("cropId", cropId);
            i.putExtra("treatmentId", -1);
            i.putExtra("field", fieldId);
            i.putExtra("plots", plots);
            i.putExtra("newCropInput", false);
            i.putExtra("title",shortTitle);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(context, manageData.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("update","");
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0, R.string.opManageData);
        menu.add(1, 1, 1, R.string.opMainMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                goToDataManager();
                break;
            case 1:
                goToMainMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToDataManager(){
        final Context context = this;
        Intent i = new Intent(context, manageData.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        i.putExtra("update","");
        startActivity(i);
        finish();
    }

    public void goToMainMenu(){
        final Context context = this;
        Intent i = new Intent(context, mainMenu.class);
        i.putExtra("userId",userId);
        i.putExtra("userRole",userRole);
        startActivity(i);
        finish();
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

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public void registerCrop(View v) {
        EditText age = (EditText) findViewById(R.id.cropAge);
        String ageValue = String.valueOf(age.getText());
        if (isNumeric(ageValue) || ageValue.isEmpty()) {
            String ageNumber = ageValue;

            EditText quantity = (EditText) findViewById(R.id.cropQuantity);
            String quantityValue = String.valueOf(quantity.getText());
            if (isNumeric(quantityValue)) {

                float quantityNumber = Float.parseFloat(quantityValue);

                EditText cost = (EditText) findViewById(R.id.cropCost);
                String costValue = String.valueOf(cost.getText());
                if (isNumeric(costValue) || costValue.isEmpty()) {

                    String costNumber = costValue;

                    EditText origin = (EditText) findViewById(R.id.cropOrigin);
                    String originText = String.valueOf(origin.getText());

                    if (!originText.isEmpty()) {
                        originText = originText.replaceAll(";", " ");
                        originText = originText.replaceAll("\\|", " ");
                        originText = originText.replaceAll("\\*", " ");
                    }

                    EditText comments = (EditText) findViewById(R.id.inputComments);
                    String commentsText = String.valueOf(comments.getText());

                    if (!commentsText.isEmpty()) {
                        commentsText = commentsText.replaceAll(";", " ");
                        commentsText = commentsText.replaceAll("\\|", " ");
                        commentsText = commentsText.replaceAll("\\*", " ");
                    }

                    if (update.equals("")) {
                        Toast.makeText(this, "Input saved successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, chooseFieldPlot.class);
                        i.putExtra("userId", userId);
                        i.putExtra("userRole", userRole);
                        i.putExtra("task", task);
                        i.putExtra("title", shortTitle);
                        i.putExtra("field", fieldId);
                        i.putExtra("plots", plots);
                        i.putExtra("newCropInput", true);
                        i.putExtra("cropId", cropId);
                        i.putExtra("treatmentId", -1);
                        i.putExtra("cropInputDate", dateToString(cropInputDate));
                        i.putExtra("cropInputAge", ageNumber);
                        i.putExtra("cropInputOrigin", originText);
                        i.putExtra("cropInputQuantity", quantityNumber);
                        i.putExtra("cropInputCost", costNumber);
                        i.putExtra("cropInputComments", commentsText);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(this, "Input edited successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, manageData.class);
                        i.putExtra("userId", userId);
                        i.putExtra("userRole", userRole);
                        i.putExtra("task", task);
                        i.putExtra("inputLogId", inputLogId);
                        i.putExtra("update", "cropInput");
                        i.putExtra("crop", cropId);
                        i.putExtra("cropInputDate", dateToString(cropInputDate));
                        i.putExtra("cropInputAge", ageNumber);
                        i.putExtra("cropInputOrigin", originText);
                        i.putExtra("cropInputQuantity", quantityNumber);
                        i.putExtra("cropInputCost", costNumber);
                        i.putExtra("cropInputComments", commentsText);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Toast.makeText(this, R.string.enterValidNumberText, Toast.LENGTH_SHORT).show();
                    cost.requestFocus();
                }
            } else {
                Toast.makeText(this, R.string.enterValidNumberText, Toast.LENGTH_SHORT).show();
                quantity.requestFocus();
            }
        } else {
            Toast.makeText(this, R.string.enterValidNumberText, Toast.LENGTH_SHORT).show();
            age.requestFocus();
        }
    }
}
