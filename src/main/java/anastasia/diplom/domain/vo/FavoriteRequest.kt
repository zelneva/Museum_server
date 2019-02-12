package anastasia.diplom.domain.vo

import anastasia.diplom.domain.models.Showpiece
import anastasia.diplom.domain.models.User
import lombok.Data

@Data
class FavoriteRequest {
    var user: User? = null
    var showpiece: Showpiece? = null
}
