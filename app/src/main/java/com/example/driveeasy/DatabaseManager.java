

package com.example.driveeasy;
//This activity is used to have a dynamic input for database for ease of modification
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseManager extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteafterupdate);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onStart() {
        TextInputLayout name = findViewById(R.id.Name);

        TextInputLayout b1 = findViewById(R.id.NoS);
        TextInputLayout b2 = findViewById(R.id.NumPlate);
        TextInputLayout d1 = findViewById(R.id.FT);
        TextInputLayout d2 = findViewById(R.id.ToC);
        TextInputLayout c1 = findViewById(R.id.Price);
        //TextInputLayout c2 = findViewById(R.id.C2);
        Button next=findViewById(R.id.next);
        FloatingActionButton back = findViewById(R.id.floatingActionButton);

        super.onStart();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Cars");
                //ref= reference.getDatabase().getReference(name.getEditText().getText().toString());
                ref=reference.child(name.getEditText().getText().toString());
                DatabaseHelperClass HelperClass = new DatabaseHelperClass(name.getEditText().getText().toString(),b1.getEditText().getText().toString(), b2.getEditText().getText().toString(), d1.getEditText().getText().toString(), d2.getEditText().getText().toString(),c1.getEditText().getText().toString());
                ref.setValue(HelperClass);
                createAccount(name.getEditText().getText().toString(), "nolol");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatabaseManager.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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
                            Toast.makeText(DatabaseManager.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                            reload();
                        }
                    }
                });
        // [END create_user_with_email]
    }
    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(DatabaseManager.this, OTP.class);
        startActivity(intent);
        finish();

    }
    private void reload(){
        Intent intent = new Intent(DatabaseManager.this, SignUp.class);
        startActivity(intent);
        finish();
    }
}

