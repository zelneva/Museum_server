package anastasia.diplom.domain.models

import lombok.Data
import org.hibernate.annotations.GenericGenerator

import java.util.UUID
import javax.persistence.*

@Data
@Entity
@Table(name = "showpiece_locale_data")
class ShowpieceLocaleData {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.MERGE))
    @JoinColumn(name = "showpiece_id", nullable = false)
    var showpiece: Showpiece? = null

    var language: String? = null

    var name: String? = null

    var description: String? = null
}
