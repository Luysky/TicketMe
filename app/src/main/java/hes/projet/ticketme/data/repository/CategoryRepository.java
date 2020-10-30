package hes.projet.ticketme.data.repository;

import android.content.Context;
import android.icu.util.ULocale;

import androidx.lifecycle.LiveData;

import java.util.List;

import hes.projet.ticketme.data.AppDatabase;
import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.entity.TicketEntity;

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


    public LiveData<CategoryEntity> getCategory(final Long id, Context context) {
        return AppDatabase.getInstance(context).categoryDao().getById(id);
    }

    public LiveData<List<CategoryEntity>> getAllCategories(Context context) {
        return AppDatabase.getInstance(context).categoryDao().getAll();
    }

}
