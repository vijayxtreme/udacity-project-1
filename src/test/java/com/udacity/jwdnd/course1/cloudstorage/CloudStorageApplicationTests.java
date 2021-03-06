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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	//basic login - note supermario username needs to be in sys w/pass mushroom

	private void canLogin() {
		String username = "supermario";
		String password = "mushroom";

		driver.get(baseUrl + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

	}


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

	/***** SIGNUP, LOGIN & UNAUTHORIZED RESTRICTIONS *****/

	//can't visit home page unless loggedin (if not authenticated)
	@Test
	@Order(1)
	public void unAuthenticatedIsRedirectedToLogin(){
		driver.get(baseUrl);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	//can signup, login, view home page, logout, Part 1
	@Test
	@Order(2)
	public void canSignUpLoginViewHomeLogout() throws InterruptedException {
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
		WebElement successElement = new WebDriverWait(driver, 10).until(
				driver -> driver.findElement(By.id("success"))
		);

		Assertions.assertEquals("You successfully signed up!", successElement.getText());

		driver.get(baseUrl + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		//assert in home page
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		Thread.sleep(1000);

		new WebDriverWait(driver, 5).until(driver -> driver.findElement(By.id("login-page")));
		Assertions.assertEquals("Login", driver.getTitle());
		Assertions.assertEquals("You have been logged out ;)", driver.findElement(By.id("loggedout")).getText());

	}


	/***** NOTE CREATION, VIEWING, EDITING, DELETION *****/

	//Test note creation
	@Test
	@Order(3)
	public void createNote() throws InterruptedException {
		this.canLogin();
		String notetitle = "Hello World";
		String notedescription = "When you want to show that your code works, show Hello World";

		//In home page, go to Note page (tab)?
		NotePage notePage = new NotePage(driver);
		notePage.createNote(notetitle, notedescription);
	}

	//Test note update
	@Test
	@Order(4)
	public void updateExistingNote() throws InterruptedException {
		this.canLogin();
		String notetitle = "Goodbye Everyone";
		String notedescription = "When you are planning to leave, be sure to tell everyone Goodbye.";

		NotePage notePage = new NotePage(driver);
		notePage.updateNote(notetitle, notedescription);

	}

	//Test delete note
	@Test
	@Order(5)
	public void deleteNote() throws InterruptedException {
		this.canLogin();
		NotePage notePage = new NotePage(driver);
		notePage.deleteNote();
	}

	/***** CREDENTIAL CREATION, VIEWING, EDITING, DELETION *****/

	@Test
	@Order(6)
	public void createCredential() throws InterruptedException {
		this.canLogin();
		String url = "https://google.com";
		String username = "Googler";
		String password = "adsasd";

		//In home page, go to Note page (tab)?
		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.createCredential(url, username, password);
	}

	@Test
	@Order(7)
	public void updateCredential() throws InterruptedException {
		this.canLogin();
		String url = "https://amazon.com";
		String username = "Amazonian";
		String password = "waeasd";

		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.updateCredential(url, username, password);
	}

	@Test
	@Order(8)
	public void deleteCredential() throws InterruptedException {
		this.canLogin();
		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.deleteCredential();
	}


}
