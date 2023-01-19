package ftmk.bits.autoremind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import ftmk.bits.autoremind.databinding.ActivityEditLicenseBinding;

public class Edit_License extends AppCompatActivity {

    ActivityEditLicenseBinding binding;
    private DatePickerDialog datePicker1;
    DatabaseReference reff;
    Licensedetails licensedetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_license);

        binding = ActivityEditLicenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        licensedetails = new Licensedetails();

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
                datePicker1 = new DatePickerDialog(Edit_License.this,
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

        /////////////load data
        reff = FirebaseDatabase.getInstance().getReference().child("LicenseDetails");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    licensedetails = dataSnapshot.getValue(Licensedetails.class);
                }
                binding.editTextOwnerName.setText(licensedetails.getOwnerName());
                binding.editTextLicenseID.setText(licensedetails.getLicenseID());
                if (licensedetails.getClassType().equals("D (Manual Car)")){binding.spinnerClass.setSelection(0);}
                else if (licensedetails.getClassType().equals("DA (Auto Car)")){binding.spinnerClass.setSelection(1);}
                binding.editTextExpiryDate.setText(licensedetails.getExpiryDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fn_back_license(View view) {
        Intent intent = new Intent(this, License.class);
        startActivity(intent);
        finish();
    }

    public void fn_backedit_license(View view) {

        if (binding.editTextOwnerName.getText().toString().equals("") || binding.editTextLicenseID.getText().toString().equals("") || binding.editTextExpiryDate.getText().toString().equals("")){
            Toast.makeText(this, "Fill in all the blanks", Toast.LENGTH_SHORT).show();
        }
        else {
            //update database
            licensedetails.setOwnerName(binding.editTextOwnerName.getText().toString());
            licensedetails.setLicenseID(binding.editTextLicenseID.getText().toString());
            licensedetails.setClassType(binding.spinnerClass.getSelectedItem().toString());
            licensedetails.setExpiryDate(binding.editTextExpiryDate.getText().toString());
            reff = FirebaseDatabase.getInstance().getReference().child("LicenseDetails");
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reff.child("License").setValue(licensedetails);
                    Toast.makeText(Edit_License.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Edit_License.this, License.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}