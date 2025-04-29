BodyTemperature	Chills	Headache	Fatigue		Yes	No
High		Yes	Yes		Yes		26	2
High		Yes	Yes		No		4	0
High		Yes	No		Yes		9	1
High		Yes	No		No		0	0
High		No	Yes		Yes		6	0
High		No	Yes		No		3	0
High		No	No		Yes		2	0
High		No	No		No		1	0
Normal		Yes	Yes		Yes		0	5
Normal		Yes	Yes		No		0	5
Normal		Yes	No		Yes		0	5
Normal		Yes	No		No		0	13
Normal		No	Yes		Yes		0	9
Normal		No	Yes		No		2	25
Normal		No	No		Yes		1	25
Normal		No	No		No		1	55


Fever (Yes): 55
No Fever (No): 145
Total Examples: 200

BODY TEMPERATURE COUNTS:
High temperature with fever: 51
High temperature without fever: 3
Normal temperature with fever: 4
Normal temperature without fever: 142

CHILLS COUNTS:
Chills with fever: 39
Chills without fever: 31
No chills with fever: 16
No chills without fever: 114

HEADACHE COUNTS:
Headache with fever: 41
Headache without fever: 46
No headache with fever: 14
No headache without fever: 99

FATIGUE COUNTS:
Fatigue with fever: 44
Fatigue without fever: 47
No fatigue with fever: 11
No fatigue without fever: 98

explenation of classes and methods:

MAIN CLASS
the purpose of the main class is to serve as the entry point to my program, it initializes the core components, the controller 
and the mainview and starts the application.

CONTROLLER CLASS
the purpose of the controller class is to act as the mediator between my naivebayesclassifier model and the mainview
it manages the applications flow and the data between the components.

attributes:
private NaiveBayesClassifier classifier: reference to the prediction model
private DataManager dataManager: reference to the data loading component
private MainView mainView: reference to the user interface
private static final String DEFAULT_FILE_PAT: a constant defining the csv file path "PersonHasFever.csv"

methods:
Controller(): constructor that initialises the controller with default values
the constructor creates a new NaiveBayesClassifier instance with predefined data
creates a new DataManager with the default file path
initializes the mainView to null to be set later

setMainView(MainView mainview)
sets the reference to the view component after controller creation
has a parameter of a MainView instance
it stores the reference to enable controller and interface communication

predict(String bodyTemp, String chills, String headache, String fatigue):
the purpoe of the predict method is to process the symptom inputs and forwards them to the classifier
it takes in four symptom values as strings
it returns a map containing probabilities for having a fever or not having a fever

loadAndTrainModel()
the purpose for this method is to handle the data loading and model training when the train model button is pressed
it calls the DataManager to load the dataset, creates a new classifier with the loaded frequency table
and updates the view if its available

getFrequencyTableSummary()
the purpose for this method is to create a formatted string representation of the frequency table
it returns a string containing the frequency table information
it gets the frequency table from the classifier, uses the StringBuilder to create a formatted display of
total examples count, class(fever or no fever), body temperature counts by fever status and chills counts by fever status
headache counts by fever status and fatigue counts by fever status

MAINVIEW CLASS
the MainView class purpose is to create a graphical user interface handling all the user interactions and displaying results
it references the controller class

methods:
MainView(Controller controller)
this is the MainView constructor, its purpose is to set up the user interface and establishes connection with the controller
for the parameters it takes in a controller instance
it stores the controller reference, establishes communcation by calling controller.setMainView
configures the main frame like the size, the layout etc
creates the prediction panel and adds buttons for training and viewing the frequency table, and sets up action listeners for buttons

createPredictionPanel()
the purpose of this method is to create the panel containing symptom inputs and prediction elements
it created a predict button with an action listener and adds a result label to display predictions

makePrediction()
this methods purpose is to collect users inputs and requests prediction from the controller
it gets the currently selected values from all the dropdown menus
logs the input values and calls the controllers predict method
then it converts the probabilities to percentages and displays the results

updateModelInfo()
the purpose of this method is to update the user interface after the train button was clicked
it calls the makePrediction to refresh the display with predicitons from the updated model

NAIVEBAYESCLASSIFIER CLASS
the purpose of this class is to implement the prediction algorithm, it calculates probabilities of fever based on symptom inputs using 
the naive bayes formula

