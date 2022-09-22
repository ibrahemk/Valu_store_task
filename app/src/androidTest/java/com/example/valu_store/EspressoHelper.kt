package com.example.valu_store

import android.text.InputFilter
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedDiagnosingMatcher
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class EspressoHelper {
    companion object{

        @JvmStatic
        fun hasTextInputLayoutErrorText(expectedErrorText: String): Matcher<View> = object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) { }
            override fun matchesSafely(view: View?): Boolean {
                if (view !is TextInputLayout) return false
                val error = view.error ?: return false
                return expectedErrorText == error.toString()
            }
        }



        @JvmStatic
        fun checkMaxLength(lines: Int): TypeSafeMatcher<Any?> {
            return object : TypeSafeMatcher<Any?>() {
                // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                override fun matchesSafely(item: Any?): Boolean {
                    val filters = (item as TextView).filters
                    val lengthFilter = filters[0] as InputFilter.LengthFilter
                    return lengthFilter.max == lines
                }

                override fun describeTo(description: Description) {
                    description.appendText("checkMaxLength")
                }

            }
        }

        @JvmStatic
        fun checkDropdownListSize(expectedSize: Int): TypeSafeMatcher<View> {
            return object : TypeSafeMatcher<View>() {

                override fun describeTo(description: Description) {
                    description.appendText("ListView should have $expectedSize items")
                }

                override fun matchesSafely(view: View): Boolean {
                    return if(view is AutoCompleteTextView) view.adapter.count == expectedSize
                    else
                        false
                }
            }
        }



        @JvmStatic
        fun clickChildViewWithId(id: Int): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController, view: View) {
                    val v = view.findViewById<View>(id) as View
                    v.performClick()
                }
            }
        }

        @JvmStatic
        fun typeTextInChildViewWithId(id: Int, textToBeTyped: String): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController, view: View) {
                    val v = view.findViewById<View>(id) as EditText
                    v.setText(textToBeTyped)
                }
            }
        }

        @JvmStatic
        fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {

            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("Child at position $position in parent ")
                    parentMatcher.describeTo(description)
                }

                public override fun matchesSafely(view: View): Boolean {
                    val parent = view.parent
                    return parent is ViewGroup && parentMatcher.matches(parent)
                            && view == parent.getChildAt(position)
                }
            }
        }

        @JvmStatic
        fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
            return RecyclerViewMatcher(recyclerViewId)
        }

        @JvmStatic
        fun getVisibleFragment(mActivityTestRule: ActivityScenarioRule<MainActivity>): Fragment {
            val visibleFragments: ArrayList<Fragment> = ArrayList()
            mActivityTestRule.scenario.onActivity {
                val fragments: List<Fragment> = it.supportFragmentManager.fragments
                for (fragment in fragments) {
                    if (fragment != null && fragment.isVisible) visibleFragments.add(fragment)
                }
            }
            return visibleFragments[0]
        }



        // A Matcher for Espresso that checks if an ImageView has a drawable applied to it.
        @JvmStatic
        fun hasDrawable(): Matcher<View?>? {
            return object : BoundedDiagnosingMatcher<View?, ImageView>(ImageView::class.java) {
                override fun describeMoreTo(description: Description) {
                    description.appendText("has drawable")
                }

                override fun matchesSafely(
                    imageView: ImageView,
                    mismatchDescription: Description
                ): Boolean {
                    return imageView.drawable != null
                }
            }
        }
        @JvmStatic
        fun forceClick(): ViewAction? {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return allOf(isClickable(), isEnabled(), isDisplayed())
                }

                override fun getDescription(): String {
                    return "force click"
                }

                override fun perform(uiController: UiController, view: View) {
                    view.performClick() // perform click without checking view coordinates.
                    uiController.loopMainThreadUntilIdle()
                }
            }
        }

        @JvmStatic
        fun havetext(string: String): BoundedMatcher<View?, TextView> {
            return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
                override fun describeTo(description: Description) {
                    description.appendText("name")
                }

                override fun matchesSafely(textView: TextView): Boolean {

                    return  textView.text.contains(string)

                }
            }
        }

        fun checkdate(dateformat:String): BoundedMatcher<View?, TextView> {
            return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
                override fun describeTo(description: Description) {
                    description.appendText("has drawable")
                }

                override fun matchesSafely(textView: TextView): Boolean {
                    var date: Date? = null
                    try {
                        date = SimpleDateFormat(dateformat).parse(textView.text.toString())
                        return true
                    } catch (ex: Exception) {
                            return false

                    }
                }
            }
        }
        @JvmStatic
        fun clickClickableSpan(textToClick: CharSequence): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return Matchers.instanceOf<View>(TextView::class.java)
                }

                override fun getDescription(): String {
                    return "clicking on a ClickableSpan"
                }

                override fun perform(uiController: UiController?, view: View) {
                    val textView = view as TextView
                    val spannableString: SpannableString = textView.text as SpannableString
                    if (spannableString.length === 0) {
                        // TextView is empty, nothing to do
                        throw NoMatchingViewException.Builder()
                            .includeViewHierarchy(true)
                            .withRootView(textView)
                            .build()
                    }

                    // Get the links inside the TextView and check if we find textToClick
                    val spans: Array<ClickableSpan> = spannableString.getSpans(
                        0, spannableString.length,
                        ClickableSpan::class.java
                    )
                    if (spans.isNotEmpty()) {
                        var spanCandidate: ClickableSpan?
                        for (span in spans) {
                            spanCandidate = span
                            val start: Int = spannableString.getSpanStart(spanCandidate)
                            val end: Int = spannableString.getSpanEnd(spanCandidate)
                            val sequence: CharSequence = spannableString.subSequence(start, end)
                            if (textToClick.toString() == sequence.toString()) {
                                span.onClick(textView)
                                return
                            }
                        }
                    }
                    throw NoMatchingViewException.Builder()
                        .includeViewHierarchy(true)
                        .withRootView(textView)
                        .build()
                }
            }
        }

    }
}