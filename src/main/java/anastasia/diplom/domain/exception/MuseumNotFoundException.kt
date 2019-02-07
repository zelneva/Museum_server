package anastasia.diplom.domain.exception

import lombok.Getter
import java.util.*

class MuseumNotFoundException(@field:Getter
                              private val id: UUID) : RuntimeException()