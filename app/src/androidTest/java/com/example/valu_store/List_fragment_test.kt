package com.example.valu_store

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.valu_store.Adapter.Products_adapter
import com.example.valu_store.Bean.Product
import com.example.valu_store.EspressoHelper.Companion.hasDrawable
import com.example.valu_store.EspressoHelper.Companion.withRecyclerView
import com.example.valu_store.Retrofitapis.MyIdlingResource
import com.example.valu_store.fragments.List_Fragment
import org.hamcrest.Description
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class List_fragment_test {
    private lateinit var fragment: List_Fragment
    @Rule
    @JvmField
    val mActivityTestRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)
    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(MyIdlingResource.getIdlingResource())
        fragment= EspressoHelper.getVisibleFragment(mActivityTestRule) as List_Fragment
    }
    @Test
    fun has_image(){
        val roundedImageView = Espresso.onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.image))
        roundedImageView.check(ViewAssertions.matches(hasDrawable()))
    }
    @Test
    fun open_product_details(){
        val studiot = Espresso.onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.row))
        studiot.perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.image))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
    @Test
    fun set_product_name(){
        val title = Espresso.onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.title))
        title.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        val product= (fragment.model.binding.list.adapter as Products_adapter).list[0]
        title.check(ViewAssertions.matches(check(product.title)))
    }
    @Test
    fun set_product_cost(){
        val title = Espresso.onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.cost))
        title.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        val product= (fragment.model.binding.list.adapter as Products_adapter).list[0]
        title.check(ViewAssertions.matches(check(product.price)))
    }


    fun check(string: String): BoundedMatcher<View?, TextView> {
        return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("name")
            }

            override fun matchesSafely(textView: TextView): Boolean {
                return string.isNotEmpty() &&textView.text==string


            }
        }
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(MyIdlingResource.getIdlingResource())
    }
}