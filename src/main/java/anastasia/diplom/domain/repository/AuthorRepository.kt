package anastasia.diplom.domain.repository

import anastasia.diplom.domain.model.Author
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuthorRepository:JpaRepository<Author, UUID> {

}