package com.example.MyBookShopApp.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class NavigationPages {

    private String urlInit = "http://localhost:8085/";
    private ChromeDriver driver;

    public NavigationPages(ChromeDriver driver) {
        this.driver = driver;
    }

    public NavigationPages callPage(String url) {
        driver.get(url);
        return this;
    }

    public NavigationPages pause() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public NavigationPages pause(Integer pause) {
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public NavigationPages setUpSearchToken(String token) {
        WebElement element = driver.findElement(By.id("query"));//находим элемент на страничке по id
        element.sendKeys(token);//в найденный элемент отправляем значение, т.е. в поисковую строку заносим строку
        return this;
    }

    public NavigationPages setUpKey(String token, String idElement) {
        WebElement element = driver.findElement(By.id(idElement));//находим элемент на страничке по id
        element.clear();

        element.sendKeys(token);//в найденный элемент отправляем значение, т.е. в поисковую строку заносим строку
        return this;
    }

    public NavigationPages setUpKeyByName(String token, String name) {
        WebElement element = driver.findElement(By.name(name));//находим элемент на страничке по id
        element.sendKeys(token);//в найденный элемент отправляем значение, т.е. в поисковую строку заносим строку
        return this;
    }

    public NavigationPages submit(String idElement) {
        WebElement element = driver.findElement(By.id(idElement));//находим кнопку искать
        element.submit();//нажимаем кнопку искать
        return this;
    }

    public NavigationPages click(String idElement) {
        WebElement element = driver.findElement(By.id(idElement));//находим кнопку искать
        element.click();//нажимаем кнопку искать
        return this;
    }

    public NavigationPages clickByXpath(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));//находим кнопку искать
        element.click();//нажимаем кнопку искать
        return this;
    }
}
