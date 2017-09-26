package arch.carlos.pokecards.baseArch.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.MainThread;

import arch.carlos.pokecards.baseArch.AppExecutors;
import arch.carlos.pokecards.baseArch.api.ApiResponse;
import arch.carlos.pokecards.baseArch.repository.notImplemented.CacheNotImplemented;
import arch.carlos.pokecards.baseArch.repository.notImplemented.DBNotImplemented;
import arch.carlos.pokecards.baseArch.repository.notImplemented.ExternalServiceNotImplemented;

/**
 * Created by mobgen on 9/22/17.
 */

public class BaseRepositoryCreate<OperationResultAPI> {

    // Tells the service to load from these sources for this operation or to ignore the data they contain
    private boolean createInCache;
    private boolean createInDB;
    private boolean createInAPI;

    // Tells the service what kind of services are meant to be used
    private boolean usesLocalStorage;
    private boolean usesExternalService;

    // Executor pool
    private final AppExecutors appExecutors;

    // Implementation of the services
    private final CacheCreateImpl cache;
    private final DBCreateImpl database;
    private final ExternalServiceCreateImpl<OperationResultAPI> externalService;

    public BaseRepositoryCreate(AppExecutors appExecutors, CacheCreateImpl cacheReadImpl, DBCreateImpl DBReadImpl, ExternalServiceCreateImpl<OperationResultAPI> externalExternalServiceReadImpl) {
        this.appExecutors = appExecutors;

        this.cache = cacheReadImpl;
        this.database = DBReadImpl;
        this.externalService = externalExternalServiceReadImpl;

        this.usesLocalStorage = true;
        this.usesExternalService = true;
    }

    public BaseRepositoryCreate(AppExecutors appExecutors, ExternalServiceCreateImpl<OperationResultAPI> externalExternalServiceReadImpl) {
        this.appExecutors = appExecutors;

        this.cache = new CacheNotImplemented();
        this.database = new DBNotImplemented();
        this.externalService = externalExternalServiceReadImpl;

        this.usesLocalStorage = false;
        this.usesExternalService = true;
    }

    public BaseRepositoryCreate(AppExecutors appExecutors, CacheCreateImpl cacheReadImpl, DBCreateImpl DBReadImpl) {
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
    public void createToAllSources() {
        this.createInCache = usesLocalStorage;
        this.createInDB = usesLocalStorage;
        this.createInAPI = usesExternalService;

        executeCreate();
    }

    /**
     * Delete the data from all specified sources,
     * keeping in mind that this source has to be implemented
     */
    @MainThread
    public void create(boolean createInCache, boolean createInDB, boolean createInAPI) {
        this.createInCache = createInCache && usesLocalStorage;
        this.createInDB = createInDB && usesLocalStorage;
        this.createInAPI = createInAPI && usesExternalService;

        executeCreate();

    }

    private void executeCreate() {

        if (createInCache) {
            cache.CACHE_create();
        }
        if (createInDB) {
            appExecutors.diskIO().execute(database::DB_create);

        }
        if (usesExternalService && createInAPI) {
            externalService.API_create();
        }
    }


    public interface CacheCreateImpl {

        void CACHE_create();

    }

    public interface DBCreateImpl {

        void DB_create();

    }

    public interface ExternalServiceCreateImpl<OperationResponseType> {

        LiveData<ApiResponse<OperationResponseType>> API_create();

    }


}
