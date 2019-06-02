package anastasia.diplom.domain.model

import lombok.Data
import org.hibernate.annotations.GenericGenerator

import java.util.Date
import java.util.UUID
import javax.persistence.*

@Data
@Entity
@Table(name = "author")
class Author {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    var id: UUID? = null

    @Column(name = "born_at")
    var bornAt: String? = null

    @Column(name = "died_at")
    var diedAt: String? = null //может быть жив

    @Column(name = "photo_src", nullable = true)
    var photoSrc: String? = null
}
