package com.davidsmt.architecture.repository;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class RepositoryException extends Throwable {

    @Retention(SOURCE)
    @IntDef({GENERAL, DATABASE, API})
    public @interface Type {}
    public static final int GENERAL = 0;
    public static final int DATABASE = 1;
    public static final int API = 2;

    private int type;
    private String message;

    public RepositoryException(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

