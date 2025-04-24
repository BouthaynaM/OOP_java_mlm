//name: Bouthayna Metarfi
//purpose: this will be my gui for the predictions
import javax.swing.*;
import java.awt.*;
import java.util.Map;

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
        
        //adding the panels to the center region
        add(predictionPanel, BorderLayout.CENTER);
        
        //panel for buttons
        JPanel buttonPanel = new JPanel();
        
        //creating my train model button
        JButton trainButton = new JButton("Train Model");
        //action listener to run when button is clicked
        trainButton.addActionListener(e -> {
            try {
                System.out.println("MainView: Train button clicked");
                //calling load and train model from the controller class to train the model with my csv data
                controller.loadAndTrainModel();
                //success message if it works
                JOptionPane.showMessageDialog(this, 
                    "Model successfully trained with CSV data!!!!!!!!!!!!!!!!", 
                    "Training Complete", 
                    JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) 
            {
                System.err.println("MainView: Error during training: " + ex.getMessage());
                //error message if it doesnt work
                JOptionPane.showMessageDialog(this, 
                    "Error training model: " + ex.getMessage(), 
                    "Training Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        //adding the train button to the button panel
        buttonPanel.add(trainButton);
        
        //creating my view frequency table button
        JButton viewTableButton = new JButton("View Frequency Table");
        //action listener for when button is clicked
        viewTableButton.addActionListener(e -> {
            //calling the get frequency table summary from the controller class and putting it in a summary string
            String summary = controller.getFrequencyTableSummary();
            //non editable text area to display the summary
            JTextArea textArea = new JTextArea(summary);
            textArea.setEditable(false);
            //adding scroll pane because the text is long
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Frequency Table", JOptionPane.INFORMATION_MESSAGE);
        });
        //adding the view frequency table button to the button panel with the train model
        buttonPanel.add(viewTableButton);
        //adds it to the bottom of the window
        add(buttonPanel, BorderLayout.SOUTH);
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
        
        //creating my predict button
        JButton predictButton = new JButton("Predict");
        //action listner for when its clicked, and message for me to know when its clicked
        predictButton.addActionListener(e -> {
            System.out.println("MainView: Predict button clicked");
            //calling make prediction method
            makePrediction();
        });
        //adding it to the prediction panel
        predictionPanel.add(predictButton);

        //label to display the prediction results, inital text will just be there before prediction text
        resultLabel = new JLabel("Result will appear here");
        //adding the result label to the prediction panel
        predictionPanel.add(resultLabel);
    }

    //will make the prediction based on the current inputs
    private void makePrediction() 
    {
        try {
            //gets the currently selected values from all four of the drop down menus
            String bodyTemp = (String) bodyTempCombo.getSelectedItem();
            String chills = (String) chillsCombo.getSelectedItem();
            String headache = (String) headacheCombo.getSelectedItem();
            String fatigue = (String) fatigueCombo.getSelectedItem();
            
            //displaying it to the terminal for me to see if its right
            System.out.println("MainView: Making prediction for: " + bodyTemp + ", " + chills + ", " + headache + ", " + fatigue);
            
            //calls the predict method in the controller class with those values to get predictions
            Map<String, Double> prediction = controller.predict(bodyTemp, chills, headache, fatigue);

            //converting the prediction probabilities to percentages
            double yesProb = prediction.get("Yes") * 100;
            double noProb = prediction.get("No") * 100;

            //displaying the probabilities to one decimal place
            String result = String.format("Fever: %.1f%%, No Fever: %.1f%%", yesProb, noProb);
            
            //updating the result label with the result string
            resultLabel.setText(result);
            
            //displaying results in console for me
            System.out.println("MainView: Prediction result: " + result);

        } 
        //catching any exceptions that happen when predicting
        catch (Exception e) {
            System.err.println("MainView: Error making prediction: " + e.getMessage());
            resultLabel.setText("Error making prediction");
        }
    }
    
    //this method is for the controller to call when the model is updated
    //ensuring the interface is showing the predictions from the updated model
    public void updateModelInfo() 
    {
        System.out.println("MainView: Updating model info");
        makePrediction();
    }
}
