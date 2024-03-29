package com.example.archv1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.archv1.domain.model.Album
import com.example.archv1.domain.model.ResponseResult
import com.example.archv1.domain.usecase.GetAlbumListResponseUseCase
import com.example.archv1.domain.usecase.GetAlbumResponseUseCase
import com.example.archv1.domain.usecase.GetAlbumUseCase
import com.example.archv1.presentation.viewModel.AlbumViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AlbumViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    val getAlbumUseCase = mockk<GetAlbumUseCase>()
    @MockK
    val getAlbumResponseUseCase = mockk<GetAlbumResponseUseCase>()
    @MockK
    val getAlbumListResponseUseCase = mockk<GetAlbumListResponseUseCase>()
    @MockK(relaxed = true)
    lateinit var albumObserver: Observer<ResponseResult<Album>>

    private lateinit var viewModel: AlbumViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = AlbumViewModel(getAlbumUseCase, getAlbumResponseUseCase, getAlbumListResponseUseCase)
    }

    @Test
    fun `should get album`() {
        val expectedResponseResult = ResponseResult.Success(Album(1, " ", 1))
        coEvery { getAlbumResponseUseCase.invoke(any()) } returns expectedResponseResult
        viewModel.album.observeForever(albumObserver)
        viewModel.loadAlbum(1)
        assert(viewModel.album.value == expectedResponseResult)
        verify { albumObserver.onChanged(expectedResponseResult) }
    }

    @Test
    fun `should get album list`() {
        val expectedResponseResultList = listOf(
            ResponseResult.Success(Album(1, " ", 1)),
            ResponseResult.Success(Album(2, " ", 2)),
            ResponseResult.Success(Album(3, " ", 3))
        )
        coEvery { getAlbumResponseUseCase.invoke(any()) } returnsMany expectedResponseResultList
        viewModel.album.observeForever(albumObserver)
        viewModel.loadAlbum(1)
        viewModel.loadAlbum(1)
        viewModel.loadAlbum(1)
        assert(viewModel.album.value == expectedResponseResultList[2])
        verifyOrder {
            albumObserver.onChanged(expectedResponseResultList[0])
            albumObserver.onChanged(expectedResponseResultList[1])
            albumObserver.onChanged(expectedResponseResultList[2])
        }
    }
}
