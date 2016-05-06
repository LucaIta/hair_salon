import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Add stylist");
  }

  @Test public void stylistIsCreated() {
    goTo("http://localhost:4567/");
    fill("#stylistname").with("Mark");
    submit(".btn");
    assertThat(pageSource()).contains("Mark");
  }

  @Test public void ClientCreatedAndDisplayed() {
    Stylist newStylist = new Stylist ("Mark");
    newStylist.save();
    String stylistPath = String.format("http://localhost:4567/stylists/%d", newStylist.getId());
    goTo(stylistPath);
    fill("#clientName").with("Luca");
    submit(".btn");
    assertThat(pageSource()).contains("Luca");
  }





  // @Test public void stylistsListIsDisplayedCorrectly() {
  //   goTo("http://localhost:4567/stylists");
  // }

}
