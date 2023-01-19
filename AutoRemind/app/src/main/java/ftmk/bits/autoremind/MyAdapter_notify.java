package ftmk.bits.autoremind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MyAdapter_notify extends RecyclerView.Adapter<MyAdapter_notify.MyViewHolder> {

    Context context;
    ArrayList<Carsdetails> list;

    public MyAdapter_notify(Context context, ArrayList<Carsdetails> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public  MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item_notify,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){

        Carsdetails carsdetails = list.get(position);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");
        String currentDate = format.format(calendar.getTime());
        String enddate = carsdetails.getServiceDate();
        String range = null;
        try {
            Date date1 = format.parse(currentDate);
            Date date2 = format.parse(enddate);
            long diff = date2.getTime() - date1.getTime();
            range = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS)+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }

            holder.platenumber.setText(carsdetails.getPlatenumber());
            holder.Brand.setText(carsdetails.getBrand());
            holder.ServiceDate.setText(carsdetails.getServiceDate());
            holder.dayleft.setText(range + " Days left");

    }

    @Override
    public int getItemCount() {return list.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView platenumber, Brand, ServiceDate, dayleft;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            platenumber = itemView.findViewById(R.id.textView_plateNo);
            Brand = itemView.findViewById(R.id.textView_brand);
            ServiceDate = itemView.findViewById(R.id.textView_serviceDate);
            dayleft = itemView.findViewById(R.id.textView_serviceDate2);

        }
    }

}
