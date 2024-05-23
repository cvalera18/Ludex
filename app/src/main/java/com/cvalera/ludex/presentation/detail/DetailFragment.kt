package com.cvalera.ludex.presentation.detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cvalera.ludex.R
import com.cvalera.ludex.core.util.PlatformLogos
import com.cvalera.ludex.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentDetailBinding? = null

    private var listener: OnDetailFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnDetailFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener?.onDetailFragmentOpened()
        initInfo()
        binding.ivBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initInfo() {
        val args = DetailFragmentArgs.fromBundle(requireArguments())
        val NAME = args.name
        val PLAT = args.plat
        val PLAT2 = args.plat2
        val PLAT3 = args.plat3
        val PLAT4 = args.plat4
        val PLAT5 = args.plat5
        val PLAT6 = args.plat6
        val STATUS = args.status
        val PIC = args.pic
        val SINOP = args.sinop
        val DEV = args.dev
        val FAV = args.fav
        val DATE = args.date

        if (FAV) {
            binding.ivStarDetail.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.baseline_star_24
                )
            )
        } else {
            binding.ivStarDetail.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.baseline_star_outline_24
                )
            )
        }
        binding.tvGameName.text = NAME
        loadImages(PLAT, PLAT2, PLAT3, PLAT4, PLAT5, PLAT6)
        binding.tvStatusSpec.text = STATUS
        Glide.with(this).load(PIC).fitCenter().into(binding.ivGameDetail)
        binding.tvSinopsisSpec.text = SINOP
        binding.tvDevSpec.text = DEV
        binding.tvLaunchDateSpec.text = DATE
    }

    private fun loadImages(vararg platforms: String?) {
        val imageViews = listOf(
            binding.ivPlat1, binding.ivPlat2, binding.ivPlat3,
            binding.ivPlat4, binding.ivPlat5, binding.ivPlat6
        )
        platforms.forEachIndexed { index, platform ->
            platform?.let {
                val logoResId = PlatformLogos.platformLogoMap[it]
                if (logoResId != null) {
                    imageViews[index].setImageResource(logoResId)
                } else {
                    Glide.with(this).load(it).fitCenter().into(imageViews[index])
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listener?.onDetailFragmentClosed()
    }
}