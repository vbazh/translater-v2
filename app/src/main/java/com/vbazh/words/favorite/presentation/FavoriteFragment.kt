package com.vbazh.words.favorite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vbazh.words.R
import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.di.ComponentManager
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.textSearch
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class FavoriteFragment : MvpAppCompatFragment(), FavoriteView {

    private val layoutResId = R.layout.fragment_favorite

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
        ComponentManager.favoriteComponent.getInstance().inject(this)
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

        textSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.search(textSearch.text.toString())
            }
            false
        }
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

    override fun onDestroy() {
        super.onDestroy()
        ComponentManager.favoriteComponent.destroy()
    }
}