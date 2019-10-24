package com.vbazh.words.languages.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vbazh.words.R
import com.vbazh.words.base.BaseFragment
import com.vbazh.words.data.local.entity.LanguageEntity
import com.vbazh.words.di.ComponentManager
import com.vbazh.words.translate.TranslateConsts.PICK_LANG_TYPE
import kotlinx.android.synthetic.main.fragment_pick_language.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class PickLanguageFragment : BaseFragment(), PickLanguageView {

    override val layoutResId = R.layout.fragment_pick_language

    @Inject
    lateinit var daggetPresenter: PickLanguagePresenter

    @InjectPresenter
    lateinit var presenter: PickLanguagePresenter

    lateinit var languageAdapter: LanguageAdapter

    @ProvidePresenter
    fun providePresenter(): PickLanguagePresenter = daggetPresenter.apply {
        type = arguments?.getString(PICK_LANG_TYPE)
    }

    companion object {
        fun newInstance(type: String): PickLanguageFragment {
            val fragment = PickLanguageFragment()
            val args = Bundle()
            args.putString(PICK_LANG_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        languageAdapter = LanguageAdapter { language -> presenter.chooseLanguage(language) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        languagesRecycler.apply {
            adapter = languageAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        pickLanguageToolbar.setNavigationOnClickListener {
            presenter.navigateBack()
        }
    }

    override fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressView.visibility = View.GONE
    }

    override fun setItems(languages: List<LanguageEntity>) {
        languageAdapter.submitList(languages)
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }


    override fun injectComponent() {
        ComponentManager.pickLanguageComponent.getInstance().inject(this)
    }

    override fun destroyComponent() {
        ComponentManager.pickLanguageComponent.destroy()
    }
}