package ftmk.bits.autoremind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ftmk.bits.autoremind.databinding.ActivityHomeBinding;
import ftmk.bits.autoremind.databinding.ActivityLicenseBinding;

public class License extends AppCompatActivity {

    ActivityLicenseBinding binding;
    DatabaseReference reff;
    Licensedetails licensedetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        binding = ActivityLicenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        licensedetails = new Licensedetails();
        //load data from firebase
        reff = FirebaseDatabase.getInstance().getReference().child("LicenseDetails");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    licensedetails = dataSnapshot.getValue(Licensedetails.class);
                }
                binding.textViewOwener.setText(licensedetails.getOwnerName());
                binding.textView14.setText(licensedetails.getLicenseID());
                binding.textView16.setText(licensedetails.getClassType());
                binding.textView22.setText(licensedetails.getExpiryDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fn_gotocar(View view) {
        Intent intent = new Intent(this, Car.class);
        startActivity(intent);
        finish();
    }

    public void fn_gotohome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }

    public void fn_gooeditlicense(View view) {
        Intent intent = new Intent(this, Edit_License.class);
        startActivity(intent);
        finish();
    }

    public void fn_gotoinsurance(View view) {
        Intent intent = new Intent(this, Insurance.class);
        startActivity(intent);
        finish();
    }

    public void fn_gotohistory(View view) {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
        finish();
    }
}