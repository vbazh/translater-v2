package com.vbazh.words.history.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vbazh.words.R
import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.di.ComponentManager
import kotlinx.android.synthetic.main.fragment_history.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class HistoryFragment : MvpAppCompatFragment(), HistoryView {
    override fun successDelete() {
        Toast.makeText(context, R.string.history_delete_success, Toast.LENGTH_SHORT).show()
    }

    override fun failedDelete() {
        Toast.makeText(context, R.string.history_delete_failed, Toast.LENGTH_SHORT).show()
    }

    private val layoutResId = R.layout.fragment_history

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
        ComponentManager.historyComponent.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        historyAdapter = HistoryAdapter(
            clickListener = { },
            deleteListener = { presenter.deleteTranslate(it) })
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
    }

    override fun setItems(translate: List<TranslateEntity>) {
        historyAdapter.submitList(translate)
    }
}