package arch.carlos.pokecards.baseArch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import arch.carlos.pokecards.baseArch.AppExecutors;
import arch.carlos.pokecards.baseArch.api.ApiResponse;
import arch.carlos.pokecards.baseArch.repository.notImplemented.CacheNotImplemented;
import arch.carlos.pokecards.baseArch.repository.notImplemented.ExternalServiceNotImplemented;
import arch.carlos.pokecards.baseArch.repository.notImplemented.DBNotImplemented;
import arch.carlos.pokecards.baseArch.vo.Resource;

/**
 * Created by mobgen on 9/22/17.
 */

public class BaseRepositoryRead<TypeInternal, TypeAPI> {

    // Tells the service to load from these sources for this operation or to ignore the data they contain
    private boolean loadFromCache;
    private boolean loadFromDB;
    private boolean loadFromAPI;

    // Tells the service what kind of services are meant to be used
    private boolean usesLocalStorage;
    private boolean usesExternalService;

    // Executor pool
    private final AppExecutors appExecutors;

    // Implementation of the services
    private final CacheReadImpl<TypeInternal> cache;
    private final DBReadImpl<TypeInternal> DB;
    private final ExternalServiceReadImpl<TypeInternal, TypeAPI> externalService;

    // Stores the result and all the states while it's being retrieved
    private final MediatorLiveData<Resource<TypeInternal>> result = new MediatorLiveData<>();


    public BaseRepositoryRead(AppExecutors appExecutors, CacheReadImpl<TypeInternal> cacheReadImpl, DBReadImpl<TypeInternal> DBReadImpl, ExternalServiceReadImpl<TypeInternal, TypeAPI> externalExternalServiceReadImpl) {
        this.appExecutors = appExecutors;

        this.cache = cacheReadImpl;
        this.DB = DBReadImpl;
        this.externalService = externalExternalServiceReadImpl;

        this.usesLocalStorage = true;
        this.usesExternalService = true;
    }

    public BaseRepositoryRead(AppExecutors appExecutors, ExternalServiceReadImpl<TypeInternal, TypeAPI> externalExternalServiceReadImpl) {
        this.appExecutors = appExecutors;

        this.cache = new CacheNotImplemented();
        this.DB = new DBNotImplemented();
        this.externalService = externalExternalServiceReadImpl;

        this.usesLocalStorage = false;
        this.usesExternalService = true;
    }

    public BaseRepositoryRead(AppExecutors appExecutors, CacheReadImpl<TypeInternal> cacheReadImpl, DBReadImpl<TypeInternal> DBReadImpl) {
        this.appExecutors = appExecutors;

        this.cache = cacheReadImpl;
        this.DB = DBReadImpl;
        this.externalService = new ExternalServiceNotImplemented();

        this.usesLocalStorage = true;
        this.usesExternalService = false;
    }


    /**
     * Retrieves the data from all sources implemented
     *
     * @return The resource requested
     */
    @MainThread
    public LiveData<Resource<TypeInternal>> getFromAllSources() {
        this.loadFromCache = usesLocalStorage;
        this.loadFromDB = usesLocalStorage;
        this.loadFromAPI = usesExternalService;

        executeGet();

        return result;
    }

    /**
     * Uses only the data retrieved from the the sources specified,
     * keeping in mind that this source has to be implemented
     *
     * @return The resource requested
     */
    @MainThread
    public LiveData<Resource<TypeInternal>> getFrom(boolean getDataFromCache, boolean getDataFromDB, boolean getDataFromAPI) {
        this.loadFromCache = getDataFromCache && usesLocalStorage;
        this.loadFromDB = getDataFromDB && usesLocalStorage;
        this.loadFromAPI = getDataFromAPI && usesExternalService;

        executeGet();

        return result;
    }

    private void executeGet() {
        result.setValue(Resource.loading(null));

        if (loadFromCache && cache.CACHE_hasDomainCache()) {
            getFromCache();
        }
        if (loadFromDB) {
            getFromDB();
        }
        if (loadFromAPI) {
            getFromAPI();
        }

    }

    private void getFromCache() {
        LiveData<TypeInternal> cacheSource = cache.CACHE_get();
        result.addSource(cacheSource, cacheData -> {
            result.removeSource(cacheSource);
            if (loadFromDB || loadFromAPI) {
                result.addSource(cacheSource, newData -> result.setValue(Resource.loading(newData)));
            } else {
                result.addSource(cacheSource, newData -> result.setValue(Resource.success(newData)));
            }
        });
    }

    private void getFromDB() {
        LiveData<TypeInternal> dbSource = DB.BD_get();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (loadFromAPI) {
                result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
                result.removeSource(dbSource);
                getFromAPI();
            } else {
                result.addSource(dbSource, newData -> result.setValue(Resource.success(newData)));
            }
        });
    }

    private void getFromAPI() {
        LiveData<ApiResponse<TypeAPI>> apiResponse = externalService.API_get();
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            if (response != null && response.isSuccessful()) {
                handleApiResponse(externalService.API_proccesToInternal(response));
            } else {
                handleApiError(response);
            }
        });
    }

    private void handleApiResponse(TypeInternal response) {
        appExecutors.diskIO().execute(() -> {
            if (usesLocalStorage) {
                //We store the results in the local storage
                saveCallResult(response);
                appExecutors.mainThread().execute(() -> {
                    //We get the refreshed local data
                    LiveData<TypeInternal> cacheSource = cache.CACHE_get();
                    result.addSource(cacheSource, cacheData -> {
                        result.removeSource(cacheSource);
                        // And relink the livedata to the source to create a success call on the viewmodel
                        result.addSource(cacheSource, newData -> result.setValue(Resource.success(newData)));
                    });
                });
            } else {
                appExecutors.mainThread().execute(() ->
                        //Set the the data as the API response
                        result.setValue(Resource.success(response)));
            }

        });
    }

    private void saveCallResult(TypeInternal response) {
        DB.BD_save(response);
        cache.CACHE_save(response);
    }

    private void handleApiError(ApiResponse<TypeAPI> errorResponse) {

        externalService.API_formatError(result, errorResponse.errorMessage, errorResponse.code);
    }

    public interface CacheReadImpl<TypeInternal> {

        boolean CACHE_hasDomainCache();

        LiveData<TypeInternal> CACHE_get();

        void CACHE_save(TypeInternal item);

    }

    public interface DBReadImpl<TypeInternal> {

        LiveData<TypeInternal> BD_get();

        void BD_save(@NonNull TypeInternal item);

    }

    public interface ExternalServiceReadImpl<TypeInternal, TypeAPI> {

        LiveData<ApiResponse<TypeAPI>> API_get();

        TypeInternal API_proccesToInternal(ApiResponse<TypeAPI> response);

        void API_formatError(MutableLiveData<Resource<TypeInternal>> partialData, String errorMessage, int errorCode);

    }


}
