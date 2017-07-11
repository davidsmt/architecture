package com.davidsmt.architecture.domain;


public interface BaseMapper<Input, Output> {

    Output map(Input useCaseResponse);

}
