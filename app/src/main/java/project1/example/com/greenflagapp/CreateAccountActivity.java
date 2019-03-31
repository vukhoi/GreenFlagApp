package project1.example.com.greenflagapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class CreateAccountActivity extends AppCompatActivity {
    private TextView tvEmail;
    private TextView tvCreatePwd;
    private TextView tvRepeatPwd;
    private EditText etEmail;
    private EditText etCreatePwd;
    private EditText etRepeatPwd;
    private TextView tvRestrictions;
    private Button  btnNext;
    private ImageView btnToolbarBack;
    private LinearLayout linearLayout;
    private ConstraintLayout clExistedAcc;
    private ConstraintLayout clInvalidPwd;
    private ConstraintLayout clPwdNotMatch;
    private ConstraintLayout clInvalidEmail;
    private ArrayList<View> basicViews;
    private String email;
    private String pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        basicViews = new ArrayList<>();

        linearLayout = findViewById(R.id.ll_create_account);
        btnNext = findViewById(R.id.btn_next);
        btnToolbarBack = findViewById(R.id.iv_back_button);
        setUpViews();

        //populate linear layout for the first time
        populateLinearLayout(null);
    }

    private void setUpViews(){
        tvEmail = setUpTextView("Create Email");
        tvCreatePwd = setUpTextView("Create Password");
        tvRepeatPwd = setUpTextView("Repeat Password");
        tvRestrictions = setUpTextView("Your password should have a minimum of 8\ncharacters and contain at least one\nnumber, one uppercase and one lower\ncase letter. You can use special characters.");

        etEmail = setUpEditText();
        etCreatePwd = setUpEditText();
        etCreatePwd.setHint("See criteria below");
        etRepeatPwd = setUpEditText();

        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ArrayList<ConstraintLayout> errorList = validateNewAccountInput();
                if (errorList == null){
                    DatabaseManager db = new DatabaseManager(getApplicationContext());
                    db.insert(new Account(email, pwd));
                    Intent intent = new Intent(CreateAccountActivity.this, AddAccountDetailActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("pwd", pwd);
                    startActivity(intent);
                }
                else{
                    populateLinearLayout(errorList);
                }
            }
        });

        btnToolbarBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        LayoutInflater factory = getLayoutInflater();
        LinearLayout itemLayout = (LinearLayout) factory.inflate(R.layout.item_layout, null);

        clExistedAcc = setUpConstraintLayout(itemLayout, R.id.cl_existed_acc);
        clInvalidPwd = setUpConstraintLayout(itemLayout, R.id.cl_invalid_pwd);
        clPwdNotMatch = setUpConstraintLayout(itemLayout, R.id.cl_pwd_not_match);
        clInvalidEmail = setUpConstraintLayout(itemLayout, R.id.cl_invalid_email);

    }

    private ConstraintLayout setUpConstraintLayout(LinearLayout itemLayout, int clId){
        ConstraintLayout cl = itemLayout.findViewById(clId);

        LinearLayout.LayoutParams clLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        clLayoutParams.weight = 1;
        return cl;
    }

    // index    errorType
    //  0       email
    //  1       pwd
    // check for email or pwd error
    // alter textedit of those as appropriate
    // return list of warnings, null if there is none
    private ArrayList<ConstraintLayout> validateNewAccountInput(){
        ArrayList<ConstraintLayout> clList = new ArrayList<ConstraintLayout>();
        int errorCount = 0;

        // check if email is valid
        CharSequence email = (CharSequence)etEmail.getText().toString();
        if (!(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())){ // if invalid email
            clList.add(clInvalidEmail);
            Log.d("input validation", "invalid email");
            errorCount += 1;
            setAsError("email");
        }
        else {
            //check if existed account
            DatabaseManager db = new DatabaseManager(getApplicationContext());
            if(db.selectByEmail(email.toString()) != null){ // existed account with similar email
                clList.add(clExistedAcc);
                Log.d("input validation", "existed acc");
                errorCount += 1;
                setAsError("email");
            }
            else{
                clList.add(null);
                this.email = etEmail.getText().toString();
                setAsClear("email");
            }
            // email clear, change background and add tick to it

        }

        String pwd = etCreatePwd.getText().toString();
        // check if pwd is valid
        if (!checkPwd(pwd)){    // if invalid email
            clList.add(clInvalidPwd);
            Log.d("input validation", "invalid pwd");

            errorCount += 1;
            setAsError("pwd");
            setAsError("pwd");
        }
        else{
            String repeatedPwd = etRepeatPwd.getText().toString();
            // check if repeated pwd similar to created one
            if (!pwd.equals(repeatedPwd)){  // if not similar
                clList.add(clPwdNotMatch);
                Log.d("input validation", "pwd mismatch");
                errorCount += 1;
                setAsError("pwd");
                setAsError("pwd");
            }
            else{
                clList.add(null);
                this.pwd = etCreatePwd.getText().toString();
                setAsClear("pwd");
                setAsClear("pwd");
            }
        }

        Log.d("input validation", "error count : " + Integer.toString(errorCount));

        if (errorCount == 0){
            return null;
        }
        else {
            return clList;
        }
    }

    //todo: alter textedit as clear or error for either email or pwd
    private void setAsClear(String type) {
//        if (type.equals("email")) {
//            etEmail.setError("", Drawable.createFromPath("C:\\Users\\Khoi\\Documents\\GreenFlagApp\\app\\src\\main\\res\\drawable\\tick2x.png"));
//            etEmail.setBackground(Drawable.createFromPath("C:\\Users\\Khoi\\Documents\\GreenFlagApp\\app\\src\\main\\res\\drawable\\green_border.xml"));
//        }
//        else{   // pwd
//            etCreatePwd.setError("", Drawable.createFromPath("C:\\Users\\Khoi\\Documents\\GreenFlagApp\\app\\src\\main\\res\\drawable\\tick2x.png"));
//            etCreatePwd.setBackground(Drawable.createFromPath("C:\\Users\\Khoi\\Documents\\GreenFlagApp\\app\\src\\main\\res\\drawable\\green_border.xml"));
//            etRepeatPwd.setError("", Drawable.createFromPath("C:\\Users\\Khoi\\Documents\\GreenFlagApp\\app\\src\\main\\res\\drawable\\tick2x.png"));
//            etRepeatPwd.setBackground(Drawable.createFromPath("C:\\Users\\Khoi\\Documents\\GreenFlagApp\\app\\src\\main\\res\\drawable\\green_border.xml"));
//        }
    }

    private void setAsError(String type) {
//        if (type.equals("email")) {
//            etEmail.setError("", Drawable.createFromPath("C:\\Users\\Khoi\\Documents\\GreenFlagApp\\app\\src\\main\\res\\drawable\\cross2x.png"));
//            etEmail.setBackground(Drawable.createFromPath("C:\\Users\\Khoi\\Documents\\GreenFlagApp\\app\\src\\main\\res\\drawable\\red_border.xml"));
//        }
//        else{   // pwd
//            etCreatePwd.setError("", Drawable.createFromPath("C:\\Users\\Khoi\\Documents\\GreenFlagApp\\app\\src\\main\\res\\drawable\\cross2x.png"));
//            etCreatePwd.setBackground(Drawable.createFromPath("C:\\Users\\Khoi\\Documents\\GreenFlagApp\\app\\src\\main\\res\\drawable\\red_border.xml"));
//            etRepeatPwd.setError("", Drawable.createFromPath("C:\\Users\\Khoi\\Documents\\GreenFlagApp\\app\\src\\main\\res\\drawable\\cross2x.png"));
//            etRepeatPwd.setBackground(Drawable.createFromPath("C:\\Users\\Khoi\\Documents\\GreenFlagApp\\app\\src\\main\\res\\drawable\\red_border.xml"));
//        }
    }


    // check if pwd valid
    private boolean checkPwd(String pwd) {
        // check if pwd is valid
        boolean numberFlag = false;
        boolean lowerFlag = false;
        boolean upperFlag = false;
        char ch;
        for(int i = 0; i < pwd.length(); i++){
            ch = pwd.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                upperFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerFlag = true;
            }
            if(numberFlag && upperFlag && lowerFlag)
                return true;
        }
        return false;
    }

    private EditText setUpEditText() {
        EditText et = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, convertDpTopPixel(10), 0, convertDpTopPixel(10));
        layoutParams.weight = 1;
        et.setLayoutParams(layoutParams);
        et.setBackgroundColor(Color.WHITE);
        return et;
    }

    private void populateLinearLayout(ArrayList<ConstraintLayout> errorList) {
        linearLayout.removeAllViews();
        basicViews.clear();
        Log.d("populate linear layout", "basicView Size before : " + basicViews.size());


        basicViews.add(tvEmail);
        basicViews.add(etEmail);
        basicViews.add(tvCreatePwd);
        basicViews.add(etCreatePwd);
        basicViews.add(tvRepeatPwd);
        basicViews.add(etRepeatPwd);
        basicViews.add(tvRestrictions);

        if (errorList != null){// there are extra things to put in array list
            Log.d("populate linear layout", "errorList size : " + errorList.size());
            int indexShift = 0;
            for(int i = 0; i < errorList.size(); i++){
                if (errorList.get(i) != null) {
                    if (i == 0) {
                        basicViews.add(2, errorList.get(i));
                        Log.d("populate linear layout", "add email error at 2");
                        indexShift += 1;
                    } else if (i == 1) {
                        basicViews.add(6 + indexShift, errorList.get(i));
                        Log.d("populate linear layout", "add pwd error at 6");
                    }
                }
            }
        }

        for (View v: basicViews){
            Log.d("populate linear layout", "added view : " + v.toString() );
            if(v.getParent() != null) { // make v's parent remove v
                ((ViewGroup)v.getParent()).removeView(v);
            }
            linearLayout.addView(v);
        }

        Log.d("populate linear layout", "basicView Size after : " + basicViews.size());

    }


    private TextView setUpTextView(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextColor(Color.WHITE);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, convertDpTopPixel(15), 0, 0);
        layoutParams.weight = 1;
        tv.setLayoutParams(layoutParams);

        if (text.length() < 30) {   // only prompts need larger text size
            tv.setTextSize(26);
        }
        else{
            tv.setTextSize(22);
        }

        return tv;
    }

    private int convertDpTopPixel(int dpMeasure) {
        Resources r = CreateAccountActivity.this.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpMeasure, r.getDisplayMetrics());
    }
}
