import org.sql2o.*;
import java.util.List;

public class Client {

  private String name;
  private int id;
  private int stylist_id;

  public Client (String name, int stylist_id){
    this.name = name;
    this.stylist_id = stylist_id;
  }


  // I could use 2 constructors to allow for the association of the client with the Stylist via name instead of ID



  public String getName(){
    return name;
  }

  public int getId(){
    return id;
  }

  public int getStylistId(){
    return stylist_id;
  }

  public void save(){
    String sql = "INSERT INTO clients (name, stylist_id) VALUES (:name, :stylist_id)";
    try (Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).addParameter("stylist_id", this.stylist_id).executeUpdate().getKey();
    }
  }

  public void delete(){
    String sql = "DELETE FROM clients WHERE id = :id";
    try (Connection con = DB.sql2o.open()){
      con.createQuery(sql).addParameter("id", this.id).executeUpdate();
    }
  }

  public void changeName(String name){
    String sql = "UPDATE clients SET name = :name WHERE id = :id";
    try (Connection con = DB.sql2o.open()){
      con.createQuery(sql).addParameter("id", this.id).addParameter("name", name).executeUpdate();
    }
  }

  public static List<Client> all(){
    String sql = "SELECT id, name, stylist_id FROM clients";
    try (Connection con = DB.sql2o.open()){
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  public static Client find(int id){
    try (Connection con = DB.sql2o.open()){
      String sql = "SELECT id, name, stylist_id FROM clients WHERE id = :id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Client.class);
    }
  }

  public static List<Client> findByStylistId(int stylist_id){ //modified
    try (Connection con = DB.sql2o.open()){
      String sql = "SELECT id, name, stylist_id FROM clients WHERE stylist_id = :stylist_id";
      return con.createQuery(sql).addParameter("stylist_id", stylist_id).executeAndFetch(Client.class);  // modified
    }
  }

  @Override
  public boolean equals(Object otherClient){
    if (!(otherClient instanceof Client)){
      return false;
    } else {
      Client otherClient2 = (Client) otherClient;
      return this.getName().equals(otherClient2.getName()) && this.getId() == otherClient2.getId() && this.getStylistId() == otherClient2.getStylistId();
    }
  }

}
