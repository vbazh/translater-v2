package com.vbazh.words.favorite

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.favorite.presentation.FavoritePresenter
import com.vbazh.words.favorite.presentation.FavoriteView
import com.vbazh.words.favorite.presentation.IFavoriteInteractor
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.terrakok.cicerone.Router

@RunWith(MockitoJUnitRunner::class)
class HistoryPresenterTest {

    @Mock
    lateinit var view: FavoriteView

    @Mock
    lateinit var interactor: IFavoriteInteractor

    @Mock
    lateinit var router: Router

    lateinit var presenter: FavoritePresenter

    private val textSearch = "search text"

    private val textSourceNormal = "some text"

    private val translateEntityNormal = TranslateEntity(
        source = textSourceNormal,
        target = "some text translated",
        direction = "en-ru",
        isFavorite = 1
    )

    @Before
    fun initTest() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }

        initInteractorCall()

        presenter = FavoritePresenter(interactor, router)
    }

    private fun initInteractorCall() {
        whenever(interactor.observeFavorite()).thenReturn(
            Flowable.just(
                listOf(
                    translateEntityNormal
                )
            )
        )
    }

    @Test
    fun `remove from favorites success`() {
        whenever(interactor.removeFromFavorite(translateEntityNormal))
            .thenReturn(Completable.complete())
        presenter.attachView(view)

        presenter.removeFromFavorite(translateEntityNormal)
        verify(view).successDelete()
    }

    @Test
    fun `remove from favorites failed`() {
        whenever(interactor.removeFromFavorite(translateEntityNormal))
            .thenReturn(Completable.error(Exception()))
        presenter.attachView(view)

        presenter.removeFromFavorite(translateEntityNormal)
        verify(view).failedDelete()
    }

    @Test
    fun `success search`() {
        whenever(interactor.search(textSearch)).thenReturn(Flowable.just(listOf(translateEntityNormal)))
        presenter.attachView(view)

        presenter.search(textSearch)
        verify(view).hideEmptyListText()
        verify(view).setItems(listOf(translateEntityNormal))
    }

    @Test
    fun `success search empty`() {
        whenever(interactor.search(textSearch)).thenReturn(Flowable.just(listOf()))
        presenter.attachView(view)

        presenter.search(textSearch)
        verify(view).showEmptyListText()
        verify(view).setItems(listOf())
    }
}