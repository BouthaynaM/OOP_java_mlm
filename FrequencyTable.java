//name: Bouthayna Metarfi
//date: 13/04/2025
//purpose: stores the frequency counts needed to calculate probabilities for the predictions
//will count how often each symptom will appear with each outcome

import java.util.HashMap;
import java.util.Map;

 //this class is storing my frequency table for the naive bayes classifier
 //its going to keep track of how many times the features values appears with each class label
public class FrequencyTable 
{
    //attributes
    //im using nested maps so i can have the feature be the outer map, the inner map will be the class label and the integer will store the number of occurrences
    private Map<String, Map<String, Integer>> bodyTemperatureCounts;
    private Map<String, Map<String, Integer>> chillsCounts;
    private Map<String, Map<String, Integer>> headacheCounts;
    private Map<String, Map<String, Integer>> fatigueCounts;

    //has the total counts for each class label
    private Map<String, Integer> classCounts;

    //has the total number of examples in the dataset
    private int totalExamples;
    
    //this is my contructure for the frequency table, its initializing the frequency table with empty counts
    public FrequencyTable() 
    {
  
        //initializing the maps
        bodyTemperatureCounts = new HashMap<>();
        chillsCounts = new HashMap<>();
        headacheCounts = new HashMap<>();
        fatigueCounts = new HashMap<>();
        classCounts = new HashMap<>();

        //setting up the structure to be for the yes or no counts for each feature value
        initializeFeatureCounts(bodyTemperatureCounts, new String[]{"High", "Normal"});
        initializeFeatureCounts(chillsCounts, new String[]{"Yes", "No"});
        initializeFeatureCounts(headacheCounts, new String[]{"Yes", "No"});
        initializeFeatureCounts(fatigueCounts, new String[]{"Yes", "No"});
        
        //initialize class counts
        classCounts.put("Yes", 0);
        classCounts.put("No", 0);

        //initialise the total to 0 
        totalExamples = 0;
    }
    
    //this method is going to help to initialize count maps for each feature value
    private void initializeFeatureCounts(Map<String, Map<String, Integer>> featureCounts, String[] values) 
    {
        for (String value : values) 
        {
            Map<String, Integer> classMap = new HashMap<>();
            classMap.put("Yes", 0);
            classMap.put("No", 0);
            featureCounts.put(value, classMap);
        }
    }
    
    //this method updates the frequency table with a new data point
    //its called for each row to populate the table when the csv loads
    public void updateCounts(String bodyTemp, String chills, String headache, String fatigue, String hasFever) 
    {
        // Increment counts for each feature value with respect to class
        bodyTemperatureCounts.get(bodyTemp).put(hasFever, bodyTemperatureCounts.get(bodyTemp).get(hasFever) + 1);
        chillsCounts.get(chills).put(hasFever, chillsCounts.get(chills).get(hasFever) + 1);
        headacheCounts.get(headache).put(hasFever, headacheCounts.get(headache).get(hasFever) + 1);
        fatigueCounts.get(fatigue).put(hasFever, fatigueCounts.get(fatigue).get(hasFever) + 1);
        
        //increment class count
        classCounts.put(hasFever, classCounts.get(hasFever) + 1);
        
        //increment total count
        totalExamples++;
    }

    //this is my hard coding of the frequency table
    //im using this as a backup if my csv file fails to load
    //the naive bayes will use these counts to calculate probabilities
    public void loadPredefinedCounts() 
    {
        //clear existing counts
        classCounts.put("Yes", 0);
        classCounts.put("No", 0);
        totalExamples = 0;
        
        //high body temperature counts
        bodyTemperatureCounts.get("High").put("Yes", 51);
        bodyTemperatureCounts.get("High").put("No", 3);
        
        //normal body temperature counts
        bodyTemperatureCounts.get("Normal").put("Yes", 4);
        bodyTemperatureCounts.get("Normal").put("No", 142);
        
        //chills counts
        chillsCounts.get("Yes").put("Yes", 39);
        chillsCounts.get("Yes").put("No", 31);
        chillsCounts.get("No").put("Yes", 16);
        chillsCounts.get("No").put("No", 114);
        
        //headache counts
        headacheCounts.get("Yes").put("Yes", 41);
        headacheCounts.get("Yes").put("No", 46);
        headacheCounts.get("No").put("Yes", 14);
        headacheCounts.get("No").put("No", 99);
        
        //fatigue counts
        fatigueCounts.get("Yes").put("Yes", 44);
        fatigueCounts.get("Yes").put("No", 47);
        fatigueCounts.get("No").put("Yes", 11);
        fatigueCounts.get("No").put("No", 98);
        
        //class totals
        classCounts.put("Yes", 55);
        classCounts.put("No", 145);
        
        //total examples
        totalExamples = 200;
    }
      
    //getters for all the count maps
    public Map<String, Map<String, Integer>> getBodyTemperatureCounts() 
    {
        return bodyTemperatureCounts;
    }
    
    public Map<String, Map<String, Integer>> getChillsCounts() 
    {
        return chillsCounts;
    }
    
    public Map<String, Map<String, Integer>> getHeadacheCounts() 
    {
        return headacheCounts;
    }
    
    public Map<String, Map<String, Integer>> getFatigueCounts() 
    {
        return fatigueCounts;
    }
    
    public Map<String, Integer> getClassCounts() 
    {
        return classCounts;
    }
    
    public int getTotalExamples() 
    {
        return totalExamples;
    }
}