package com.example.quizapp.service;

import com.example.quizapp.model.GeminiRequest;
import com.example.quizapp.model.GeminiResponse;
import com.example.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeminiService {

    // pass the proper prompt for analysis, to pass (Qns , responses as sting, format of the response we want !)



    @Autowired
    @Lazy
    QuizService quizService;

    @Autowired
    RestTemplate restTemplate;


    private static final String api_url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=GEMINI_API_KEY";
    private static final String api_key = "YOUR-GEMINI-API-KEY";


    public ResponseEntity<String> analyseQuiz(Integer id, List<Response> responses) {

        String final_api_url = api_url
                .replace("GEMINI_API_KEY",api_key);

        // Build the request

        String qnsData = quizService.getQuizQuestionsFromDB(id).toString();
        String qnsResponseData = responses.toString();
        String analysisFormat = " Here are the quiz questions, user's answers, and correct answers. Analyze the quiz performance and respond in the following structured and user-friendly format (as plain text):\n" +
                "\n" +
                "\uD83C\uDFAF Quiz Performance Analysis\n" +
                "\n" +
                "\uD83D\uDCCA 1. Summary\n" +
                "- \uD83E\uDDEE Total Questions: X\n" +
                "- ✅ Correct Answers: Y\n" +
                "- ❌ Wrong Answers: Z\n" +
                "- \uD83D\uDCC8 Score: Y / X\n" +
                "- \uD83C\uDF1F Overall Performance: [Excellent / Good / Average / Poor]\n" +
                "\n" +
                "\uD83D\uDCDA 2. Topic-wise Analysis\n" +
                "- \uD83D\uDCCC [Topic 1]: X correct out of Y questions\n" +
                "- \uD83D\uDCCC [Topic 2]: X correct out of Y questions\n" +
                "...\n" +
                "\n" +
                "\uD83D\uDCDD 3. Detailed Feedback\n" +
                "- Q1: [Correct/Incorrect] – [Brief feedback, e.g., \"Well done.\" or \"Misunderstood the concept of angles.\"]\n" +
                "- Q2: ...\n" +
                "...\n" +
                "\n" +
                "\uD83D\uDCA1 4. Suggestion\n" +
                "- Based on the performance, provide a brief and helpful suggestion to improve. For example: \"Revise Algebra concepts and practice timed quizzes.\"\n" +
                "\n" +
                "Please format the response as plain text using the sections and emojis exactly as above.\n ";

        String analysisFormatExample = " \uD83D\uDCCA Quiz Performance Summary\n" +
                "----------------------------\n" +
                "\uD83E\uDDEE Total Questions: 10\n" +
                "✅ Correct Answers: 7\n" +
                "❌ Wrong Answers: 3\n" +
                "\uD83D\uDCC8 Score: 7 / 10\n" +
                "\uD83C\uDF1F Overall Performance: Good\n" +
                "\n" +
                "\uD83D\uDCDA Topic-wise Analysis\n" +
                "----------------------------\n" +
                "\uD83D\uDCCC Algebra: 3 correct out of 4\n" +
                "\uD83D\uDCCC Geometry: 2 correct out of 3\n" +
                "\uD83D\uDCCC Arithmetic: 2 correct out of 3\n" +
                "\n" +
                "\uD83D\uDCDD Detailed Feedback\n" +
                "----------------------------\n" +
                "Q1: ✅ Correct. Well done!\n" +
                "Q2: ❌ Incorrect. Missed applying the formula.\n" +
                "Q3: ✅ Correct. Logical thinking was on point.\n" +
                "\n" +
                "\uD83D\uDCA1 Suggestion\n" +
                "----------------------------\n" +
                "Revise Geometry basics and improve accuracy on formulas. Practice more timed quizzes to boost speed.\n ";

        String customPrompt = qnsData + qnsResponseData + analysisFormat + analysisFormatExample ;


        GeminiRequest.Part part = new GeminiRequest.Part(customPrompt);
        ArrayList<GeminiRequest.Part> partArrayList = new ArrayList<>();
        partArrayList.add(part);

        GeminiRequest.Content content = new GeminiRequest.Content(partArrayList);
        ArrayList<GeminiRequest.Content> contentArrayList = new ArrayList<>();
        contentArrayList.add(content);

        GeminiRequest request = new GeminiRequest();
        request.setContents(contentArrayList);

        // setting http headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        // setup the request entity
        HttpEntity<GeminiRequest> entity = new HttpEntity<>(request,headers);

        // seding the request

        ResponseEntity<GeminiResponse> responseEntity = restTemplate.exchange(
                final_api_url,
                HttpMethod.POST,
                entity,
                GeminiResponse.class
        );

        if(responseEntity.getStatusCode() == HttpStatus.OK){

            String MSG =responseEntity.getBody()
                    .getCandidates()
                    .get(0)
                    .getContent()
                    .getParts()
                    .get(0)
                    .getText();

            return new ResponseEntity<>(MSG,HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed" , HttpStatus.NOT_FOUND);

    }

}
