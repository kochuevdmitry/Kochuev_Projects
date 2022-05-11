package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.JwtBlacklistEntity;
import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.data.user.UserEntityChangeVerification;
import com.example.MyBookShopApp.errs.PasswordChangeNotMatchException;
import com.example.MyBookShopApp.repositories.BalanceTransactionEntityRepository;
import com.example.MyBookShopApp.repositories.BookstoreUserRepository;
import com.example.MyBookShopApp.repositories.JwtBlacklistRepository;
import com.example.MyBookShopApp.repositories.UserEntityChangeVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Service
public class ProfileService {

    private final BookstoreUserRegisterService userRegister;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private UserEntityChangeVerificationRepository userEntityChangeVerificationRepository;
    private BalanceTransactionEntityRepository balanceTransactionEntityRepository;
    private BookstoreUserRepository bookstoreUserRepository;
    private JwtBlacklistRepository jwtBlacklistRepository;
    @Value("${appEmail.email}")
    private String emailFrom;

    @Autowired
    public ProfileService(UserEntityChangeVerificationRepository userEntityChangeVerificationRepository, BalanceTransactionEntityRepository balanceTransactionEntityRepository, BookstoreUserRepository bookstoreUserRepository, JwtBlacklistRepository jwtBlacklistRepository, BookstoreUserRegisterService userRegister, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.userEntityChangeVerificationRepository = userEntityChangeVerificationRepository;
        this.balanceTransactionEntityRepository = balanceTransactionEntityRepository;
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.jwtBlacklistRepository = jwtBlacklistRepository;
        this.userRegister = userRegister;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    public UserEntityChangeVerification getUserEntityChangeVerificationByToken(String token) {
        return userEntityChangeVerificationRepository.findUserEntityChangeVerificationByToken(token);
    }

    public void payment(UserEntity user, Double amount, String reason) {
        BalanceTransactionEntity balanceTransactionEntity = new BalanceTransactionEntity();
        balanceTransactionEntity.setBookId(0);
        balanceTransactionEntity.setDescription(reason);
        balanceTransactionEntity.setUserId(user.getId());
        balanceTransactionEntity.setValue(amount);
        balanceTransactionEntity.setTime(LocalDateTime.now());

        balanceTransactionEntityRepository.save(balanceTransactionEntity);
        Double balance = user.getBalance() + amount;
        user.setBalance(balance);
        bookstoreUserRepository.save(user);
    }

    public void logoutProcessing(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {

                    token = cookie.getValue();
                    JwtBlacklistEntity jwtBlacklistEntity = new JwtBlacklistEntity();
                    jwtBlacklistEntity.setJwtBlacklisted(token);
                    jwtBlacklistEntity.setRevocationDate(LocalDateTime.now());
                    jwtBlacklistRepository.save(jwtBlacklistEntity);

                    cookie.setMaxAge(0);
                    response.addCookie(cookie);

                    HttpSession session = request.getSession();
                    SecurityContextHolder.clearContext();
                    if (session != null) {
                        session.invalidate();
                    }
                }
            }
        }

        request.logout();
    }

    public Boolean changeProfile(String name,
                                 String email,
                                 String phone,
                                 String password1,
                                 String password2) throws Exception {
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        Boolean needChange = false;
        if (password1.equals(password2)) {
            if (!user.getName().equals(name)
                    || !user.getEmail().equals(email)
                    || !user.getPhone().equals(phone)
                    || !password1.equals("")) {
                needChange = true;
            }
        } else {
            throw new PasswordChangeNotMatchException("Пароли не совпадают!");
        }

        if (needChange) {
            if (password1.equals("")) {
                password1 = user.getPassword();
            } else {
                password1 = passwordEncoder.encode(password1);
            }

            profileChangeLinkSend(name, email, phone, password1, user.getId());
        }
        return needChange;
    }

    private void profileChangeLinkSend(String name,
                                       String email,
                                       String phone,
                                       String password1,
                                       Integer userId) {
        UserEntityChangeVerification userEntityChangeVerification
                = new UserEntityChangeVerification(300);
        userEntityChangeVerification.setName(name);
        userEntityChangeVerification.setEmail(email);
        userEntityChangeVerification.setPhone(phone);
        userEntityChangeVerification.setPassword(password1);
        userEntityChangeVerification.setTokenTime(LocalDateTime.now());
        userEntityChangeVerification.setUserId(userId);

        String token = "profile_change_hash_" + (name + email + phone + password1 + LocalDateTime.now()).hashCode();
        userEntityChangeVerification.setToken(token);
        userEntityChangeVerificationRepository.save(userEntityChangeVerification);

        confirmationProfileChangeLinkSendToEmail(email, token);
    }

    private void confirmationProfileChangeLinkSendToEmail(String emailTo, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(emailTo);

        String confirmationLink = "http://localhost:8085/prof/update?token=" + token;

        message.setSubject("Bookstore profile update confirmation!");
        message.setText("Please follow this link to confirm your recent changes: " + confirmationLink +
                "\nTHis link is active for 5 minutes. Do not follow the link if you do not want to change anything in you Bookshop account.");
        javaMailSender.send(message);
    }

    public Boolean confirmProfileChange(String token) {
        UserEntityChangeVerification userChange
                = userEntityChangeVerificationRepository.findUserEntityChangeVerificationByToken(token);
        if (!userChange.isExpired()) {
            UserEntity user = bookstoreUserRepository.findUserEntityById(userChange.getUserId());
            user.setName(userChange.getName());
            user.setEmail(userChange.getEmail());
            user.setPhone(userChange.getPhone());
            user.setPassword(userChange.getPassword());

            bookstoreUserRepository.save(user);
            return true;
        }
        return false;

    }
}
