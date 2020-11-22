package hes.projet.ticketme.data.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.entity.TicketEntity;

public class CategoryListLiveData extends LiveData<List<String>> {

    private static final String TAG = "CategoryListLiveData";

    private final DatabaseReference reference;
    private final CategoryListLiveData.MyValueEventListener listener = new CategoryListLiveData.MyValueEventListener();


    public CategoryListLiveData(DatabaseReference ref) {
        reference = ref;
    }


    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.e(TAG, "Query with results " + reference);
            setValue(toCategoryList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<String> toCategoryList(DataSnapshot snapshot) {
        List<String> categories = new ArrayList<>();

        for (DataSnapshot childSnapshot : snapshot.getChildren()) {

//            CategoryEntity entity = childSnapshot.getValue(CategoryEntity.class);
//
//            Log.e(TAG, "Category found " + childSnapshot.getKey());
//
//            entity.setName(childSnapshot.getKey());
            categories.add(childSnapshot.getKey());
        }

        return categories;
    }

}
