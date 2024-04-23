package com.cvalera.ludex.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cvalera.ludex.domain.model.GameStatus
import com.cvalera.ludex.R
import com.cvalera.ludex.presentation.adapter.GameListAdapter
import com.cvalera.ludex.databinding.FragmentFavBinding
import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.presentation.viewmodel.FavViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment() {

    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: GameListAdapter
    private var isLoading = false

    private val viewModel: FavViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterChips()
        searchInList()
        initRecyclerView()
        configSwipe()
        observeFavGameList()
        getListGames()
    }

    override fun onResume() {
    super.onResume()
    getListGames()
    }
    private fun getListGames() {
        viewModel.getListGames()
    }

    private fun observeFavGameList() {
        viewModel.favGameList.observe(viewLifecycleOwner) { favGameList ->
            adapter.updateGames(favGameList)
        }
    }

    private fun configSwipe() {

//        binding.swipe.setColorSchemeResources(R.color.md_theme_outline_highContrast, R.color.md_theme_primary_highContrast)
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
        binding.etFilter.addTextChangedListener { userSearch ->
            viewModel.searchInList(userSearch.toString())
        }
    }

    private fun filterChips() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == View.NO_ID) {
                viewModel.getListGames()
            } else {
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
                    R.id.chipSC -> {
                        filterByStatus(GameStatus.SIN_CLASIFICAR)
                    }
                }
            }
        }
    }

    private fun filterByStatus(status: GameStatus) {
        viewModel.filterByStatus(status)
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
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que quieres eliminar este juego de la lista de favoritos?")
                .setPositiveButton("Eliminar") { _, _ ->
                    // Acción de eliminación del juego
                    viewModel.onFavItem(game)
                }
                .setNegativeButton("Cancelar", null)
                .create()
            alertDialog.show()
    }

    private fun onItemSelected(game: Game) {
        val action = FavFragmentDirections.actionFavFragment2ToDetailFragment(
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

    private fun onListedItem(game: Game, status: GameStatus) {
        viewModel.updateGameStatus(game, status)
    }

}