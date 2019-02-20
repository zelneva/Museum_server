package anastasia.diplom.domain.repository

import anastasia.diplom.domain.model.Comment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CommentRepository: JpaRepository<Comment, UUID> {
    fun findByShowpieceId(id:UUID): List<Comment>
}