package ftmk.bits.autoremind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import ftmk.bits.autoremind.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    DatabaseReference reff;
    ProgressDialog progressDialog;
    ArrayList<Carsdetails> list;
    ArrayList<InsuranceDetails> list2;
    ArrayList<Licensedetails> list3;
    MyAdapter_Activity myAdapter;
    MyAdapter_Activity2 myAdapter2;
    MyAdapter_Activity3 myAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerforactivity.setVisibility(View.VISIBLE);
        binding.recyclerforactivity2.setVisibility(View.VISIBLE);
        binding.recyclerforactivity3.setVisibility(View.VISIBLE);

        binding.recyclerforactivity.setHasFixedSize(true);
        binding.recyclerforactivity.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerforactivity2.setHasFixedSize(true);
        binding.recyclerforactivity2.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerforactivity3.setHasFixedSize(true);
        binding.recyclerforactivity3.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        myAdapter = new MyAdapter_Activity(Home.this,list);
        binding.recyclerforactivity.setAdapter(myAdapter);
        myAdapter2 = new MyAdapter_Activity2(Home.this,list2);
        binding.recyclerforactivity2.setAdapter(myAdapter2);
        myAdapter3 = new MyAdapter_Activity3(Home.this,list3);
        binding.recyclerforactivity3.setAdapter(myAdapter3);

        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String thedate = i+"-"+(i1+1)+"-"+i2;
                String getquery = "";

                binding.recyclerforactivity.setVisibility(View.GONE);
                binding.recyclerforactivity2.setVisibility(View.GONE);
                binding.recyclerforactivity3.setVisibility(View.GONE);

                if (binding.spinner21.getSelectedItem().toString().equals("Service Activity")){
                    getquery = "CarsDetails";
                    binding.recyclerforactivity.setVisibility(View.VISIBLE);
                    progressDialog = new ProgressDialog(Home.this);
                    progressDialog.setMessage("Retrieve Data");
                    progressDialog.show();
                    Query querychecker = FirebaseDatabase.getInstance().getReference(getquery).orderByChild("serviceDate").equalTo(thedate);
                    querychecker.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear();
                            myAdapter.notifyDataSetChanged();
                            if (snapshot.exists())
                            {
                                //Toast.makeText(Home.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                //recyclerview to display more than one data
                                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                                {
                                    Carsdetails carsdetails = dataSnapshot.getValue(Carsdetails.class);
                                    list.add(carsdetails);
                                    progressDialog.cancel();
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                            else
                            {
                                Toast.makeText(Home.this, "nothing here", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if (binding.spinner21.getSelectedItem().toString().equals("Insurance Activity")){
                    getquery = "InsuranceDetails";
                    binding.recyclerforactivity2.setVisibility(View.VISIBLE);
                    progressDialog = new ProgressDialog(Home.this);
                    progressDialog.setMessage("Retrieve Data");
                    progressDialog.show();
                    Query querychecker = FirebaseDatabase.getInstance().getReference(getquery).orderByChild("expiryDate").equalTo(thedate);
                    querychecker.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list2.clear();
                            myAdapter2.notifyDataSetChanged();
                            if (snapshot.exists())
                            {
                                //Toast.makeText(Home.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                //recyclerview to display more than one data
                                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                                {
                                    InsuranceDetails insuranceDetails = dataSnapshot.getValue(InsuranceDetails.class);
                                    list2.add(insuranceDetails);
                                    progressDialog.cancel();
                                }
                                myAdapter2.notifyDataSetChanged();
                            }
                            else
                            {
                                Toast.makeText(Home.this, "nothing here", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if (binding.spinner21.getSelectedItem().toString().equals("License Activity")){
                    getquery = "LicenseDetails";
                    binding.recyclerforactivity3.setVisibility(View.VISIBLE);
                    progressDialog = new ProgressDialog(Home.this);
                    progressDialog.setMessage("Retrieve Data");
                    progressDialog.show();
                    Query querychecker = FirebaseDatabase.getInstance().getReference(getquery).orderByChild("expiryDate").equalTo(thedate);
                    querychecker.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list3.clear();
                            myAdapter3.notifyDataSetChanged();
                            if (snapshot.exists())
                            {
                                //Toast.makeText(Home.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                //recyclerview to display more than one data
                                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                                {
                                    Licensedetails licensedetails = dataSnapshot.getValue(Licensedetails.class);
                                    list3.add(licensedetails);
                                    progressDialog.cancel();
                                }
                                myAdapter3.notifyDataSetChanged();
                            }
                            else
                            {
                                Toast.makeText(Home.this, "nothing here", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

//        // set notification demo
//        int notificationId = 1;
//        Intent intent = new Intent(Home.this, AlarmReceiver.class);
//        intent.putExtra("notificationId", notificationId);
//        intent.putExtra("todo","testing");
//        PendingIntent alarmIntent = PendingIntent.getBroadcast(Home.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//
//        ///set if sdk outtodate
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//
//        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
//        //set time from timepicker
//        Calendar startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY,12);
//        startTime.set(Calendar.MINUTE,38);
//        startTime.set(Calendar.SECOND,0);
//        long alarmStartTime = startTime.getTimeInMillis();
//        alarm.set(AlarmManager.RTC_WAKEUP,alarmStartTime,alarmIntent);
//        Toast.makeText(this, "success on set notification", Toast.LENGTH_SHORT).show();
    }

    public void fn_gotocar(View view) {
        //Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Car.class);
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

    public void fn_gotoreminder(View view){
        Intent intent = new Intent(this,Reminder.class);
        startActivity(intent);
        finish();
    }
}