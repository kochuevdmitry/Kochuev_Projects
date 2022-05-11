package com.example.MyBookShopApp.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class NavigationSeleniumTests {

    private static ChromeDriver driver;

    @BeforeAll
    static void setup(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\kochuev.dmitry\\OneDrive\\tmp\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);//сколько по времени ждать запуска странички, иначе вылетит ошибка
    }

    @AfterAll
    static void tearDown(){
        driver.quit();
    }

    @Test
    public void NavigationTest(){
        NavigationPages navigationPages = new NavigationPages(driver);

        //main page
        navigationPages
                .callPage("http://localhost:8085/")
                .pause();
        assertTrue(driver.getPageSource().contains("BOOKSHOP"));

        //ganres
        navigationPages
                .callPage("http://localhost:8085/genres")
                .pause();
        assertTrue(driver.getPageSource().contains("superstructure"));

        //search check
       navigationPages
                .callPage("http://localhost:8085/books/popul")
                .pause()
                .setUpKey("down", "query")
                .pause()
                .submit("search")
                .pause();
        assertTrue(driver.getPageSource().contains("Final Countdown, The"));

    }

    @Test
    public void NavigationRecentTest(){
        NavigationPages navigationPages = new NavigationPages(driver);

        //recent books
        navigationPages
                .callPage("http://localhost:8085/books/new")
                .pause()
                .setUpKey("01.01.2021", "fromdaterecent")
                //.setUpKey("01.01.2022", "enddaterecent")
                .click("enddaterecent")
                .pause();
        assertTrue(driver.getPageSource().contains("Blue Like Jazz"));

    }

    @Test
    public void NavigationSigningMailTest(){
        NavigationPages navigationPages = new NavigationPages(driver);

        //recent books
        navigationPages
                .callPage("http://localhost:8085/signin")
                .pause()
                .clickByXpath("/html/body/div/div[2]/main/form/div/div[1]/div[2]/div/div[2]/label/span[2]")
                .pause()
                .setUpKey("adimonk@yandex.ru", "mail")
                .pause()
                .click("sendauth")
                .pause()
                .setUpKey("111","mailcode")
                .pause()
                .click("toComeInMail")
                .pause();

        assertTrue(driver.getPageSource().contains("Мои книги"));

        navigationPages
                .clickByXpath("/html/body/header/div/div[1]/div/div[3]/div/a[5]")
                .pause();

        assertTrue(driver.getPageSource().contains("Зарегистрироваться"));

    }


}