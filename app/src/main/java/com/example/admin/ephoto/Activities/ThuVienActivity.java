package com.example.admin.ephoto.Activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.ephoto.Adapters.ThuVienAdapter;
import com.example.admin.ephoto.Databases.DatabaseThuVien;
import com.example.admin.ephoto.R;
import com.example.admin.ephoto.Thuvien;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class ThuVienActivity extends AppCompatActivity {

    private List<Thuvien> list = new ArrayList<>();
    public static ThuVienAdapter adapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ImageView Slide_Imageview;
    private DatabaseThuVien mdb;
    private int Zize;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String TAG="DUY28";
    private Handler mhander;
    private int mTime=-1;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_vien);
        recyclerView = (RecyclerView) findViewById(R.id.id_recylerview_thuvien);
        Slide_Imageview=(ImageView) findViewById(R.id.id_image_collapsingtoolbar);
        animation= AnimationUtils.loadAnimation(this,R.anim.thuvien_anim_fadeout);
        //sử dụng cơ sở dữ liệu
        mdb=new DatabaseThuVien(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.id_collapsingToolbarlayout);
        collapsingToolbarLayout.setTitle("Thư Viện Ảnh");
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        Zize=mdb.getcount();
        Init();
        Update();

        for (int i=0;i<=Zize;i++) {
            list.add(addLib(i));
        }

//        Log.d(TAG, "onCreate: "+list.get(1).getPath());

        // sr
        adapter = new ThuVienAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
     //Lay ra list anh :v

    private Thuvien addLib(int Position) {
        Thuvien thuvien=mdb.GetItem(Position);
        Log.d(TAG, "addLib: "+Zize);
       return  thuvien;
    }
    //xác nhận dữ liệu và Hiển thị ảnh
    @SuppressLint("HandlerLeak")
    void Init(){
            mhander=new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1998) {


                        if(mTime==Zize){
                            mTime=-1;
                        }else {
//                            Glide.with(getApplicationContext()).load(list.get(mTime).getPath()).into(Slide_Imageview);
                            Slide_Imageview.setImageURI(Uri.parse(list.get(mTime).getPath()));
                            adapter.notifyDataSetChanged();
                            Slide_Imageview.setAnimation(animation);
                            Slide_Imageview.startAnimation(animation);
                            Log.d(TAG, "handleMessage: " + list.get(mTime).getPath());
                        }

                    }
                    super.handleMessage(msg);
                }
            };


    }
    //tác vụ lấy dữ liệu và gửi qua init();
    void Update(){
        final Thread thread=new Thread(){
            @Override
            public void run() {
                while (true){
                   mTime+=1;
                    Message message=new Message();
                    message.what=1998;
                    message.arg1=mTime;
                    mhander.sendMessage(message);

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        thread.start();
    }

}
