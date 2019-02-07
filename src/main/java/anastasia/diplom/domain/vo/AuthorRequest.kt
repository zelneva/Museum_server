package anastasia.diplom.domain.vo

import lombok.Data
import java.util.*

@Data
class AuthorRequest {
    var bornAt: Date? = null
    var diedAt: Date? = null
}