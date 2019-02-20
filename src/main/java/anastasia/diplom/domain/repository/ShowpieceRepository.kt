package anastasia.diplom.domain.repository

import anastasia.diplom.domain.model.Showpiece
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ShowpieceRepository : JpaRepository<Showpiece, UUID> {

}