package com.cvalera.ludex.presentation.mylist

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cvalera.ludex.domain.model.GameStatus
import com.cvalera.ludex.R
import com.cvalera.ludex.presentation.adapter.GameListAdapter
import com.cvalera.ludex.databinding.FragmentMyListBinding
import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.presentation.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyListFragment : Fragment() {

    private var _binding: FragmentMyListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: GameListAdapter
    private val viewModel: MyListViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterChips()
        searchInList()
        initRecyclerView()
        configSwipe()
        observeMyGameList()
        viewModel.getListGames()
    }


    override fun onResume() {
        super.onResume()
        viewModel.getListGames()
    }


    private fun observeMyGameList() {
        viewModel.listedGameList.observe(viewLifecycleOwner) { listedGameList ->
            adapter.updateGames(listedGameList)
        }
    }

    private fun configSwipe() {

//        binding.swipe.setColorSchemeResources(R.color.grey, R.color.blueoscuro)
        binding.swipe.setOnRefreshListener {
            if (!isLoading) {
                isLoading = true
                binding.swipe.isRefreshing = true
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.swipe.isRefreshing = false
                    isLoading = false
                }, 2000)
            }
        }
    }

    private fun searchInList() {
        sharedViewModel.searchQuery.observe(viewLifecycleOwner) { query ->
            viewModel.searchInList(query)
        }
    }

    private fun filterByStatus(status: GameStatus) {
        viewModel.filterByStatus(status)
    }

    private fun filterChips() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            // Comprueba si no se ha seleccionado ningún chip
            if (checkedId == View.NO_ID) {
                viewModel.getListGames()
            } else {
                // Si se ha seleccionado un chip, aplica el filtro correspondiente
                when (checkedId) {
                    R.id.chipCompletado -> {
                        filterByStatus(GameStatus.COMPLETADO)
                    }

                    R.id.chipPendiente -> {
                        filterByStatus(GameStatus.PENDIENTE)
                    }

                    R.id.chipAbandonado -> {
                        filterByStatus(GameStatus.ABANDONADO)
                    }

                    R.id.chipJugando -> {
                        filterByStatus(GameStatus.JUGANDO)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        val llmanager = LinearLayoutManager(requireContext())
        adapter = GameListAdapter(
            gameList = emptyList(),
            onClickListener = { onItemSelected(it) },
            onClickStarListener = { onFavItem(it) }
        ) { game, status -> onListedItem(game, status) }

        DividerItemDecoration(binding.recyclerGameList.context, llmanager.orientation)
        binding.recyclerGameList.layoutManager = llmanager
        binding.recyclerGameList.adapter = adapter
    }

    private fun onFavItem(game: Game) {
        viewModel.onFavItem(game)
    }

    private fun onListedItem(game: Game, status: GameStatus) {
        viewModel.updateGameStatus(game, status)
    }

    private fun onItemSelected(game: Game) {
        val action = MyListFragmentDirections.actionMyListFragmentToDetailFragment(
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