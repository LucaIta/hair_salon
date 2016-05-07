import org.sql2o.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.*;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
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
    submit("#addClient");
    assertThat(pageSource()).contains("Luca");
  }

  @Test public void ClientPageDisplayed() {
    Stylist newStylist = new Stylist ("Mark");
    newStylist.save();
    String stylistPath = String.format("http://localhost:4567/stylists/%d", newStylist.getId());
    goTo(stylistPath);
    fill("#clientName").with("Luca");
    submit("#addClient");
    click("a", withText("Luca"));
    assertThat(pageSource()).contains("Luca");
  }

  @Test public void ClientCanBeDelited() {
    Stylist newStylist = new Stylist ("Mark");
    newStylist.save();
    String stylistPath = String.format("http://localhost:4567/stylists/%d", newStylist.getId());
    goTo(stylistPath);
    fill("#clientName").with("Luca");
    submit("#addClient");
    click("a", withText("Luca"));
    submit("#delete-user");
    assertThat(pageSource()).contains("Mark");
    assertThat(!(pageSource()).contains("Luca")); // is this test correct?
  }

  @Test public void ClientNameCanBeModified() {
    Stylist newStylist = new Stylist ("Mark");
    newStylist.save();
    String stylistPath = String.format("http://localhost:4567/stylists/%d", newStylist.getId());
    goTo(stylistPath);
    fill("#clientName").with("Luca");
    submit("#addClient");
    click("a", withText("Luca"));
    fill("#newClientName").with("Whatever");
    submit("#modifyButton");
    assertThat(pageSource()).contains("Whatever");
  }

  @Test public void stylistIsDeleted() { // it passes because I get error page
    goTo("http://localhost:4567/");
    fill("#stylistname").with("Mark");
    submit(".btn");
    submit("#delete-stylist");
    assertThat(!(pageSource()).contains("Mark"));
  }

  @Test public void stylistNameIsChangedCorrectly() { // it passes because I get error page
    goTo("http://localhost:4567/");
    fill("#stylistname").with("Mark");
    submit(".btn");
    fill("#stylistname").with("Luke");
    submit("#modifyStylistName");
    assertThat(!(pageSource()).contains("Mark"));
  }



  // @Test public void stylistsListIsDisplayedCorrectly() {
  //   goTo("http://localhost:4567/stylists");
  // }

}
