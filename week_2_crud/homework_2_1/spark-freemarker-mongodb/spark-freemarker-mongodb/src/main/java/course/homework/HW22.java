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
