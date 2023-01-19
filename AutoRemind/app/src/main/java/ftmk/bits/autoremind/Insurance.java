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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ftmk.bits.autoremind.databinding.ActivityHistoryBinding;
import ftmk.bits.autoremind.databinding.ActivityInsuranceBinding;

public class Insurance extends AppCompatActivity {

    ActivityInsuranceBinding binding;
    DatabaseReference reff;
    ArrayList<InsuranceDetails> list;
    MyAdapter_insurance myAdapter;
    ProgressDialog progressDialog;
    static String getplatee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);

        binding = ActivityInsuranceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(Insurance.this);
        progressDialog.setMessage("Load Data");
        progressDialog.show();
        reff = FirebaseDatabase.getInstance().getReference("CarsDetails");
        //Query query = FirebaseDatabase.getInstance().getReference().child("CarsDetails").
        binding.recyclerHis.setHasFixedSize(true);
        binding.recyclerHis.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new MyAdapter_insurance(this,list);
        binding.recyclerHis.setAdapter(myAdapter);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                myAdapter.notifyDataSetChanged();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String getplate =  dataSnapshot.child("platenumber").getValue(String.class);
                    Query query = FirebaseDatabase.getInstance().getReference().child("InsuranceDetails").orderByChild("plateNumber").equalTo(getplate);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    InsuranceDetails insurancedetails = dataSnapshot.getValue(InsuranceDetails.class);
                                    insurancedetails.setStatus("Active");
                                    list.add(insurancedetails);
                                    Toast.makeText(Insurance.this, " Insurance Active", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                InsuranceDetails insurancedetails = dataSnapshot.getValue(InsuranceDetails.class);
                                insurancedetails.setId("None");
                                insurancedetails.setCompany("None");
                                insurancedetails.setExpiryDate("None");
                                insurancedetails.setStatus("Non-Active");
                                insurancedetails.setPlateNumber(getplate);
                                list.add(insurancedetails);
                            }
                            myAdapter.notifyDataSetChanged();
                            progressDialog.cancel();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(Insurance.this, "Lost connection!", Toast.LENGTH_SHORT).show();
            }
        });
        myAdapter.setOnItemClickListener(new MyAdapter_insurance.OnItemClickListener() {
            @Override
            public void onItemClick(String getid, String getinid) {
                //Toast.makeText(Insurance.this, getid, Toast.LENGTH_SHORT).show();
                //if (getid.equals("None")){
                getplatee = getinid;
                    Intent intent = new Intent(Insurance.this, Edit_Insurance.class);
                    intent.putExtra("id", getid);
                    startActivity(intent);
                    finish();
                //}
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

    public void fn_gotolicense(View view) {
        Intent intent = new Intent(this, License.class);
        startActivity(intent);
        finish();
    }


   public void fn_gooeditinsurance(View view) {
        Intent intent = new Intent(this, Edit_Insurance.class);
       startActivity(intent);
        finish();
    }

    public void fn_gotohistory(View view) {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
        finish();
    }
}