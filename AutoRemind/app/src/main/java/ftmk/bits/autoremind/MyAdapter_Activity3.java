package ftmk.bits.autoremind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_Activity3 extends RecyclerView.Adapter<MyAdapter_Activity3.MyViewHolder> {

    Context context;
    ArrayList<Licensedetails> list;

    public MyAdapter_Activity3(Context context, ArrayList<Licensedetails> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter_Activity3.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item_activity_display3,parent,false);
        return new MyAdapter_Activity3.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_Activity3.MyViewHolder holder, int position){

        Licensedetails licensedetails = list.get(position);
        holder.platenumber.setText(licensedetails.getClassType() +" - "+ licensedetails.getLicenseID());

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
