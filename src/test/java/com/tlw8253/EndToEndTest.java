package com.tlw8253;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.tlw8253.javalin.JavalinHelper;

import static org.assertj.core.api.Assertions.assertThat;


/*
 * This class was taken by following instruction on the Javalin page on test tutorial https://javalin.io/tutorials/testing
 * 	Code example taken from GIT Clone of https://github.com/tipsy/javalin-testing-example.git
 * 	Source code from example are modified for this project
 */
public class EndToEndTest {

    private JavalinHelper app = new JavalinHelper(); // inject any dependencies you might have

    @Test
    public void UI_contains_correct_heading() {
        app.start(1234);
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver(options);
        driver.get("http://localhost:1234/ui");
        assertThat(driver.getPageSource()).contains("<h1>User UI</h1>");
        driver.quit();
        app.stop();
    }

}
