package no.dilldall.graphql_demo

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Entity
data class Book(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val pageCount: Int = 0,
    val authorId: String
) {
    constructor() : this(UUID.randomUUID(), "", 0, ""){}
}

@Entity
data class Author(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val country: String
) {
    constructor() : this(UUID.randomUUID(), "", "", ""){}
}

@Entity
data class TodoItem(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var description: String,
    var completed: Boolean
) {
    constructor() : this(UUID.randomUUID(), "", "", false){}
    constructor(name: String, description: String, completed: Boolean) : this(UUID.randomUUID(), name, description, completed)
}


interface BookRepository : JpaRepository<Book, String>
interface AuthorRepository : JpaRepository<Author, String>
interface TodoItemRepository : JpaRepository<TodoItem, UUID>