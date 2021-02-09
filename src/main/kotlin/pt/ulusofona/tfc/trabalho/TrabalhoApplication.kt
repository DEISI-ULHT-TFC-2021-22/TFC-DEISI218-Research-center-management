package pt.ulusofona.tfc.trabalho

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TrabalhoApplication

fun main(args: Array<String>) {
    println("Teste commit")
    runApplication<TrabalhoApplication>(*args)
}
