package anastasia.diplom.domain.service

import anastasia.diplom.domain.models.Showpiece
import anastasia.diplom.domain.repository.ShowpieceRepository
import anastasia.diplom.domain.vo.ShowpieceRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
open class ShowpieceService: AbstractService {

    companion object {
        lateinit var showpieceRepository: ShowpieceRepository
    }

    @Autowired
    constructor(repository: ShowpieceRepository, userService: UserService) : super(userService) {
        showpieceRepository = repository
    }


    @Transactional
    open fun create(showpieceRequest: ShowpieceRequest) {
        val showpiece = Showpiece()
        showpiece.author = showpieceRequest.author
        showpiece.date = showpieceRequest.date
        showpiece.exhibition = showpieceRequest.exhibition
        showpiece.genre = showpieceRequest.genre
        showpiece.srcPhoto = showpieceRequest.srcPhoto
        showpieceRepository.save(showpiece)
    }


    @Transactional
    open fun update(id: UUID, showpieceRequest: ShowpieceRequest): Showpiece {
        val showpiece = showpieceRepository.findOne(id)
        showpiece.author = showpieceRequest.author ?: showpiece.author
        showpiece.date = showpieceRequest.date ?: showpiece.date
        showpiece.exhibition = showpieceRequest.exhibition ?: showpiece.exhibition
        showpiece.genre = showpieceRequest.genre ?: showpiece.genre
        showpiece.srcPhoto = showpieceRequest.srcPhoto ?: showpiece.srcPhoto
        return showpieceRepository.save(showpiece)
    }


    @Transactional
    open fun delete(id: UUID) {
        showpieceRepository.delete(id)
    }

    fun findOne(id: UUID) = showpieceRepository.findOne(id)

    fun findAll() = showpieceRepository.findAll()
}