//name: Bouthayna Metarfi
//purpose: controlling the flow of the applicatin, connecting my model with the view

import java.util.Map;

public class Controller 
{
    //referencing my prediction algorithm, the datamanger and the mainview to interact with
    private NaiveBayesClassifier classifier;
    private DataManager dataManager;
    private MainView mainView;
    
    //a constant with the value PersonHasFver.csv, the data that the model will use
    private static final String DEFAULT_FILE_PATH = "PersonHasFever.csv";
    
    //controller constructor
    public Controller() 
    {
        //creating a new naive bayes classifier instance, which will be populated with predefined data
        classifier = new NaiveBayesClassifier();
        
        //creating a new data manager with the default file path
        dataManager = new DataManager(DEFAULT_FILE_PATH);
        
        //initialisng the view to null, because it will be set later with the setmainview method
        mainView = null;
    }
    
    //this method will let me set the mainview reference after the controller is created
    public void setMainView(MainView mainView) 
    {
        this.mainView = mainView;
    }

}
