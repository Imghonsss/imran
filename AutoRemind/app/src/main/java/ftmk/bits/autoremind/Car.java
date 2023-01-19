package ftmk.bits.autoremind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ftmk.bits.autoremind.databinding.ActivityCarBinding;

public class Car extends AppCompatActivity {

    ActivityCarBinding binding;
    DatabaseReference reff;
    ArrayList<Carsdetails> list;
    MyAdapter_CarName myAdapter;
    static String getget, getgetget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        ////////////display recyclerview
        binding = ActivityCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reff = FirebaseDatabase.getInstance().getReference("CarsDetails");
        binding.recyclerHis.setHasFixedSize(true);
        binding.recyclerHis.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new MyAdapter_CarName(this,list);
        binding.recyclerHis.setAdapter(myAdapter);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                myAdapter.notifyDataSetChanged();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Carsdetails carsdetails = dataSnapshot.getValue(Carsdetails.class);
                    carsdetails.setKey(dataSnapshot.getKey());
                    list.add(carsdetails);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myAdapter.setOnItemClickListener(new MyAdapter_CarName.OnItemClickListener() {
            @Override
            public void onItemClick(String getid, String realid) {
                getget = getid;
                getgetget = realid;
                Toast.makeText(Car.this, Car.getget, Toast.LENGTH_SHORT).show();
                /////new activity
                Intent intent = new Intent(Car.this, Update_Car.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void fn_gotohome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }

    public void fn_register_form(View view) {
        Intent intent = new Intent(this, Register_Car.class);
        startActivity(intent);
        finish();
    }

    public void fn_gotolicense(View view) {
        Intent intent = new Intent(this, License.class);
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