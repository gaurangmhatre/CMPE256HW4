
import java.io.*;
import java.util.*;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.neighborhood.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;

public class UserBasedRecommendationSystem {

    public static void main(String[] args) {
        try{


            //Step 1:- Input CSV file (CSV file should be in userID, itemID, preference) format

            //DataModel dm = new FileDataModel(new File("filePath"));

            //Step 2:- Create UserSimilarity or ItemSimilarity Matrix

            //Step 3:- Create UserNeighbourHood object. (No Need to create ItemNeighbourHood object while creating
            //Item based Recommendation)

            //Step 4:- Create object of UserBasedRecommender or ItemBasedRecommender

            //Step 5:- Call the Generated Recommender in previous step to getting
            //recommendation for particular user or Item


        }
        catch (Exception e) {
            System.out.println("There was an error.");
            e.printStackTrace();
        }

    }

}
