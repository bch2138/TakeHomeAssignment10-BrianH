package com.example.uhylabr.car;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.quickstart.auth.EmailPasswordActivity;
import com.google.firebase.quickstart.auth.R;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference carRef = mDatabase.getReference("Car");
    private DatabaseReference myCarRef = mDatabase.getReference("Cars");
    private TextView displayText;
    private ArrayList<Car> carList = new ArrayList<>();
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    mAuth = FirebaseAuth.getInstance();}

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);


    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                updateUI(null);
            }

            // ...
        }
    });

    mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.getException());
                Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                updateUI(null);
            }

            // ...
        }
    });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();}}

    public void addCar(View view) {
        String id = UUID.randomUUID().toString();
        Car c = new Car(id, "Honda","Civic", 2000, false);
        carList.add(c);
        carRef.child(id).setValue(c);
        carRef.push().setValue(c);
    }

    public void removeCar(View view) {
        if (carList.size() == 0) return;
        Car c = carList.remove(0);
        carRef.child(c.id).removeValue();


    displayText = (TextView) findViewById(R.id.display_text);}

    private void displayCars() {
        String text = "";
        for (Car c : carList)
            text += c + "\n";
        displayText.setText(text);

        carRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                carList.add(dataSnapshot.getValue(Car.class));
                displayCars();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(MainActivity.this, dataSnapshot.getValue(Car.class) + " has changed", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(MainActivity.this, dataSnapshot.getValue(Car.class) + " is removed", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });}


/*
    carRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Car c = dataSnapshot.getValue(Car.class);
            displayText.setText(c.toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(MainActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();

        }
    });

    }*/



    //public void set (View view){carRef.setValue(new Car("","Honda", "Civic", 2000, false));
    //}

    //public void setMyCarRef (View view){myCarRef.setValue(new Car("","Toyota", "Prius", 2015, true));
        }
//}
