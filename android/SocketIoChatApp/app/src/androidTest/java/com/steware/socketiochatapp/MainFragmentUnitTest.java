package com.steware.socketiochatapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class MainFragmentUnitTest {

    private static final String FRAGMENT_TAG = "testmainfragment";

    @Rule
    public ActivityTestRule<TestActivity> activityTestRule = new ActivityTestRule<>(TestActivity.class);

    @After
    public void tearDown() {
        TestActivity.dependencyProvider = null;
    }

    @Test
    public void testCreateAndBind() {

        DependencyProvider mockDependencies = mock(DependencyProvider.class);

        WebSocketServiceProxy mockProxy = mock(WebSocketServiceProxy.class);

        when(mockDependencies.createWebSocketServiceProxy((WebSocketServiceCallback) anyObject())).thenReturn(mockProxy);

        attachFragment(mockDependencies);

        verify(mockDependencies, times(1)).createWebSocketServiceProxy((WebSocketServiceCallback) anyObject());
        verify(mockProxy, times(1)).bind();

        verifyNoMoreInteractions(mockDependencies, mockProxy);
    }

    @Test
    public void testRotation() {

        DependencyProvider mockDependencies = mock(DependencyProvider.class);

        WebSocketServiceProxy mockProxy = mock(WebSocketServiceProxy.class);

        when(mockDependencies.createWebSocketServiceProxy((WebSocketServiceCallback) anyObject())).thenReturn(mockProxy);

        attachFragment(mockDependencies);
        rotateScreen();

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdleSync();

        verify(mockDependencies, times(2)).createWebSocketServiceProxy((WebSocketServiceCallback) anyObject());
        verify(mockProxy, times(2)).bind();
        verify(mockProxy, times(1)).unbind();

        verifyNoMoreInteractions(mockDependencies, mockProxy);
    }

    private void attachFragment(DependencyProvider dependencies) {

        TestActivity.dependencyProvider = dependencies;
        TestActivity activity = activityTestRule.getActivity();

        FragmentManager fraggleMan = activity.getSupportFragmentManager();

        MainFragment fraggleToTest = MainFragment.newInstance();

        FragmentTransaction transaction = fraggleMan.beginTransaction();

        transaction.replace(R.id.test_root, fraggleToTest, FRAGMENT_TAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdleSync();
    }

    private void rotateScreen() {
        int currentOrientation = InstrumentationRegistry.getTargetContext().getResources().getConfiguration().orientation;
        Activity activity = activityTestRule.getActivity();

        activity.setRequestedOrientation(
                currentOrientation == Configuration.ORIENTATION_PORTRAIT ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
