package no.dilldall.graphql_demo

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.InjectMocks
import org.mockito.Mock
import java.util.*

class BookControllerTest {

    @Mock
    private lateinit var bookService: BookService

    @Mock
    private lateinit var authorService: AuthorService

    @InjectMocks
    private lateinit var bookController: BookController

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test bookById returns book`() {
        // Arrange
        val bookId = UUID.randomUUID().toString()
        val book = Book(
            id = UUID.fromString(bookId),
            name = "Test Book",
            pageCount = 100,
            authorId = UUID.randomUUID().toString()
        )
        `when`(bookService.getById(bookId)).thenReturn(book)

        // Act
        val result = bookController.bookById(bookId)

        // Assert
        assertNotNull(result)
        assertEquals(book, result)
    }

    @Test
    fun `test author returns author`() {
        // Arrange
        val authorId = UUID.randomUUID().toString()
        val author = Author(
            id = UUID.fromString(authorId),
            firstName = "John",
            lastName = "Doe",
            country = "Norway"
        )
        val book = Book(
            id = UUID.randomUUID(),
            name = "Test Book",
            pageCount = 100,
            authorId = authorId
        )
        `when`(authorService.getById(authorId)).thenReturn(author)

        // Act
        val result = bookController.author(book)

        // Assert
        assertNotNull(result)
        assertEquals(author, result)
    }
}

class TodoControllerTest {

    @Mock
    private lateinit var todoItemService: TodoItemService

    @InjectMocks
    private lateinit var todoController: TodoController

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test todos returns list`() {
        // Arrange
        val todoList = listOf(
            TodoItem(UUID.randomUUID(), "Todo 1", "Desc 1", false),
            TodoItem(UUID.randomUUID(), "Todo 2", "Desc 2", true)
        )
        `when`(todoItemService.getAll()).thenReturn(todoList)

        // Act
        val result = todoController.todos()

        // Assert
        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals(todoList, result)
    }

    @Test
    fun `test addTodoItem adds and returns item`() {
        // Arrange
        val name = "New Todo"
        val description = "New Description"
        val todoItem = TodoItem(UUID.randomUUID(), name, description, false)
        `when`(todoItemService.add(name, description)).thenReturn(todoItem)

        // Act
        val result = todoController.addTodoItem(name, description)

        // Assert
        assertNotNull(result)
        assertEquals(todoItem, result)
    }

    @Test
    fun `test updateTodoItem updates and returns item`() {
        // Arrange
        val id = UUID.randomUUID().toString()
        val name = "Updated Todo"
        val description = "Updated Description"
        val completed = true
        val updatedTodoItem = TodoItem(UUID.fromString(id), name, description, completed)
        `when`(todoItemService.update(id, name, description, completed)).thenReturn(updatedTodoItem)

        // Act
        val result = todoController.updateTodoItem(id, name, description, completed)

        // Assert
        assertNotNull(result)
        assertEquals(updatedTodoItem, result)
    }

    @Test
    fun `test deleteTodoItem deletes item`() {
        // Arrange
        val id = UUID.randomUUID().toString()
        `when`(todoItemService.delete(id)).thenReturn(true)

        // Act
        val result = todoController.deleteTodoItem(id)

        // Assert
        assertTrue(result)
    }
}
