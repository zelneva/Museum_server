package anastasia.diplom.domain.model

import lombok.Data
import org.hibernate.annotations.GenericGenerator

import java.util.Date
import java.util.UUID
import javax.persistence.*

@Data
@Entity
@Table(name = "exhibition")
//Выставка
class Exhibition {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    var id: UUID? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "starts_at")
    var startsAt: Date? = null

    @Column(name = "ends_at")
    var endsAt: Date? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.MERGE))
    @JoinColumn(name = "museum_id", nullable = false)
    var museum: Museum? = null
}


