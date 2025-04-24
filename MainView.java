//name: Bouthayna Metarfi
//purpose: this will be my gui for the predictions
import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame 
{
    //here im referencing my controller class 
    private Controller controller;
    
    //a panel to hold the prediction inputs and results
    private JPanel predictionPanel;
    //a label to display prediction results
    private JLabel resultLabel;
    
    //four jcomboboxes for my four features, to let users select symptoms
    private JComboBox<String> bodyTempCombo;
    private JComboBox<String> chillsCombo;
    private JComboBox<String> headacheCombo;
    private JComboBox<String> fatigueCombo;
    
    // Constants for feature values
    //these constant arrays are the sympton choices for the options, wont be changing them so kept them constant
    private static final String[] BODY_TEMP_VALUES = {"High", "Normal"};
    private static final String[] YES_NO_VALUES = {"Yes", "No"};

    //this is the mainview constructor, it accepts a controller parameter
    public MainView(Controller controller) 
    {
        this.controller = controller;
        //for communication between the view and the controller
        controller.setMainView(this);
            
        //setting up the frame with the title, the size, setting default close operation and the layout
        setTitle("Fever Prediction");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

            //calling the create prediction panel to build the interface for making predictions
        createPredictionPanel();

    }


    //panel for the prediction inputs
    private void createPredictionPanel() 
    {
        predictionPanel = new JPanel();
        predictionPanel.setBorder(BorderFactory.createTitledBorder("Predict Fever"));
        predictionPanel.setLayout(new GridLayout(6, 2, 10, 10));
        
        //adding the input choices with layouts
        predictionPanel.add(new JLabel("Body Temperature:"));
        //jcombobox drop down menu with the choices
        bodyTempCombo = new JComboBox<>(BODY_TEMP_VALUES);
        //adding the drop down to the panel
        predictionPanel.add(bodyTempCombo);
        
        //same for chills
        predictionPanel.add(new JLabel("Chills:"));
        chillsCombo = new JComboBox<>(YES_NO_VALUES);
        predictionPanel.add(chillsCombo);
        
        //same for headache
        predictionPanel.add(new JLabel("Headache:"));
        headacheCombo = new JComboBox<>(YES_NO_VALUES);
        predictionPanel.add(headacheCombo);
        
        //same for fatigue
        predictionPanel.add(new JLabel("Fatigue:"));
        fatigueCombo = new JComboBox<>(YES_NO_VALUES);
        predictionPanel.add(fatigueCombo);
        
        //label to display the prediction results, inital text will just be there before prediction text
        resultLabel = new JLabel("Result will appear here");
        //adding the result label to the prediction panel
        predictionPanel.add(resultLabel);
    }


}