package com.example.dogapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.dogapp.R
import com.example.dogapp.core.Resource
import com.example.dogapp.data.remote.BreedDataSource
import com.example.dogapp.databinding.FragmentPicsBinding
import com.example.dogapp.domain.BreedRepositoryImpl
import com.example.dogapp.domain.RetrofitClient
import com.example.dogapp.presentation.BreedViewModel


class PicsFragment : Fragment(R.layout.fragment_pics) {

    private lateinit var binding: FragmentPicsBinding
    private val args by navArgs<PicsFragmentArgs>()
    private lateinit var breedImagesAdapter: BreedImagesAdapter
    private val breeds = mutableListOf<String>()

    private val viewModel by viewModels<BreedViewModel> {
        BreedViewModel.BreedViewModelFactory(
            BreedRepositoryImpl(
                BreedDataSource(RetrofitClient.webService)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPicsBinding.bind(view)

        viewModel.fetchBreedListImages(args.breedName).observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success-> {
                    binding.progressBar.visibility = View.GONE
                    breeds.clear()
                    breeds.addAll(it.data.message)

                    if(::breedImagesAdapter.isInitialized) {
                        breedImagesAdapter.updateDataSet(breeds)
                    }
                    initViews()
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility= View.GONE
                }
            }
        })
    }

    private fun initViews(){
        breedImagesAdapter = BreedImagesAdapter(breeds)
        binding.rvPics.adapter = breedImagesAdapter
        binding.rvPics.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
    }

}