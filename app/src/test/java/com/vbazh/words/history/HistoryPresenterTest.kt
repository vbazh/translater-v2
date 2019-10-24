package com.vbazh.words.history

import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.whenever
import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.history.presentation.HistoryPresenter
import com.vbazh.words.history.presentation.HistoryView
import com.vbazh.words.history.presentation.IHistoryInteractor
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
    lateinit var view: HistoryView

    @Mock
    lateinit var interactor: IHistoryInteractor

    @Mock
    lateinit var router: Router

    lateinit var presenter: HistoryPresenter

    private val textSourceNormal = "some text"

    private val translateEntityNormal = TranslateEntity(
        source = textSourceNormal,
        target = "some text translated",
        direction = "en-ru",
        isFavorite = 0
    )

    @Before
    fun initTest() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }

        initInteractorCall()

        presenter = HistoryPresenter(interactor, router)
    }

    private fun initInteractorCall() {
        whenever(interactor.observeHistory()).thenReturn(
            Flowable.just(
                listOf(
                    translateEntityNormal
                )
            )
        )
    }

    @Test
    fun `get history empty`() {
        whenever(interactor.observeHistory()).thenReturn(Flowable.just(listOf()))
        presenter.attachView(view)

        inOrder(view) {
            verify(view).showProgress()
            verify(view).hideProgress()
            verify(view).showEmptyListText()
            verify(view).setItems(listOf())
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `get history normal`() {
        presenter.attachView(view)

        inOrder(view) {
            verify(view).showProgress()
            verify(view).hideProgress()
            verify(view).hideEmptyListText()
            verify(view).setItems(listOf(translateEntityNormal))
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `delete translate success`() {
        whenever(interactor.delete(translateEntityNormal)).thenReturn(Completable.complete())
        presenter.attachView(view)

        presenter.deleteTranslate(translateEntityNormal)
        inOrder(view) {
            verify(view).showProgress()
            verify(view).hideProgress()
            verify(view).successDelete()
        }
    }

    @Test
    fun `delete translate failed`() {
        whenever(interactor.delete(translateEntityNormal)).thenReturn(Completable.error(Exception()))
        presenter.attachView(view)

        presenter.deleteTranslate(translateEntityNormal)
        inOrder(view) {
            verify(view).showProgress()
            verify(view).hideProgress()
            verify(view).failedDelete()
        }
    }
}