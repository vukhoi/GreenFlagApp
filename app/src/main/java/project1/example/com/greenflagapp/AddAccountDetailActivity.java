package project1.example.com.greenflagapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddAccountDetailActivity extends AppCompatActivity {
    private static final int PICK_FROM_GALLERY = 1;
    String accEmail;
    String accPwd;
    String photoURI;
    EditText etName;
    EditText etUserName;
    EditText etPwd;
    Button btnPhoto;
    Button btnChooseBirthDate;
    EditText etBirthDate;
    EditText etAge;
    RadioGroup rgGender;
    String gender;
    EditText etPostalAddress;
    ImageView ivAccPhoto;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_detail);
        Intent intent = getIntent();
        accEmail = intent.getStringExtra("email");
        accPwd = intent.getStringExtra("pwd");


        setUpViews();
    }


    // if activity is granted access request, pick photo from galery
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    Toast.makeText(AddAccountDetailActivity.this, "no permission to access galery", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    // after user selected photo, turn photo into bitmap, and attach it to image view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_GALLERY){
            if (resultCode == RESULT_OK){
                photoURI = data.getData().toString();
                // display image in iv_acc_photo
                try {
                    InputStream in = new java.net.URL(photoURI).openStream();
                    Bitmap photo = BitmapFactory.decodeStream(in);
                    ivAccPhoto.setImageBitmap(photo);
                } catch (Exception e) {
                    Log.e("Error attaching photo", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private void setUpViews() {

        etName = findViewById(R.id.et_name);
        etUserName = findViewById(R.id.et_user_name);
        etPwd = findViewById(R.id.et_pwd);
        btnPhoto = findViewById(R.id.btn_change_photo);
        btnChooseBirthDate = findViewById(R.id.btn_choose_birth_date);
        etBirthDate = findViewById(R.id.et_birth_date);
        etAge = findViewById(R.id.et_age);
        ivAccPhoto = findViewById(R.id.iv_acc_photo);
        rgGender = findViewById(R.id.rg_gender);
        etPostalAddress = findViewById(R.id.et_postal_address);
        photoURI = "";
        btnSave = findViewById(R.id.btn_save);

        etName.setText("");
        etBirthDate.setText("");
        etUserName.setText(accEmail);
        etPwd.setText(accPwd);


        btnPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                try {
                    if (ActivityCompat.checkSelfPermission(AddAccountDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddAccountDetailActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        // select birth date
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateETBirthDate(calendar);
            }

        };
        btnChooseBirthDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddAccountDetailActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateDB();
            }
        });
    }

    private void updateDB() {
        getGender();
        DatabaseManager db = new DatabaseManager(AddAccountDetailActivity.this);
        Account acc = new Account(etName.getText().toString(), etUserName.getText().toString(), etPwd.getText().toString(), photoURI
                , accEmail, Integer.parseInt(etAge.getText().toString()), etBirthDate.getText().toString(), gender, etPostalAddress.getText().toString());
        Log.d("update db" , "name : " + etName.getText().toString() );
        Log.d("update db" , "username : " + etUserName.getText().toString() );
        Log.d("update db" , "pwd : " + etPwd.getText().toString() );
        Log.d("update db" , "email : " + accEmail );
        Log.d("update db" , "birth date : " + etBirthDate.getText().toString() );
        Log.d("update db" , "gender : " + gender );
        Log.d("update db" , "photo : " + photoURI );
        Log.d("update db" , "postal address : " + etPostalAddress.getText().toString() );
        db.updateByEmail(acc, accEmail);
    }

    private String getGender() {
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId == -1){  // no radio button selected
            return "";
        }
        return((RadioButton)findViewById(selectedId)).getText().toString();
    }

    private void updateETBirthDate(Calendar calendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etBirthDate.setText(sdf.format(calendar.getTime()));
    }

}
