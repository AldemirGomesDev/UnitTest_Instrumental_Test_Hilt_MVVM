package br.com.aldemir.unittesthiltroomapi.data.repo


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.aldemir.unittesthiltroomapi.data.database.Art
import br.com.aldemir.unittesthiltroomapi.data.repo.ArtRepository
import br.com.aldemir.unittesthiltroomapi.model.ImageResponse
import br.com.aldemir.unittesthiltroomapi.util.Resource
import javax.inject.Inject

class FakeArtRepositoryAndroid @Inject constructor(): ArtRepository {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshLiveData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshLiveData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshLiveData() {
        artsLiveData.postValue(arts)
    }


}