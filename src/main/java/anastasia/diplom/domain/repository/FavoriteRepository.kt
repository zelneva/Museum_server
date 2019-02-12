package anastasia.diplom.domain.repository

import anastasia.diplom.domain.models.Favorite
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FavoriteRepository: JpaRepository<Favorite, UUID> {
    fun findByUserId(id: UUID): List<Favorite>
}