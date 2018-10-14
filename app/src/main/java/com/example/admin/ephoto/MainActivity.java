package com.example.admin.ephoto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.admin.ephoto.Activities.EditPhotoActivity;
import com.example.admin.ephoto.Activities.ThuVienActivity;

public class MainActivity extends AppCompatActivity {
    TextView menu_text_comming,menu_text_sua_Anh,textView3,menu_text_thuvien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu_text_comming=(TextView) findViewById(R.id.id_conmingsoon);
        menu_text_sua_Anh=(TextView) findViewById(R.id.id_suaanh);
        textView3=(TextView) findViewById(R.id.id_suaanh_nc);
        menu_text_thuvien=(TextView) findViewById(R.id.id_thuvien);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.menu_animation);
        Animation animation2= AnimationUtils.loadAnimation(this,R.anim.menu_animation2);
        animation.setRepeatCount(Animation.INFINITE);
        menu_text_comming.setAnimation(animation2); menu_text_comming.startAnimation(animation2);
        menu_text_sua_Anh.setAnimation(animation2); menu_text_sua_Anh.startAnimation(animation2);
        textView3.setAnimation(animation); textView3.startAnimation(animation);
        menu_text_thuvien.setAnimation(animation); menu_text_thuvien.startAnimation(animation);

        menu_text_thuvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ThuVienActivity.class));
            }
        });
        menu_text_sua_Anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EditPhotoActivity.class));
            }
        });



    }
}
