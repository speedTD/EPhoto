package com.example.admin.ephoto.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.ephoto.Databases.DatabaseThuVien;
import com.example.admin.ephoto.PaintLineView;
import com.example.admin.ephoto.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class EditPhotoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btn_But_ve, btn_tay_xoa, btn_chinh_as, btn_ve_chu, btn_lưu_hinh,btn_add_picture;
    private DrawableImageView Anh_Nen; // ảnh nền để vẽ vời các kiểu
    private  final int REQUEST_CODE=2222;
    private Uri uri;
    private Bitmap bmp;
    private Bitmap alebitmap;
    public static DatabaseThuVien mDb;
    private String TAG="DUY28";
    private PaintLineView Imageview;
    // đoạn này để khai báo trong Custom dilog Paint Line nhé
    private Button btn_maudo,btn_maucam,btn_mau_vang,btn_mauxanhla,btn_mauxanhduong,btn_mautim,btn_mauhong;
    private TextView txt_co_Line,mtxt_anhsang; // kích thước line màu
    private SeekBar mSeekbar_kich_co_line,mSeebar_Anhsang;
    private ImageButton m_btn_amban,m_btn_do,m_btn_xanh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        mDb=new DatabaseThuVien(getBaseContext());
        Anh_xa();
        Imageview=(PaintLineView) findViewById(R.id.id_paintview);





    }
    int kichco=20;
    String Mamau="#15ff00";
    public void DialogVeLine(){
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.dilog_paint_line);
        dialog.setTitle("Vẽ line nè :v");
        //ánh xạ hàng loạt  nè

        txt_co_Line=(TextView) dialog.findViewById(R.id.id_dilog_do_lon_line);
        mSeekbar_kich_co_line=(SeekBar) dialog.findViewById(R.id.id_dilog_seebak);
        mSeekbar_kich_co_line.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                kichco=seekBar.getProgress();
                txt_co_Line.setText(kichco+"");
                Imageview.setBrushSize(kichco);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // Màu nè
        btn_maudo=(Button)  dialog.findViewById(R.id.id_dilog_maudo);
        btn_maucam=(Button)  dialog.findViewById(R.id.id_dilog_cam);
        btn_mau_vang=(Button)  dialog.findViewById(R.id.id_dilog_vang);
        btn_mauxanhla=(Button)  dialog.findViewById(R.id.id_dilog_xanhla);
        btn_mauxanhduong=(Button)  dialog.findViewById(R.id.id_dilog_xanhduonng);
        btn_mautim=(Button)  dialog.findViewById(R.id.id_dilog_tim);
        btn_mauhong=(Button)  dialog.findViewById(R.id.id_dilog_hong);
        // Màu nè
        btn_maudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mã màu đỏ
                Mamau="#f10000";
                Log.d(TAG, "onClick: "+Mamau);
                Imageview.setColor(Mamau);

            }
        });
        btn_maucam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mã màu cam
                Mamau="#ff8000";
                Log.d(TAG, "onClick: "+Mamau);
                Imageview.setColor(Mamau);
            }
        });
        btn_mau_vang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mã màu Vàng
                Mamau="#fbff00";
                Log.d(TAG, "onClick: "+Mamau);
                Imageview.setColor(Mamau);
            }
        });
        btn_mauxanhla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mã màu xanh lá
                Mamau="#15ff00";
                Log.d(TAG, "onClick: "+Mamau);
                Imageview.setColor(Mamau);
            }
        });
        btn_mauxanhduong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mã màu xanh dương
                Mamau="#0414f9";
                Log.d(TAG, "onClick: "+Mamau);
                Imageview.setColor(Mamau);
            }
        });
        btn_mautim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mã màu tím
                Mamau="#ad0af9";
                Log.d(TAG, "onClick: "+Mamau);
                Imageview.setColor(Mamau);
            }
        });
        btn_mauhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mã màu hồng
                Mamau="#ff0eeb";
                Log.d(TAG, "onClick: "+Mamau);
                Imageview.setColor(Mamau);
            }
        });


        dialog.show();

    }
    public void paintClicked(View view){
        //use chosen color

    }


    private void Anh_xa() {
        btn_But_ve = (ImageButton) findViewById(R.id.id_menu_edit_but_ve);
        btn_But_ve.setOnClickListener(this);
        btn_tay_xoa = (ImageButton) findViewById(R.id.id_menu_edit_tay);
        btn_tay_xoa.setOnClickListener(this);
        btn_chinh_as = (ImageButton) findViewById(R.id.id_menu_edit_anh_sang);
        btn_chinh_as.setOnClickListener(this);
        btn_ve_chu = (ImageButton) findViewById(R.id.id_menu_edit_them_chu);
        btn_ve_chu.setOnClickListener(this);
        btn_lưu_hinh = (ImageButton) findViewById(R.id.id_menu_edit_luu_anh);
        btn_lưu_hinh.setOnClickListener(this);
        btn_add_picture = (ImageButton) findViewById(R.id.id_menu_add);
        btn_add_picture.setOnClickListener(this);
      //  Anh_Nen=(DrawableImageView) findViewById(R.id.id_edit_hinh_loai_1);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_menu_edit_but_ve:
                Toasty.success(this, "Bút Vẽ", Toast.LENGTH_SHORT).show();

                //Show Dilog nè
                DialogVeLine();
                //vẽ
//                DrawableImageView drawableImageView=new DrawableImageView(this);
//                drawableImageView.setNewImage(alebitmap,bmp);
                // set Gallery Image to Drawing-view
                Bitmap PaintBitmap;

                if (uri != null) {
//                   // PaintBitmap = getResizeBitmap(String.valueOf(uri));
                    BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
                    Imageview.setBackgroundDrawable(ob);
                }

                break;
            case R.id.id_menu_edit_tay:
                Toasty.success(this, "Bút Tẩy", Toast.LENGTH_SHORT).show();
                Imageview.startNew();
                break;
            case R.id.id_menu_edit_anh_sang:
                Toasty.success(this, "Ánh Sáng", Toast.LENGTH_SHORT).show();
                //Anh Sang
                DialogAnhSang();


                break;
            case R.id.id_menu_edit_them_chu:
                Toasty.success(this, "Thêm Chũ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_menu_edit_luu_anh:
                Toasty.success(this, "Lưu ảnh", Toast.LENGTH_SHORT).show();
                // luu anh
                SaveDatabase();
                break;
            case R.id.id_menu_add:
                Toasty.success(this, "Thêm ảnh", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            default:
                return;
        }

    }

    //mETHOD gET bITMAP
    public Bitmap GetBitmap(){
        Bitmap bitmap=Bitmap.createBitmap(Imageview.getWidth(),Imageview.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        canvas.drawBitmap(bitmap,0,0,null);

        return bitmap;
    }
        //method chỉnh Ánh Sáng Của ảnh
    int DoSang=2;
    private void DialogAnhSang() {


        BottomSheetDialog dialog = new BottomSheetDialog(this);
      //  Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_anhsang);
        dialog.setTitle("Vẽ line nè :v");
        //ánh xạ hàng loạt  nè
        m_btn_amban=(ImageButton) dialog.findViewById(R.id.id_dilog_anh_amban) ;
        m_btn_do=(ImageButton) dialog.findViewById(R.id.id_dilog_anh_do) ;
        m_btn_xanh=(ImageButton) dialog.findViewById(R.id.id_dilog_anh_xanh) ;
        mtxt_anhsang=(TextView) dialog.findViewById(R.id.id_dialog_as_txt);

        //chọn Hiệu Ứng Đỏ ảnh
         m_btn_do.setOnClickListener(new View.OnClickListener() {
             @SuppressLint("NewApi")
             @Override
             public void onClick(View view) {
                 Drawable d = new BitmapDrawable(getResources(), createSepiaToningEffect(bmp,1,1,13,2));
                     Imageview.setBackground(d);
                 Log.d(TAG, "onClick: Hiệu Ứng Đỏ đã chọn");
             }
         });

         // chọn hiệu Ứng Xanh ảnh
        m_btn_xanh.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                Drawable d = new BitmapDrawable(getResources(), createSepiaToningEffect(bmp,1,10,1,1));
                    Imageview.setBackground(d);
                Log.d(TAG, "onClick: Hiệu Ứng Xanh Đã Chọn");
            }
        });

        //chọn hiệu Ứng âm bản
        m_btn_amban.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                Drawable d = new BitmapDrawable(getResources(),Amban(bmp));
                Imageview.setBackground(d);
                Log.d(TAG, "onClick: Hiệu Ứng âm bản đa chọn");
            }
        });



        dialog.show();
    }


    private void SaveDatabase() {
        //Tạo Đường dẫn
        Imageview.setDrawingCacheEnabled(true);
        String mFilePath;
        String Filename=UUID.randomUUID().toString()+".jpg";


        mFilePath = Environment.getExternalStorageDirectory()+"/"+Filename;
        //Tạo Định dang Output và , save file vào thư Mục EPHOTO
        File file=new File(mFilePath);
        OutputStream outputStream=null;
        try {
         //   File file=new File(mFilePath);
            outputStream=new FileOutputStream(file);
            //định dạng ảnh Output 75 , JPEG
            Imageview.getDrawingCache().compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            Log.d(TAG, "SaveDatabase Đã Lưu Ảnh  Tại "+file.getPath());

            mDb.InsertData("duy.jpg",mFilePath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }else {
                    Log.d(TAG, "SaveDatabase output null");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK&&data!=null){
            //Khác rỗng thì làm gì , Thì lấy ảnh mà gán vào Image chứ sao
            uri=data.getData(); //dữ liệu lấy về ở dạng Uri
           // Anh_Nen.setImageURI(uri);
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            try {
                bmp = BitmapFactory
                        .decodeStream(
                                getContentResolver().openInputStream(
                                        uri), null, bmpFactoryOptions);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // Calculate inSampleSize
            bmpFactoryOptions.inJustDecodeBounds = false;
            try {
                bmp = BitmapFactory
                        .decodeStream(
                                getContentResolver().openInputStream(
                                        uri), null, bmpFactoryOptions);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//            Imageview.setImageURI(uri);
//            alebitmap = Bitmap.createBitmap(bmp.getWidth(),
//                    bmp.getHeight(), bmp.getConfig());

            // Decode bitmap with inSampleSize set
//             Imageview.setBitmap(uri);

//            BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
//            Imageview.setBackgroundDrawable(ob);

          // Xu ly bitmap ne





        }

    }
    public static Bitmap createSepiaToningEffect(Bitmap src, int depth, double red, double green, double blue) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // constant grayscale
        final double GS_RED = 0.3;
        final double GS_GREEN = 0.59;
        final double GS_BLUE = 0.11;
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                // get color on each channel
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // apply grayscale sample
                B = G = R = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);

                // apply intensity level for sepid-toning on each channel
                R += (depth * red);
                if(R > 255) { R = 255; }

                G += (depth * green);
                if(G > 255) { G = 255; }

                B += (depth * blue);
                if(B > 255) { B = 255; }

                // set new pixel color to output image
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    // ảnh âm bản
    public static Bitmap Amban(Bitmap src) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // color info
        int A, R, G, B;
        int pixelColor;
        // image size
        int height = src.getHeight();
        int width = src.getWidth();

        // scan through every pixel
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                // get one pixel
                pixelColor = src.getPixel(x, y);
                // saving alpha channel
                A = Color.alpha(pixelColor);
                // inverting byte for each R/G/B channel
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);
                // set newly-inverted pixel to output image
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final bitmap
        return bmOut;
    }

}
