package com.moutamid.sra_admin;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Constants {

    public static FirebaseAuth auth() {
        return FirebaseAuth.getInstance();
    }

    public static DatabaseReference databaseReference() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("SRA");
        db.keepSynced(true);
        return db;
    }

    public static StorageReference storageReference(String auth){
        StorageReference sr = FirebaseStorage.getInstance().getReference().child("SRA").child(auth);
        return sr;
    }
}
