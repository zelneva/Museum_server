package anastasia.diplom.domain.vo

import anastasia.diplom.domain.model.Museum
import lombok.Data
import java.util.*

@Data
class ExhibitionRequest {
    var name: String? = null
    var startsAt: Date? = null
    var endsAt: Date? = null
    var museum: Museum? = null
}