package anastasia.diplom.domain.vo

import anastasia.diplom.domain.model.Museum
import lombok.Data
import java.util.*

@Data
class ExhibitionRequest (
    var name: String,
    var startsAt: String,
    var endsAt: String,
    var museumId: String
)