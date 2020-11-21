package hes.projet.ticketme.data.repository;

import android.content.Context;
import android.icu.util.ULocale;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;
import java.util.List;

import hes.projet.ticketme.data.AppDatabase;
import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.firebase.CategoryListLiveData;
import hes.projet.ticketme.data.firebase.TicketLiveData;

public class CategoryRepository {

    private static CategoryRepository instance;

    /**
     * Private constructor for singleton
     */
    private CategoryRepository() {}

    /**
     * Public method to get an instance of this repository
     *
     * @return TicketRepository singleton instance
     */
    public static CategoryRepository getInstance() {
        if (instance == null) {
            synchronized (CategoryRepository.class) {
                if (instance == null) {
                    instance = new CategoryRepository();
                }
            }
        }
        return instance;
    }


    public LiveData<CategoryEntity> getCategory(final String id) {
        return null;
//        return AppDatabase.getInstance(context).categoryDao().getById(id);
    }

    /**
     * TODO Vérifier si getAllCategoies nécessite un id? je l'ai supprimé
     * @param
     * @return
     */

    public LiveData<List<CategoryEntity>> getAllCategories() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("categories");
        return new CategoryListLiveData(reference);
    }

}
