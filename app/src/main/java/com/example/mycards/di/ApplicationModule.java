package com.example.mycards.di;

import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//Returns the application as a dependency.
//No code needed. See: https://proandroiddev.com/dagger-2-component-builder-1f2b91237856
@Module
public class ApplicationModule {

    @Provides
    public Executor provideSingleThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    public ExecutorService provideMultiExecutorService() {
        return Executors.newFixedThreadPool(5);
    }

//    Application myApplication;
//
//    public ApplicationModule(Application application) {
//        this.myApplication = application;
//    }
//
//    //Don't need below?
//    //See: https://stackoverflow.com/questions/44083243/dagger-2-component-builder-is-missing-setters-for-required-modules-or-componen
//    @Provides
//    @Singleton
//    Application providesApplication() {
//        return myApplication;
//    }
}
