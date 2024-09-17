package no.dilldall.graphql_demo

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class BookController(private val bookService: BookService, private val authorService: AuthorService) {

    @QueryMapping
    fun bookById(@Argument id: String?): Book? = bookService.getById(id!!)

    @SchemaMapping
    fun author(book: Book): Author? = authorService.getById(book.authorId)
}

@Controller
class TodoController(private val todoItemService: TodoItemService) {

    @QueryMapping
    fun todos(): List<TodoItem> = todoItemService.getAll()

    @QueryMapping
    fun todoById(@Argument id: String): TodoItem? = todoItemService.getById(id)

    @MutationMapping
    fun addTodoItem(@Argument name: String, @Argument description: String): TodoItem = todoItemService.add(name, description)

    @MutationMapping
    fun updateTodoItem(@Argument id: String, @Argument name: String?, @Argument description: String?, @Argument completed: Boolean): TodoItem? {
        return todoItemService.update(id, name, description, completed)
    }

    @MutationMapping
    fun deleteTodoItem(@Argument id: String): Boolean = todoItemService.delete(id)
}