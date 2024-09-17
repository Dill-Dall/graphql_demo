package no.dilldall.graphql_demo

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.InjectMocks
import org.mockito.Mock
import java.util.*

class BookServiceTest {

    @Mock
    private lateinit var bookRepository: BookRepository

    @InjectMocks
    private lateinit var bookService: BookService

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test getById returns book`() {
        // Arrange
        val id = UUID.randomUUID().toString()
        val book = Book(UUID.fromString(id), "Test Book", 200, UUID.randomUUID().toString())
        `when`(bookRepository.findById(id)).thenReturn(Optional.of(book))

        // Act
        val result = bookService.getById(id)

        // Assert
        assertNotNull(result)
        assertEquals(book, result)
    }

    @Test
    fun `test getById returns null when not found`() {
        // Arrange
        val id = UUID.randomUUID().toString()
        `when`(bookRepository.findById(id)).thenReturn(Optional.empty())

        // Act
        val result = bookService.getById(id)

        // Assert
        assertNull(result)
    }
}

class AuthorServiceTest {

    @Mock
    private lateinit var authorRepository: AuthorRepository

    @InjectMocks
    private lateinit var authorService: AuthorService

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test getById returns author`() {
        // Arrange
        val id = UUID.randomUUID().toString()
        val author = Author(UUID.fromString(id), "Jane", "Doe", "Norway")
        `when`(authorRepository.findById(id)).thenReturn(Optional.of(author))

        // Act
        val result = authorService.getById(id)

        // Assert
        assertNotNull(result)
        assertEquals(author, result)
    }

    @Test
    fun `test getById returns null when not found`() {
        // Arrange
        val id = UUID.randomUUID().toString()
        `when`(authorRepository.findById(id)).thenReturn(Optional.empty())

        // Act
        val result = authorService.getById(id)

        // Assert
        assertNull(result)
    }
}

class TodoItemServiceTest {

    @Mock
    private lateinit var todoItemRepository: TodoItemRepository

    @InjectMocks
    private lateinit var todoItemService: TodoItemService

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test getAll returns list of todo items`() {
        // Arrange
        val todoList = listOf(
            TodoItem(UUID.randomUUID(), "Todo 1", "Desc 1", false),
            TodoItem(UUID.randomUUID(), "Todo 2", "Desc 2", true)
        )
        `when`(todoItemRepository.findAll()).thenReturn(todoList)

        // Act
        val result = todoItemService.getAll()

        // Assert
        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals(todoList, result)
    }

    @Test
    fun `test getById returns todo item`() {
        // Arrange
        val id = UUID.randomUUID().toString()
        val todoItem = TodoItem(UUID.fromString(id), "Todo", "Desc", false)
        `when`(todoItemRepository.findById(UUID.fromString(id))).thenReturn(Optional.of(todoItem))

        // Act
        val result = todoItemService.getById(id)

        // Assert
        assertNotNull(result)
        assertEquals(todoItem, result)
    }

    @Test
    fun `test add saves and returns todo item`() {
        // Arrange
        val name = "New Todo"
        val description = "New Desc"
        val todoItem = TodoItem(UUID.randomUUID(), name, description, false)
        `when`(todoItemRepository.save(any(TodoItem::class.java))).thenReturn(todoItem)

        // Act
        val result = todoItemService.add(name, description)

        // Assert
        assertNotNull(result)
        assertEquals(todoItem, result)
    }

    @Test
    fun `test update updates and returns todo item`() {
        // Arrange
        val id = UUID.randomUUID().toString()
        val existingTodo = TodoItem(UUID.fromString(id), "Old Name", "Old Desc", false)
        val updatedTodo = existingTodo.copy(name = "New Name", description = "New Desc", completed = true)
        `when`(todoItemRepository.findById(UUID.fromString(id))).thenReturn(Optional.of(existingTodo))
        `when`(todoItemRepository.save(existingTodo)).thenReturn(updatedTodo)

        // Act
        val result = todoItemService.update(id, "New Name", "New Desc", true)

        // Assert
        assertNotNull(result)
        assertEquals(updatedTodo, result)
        assertEquals("New Name", result?.name)
        assertEquals("New Desc", result?.description)
        assertTrue(result?.completed ?: false)
    }

    @Test
    fun `test delete removes todo item`() {
        // Arrange
        val id = UUID.randomUUID().toString()
        `when`(todoItemRepository.existsById(UUID.fromString(id))).thenReturn(true)
        doNothing().`when`(todoItemRepository).deleteById(UUID.fromString(id))

        // Act
        val result = todoItemService.delete(id)

        // Assert
        assertTrue(result)
        verify(todoItemRepository, times(1)).deleteById(UUID.fromString(id))
    }

    @Test
    fun `test delete returns false when item does not exist`() {
        // Arrange
        val id = UUID.randomUUID().toString()
        `when`(todoItemRepository.existsById(UUID.fromString(id))).thenReturn(false)

        // Act
        val result = todoItemService.delete(id)

        // Assert
        assertFalse(result)
        verify(todoItemRepository, never()).deleteById(any())
    }
}
