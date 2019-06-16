package anastasia.diplom.domain.model

import lombok.Data
import org.hibernate.annotations.GenericGenerator
import org.mindrot.jbcrypt.BCrypt

import java.util.UUID
import javax.persistence.*

@Data
@Entity
@Table(name = "users")
class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    var id: UUID? = null

    var name: String? = null

    var username: String? = null

    var password: String? = null

    var role: String? = null

    var srcPhoto: String? = null


    companion object {
        fun generatePassword(password: String): String {
            return BCrypt.hashpw(password, BCrypt.gensalt())
        }

        fun compare(password: String, hash: String): Boolean {
            return BCrypt.checkpw(password, hash)
        }
    }


    override fun toString(): String {
        super.toString()
        return "$id"
    }
}
