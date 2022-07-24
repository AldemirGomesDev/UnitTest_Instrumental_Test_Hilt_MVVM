package br.com.aldemir.unittesthiltroomapi.view


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import br.com.aldemir.unittesthiltroomapi.R
import br.com.aldemir.unittesthiltroomapi.getOrAwaitValue
import br.com.aldemir.unittesthiltroomapi.launchFragmentInHiltContainer
import br.com.aldemir.unittesthiltroomapi.data.repo.FakeArtRepositoryAndroid
import br.com.aldemir.unittesthiltroomapi.view.adapter.ImageRecyclerAdapter
import br.com.aldemir.unittesthiltroomapi.view.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ImageApiFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory : ArtFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testSelectImage() {
        val navController = Mockito.mock(NavController::class.java)
        val selectedImageUrl = "atilsamancioglu.com"
        val testViewModel = ArtViewModel(FakeArtRepositoryAndroid())

        launchFragmentInHiltContainer<ImageApiFragment>(
            factory = fragmentFactory,
        ) {
            Navigation.setViewNavController(requireView(),navController)
            imageRecyclerAdapter.images = listOf(selectedImageUrl)
            viewModel = testViewModel
        }

        Espresso.onView(ViewMatchers.withId(R.id.imageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(
                0,click()
            )

        )

        Mockito.verify(navController).popBackStack()

        assertThat(testViewModel.selectedImageUrl.getOrAwaitValue()).isEqualTo(selectedImageUrl)

    }
}