package project1.example.com.greenflagapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddAccountDetailActivity extends AppCompatActivity {
    String[] countries;
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
        countries = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};

        setUpViews();
    }


    // if activity is granted access request, pick photo from galery
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
        Log.d("request result", " got result ");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d("activityResult", "attaching image to iv");
        if (requestCode == PICK_FROM_GALLERY){
            if (resultCode == RESULT_OK){
                photoURI = getRealPathFromUri(AddAccountDetailActivity.this, data.getData());
                Log.d("photo path", photoURI);
                // display image in iv_acc_photo
                try {
                    InputStream in = new FileInputStream(photoURI);
                    Bitmap photo = BitmapFactory.decodeStream(in);
                    ivAccPhoto.setImageBitmap(photo);
                } catch (IOException e) {
                    Log.e("Error IO", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
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
        etAge.setText("-1");
        etUserName.setText(accEmail);
        etPwd.setText(accPwd);

        etPostalAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(AddAccountDetailActivity.this, android.R.layout.simple_spinner_item, countries);
                final Spinner spinnerPostalAddress = new Spinner(AddAccountDetailActivity.this);
                spinnerPostalAddress.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                spinnerPostalAddress.setAdapter(countryAdapter);
                spinnerPostalAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        etPostalAddress.setText(spinnerPostalAddress.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        //do nothing
                    }
                });

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddAccountDetailActivity.this);
                alertDialogBuilder.setView(spinnerPostalAddress);
                alertDialogBuilder.create().show();

            }
        });


        // attach photo to image view
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
                Intent intent = new Intent(AddAccountDetailActivity.this, ListAccountActivity.class);
                intent.putExtra("email", accEmail);
                startActivity(intent);
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

    private void getGender() {
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId != -1){  // no radio button selected
            gender = ((RadioButton)findViewById(selectedId)).getText().toString();
        }
    }

    private void updateETBirthDate(Calendar calendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etBirthDate.setText(sdf.format(calendar.getTime()));
    }

}
