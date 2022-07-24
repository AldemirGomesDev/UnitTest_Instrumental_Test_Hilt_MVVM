package br.com.aldemir.unittesthiltroomapi.data.repo

import androidx.lifecycle.LiveData
import br.com.aldemir.unittesthiltroomapi.data.database.Art
import br.com.aldemir.unittesthiltroomapi.model.ImageResponse
import br.com.aldemir.unittesthiltroomapi.util.Resource

interface ArtRepository {

    suspend fun insertArt(art : Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>
}