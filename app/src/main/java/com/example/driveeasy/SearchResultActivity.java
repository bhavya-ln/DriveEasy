package com.example.driveeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

//import com.google.api.core.ApiFuture;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.Firestore;
//import com.google.cloud.firestore.FirestoreOptions;
//import com.google.cloud.firestore.QueryDocumentSnapshot;
//import com.google.cloud.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

class SearchResultsActivity extends Activity {
//    FirestoreOptions firestoreOptions =
//            FirestoreOptions.getDefaultInstance().toBuilder()
//                    .setProjectId("driveeasy-f0915")
//                    .setCredentials(GoogleCredentials.getApplicationDefault())
//                    .build();
//    Firestore db = firestoreOptions.getService();

    SearchResultsActivity() throws IOException {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(SearchResultsActivity.this,query,Toast.LENGTH_SHORT).show();

            //use the query to search your data somehow
//            ApiFuture<QuerySnapshot> query = db.collection("Location").get();
//// ...
//// query.get() blocks on response
//            QuerySnapshot querySnapshot = null;
//            try {
//                querySnapshot = query.get();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
//            for (QueryDocumentSnapshot document : documents) {
//                System.out.println("City: " + document.getId());
//                System.out.println("Locality: " + document.getString(q));
//
//            }
        }
    }
}
