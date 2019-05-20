package anastasia.diplom.domain.vo

import lombok.Data

@Data
class AuthorLocaleDataRequest(
    var name: String,
    var language: String,
    var description: String,
    var authorId: String?
)