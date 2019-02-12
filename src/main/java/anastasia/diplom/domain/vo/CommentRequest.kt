package anastasia.diplom.domain.vo

import anastasia.diplom.domain.models.Showpiece
import anastasia.diplom.domain.models.User
import lombok.Data
import java.util.*

@Data
class CommentRequest {
    var user: User? = null
    var showpiece: Showpiece? = null
    var text: String? = null
    var date: Date? = null
}