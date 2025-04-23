//name: Bouthayna Metarfi
//purpose: this will be the algorithm for the predictions
import java.util.HashMap;
import java.util.Map;

public class NaiveBayesClassifier 
{
    //this private variable is going to hold my frequency table object, so the classifier has a frequency table
    private FrequencyTable frequencyTable;

    //this will be the default constructor
    public NaiveBayesClassifier() 
    {
        //creating a new frequency table object
        this.frequencyTable = new FrequencyTable();
        //calling the loadpredifinedcounts method on this table, which will populate the table with my hard coded values
        this.frequencyTable.loadPredefinedCounts();
    }

}
