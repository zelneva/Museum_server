package anastasia.diplom.domain.service

import anastasia.diplom.domain.model.Showpiece
import anastasia.diplom.domain.repository.ShowpieceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.util.FileSystemUtils
import java.io.File
import java.util.stream.Stream


@Service
@Transactional(readOnly = true)
open class ShowpieceService : AbstractService {


    companion object {
        lateinit var showpieceRepository: ShowpieceRepository
        lateinit var authorService: AuthorService
        lateinit var exhibitionService: ExhibitionService
        lateinit var showpieceLocaleDataService: ShowpieceLocaleDataService
        lateinit var authorLocaleDataService: AuthorLocaleDataService
    }

    @Autowired
    constructor(repository: ShowpieceRepository, author: AuthorService,
                exhibition: ExhibitionService, showpieceLocaleDataServ: ShowpieceLocaleDataService,
                authorLocaleDataServ: AuthorLocaleDataService, userService: UserService) : super(userService) {
        showpieceRepository = repository
        authorService = author
        exhibitionService = exhibition
        showpieceLocaleDataService = showpieceLocaleDataServ
        authorLocaleDataService = authorLocaleDataServ
    }


    @Transactional
    open fun create(photoSrc: String, year: String, authorName: String,
                    titleRus: String, descriptionRus: String,
                    titleEng: String, descriptionEng: String,
                    titleGer: String, descriptionGer: String) {
        val showpiece = Showpiece()
        showpiece.srcPhoto = photoSrc
        showpiece.date = year
        showpiece.author = authorLocaleDataService.findByName(authorName).author
        showpieceLocaleDataService.create("ru", titleRus, descriptionRus, showpiece)
        showpieceLocaleDataService.create("en", titleEng, descriptionEng, showpiece)
        showpieceLocaleDataService.create("ge", titleGer, descriptionGer, showpiece)
        showpiece.exhibition = null
        showpieceRepository.save(showpiece)
    }


    @Transactional
    open fun update(id: UUID, srcPhoto: String?, year: String, authorName: String,
                    titleRus: String, descriptionRus: String,
                    titleEng: String, descriptionEng: String,
                    titleGer: String, descriptionGer: String): Showpiece {
        val showpiece = showpieceRepository.findOne(id)
        showpiece.srcPhoto = srcPhoto
        showpiece.date = year
        showpiece.author = authorLocaleDataService.findByName(authorName).author
        showpieceLocaleDataService.create("ru", titleRus, descriptionRus, showpiece)
        showpieceLocaleDataService.create("en", titleEng, descriptionEng, showpiece)
        showpieceLocaleDataService.create("ge", titleGer, descriptionGer, showpiece)
        showpiece.exhibition = null
        return showpieceRepository.save(showpiece)
    }


    @Transactional
    open fun delete(id: UUID) {
        showpieceRepository.delete(id)
    }


    fun findOne(id: UUID) = showpieceRepository.findOne(id)


    fun findAll() = showpieceRepository.findAll()


    fun getShowpieceByExhibitionId(exhibitionId: String): List<Showpiece> {
        return findAll().filter { it.exhibition?.id.toString() == exhibitionId }
    }


    fun getShowpieceByAuthor(authorId: String): List<Showpiece> {
        return findAll().filter { showpiece -> showpiece.author?.id.toString() == authorId }
    }


    fun updateExhibition(showpieceId: String, exhibitionId: String) {
        val showpiece = showpieceRepository.findOne(UUID.fromString(showpieceId))
        showpiece.exhibition = exhibitionService.findOne(UUID.fromString(exhibitionId))
        showpieceRepository.save(showpiece)
    }


}
