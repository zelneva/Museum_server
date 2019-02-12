package anastasia.diplom.domain.models

import lombok.Data
import org.hibernate.annotations.GenericGenerator

import java.util.UUID
import javax.persistence.*

@Data
@Entity
@Table(name = "favorite")
class Favorite {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.MERGE))
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.MERGE))
    @JoinColumn(name = "showpiece_id", nullable = false)
    var showpiece: Showpiece? = null
}
