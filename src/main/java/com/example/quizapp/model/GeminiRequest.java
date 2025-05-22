package com.example.quizapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeminiRequest {

    private ArrayList<Content> contents;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content{
        private ArrayList<Part> parts;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Part{
        private String text;
    }


}
