
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.GenericUserSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;


import org.apache.mahout.cf.taste.hadoop.item.RecommenderJob;
import org.apache.mahout.cf.taste.impl.recommender.knn.KnnItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.knn.Optimizer;
import org.apache.mahout.cf.taste.impl.recommender.knn.ConjugateGradientOptimizer;

public class Item {

    public static void main(String[] args) throws IOException, TasteException {
        // TODO Auto-generated method stub
        System.out.println("ITEM Based recommendation system");
        //DataModel model = new FileDataModel(new File("ratings-1.csv")); //original file

        DataModel model = new FileDataModel(new File("ratings-1_NormalizedSpecialCSV.csv"));// Normalized file
        //ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
        //ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
        ItemSimilarity similarity = new TanimotoCoefficientSimilarity(model);
        //ItemSimilarity similarity = new EuclideanDistanceSimilarity(model);


        int neighbours = 15;
        System.out.println( "Neighbours = "+neighbours);
        System.out.println( "Similarity = "+similarity.toString());

        Optimizer optimizer=new ConjugateGradientOptimizer();
        final ItemBasedRecommender recommender = new KnnItemBasedRecommender(model,similarity,optimizer,neighbours);

        List<RecommendedItem> recommendations = recommender.recommend(200, 3);
        for (RecommendedItem recommendation : recommendations) {
            System.out.println("recommendation : "+recommendation);
        }


        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel model) throws TasteException {
                ItemSimilarity similarity = new EuclideanDistanceSimilarity(model);
                //Optimizer optimizer = new NonNegativeQuadraticOptimizer();
                return new GenericItemBasedRecommender(model,similarity);
            }
        };


        RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);
        System.out.println("RMSE: " + score);

        RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
        IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, model, null, 10, 0.1, 0.7); // evaluate precision recall at 10

        System.out.println("Precision: " + stats.getPrecision());
        System.out.println("Recall: " + stats.getRecall());
        System.out.println("F1 Score: " + stats.getF1Measure());


    }
}
