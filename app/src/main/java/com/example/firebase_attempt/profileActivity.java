package com.example.firebase_attempt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileActivity extends AppCompatActivity {
    private Button logout;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //logout functionality
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profileActivity.this, MainActivity.class));
            }
        });

        //section to display the user information from our firebase database
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid(); //uid is the firebase unique user code

        final TextView nameView = (TextView) findViewById(R.id.nameDisplay);
        final TextView emailView = (TextView) findViewById(R.id.emailDisplay);
        final TextView ageView = (TextView) findViewById(R.id.ageDisplay);

        //getting data from the firebase real time database
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProf = snapshot.getValue(User.class);

                if(userProf !=null){
                    String fullName = userProf.fullName;
                    String email = userProf.email;
                    String age = userProf.age;

                    nameView.setText(fullName);
                    emailView.setText(email);
                    ageView.setText(age);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profileActivity.this, "Something Wrong happened, contact sys admin", Toast.LENGTH_SHORT).show();
            }
        });
    }
}