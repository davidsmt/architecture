package com.davidsmt.architecture.domain.usecase.base;

import com.davidsmt.architecture.domain.model.DomainError;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public interface OnResultCallback<OutputSuccess> {
    void onSuccess(OutputSuccess data);
    void onFailure(DomainError error);
}
