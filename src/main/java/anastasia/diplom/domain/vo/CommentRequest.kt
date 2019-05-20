package anastasia.diplom.domain.vo

import anastasia.diplom.domain.model.Showpiece
import anastasia.diplom.domain.model.User
import lombok.Data
import java.util.*

@Data
class CommentRequest(
        var user: User,
        var showpieceId: String,
        var text: String,
        var date: String
)