package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.ContactConfirmationPayload;
import com.example.MyBookShopApp.data.ContactConfirmationResponse;
import com.example.MyBookShopApp.data.RegistrationForm;
import com.example.MyBookShopApp.data.SmsCode;
import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.errs.EmptyPaymentException;
import com.example.MyBookShopApp.repositories.BalanceTransactionEntityRepository;
import com.example.MyBookShopApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class AuthUserController extends CommonFooterAndHeaderController {

    private static final String REFERER = "referer";
    private static final String REDIRECTPROFILE = "redirect:/profile";
    private final SmsService smsService;
    private final JavaMailSender javaMailSender;
    private final BalanceTransactionEntityRepository balanceTransactionEntityRepository;
    private final ProfileService profileService;
    private final Logger logger = Logger.getLogger(AuthUserController.class.getName());

    @Value("${appEmail.email}")
    private String email;

    @Autowired
    public AuthUserController(BookService bookService, BookstoreUserRegisterService userRegister, SmsService smsService, JavaMailSender javaMailSender, BalanceTransactionEntityRepository balanceTransactionEntityRepository, ProfileService profileService, BookshpCartService bookshpCartService) {
        super(userRegister, bookshpCartService, bookService);
        this.smsService = smsService;
        this.javaMailSender = javaMailSender;
        this.balanceTransactionEntityRepository = balanceTransactionEntityRepository;
        this.profileService = profileService;
    }

    @GetMapping("/signin")
    public String handleSignin(Model model) {
        model.addAttribute("emptyUser", new RegistrationForm());
        return "signin";
    }

    @GetMapping("/signup")
    public String handleSignUp(Model model) {
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestContactConfirmation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        if (payload.getContact().contains("@")) {
            return response;//for email signing
        } else {
            String smsCodeString = smsService.sendSecretCodeSms(payload.getContact());
            smsService.saveNewCode(new SmsCode(smsCodeString, 180)); //expires in 180 seconds
            return response;
        }
    }

    @PostMapping("/requestEmailContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestEmailConfirmation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(payload.getContact());
        SmsCode smsCode = new SmsCode(smsService.generateCode(), 300); //5 minutes, похорошему сделать код для почты отдельно и отдельно его проверять, а то один и тот же код сработает
        smsService.saveNewCode(smsCode);
        message.setSubject("Bookstore email verification!");
        message.setText("Verification code is: " + smsCode.getCode());
        javaMailSender.send(message);
        response.setResult("true");
        return response;
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        if (smsService.verifyCode(payload.getCode())) {
            response.setResult("true");
        }
        return response;
    }

    @PostMapping("/reg")
    public String handleUserRegistration(RegistrationForm registrationForm, Model model) {
        userRegister.registerNewUser(registrationForm);
        model.addAttribute("regOk", true);
        logger.info("handleUserRegistration");
        return "signin";
    }

    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload,
                                                   HttpServletResponse httpServletResponse) {
        ContactConfirmationResponse loginResponse = userRegister.jwtLogin(payload);
        Cookie cookie = new Cookie("token", loginResponse.getResult());
        httpServletResponse.addCookie(cookie);
        logger.info("handleLogin");
        return loginResponse;
    }

    @PostMapping("/login-by-phone-number")
    @ResponseBody
    public ContactConfirmationResponse handleLoginByPhoneNumber(@RequestBody ContactConfirmationPayload payload,
                                                                HttpServletResponse httpServletResponse) {
        if (smsService.verifyCode(payload.getCode())) {
            ContactConfirmationResponse loginResponse = userRegister.jwtLoginByPhoneNumber(payload);
            Cookie cookie = new Cookie("token", loginResponse.getResult());
            httpServletResponse.addCookie(cookie);
            logger.info("handleLoginByPhoneNumber");
            return loginResponse;
        } else {
            return null;
        }
    }

    @GetMapping("/loginoauth")
    public String handleOauth(Model model, OAuth2AuthenticationToken authentication) throws Exception {
        if (authentication != null) {
            //userRegister.getCurrentUser();//.getCurrentUserOauth(authentication);
            logger.info("handleOauth ");
            return "redirect:/my";
        } else {
            return "redirect:/signin";
        }
    }

    @GetMapping("/my")
    public String handleMy(Model model) throws Exception {
        model.addAttribute("paidBooks", bookshpCartService.loadLinkedBooksForUser(3));
        return "my";
    }

    @GetMapping("/myarchive")
    public String handleMyArhive(Model model) throws Exception {
        model.addAttribute("archivedBooks", bookshpCartService.loadLinkedBooksForUser(4));
        return "myarchive";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model) throws Exception {
        logger.info("handleProfile");
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        model.addAttribute("curUsr", user);
        List<BalanceTransactionEntity> balanceTransactions = balanceTransactionEntityRepository.findAllByUserId(user.getId());
        model.addAttribute("transactions", balanceTransactions);
        return "profile";
    }

    @GetMapping("/logoutd")
    public String handleLogout(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getHeader(REFERER) != null) {
                profileService.logoutProcessing(request, response);
                return "redirect:" + request.getHeader(REFERER).substring(21);
            }

        } catch (Exception e) {
            return "redirect:/signin";
        }
        return "redirect:/signin";
    }

    @PostMapping(path = "/payment")
    public String handlePayment(@RequestParam("sum") @Valid @NotNull Double amount,
                                Model model, RedirectAttributes redirectAttributes) throws Exception {
        UserEntity user = new UserEntity();
        try {
            user = (UserEntity) userRegister.getCurrentUser();
            if (amount != null && amount > 0.0) {
                profileService.payment(user, amount, "Payment to account");
                logger.info("handlePayment done " + user.getEmail());
                redirectAttributes.addFlashAttribute("paid", true);
                redirectAttributes.addFlashAttribute("amount", amount);
                return REDIRECTPROFILE;
            }
        } catch (Exception e) {
            model.addAttribute("paid", false);
            model.addAttribute("curUsr", user);
            throw new EmptyPaymentException("Введите сумму платежа!");
        }
        return REDIRECTPROFILE;
    }

    @PostMapping(path = "/profile/save")
    public String handleChangeProfile(@RequestParam("userName") @Valid @NotNull String name,
                                      @RequestParam("userEmail") @Valid @NotNull String email,
                                      @RequestParam("userPhone") @Valid @NotNull String phone,
                                      @RequestParam("userPassword1") @Valid @NotNull String password1,
                                      @RequestParam("userPassword2") @Valid @NotNull String password2,
                                      RedirectAttributes redirectAttributes) {
        try {
            if (profileService.changeProfile(name, email, phone, password1, password2)) {
                logger.info("handleChangeProfile NEED CHANGE DONE");
                redirectAttributes.addFlashAttribute("dataUpdated", true);
                redirectAttributes.addFlashAttribute("dataUpdatedMessage", "Данные успешно обновлены.");
                return REDIRECTPROFILE;
            }
        } catch (Exception e){
            logger.info("handleChangeProfile - no change " + e.getMessage());
            logger.info("handleChangeProfile done " + name);
            redirectAttributes.addFlashAttribute("noChangesNeed", true);
            redirectAttributes.addFlashAttribute("dataUpdatedMessage", "Ошибка при изменении данных - " + e.getMessage() );
            return REDIRECTPROFILE;
        }
        logger.info("handleChangeProfile done " + name);
        redirectAttributes.addFlashAttribute("noChangesNeed", true);
        redirectAttributes.addFlashAttribute("dataUpdatedMessage", "Нет данных для изменения.");
        return REDIRECTPROFILE;
    }

    @GetMapping(path = "/prof/update")
    public String confirmProfileChange(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        if (profileService.confirmProfileChange(token)) {
            redirectAttributes.addFlashAttribute("profileChange", true);
            return REDIRECTPROFILE;
        }
        redirectAttributes.addFlashAttribute("profileChangeFalse", true);
        return REDIRECTPROFILE;
    }

}
