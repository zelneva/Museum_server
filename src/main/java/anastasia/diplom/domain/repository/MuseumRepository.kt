package anastasia.diplom.domain.repository

import anastasia.diplom.domain.models.Museum
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MuseumRepository : JpaRepository<Museum, UUID> {

    fun findByName(name: String): List<Museum>

    fun findByNameIgnoreCaseStartingWith(name: String): List<Museum>
}