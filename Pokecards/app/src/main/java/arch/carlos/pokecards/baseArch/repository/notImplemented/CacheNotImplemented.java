package arch.carlos.pokecards.baseArch.repository.notImplemented;

import android.arch.lifecycle.LiveData;

import arch.carlos.pokecards.baseArch.api.ApiResponse;
import arch.carlos.pokecards.baseArch.repository.BaseRepositoryCreate;
import arch.carlos.pokecards.baseArch.repository.BaseRepositoryDelete;
import arch.carlos.pokecards.baseArch.repository.BaseRepositoryRead;
import arch.carlos.pokecards.baseArch.utils.RepositoryOperationNotImplementedException;

/**
 * Created by mobgen on 9/25/17.
 */

public class CacheNotImplemented<T> implements BaseRepositoryCreate.CacheCreateImpl, BaseRepositoryRead.CacheReadImpl<T>, BaseRepositoryDelete.CacheDeleteImpl {

    @Override
    public boolean CACHE_hasDomainCache() {
        throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
    }

    @Override
    public LiveData<T> CACHE_get() {
        throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
    }

    @Override
    public void CACHE_save(T item) {
        throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
    }

    @Override
    public void CACHE_delete() {
        throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
    }

    @Override
    public void CACHE_create() {

    }
}
