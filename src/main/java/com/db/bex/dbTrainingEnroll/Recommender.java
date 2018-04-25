package com.db.bex.dbTrainingEnroll;

import com.db.bex.dbTrainingEnroll.dao.TrainingRepository;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class Recommender {

    public Recommender(TrainingRepository trainingRepository) {
        try {
            DataModel model = new FileDataModel(new File("E:\\date1.csv"));
//            ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
            ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
            CachingRecommender recommender1 = new CachingRecommender(recommender);
            List<RecommendedItem> recommendedItemList = recommender1.recommend(4, 2);
            for (RecommendedItem i : recommendedItemList)
                System.out.println(trainingRepository.findById(i.getItemID()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }
    }
}
