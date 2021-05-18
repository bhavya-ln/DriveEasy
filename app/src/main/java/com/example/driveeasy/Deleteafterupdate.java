


package com.example.driveeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Deleteafterupdate extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteafterupdate);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onStart() {
        TextInputLayout loc = findViewById(R.id.Location);
        TextInputLayout b1 = findViewById(R.id.B1);
        TextInputLayout b2 = findViewById(R.id.B2);
        TextInputLayout d1 = findViewById(R.id.D1);
        TextInputLayout d2 = findViewById(R.id.D2);
        TextInputLayout c1 = findViewById(R.id.C1);
        TextInputLayout c2 = findViewById(R.id.C2);
        Button next=findViewById(R.id.next);
        FloatingActionButton back = findViewById(R.id.floatingActionButton);

        super.onStart();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference(loc.getEditText().getText().toString());
                DeleteHelperClass HelperClass = new DeleteHelperClass(loc.getEditText().getText().toString(),b1.getEditText().getText().toString(), b2.getEditText().getText().toString(), d1.getEditText().getText().toString(), d2.getEditText().getText().toString(),c1.getEditText().getText().toString(),c2.getEditText().getText().toString());
                createAccount(loc.getEditText().getText().toString(), "nolol");
                reference.setValue(HelperClass);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.driveeasy.Deleteafterupdate.this, SignIn.class);
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
                            Toast.makeText(Deleteafterupdate.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                            reload();
                        }
                    }
                });
        // [END create_user_with_email]
    }
    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(Deleteafterupdate.this, OTP.class);
        startActivity(intent);
        finish();

    }
    private void reload(){
        Intent intent = new Intent(Deleteafterupdate.this, SignUp.class);
        startActivity(intent);
        finish();
    }
}

