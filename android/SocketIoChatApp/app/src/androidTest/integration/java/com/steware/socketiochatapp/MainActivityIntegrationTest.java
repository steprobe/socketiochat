package com.steware.socketiochatapp;

import android.support.test.rule.ActivityTestRule;

import com.steware.socketiochatapp.utils.IntegrationTests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.steware.socketiochatapp.utils.TestUtils.waitFor;
import static org.hamcrest.CoreMatchers.containsString;

@Category(IntegrationTests.class)
public class MainActivityIntegrationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkConnection() {
        onView(withId(R.id.messages)).check(matches(withText(containsString("Connecting..."))));
        waitFor(onView(withId(R.id.messages)), matches(withText(containsString("Connected to socket.io chat service"))), 5000);
    }

    @Test
    public void checkSendMessage() {

        final String testMessage = "Here is a test message";

        onView(withId(R.id.textEntry)).perform(typeText(testMessage), closeSoftKeyboard());
        onView(withId(R.id.send)).perform(click());

        waitFor(onView(withId(R.id.messages)), matches(withText(containsString(testMessage))), 5000);
    }
}
