package com.steware.socketiochatapp;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MyModule.class})
public interface MyComponent {

    DummDep provideDummDep();

}
