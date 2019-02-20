package anastasia.diplom.domain.vo

import anastasia.diplom.domain.model.Showpiece
import anastasia.diplom.domain.model.User
import lombok.Data
import java.util.*

@Data
class CommentRequest {
    var user: User? = null
    var showpiece: Showpiece? = null
    var text: String? = null
    var date: Date? = null
}