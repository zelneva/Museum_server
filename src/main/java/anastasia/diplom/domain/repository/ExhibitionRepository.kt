package anastasia.diplom.domain.repository

import anastasia.diplom.domain.models.Exhibition
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ExhibitionRepository: JpaRepository<Exhibition, UUID> {
    open fun findAllByOrderByStartsAt()
}