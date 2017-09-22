package arch.carlos.pokecards.baseArch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import arch.carlos.pokecards.baseArch.AppExecutors;
import arch.carlos.pokecards.baseArch.api.ApiResponse;
import arch.carlos.pokecards.baseArch.utils.RepositoryOperationNotImplementedException;
import arch.carlos.pokecards.baseArch.vo.Resource;

/**
 * Created by mobgen on 9/22/17.
 */

public class BaseReadFromRepository<TypeInternal, TypeAPI> {

    private boolean fetchFromBD;
    private boolean fetchFromAPI;
    private boolean usesLocalStorage;
    private boolean usesExternalService;
    private final AppExecutors appExecutors;
    private final LocalStorageImpl<TypeInternal> localStorage;
    private final ExternalServiceImpl<TypeInternal, TypeAPI> externalService;

    private final MediatorLiveData<Resource<TypeInternal>> result = new MediatorLiveData<>();


    public BaseReadFromRepository(AppExecutors appExecutors, LocalStorageImpl<TypeInternal> localStorageImpl, ExternalServiceImpl<TypeInternal, TypeAPI> externalExternalServiceImpl) {
        this.appExecutors = appExecutors;
        this.localStorage = localStorageImpl;
        this.externalService = externalExternalServiceImpl;
        this.usesLocalStorage = true;
        this.usesExternalService = true;
    }

    public BaseReadFromRepository(AppExecutors appExecutors, ExternalServiceImpl<TypeInternal, TypeAPI> externalExternalServiceImpl) {
        this.appExecutors = appExecutors;
        this.localStorage = new LocalStorageImpl<TypeInternal>() {
            @Override
            public void BD_save(@NonNull TypeInternal item) {
                throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
            }

            @Override
            public LiveData<TypeInternal> BD_get() {
                throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
            }

            @Override
            public String CACHE_getDomainKey() {
                throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
            }

            @Override
            public boolean CACHE_hasDomainCache() {
                throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
            }

            @Override
            public void CACHE_save(TypeInternal item) {
                throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
            }

            @Override
            public LiveData<TypeInternal> CACHE_get() {
                throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
            }
        };
        this.externalService = externalExternalServiceImpl;
        this.usesLocalStorage = false;
        this.usesExternalService = true;
    }

    public BaseReadFromRepository(AppExecutors appExecutors, LocalStorageImpl<TypeInternal> localStorageImpl) {
        this.appExecutors = appExecutors;
        this.localStorage = localStorageImpl;
        this.externalService = new ExternalServiceImpl<TypeInternal, TypeAPI>() {
            @Override
            public LiveData<ApiResponse<TypeAPI>> API_get() {
                throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
            }

            @Override
            public TypeInternal API_proccesToInternal(ApiResponse<TypeAPI> response) {
                throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
            }

            @Override
            public void API_formatError(MutableLiveData<Resource<TypeInternal>> partialData, String errorMessage, int errorCode) {
                throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
            }
        };
        this.usesLocalStorage = true;
        this.usesExternalService = false;
    }

    @MainThread
    public LiveData<Resource<TypeInternal>> getLiveData(boolean fetchFromAPI, boolean fetchFromBD) {
        this.fetchFromAPI = fetchFromAPI;
        this.fetchFromBD = fetchFromBD;
        executeGet();
        return result;
    }

    private void executeGet() {
        result.setValue(Resource.loading(null));
        if(usesLocalStorage){
            if (localStorage.CACHE_hasDomainCache()) {
                fetchFromCache();
            } else {
                fetchFromDB();
            }
        }else{
            fetchFromAPI();
        }
    }

    private void fetchFromCache() {
        LiveData<TypeInternal> cacheSource = localStorage.CACHE_get();
        result.addSource(cacheSource, cacheData -> {
            result.removeSource(cacheSource);
            if (fetchFromBD) {
                result.addSource(cacheSource, newData -> result.setValue(Resource.loading(newData)));
                fetchFromDB();
            } else {
                result.addSource(cacheSource, newData -> result.setValue(Resource.success(newData)));
            }
            //result.setValue(Resource.loading(cacheData));
        });
    }

    private void fetchFromDB() {
        LiveData<TypeInternal> dbSource = localStorage.BD_get();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (usesExternalService && fetchFromAPI) {
                result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
                result.removeSource(dbSource);
                fetchFromAPI();
            } else {
                result.addSource(dbSource, newData -> result.setValue(Resource.success(newData)));
            }
        });
    }

    private void fetchFromAPI() {
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
            if(usesLocalStorage){
                //We store the results in the local storage
                saveCallResult(response);
                appExecutors.mainThread().execute(() ->
                        //And relink the result livedata to the refreshed local storage
                        result.addSource(localStorage.CACHE_get(),
                                newData -> result.setValue(Resource.success(newData)))
                );
            }else{
                appExecutors.mainThread().execute(() ->
                        //Set the the data as the API response
                        result.setValue(Resource.success(response)));
            }

        });
    }

    private void saveCallResult(TypeInternal response) {
        localStorage.BD_save(response);
        localStorage.CACHE_save(response);
    }

    private void handleApiError(ApiResponse<TypeAPI> errorResponse) {

        externalService.API_formatError(result, errorResponse.errorMessage, errorResponse.code);
    }

    // TODO: 9/22/17 insert create, update and delete impls



    public interface ExternalServiceImpl<TypeInternal, TypeAPI> {

        LiveData<ApiResponse<TypeAPI>> API_get();

        TypeInternal API_proccesToInternal(ApiResponse<TypeAPI> response);

        void API_formatError(MutableLiveData<Resource<TypeInternal>> partialData, String errorMessage, int errorCode);


        // TODO: 9/22/17 insert create, update and delete impls

    }

    public interface LocalStorageImpl<TypeInternal> {

        void BD_save(@NonNull TypeInternal item);

        LiveData<TypeInternal> BD_get();

        String CACHE_getDomainKey();

        boolean CACHE_hasDomainCache();

        void CACHE_save(TypeInternal item);

        LiveData<TypeInternal> CACHE_get();

        // TODO: 9/22/17 insert create, update and delete impls
    }
}
