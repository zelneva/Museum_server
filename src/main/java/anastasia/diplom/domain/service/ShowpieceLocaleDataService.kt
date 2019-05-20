package anastasia.diplom.domain.service

import anastasia.diplom.domain.model.Showpiece
import anastasia.diplom.domain.model.ShowpieceLocaleData
import anastasia.diplom.domain.repository.ShowpieceLocaleDataRepository
import anastasia.diplom.domain.vo.ShowpieceLocaleDataRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
open class ShowpieceLocaleDataService : AbstractService {

    companion object {
        lateinit var showpieceLocaleDataRepository: ShowpieceLocaleDataRepository
        lateinit var exhibitionService: ExhibitionService
    }

    @Autowired
    constructor(repository: ShowpieceLocaleDataRepository, exhibitionServ: ExhibitionService, userServ: UserService) : super(userServ) {
        showpieceLocaleDataRepository = repository
        exhibitionService = exhibitionServ
    }


    @Transactional
    open fun create(language: String, name: String, description: String, showpiece: Showpiece) {
        val showpieceLocaleData = ShowpieceLocaleData()
        showpieceLocaleData.description = description
        showpieceLocaleData.language = language
        showpieceLocaleData.name = name
        showpieceLocaleData.showpiece = showpiece
        showpieceLocaleDataRepository.save(showpieceLocaleData)
    }


    @Transactional
    open fun update(id: UUID, language: String, name: String, description: String, showpiece: Showpiece): ShowpieceLocaleData {
        val showpieceLocaleData = showpieceLocaleDataRepository.findOne(id)
        showpieceLocaleData.name = name ?: showpieceLocaleData.name
        showpieceLocaleData.showpiece = showpiece
        showpieceLocaleData.language = language ?: showpieceLocaleData.language
        showpieceLocaleData.description = description ?: showpieceLocaleData.description
        return showpieceLocaleDataRepository.save(showpieceLocaleData)
    }


    @Transactional
    open fun delete(id: UUID) {
        showpieceLocaleDataRepository.delete(id)
    }

    fun findOne(id: UUID) = showpieceLocaleDataRepository.findOne(id)

    fun findAll() = showpieceLocaleDataRepository.findAll()


    //return list all of data about showpiece by id
    fun findDataById(showpieceId: UUID) = showpieceLocaleDataRepository.findByShowpieceId(showpieceId)


    fun updateExhibition(array: Array<String>, exhibitionId: String) {
        for (a in array) {
            val showpieceLocaleData= showpieceLocaleDataRepository.findOne(UUID.fromString(a))
            showpieceLocaleData.showpiece!!.exhibition =  exhibitionService.findOne(UUID.fromString(exhibitionId))
            showpieceLocaleDataRepository.save(showpieceLocaleData)
        }
    }

}