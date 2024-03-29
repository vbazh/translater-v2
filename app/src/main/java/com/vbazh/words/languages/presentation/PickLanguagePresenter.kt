package com.vbazh.words.languages.presentation

import android.util.Log
import com.vbazh.words.data.local.entity.LanguageEntity
import com.vbazh.words.languages.LanguagesConsts
import com.vbazh.words.translate.TranslateConsts
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class PickLanguagePresenter @Inject constructor(
    private val languagesInteractor: ILanguagesInteractor,
    private val router: Router
) : MvpPresenter<PickLanguageView>() {

    var type: String? = null

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        compositeDisposable.add(
            languagesInteractor.getLanguage(LanguagesConsts.DEFAULT_UI_LANG)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doFinally { viewState.hideProgress() }
                .subscribe(
                    { languages ->
                        viewState.hideProgress()
                        viewState.setItems(languages)
                    },
                    { error ->
                        viewState.hideProgress()
                        Log.d("ERRO", "error get languages", error)
                        error.message?.let { viewState.showError(it) }
                    })
        )
    }

    fun chooseLanguage(language: LanguageEntity) {

        val disposable = when (type) {
            TranslateConsts.PICK_LANG_SOURCE -> languagesInteractor.chooseSourceLanguage(language.id)
            TranslateConsts.PICK_LANG_TARGET -> languagesInteractor.chooseTargetLanguage(language.id)
            else -> Completable.complete()
        }

        compositeDisposable.add(
            disposable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ router.exit() }, {})
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun navigateBack() {
        router.exit()
    }
}