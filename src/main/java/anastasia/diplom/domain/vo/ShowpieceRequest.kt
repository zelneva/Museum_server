package anastasia.diplom.domain.vo

import lombok.Data

@Data
class ShowpieceRequest(
    var exhibitionId: String?,
    var authorId: String?,
    var date: String?,
    var srcPhoto: String?
)

