package ftmk.bits.autoremind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_CarName extends RecyclerView.Adapter<MyAdapter_CarName.MyViewHolder> {

    Context context;
    ArrayList<Carsdetails> list;

    private MyAdapter_CarName.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(String getid, String realid);
    }

    public void setOnItemClickListener(MyAdapter_CarName.OnItemClickListener clickListener){
        listener = clickListener;
    }

    public MyAdapter_CarName(Context context, ArrayList<Carsdetails> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public  MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item_cars_display,parent,false);
        return new MyViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){

        Carsdetails carsdetails = list.get(position);
        holder.platenumber.setText(carsdetails.getPlatenumber());
        holder.Brand.setText(carsdetails.getBrand());
        holder.ServiceDate.setText(carsdetails.getServiceDate());
        holder.getid = carsdetails.getPlatenumber();
        holder.realid = carsdetails.getKey();
    }

    @Override
    public int getItemCount() {return list.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView platenumber, Brand, ServiceDate, button1;

        String getid, realid;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener){
            super(itemView);

            platenumber = itemView.findViewById(R.id.textView_plateNo);
            Brand = itemView.findViewById(R.id.textView_brand);
            ServiceDate = itemView.findViewById(R.id.textView_serviceDate);

            button1 = itemView.findViewById(R.id.button4);

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getid, realid);
                }
            });

        }
    }

}
