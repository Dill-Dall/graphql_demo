package no.dilldall.graphql_demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@EntityScan
@SpringBootApplication
class GraphqlDemoApplication

fun main(args: Array<String>) {
    runApplication<GraphqlDemoApplication>(*args)
}