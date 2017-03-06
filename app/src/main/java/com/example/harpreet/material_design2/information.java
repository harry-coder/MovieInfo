package com.example.harpreet.material_design2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Harpreet on 16/02/2017.
 */

public class information {

    int image;
    String text;

    information(int image, String title) {
        this.image = image;
        this.text = title;
    }


}

class myAdapter extends RecyclerView.Adapter<myHolder> {
    LayoutInflater inflater;
    Context context;
    List<information> data = Collections.emptyList();

    public myAdapter(Context context, List<information> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.viewlayout, parent, false);

        myHolder holder = new myHolder(view,context);

        return holder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {

        //System.out.println("Inside bind");
        information dataObject = data.get(position);
        holder.textView.setText(dataObject.text);
        holder.imageView.setImageResource(dataObject.image);




    }

    @Override
    public int getItemCount() {
        //  Toast.makeText(context, data.size(), Toast.LENGTH_SHORT).show();
//        System.out.println(data.size());
        return data.size();
    }
}

class myHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;

    public myHolder(View itemView, final Context context) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView2);
        textView = (TextView) itemView.findViewById(R.id.textView);



    }
}
