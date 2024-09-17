package no.dilldall.graphql_demo

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class BookService(private val bookRepository: BookRepository) {
    fun getById(id: String): Book? = bookRepository.findById(id).orElse(null)
}

@Service
class AuthorService(private val authorRepository: AuthorRepository) {
    fun getById(id: String): Author? = authorRepository.findById(id).orElse(null)
}

@Service
class TodoItemService(private val todoItemRepository: TodoItemRepository) {
    fun getAll(): List<TodoItem> = todoItemRepository.findAll()
    fun getById(id: String): TodoItem? = todoItemRepository.findById(UUID.fromString(id)).orElse(null)
    fun add(name: String, description: String): TodoItem = todoItemRepository.save(TodoItem(name = name, description = description, completed = false))
    fun update(id: String, name: String?, description: String?, completed: Boolean?): TodoItem? {
        val todo = todoItemRepository.findById(UUID.fromString(id)).orElse(null) ?: return null
        if (name != null) {
            todo.name = name
        }
        if (completed != null) {
            todo.completed = completed
        }
        if (description != null) {

            todo.description = description
        }
        return todoItemRepository.save(todo)
    }
    fun delete(id: String): Boolean {
        return if (todoItemRepository.existsById(UUID.fromString(id))) {
            todoItemRepository.deleteById(UUID.fromString(id))
            true
        } else {
            false
        }
    }
}

@Component
class DataInitializer {
    @Bean
    fun init(
        authorRepository: AuthorRepository,
        bookRepository: BookRepository,
        todoItemRepository: TodoItemRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            val todo1 = TodoItem(
                "Sample Todo 1",
                "Description for sample todo 1",
                false
            )
            val todo2 =
                TodoItem( "Sample Todo 2", "Description for sample todo 2", true)
            todoItemRepository.save(todo1)
            todoItemRepository.save(todo2)
        }
    }
}