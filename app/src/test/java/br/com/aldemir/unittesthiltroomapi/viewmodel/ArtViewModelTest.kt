package br.com.aldemir.unittesthiltroomapi.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.aldemir.unittesthiltroomapi.MainCoroutineRule
import br.com.aldemir.unittesthiltroomapi.getOrAwaitValueTest
import br.com.aldemir.unittesthiltroomapi.repo.FakeArtRepository
import br.com.aldemir.unittesthiltroomapi.util.Status
import br.com.aldemir.unittesthiltroomapi.view.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel : ArtViewModel

    @Before
    fun setup() {
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`() {
        viewModel.makeArt("Mona Lisa","Da Vinci","")

        val value = viewModel.insertArtMessage.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.ERROR)
    }


    @Test
    fun `insert art without name returns error`() {
        viewModel.makeArt("","Da Vinci","1500")

        val value = viewModel.insertArtMessage.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`() {
        viewModel.makeArt("Mona Lisa","","1500")

        val value = viewModel.insertArtMessage.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}