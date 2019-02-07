package anastasia.diplom.domain.service

import anastasia.diplom.domain.models.Exhibition
import anastasia.diplom.domain.repository.ExhibitionRepository
import anastasia.diplom.domain.vo.ExhibitionRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
open class ExhibitionService {

    companion object {
        lateinit var exhibitionRepository: ExhibitionRepository
    }

    @Autowired
    constructor(repository: ExhibitionRepository) {
        exhibitionRepository = repository
    }


    @Transactional
    open fun create(exhibitionRequest: ExhibitionRequest){
        var exhibition = Exhibition()
        exhibition.name = exhibitionRequest.name
        exhibition.startsAt = exhibitionRequest.startsAt
        exhibition.endsAt = exhibitionRequest.endsAt
        exhibition.museum = exhibitionRequest.museum
        exhibitionRepository.save(exhibition)
    }


    @Transactional
    open fun update(id: UUID, exhibitionRequest: ExhibitionRequest): Exhibition{
        val exhibition = exhibitionRepository.findOne(id)
        exhibition.name = exhibitionRequest.name ?: exhibition.name
        exhibition.startsAt = exhibitionRequest.startsAt ?: exhibition.startsAt
        exhibition.endsAt = exhibitionRequest.endsAt ?: exhibition.endsAt
        exhibition.museum = exhibitionRequest.museum ?: exhibition.museum
        return exhibitionRepository.save(exhibition)
    }


    @Transactional
    open fun delete(id: UUID){
        exhibitionRepository.delete(id)
    }

    fun findOne(id: UUID) = exhibitionRepository.findOne(id)

    fun findAll() = exhibitionRepository.findAll()

//    fun findAllBySort() = exhibitionRepository!!.findAllByOrderByStartsAt()

}