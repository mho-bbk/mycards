package com.example.mycards.base.usecasetypes;

public interface BaseUseCaseWithParam<P, R> {

    public R run(P param);
}
