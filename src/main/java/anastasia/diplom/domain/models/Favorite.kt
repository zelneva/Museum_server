package anastasia.diplom.domain.models

import lombok.Data

import java.util.UUID

@Data
class Favorite {

    var id: UUID? = null

    var user: User? = null

    var showpiece: Showpiece? = null
}
