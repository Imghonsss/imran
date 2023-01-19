package ftmk.bits.autoremind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ftmk.bits.autoremind.databinding.ActivityHistoryBinding;
import ftmk.bits.autoremind.databinding.ActivityReminderBinding;

public class History extends AppCompatActivity {

    ActivityHistoryBinding binding;
    DatabaseReference reff;
    ArrayList<Carsdetails> list;
    ArrayList<InsuranceDetails> list2;
    ArrayList<Licensedetails> list3;
    MyAdapter_history myAdapter;
    MyAdapter_history2 myAdapter2;
    MyAdapter_history3 myAdapter3;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");
        String currentDate = format.format(calendar.getTime());

        progressDialog = new ProgressDialog(History.this);
        progressDialog.setMessage("Load Data");
        progressDialog.show();
        reff = FirebaseDatabase.getInstance().getReference("CarsDetails");
        binding.recyclerHis.setHasFixedSize(true);
        binding.recyclerHis.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new MyAdapter_history(this,list);
        binding.recyclerHis.setAdapter(myAdapter);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                myAdapter.notifyDataSetChanged();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Carsdetails carsdetails = dataSnapshot.getValue(Carsdetails.class);
                    if (currentDate.compareTo(carsdetails.getServiceDate())>=0){
                        list.add(carsdetails);
                    }
                }
                myAdapter.notifyDataSetChanged();
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(History.this, "Lost connection!", Toast.LENGTH_SHORT).show();
            }
        });
        ///////////////////////
        reff = FirebaseDatabase.getInstance().getReference("InsuranceDetails");
        binding.recyclerHis2.setHasFixedSize(true);
        binding.recyclerHis2.setLayoutManager(new LinearLayoutManager(this));
        list2 = new ArrayList<>();
        myAdapter2 = new MyAdapter_history2(this,list2);
        binding.recyclerHis2.setAdapter(myAdapter2);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                myAdapter2.notifyDataSetChanged();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    InsuranceDetails insuranceDetails = dataSnapshot.getValue(InsuranceDetails.class);
                    if (currentDate.compareTo(insuranceDetails.getExpiryDate())>=0){
                        list2.add(insuranceDetails);
                    }
                }
                myAdapter2.notifyDataSetChanged();
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(History.this, "Lost connection!", Toast.LENGTH_SHORT).show();
            }
        });
        ////////////////////////////
        reff = FirebaseDatabase.getInstance().getReference("LicenseDetails");
        binding.recyclerHis3.setHasFixedSize(true);
        binding.recyclerHis3.setLayoutManager(new LinearLayoutManager(this));
        list3 = new ArrayList<>();
        myAdapter3 = new MyAdapter_history3(this,list3);
        binding.recyclerHis3.setAdapter(myAdapter3);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3.clear();
                myAdapter3.notifyDataSetChanged();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Licensedetails licensedetails = dataSnapshot.getValue(Licensedetails.class);
                    if (currentDate.compareTo(licensedetails.getExpiryDate())>=0){
                        list3.add(licensedetails);
                    }
                }
                myAdapter3.notifyDataSetChanged();
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(History.this, "Lost connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fn_gotocar(View view) {
        Intent intent = new Intent(this, Car.class);
        startActivity(intent);
        finish();
    }
    public void fn_gotoinsurance(View view) {
        Intent intent = new Intent(this, Insurance.class);
        startActivity(intent);
        finish();
    }
    public void fn_gotolicense(View view) {
        Intent intent = new Intent(this, License.class);
        startActivity(intent);
        finish();
    }
    public void fn_gotohome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }
}