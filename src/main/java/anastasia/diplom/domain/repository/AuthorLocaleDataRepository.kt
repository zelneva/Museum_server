package anastasia.diplom.domain.repository

import anastasia.diplom.domain.model.AuthorLocaleData
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuthorLocaleDataRepository : JpaRepository<AuthorLocaleData, UUID> {
    fun findByAuthorId(id: UUID): List<AuthorLocaleData>
}