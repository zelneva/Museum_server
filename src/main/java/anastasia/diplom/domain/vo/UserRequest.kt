package anastasia.diplom.domain.vo

import lombok.Data

@Data
class UserRequest {
    var name: String? = null
    var username: String? = null
    var password: String? = null
    var role: String? = null
}