package com.example.admin.ephoto.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.ephoto.Activities.DisplayItemActivity;
import com.example.admin.ephoto.Databases.DatabaseThuVien;
import com.example.admin.ephoto.R;
import com.example.admin.ephoto.Thuvien;


import java.util.List;

public class ThuVienAdapter extends RecyclerView.Adapter<ThuVienAdapter.hoderview> {
    private List<Thuvien> list;
    private DatabaseThuVien mdb;
    private Context context;
    private Thuvien thuvien;

    public ThuVienAdapter( Context context) {
        mdb=new DatabaseThuVien(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ThuVienAdapter.hoderview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutInflater=LayoutInflater.from(context).inflate(R.layout.row_item_thuvien,parent,false);
        return new hoderview(layoutInflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ThuVienAdapter.hoderview holder, final int position) {
        thuvien =getItem(position);
        Glide.with(context).load(thuvien.getPath()).into(holder.imageView);
      //  holder.imageView.setImageURI(Uri.parse(thuvien.getPath()));
        Log.d("365", "onBindViewHolder: "+thuvien.getPath());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
           Thuvien tv =getItem(position);
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DisplayItemActivity.class);
                intent.putExtra("URI",tv.getPath());
                intent.putExtra("ID",tv.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdb.getcount();
    }
    public Thuvien getItem(int position) {
        return mdb.GetItem(position);
    }
    public class hoderview extends RecyclerView.ViewHolder{
        ImageView imageView;

        public hoderview(View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.id_image_item);
        }
    }
}
