package com.proyecto_integrador.services

import com.proyecto_integrador.exceptions.*
import com.proyecto_integrador.mappers.UserMapper
import com.proyecto_integrador.models.entities.UserEntity
import com.proyecto_integrador.models.requests.CreateUserRequest
import com.proyecto_integrador.models.requests.UpdateUserRequest
import com.proyecto_integrador.repositories.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class UserServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var userService: UserService
    private val mapper = UserMapper()

    @BeforeEach
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        userService = UserService(userRepository, mapper)
    }

    @Test
    fun `findAll should return list of users`() {
        val users = listOf(
            UserEntity(
                username = "juan",
                password = "1234",
                email = "juan@test.com",
                firstName = "Juanito",
                lastName = "Torres",
                isSuperuser = true
            ).apply { id = 1L },
            UserEntity(
                username = "maria",
                password = "abcd",
                email = "maria@test.com",
                firstName = "Maria",
                lastName = "Lopez",
                isSuperuser = false
            ).apply { id = 2L }
        )

        `when`(userRepository.findAll()).thenReturn(users)

        val result = userService.findAll()

        assertEquals(2, result.size)
        assertEquals("juan", result[0].username)
        assertEquals("maria", result[1].username)
    }

    @Test
    fun `findById should return user when exists`() {
        val user = UserEntity(
            username = "juan",
            password = "1234",
            email = "juan@test.com",
            firstName = "Juanito",
            lastName = "Torres",
            isSuperuser = true
        ).apply { id = 1L }

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(user))

        val result = userService.findById(1L)

        assertEquals("juan", result.username)
        assertEquals("juan@test.com", result.email)
    }

    @Test
    fun `findById should throw UserNotFoundException when not exists`() {
        `when`(userRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(UserNotFoundException::class.java) {
            userService.findById(99L)
        }
    }

    @Test
    fun `save should throw UsernameAlreadyExistsException when username exists`() {
        val request = CreateUserRequest(
            username = "juan",
            password = "1234",
            email = "juan@test.com",
            firstName = "Juanito",
            lastName = "Torres"
        )

        `when`(userRepository.findByUsername("juan"))
            .thenReturn(Optional.of(UserEntity(
                username = "juan",
                password = "1234",
                email = "juan@test.com",
                firstName = "Juanito",
                lastName = "Torres",
                isSuperuser = false
            )))

        assertThrows(UsernameAlreadyExistsException::class.java) {
            userService.save(request)
        }
    }

    @Test
    fun `save should throw EmailAlreadyExistsException when email exists`() {
        val request = CreateUserRequest(
            username = "juan",
            password = "1234",
            email = "juan@test.com",
            firstName = "Juanito",
            lastName = "Torres"
        )

        `when`(userRepository.findByUsername("juan")).thenReturn(Optional.empty())
        `when`(userRepository.findByEmail("juan@test.com"))
            .thenReturn(Optional.of(UserEntity(
                username = "otro",
                password = "xxxx",
                email = "juan@test.com",
                firstName = "Otro",
                lastName = "User",
                isSuperuser = false
            )))

        assertThrows(EmailAlreadyExistsException::class.java) {
            userService.save(request)
        }
    }

    @Test
    fun `save should throw IllegalArgumentException when username is blank`() {
        val request = CreateUserRequest(
            username = "",
            password = "1234",
            email = "juan@test.com",
            firstName = "Juanito",
            lastName = "Torres"
        )

        `when`(userRepository.findByUsername("")).thenReturn(Optional.empty())
        `when`(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.empty())

        assertThrows(IllegalArgumentException::class.java) {
            userService.save(request)
        }
    }

    @Test
    fun `save should throw IllegalArgumentException when password is blank`() {
        val request = CreateUserRequest(
            username = "juan",
            password = "",
            email = "juan@test.com",
            firstName = "Juanito",
            lastName = "Torres"
        )

        `when`(userRepository.findByUsername("juan")).thenReturn(Optional.empty())
        `when`(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.empty())

        assertThrows(IllegalArgumentException::class.java) {
            userService.save(request)
        }
    }

    @Test
    fun `save should persist and return user`() {
        val request = CreateUserRequest(
            username = "juan",
            password = "1234",
            email = "juan@test.com",
            firstName = "Juanito",
            lastName = "Torres"
        )
        val savedEntity = UserEntity(
            username = "juan",
            password = "1234",
            email = "juan@test.com",
            firstName = "Juanito",
            lastName = "Torres",
            isSuperuser = false
        ).apply { id = 1L }

        `when`(userRepository.findByUsername("juan")).thenReturn(Optional.empty())
        `when`(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.empty())
        `when`(userRepository.save(any(UserEntity::class.java))).thenReturn(savedEntity)

        val result = userService.save(request)

        assertEquals(1L, result.id)
        assertEquals("juan", result.username)
    }

    @Test
    fun `update should throw UserNotFoundException when user not exists`() {
        val request = UpdateUserRequest(
            username = "nuevo",
            email = "nuevo@test.com",
            password = "abcd",
            isSuperuser = true
        )

        `when`(userRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(UserNotFoundException::class.java) {
            userService.update(99L, request)
        }
    }

    @Test
    fun `update should throw IllegalArgumentException when email is blank`() {
        val existing = UserEntity(
            username = "juan",
            password = "1234",
            email = "juan@test.com",
            firstName = "Juanito",
            lastName = "Torres",
            isSuperuser = false
        ).apply { id = 1L }

        val request = UpdateUserRequest(
            username = "juan",
            email = "",
            password = "1234",
            isSuperuser = true
        )

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(existing))

        assertThrows(IllegalArgumentException::class.java) {
            userService.update(1L, request)
        }
    }

    @Test
    fun `update should persist and return updated user`() {
        val existing = UserEntity(
            username = "juan",
            password = "1234",
            email = "juan@test.com",
            firstName = "Juanito",
            lastName = "Torres",
            isSuperuser = false
        ).apply { id = 1L }

        val request = UpdateUserRequest(
            username = "nuevo",
            email = "nuevo@test.com",
            password = "abcd",
            isSuperuser = true
        )

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(existing))
        `when`(userRepository.save(any(UserEntity::class.java)))
            .thenReturn(existing.apply { username = "nuevo"; email = "nuevo@test.com" })

        val result = userService.update(1L, request)

        assertEquals("nuevo", result.username)
        assertEquals("nuevo@test.com", result.email)
    }

    @Test
    fun `delete should throw UserNotFoundException when not exists`() {
        `when`(userRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(UserNotFoundException::class.java) {
            userService.delete(99L)
        }
    }

    @Test
    fun `delete should throw IllegalStateException when repository fails`() {
        val user = UserEntity(
            username = "juan",
            password = "1234",
            email = "juan@test.com",
            firstName = "Juanito",
            lastName = "Torres",
            isSuperuser = false
        ).apply { id = 1L }

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(user))
        doThrow(RuntimeException("DB error")).`when`(userRepository).delete(user)

        assertThrows(IllegalStateException::class.java) {
            userService.delete(1L)
        }
    }

    @Test
    fun `delete should remove user when exists`() {
        val user = UserEntity(
            username = "juan",
            password = "1234",
            email = "juan@test.com",
            firstName = "Juanito",
            lastName = "Torres",
            isSuperuser = false
        ).apply { id = 1L }

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(user))

        userService.delete(1L)

        verify(userRepository, times(1)).delete(user)
    }
}
