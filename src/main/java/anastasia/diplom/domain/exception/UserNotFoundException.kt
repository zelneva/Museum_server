package anastasia.diplom.domain.exception

import lombok.Getter
import java.util.*

class UserNotFoundException  (@field:Getter
                              private val username: String) : RuntimeException() {
}