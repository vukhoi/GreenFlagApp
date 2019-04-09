package project1.example.com.greenflagapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import project1.example.com.greenflagapp.R;
import project1.example.com.greenflagapp.model.Account;
import project1.example.com.greenflagapp.model.DatabaseManager;

public class ListAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_account);

        // username, photo
        if (!getIntent().getStringExtra("email").equals("test")){
            DatabaseManager db = new DatabaseManager(getApplicationContext());
            Account currentAcc = db.selectByEmail(getIntent().getStringExtra("email"));
            String userName = currentAcc.getUsername();
            String photoPath = currentAcc.getPhoto();
            ((TextView)findViewById(R.id.tv_current_acc)).setText("Welcome " + userName);
            Log.d("currentAcc",currentAcc.toString());
            Log.d("photoPath", photoPath);


            try {
                InputStream in = new FileInputStream(photoPath);
                Bitmap photo = BitmapFactory.decodeStream(in);
                ((ImageView)findViewById(R.id.iv_current_acc)).setImageBitmap(photo);
            } catch (IOException e) {
                Log.e("Error IO", e.getMessage());
                e.printStackTrace();
            }
        }



        DatabaseManager db = new DatabaseManager(getApplicationContext());

        ArrayList<Account> accountList = db.selectAll();

        LinearLayoutManager llManager = new LinearLayoutManager(ListAccountActivity.this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(llManager);
        recyclerView.setAdapter(new CustomAdapter(accountList));

        Button btnClrDb = findViewById(R.id.btn_clr_db);
        btnClrDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseManager db = new DatabaseManager(getApplicationContext());
                db.clearDb();
                Intent intent = new Intent(ListAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{
        ArrayList<Account> accountArrayList;

        public CustomAdapter(ArrayList<Account> accountArrayList) {
            this.accountArrayList = accountArrayList;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater factory = getLayoutInflater();
            LinearLayout itemLayout = (LinearLayout) factory.inflate(R.layout.item_layout, parent, false);
            ConstraintLayout cl = itemLayout.findViewById(R.id.cl_item_layout);
            if (cl.getParent() != null) {
                ((ViewGroup) cl.getParent()).removeView(cl);
            }
            return new CustomViewHolder(cl);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            Account currentAcc = accountArrayList.get(position);
            holder.tvName.setText(changeNullToEmptyStr(currentAcc.getName()));
            holder.tvUserName.setText(changeNullToEmptyStr(currentAcc.getUsername()));
            holder.tvPostalAddress.setText(changeNullToEmptyStr(currentAcc.getPostalAddress()));
            holder.tvGender.setText(changeNullToEmptyStr(currentAcc.getGender()));
            holder.tvBirthDate.setText(changeNullToEmptyStr(currentAcc.getBirthDate()));
            holder.tvEmail.setText(changeNullToEmptyStr(currentAcc.getEmail()));
            holder.tvAge.setText(changeNullToEmptyStr(Integer.toString(currentAcc.getAge())));

            String photoURI = currentAcc.getPhoto();
            // display image in iv_acc_photo
            try {
                InputStream in = new FileInputStream(photoURI);
                Bitmap photo = BitmapFactory.decodeStream(in);
                holder.ivAccPhoto.setImageBitmap(photo);
            } catch (Exception e) {
                Log.e("Error attaching photo", e.getMessage());
                e.printStackTrace();
            }
        }

        private String changeNullToEmptyStr(String text) {
            if (text.equals("null") || text.equals("-1")){
                return "";
            }
            return text;
        }

        @Override
        public int getItemCount() {
            return accountArrayList.size();
        }
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvUserName;
        TextView tvPostalAddress;
        TextView tvGender;
        TextView tvBirthDate;
        TextView tvAge;
        TextView tvEmail;
        ImageView ivAccPhoto;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvPostalAddress = itemView.findViewById(R.id.tv_postal_address);
            tvGender = itemView.findViewById(R.id.tv_gender);
            tvBirthDate = itemView.findViewById(R.id.tv_birth_date);
            tvAge = itemView.findViewById(R.id.tv_age);
            tvEmail = itemView.findViewById(R.id.tv_email);
            ivAccPhoto = itemView.findViewById(R.id.iv_acc_photo);
        }
    }
}

