//name: Bouthayna Metarfi
//purpose: this will handle the loading of my dataset

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataManager 
{
    //this variable stores the path to my csv file
    private String dataFilePath;
    
    //constructor, takes the file path parameter and stores it in the variable
    public DataManager(String dataFilePath) 
    {
        this.dataFilePath = dataFilePath;
    }
    
    //this method returns a frequency table object populated with data from my csv file
    public FrequencyTable loadDataset() 
    {
        //creating a new empty frequency table object that will be filled with data
        FrequencyTable frequencyTable = new FrequencyTable();
        
        //checking if the file exists
        File file = new File(dataFilePath);

        //for me to see if its working in terminal
        System.out.println("DataManager: Attempting to load dataset from: " + file.getAbsolutePath());
        System.out.println("DataManager: File exists: " + file.exists());
        
        //opens the file for reading using the bufferreader and the filereader
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) 
        {
            //this will hold each line read from the file
            String line;
            //this will check the header
            boolean isFirstLine = true;
            //this is going to count how many data rows are done
            int rowCount = 0;
            
            //this while loop reads the file until the end, when readline returns null
            while ((line = reader.readLine()) != null) {
                //if statement to skip the header
                if (isFirstLine) 
                {
                    isFirstLine = false;
                    System.out.println("DataManager: Found header: " + line);
                    continue;
                }

                //here i created an array of strings called symptomdata
                //this will divide the original string into an array of substrings
                String[] symptomData = line.split(",");
                //if statement to check if there are atleast 5 symptom data in the line
                if (symptomData.length >= 5) 
                {
                    //using the update counts method to update the frequency table with this data row
                    frequencyTable.updateCounts
                    (
                        symptomData[0].trim(), // BodyTemperature
                        symptomData[1].trim(), // Chills
                        symptomData[2].trim(), // Headache
                        symptomData[3].trim(), // Fatigue
                        symptomData[4].trim()  // HasFever
                    );
                    //incrementing the count of rows that are done
                    rowCount++;
                } 
                //else statement if the data set doesnt have enough symptoms
                else 
                {
                    System.out.println("DataManager: Invalid row format: " + line);
                }
            }
            
            //for me to see summary of whats going on
            System.out.println("DataManager: Successfully loaded " + rowCount + " rows from dataset");
            System.out.println("DataManager: Fever (Yes): " + frequencyTable.getClassCounts().get("Yes"));
            System.out.println("DataManager: No Fever (No): " + frequencyTable.getClassCounts().get("No"));
            
        } 
        //this will catch any io exceptions that occur while reading the file
        catch (IOException e) 
        {
            System.err.println("DataManager: Error loading dataset: " + e.getMessage());
            System.out.println("DataManager: Falling back to predefined counts:(((");
            //the method will rely on the predefined data if an error occurs
            frequencyTable.loadPredefinedCounts();
        }
        //returns the filled in frequency table yay
        return frequencyTable;
    }
}
