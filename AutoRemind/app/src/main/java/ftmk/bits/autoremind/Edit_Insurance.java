package ftmk.bits.autoremind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import ftmk.bits.autoremind.databinding.ActivityEditInsuranceBinding;
import ftmk.bits.autoremind.databinding.ActivityEditLicenseBinding;

public class Edit_Insurance extends AppCompatActivity {

    ActivityEditInsuranceBinding binding;
    DatabaseReference reff;
    ArrayList<InsuranceDetails> list;
    MyAdapter_insurance myAdapter;
    ProgressDialog progressDialog;
    String the_id;
    private DatePickerDialog datePicker1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_insurance);

        binding = ActivityEditInsuranceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        the_id = intent.getStringExtra("id");
        Toast.makeText(this, the_id, Toast.LENGTH_SHORT).show();

        ///picker
        binding.editTextExpiryDate.setOnClickListener(new View.OnClickListener() {
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
                datePicker1 = new DatePickerDialog(Edit_Insurance.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                binding.editTextExpiryDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                datePicker1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePicker1.show();
            }
        });

        ////load data
        binding.editTextPlate.setText(Insurance.getplatee);
        Query query = FirebaseDatabase.getInstance().getReference().child("InsuranceDetails").orderByChild("id").equalTo(the_id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    InsuranceDetails insuranceDetails = dataSnapshot.getValue(InsuranceDetails.class);
                    binding.editTextPlate.setText(insuranceDetails.getPlateNumber());
                    binding.editTextLicenseID.setText(insuranceDetails.getId());
                    if (insuranceDetails.getCompany().equals("Allianz")){binding.spinnerClass.setSelection(0);}
                    else if (insuranceDetails.getCompany().equals("Manulife")){binding.spinnerClass.setSelection(1);}
                    else if (insuranceDetails.getCompany().equals("Prudential")){binding.spinnerClass.setSelection(2);}
                    else if (insuranceDetails.getCompany().equals("Zurich")){binding.spinnerClass.setSelection(3);}
                    else if (insuranceDetails.getCompany().equals("Etiqa")){binding.spinnerClass.setSelection(4);}
                    binding.editTextExpiryDate.setText(insuranceDetails.getExpiryDate());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fn_back_insurance(View view) {
        Intent intent = new Intent(this, Insurance.class);
        startActivity(intent);
        finish();
    }

    public void fn_update(View view) {
        ///update
        if (binding.editTextPlate.getText().toString().equals("")||
                binding.editTextLicenseID.getText().toString().equals("")||
                binding.editTextExpiryDate.getText().toString().equals(""))
        {
            Toast.makeText(this, "Fill in all the blanks", Toast.LENGTH_SHORT).show();
        }
        else {
            reff = FirebaseDatabase.getInstance().getReference().child("InsuranceDetails");
            InsuranceDetails insuranceDetails = new InsuranceDetails();
            insuranceDetails.setPlateNumber(binding.editTextPlate.getText().toString());
            insuranceDetails.setId(binding.editTextLicenseID.getText().toString());
            insuranceDetails.setCompany(binding.spinnerClass.getSelectedItem().toString());
            insuranceDetails.setExpiryDate(binding.editTextExpiryDate.getText().toString());
            reff.child(insuranceDetails.getId()).setValue(insuranceDetails);

            Intent intent = new Intent(this, Insurance.class);
            startActivity(intent);
            finish();
        }
    }
}