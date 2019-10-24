package com.vbazh.words.favorite.presentation

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
import com.vbazh.words.favorite.FavoriteConsts.SEARCH_DEBOUNCE
import com.vbazh.words.favorite.FavoriteConsts.TEXT_DEBOUNCE
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.textSearch
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FavoriteFragment : BaseFragment(), FavoriteView {

    override val layoutResId = R.layout.fragment_favorite

    @Inject
    lateinit var daggerPresenter: FavoritePresenter

    @InjectPresenter
    lateinit var presenter: FavoritePresenter

    lateinit var favoriteAdapter: FavoriteAdapter

    @ProvidePresenter
    fun providePresenter(): FavoritePresenter = daggerPresenter

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteAdapter = FavoriteAdapter(
            clickListener = {},
            deleteListener = { presenter.removeFromFavorite(it) }
        )
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

        favoriteRecycler.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        favoriteToolbar.setNavigationOnClickListener {
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

    override fun setItems(favorites: List<TranslateEntity>) {
        favoriteAdapter.submitList(favorites)
    }

    override fun successDelete() {
        Toast.makeText(context, R.string.favorite_delete_success, Toast.LENGTH_SHORT).show()
    }

    override fun failedDelete() {
        Toast.makeText(context, R.string.favorite_delete_failed, Toast.LENGTH_SHORT).show()
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
        ComponentManager.favoriteComponent.getInstance().inject(this)
    }

    override fun destroyComponent() {
        ComponentManager.favoriteComponent.destroy()
    }
}