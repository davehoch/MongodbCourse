package course.homework;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class HW22 {

	public static void main(String[] args) {
		// do1();
		do31();
	}

	public static void do31() {
		try (MongoClient client = new MongoClient()) {
			MongoDatabase database = client.getDatabase("school");
			MongoCollection<Document> collection = database.getCollection("students");

			List<Document> results
					= collection.find()
					.sort(Sorts.orderBy(Sorts.ascending("_id")))
					.into(new ArrayList<Document>());

			for (Document doc : results) {
				Object id = doc.get("_id");
				List<Document> scores = (List<Document>) doc.get("scores");
				Double lowestScore = null;
				int indexOfLowest = -1;
				int index = 0;
				for (Document score : scores) {
					if (score.getString("type").equals("homework")) {
						Double grade = score.getDouble("score");
						if (lowestScore == null || grade < lowestScore) {
							lowestScore = grade;
							indexOfLowest = index;
						}
					}
					index++;
				}
				if (indexOfLowest != -1) {
					System.out.println(scores.get(indexOfLowest));
					scores.remove(indexOfLowest);
//					collection.updateOne(Filters.eq("_id", id), doc);
					collection.findOneAndReplace(Filters.eq("_id", id), doc);
				}
				//collection.updateOne(doc, doc)

//				Document newScores = new Document().;
//				for (Document score : scores) {
//					if (score.getString("type").equals("homework")) {
//						Long grade = score.getLong("score");
//						if (lowestScore == null || grade < lowestScore) {
//							lowestScore = grade;
//						}
//					}
//					else
//						newScores.append(key, score)
				}

			}

			//14, 21, 60
			// 50906d7fa3c412bb040eb579
			// 50906d7fa3c412bb040eb57d
			// 50906d7fa3c412bb040eb581
//			Integer lastStudentId = -1;
//			for (Document result : results) {
//				Integer studentId = result.getInteger("student_id");
//				if (!studentId.equals(lastStudentId)) {
//					ObjectId rowId = result.getObjectId("_id");
//					collection.deleteOne(Filters.eq("_id", rowId));
//					lastStudentId = studentId;
//				}
//			}
	}

	public static void do1() {
		try (MongoClient client = new MongoClient()) {
			MongoDatabase database = client.getDatabase("students");
			MongoCollection<Document> collection = database.getCollection("grades");

			List<Document> results
					= collection.find(Filters.eq("type", "homework"))
					.sort(Sorts.orderBy(Sorts.ascending("student_id"), Sorts.ascending("score")))
					.into(new ArrayList<Document>());

			//14, 21, 60
			// 50906d7fa3c412bb040eb579
			// 50906d7fa3c412bb040eb57d
			// 50906d7fa3c412bb040eb581
			Integer lastStudentId = -1;
			for (Document result : results) {
				Integer studentId = result.getInteger("student_id");
				if (!studentId.equals(lastStudentId)) {
					ObjectId rowId = result.getObjectId("_id");
					collection.deleteOne(Filters.eq("_id", rowId));
					lastStudentId = studentId;
				}
			}
		}
	}
}
