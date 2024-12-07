package WatChill.User;

import WatChill.Cast.Cast;
import WatChill.Movie.*;
import WatChill.Director.*;
import WatChill.Subscription.Subscription;
/*  Functionalities:
1- Add /update /delete in all of the entities.
2- The user can display all the movies he already watched.
3- The movie rating should be updated by the new ratings given to a
movie based on the users watch record.
4- Displaying the top-rated movies to the user.
5- The admin can see the most subscribed plan among (Basic, Standard,
Premium).
6- The admin can see which month had the most revenue to the
application by adding all the plan prices subscribed in that month.
7- The user can see the casts and directors’ information and their
movies.
8- The user can search for a movie/director/actor by his name.
9- The user can also search and display all movies by the genre of
movie.
10- Display the top watched movies for the user.
11- Display the recent movies for the users.
12- Display movies that matches specific filters (The filters may be
based on rating, movie duration and language)
13- Recommendation feature (That recommends movies to user
based on the genres and cast that the user usually watch movies for)
*/


import java.util.*;
public abstract class User {
    String name;
    String email;
    String password;
    String fname;
    String lname;
    String subscription;

    public User(String name, String email, String password, String fname, String lname, String subscription) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.subscription = subscription;
        this.movies = new ArrayList<>();
        this.watchRecords = new ArrayList<>();
        this.admin = admin;
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }

    public void setFname(String fname) { this.fname = fname; }
    public String getFname() { return fname; }

    public void setLname(String lname) { this.lname = lname; }
    public String getLname() { return lname; }

    public void setSubscription(String subscription) { this.subscription = subscription; }
    public String getSubscription() { return subscription; }
    
}

