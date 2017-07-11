package com.davidsmt.architecture.domain.usecase.base;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.davidsmt.architecture.domain.BaseMapper;
import com.davidsmt.architecture.domain.model.DomainError;

/**
 * Created by d.san.martin.torres on 6/7/17.
 *
 * This class provides different execution methods that allow running a task in foreground or background.
 * Use cases are generalized with a Params class to communicate all paremeters needed to execute the task
 * and the result data type.
 * Bonus:
 *  - Execute a task and its result can be mapped to obtain the mapped output data directly.
 *  - Execute a task with a specific delay.
 *
 */

public abstract class BaseUseCase<Parameters extends BaseUseCase.Params, OutputData> {

    private static final long LOADING_DELAY_MILLIS = 1000L;

    public Response<OutputData> executeSync() {
        return executeSync(null);
    }

    public Response<OutputData> executeSync(@Nullable Parameters param) {
        return task(param);
    }

    public void execute(@Nullable Parameters param, OnResultCallback<OutputData> callback) {
        new ExecuteWithoutMapperTask(param, callback).execute();
    }

    public void execute(final OnResultCallback<OutputData> callback) {
        execute((Parameters) null, callback);
    }

    public <MappedOutputData> void execute(@NonNull final BaseMapper<OutputData, MappedOutputData> mapper, @Nullable final Parameters param,
                                           final OnResultCallback<MappedOutputData> callback) {
        new ExecuteWithMapperTask<>(mapper, param, callback).execute();
    }

    public <MappedOutputData> void execute(BaseMapper<OutputData, MappedOutputData> mapper, OnResultCallback<MappedOutputData> callback) {
        execute(mapper, null, callback);
    }

    public void executeDelayed(@Nullable Parameters param, OnResultCallback<OutputData> callback) {
        new ExecuteWithoutMapperTask(param, delayedCallback(System.currentTimeMillis(), callback)).execute();
    }

    public void executeDelayed(final OnResultCallback<OutputData> callback) {
        execute((Parameters) null, delayedCallback(System.currentTimeMillis(), callback));
    }

    public <MappedOutputData> void executeDelayed(@NonNull final BaseMapper<OutputData, MappedOutputData> mapper, @Nullable final Parameters param,
                                                  final OnResultCallback<MappedOutputData> callback) {

        new ExecuteWithMapperTask<>(mapper, param, delayedCallback(System.currentTimeMillis(), callback)).execute();
    }

    public <MappedOutputData> void executeDelayed(BaseMapper<OutputData, MappedOutputData> mapper, OnResultCallback<MappedOutputData> callback) {
        execute(mapper, null, delayedCallback(System.currentTimeMillis(), callback));
    }

    private <DelayedOutput> OnResultCallback<DelayedOutput> delayedCallback(final long startTime, final OnResultCallback<DelayedOutput> callback) {
        return new OnResultCallback<DelayedOutput>() {
            @Override
            public void onSuccess(final DelayedOutput data) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(data);
                    }
                });
            }

            @Override
            public void onFailure(final DomainError error) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(error);
                    }
                });
            }

            private void postDelayed(Runnable runnable) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime < LOADING_DELAY_MILLIS) {
                    new Handler().postDelayed(runnable, LOADING_DELAY_MILLIS - elapsedTime);
                } else {
                    runnable.run();
                }
            }
        };
    }

    public abstract
    @NonNull
    Response<OutputData> task(@Nullable Parameters param);

    private class ExecuteWithoutMapperTask extends AsyncTask<Void, Void, Response<OutputData>> {

        private OnResultCallback<OutputData> callback;
        private Parameters param;

        ExecuteWithoutMapperTask(@Nullable Parameters param, OnResultCallback<OutputData> callback) {
            this.callback = callback;
            this.param = param;
        }

        @Override
        protected Response<OutputData> doInBackground(Void... params) {
            return task(this.param);
        }

        @Override
        protected void onPostExecute(Response<OutputData> response) {
            if (!response.hasError()) {
                callback.onSuccess(response.getData());
            } else {
                callback.onFailure(response.getError());
            }
        }
    }

    private class ExecuteWithMapperTask<MappedOutputData> extends AsyncTask<Void, Void, Response<OutputData>> {
        private OnResultCallback<MappedOutputData> callback;
        private BaseMapper<OutputData, MappedOutputData> mapper;
        private Parameters param;

        public ExecuteWithMapperTask(BaseMapper<OutputData, MappedOutputData> mapper, @Nullable Parameters param, OnResultCallback<MappedOutputData> callback) {
            this.mapper = mapper;
            this.callback = callback;
            this.param = param;
        }

        @Override
        protected Response<OutputData> doInBackground(Void... params) {
            return task(this.param);
        }

        @Override
        protected void onPostExecute(Response<OutputData> response) {
            if (!response.hasError()) {
                new ApplyMapperTask<>(mapper, response.getData(), callback).execute();
            } else {
                callback.onFailure(response.getError());
            }
        }
    }

    private class ApplyMapperTask<MappedOutputData> extends AsyncTask<Void, Void, MappedOutputData> {

        private OnResultCallback<MappedOutputData> callback;
        private BaseMapper<OutputData, MappedOutputData> mapper;
        private OutputData input;

        public ApplyMapperTask(BaseMapper<OutputData, MappedOutputData> mapper, OutputData input, OnResultCallback<MappedOutputData> callback) {
            this.mapper = mapper;
            this.callback = callback;
            this.input = input;
        }

        @Override
        protected MappedOutputData doInBackground(Void... params) {
            return mapper.map(this.input);
        }

        @Override
        protected void onPostExecute(MappedOutputData mappedOutputData
        ) {
            callback.onSuccess(mappedOutputData);
        }
    }

    public static class Params {

        public final class VoidParams extends Params {
        }

    }

    public static class Response<T> {

        private DomainError error;
        private T data;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public DomainError getError() {
            return error;
        }

        public void setError(DomainError error) {
            this.error = error;
        }

        boolean hasError() {
            return error != null;
        }


        final class EmptyResponse extends Response {
        }

    }

}

