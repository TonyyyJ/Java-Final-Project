import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import java.util.logging.Logger;
import weka.core.Attribute;

public class EmailClassifier {
    private FilteredClassifier classifier;
    private Instances structure;
    private StringToWordVector filter;
    private static Logger LOGGER = Logger.getLogger("EmailClassifier");
    private ArrayList < Attribute > wekaAttributes;
    private Instances data;

    
    public EmailClassifier() throws Exception {
        // Declare text attribute to hold the message
        Attribute attributeText = new Attribute("text", (List < String > ) null);

        // Declare the label attribute along with its values
        ArrayList < String > classAttributeValues = new ArrayList < > ();
        classAttributeValues.add("spam");
        classAttributeValues.add("ham");
        Attribute classAttribute = new Attribute("label", classAttributeValues);
        
        wekaAttributes = new ArrayList < > ();
        wekaAttributes.add(classAttribute);
        wekaAttributes.add(attributeText);
        
        
        
        data = loadRawDataset("final/train.txt");
        saveArff(data, "final/train.arff");
        
        filter = new StringToWordVector();
        filter.setAttributeIndices("last");

        //add ngram tokenizer to filter with min and max length set to 1
        NGramTokenizer tokenizer = new NGramTokenizer();
        tokenizer.setNGramMinSize(1);
        tokenizer.setNGramMaxSize(1);
        //use word delimeter
        tokenizer.setDelimiters("\\W");
        filter.setTokenizer(tokenizer);

        //convert tokens to lowercase
        filter.setLowerCaseTokens(true);
        

        
       
        classifier = new FilteredClassifier();

        // set Multinomial NaiveBayes as arbitrary classifier
        classifier.setClassifier(new NaiveBayesMultinomial());
        classifier.setFilter(filter);
        classifier.buildClassifier(data);
        
        
   
    }

    public Instances loadRawDataset(String filename) {
        /* 
         *  Create an empty training set
         *  name the relation “Rel”.
         *  set intial capacity of 10*
         */

        Instances dataset = new Instances("spam", wekaAttributes, 10);

        // Set class index
        dataset.setClassIndex(0);

        // read text file, parse data and add to instance
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            for (String line;
                (line = br.readLine()) != null;) {
                // split at first occurance of n no. of words
                String[] parts = line.split("\\s+", 2);

                // basic validation
                if (!parts[0].isEmpty() && !parts[1].isEmpty()) {

                    DenseInstance row = new DenseInstance(2);
                    row.setValue(wekaAttributes.get(0), parts[0]);
                    row.setValue(wekaAttributes.get(1), parts[1]);

                    // add row to instances
                    dataset.add(row);
                }

            }

        } 
        catch (IOException e) {
            LOGGER.warning(e.getMessage());
        } 
        catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.info("invalid row.");
        }
        return dataset;

    }

    public void saveArff(Instances dataset, String filename) {
        try {
            // initialize 
            ArffSaver arffSaverInstance = new ArffSaver();
            arffSaverInstance.setInstances(dataset);
            arffSaverInstance.setFile(new File(filename));
            arffSaverInstance.writeBatch();
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    

    public boolean isSpam(String emailContent) throws Exception {
        Instance instance = new DenseInstance(structure.numAttributes());
        instance.setDataset(structure);
        instance.setValue(structure.attribute("text"), emailContent);

        Instances singleInstance = new Instances(structure, 0);
        singleInstance.add(instance);

        Instances filteredInstance = Filter.useFilter(singleInstance, this.filter);

        double result = classifier.classifyInstance(filteredInstance.firstInstance());

        return result == 1.0;
    }

    public String predict(String text) {
        try {
            // create new Instance for prediction.
            DenseInstance newinstance = new DenseInstance(2);

            //weka demand a dataset to be set to new Instance
            Instances newDataset = new Instances("predictiondata", wekaAttributes, 1);
            newDataset.setClassIndex(0);

            newinstance.setDataset(newDataset);

            // text attribute value set to value to be predicted
            newinstance.setValue(wekaAttributes.get(1), text);

            // predict most likely class for the instance
            double pred = classifier.classifyInstance(newinstance);

            // return original label
            return newDataset.classAttribute().value((int) pred);
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            return null;
        }
    }
    
}
