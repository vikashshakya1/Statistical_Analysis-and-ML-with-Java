package com.example.model;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.classifiers.Evaluation;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.Filter;
import java.io.File;
public class RandomForestModel {
       public static void main(String[] args) throws Exception {
        // Load CSV file
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("src/main/resources/Financials.csv"));
        Instances data = loader.getDataSet();
        // Convert numeric class to nominal
        NumericToNominal convert = new NumericToNominal();
        convert.setInputFormat(data);
        Instances newData = Filter.useFilter(data, convert);
        // Set class index for the target variable (profit)
        newData.setClassIndex(newData.numAttributes() - 1);
        // Initialize and build the classifier (RandomForest)
        RandomForest rf = new RandomForest();
        rf.buildClassifier(newData);
        // Evaluate the model
        Evaluation eval = new Evaluation(newData);
        eval.evaluateModel(rf, newData);
        // Print the evaluation results
        System.out.println(eval.toSummaryString());
        // Print the accuracy for each instance and interpret as more or less profit
        System.out.println("Instance Predictions:");
        for (int i = 0; i < newData.numInstances(); i++) {
            double actual = newData.instance(i).classValue();
            double predicted = rf.classifyInstance(newData.instance(i));
            // Interpret prediction as more profit (1) or less profit (0)
            String profitLabel = predicted == 1 ? "More Profit" : "Less Profit";  
            System.out.println("Instance " + (i + 1) + ": Actual=" + actual + ", Predicted=" + predicted + " (" + profitLabel + ")");
        }
    }
}