attributes: references the frequency table class
private static final double addOne: a constant value to prevent zero probabilities

methods:
NaiveBayesClassifier()
this is the default constructure that initialises the classifier with predefined data
it creates a new FrequencyTable and loads it with predefined counts through loadPredefinedCounts() method from the frequencytable class

NaiveBayesClassifier(FrequencyTable frequencyTable)
this is the second constructor, its purpose is to create a classifier with a pre populated frequency table
it takes in a populated FrequencyTable instance, stores the provided frequency table for predictions

predict(String bodyTemp, String chills, String headaches, String fatigue)
the purpose of this predict method is for the main prediction that calculates probabilities for each outcome
it takes in four symptom values as strings, and returns a map containing normalized probabilities for fever and no fever
it creates a result map, calculates raw probabilities for yes and no outcomes, normalizes the probabilities by dividing by their sum
and returns the normalized probabilites

calculateProbability(String bodyTemp, String chills, String headache, String fatigue, String className)
the purpose of this method is to implement the naive bayes formula to calculate the raw probability
it takes four symptom values and the class label yes or no
it returns a double representing the unnormalized probability
it gets the count of the specified class, calculates the prior probability, gets conditional probabilities for each symptom
then multiplies them together according to naive bayes formula

getConditionalProbability(Map<String, Map<String, Integer>> featureCounts, String value, String className)
the purpose of this method is to calculatee the conditional probability with a variable to prevent zero probabilities
it takes in a map, feature value and class label, and returns the condition probability as a double
it gets the count of the feature class combination, gets the total count for the class then applies the addOne varriable to prevent 
zero probabilities and calculates the probability

getFrequencyTable()
this methods purpose is for the frequency table, it returns the FrequencyTable used by the classifier
it provides access to the frequency data

FREQUENCYTABLE CLASS
the purpose for this class is to store and manage frequency counts of symptoms and fever status. 
it serves as the data structure for the training data statistics used by the naive bayes algorithm

attributes:
private Map<String, Map<String, Integer>> bodyTemperatureCounts: nested map for body temperature counts
private Map<String, Map<String, Integer>> chillsCounts: nested map for chill counts
private Map<String, Map<String, Integer>> headacheCounts: nested map for headache counts
private Map<String, Map<String, Integer>> fatigueCounts: nested map for fatigue counts
private int totalExamples: counter for total examples

methods:
FrequencyTable()
this is the constructor for the frequency table, its purpose is to initialize empty frequency maps
it initializes all map structures, sets up the nested maps using initialiseFeatureCounts
initialises class counts and total to zero

initializeFeatureCounts(Map<String, Map<String, Integer>> featureCounts, String[] values)
this methods purpose is to help set up the nested map structure, it takes in the map to initialize and array of possible values
for each value, it creates a new inner map
initializes yes and no counts to zero
adds the inner map to the outer map with the value as key

updateCounts(String bodyTemp, String chills, String headache, String fatigue, String hasFever)
the purpose of this method is to update the frequency table with a new data point
it takes four symptoms values and the fever status
it increments the appropriate count for each feature class combination
increments the class count
increments the total examples count

loadPredefinedCounts()
the purpose of this method is for the hardcoded frequency table to be used as fallback incase the train doesnt work
it clears the existing counts, sets predefined values for all symptom fever combinations, sets the class totals
which are 55 fever, 145 no fever and sets the total examples to 200

getters:
getBodyTemperatureCounts(), getChillsCounts(), getHeadacheCounts(), getFatigueCounts(), getClassCounts(), getTotalExamples()
purpose of the getter methods is to get frequency data, it returns the respective attribute and provides access
to the frequency counts

DATAMANAGER CLASS
the purpose of the data manager class is to handle the loading and reading from the csv file, it
reads the training examples and populates the frequency table

attributes:
private String dataFilePath: stores the path to the csv file

methods:
DataManager(String dataFilePath)
this is the constructor that stores the files path
it takes in a string continaing the csv file path
and stores the file path for later use

loadDataSet()
the purpose of this method is to read the data from the csv file and create a populated frequencytable
it returns the frequencytable populated with data from the csv
it creates a new empty FrequencyTable, checks if the file exists, opens the file using bufferedreader and filereader
skips the header line, processing each row of data by splitting the line by commans and extracting the symptom values and fever status
and updates the frequency table with each data point, returning the populated frequency table




