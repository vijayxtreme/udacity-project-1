package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private static Helper helper;
	@Autowired
	private CredentialService credentialService;

	private Boolean signedUp;

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

	@Test
	@Order(2)
	public void signUpAtLeastOnce(){
		String username = "supermario";
		String password = "mushroom";
		String firstName = "Mario";
		String lastName = "Mario";

		driver.get(baseUrl + "/signup");

		SignupPage signupPage = new SignupPage(this.driver);
		signupPage.signup(firstName, lastName, username, password);

	}

	//can signup, login, view home page, logout, Part 1
	@Test
	@Order(3)
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
	@Order(4)
	public void createNote() throws InterruptedException {
		this.canLogin();
		String notetitle = "Hello World";
		String notedescription = "When you want to show that your code works, show Hello World";

		//In home page, go to Note page (tab)?
		NotePage notePage = new NotePage(driver);
		notePage.createNote(notetitle, notedescription);

		//verify created note is visible
		Assertions.assertTrue(notePage.verifyNoteDisplayed(notetitle, notedescription));

	}

	//Test note update
	@Test
	@Order(5)
	public void updateExistingNote() throws InterruptedException {
		this.canLogin();
		String notetitle = "Goodbye Everyone";
		String notedescription = "When you are planning to leave, be sure to tell everyone Goodbye.";

		NotePage notePage = new NotePage(driver);
		notePage.updateNote(notetitle, notedescription);

		//verify updated note is visible
		Assertions.assertTrue(notePage.verifyNoteDisplayed(notetitle, notedescription));
	}

	//Test delete note
	@Test
	@Order(6)
	public void deleteNote() throws InterruptedException {
		this.canLogin();
		NotePage notePage = new NotePage(driver);
		String notetitle = driver.findElement(By.className("note-title")).getText();
		String noteDescription = driver.findElement(By.className("note-description")).getText();
		notePage.deleteNote();

		//verify note is deleted - driver can't find
		Assertions.assertFalse(notePage.verifyNoteDisplayed(notetitle, noteDescription));
	}

	/***** CREDENTIAL CREATION, VIEWING, EDITING, DELETION *****/

	@Test
	@Order(7)
	public void createCredential() throws InterruptedException {
		this.canLogin();
		String url = "https://google.com";
		String username = "Googler";
		String password = "adsasd";

		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.createCredential(url, username, password);

		//Verify that the credentials for url and username are displayed
		Assertions.assertTrue(credentialPage.verifyCredentialDisplayed(url, username));


		/** -- Verify that the credential password is displayed, is encrypted and upon view in modal decrypted --*/

		//get the displayed encrypted password on page
		String foundEncryptedPassword = credentialPage.getCredentialEncryptedPassword();

		//get the displayed encrypted password's credential id
		WebElement editCredentialEl = driver.findElement(By.className("edit-credential"));
		String credentialid = editCredentialEl.getAttribute("data-credentialid");

		//search for the credential in the DB
		Credentials storedCredential = credentialService.getCredentialById(credentialid);
		String storedEncryptedPassword = storedCredential.getPassword(); //password is saved encrypted

		//assert foundEncryptedpassword is indeed the storedEncryptedPassword
		Assertions.assertEquals(foundEncryptedPassword, storedEncryptedPassword);
	}

	@Test
	@Order(8)
	public void updateCredential() throws InterruptedException {
		this.canLogin();
		String desiredUrl = "https://amazon.com";
		String desiredUsername = "Amazonian";
		String desiredPassword = "waeasd";

		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.openCredentialTab();

		//View existing credential (from previous test)'s fields are not ""
		WebElement editCredentialEl = driver.findElement(By.className("edit-credential"));
		WebElement currentUrl = driver.findElement(By.className("credential-url"));
		WebElement currentUsername = driver.findElement(By.className("credential-username"));
		WebElement currentPassword = driver.findElement(By.className("credential-password"));

		Thread.sleep(1000);

		Assertions.assertTrue(currentUrl.getText() !="");
		Assertions.assertTrue(currentUsername.getText() !="");
		Assertions.assertTrue(currentPassword.getText() !="");

		//search for the credential in the DB
		String credentialid = editCredentialEl.getAttribute("data-credentialid");


		//Open the modal to edit
		editCredentialEl.click();
		Thread.sleep(1000);
		WebElement passwordField = new WebDriverWait(driver, 5).until(
				driver -> driver.findElement(By.id("credential-password"))
		);
		Thread.sleep(1000);
		String foundDecryptedPassword = passwordField.getAttribute("value");

		//decrypt stored password
		EncryptionService encryptionService = new EncryptionService();
		Credentials storedCredential = credentialService.getCredentialById(credentialid);
		String key = storedCredential.getKey();
		String hashedPassword = storedCredential.getPassword();
		String decryptedPass = encryptionService.decryptValue(hashedPassword, key);

		//Check that the found decrypted password matches the stored decrypted password
		Assertions.assertEquals(foundDecryptedPassword, decryptedPass);

		//Update the credential
		credentialPage.updateCredential(desiredUrl, desiredUsername, desiredPassword);
		//after returning home
		credentialPage.openCredentialTab();

		//Verify that the changes are displayed
		WebElement updatedCredentialEl = driver.findElement(By.className("edit-credential"));
		WebElement updatedUrl = driver.findElement(By.className("credential-url"));
		WebElement updatedUsername = driver.findElement(By.className("credential-username"));
		WebElement updatedPassword = driver.findElement(By.className("credential-password"));
		Assertions.assertEquals(desiredUrl, updatedUrl.getText());
		Assertions.assertEquals(desiredUsername, updatedUsername.getText());
		String updatedCredentialid = updatedCredentialEl.getAttribute("data-credentialid");
		Credentials updatedStoredCredential = credentialService.getCredentialById(updatedCredentialid);

		String updatedKey = updatedStoredCredential.getKey();
		String updatedHashedPassword = updatedStoredCredential.getPassword();
		String updatedDecryptedPass = encryptionService.decryptValue(updatedHashedPassword, updatedKey);


		Assertions.assertEquals(desiredPassword, updatedDecryptedPass);

	}

	@Test
	@Order(9)
	public void deleteCredential() throws InterruptedException {
		this.canLogin();
		CredentialPage credentialPage = new CredentialPage(driver);
		String url = driver.findElement(By.className("credential-url")).getText();
		String username = driver.findElement(By.className(("credential-username"))).getText();

		credentialPage.deleteCredential();

		Assertions.assertFalse(credentialPage.verifyCredentialDisplayed(url, username));
	}


}
