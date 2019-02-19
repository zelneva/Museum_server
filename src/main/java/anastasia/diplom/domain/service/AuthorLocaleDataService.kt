package anastasia.diplom.domain.service

import anastasia.diplom.domain.models.AuthorLocaleData
import anastasia.diplom.domain.repository.AuthorLocaleDataRepository
import anastasia.diplom.domain.vo.AuthorLocaleDataRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
@Transactional(readOnly = true)
open class AuthorLocaleDataService : AbstractService {

    companion object {
        lateinit var authorLocaleDataRepository: AuthorLocaleDataRepository
    }

    @Autowired
    constructor(repository: AuthorLocaleDataRepository, userService: UserService) : super(userService) {
        authorLocaleDataRepository = repository
    }


    @Transactional
    open fun create(authorLocaleDataRequest: AuthorLocaleDataRequest) {
        val authorLocaleData = AuthorLocaleData()
        authorLocaleData.name = authorLocaleDataRequest.name
        authorLocaleData.description = authorLocaleDataRequest.description
        authorLocaleData.language = authorLocaleDataRequest.language
        authorLocaleData.author = authorLocaleDataRequest.author
        authorLocaleDataRepository.saveAndFlush(authorLocaleData)
    }


    @Transactional
    open fun update(id: UUID, authorLocaleDataRequest: AuthorLocaleDataRequest): AuthorLocaleData {
        var authorLocaleData = authorLocaleDataRepository.findOne(id)
        authorLocaleData.name = authorLocaleDataRequest.name ?: authorLocaleData.name
        authorLocaleData.author = authorLocaleDataRequest.author ?: authorLocaleData.author
        authorLocaleData.description = authorLocaleDataRequest.description ?: authorLocaleData.description
        authorLocaleData.language = authorLocaleDataRequest.language ?: authorLocaleData.description
        return authorLocaleDataRepository.save(authorLocaleData)
    }


    @Transactional
    open fun delete(id: UUID) {
        authorLocaleDataRepository.delete(id)
    }


    fun findOne(id: UUID) = authorLocaleDataRepository.findOne(id)

    fun findAll() = authorLocaleDataRepository.findAll()


    //return list all of data about author by id
    fun findDataById(authorId: UUID) = authorLocaleDataRepository.findByAuthorId(authorId)

}