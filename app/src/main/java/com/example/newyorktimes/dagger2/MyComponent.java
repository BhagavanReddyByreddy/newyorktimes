package com.example.newyorktimes.dagger2;

import android.content.Context;

import com.example.newyorktimes.MainActivity;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {SharedPrefModule.class})
public interface MyComponent {
    void inject(MainActivity activity);
}
