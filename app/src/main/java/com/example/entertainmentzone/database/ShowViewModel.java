package com.example.entertainmentzone.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ShowViewModel extends AndroidViewModel {
    private ShowRepository sRepository;
    private LiveData<List<ShowEntity>> mAllShows;

    public ShowViewModel(@NonNull Application application) {
        super(application);
        sRepository = new ShowRepository(application);
        mAllShows = sRepository.getAllShows();
    }

    public LiveData<List<ShowEntity>> getAllShows()
    {
        return mAllShows;
    }
    public  void insert(ShowEntity showEntity)
    {
        sRepository.insert(showEntity);
    }
    public ShowEntity checkMovie(String id)
    {
        return sRepository.checkMovie(id);
    }
    public void delete(ShowEntity showEntity)
    {
        sRepository.delete(showEntity);
    }

}
