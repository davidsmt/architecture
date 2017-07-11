package com.davidsmt.architecture;


import android.app.Application;

import com.davidsmt.architecture.domain.usecase.UseCaseProvider;
import com.davidsmt.architecture.domain.usecase.UseCaseProviderImpl;

public class PokeApplication extends Application {

    private static UseCaseProvider useCaseProvider;

    static {
        useCaseProvider = new UseCaseProviderImpl();
    }

    public static UseCaseProvider getUseCaseProvider() {
        return useCaseProvider;
    }
}
