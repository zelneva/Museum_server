package anastasia.diplom.domain.models

import lombok.Data

import java.util.Date
import java.util.UUID

@Data
class Comment {

    var id: UUID? = null

    var user: User? = null

    var showpiece: Showpiece? = null

    var text: String? = null

    var date: Date? = null
}
