package com.example.MyBookShopApp.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainPage {

    private String url = "http://localhost:8085/";
    private ChromeDriver driver;

    public MainPage(ChromeDriver driver) {
        this.driver = driver;
    }

    public MainPage callPage() {
        driver.get(url);
        return this;
    }

    public MainPage pause() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public MainPage setUpSearchToken(String token) {
        WebElement element = driver.findElement(By.id("query"));//находим элемент на страничке по id
        element.sendKeys(token);//в найденный элемент отправляем значение, т.е. в поисковую строку заносим строку
        return this;
    }

    public MainPage submit() {
        WebElement element = driver.findElement(By.id("search"));//находим кнопку искать
        element.submit();//нажимаем кнопку искать
        return this;
    }
}
