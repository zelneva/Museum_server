package anastasia.diplom.domain.service

import anastasia.diplom.domain.exception.MuseumNotFoundException
import anastasia.diplom.domain.model.Museum
import anastasia.diplom.domain.repository.MuseumRepository
import anastasia.diplom.domain.vo.MuseumRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
open class MuseumService : AbstractService {

    companion object {
        lateinit var museumRepository: MuseumRepository
    }

    @Autowired
    constructor(repository: MuseumRepository, userService: UserService) : super(userService) {
        museumRepository = repository
    }


    @Transactional
    open fun create(request: MuseumRequest) {
        var museum = Museum()
        museum.photoSrc = request.photoSrc
        museum.name = request.name
        museum.address = request.address
        museumRepository.save(museum)
    }


    @Transactional
    open fun update(id: UUID, museumRequest: MuseumRequest): Museum {
        val museum = museumRepository.findOne(id)
        museum.photoSrc = museumRequest.photoSrc?: museum.photoSrc
        museum.name = museumRequest.name ?: museum.name
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