package ojovoz.agroecoresearch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
public class enterTreatmentInput extends AppCompatActivity {

    public int userId;
    public int userRole;
    public String task;
    public String subTask;
    public int inputLogId;
    public int fieldId;
    public String plots;
    public int treatmentId;
    public String inputTitle;
    public String shortTitle;
    public String update;

    public Date treatmentInputDate;

    public boolean changes=false;
    public int exitAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_treatment_input);

        userId = getIntent().getExtras().getInt("userId");
        userRole = getIntent().getExtras().getInt("userRole");
        task = getIntent().getExtras().getString("task");
        subTask = getIntent().getExtras().getString("subTask");
        fieldId = getIntent().getExtras().getInt("field");
        plots = getIntent().getExtras().getString("plots");
        treatmentId = getIntent().getExtras().getInt("taskId");
        inputTitle = getIntent().getExtras().getString("title");
        shortTitle = getIntent().getExtras().getString("shortTitle");
        update = getIntent().getExtras().getString("update");

        TextView tt = (TextView)findViewById(R.id.treatmentInputTitle);
        tt.setText(inputTitle);

        EditText quantity = (EditText) findViewById(R.id.treatmentQuantity);
        EditText cost = (EditText) findViewById(R.id.treatmentCost);
        EditText material = (EditText) findViewById(R.id.treatmentMaterial);
        EditText method = (EditText) findViewById(R.id.treatmentPreparationMethod);
        EditText comments = (EditText) findViewById(R.id.inputComments);

        if(update.equals("treatment")){
            inputLogId = getIntent().getExtras().getInt("inputLogId");

            Button ob = (Button)findViewById(R.id.okButton);
            ob.setText(R.string.editButtonText);

            Button db = (Button)findViewById(R.id.dateButton);
            db.setText(getIntent().getExtras().getString("treatmentInputDate"));
            treatmentInputDate = stringToDate(getIntent().getExtras().getString("treatmentInputDate"));

            quantity.setText(Float.toString(getIntent().getExtras().getFloat("treatmentInputQuantity")));
            cost.setText(getIntent().getExtras().getString("treatmentInputCost"));
            material.setText(getIntent().getExtras().getString("treatmentInputMaterial"));
            method.setText(getIntent().getExtras().getString("treatmentInputMethod"));
            comments.setText(getIntent().getExtras().getString("treatmentInputComments"));
        } else {
            treatmentInputDate = new Date();
        }

        comments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changes=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changes=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changes=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        material.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changes=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        method.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changes=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button cb = (Button)findViewById(R.id.dateButton);
        cb.setText(dateToString(treatmentInputDate));
        cb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                displayDatePicker();
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(changes) {
            exitAction = 0;
            confirmExit();
        } else {
            goBack();
        }
    }

    public void confirmExit(){
        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(this);
        logoutDialog.setTitle(R.string.logoutAlertTitle);
        logoutDialog.setMessage(R.string.exitAlertString);
        logoutDialog.setNegativeButton(R.string.cancelButtonText,null);
        logoutDialog.setPositiveButton(R.string.okButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (exitAction){
                    case 0:
                        goBack();
                        break;
                    case 1:
                        goToDataManager();
                        break;
                    case 2:
                        goToMainMenu();
                        break;
                }
            }
        });
        logoutDialog.create();
        logoutDialog.show();
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
                if(changes) {
                    exitAction = 1;
                    confirmExit();
                } else {
                    goToDataManager();
                }
                break;
            case 1:
                if(changes) {
                    exitAction = 2;
                    confirmExit();
                } else {
                    goToMainMenu();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public void goBack(){
        final Context context = this;
        if(update.equals("")) {
            Intent i = new Intent(context, chooseFieldPlot.class);
            i.putExtra("userId", userId);
            i.putExtra("userRole", userRole);
            i.putExtra("task", task);
            i.putExtra("treatmentId", treatmentId);
            i.putExtra("cropId", -1);
            i.putExtra("field", fieldId);
            i.putExtra("plots", plots);
            i.putExtra("newTreatmentInput", false);
            i.putExtra("title", shortTitle);
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
        calActivity.setTime(treatmentInputDate);
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

                treatmentInputDate = calendar.getTime();

                Button cb = (Button)findViewById(R.id.dateButton);
                cb.setText(dateToString(treatmentInputDate));
                dialog.dismiss();
                changes=true;
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

    public void registerTreatment(View v) {
        EditText quantity = (EditText) findViewById(R.id.treatmentQuantity);
        String quantityValue = String.valueOf(quantity.getText());
        if (isNumeric(quantityValue)) {
            float quantityNumber = Float.parseFloat(quantityValue);

            EditText cost = (EditText) findViewById(R.id.treatmentCost);
            String costValue = String.valueOf(cost.getText());
            if (isNumeric(costValue) || costValue.isEmpty()) {

                String costNumber = costValue;

                EditText material = (EditText) findViewById(R.id.treatmentMaterial);
                String materialText = String.valueOf(material.getText());
                if (!materialText.isEmpty()) {
                    materialText = materialText.replaceAll(";", " ");
                    materialText = materialText.replaceAll("\\|", " ");
                    materialText = materialText.replaceAll("\\*", " ");

                    EditText method = (EditText) findViewById(R.id.treatmentPreparationMethod);
                    String methodText = String.valueOf(method.getText());
                    if (!methodText.isEmpty()) {
                        methodText = methodText.replaceAll(";", " ");
                        methodText = methodText.replaceAll("\\|", " ");
                        methodText = methodText.replaceAll("\\*", " ");
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
                        Intent i = new Intent(this, inputChooser.class);
                        i.putExtra("userId", userId);
                        i.putExtra("userRole", userRole);
                        i.putExtra("task", task);
                        i.putExtra("field", fieldId);
                        i.putExtra("plots", plots);
                        i.putExtra("title", shortTitle);
                        i.putExtra("newTreatmentInput", true);
                        i.putExtra("treatmentId", treatmentId);
                        i.putExtra("cropId",-1);
                        i.putExtra("treatmentInputDate", dateToString(treatmentInputDate));
                        i.putExtra("treatmentInputMaterial", materialText);
                        i.putExtra("treatmentInputQuantity", quantityNumber);
                        i.putExtra("treatmentInputMethod", methodText);
                        i.putExtra("treatmentInputCost", costNumber);
                        i.putExtra("treatmentInputComments", commentsText);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(this, "Input edited successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, manageData.class);
                        i.putExtra("userId", userId);
                        i.putExtra("userRole", userRole);
                        i.putExtra("task", task);
                        i.putExtra("inputLogId", inputLogId);
                        i.putExtra("update", "treatmentInput");
                        i.putExtra("treatment", treatmentId);
                        i.putExtra("treatmentInputDate", dateToString(treatmentInputDate));
                        i.putExtra("treatmentInputMaterial", materialText);
                        i.putExtra("treatmentInputQuantity", quantityNumber);
                        i.putExtra("treatmentInputMethod", methodText);
                        i.putExtra("treatmentInputCost", costNumber);
                        i.putExtra("treatmentInputComments", commentsText);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Toast.makeText(this, R.string.enterValidTextText, Toast.LENGTH_SHORT).show();
                    material.requestFocus();
                }
            } else {
                Toast.makeText(this, R.string.enterValidNumberText, Toast.LENGTH_SHORT).show();
                cost.requestFocus();
            }
        } else {
            Toast.makeText(this, R.string.enterValidNumberText, Toast.LENGTH_SHORT).show();
            quantity.requestFocus();
        }
    }
}
