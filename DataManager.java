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
    }

}
