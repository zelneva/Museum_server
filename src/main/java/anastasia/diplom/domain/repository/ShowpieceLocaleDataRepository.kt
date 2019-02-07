package anastasia.diplom.domain.repository

import anastasia.diplom.domain.models.ShowpieceLocaleData
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ShowpieceLocaleDataRepository: JpaRepository<ShowpieceLocaleData, UUID> {
}