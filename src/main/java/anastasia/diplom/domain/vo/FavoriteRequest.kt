package anastasia.diplom.domain.vo

import anastasia.diplom.domain.model.Showpiece
import anastasia.diplom.domain.model.User
import lombok.Data

@Data
class FavoriteRequest {
    var user: User? = null
    var showpiece: Showpiece? = null
}
