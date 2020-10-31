package hes.projet.ticketme.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.repository.CategoryRepository;

public class CategoryViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<CategoryEntity> observableCategory;

    public CategoryViewModel(@NonNull Application application, final Long categoryId, CategoryRepository categoryRepository) {
        super(application);

        Context applicationContext = application.getApplicationContext();

        observableCategory = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableCategory.setValue(null);

        LiveData<CategoryEntity> category = categoryRepository.getCategory(categoryId, applicationContext);

        // observe the changes of the entities from the database and forward them
        observableCategory.addSource(category, observableCategory::setValue);
    }


    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final CategoryRepository categoryRepository;

        private final Long categoryId;

        public Factory(@NonNull Application application, Long categoryId) {
            this.application = application;
            this.categoryId = categoryId;
            categoryRepository = CategoryRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CategoryViewModel(application, categoryId, categoryRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<CategoryEntity> getCategory() {
        return observableCategory;
    }
}
