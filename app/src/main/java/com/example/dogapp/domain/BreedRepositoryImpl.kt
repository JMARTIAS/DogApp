package com.example.dogapp.domain

import com.example.dogapp.data.model.BreedResponse
import com.example.dogapp.data.remote.BreedDataSource

class BreedRepositoryImpl(private val dataSource: BreedDataSource) : BreedRepository {
    override suspend fun getBreedResponse(): BreedResponse = dataSource.getBreedList()
    override suspend fun getBreedImages(breed: String): BreedResponse =
        dataSource.getBreedImages(breed)
}