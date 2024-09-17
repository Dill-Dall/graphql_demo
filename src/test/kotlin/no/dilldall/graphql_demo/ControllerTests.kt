package no.dilldall.graphql_demo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.graphql.test.tester.GraphQlTester

@GraphQlTest(BookController::class)
@Import(BookService::class, BookRepository::class)
@TestConfiguration

class BookControllerTests {
    @Autowired
    private lateinit var graphQlTester: GraphQlTester

    @Test
    fun shouldGetFirstBook() {
        graphQlTester
            .documentName("bookDetails")
            .variable("id", "book-1")
            .execute()
            .path("data.bookById")
            .matchesJson(
                """
                    {
                        "id": "book-1",
                        "name": "Effective Java",
                        "pageCount": 416,
                        "author": {
                          "firstName": "Joshua",
                          "lastName": "Bloch"
                        }
                    }
                """.trimIndent()
            )
    }
}

@GraphQlTest(TodoController::class)
@Import(TodoItemService::class, TodoItemRepository::class)
@TestConfiguration
class TodoControllerTests {

    @Autowired
    private lateinit var graphQlTester: GraphQlTester

    @Test
    fun shouldAddTodoItem() {
        graphQlTester
            .document("""
                mutation {
                    addTodoItem(name: "Test Todo", description: "This is a test todo") {
                        id
                        name
                        description
                        completed
                    }
                }
            """)
            .execute()
            .path("data.addTodoItem")
            .matchesJson("""
                {
                    "name": "Test Todo",
                    "description": "This is a test todo",
                    "completed": false
                }
            """)
    }

    @Test
    fun shouldGetTodos() {
        graphQlTester
            .document("""
                query {
                    todos {
                        id
                        name
                        description
                        completed
                    }
                }
            """)
            .execute()
            .path("data.todos")
            .entityList(TodoItem::class.java)
            .hasSize(1)
    }
}

@TestConfiguration
@EnableJpaRepositories(basePackages = ["com.example.demo"])
@ComponentScan(basePackages = ["com.example.demo"])
class TestConfig {

}