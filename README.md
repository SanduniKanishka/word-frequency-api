# Word Frequency API

## Description

The Word Frequency API is a Spring Boot application that analyzes a given text file to find the top K most frequent words. It uses Java 16 and provides an endpoint to retrieve the results.


## Installation and Setup

1. Clone the repository:

   git clone https://github.com/yourusername/word-frequency-api.git
   cd word-frequency-api
   
2. Build the project
	./mvnw clean install

The application will start at http://localhost:8080

## API Endpoint
Retrieve Top K Frequent Words
Endpoint: /api/v1/TopFrequentWords
Method: GET
Parameters:
filePath (String): Path to the text file.
topK (int): Number of top frequent words to retrieve.
Authorization: Basic Authentication required. Use username and password specified in the SecurityConfig class.
For the method 'TopFrequentWords' 'USER' level is authorized. Below are the credentials of that.
username: user
password: password

### Example using cURL
curl -u user:password "http://localhost:8080/api/v1/TopFrequentWords?filePath=/path/to/your/text/file.txt&topK=5"

### Example using Postman
Open Postman.
Create a new request.
Set the request method to GET.
Enter the URL: http://localhost:8080/api/v1/TopFrequentWords.
In the "Authorization" tab, choose "Basic Auth" and enter the username and password.
In the "Params" tab, add the parameters:
Key: filePath, Value: /path/to/your/text/file.txt
Key: topK, Value: 5
Click "Send" to make the request.

## Technical Aspects

### Algorithm

calculateTopKFrequentWords Function:
In this method, first a hashmap named wordFrequencyMap is initialized to store the frequencies of words.
BufferedReader is used to read line by line and for each line, it calls the processLine method to extract and process words. In here, to handle verylarge files, the file is processed line by line instead of reading the entire content into memory at once. 
A PriorityQueue (maxHeap) is created using a custom comparator to compare entries based on word frequencies.
The comparator ensures that the entry with a higher frequency comes first, and in case of a tie, it uses lexicographical order(alphabetical order).
Then all the entries are added from the wordFrequencyMap to the maxHeap. We can also do this using Java Stream API and collectors in less line of codes. But priority queues is efficient comparing hashmap. Also PriorityQueue stores the word frequencies in a HashMap and then builds a max heap and this is memory-efficient for large datasets as it doesn't require storing all entries in memory. Therefore, priority queue is used in here.
Finally, It retrieves the top K frequent words from the maxHeap, adding them to the result list (result) and returns the list of top K frequent words.

processLine function:
This helper function processes a line of text, extracts words, cleans them, and updates the word frequency map. The cleaning process ensures that only alphabetic characters are considered, and case-insensitivity is maintained. The cleaned words are used as keys in the wordFrequencyMap, and their counts are updated accordingly.

### Caching
To avoid re-calculating the top K most frequent words every time the API is called, a caching mechanishm is implemented. For this a hash map is used and key of the record is created using file path and k.

### Authentication and Authorization
For authentication, username and password is hardcode in SecurityConfig file. In here 2 roles are created as 'USER' and 'ADMIN'. But only 'USER' level is authorized for 'TopFrequentWords' method. Username and password for 'USER' is as follows.
username: user
password: password

### Testing
Multiple test cases are written to cover the scenarios. In here, to create temporary files Files.createTempFile() method is used. These files are created in  default temporary-file directory and this  default temporary-file directory can be vaied in operating system. As an example,
for Windows :  %USER%\AppData\Local\Temp
for Ubuntu : /tmp
If any error is occured when creating a temporary file, please change Files.createTempFile() like below in createTempTestFile(String content) Method
Path tempFilePath = Files.createTempFile(Paths.get("E://"), "testFile", ".txt");
Please specify specific path instead "E://" .



 