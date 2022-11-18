package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Test;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.PatientViewModel;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.TestViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class TestActivity extends AppCompatActivity {

    //UI Related
    TextInputEditText editTextBpl;
    TextInputEditText editTextBph;
    TextInputEditText editTextTemp;
    TextInputEditText editTextCovid;
    TextInputEditText editTextHiv;
    Button saveTestButton;

    //Test Attributes
    String bpl;
    String bph;
    String temp;
    String hiv;
    String covid;

    //Database related
    TestViewModel testViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //Initialize UI
        editTextBpl = findViewById(R.id.addTest_bpl);
        editTextBph = findViewById(R.id.addTest_bph);
        editTextTemp = findViewById(R.id.addTest_temp);
        editTextCovid = findViewById(R.id.addTest_covid);
        editTextHiv = findViewById(R.id.addTest_hiv);
        saveTestButton = findViewById(R.id.save_test_btn);

        //Initialize view model for test
        testViewModel = new ViewModelProvider(this).get(TestViewModel.class);

        //Listeners
        saveTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get value from edit text
                if (editTextBpl.getText().toString().length() != 0 &&
                        editTextBph.getText().toString().length() != 0 &&
                        editTextTemp.getText().toString().length() != 0 &&
                        editTextCovid.getText().toString().length() != 0 &&
                        editTextHiv.getText().toString().length() != 0) {

                    bpl = editTextBpl.getText().toString();
                    bph = editTextBph.getText().toString();
                    temp = editTextTemp.getText().toString();
                    hiv = editTextHiv.getText().toString();
                    covid = editTextCovid.getText().toString();

                    Test test = new Test(
                            1,
                            2222,
                            bpl,
                            bph,
                            temp,
                            hiv,
                            covid
                    );

                    new AddTestTask().execute(test);

                }else{
                    Toast.makeText(TestActivity.this, "Test fields cant be left empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class AddTestTask extends AsyncTask<Test, Void, Void> {

        @Override
        protected Void doInBackground(Test... tests) {
            Test testToBeAdded = tests[0];
            if(testViewModel != null && testToBeAdded != null) {
                testViewModel.insert(testToBeAdded);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }
}