package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // vaihda oma opiskelijanumerosi seuraavaan, ÄLÄ kuitenkaan laita githubiin omaa opiskelijanumeroasi
        String studentNr = "014456446";
        if (args.length > 0) {
            studentNr = args[0];
        }

        String url = "http://ohtustats2017.herokuapp.com/students/" + studentNr + "/submissions";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        
        String url2 = "http://ohtustats2017.herokuapp.com/courses/1.json";

        String bodyText2 = Request.Get(url2).execute().returnContent().asString();

//        System.out.println(bodyText);
//        System.out.println(bodyText2);

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        Course course = mapper.fromJson(bodyText2, Course.class);
        
        System.out.println(course);
        addMaximumsToSubmission(course.getWeeksAsArray(), subs);

        System.out.println("Student number: " + subs[0].getStudent_number());
        int hoursTotal = 0;
        
        for (Submission submission : subs) {
            System.out.println(submission);
            hoursTotal += submission.getHours();
        }
        System.out.println("\n Total time spent: " + hoursTotal);
    }

    private static void addMaximumsToSubmission(String[] weeksAsArray, Submission[] subs) {
        for (int i = 0; i < weeksAsArray.length && i < subs.length; i++) {
            String max = weeksAsArray[i];
            subs[i].setMaxPoints(max);
            
        }
    }
    
    
}
