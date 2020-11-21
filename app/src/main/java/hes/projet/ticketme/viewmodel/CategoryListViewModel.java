package hes.projet.ticketme.viewmodel;

import android.app.Application;
import android.content.Context;
import android.icu.util.ULocale;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.repository.CategoryRepository;

public class CategoryListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<CategoryEntity>> observableCategories;

    public CategoryListViewModel(@NonNull Application application,
                                 CategoryRepository categoryRepository) {
        super(application);

        observableCategories = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableCategories.setValue(null);

        LiveData<List<CategoryEntity>> categories = categoryRepository.getAllCategories();

        // observe the changes of the entities from the database and forward them
        observableCategories.addSource(categories, observableCategories::setValue);
    }


    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final CategoryRepository categoryRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            categoryRepository = CategoryRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CategoryListViewModel(application, categoryRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<CategoryEntity>> getCategories() {
        return observableCategories;
    }
}
