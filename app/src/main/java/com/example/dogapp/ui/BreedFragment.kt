package com.example.dogapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.dogapp.R
import com.example.dogapp.core.Resource
import com.example.dogapp.data.remote.BreedDataSource
import com.example.dogapp.databinding.FragmentBreedBinding
import com.example.dogapp.domain.BreedRepositoryImpl
import com.example.dogapp.domain.RetrofitClient
import com.example.dogapp.presentation.BreedViewModel


class BreedFragment : Fragment(R.layout.fragment_breed), BreedAdapter.OnItemClickListener {

    private lateinit var binding: FragmentBreedBinding
    private lateinit var breedAdapter: BreedAdapter
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
        binding = FragmentBreedBinding.bind(view)

        viewModel.fetchBreedList().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success-> {
                    binding.progressBar.visibility = View.GONE
                    breeds.clear()
                    breeds.addAll(it.data.message)

                    if(::breedAdapter.isInitialized) {
                        breedAdapter.updateDataSet(breeds)
                    }
                    initViews()
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility=View.GONE
                }
            }
        })
    }

    private fun initViews(){
        breedAdapter = BreedAdapter(breeds,this)
        binding.rvBreeds.adapter = breedAdapter
        binding.rvBreeds.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
    }

    override fun onItemClick(data: String) {
        val action =  BreedFragmentDirections.actionBreedFragmentToPicsFragment(data)
        findNavController().navigate(action)
    }
}