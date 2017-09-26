package arch.carlos.pokecards.baseArch.repository.notImplemented;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import arch.carlos.pokecards.baseArch.repository.BaseRepositoryCreate;
import arch.carlos.pokecards.baseArch.repository.BaseRepositoryDelete;
import arch.carlos.pokecards.baseArch.repository.BaseRepositoryRead;
import arch.carlos.pokecards.baseArch.utils.RepositoryOperationNotImplementedException;

/**
 * Created by mobgen on 9/25/17.
 */

public class DBNotImplemented<T> implements BaseRepositoryCreate.DBCreateImpl, BaseRepositoryRead.DBReadImpl, BaseRepositoryDelete.DBDeleteImpl{
    @Override
    public LiveData<T> BD_get() {
        throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
    }

    @Override
    public void BD_save(@NonNull Object item) {
        throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
    }

    @Override
    public void BD_delete() {
        throw new RepositoryOperationNotImplementedException(Thread.currentThread().getStackTrace()[0].getMethodName() + " not implemented");
    }

    @Override
    public void DB_create() {

    }
}
