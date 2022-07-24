package br.com.aldemir.unittesthiltroomapi.view


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import br.com.aldemir.unittesthiltroomapi.R
import br.com.aldemir.unittesthiltroomapi.data.database.Art
import br.com.aldemir.unittesthiltroomapi.getOrAwaitValue
import br.com.aldemir.unittesthiltroomapi.launchFragmentInHiltContainer
import br.com.aldemir.unittesthiltroomapi.data.repo.FakeArtRepositoryAndroid
import br.com.aldemir.unittesthiltroomapi.view.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ArtDetailsFragmentTest : Fragment() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory : ArtFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtDetailsToImageAPI() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(),navController)
        }

        onView(withId(R.id.artImageView)).perform(click())
        verify(navController).navigate(
            ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment()
        )
    }

    @Test
    fun testOnBackPressed() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(),navController)
        }

        pressBack()
        verify(navController).popBackStack()
    }

    @Test
    fun testSave() {
        var testViewModel = ArtViewModel(FakeArtRepositoryAndroid())
        val loginViewModel by activityViewModels<ArtViewModel>()
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ) {
            viewModel = testViewModel
        }

        onView(withId(R.id.nameText)).perform(replaceText("Mona Lisa"))
        onView(withId(R.id.artistText)).perform(replaceText("Da Vinci"))
        onView(withId(R.id.yearText)).perform(replaceText("1700"))
        onView(withId(R.id.saveButton))
            .perform(closeSoftKeyboard())
            .perform(click())

        assertThat(testViewModel.artList.getOrAwaitValue()).contains(
            Art(
                "Mona Lisa",
                "Da Vinci",
                1700,"")
        )

    }

}