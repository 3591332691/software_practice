import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class login_steps {
    
    private  String password_input;
    
    @Given("I never login in as reader")
    public void iNeverLoginInAsReader() {
        
    }

    @When("I click the reader_login button")
    public void iClickTheReader_loginButton() {
        
    }

    @And("I authorized my wechat account")
    public void iAuthorizedMyWechatAccount() {
        
    }

    @Then("A new reader account is created successfully and author sql no change")
    public void aNewReaderAccountIsCreatedSuccessfullyAndAuthorSqlNoChange() {
        
    }

    @Given("I never login in as author")
    public void iNeverLoginInAsAuthor() {
    }

    @When("I click the author_login button")
    public void iClickTheAuthor_loginButton() {
        
    }

    @Then("A new author account is created successfully and reader sql no change")
    public void aNewAuthorAccountIsCreatedSuccessfullyAndReaderSqlNoChange() {
        
    }

    @Given("I've logged in as reader")
    public void iVeLoggedInAsReader() {
        
    }

    @Then("reader sql and author sql no change")
    public void readerSqlAndAuthorSqlNoChange() {
        
    }

    @Given("I've logged in as author")
    public void iVeLoggedInAsAuthor() {
        
    }

    @Given("I know the password")
    public void iKnowThePassword() {
        password_input = "123456";
    }

    @Given("I don't know the password")
    public void iDonTKnowThePassword() {
        password_input = "112233";
    }

    @When("I click the admin_login button")
    public void iClickTheAdmin_loginButton() {
        
    }

    @And("I enter the right password")
    public void iEnterTheRightPassword() {
        
    }

    @And("I enter the wrong password")
    public void iEnterTheWrongPassword() {
        
    }

    @Then("Login successfully")
    public void loginSuccessfully() {
        
    }

    @Then("Login fail")
    public void loginFail() {
    }
}
