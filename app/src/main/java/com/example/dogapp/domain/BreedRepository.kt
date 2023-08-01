package com.example.dogapp.domain

import com.example.dogapp.data.model.BreedResponse

interface BreedRepository {
    suspend fun getBreedResponse(): BreedResponse
    suspend fun getBreedImages(breed: String): BreedResponse
}