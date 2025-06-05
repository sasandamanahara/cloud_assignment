import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class ACBrandRatingReducer extends Reducer<Text, Text, Text, Text> {
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int totalRatings = 0;
        double totalScore = 0.0;

        for (Text val : values) {
            String[] parts = val.toString().split(",");
            if (parts.length != 2)
                continue;

            double rating = Double.parseDouble(parts[0]);
            int count = Integer.parseInt(parts[1]);

            totalRatings += count;
            totalScore += rating * count;
        }

        double avgRating = totalRatings == 0 ? 0 : totalScore / totalRatings;
        context.write(key,
                new Text("Average Rating: " + String.format("%.2f", avgRating) + ", Total Ratings: " + totalRatings));
    }
}
