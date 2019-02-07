package anastasia.diplom.domain.vo

import lombok.Data

@Data
class MuseumRequest {
    var name: String? = null
    var address: String? = null
    var lat: Float? = null
    var lng: Float? = null
}