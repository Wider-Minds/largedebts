package models;


public class User {

	public String id;

    public String name;
    
    public User () {
        //For auto fill
    }
    
    public User (String id, String name) {
        this.id = id;
        this.name = name;
    }
}
