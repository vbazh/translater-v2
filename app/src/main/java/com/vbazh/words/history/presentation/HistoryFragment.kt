package com.vbazh.words.history.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.widget.editorActionEvents
import com.jakewharton.rxbinding3.widget.textChanges
import com.vbazh.words.R
import com.vbazh.words.base.BaseFragment
import com.vbazh.words.base.gone
import com.vbazh.words.base.visible
import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.di.ComponentManager
import com.vbazh.words.history.HistoryConsts.SEARCH_DEBOUNCE
import com.vbazh.words.history.HistoryConsts.TEXT_DEBOUNCE
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_history.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HistoryFragment : BaseFragment(), HistoryView {

    override val layoutResId = R.layout.fragment_history

    @Inject
    lateinit var daggerPresenter: HistoryPresenter

    @InjectPresenter
    lateinit var presenter: HistoryPresenter

    lateinit var historyAdapter: HistoryAdapter

    @ProvidePresenter
    fun providePresenter(): HistoryPresenter = daggerPresenter

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyAdapter = HistoryAdapter(
            clickListener = { },
            deleteListener = { presenter.deleteTranslate(it) },
            favoriteListener = { presenter.favoriteTranslate(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyRecycler.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        historyToolbar.setNavigationOnClickListener {
            presenter.navigateBack()
        }

        addDisposable(textSearch.textChanges()
            .skipInitialValue()
            .debounce(TEXT_DEBOUNCE, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { presenter.search(it) })

        addDisposable(textSearch.editorActionEvents()
            .filter { it.actionId == EditorInfo.IME_ACTION_DONE }
            .debounce(SEARCH_DEBOUNCE, TimeUnit.MILLISECONDS)
            .subscribe { presenter.search(it.view.text.toString()) })
    }

    override fun successDelete() {
        Toast.makeText(context, R.string.history_delete_success, Toast.LENGTH_SHORT).show()
    }

    override fun failedDelete() {
        Toast.makeText(context, R.string.history_delete_failed, Toast.LENGTH_SHORT).show()
    }

    override fun setItems(translate: List<TranslateEntity>) {
        historyAdapter.submitList(translate)
    }

    override fun showProgress() {
        progressView.visible()
    }

    override fun hideProgress() {
        progressView.gone()
    }

    override fun showEmptyListText() {
        emptyListText.visible()
    }

    override fun hideEmptyListText() {
        emptyListText.gone()
    }

    override fun injectComponent() {
        ComponentManager.historyComponent.getInstance().inject(this)
    }

    override fun destroyComponent() {
        ComponentManager.historyComponent.destroy()
    }
}