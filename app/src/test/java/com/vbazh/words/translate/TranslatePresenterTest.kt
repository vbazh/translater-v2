package com.vbazh.words.translate

import com.nhaarman.mockito_kotlin.*
import com.vbazh.words.data.local.entity.LanguageEntity
import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.translate.data.LangDidntPickedException
import com.vbazh.words.translate.presentation.ITranslateInteractor
import com.vbazh.words.translate.presentation.TranslatePresenter
import com.vbazh.words.translate.presentation.TranslateView
import io.reactivex.Observable
import io.reactivex.Single
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
class TranslatePresenterTest {

    @Mock
    lateinit var view: TranslateView

    @Mock
    lateinit var interactor: ITranslateInteractor

    @Mock
    lateinit var router: Router

    lateinit var presenter: TranslatePresenter

    private val textSourceNormal = "some text"

    private val translateEntityNormal = TranslateEntity(
        source = textSourceNormal,
        target = "some text translated",
        direction = "en-ru",
        isFavorite = 0
    )
    private val emptyLanguageEntity = LanguageEntity("", "")
    private val languageSourceEntityNormal = LanguageEntity("en", "English")
    private val languageTargetEntityNormal = LanguageEntity("ru", "Russian")

    @Before
    fun initTest() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }

        initInteractorCall()

        presenter = TranslatePresenter(interactor, router)
    }

    private fun initInteractorCall() {
        whenever(interactor.getSourceLanguage()).thenReturn(Observable.just(languageSourceEntityNormal))
        whenever(interactor.getTargetLanguage()).thenReturn(Observable.just(languageTargetEntityNormal))
        whenever(interactor.translate(textSourceNormal)).thenReturn(Single.just(translateEntityNormal))
    }

    @Test
    fun `languages wasnt choosed`() {
        whenever(interactor.translate(textSourceNormal)).thenReturn(Single.error(LangDidntPickedException()))
        presenter.attachView(view)

        presenter.translate(textSourceNormal)
        inOrder(view) {
            verify(view).showErrorPickLanguage()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `translate empty field`() {
        presenter.attachView(view)
        presenter.translate("")
        verify(view, times(0)).setTranslateResult("")
    }

    @Test
    fun `choosed empty target language`() {
        whenever(interactor.getTargetLanguage()).thenReturn(Observable.just(emptyLanguageEntity))
        presenter.attachView(view)
        verify(view, times(0)).setTargetLanguageText(emptyLanguageEntity.id)
        verify(view).setTargetLanguageEmpty()
    }

    @Test
    fun `choosed empty source language`() {
        whenever(interactor.getSourceLanguage()).thenReturn(Observable.just(emptyLanguageEntity))
        presenter.attachView(view)
        verify(view, times(0)).setSourceLanguageText(emptyLanguageEntity.id)
        verify(view).setSourceLanguageEmpty()
    }

    @Test
    fun `normal translate`() {
        whenever(interactor.translate(textSourceNormal)).thenReturn(Single.just(translateEntityNormal))
        presenter.attachView(view)
        presenter.translate(textSourceNormal)
        verify(view).setTranslateResult(translateEntityNormal.target)
    }

    @Test
    fun `error translate`() {
        whenever(interactor.translate(textSourceNormal)).thenReturn(Single.error(Exception()))
        presenter.attachView(view)
        presenter.translate(textSourceNormal)
        verify(view, times(0)).setTranslateResult(translateEntityNormal.target)
    }
}