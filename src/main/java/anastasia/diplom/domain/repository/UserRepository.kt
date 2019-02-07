package anastasia.diplom.domain.repository

import anastasia.diplom.domain.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, UUID> {
}