import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;


public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Client_instantiatesCorrectly_true(){
    Client newClient = new Client("Mark", 1);
    assertTrue(newClient instanceof Client);
  }

  @Test
  public void getName_returnNameCorrectly_Mark(){
    Client newClient = new Client("Mark", 1);
    assertEquals("Mark", newClient.getName());
  }

  @Test
  public void getStylistId_returnsStylistIdCorrectly_34(){
    Client newClient = new Client("Mark", 34);
    assertEquals(34, newClient.getStylistId());
  }

  @Test
  public void equals_correctlyCompareClientsCorrectly_true(){
    Client firstClient = new Client("Mark", 1);
    Client secondClient = new Client("Mark", 1);
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void save_savesClientCorrectlyInDatabase(){
    Client newClient = new Client("Mark", 1);
    newClient.save();
    assertTrue(Client.all().get(0).equals(newClient));
  }

  @Test
  public void find_returnsClientCorrectly(){
    Client newClient = new Client("Mark", 1);
    newClient.save();
    assertTrue(newClient.equals(Client.find(newClient.getId())));
  }

  @Test
  public void find_returnClientByStylistId(){
    Client firstClient = new Client("Mark", 81);
    Client secondClient = new Client("Luke", 81);
    firstClient.save();
    secondClient.save();
    Client[] clients = new Client[] {firstClient, secondClient};
    assertTrue(Client.findByStylistId(81).containsAll(Arrays.asList(clients)));
  }

  @Test
  public void delete_deleteClientCorrectly(){
    Client firstClient = new Client("Mark", 81);
    firstClient.save();
    firstClient.delete();
    assertEquals(0, Client.all().size());
  }

  @Test
  public void changeName_changeClientNameCorrectly(){
    Client firstClient = new Client("Mark", 81);
    firstClient.save();
    int client_Id = firstClient.getId();
    firstClient.changeName("Luca");
    assertEquals("Luca", Client.find(client_Id).getName());
  }


}
