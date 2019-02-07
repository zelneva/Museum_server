package anastasia.diplom.domain.models

import lombok.Data
import org.hibernate.annotations.GenericGenerator

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Data
@Entity
@Table(name = "museum")
class Museum {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    var id: UUID? = null

    var name: String? = null

    var address: String? = null

    var lat: Float? = null

    var lng: Float? = null
}