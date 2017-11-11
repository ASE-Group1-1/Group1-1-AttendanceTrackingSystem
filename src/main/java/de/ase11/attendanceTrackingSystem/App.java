package de.ase11.attendanceTrackingSystem;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        User testUser = new User("test@example.de", "Test User");
        System.out.println(testUser.getEmail());
        System.out.println(testUser.getUser_id());
    }
}
