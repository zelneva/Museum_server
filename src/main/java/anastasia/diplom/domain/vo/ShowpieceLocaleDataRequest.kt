package anastasia.diplom.domain.vo

import anastasia.diplom.domain.models.Showpiece
import lombok.Data

@Data
class ShowpieceLocaleDataRequest {
    var showpiece: Showpiece? = null
    var language: String? = null
    var name: String? = null
    var description: String? = null
}