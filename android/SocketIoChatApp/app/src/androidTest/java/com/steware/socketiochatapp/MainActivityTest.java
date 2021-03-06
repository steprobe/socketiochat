package com.steware.socketiochatapp;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.steware.socketiochatapp.TestUtils.waitFor;
import static org.hamcrest.CoreMatchers.containsString;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkConnection() {
        onView(withId(R.id.messages)).check(matches(withText(containsString("Connecting..."))));
        onView(withId(R.id.messages)).check(matches(withText(containsString("Connected to socket.io chat service"))));
    }

    @Test
    public void checkSendMessage() {

        final String testMessage = "Here is a test message";

        onView(withId(R.id.textEntry)).perform(typeText(testMessage), closeSoftKeyboard());
        onView(withId(R.id.send)).perform(click());

        waitFor(onView(withId(R.id.messages)), matches(withText(containsString(testMessage))), 5000);
    }
}
