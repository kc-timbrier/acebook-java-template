import com.github.javafaker.Faker;
import com.makersacademy.acebook.Application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
public class SignUpTest {

    WebDriver driver;
    Faker faker;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        faker = new Faker();
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void successfulSignUpRedirectsToSignIn() {
        driver.get("http://localhost:8080/users/new");
        driver.findElement(By.id("username")).sendKeys(faker.name().firstName());
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("submit")).click();
        String title = driver.getTitle();
        assertThat(title).isEqualTo("Please sign in");
    }
}
