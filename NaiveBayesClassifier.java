//name: Bouthayna Metarfi
//purpose: this will be the algorithm for the predictions
import java.util.HashMap;
import java.util.Map;

public class NaiveBayesClassifier 
{
    //this private variable is going to hold my frequency table object, so the classifier has a frequency table
    private FrequencyTable frequencyTable;
    
    //this will prevent any 0 probabilities, which would make the calculation equal to 0
    private static final double addOne = 1;

    //this will be the default constructor
    public NaiveBayesClassifier() 
    {
        //creating a new frequency table object
        this.frequencyTable = new FrequencyTable();
        //calling the loadpredifinedcounts method on this table, which will populate the table with my hard coded values
        this.frequencyTable.loadPredefinedCounts();
    }
    
    //this constructor will be for when i load the data from my csv file, it allows creating
    //a classifier with an already populated frequency table, it accepts a frequency table parameter.
    public NaiveBayesClassifier(FrequencyTable frequencyTable) 
    {
        this.frequencyTable = frequencyTable;
    }
    
    //this method will predict the probability based off of the input features, and returns a map with the probabilities of yes and no
    public Map<String, Double> predict(String bodyTemp, String chills, String headache, String fatigue) 
    {
        //this hash map will store the results of the calculation
        Map<String, Double> result = new HashMap<>();
        
        //calls the calculateprobability method to calculate the probablility for each outcome, passes all the necessary features and labels
        double probYes = calculateProbability(bodyTemp, chills, headache, fatigue, "Yes");
        double probNo = calculateProbability(bodyTemp, chills, headache, fatigue, "No");
        
        //calculates the final probability by dividing it by their sum
        double totalProb = probYes + probNo;

        //storing the results into the result map
        result.put("Yes", probYes / totalProb);
        result.put("No", probNo / totalProb);
        
        //returning the map with the probabilities
        return result;
    }

    //this method uses naive bayes theoram to calculate the probability of the class
    private double calculateProbability(String bodyTemp, String chills, String headache, String fatigue, String className) 
    {
        //gets the count of the class from the frequency table
        int classCount = frequencyTable.getClassCounts().get(className);
        //this divides the total examples with the class count to get the prior probability
        double classPrior = (double) classCount / frequencyTable.getTotalExamples();
        
        //this with call getconditionprobability to get the probability of that feature given the class
        double pBodyTemp = getConditionalProbability(frequencyTable.getBodyTemperatureCounts(), bodyTemp, className);
        double pChills = getConditionalProbability(frequencyTable.getChillsCounts(), chills, className);
        double pHeadache = getConditionalProbability(frequencyTable.getHeadacheCounts(), headache, className);
        double pFatigue = getConditionalProbability(frequencyTable.getFatigueCounts(), fatigue, className);
        
        //this uses the naive bayes formula to return the probability
        return pBodyTemp * pChills * pHeadache * pFatigue * classPrior;
    }
    
    //this method calculates the conditional probability of a feature given a class
    private double getConditionalProbability(Map<String, Map<String, Integer>> featureCounts, String value, String className) 
    {
        //gets the count of how many times this symptom appears with the class
        int featureClassCount = featureCounts.get(value).get(className);

        //gets the total number for this class, so the total number of fever or no fever
        int classCount = frequencyTable.getClassCounts().get(className);

        //this formula will prevent any probability from being exactly zero incase there are situations with zero examples
        //the 2 represents the 2 possible values
        //returns the probability of the fever outcome given the overall sum of that fever outcome
        return (featureClassCount + addOne) / (classCount + addOne * 2);
    }
    
    //getter method to return the frequency table
    public FrequencyTable getFrequencyTable() 
    {
        return frequencyTable;
    }
}
