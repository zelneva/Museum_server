package anastasia.diplom.domain.models

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
    var bornAt: Date? = null

    @Column(name = "died_at")
    var diedAt: Date? = null //может быть жив
}
