package anastasia.diplom

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration

@SpringBootApplication
@EnableAutoConfiguration
open class DiplomApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(DiplomApplication::class.java, *args)
        }
    }
}

