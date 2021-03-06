package com.example.covidtracker.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class DBHelper {
    private FirebaseFirestore dB = FirebaseFirestore.getInstance();
    private String location, time;
    private int x, y;
    /*
    Basic constructor that has coordinates as arguments.
     */
    public DBHelper(int xCoor, int yCoor) {
        x = xCoor ;
        y = yCoor ;
        location = "(" + x + "," + y + ")";
    }
    /*
    A method that creates a location document in the database.
    @param x,y integer coordinates.
    @return boolean check for COVID-19 cases if there is at least one case present in the area.
     */
    public void addCaseToLocation() {
        int temp = getCasesFromLocation();
        temp++;
        HashMap<String, Object> map = new HashMap<>();
        map.put("cases", Integer.toString(temp));
        dB.collection("location").document(location).update(map);
    }


    /*
        retrieves the most recent time from the Location database
     */
    public String getTimeFromLocation(){
        final String[] temp = {null};
        final DocumentReference[] docRef = {dB.collection("location").document(Objects.requireNonNull(location))};
        docRef[0].get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                temp[0] = doc.get("time").toString();
            }
        });
        return temp[0];
    }

    /*
    Creates a document for reading to onto the database.
    @param x, y coordinates to pass.
     */
    public int getCasesFromLocation() {
        final int[] temp = {0}; // Number of users if it doesn't exist.
        // If it exists, set temp to a diff value
        // If it doesn't exists it'll stay to 0.
        // Begin read to database:
        final DocumentReference[] docRef = {dB.collection("location").document(Objects.requireNonNull(location))};
        docRef[0].get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                temp[0] = Integer.parseInt(doc.get("cases").toString());
            }
        });
        // FirebaseFirestore reference = FirebaseFirestore.getInstance();
        return temp[0];
    }
    /*
    Checks for cases in the passed coordinates.
    @param x,y coordinates to be assessed for cases.
    @return boolean check for cases in the area.
     */
    public boolean hasCases() {
        if (getCasesFromLocation() > 0)
            return true;
        return false;
    }
    /*
    Removes a field.
     */
    public void removeField() {
        dB.collection("location").document(location).update(null);
    }
    /*
    Decrements the number of cases at the pinged location.
     */
    public void decrementCases() {
        int temp = getCasesFromLocation();
        temp--;
        if (temp < 1) {
            removeField(); // No unnecessary info in dB.
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("cases", Integer.toString(temp));
        dB.collection("location").document(location).update(map);
    }
}
