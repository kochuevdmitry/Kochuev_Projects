package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.RegistrationForm;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.BookstoreUserRepository;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookstoreUserRegisterTest {

    private final BookstoreUserRegisterService userRegister;
    private final PasswordEncoder passwordEncoder;
    private RegistrationForm registrationForm;

    @MockBean
    private BookstoreUserRepository bookstoreUserRepositoryMock;// В тесте регистрации юзера мы создаем мок юзерРепозиторий, чтобы тестовый юзер не сохранялся в базе

    @Autowired
    public BookstoreUserRegisterTest(BookstoreUserRegisterService userRegister, PasswordEncoder passwordEncoder) {
        this.userRegister = userRegister;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
        registrationForm = new RegistrationForm();
        registrationForm.setEmail("test@mail.ru");
        registrationForm.setName("Tester");
        registrationForm.setPass("iddqd");
        registrationForm.setPhone("90312312323");
    }

    @AfterEach
    void tearDown() {
        registrationForm = null;
    }

    @Test
    void registerNewUser() {
        UserEntity user = userRegister.registerNewUser(registrationForm);
        assertNotNull(user);
        assertTrue(passwordEncoder.matches(registrationForm.getPass(), user.getPassword()));
        assertTrue(CoreMatchers.is(user.getPhone()).matches(registrationForm.getPhone()));
        assertTrue(CoreMatchers.is(user.getName()).matches(registrationForm.getName()));
        assertTrue(CoreMatchers.is(user.getEmail()).matches(registrationForm.getEmail()));

        Mockito.verify(bookstoreUserRepositoryMock, Mockito.times(1))
                .save(Mockito.any(UserEntity.class)); //проверка условия, что сохранение вызывалось 1 раз с объектом UserEntity
    }


    @Test
    void registerNewUserFail(){
        Mockito.doReturn(new UserEntity())
                .when(bookstoreUserRepositoryMock)
                .findUserEntityByEmail(registrationForm.getEmail());

        UserEntity user = userRegister.registerNewUser(registrationForm);
        assertNull(user);
    }


}