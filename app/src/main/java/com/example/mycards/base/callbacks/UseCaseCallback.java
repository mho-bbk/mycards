package com.example.mycards.base.callbacks;

public interface UseCaseCallback<T> {
    void onComplete(Result<T> result);
}
