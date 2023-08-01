package com.example.dogapp.data.remote

import com.example.dogapp.data.model.BreedResponse
import com.example.dogapp.domain.WebService

class BreedDataSource(private val webService: WebService) {
    suspend fun getBreedList(): BreedResponse = webService.getBreedResponse()
    suspend fun getBreedImages(breed: String): BreedResponse = webService.getBreedImages(breed)
}