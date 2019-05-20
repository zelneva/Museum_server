package anastasia.diplom.domain.model

import lombok.Data
import org.hibernate.annotations.GenericGenerator

import java.util.Date
import java.util.UUID
import javax.persistence.*

@Data
@Entity
@Table(name = "showpiece")
// Экспонат
class Showpiece {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.MERGE))
    @JoinColumn(name = "exhibition_id", nullable = true)
    var exhibition: Exhibition? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.MERGE))
    @JoinColumn(name = "author_id", nullable = true)
    var author: Author? = null

    @Column(name = "date")
    var date: String? = null

    @Column(name = "src_photo", nullable = true)
    var srcPhoto: String? = null
}
