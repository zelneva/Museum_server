package anastasia.diplom.domain.model

import lombok.Data
import org.hibernate.annotations.GenericGenerator

import java.util.UUID
import javax.persistence.*

@Data
@Entity
@Table(name = "author_locale_data")
class AuthorLocaleData {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.MERGE))
    @JoinColumn(name = "author_id",nullable = true)
    var author: Author? = null

    @Column(name = "language")
    var language: String? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "description")
    var description: String? = null

}
