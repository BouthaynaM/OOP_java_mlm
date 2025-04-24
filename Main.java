//name: Bouthayna Metarfi
//purpose: main class, initialising the key objects to let them handle the logic

public class Main 
{
    public static void main(String[] args) 
    {
        //displaying message in the terminal for me to check if working
        System.out.println("Main: Starting application");
        
        //creates a new instance of my controller class
        Controller controller = new Controller();
        
        //creates a new instance of my mainview class
        MainView view = new MainView(controller);
        
        //makes the screen visible
        view.setVisible(true);
        
        //confirmation message for me to confirm it successfully started
        System.out.println("Main: Application started");
    }
}