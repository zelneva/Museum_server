package anastasia.diplom.domain.vo

import lombok.Data
import java.util.*

@Data
class AuthorRequest(
        var bornAt: String?,
        var diedAt: String?,
        var photoSrc: String?
)