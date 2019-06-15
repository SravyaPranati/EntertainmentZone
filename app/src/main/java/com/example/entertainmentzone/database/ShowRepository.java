package com.example.entertainmentzone.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ShowRepository  {

    private ShowDao showDao;
    private LiveData<List<ShowEntity>> allShows;

    public ShowRepository(Application application)
    {
        ShowDatabase mdb = ShowDatabase.getDatabase(application);
        showDao = mdb.showDao();
        allShows = showDao.getAllFavShows();
    }
    public LiveData<List<ShowEntity>> getAllShows(){
        return allShows;
    }
    public void insert(ShowEntity showEntity)
    {
        new InsertAsyncTask(showDao).execute(showEntity);
    }
    public void delete(ShowEntity showEntity)
    {
        new DeleteAsyncTask(showDao).execute(showEntity);
    }

    public ShowEntity checkMovie(String id)
    {
        return showDao.checkMovie(id);
    }


    public static class DeleteAsyncTask extends AsyncTask<ShowEntity,Void,Void>
    {
        private ShowDao showDao;

        public DeleteAsyncTask(ShowDao showDao) {
            this.showDao = showDao;
        }

        @Override
        protected Void doInBackground(ShowEntity... showEntities) {
            showDao.delete(showEntities[0]);
            return null;
        }
    }

    public static class InsertAsyncTask extends AsyncTask<ShowEntity,Void,Void>
    {
        private ShowDao showDao;

        public InsertAsyncTask(ShowDao showDao) {
            this.showDao = showDao;
        }

        @Override
        protected Void doInBackground(ShowEntity... showEntities) {
            showDao.insert(showEntities[0]);
            return null;
        }
    }
}
