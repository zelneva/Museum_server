package anastasia.diplom.domain.service

import anastasia.diplom.domain.models.ShowpieceLocaleData
import anastasia.diplom.domain.repository.ShowpieceLocaleDataRepository
import anastasia.diplom.domain.vo.ShowpieceLocaleDataRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
open class ShowpieceLocaleDataService {

    companion object {
        lateinit var showpieceLocaleDataRepository: ShowpieceLocaleDataRepository
    }

    @Autowired
    constructor(repository: ShowpieceLocaleDataRepository) {
        showpieceLocaleDataRepository = repository
    }


    @Transactional
    open fun create(showpieceLocaleDataRequest: ShowpieceLocaleDataRequest) {
        val showpieceLocaleData = ShowpieceLocaleData()
        showpieceLocaleData.description = showpieceLocaleDataRequest.description
        showpieceLocaleData.language = showpieceLocaleDataRequest.language
        showpieceLocaleData.name = showpieceLocaleDataRequest.name
        showpieceLocaleData.showpiece = showpieceLocaleDataRequest.showpiece
        showpieceLocaleDataRepository.save(showpieceLocaleData)
    }


    @Transactional
    open fun update(id: UUID, showpieceLocaleDataRequest: ShowpieceLocaleDataRequest): ShowpieceLocaleData {
        val showpieceLocaleData = showpieceLocaleDataRepository.findOne(id)
        showpieceLocaleData.name = showpieceLocaleDataRequest.name ?: showpieceLocaleData.name
        showpieceLocaleData.showpiece = showpieceLocaleDataRequest.showpiece ?: showpieceLocaleData.showpiece
        showpieceLocaleData.language = showpieceLocaleDataRequest.language ?: showpieceLocaleData.language
        showpieceLocaleData.description = showpieceLocaleDataRequest.description ?: showpieceLocaleData.description
        return showpieceLocaleDataRepository.save(showpieceLocaleData)
    }


    @Transactional
    open fun delete(id:UUID){
        showpieceLocaleDataRepository.delete(id)
    }

    fun findOne(id:UUID) = showpieceLocaleDataRepository.findOne(id)

    fun findAll() = showpieceLocaleDataRepository.findAll()


    //return list all of data about showpiece by id
    fun findDataById(showpieceId: UUID): List<ShowpieceLocaleData>{
        var dataListByShowpieceId = mutableListOf<ShowpieceLocaleData>()
        for (i in showpieceLocaleDataRepository.findAll()){
            if(i.showpiece!!.id  == showpieceId){
                dataListByShowpieceId.add(i)
            }
        }
        return dataListByShowpieceId
    }
}