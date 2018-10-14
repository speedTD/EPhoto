package com.example.admin.ephoto.Activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.ephoto.Databases.DatabaseThuVien;
import com.example.admin.ephoto.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static android.support.v4.os.LocaleListCompat.create;

public class DisplayItemActivity extends AppCompatActivity {

       private PhotoView imageView;
       private LinearLayout Displaymenu;
       private boolean check=false;
       private TextView Text_Share,Text_Delete;
       private String uri;
       private String TAG="DUY28";
       private DatabaseThuVien mdb;
       private int id;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Holo_Light_ButtonBar); // (for Android Built In Theme)
        setContentView(R.layout.fragment_display_item);
        mdb=new DatabaseThuVien(this);
      //  uri=getIntent().getData();

        Intent intent2=getIntent();
        uri=intent2.getStringExtra("URI");
        id=intent2.getIntExtra("ID",0);
        Log.d(TAG, "onCreate: "+id);
        imageView=(PhotoView) findViewById(R.id.id_display_item);
        Displaymenu=(LinearLayout) findViewById(R.id.id_display_menu);
        Text_Share=(TextView) findViewById(R.id.id_display_item_share);
        Text_Delete=(TextView) findViewById(R.id.id_display_item_delete);
        Glide.with(this).load(uri).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check=!check;
               if (check==true){
                    Displaymenu.setVisibility(View.VISIBLE);
                }else {
                   Displaymenu.setVisibility(View.INVISIBLE);
               }

            }
        });
        Text_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // xoa trong bo nho + xoa trong csdl
                final AlertDialog.Builder builder=new AlertDialog.Builder(DisplayItemActivity.this);
                builder.setMessage("You will delele this Picture ?")
                        .setTitle("Delele item")
                       .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Remove();
                        Toasty.success(getApplicationContext(),"Delele complie").show();
                        finish();
                        ThuVienActivity.adapter.notifyDataSetChanged();

                    }
                });
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        dialogInterface.cancel();

                    }
                });
                AlertDialog alertDialog=  builder.create();
                alertDialog.show();

            }
        });
        Text_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Your body here";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

    }
    // hàm Remove để xóa file trong điện thoại đồng thời xóa trong cơ sở dữ liệu
    private void Remove(){
        File file=new File(String.valueOf(uri));
        boolean e=file.delete();
        if(e==false){
            Log.d(TAG, "Remove: File chua duoc xoa");
        }else {
            Log.d(TAG, "Remove: Đã xóa file trong hệ thống");
        }
        //xóa trong CSDL;
        mdb.DeleteData(id);
        Log.d(TAG, "Remove: Đã Xóa file trong Database");


    }

}
