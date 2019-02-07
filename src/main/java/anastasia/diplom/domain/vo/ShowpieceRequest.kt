package anastasia.diplom.domain.vo

import anastasia.diplom.domain.models.Author
import anastasia.diplom.domain.models.Exhibition
import lombok.Data
import java.util.*

@Data
class ShowpieceRequest {
    var exhibition: Exhibition? = null
    var author: Author? = null
    var date: Date? = null
    var genre: String? = null
    var srcPhoto: String? = null

}