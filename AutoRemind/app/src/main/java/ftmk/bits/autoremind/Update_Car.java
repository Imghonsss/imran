package ftmk.bits.autoremind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import java.util.Calendar;

import ftmk.bits.autoremind.databinding.ActivityRegisterCarBinding;
import ftmk.bits.autoremind.databinding.ActivityUpdateCarBinding;

public class Update_Car extends AppCompatActivity {

    ActivityUpdateCarBinding binding;
    Carsdetails carsdetails;
    DatabaseReference reff;
    private DatePickerDialog datePicker1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car);

        binding = ActivityUpdateCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.editTextTservicedate2.setOnClickListener(new View.OnClickListener() {
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
                datePicker1 = new DatePickerDialog(Update_Car.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                binding.editTextTservicedate2.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                // set maximum date to be selected as today
                datePicker1.getDatePicker().setMinDate(cldr.getTimeInMillis());

                datePicker1.show();
            }
        });

        Query querychecker = FirebaseDatabase.getInstance().getReference("CarsDetails").orderByChild("platenumber").equalTo(Car.getget);
        querychecker.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Carsdetails carsdetails = dataSnapshot.getValue(Carsdetails.class);
                    binding.editTextplateumber2.setText(carsdetails.getPlatenumber());
                    if (carsdetails.getBrand().equals("Proton")){binding.spinnerBrand2.setSelection(0);}
                    else if (carsdetails.getBrand().equals("Peroduo")){binding.spinnerBrand2.setSelection(1);}
                    else if (carsdetails.getBrand().equals("Hyundai")){binding.spinnerBrand2.setSelection(2);}
                    else if (carsdetails.getBrand().equals("BMW")){binding.spinnerBrand2.setSelection(3);}
                    if (carsdetails.getModel().equals("Sedan")){binding.spinnerBrand3.setSelection(0);}
                    else if (carsdetails.getModel().equals("SUV")){binding.spinnerBrand3.setSelection(1);}
                    else if (carsdetails.getModel().equals("MPV")){binding.spinnerBrand3.setSelection(2);}
                    else if (carsdetails.getModel().equals("Compact")){binding.spinnerBrand3.setSelection(3);}
                    if (carsdetails.getCc().equals("1.0 L")){binding.spinnerBrand4.setSelection(0);}
                    else if (carsdetails.getCc().equals("1.5 L")){binding.spinnerBrand4.setSelection(1);}
                    else if (carsdetails.getCc().equals("2.0 L")){binding.spinnerBrand4.setSelection(2);}
                    else if (carsdetails.getCc().equals("2.5 L")){binding.spinnerBrand4.setSelection(3);}
                    binding.editTextTservicedate2.setText(carsdetails.getServiceDate());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fn_back_car(View view) {
        Intent intent = new Intent(this, Car.class);
        startActivity(intent);
        finish();
    }

    public void fn_update(View view) {
        carsdetails = new Carsdetails();
        reff= FirebaseDatabase.getInstance().getReference().child("CarsDetails");
        carsdetails.setPlatenumber(binding.editTextplateumber2.getText().toString());
        carsdetails.setBrand(binding.spinnerBrand2.getSelectedItem().toString());
        carsdetails.setModel(binding.spinnerBrand3.getSelectedItem().toString());
        carsdetails.setCc(binding.spinnerBrand4.getSelectedItem().toString());
        carsdetails.setServiceDate(binding.editTextTservicedate2.getText().toString());

        if (binding.editTextplateumber2.getText().toString().equals("") || binding.editTextTservicedate2.getText().toString().equals("")){
            Toast.makeText(this, "Fill in all the blanks", Toast.LENGTH_SHORT).show();
        }
        else {
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reff.child(Car.getgetget).setValue(carsdetails);
                    Toast.makeText(Update_Car.this, "Update successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Update_Car.this, Car.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void fn_delete(View view) {

        Query querychecker = FirebaseDatabase.getInstance().getReference("CarsDetails").orderByChild("platenumber").equalTo(Car.getget);

        AlertDialog.Builder builder = new AlertDialog.Builder(Update_Car.this);

        builder.setTitle("Delete?")
                .setMessage("Are you sure?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        querychecker.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                    Toast.makeText(Update_Car.this, "Deleted!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Update_Car.this, Car.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                })
                .setNegativeButton("Cancel",null).setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();

    }
}