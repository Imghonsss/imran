package ftmk.bits.autoremind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import ftmk.bits.autoremind.databinding.ActivityHomeBinding;
import ftmk.bits.autoremind.databinding.ActivityRegisterCarBinding;

public class Register_Car extends AppCompatActivity {

    ActivityRegisterCarBinding binding;
    Carsdetails carsdetails;
    DatabaseReference reff;
    private DatePickerDialog datePicker1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_car);

        binding = ActivityRegisterCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.editTextTservicedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                int mHour = cldr.get(Calendar.HOUR_OF_DAY);
                int mMinute = cldr.get(Calendar.MINUTE);
                String strDay ="";
                // date picker dialog
                datePicker1 = new DatePickerDialog(Register_Car.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                binding.editTextTservicedate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                // set maximum date to be selected as today
                datePicker1.getDatePicker().setMinDate(cldr.getTimeInMillis());

                datePicker1.show();
            }
        });

        binding.spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (binding.spinnerBrand.getSelectedItem().toString().equals("Nissan"))
                {
                    Toast.makeText(Register_Car.this, "congrates", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    public void fn_back_car(View view) {
        Intent intent = new Intent(this, Car.class);
        startActivity(intent);
        finish();
    }

    public void fn_save_back(View view) {
        // insert data to firebase

        if (binding.editTextplateumber.getText().toString().equals("") || binding.editTextTservicedate.getText().toString().equals("")){
            Toast.makeText(this, "Fill in all the blanks", Toast.LENGTH_SHORT).show();
        }
        else {
            carsdetails = new Carsdetails();
            reff = FirebaseDatabase.getInstance().getReference().child("CarsDetails");
            carsdetails.setPlatenumber(binding.editTextplateumber.getText().toString());
            carsdetails.setBrand(binding.spinnerBrand.getSelectedItem().toString());
            carsdetails.setModel(binding.spinnerBrand5.getSelectedItem().toString());
            carsdetails.setCc(binding.spinnerBrand6.getSelectedItem().toString());
            carsdetails.setServiceDate(binding.editTextTservicedate.getText().toString());

            Query querychecker = FirebaseDatabase.getInstance().getReference("CarsDetails").orderByChild("platenumber").equalTo(binding.editTextplateumber.getText().toString());
            querychecker.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        Toast.makeText(Register_Car.this, "Duplicated Plate No", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        reff.push().setValue(carsdetails);
                        Toast.makeText(Register_Car.this, "Successful added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register_Car.this, Car.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}