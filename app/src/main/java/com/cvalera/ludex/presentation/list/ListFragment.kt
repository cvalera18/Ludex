package com.cvalera.ludex.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cvalera.ludex.R
import com.cvalera.ludex.databinding.FragmentListBinding
import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import com.cvalera.ludex.presentation.SharedViewModel
import com.cvalera.ludex.presentation.adapter.GameListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: GameListAdapter
    private val viewModel: ListViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeGameList()
        configFilter()
        initRecyclerView()
        observeLoadingState()
        getListGames()
    }

    private fun observeLoadingState() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipe.isRefreshing = isLoading
        }
    }

    private fun getListGames() {
        viewModel.getListGames()
    }

    private fun observeGameList() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allGames.collect { gameList ->
                    adapter.updateGames(gameList)
                }
            }
        }
    }

    private fun configFilter() {
        sharedViewModel.searchQuery.observe(viewLifecycleOwner) { query ->
            viewModel.configFilter(query)
        }
    }


    private fun initRecyclerView() {
        adapter = GameListAdapter(gameList = emptyList(),
            onClickListener = { onItemSelected(it) },
            onClickStarListener = { onFavItem(it) }) { game, status -> onListedItem(game, status) }

        val llmanager = LinearLayoutManager(requireContext())

        binding.recyclerGameList.layoutManager = llmanager
        binding.recyclerGameList.adapter = adapter

//         Agregar ScrollListener para cargar más juegos al llegar al final de la lista
        binding.recyclerGameList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount: Int = llmanager.childCount
                val pastVisibleItem: Int = llmanager.findLastCompletelyVisibleItemPosition()
                val total = adapter.itemCount
                if (visibleItemCount + pastVisibleItem >= total) {
                    observeLoadingState()
                    viewModel.loadMoreGames()
                    getListGames()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun onFavItem(game: Game) {
        viewModel.onFavItem(game)
    }

    private fun onListedItem(game: Game, status: GameStatus) {
        viewModel.updateGameStatus(game, status)
    }

    private fun onItemSelected(game: Game) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(
            name = game.titulo,
            status = getString(game.status.stringResId),
            pic = game.imagen,
            sinop = game.sinopsis,
            dev = game.dev,
            fav = game.fav,
            date = game.releaseDate,
            plat = game.plataforma.getOrNull(0),
            plat2 = game.plataforma.getOrNull(1),
            plat3 = game.plataforma.getOrNull(2),
            plat4 = game.plataforma.getOrNull(3),
            plat5 = game.plataforma.getOrNull(4),
            plat6 = game.plataforma.getOrNull(5)
        )
        findNavController().navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
