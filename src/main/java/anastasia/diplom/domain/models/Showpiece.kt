package anastasia.diplom.domain.models

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
    @JoinColumn(name = "exhibition_id", nullable = false)
    var exhibition: Exhibition? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.MERGE))
    @JoinColumn(name = "author_id", nullable = false)
    var author: Author? = null

    @Column(name = "date")
    var date: Date? = null

    @Column(name = "genre")
    var genre: String? = null

    @Column(name = "src_photo")
    var srcPhoto: String? = null
}
