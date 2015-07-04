package controllers;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import views.html.*;
import models.*;
import play.data.Form;
import java.util.List;

import static play.libs.Json.*;

public class Application extends Controller {
    
    private UserManager userManager = new UserManager();

    public Result index() {
        return ok(index.render());
    }

    
    public Result addUser() {
        User user = Form.form(User.class).bindFromRequest().get();
        userManager.insert(user);
        return redirect(routes.Application.index());
    }

    
    public Result getUsers() {
        List<User> users = userManager.getUsers();
        return ok(toJson(users));
    }
    
    public Result testMethod() {
        userManager.test();
        
        return ok(index.render());
    }
}
