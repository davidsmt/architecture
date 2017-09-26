package arch.carlos.pokecards.baseArch.repository.notImplemented;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import arch.carlos.pokecards.baseArch.api.ApiResponse;
import arch.carlos.pokecards.baseArch.repository.BaseRepositoryCreate;
import arch.carlos.pokecards.baseArch.repository.BaseRepositoryDelete;
import arch.carlos.pokecards.baseArch.repository.BaseRepositoryRead;
import arch.carlos.pokecards.baseArch.utils.RepositoryOperationNotImplementedException;
import arch.carlos.pokecards.baseArch.vo.Resource;

/**
 * Created by mobgen on 9/25/17.
 */

public class ExternalServiceNotImplemented implements BaseRepositoryCreate.ExternalServiceCreateImpl, BaseRepositoryRead.ExternalServiceReadImpl, BaseRepositoryDelete.ExternalServiceDeleteImpl{
    @Override
    public LiveData<ApiResponse> API_delete() {
        throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
    }

    @Override
    public LiveData<ApiResponse> API_get() {
        throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
    }

    @Override
    public Object API_proccesToInternal(ApiResponse response) {
        throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
    }

    @Override
    public void API_formatError(MutableLiveData partialData, String errorMessage, int errorCode) {
        throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
    }

    @Override
    public LiveData<ApiResponse> API_create() {
        return null;
    }
}
