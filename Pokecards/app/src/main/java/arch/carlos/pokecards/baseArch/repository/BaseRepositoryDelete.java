package arch.carlos.pokecards.baseArch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;

import arch.carlos.pokecards.baseArch.AppExecutors;
import arch.carlos.pokecards.baseArch.api.ApiResponse;
import arch.carlos.pokecards.baseArch.repository.notImplemented.CacheNotImplemented;
import arch.carlos.pokecards.baseArch.repository.notImplemented.DBNotImplemented;
import arch.carlos.pokecards.baseArch.repository.notImplemented.ExternalServiceNotImplemented;
import arch.carlos.pokecards.baseArch.vo.Resource;

/**
 * Created by mobgen on 9/22/17.
 */

public class BaseRepositoryDelete<OperationResultAPI> {

    // Tells the service to load from these sources for this operation or to ignore the data they contain
    private boolean deleteCache;
    private boolean deleteDB;
    private boolean deleteAPI;

    // Tells the service what kind of services are meant to be used
    private boolean usesLocalStorage;
    private boolean usesExternalService;

    // Executor pool
    private final AppExecutors appExecutors;

    // Implementation of the services
    private final CacheDeleteImpl cache;
    private final DBDeleteImpl database;
    private final ExternalServiceDeleteImpl<OperationResultAPI> externalService;

    public BaseRepositoryDelete(AppExecutors appExecutors, CacheDeleteImpl cacheReadImpl, DBDeleteImpl DBReadImpl, ExternalServiceDeleteImpl<OperationResultAPI> externalExternalServiceReadImpl) {
        this.appExecutors = appExecutors;

        this.cache = cacheReadImpl;
        this.database = DBReadImpl;
        this.externalService = externalExternalServiceReadImpl;

        this.usesLocalStorage = true;
        this.usesExternalService = true;
    }

    public BaseRepositoryDelete(AppExecutors appExecutors, ExternalServiceDeleteImpl<OperationResultAPI> externalExternalServiceReadImpl) {
        this.appExecutors = appExecutors;

        this.cache = new CacheNotImplemented();
        this.database = new DBNotImplemented();
        this.externalService = externalExternalServiceReadImpl;

        this.usesLocalStorage = false;
        this.usesExternalService = true;
    }

    public BaseRepositoryDelete(AppExecutors appExecutors, CacheDeleteImpl cacheReadImpl, DBDeleteImpl DBReadImpl) {
        this.appExecutors = appExecutors;

        this.cache = cacheReadImpl;
        this.database = DBReadImpl;
        this.externalService = new ExternalServiceNotImplemented();

        this.usesLocalStorage = true;
        this.usesExternalService = false;
    }


    /**
     * Delete the data from all implemented sources
     */
    @MainThread
    public void deleteFromAllSources() {
        this.deleteCache = usesLocalStorage;
        this.deleteDB = usesLocalStorage;
        this.deleteAPI = usesExternalService;

        executeDelete();
    }

    /**
     * Delete the data from all specified sources,
     * keeping in mind that this source has to be implemented
     */
    @MainThread
    public void deleteFrom(boolean getDataFromCache, boolean getDataFromDB, boolean getDataFromAPI) {
        this.deleteCache = getDataFromCache && usesLocalStorage;
        this.deleteDB = getDataFromDB && usesLocalStorage;
        this.deleteAPI = getDataFromAPI && usesExternalService;

        executeDelete();

    }

    private void executeDelete() {
        if (deleteCache && cache.CACHE_hasDomainCache()) {
            cache.CACHE_delete();
        }
        if (deleteDB) {
            appExecutors.diskIO().execute(database::BD_delete);

        }
        if (deleteAPI) {
            externalService.API_delete();
        }
    }


    public interface CacheDeleteImpl {

        boolean CACHE_hasDomainCache();

        void CACHE_delete();

    }

    public interface DBDeleteImpl {

        void BD_delete();

    }

    public interface ExternalServiceDeleteImpl<OperationResultAPI> {

        LiveData<ApiResponse<OperationResultAPI>> API_delete();

    }


}
