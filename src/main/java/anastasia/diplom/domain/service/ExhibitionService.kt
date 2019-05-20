package anastasia.diplom.domain.service

import anastasia.diplom.domain.model.Exhibition
import anastasia.diplom.domain.repository.ExhibitionRepository
import anastasia.diplom.domain.repository.MuseumRepository
import anastasia.diplom.domain.vo.ExhibitionRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.util.*

@Service
@Transactional(readOnly = true)
open class ExhibitionService : AbstractService {

    companion object {
        lateinit var exhibitionRepository: ExhibitionRepository
        lateinit var museumService: MuseumService
    }

    @Autowired
    constructor(repository: ExhibitionRepository, userService: UserService, museum: MuseumService) : super(userService) {
        exhibitionRepository = repository
        museumService = museum
    }


    @Transactional
    open fun create(exhibitionRequest: ExhibitionRequest) {
        var exhibition = Exhibition()
        exhibition.name = exhibitionRequest.name
        exhibition.startsAt = SimpleDateFormat("dd/MM/yyyy").parse(exhibitionRequest.startsAt)
        exhibition.endsAt = SimpleDateFormat("dd/MM/yyyy").parse(exhibitionRequest.endsAt)
        exhibition.museum = museumService.findOne(UUID.fromString(exhibitionRequest.museumId))
        exhibitionRepository.save(exhibition)
    }


    @Transactional
    open fun update(id: UUID, exhibitionRequest: ExhibitionRequest): Exhibition {
        val exhibition = exhibitionRepository.findOne(id)
        exhibition.name = exhibitionRequest.name ?: exhibition.name
        exhibition.startsAt = SimpleDateFormat("dd/MM/yyyy").parse(exhibitionRequest.startsAt) ?: exhibition.startsAt
        exhibition.endsAt = SimpleDateFormat("dd/MM/yyyy").parse(exhibitionRequest.endsAt) ?: exhibition.endsAt
        exhibition.museum = museumService.findOne(UUID.fromString(exhibitionRequest.museumId)) ?: exhibition.museum
        return exhibitionRepository.save(exhibition)
    }


    @Transactional
    open fun delete(id: UUID) {
        exhibitionRepository.delete(id)
    }

    fun findOne(id: UUID) = exhibitionRepository.findOne(id)

    fun findAll() = exhibitionRepository.findAll()

    fun findExhibitionsByMuseumId(museumId: String): List<Exhibition> =
            findAll().filter {it.museum!!.id!!.toString() == museumId }


}