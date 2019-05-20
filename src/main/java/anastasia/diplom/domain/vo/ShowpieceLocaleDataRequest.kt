package anastasia.diplom.domain.vo

import anastasia.diplom.domain.model.Showpiece
import lombok.Data

@Data
class ShowpieceLocaleDataRequest() {


    lateinit var id: String
    lateinit var showpieceId: String
    lateinit var language: String
    lateinit var name: String
    lateinit var description: String

    constructor(id: String,
                showpieceId: String,
                language: String,
                name: String,
                description: String) : this() {
        this.id = id
        this.showpieceId = showpieceId
        this.language = language
        this.name = name
        this.description = description

    }
}