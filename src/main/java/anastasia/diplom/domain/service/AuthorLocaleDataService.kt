package anastasia.diplom.domain.service

import anastasia.diplom.domain.model.Author
import anastasia.diplom.domain.model.AuthorLocaleData
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
    constructor(repository: AuthorLocaleDataRepository,userService: UserService) : super(userService) {
        authorLocaleDataRepository = repository
    }


    @Transactional
    open fun create(language: String, name: String, description: String, author: Author) {
        val authorLocaleData = AuthorLocaleData()
        authorLocaleData.name = name
        authorLocaleData.description = description
        authorLocaleData.language = language
        authorLocaleData.author = author
        authorLocaleDataRepository.save(authorLocaleData)
    }


    @Transactional
    open fun update(id: UUID, language: String, name: String?, description: String?, author: Author?): AuthorLocaleData {
        var authorLocaleData = authorLocaleDataRepository.findOne(id)
        authorLocaleData.name = name  ?: authorLocaleData.name
        authorLocaleData.author = author ?: authorLocaleData.author
        authorLocaleData.description = description ?: authorLocaleData.description
        authorLocaleData.language = language
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

    fun findByName(name: String) = authorLocaleDataRepository.findAll().filter { it.name == name }.get(0)

}