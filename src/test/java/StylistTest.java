import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;


public class StylistTest {



  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Stylist_instantiatesCorrectly_true(){
    Stylist newStylist = new Stylist("Luca");
    assertTrue(newStylist instanceof Stylist);
  }

  @Test
  public void getName_returnNameCorrectly_Luca(){
    Stylist newStylist = new Stylist("Luca");
    assertEquals("Luca", newStylist.getName());
  }

  @Test
  public void equals_correctlyCompareStylists_True(){
    Stylist firstStylist = new Stylist("Luca");
    Stylist secondStylist = new Stylist("Luca");
    assertTrue(firstStylist.equals(secondStylist));
  }

  @Test
  public void save_savesStylistCorrectlyInDatabase(){
    Stylist newStylist = new Stylist("Luca");
    newStylist.save();
    assertTrue(Stylist.all().get(0).equals(newStylist));
  }

  @Test
  public void find_returnsStylistCorrectly(){
    Stylist newStylist = new Stylist("Luca");
    newStylist.save();
    assertTrue(newStylist.equals(Stylist.find(newStylist.getId())));
  }

  @Test
  public void delete_deleteStylistCorrectly(){
    Stylist newStylist = new Stylist("Luca");
    newStylist.save();
    newStylist.delete();
    assertEquals(0, Client.all().size());
  }

  @Test
  public void changeName_changeStylistNameCorrectly(){
    Stylist newStylist = new Stylist("Luca");
    newStylist.save();
    int client_Id = newStylist.getId();
    newStylist.changeName("Mark");
    assertEquals("Mark", Stylist.find(client_Id).getName());
  }
  
}
