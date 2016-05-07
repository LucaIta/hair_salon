import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main (String[] args){

    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      model.put("stylists", Stylist.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist newStylist = new Stylist (request.queryParams("stylistname"));
      newStylist.save();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int stylist_id = Integer.parseInt(request.params("id"));
      model.put("stylist", Stylist.find(stylist_id));
      model.put("clients", Client.findByStylistId(stylist_id));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/clients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String clientName = request.queryParams("clientName");
      int stylist_id = Integer.parseInt(request.queryParams("stylist_id"));
      Client newClient = new Client(clientName, stylist_id);
      newClient.save();
      model.put("stylist", Stylist.find(stylist_id));
      model.put("clients", Client.findByStylistId(stylist_id));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int stylist_id = Integer.parseInt(request.params("id"));
      model.put("stylist", Stylist.find(stylist_id));
      model.put("clients", Client.findByStylistId(stylist_id));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylist_id/clients/:client_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int client_id = Integer.parseInt(request.params("client_id"));
      model.put("client", Client.find(client_id));
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/tasks/:client_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int client_id = Integer.parseInt(request.params("client_id"));
      int stylist_id = Integer.parseInt(request.params("stylist_id"));
      Client.find(client_id).delete();
      model.put("stylist", Stylist.find(stylist_id));
      model.put("clients", Client.findByStylistId(stylist_id));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/tasks/:client_id/modifyName", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int client_id = Integer.parseInt(request.params("client_id"));
      int stylist_id = Integer.parseInt(request.params("stylist_id"));
      String newClientName = request.queryParams("newClientName");
      Client.find(client_id).changeName(newClientName);
      model.put("stylist", Stylist.find(stylist_id));
      model.put("clients", Client.findByStylistId(stylist_id));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // /stylists/$stylist.getId()/clients

  }





}
