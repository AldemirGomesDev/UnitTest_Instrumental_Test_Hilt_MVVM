package br.com.aldemir.unittesthiltroomapi.data.repo

import androidx.lifecycle.LiveData
import br.com.aldemir.unittesthiltroomapi.data.api.RetrofitAPI
import br.com.aldemir.unittesthiltroomapi.data.database.Art
import br.com.aldemir.unittesthiltroomapi.data.database.ArtDao
import br.com.aldemir.unittesthiltroomapi.model.ImageResponse
import br.com.aldemir.unittesthiltroomapi.util.Resource
import java.lang.Exception
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor (
    private val artDao : ArtDao,
    private val retrofitApi : RetrofitAPI
) : ArtRepository {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitApi.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else {
                Resource.error("Error",null)
            }
        } catch (e: Exception) {
            Resource.error("No data!",null)
        }
    }


}