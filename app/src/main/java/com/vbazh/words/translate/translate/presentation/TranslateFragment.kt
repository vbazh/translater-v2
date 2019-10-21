package com.vbazh.words.translate.translate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.vbazh.words.R
import com.vbazh.words.di.ComponentManager
import kotlinx.android.synthetic.main.fragment_translate.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class TranslateFragment : MvpAppCompatFragment(), TranslateView {

    private val layoutResId = R.layout.fragment_translate

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

    override fun onCreate(savedInstanceState: Bundle?) {
        ComponentManager.translateComponent.getInstance().inject(this)
        super.onCreate(savedInstanceState)
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

        translateButton.setOnClickListener {
            presenter.translate(sourceText.text.toString())
        }

        historyButton.setOnClickListener {
            presenter.navigateToHistory()
        }

        sourceText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.translate(sourceText.text.toString())
                true
            }
            false
        }
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
}