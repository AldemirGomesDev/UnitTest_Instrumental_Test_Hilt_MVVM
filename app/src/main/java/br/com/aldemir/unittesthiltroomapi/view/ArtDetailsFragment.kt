package br.com.aldemir.unittesthiltroomapi.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.aldemir.unittesthiltroomapi.R
import br.com.aldemir.unittesthiltroomapi.databinding.FragmentArtDetailsBinding
import br.com.aldemir.unittesthiltroomapi.util.Status
import br.com.aldemir.unittesthiltroomapi.view.viewmodel.ArtViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "TestImageSearch"

@AndroidEntryPoint
class ArtDetailsFragment @Inject constructor(
    val glide : RequestManager
) : Fragment(R.layout.fragment_art_details) {

    lateinit var viewModel: ArtViewModel

    private var _binding : FragmentArtDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: ArtViewModel by activityViewModels()
        this.viewModel = viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()

        binding.artImageView.setOnClickListener {
            findNavController().navigate(
                ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment()
            )
        }


        val callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewModel.setSelectedImage("")
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding.saveButton.setOnClickListener {
            viewModel.makeArt(binding.nameText.text.toString(),
                binding.artistText.text.toString(),
                binding.yearText.text.toString())

        }

    }

    private fun subscribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            binding.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}