package project1.example.com.greenflagapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private ArrayList<View> basicViews;


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
                if (validateNewAccountInput()){
                    Intent intent = new Intent(CreateAccountActivity.this, AddAccountDetailActivity.class);
                    startActivity(intent);
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
        @SuppressLint("InflateParams") LinearLayout itemLayout = (LinearLayout) factory.inflate(R.layout.item_layout, null);
        clExistedAcc = itemLayout.findViewById(R.id.cl_existed_acc);
        clInvalidPwd = itemLayout.findViewById(R.id.cl_invalid_pwd);
        clPwdNotMatch = itemLayout.findViewById(R.id.cl_pwd_not_match);

        basicViews.add(tvEmail);
        basicViews.add(etEmail);
        basicViews.add(tvCreatePwd);
        basicViews.add(etCreatePwd);
        basicViews.add(tvRepeatPwd);
        basicViews.add(etRepeatPwd);
        basicViews.add(tvRestrictions);
    }

    private boolean validateNewAccountInput(){
        //

        return true;
    }

    private EditText setUpEditText() {
        EditText et = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        et.setLayoutParams(layoutParams);
        et.setBackgroundColor(Color.WHITE);
        return et;
    }

    private void populateLinearLayout(ArrayList<View> viewList) {

        if (viewList == null){ // populate linear layout first time
            for (View v: basicViews){
                Log.d("populate linear layout", "added view : " + v.toString() );
                if(v.getParent() != null) { // make v's parent remove v
                    ((ViewGroup)v.getParent()).removeView(v);
                }
                linearLayout.addView(v);
            }
        }
        else{       // there are extra things to put in array list

        }
    }


    private TextView setUpTextView(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextColor(Color.WHITE);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, convertDpTopPixel(20), 0, 0);
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
