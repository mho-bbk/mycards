package com.example.mycards.di;

import android.app.Application;

import com.example.mycards.main.MainActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, DataModule.class, ViewModelModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {

        AppComponent build();

        @BindsInstance
        Builder application(Application application);
    }

}
