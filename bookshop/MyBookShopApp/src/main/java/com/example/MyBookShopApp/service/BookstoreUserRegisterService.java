package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.BookstoreUserDetails;
import com.example.MyBookShopApp.data.ContactConfirmationPayload;
import com.example.MyBookShopApp.data.ContactConfirmationResponse;
import com.example.MyBookShopApp.data.RegistrationForm;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.BookstoreUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class BookstoreUserRegisterService {

    private final BookstoreUserRepository bookstoreUserRepository;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtilService jwtUtilService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public BookstoreUserRegisterService(BookstoreUserRepository bookstoreUserRepository, BookstoreUserDetailsService bookstoreUserDetailsService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTUtilService jwtUtilService, OAuth2AuthorizedClientService authorizedClientService) {
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtilService = jwtUtilService;
        this.authorizedClientService = authorizedClientService;
    }

    public UserEntity registerNewUser(RegistrationForm registrationForm) {
        UserEntity userbyEmail = null;
        UserEntity userbyPhone = null;
        if (registrationForm.getEmail() != null) {
            userbyEmail = bookstoreUserRepository.findUserEntityByEmail(registrationForm.getEmail());
        }
        if (registrationForm.getPhone() != null) {
            userbyPhone = bookstoreUserRepository.findUserEntityByPhone(registrationForm.getPhone());
        }

        if (userbyEmail == null && userbyPhone == null) {
            UserEntity user = new UserEntity();
            user.setBalance(0.0);
            user.setHash(createHash(registrationForm.getName(), registrationForm.getEmail()));
            user.setRegTime(LocalDateTime.now());
            user.setName(registrationForm.getName());
            user.setEmail(registrationForm.getEmail());
            user.setPhone(registrationForm.getPhone());
            user.setPassword(passwordEncoder.encode(registrationForm.getPass()));
            bookstoreUserRepository.save(user);
            return user;
        }
        return null;
    }

    private String createHash(String name, String email) {
        String hash = "user-";
        String check = name + email + LocalDateTime.now();
        hash = hash + check.hashCode();
        return hash;
    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                        payload.getCode()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());

        String jwtToken = jwtUtilService.generateToken(userDetails);

        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public ContactConfirmationResponse jwtLoginByPhoneNumber(ContactConfirmationPayload payload) {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setPhone(payload.getContact());
        registrationForm.setPass(payload.getCode());
        registerNewUser(registrationForm);
        UserDetails userDetails = bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtilService.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public Object getCurrentUser() {
        Object authenticationCheck = SecurityContextHolder.getContext().getAuthentication();

        if (authenticationCheck.toString().contains("org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken")) {
            OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) authenticationCheck;
            if (authentication.getPrincipal() != null) {
                return getCurrentUserOauth(authentication);
            }
        }

        try {
            BookstoreUserDetails userDetails =
                    (BookstoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getBookstoreUser();
        } catch (Exception e) {
            return null;
        }
    }

    public Object getCurrentUserOauth(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();
        UserEntity userEntity = new UserEntity();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());
            HttpEntity entity = new HttpEntity("", headers);
            ResponseEntity<Map> response = restTemplate
                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            try {


                Map userAttributes = response.getBody();

                if (bookstoreUserRepository.findUserEntityByEmail(userAttributes.get("email").toString()) == null) {
                    userEntity.setBalance(0.0);
                    userEntity.setHash(createHash(userAttributes.get("name").toString(), userAttributes.get("email").toString()));
                    userEntity.setRegTime(LocalDateTime.now());
                    userEntity.setName(userAttributes.get("name").toString());
                    userEntity.setEmail(userAttributes.get("email").toString());
                    if (userAttributes.get("phone") != null) {
                        userEntity.setPhone(userAttributes.get("phone").toString());
                    } else {
                        userEntity.setPhone("");
                    }
                    bookstoreUserDetailsService.save(userEntity);
                } else {
                    userEntity = bookstoreUserRepository.findUserEntityByEmail(userAttributes.get("email").toString());
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                return null;
            }
        }
        BookstoreUserDetails userDetails = new BookstoreUserDetails(userEntity);
        return userDetails.getBookstoreUser();
    }
}
