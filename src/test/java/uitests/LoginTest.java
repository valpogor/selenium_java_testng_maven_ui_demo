package uitests;

import org.testng.annotations.*;
import pages.*;

public class LoginTest extends LoginPage {

    //Wrapped methods to do all verification on a single line

    @Test(priority = 1, groups={"login", "smoke"})
    public void VerifyLoginPageHeadersButtons() {
        verifyLoginPageHeadersButtons();
    }

    @Test(priority = 2, groups={"search", "smoke"})
    public void VerifySearchMostExpensiveSaleListing() throws Exception {
        verifySearchMostExpensiveSaleListing(5);
    }
}
