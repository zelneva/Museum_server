package anastasia.diplom.domain.service

import anastasia.diplom.domain.model.Author
import anastasia.diplom.domain.repository.AuthorRepository
import anastasia.diplom.domain.vo.AuthorRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.util.*

@Service
@Transactional(readOnly = true)
open class AuthorService: AbstractService {

    companion object {
        lateinit var authorRepository: AuthorRepository
        lateinit var authorLocaleDataService: AuthorLocaleDataService
    }

    @Autowired
    constructor(repository: AuthorRepository, authorLD: AuthorLocaleDataService, userServ: UserService): super(userServ){
        authorRepository = repository
        authorLocaleDataService = authorLD
    }


    @Transactional
    open fun create(srcPhoto: String?, born: String, dead: String,
                    titleRus: String, descriptionRus: String,
                    titleEng: String, descriptionEng: String,
                    titleGer: String, descriptionGer: String){
        val author = Author()
        author.bornAt = born
        author.diedAt = dead
        authorLocaleDataService.create("ru", titleRus, descriptionRus, author)
        authorLocaleDataService.create("en", titleEng, descriptionEng, author)
        authorLocaleDataService.create("ge", titleGer, descriptionGer, author)
        authorRepository.save(author)
    }


    @Transactional
    open fun update(id: UUID, srcPhoto: String?, born: String, dead: String,
                    titleRus: String, descriptionRus: String,
                    titleEng: String, descriptionEng: String,
                    titleGer: String, descriptionGer: String): Author{
        val author = authorRepository.findOne(id)
        author.bornAt = born
        author.diedAt = dead
        authorLocaleDataService.create("ru", titleRus, descriptionRus, author)
        authorLocaleDataService.create("en", titleEng, descriptionEng, author)
        authorLocaleDataService.create("ge", titleGer, descriptionGer, author)
        return authorRepository.save(author)
    }


    @Transactional
    open fun delete(id: UUID){
        authorRepository.delete(id)
    }


    fun findOne(id: UUID) = authorRepository.findOne(id)!!


    fun findAll() = authorRepository.findAll()!!
}
