package com.example.myschoolapp.ui.mine;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.myschoolapp.BaseActivity;
import com.example.myschoolapp.BitmapUtil;
import com.example.myschoolapp.DownPicUtil;
import com.example.myschoolapp.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.example.myschoolapp.Constant.icon_path;


public class ChangeMyInfoActivity extends BaseActivity {

    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    public static final int CUT_PHOTO = 3;

    private ImageView picture;

    private Uri imageUri;

    public Button makeSure,cancel;

    public String tempIconPath=icon_path;
    public String  photopath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changemyinfo);

        Button takePhoto = (Button) findViewById(R.id.take_photo);
        Button chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
        makeSure=(Button)findViewById(R.id.make_sure);
        picture = (ImageView) findViewById(R.id.picture);
        cancel=findViewById(R.id.cancle2);

        Bitmap bitmap = BitmapFactory.decodeFile(icon_path);
        picture.setImageBitmap(bitmap);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ??????File???????????????????????????????????????
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                photopath=outputImage.toString();
                System.out.println("????????????????????????"+photopath);
                try {
//                    if (outputImage.exists()) {
//                        outputImage.delete();
//                    }
                    if(!outputImage.exists()){
                        System.out.println("??????????????????");
                        outputImage.createNewFile();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT < 24) {
                    imageUri = Uri.fromFile(outputImage);
                } else {
                    imageUri = FileProvider.getUriForFile(ChangeMyInfoActivity.this, "com.example.myschoolapp.ui.mine.fileprovider", outputImage);
                }
                // ??????????????????
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ChangeMyInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ChangeMyInfoActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
            }
        });

        makeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String resPath=getIconPath();
                System.out.println("????????????????????????"+tempIconPath);
                System.out.println("??????????????????????????????"+icon_path);
                copyFile(tempIconPath,icon_path);
//                Uri realuri= FileProvider.getUriForFile(ChangeMyInfoActivity.this, "com.example.myschoolapp.ui.mine.fileprovider",new File(icon_path));
//                CropPic(realuri);
//                icon_path=tempIconPath;
                System.out.println("????????????????????????"+icon_path);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivityForResult(intent, CHOOSE_PHOTO); // ????????????
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        System.out.println("???????????????????????????"+imageUri);
                        tempIconPath=photopath;
                        Bitmap bitmap1 = BitmapFactory.decodeFile(tempIconPath);
                        Bitmap bitmap2= BitmapUtil.toRoundBitmap(bitmap1);
                        picture.setImageBitmap(bitmap2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // ???????????????????????????
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4?????????????????????????????????????????????
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4??????????????????????????????????????????
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            case CUT_PHOTO:
                setpic(data);
                break;
            default:
                break;
        }
    }

    private void savepictofile(Bitmap mBitmap,String fileName) {
        FileOutputStream b = null;
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// ?????????????????????
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // ?????????
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void setpic(Intent intent){
        String temp_cut_path=getIconPath();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            try{
                savepictofile(photo,temp_cut_path);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        Bitmap bitmap = BitmapFactory.decodeFile(temp_cut_path);
        picture.setImageBitmap(bitmap);
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();

        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // ?????????document?????????Uri????????????document id??????
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // ????????????????????????id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // ?????????content?????????Uri??????????????????????????????
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // ?????????file?????????Uri?????????????????????????????????
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // ??????????????????????????????
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();

        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // ??????Uri???selection??????????????????????????????
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        System.out.println("???????????????????????????"+path);
        tempIconPath=path;
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            System.out.println("??????????????????"+imagePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            Bitmap bitmap1= BitmapUtil.toRoundBitmap(bitmap);
            picture.setImageBitmap(bitmap1);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getIconPath(){
        // ????????????????????????
        return icon_path;
    }
    public static void copyFile(String old_Path, String new_Path) {
        final String oldPath=old_Path,newPath=new_Path;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int bytesum = 0;
                    int byteread = 0;
                    File oldfile = new File(oldPath);
                    if (oldfile.exists()) {//???????????????
                        InputStream inStream = new FileInputStream(oldPath);//???????????????
                        FileOutputStream fs = new FileOutputStream(newPath);
                        byte[] buffer = new byte[1444];
                        int length;
                        int value = 0 ;
                        while ((byteread = inStream.read(buffer)) != -1) {
                            bytesum += byteread;//????????? ????????????
                            value ++ ;  //??????
                            fs.write(buffer, 0, byteread);
                        }
                        inStream.close();
                    }
                    else {
                        System.out.println("???????????????");
                    }
                } catch (Exception e) {
                    System.out.println("??????????????????????????????");
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void CropPic(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.setData(uri);
        System.out.println("Uri?????????"+uri.toString());

        // ????????????
        intent.putExtra("crop", "false");
        // aspectX aspectY ??????????????????
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY ?????????????????????
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CUT_PHOTO);
    }

    /**
     * ?????????????????????????????????
     */
//    private void getImageToView(Intent data) {
//        Bundle extras = data.getExtras();
//        if (extras != null) {
//            Bitmap photo = extras.getParcelable("data");
//            savePic(photo);
//            photo=Tools.toRoundCorner(photo,7);
//            Drawable drawable = new BitmapDrawable(this.getResources(),photo);
//            faceImage.setImageDrawable(drawable);
//        }
//    }
//
//    private void savePic(Bitmap photo ){
//        long l2 = System.currentTimeMillis();
//        String fileName = l2 + ".jpg";
//        String tempImgPath = getCacheDir().getAbsolutePath() + "/sysfiles/temp/" + fileName;
//        String dir = getDir(tempImgPath);
//        File dirFile = new File(dir);
//        dirFile.mkdirs();
//        if (!dirFile.exists()) {
//            Toast.makeText(MyFaceActivity.this, "????????????SD?????????,??????????????????", Toast.LENGTH_LONG).show();
//        }
//        try {
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempImgPath));
//            photo.compress(Bitmap.CompressFormat.JPEG, 75, bos);// (0 - 100)????????????
//            SharedPreferencesUtils.setStringValue(MyFaceActivity.this, SharedPreferencesUtils.PHOTO_PATH, tempImgPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}