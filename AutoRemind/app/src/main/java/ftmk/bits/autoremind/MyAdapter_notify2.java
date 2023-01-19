package ftmk.bits.autoremind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MyAdapter_notify2 extends RecyclerView.Adapter<MyAdapter_notify2.MyViewHolder> {

    Context context;
    ArrayList<InsuranceDetails> list;

    public MyAdapter_notify2(Context context, ArrayList<InsuranceDetails> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter_notify2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item_notify2,parent,false);
        return new MyAdapter_notify2.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_notify2.MyViewHolder holder, int position){

        InsuranceDetails insuranceDetails = list.get(position);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");
        String currentDate = format.format(calendar.getTime());
        String enddate = insuranceDetails.getExpiryDate();
        String range = null;
        try {
            Date date1 = format.parse(currentDate);
            Date date2 = format.parse(enddate);
            long diff = date2.getTime() - date1.getTime();
            range = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS)+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.platenumber.setText(insuranceDetails.getPlateNumber());
        holder.Brand.setText(insuranceDetails.getCompany());
        holder.ServiceDate.setText(insuranceDetails.getExpiryDate());
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
