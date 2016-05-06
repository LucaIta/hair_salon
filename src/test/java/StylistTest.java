import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;


public class stylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Stylist_instantiatesCorrectly_true(){
    Stylist newStylist = new Stylist("Luca");
    assertTrue(newStylist instanceof Stylist);
  }

}
