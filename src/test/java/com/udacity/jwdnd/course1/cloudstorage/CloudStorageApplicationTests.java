package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	public String baseUrl;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {

		this.driver = new ChromeDriver();
		baseUrl = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	//can't visit home page unless loggedin (if not authenticated)
	@Test
	public void unAuthenticatedIsRedirectedToLogin(){
		driver.get(baseUrl);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	//can signup, login, view home page, logout, Part 1
	@Test
	public void canSignUpLoginViewHomeLogout(){
		driver.get(baseUrl + "/signup");
		Random random = new Random();
		String randNum = String.valueOf(random.nextInt());

		String username = "supermario" + randNum;
		String password = "mushroom" + randNum;
		String firstName = "Mario" + randNum;
		String lastName = "Mario" + randNum;

		SignupPage signupPage = new SignupPage(driver);

		signupPage.signup(firstName, lastName, username, password);

		//assert that user success message shows on page, maybe wait
		WebElement successElement = new WebDriverWait(driver, 2).until(
				driver -> driver.findElement(By.id("success"))
		);

		Assertions.assertEquals("You successfully signed up!", successElement.getText());

		driver.get(baseUrl + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		//assert in home page
		Assertions.assertEquals("Home", driver.getTitle());

		//
		HomePage homepage = new HomePage(driver);
		homepage.logout();

		WebElement loggedoutElement = new WebDriverWait(driver, 2).until(
				driver -> driver.findElement(By.id("loggedout"))
		);


		//assert can logout
		Assertions.assertEquals("Login", driver.getTitle());
		Assertions.assertEquals("You have been logged out ;)", loggedoutElement.getText());
		System.out.println("All tests passed");

	}

	//test user shouldn't be able to sign up if username already in use
	@Test
	public void cantSignUpAgainWithSameName(){
		driver.get(baseUrl + "/signup");

		String username = "supermario";
		String password = "mushroom";
		String firstName = "Mario";
		String lastName = "Mario";
		SignupPage signupPage = new SignupPage(driver);

		signupPage.signup(firstName, lastName, username, password);

		//assert that user success message shows on page, maybe wait
		WebElement webElement = new WebDriverWait(driver, 2).until(
				ExpectedConditions.elementToBeClickable(By.id("error"))
		);

		Assertions.assertEquals("Can't sign up with that username. It may already be in use.", webElement.getText());
	}

	//test basic login - note supermario username needs to be in sys w/pass mushroom
	@Test
	public void canLogin() {
		String username = "supermario";
		String password = "mushroom";

		driver.get(baseUrl + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		Assertions.assertEquals("Home", driver.getTitle());
	}

	//Test note creation/CRUD
	@Test
	public void createNote(){
		this.canLogin();
		String notetitle = "Hello World";
		String notedescription = "When you want to show that your code works, show Hello World";

		//In home page, go to Note page (tab)?
		NotePage notePage = new NotePage(driver);
		notePage.createNote(notetitle, notedescription);
	}

	//Test credential creation/CRUD

}
