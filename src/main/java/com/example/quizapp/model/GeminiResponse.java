package com.example.quizapp.model;

import java.util.List;

public class GeminiResponse {
    private List<Candidate> candidates;

    class Candidate {
        private Content content;

        public Content getContent() { return content; }

    }

     class Content {
        private List<Part> parts;

        public List<Part> getParts() { return parts; }


        class Part {
            private String text;

            public String getText() { return text; }
        }

    }



    public List<Candidate> getCandidates() { return candidates; }
}
