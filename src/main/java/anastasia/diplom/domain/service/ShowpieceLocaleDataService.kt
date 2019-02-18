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
        lateinit var userService: UserService
    }

    @Autowired
    constructor(repository: ShowpieceLocaleDataRepository, userServ: UserService) {
        showpieceLocaleDataRepository = repository
        ShowpieceService.userService = userServ
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
    fun findDataById(showpieceId: UUID) = showpieceLocaleDataRepository.findByShowpieceId(showpieceId)


    fun isAdmin(sessionId: String): Boolean {
        if (ShowpieceService.userService.checkUserInRedis(sessionId)) {
            val userId = ShowpieceService.userService.getUserIdFromSessionId(sessionId)
            val user = UserService.userRepository.findOne(UUID.fromString(userId))
            if (user.role == "admin") {
                return true
            }
        }
        return false
    }
}