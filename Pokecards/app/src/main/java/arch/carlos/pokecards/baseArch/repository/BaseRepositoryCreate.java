package arch.carlos.pokecards.baseArch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.graphics.Path;
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

public class BaseRepositoryCreate<OperationResponse> {

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
    private final ExternalServiceCreateImpl<OperationResponse> externalService;

    // Stores the result of the add operation and all the changes
    private final MediatorLiveData<Resource<OperationResponse>> result = new MediatorLiveData<>();

    public BaseRepositoryCreate(AppExecutors appExecutors, CacheCreateImpl cacheReadImpl, DBCreateImpl DBReadImpl, ExternalServiceCreateImpl<OperationResponse> externalExternalServiceReadImpl) {
        this.appExecutors = appExecutors;

        this.cache = cacheReadImpl;
        this.database = DBReadImpl;
        this.externalService = externalExternalServiceReadImpl;

        this.usesLocalStorage = true;
        this.usesExternalService = true;
    }

    public BaseRepositoryCreate(AppExecutors appExecutors, ExternalServiceCreateImpl<OperationResponse> externalExternalServiceReadImpl) {
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
    public LiveData<Resource<OperationResponse>> createToAllSources() {
        this.createInCache = usesLocalStorage;
        this.createInDB = usesLocalStorage;
        this.createInAPI = usesExternalService;

        executeCreate();

        return result;
    }

    /**
     * Delete the data from all specified sources,
     * keeping in mind that this source has to be implemented
     */
    @MainThread
    public LiveData<Resource<OperationResponse>> create(boolean createInCache, boolean createInDB, boolean createInAPI) {
        this.createInCache = createInCache && usesLocalStorage;
        this.createInDB = createInDB && usesLocalStorage;
        this.createInAPI = createInAPI && usesExternalService;

        executeCreate();

        return result;

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

        result.setValue(Resource.success(null));
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
