package com.conflux.finflux.test.common.injection.component;

import com.conflux.finflux.injection.component.ApplicationComponent;
import com.conflux.finflux.test.common.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
