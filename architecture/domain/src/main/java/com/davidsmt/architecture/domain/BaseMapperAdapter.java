package com.davidsmt.architecture.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d.san.martin.torres on 6/8/17.
 */

public abstract class BaseMapperAdapter<Input,Output> implements BaseMapper<Input,Output> {

    @Override
    public abstract Output map(Input useCaseResponse);

    public List<Output> map(List<Input> inputList) {
        List<Output> outputList = new ArrayList<>();
        for (Input input : inputList) {
            outputList.add(map(input));
        }
        return outputList;
    }

}
