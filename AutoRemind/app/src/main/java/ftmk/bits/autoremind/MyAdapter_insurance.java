package ftmk.bits.autoremind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MyAdapter_insurance extends RecyclerView.Adapter<MyAdapter_insurance.MyViewHolder> {

    Context context;
    ArrayList<InsuranceDetails> list;

    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(String getid, String getinid);
    }

    public void setOnItemClickListener(MyAdapter_insurance.OnItemClickListener clickListener){
        listener = clickListener;
    }

    public MyAdapter_insurance(Context context, ArrayList<InsuranceDetails> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter_insurance.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item_cars_insurance,parent,false);
        return new MyAdapter_insurance.MyViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_insurance.MyViewHolder holder, int position){

        InsuranceDetails insurancedetails = list.get(position);


        holder.platenumber.setText(insurancedetails.getPlateNumber());
        holder.expirydate.setText(insurancedetails.getExpiryDate());
        holder.company.setText(insurancedetails.getCompany());
        holder.status.setText(insurancedetails.getStatus());
        holder.getid = insurancedetails.getId();
        holder.getinid = insurancedetails.getPlateNumber();


        if (holder.status.getText().toString().equals("Active")){
            holder.button1.setText("Edit");
        }

    }

    @Override
    public int getItemCount() {return list.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView insuranceid, platenumber, expirydate, company, status;
        Button button1;

        String getid, getinid;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener){
            super(itemView);

            platenumber = itemView.findViewById(R.id.textView_plateNo);
            expirydate = itemView.findViewById(R.id.textView_expiryDate);
            company = itemView.findViewById(R.id.textView_company);
            status = itemView.findViewById(R.id.textView_status);

            button1 = itemView.findViewById(R.id.button4);

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getid, getinid);
                }
            });

        }
    }

}