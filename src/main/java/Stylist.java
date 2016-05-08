import org.sql2o.*;
import java.util.List;


public class Stylist {

  private String name;
  private int id;

  public Stylist (String name){
    this.name = name;
  }



  public String getName(){
    return name;
  }

  public int getId(){
    return id;
  }

  public void delete(){
    String sql = "DELETE FROM stylists WHERE id = :id";
    try (Connection con = DB.sql2o.open()){
      con.createQuery(sql).addParameter("id", this.id).executeUpdate();
    }
  }

  public void changeName(String name){
    String sql = "UPDATE stylists SET name = :name WHERE id = :id";
    try (Connection con = DB.sql2o.open()){
      con.createQuery(sql).addParameter("id", this.id).addParameter("name", name).executeUpdate();
    }
  }

  public void save(){
    String sql = "INSERT INTO stylists (name) values (:name)";
    try (Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).executeUpdate().getKey();
    }
  }

  public static List<Stylist> all(){
    String sql = "SELECT name, id FROM stylists";
    try (Connection con = DB.sql2o.open()){
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public static Stylist find(int id){
    try (Connection con = DB.sql2o.open()){
      String sql = "SELECT id, name FROM stylists WHERE id = :id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Stylist.class);
    }
  }

  @Override
  public boolean equals(Object otherStylist){
    if (!(otherStylist instanceof Stylist)){
      return false;
    } else {
      Stylist otherStylist2 = (Stylist) otherStylist;
      return this.getName().equals(otherStylist2.getName()) && this.getId() == otherStylist2.getId();
    }
  }
}
