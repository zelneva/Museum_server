package anastasia.diplom.domain.service

import anastasia.diplom.domain.exception.MuseumNotFoundException
import anastasia.diplom.domain.models.Museum
import anastasia.diplom.domain.repository.MuseumRepository
import anastasia.diplom.domain.vo.MuseumRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
open class MuseumService {

    companion object {
        lateinit var museumRepository: MuseumRepository
    }

    @Autowired
    constructor(repository: MuseumRepository) {
        museumRepository = repository
    }


    @Transactional
    open fun create(request: MuseumRequest){
        var museum = Museum()
        museum.name = request.name
        museum.address = request.address
        museum.lat = request.lat
        museum.lng = request.lng
        museumRepository.save(museum)
    }


    @Transactional
    open fun update(id: UUID, museumRequest: MuseumRequest): Museum {
        val museum = museumRepository.findOne(id)
        museum.name = museumRequest.name ?: museum.name
        museum.lng = museumRequest.lng ?: museum.lng
        museum.lat = museumRequest.lat ?: museum.lat
        museum.address = museumRequest.address ?: museum.address
        return museumRepository.save(museum)
    }


    @Transactional
    open fun delete(id: UUID) {
        val museum = museumRepository.findOne(id)
        museumRepository.delete(museum)
    }

    fun findAll() = museumRepository.findAll()


    fun findOne(id: UUID): Museum? {
        val museum = museumRepository.findOne(id)
        return museum ?: throw MuseumNotFoundException(id)

    }

    fun findByName(name: String) = museumRepository.findByName(name)

}