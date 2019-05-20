package anastasia.diplom.domain.vo

import lombok.Data

@Data
class UserRequest (
    var name: String,
    var username: String,
    var password: String,
    var role: String,
    var srcPhoto: String?
)