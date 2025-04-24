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
    
    //method to make prediction based on the symptoms inputted
    //essentially connects the mainviews request for a prediction to the model
    public Map<String, Double> predict(String bodyTemp, String chills, String headache, String fatigue) 
    {
        //sends the symptoms to the classifier object to calculate the predictions and returns the prediction result
        return classifier.predict(bodyTemp, chills, headache, fatigue);
    }

    //this method will handle the process of loading data and updating the model, when train button is clicked
    public void loadAndTrainModel() 
    {
        //message in terminal for me to see if working
        System.out.println("Controller: Starting model training :)");

        //this calls the loaddataset from the datamanger class and returns a populated frequency table
        FrequencyTable frequencyTable = dataManager.loadDataset();

        //replacing old classifier with new classifer with the frequency table, using the second constructor
        classifier = new NaiveBayesClassifier(frequencyTable);

        //displaying message to confrim the classifer was updated
        System.out.println("Controller: Classifier updated with new frequency table");
        
        //checks if the view is available, and if it is, updates itself wit the new model information
        if (mainView != null) 
        {
            mainView.updateModelInfo();
        }
    }

    //formatted string for the frequency table data to be displayed if the user wants to see it
    public String getFrequencyTableSummary() 
    {
        //getting the frequency table from the classifier
        FrequencyTable table = classifier.getFrequencyTable();
        //using string builder so its nicely formatted with the total number of examples,
        //counts of fever and no fever cases, counts of each body temperature value with and without fever
        //counts of chils with and without fever
        StringBuilder sb = new StringBuilder();
        
        sb.append("--- FREQUENCY TABLE SUMMARY!!! ---\n");
        sb.append("Total examples: ").append(table.getTotalExamples()).append("\n");
        sb.append("Fever (Yes): ").append(table.getClassCounts().get("Yes")).append("\n");
        sb.append("No Fever (No): ").append(table.getClassCounts().get("No")).append("\n");
        
        sb.append("\nBODY TEMPERATURE:\n");
        sb.append("High temperature with fever: ").append(table.getBodyTemperatureCounts().get("High").get("Yes")).append("\n");
        sb.append("High temperature without fever: ").append(table.getBodyTemperatureCounts().get("High").get("No")).append("\n");
        sb.append("Normal temperature with fever: ").append(table.getBodyTemperatureCounts().get("Normal").get("Yes")).append("\n");
        sb.append("Normal temperature without fever: ").append(table.getBodyTemperatureCounts().get("Normal").get("No")).append("\n");
        
        sb.append("\nCHILLS:\n");
        sb.append("Chills with fever: ").append(table.getChillsCounts().get("Yes").get("Yes")).append("\n");
        sb.append("Chills without fever: ").append(table.getChillsCounts().get("Yes").get("No")).append("\n");
        sb.append("No chills with fever: ").append(table.getChillsCounts().get("No").get("Yes")).append("\n");
        sb.append("No chills without fever: ").append(table.getChillsCounts().get("No").get("No")).append("\n");
        
        return sb.toString();
    }
}
