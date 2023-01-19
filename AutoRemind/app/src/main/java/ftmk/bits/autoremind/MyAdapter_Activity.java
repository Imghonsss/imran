package ftmk.bits.autoremind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_Activity extends RecyclerView.Adapter<MyAdapter_Activity.MyViewHolder> {

    Context context;
    ArrayList<Carsdetails> list;

    public MyAdapter_Activity(Context context, ArrayList<Carsdetails> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter_Activity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item_activity_display,parent,false);
        return new MyAdapter_Activity.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_Activity.MyViewHolder holder, int position){

        Carsdetails carsdetails = list.get(position);
        holder.platenumber.setText(carsdetails.getPlatenumber() +" - "+ carsdetails.getBrand());

    }

    @Override
    public int getItemCount() {return list.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView platenumber;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            platenumber = itemView.findViewById(R.id.textView_plateNo);
        }
    }

}
