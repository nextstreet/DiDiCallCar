package com.tantuo.didicar.DriverLicenseRecognition;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.tantuo.didicar.MainActivity;
import com.tantuo.didicar.R;

import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tantuo.didicar.utils.LogUtil;
import com.tantuo.didicar.utils.WebDetailActivityUtils;

import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class LicenseMainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "Author:tantuo";
    private int REQUEST_CAPTURE_IMAGE = 1;
    private Uri fileUri;
    private String iv_bottom_sheet_item_url2 = "https://dpubstatic.udache.com/static/dpubimg/dpub2_project_187481/index_187481.html?TripCountry=CN&access_key_id=2&appid=10000&appversion=5.2.52&area=%E5%8C%97%E4%BA%AC%E5%B8%82&channel=780&city_id=1&cityid=1&datatype=1&deviceid=6cd1d3832da36056681ad4ed7ade2155&dviceid=6cd1d3832da36056681ad4ed7ade2155&flat=40.39293&flng=116.84192&imei=868227037142403854C78AD10B66380C8F28CC6327C3788&lang=zh-CN&lat=40.392355381081394&lng=116.8424214994192&location_cityid=1&location_country=CN&maptype=soso&model=HWI-AL00&origin_id=1&os=9&phone=W471piXc0R0glRFq7nvDow&pid=1_xID-B2_hV&platform=1&susig=e4f80d8df39b46ae679cb58d721db&suuid=A1702CD0DD1175EDF286DE35369DF4CA_780&terminal_id=1&time=1560742235707&trip_cityId=1&trip_cityid=1&trip_country=CN&uid=281867467423745&utc_offset=480&uuid=A0AF094F9D975FBBAE7AD129E96CF26F&";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_recognition_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Button takePicBtn = (Button) this.findViewById(R.id.take_picture_btn);
        takePicBtn.setOnClickListener(this);

        Button selectPicBtn = (Button) this.findViewById(R.id.select_picture_btn);
        selectPicBtn.setOnClickListener(this);

        ImageView iv_bottom_image = (ImageView) findViewById(R.id.iv_bottom_sheet_item2);
        iv_bottom_image.setOnClickListener(this);


        copyFilesFassets(this, "driver_card_sample_tantuo.png", "sdcard/driver_card_sample_tantuo.png");


        //?????????openCV
        iniLoadOpenCV();
        initTitleBar();
    }


    private void iniLoadOpenCV() {
        boolean success = OpenCVLoader.initDebug();
        //????????????OpenCVLoader.initDebug()????????????OpenCV?????????????????????
        if (success) {
            LogUtil.i("?????????:LicenseMainActivity, ??????:iniLoadOpenCV() openCV load ?????? ");
        } else {
            LogUtil.i("?????????:LicenseMainActivity, ??????:iniLoadOpenCV() openCV load ?????? ");
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

    }

    private void initTitleBar() {
        ImageButton popUpMenu = (ImageButton) findViewById(R.id.btnPopUpMenu);
        popUpMenu.setOnClickListener(this);


        ImageView ib_titlebar_back = (ImageView) findViewById(R.id.ib_titlebar_back);
        ib_titlebar_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.take_picture_btn:
                //???????????????????????????????????????
                getDriverLicenseFromCamera();
                break;

            case R.id.select_picture_btn:
                //???????????????????????????????????????
                //getDriverLicenseFromPhone();

                //???assets????????????????????????????????? Sample
                getDriverLicenseFromMysample();
                break;

            case R.id.iv_bottom_sheet_item2:

                WebDetailActivityUtils.start_DiDi_info_Activity(LicenseMainActivity.this, iv_bottom_sheet_item_url2);
                break;


            case R.id.ib_titlebar_back:
                finish();
                break;

            default:
                break;
        }
    }


    private void getDriverLicenseFromMysample() {

        Intent intent = new Intent(getApplicationContext(), LicenseOCRActivity.class);

        Uri uri = Uri.fromFile(new File("sdcard/driver_card_sample_tantuo.png"));


        intent.putExtra("PICTURE-URL", uri);
        startActivity(intent);

    }

    private void getDriverLicenseFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Uri.fromFile(getSaveFilePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
    }

    private File getSaveFilePath() {
        //??????SD???????????????
        String status = Environment.getExternalStorageState();
        //??????????????????????????????
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            Log.i(TAG, "SD Card is not mounted...");
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_hhmmss");
        String name = df.format(new Date(System.currentTimeMillis())) + ".jpg";
        File filedir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myOcrImages");
        filedir.mkdirs();
        String fileName = filedir.getAbsolutePath() + File.separator + name;
        File imageFile = new File(fileName);
        return imageFile;
    }

    private void getDriverLicenseFromPhone() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "????????????..."), REQUEST_CAPTURE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            if (data == null) {
                Intent intent = new Intent(getApplicationContext(), LicenseOCRActivity.class);
                intent.putExtra("PICTURE-URL", fileUri);
                startActivity(intent);
            } else {
                Uri uri = data.getData();
                Intent intent = new Intent(getApplicationContext(), LicenseOCRActivity.class);
                File f = new File(getRealPath(uri));
                intent.putExtra("PICTURE-URL", Uri.fromFile(f));
                startActivity(intent);
            }
        }
    }

    private String getRealPath(Uri uri) {
        String filePath = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {//4.4?????????
            String wholeID = DocumentsContract.getDocumentId(uri);
            String id = wholeID.split(":")[1];
            String[] column = {MediaStore.Images.Media.DATA};
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                    sel, new String[]{id}, null);
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        } else {//4.4????????????4.4???????????????????????????
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(column_index);
        }
        Log.i("CV_TAG", "selected image path : " + filePath);
        return filePath;
    }


    /**
     * ???assets????????????????????????????????????
     * @param context Context ??????CopyFiles??????Activity
     * @param oldPath String  ???????????????  ??????/aa
     * @param newPath String  ???????????????  ??????xx:/bb/cc
     */
    public void copyFilesFassets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);//??????assets????????????????????????????????????
            if (fileNames.length > 0) {//???????????????
                File file = new File(newPath);
                file.mkdirs();//????????????????????????????????????
                for (String fileName : fileNames) {
                    copyFilesFassets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {//???????????????
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//???????????????????????? buffer??????
                    fos.write(buffer, 0, byteCount);//???????????????????????????????????????
                }
                fos.flush();//???????????????
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }


    /**
     * Gets the content:// URI from the given corresponding path to a file
     * @param context
     * @param imageFile
     * @return content Uri
     */
    public static Uri getImageContentUri(Context context, java.io.File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}