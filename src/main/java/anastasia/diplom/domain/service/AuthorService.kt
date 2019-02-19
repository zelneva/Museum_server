package anastasia.diplom.domain.service

import anastasia.diplom.domain.models.Author
import anastasia.diplom.domain.repository.AuthorRepository
import anastasia.diplom.domain.vo.AuthorRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
open class AuthorService: AbstractService {

    companion object {
        lateinit var authorRepository: AuthorRepository
    }

    @Autowired
    constructor(repository: AuthorRepository,userServ: UserService): super(userServ){
        authorRepository = repository
    }


    @Transactional
    open fun create(request: AuthorRequest){
        val author = Author()
        author.bornAt = request.bornAt
        author.diedAt = request.diedAt
        authorRepository.save(author)
    }


    @Transactional
    open fun update(id: UUID, request: AuthorRequest): Author{
        val author = authorRepository.findOne(id)
        author.bornAt = request.bornAt ?: author.bornAt
        author.diedAt = request.diedAt ?: author.diedAt
        return authorRepository.save(author)
    }


    @Transactional
    open fun delete(id: UUID){
        authorRepository.delete(id)
    }


    fun findOne(id: UUID) = authorRepository.findOne(id)


    fun findAll() = authorRepository.findAll()
}
