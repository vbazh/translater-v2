package com.vbazh.words.translate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.jakewharton.rxbinding3.widget.editorActionEvents
import com.jakewharton.rxbinding3.widget.textChanges
import com.vbazh.words.R
import com.vbazh.words.base.BaseFragment
import com.vbazh.words.di.ComponentManager
import com.vbazh.words.translate.TranslateConsts.DONE_DEBOUNCE
import com.vbazh.words.translate.TranslateConsts.TEXT_DEBOUNCE
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_translate.*
import kotlinx.android.synthetic.main.fragment_translate.sourceText
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TranslateFragment : BaseFragment(), TranslateView {

    override val layoutResId = R.layout.fragment_translate

    @Inject
    lateinit var daggerPresenter: TranslatePresenter

    @InjectPresenter
    lateinit var presenter: TranslatePresenter

    @ProvidePresenter
    fun providePresenter(): TranslatePresenter = daggerPresenter

    companion object {
        fun newInstance(): TranslateFragment {
            return TranslateFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sourceText.imeOptions = EditorInfo.IME_ACTION_DONE

        sourceLanguage.setOnClickListener {
            presenter.pickSourceLanguage()
        }

        targetLanguage.setOnClickListener {
            presenter.pickTargetLanguage()
        }

        historyButton.setOnClickListener {
            presenter.navigateToHistory()
        }

        favoriteButton.setOnClickListener {
            presenter.navigateToFavorite()
        }

        clearImage.setOnClickListener {
            presenter.clearTranslation()
        }

        addDisposable(sourceText.textChanges()
            .skipInitialValue()
            .debounce(TEXT_DEBOUNCE, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { presenter.translate(it) })

        addDisposable(sourceText.editorActionEvents()
            .filter { it.actionId == EditorInfo.IME_ACTION_DONE }
            .debounce(DONE_DEBOUNCE, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { presenter.translate(it.view.text.toString()) })
    }

    override fun setTargetLanguageText(target: String) {
        targetLanguage.text = target
    }

    override fun setSourceLanguageText(source: String) {
        sourceLanguage.text = source
    }

    override fun setTranslateResult(translated: String) {
        translatedText.text = translated
    }

    override fun clearTextSource() {
        translatedText.text = ""
        sourceText.text?.clear()
    }

    override fun showErrorPickLanguage() {
        Toast.makeText(context, R.string.translate_pick_language_error, Toast.LENGTH_SHORT).show()
    }

    override fun setTargetLanguageEmpty() {
        targetLanguage.text = getString(R.string.translate_empty_language)
    }

    override fun setSourceLanguageEmpty() {
        sourceLanguage.text = getString(R.string.translate_empty_language)
    }

    override fun injectComponent() {
        ComponentManager.translateComponent.getInstance().inject(this)
    }

    override fun destroyComponent() {
        ComponentManager.translateComponent.destroy()
    }
}