package net.safedata.java.advanced.training.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImperativeVsFunctionalProgramming {

    public static void main(String[] args) {
        List<String> aListOfWords = Arrays.asList("A simple list of words".split(" "));

        List<String> wordsShorterThan3Chars = imperativeProcessing(aListOfWords);
        wordsShorterThan3Chars = functionalProcessing(aListOfWords);
    }

    private static List<String> functionalProcessing(List<String> aListOfWords) {
        return aListOfWords.stream()
                           .filter(word -> word.length() <= 3)
                           .collect(Collectors.toList());
    }

    private static List<String> imperativeProcessing(List<String> aListOfWords) {
        List<String> returnedList = new ArrayList<>();
        for (String word : aListOfWords) {
            if (word.length() <= 3) {
                returnedList.add(word);
            }
        }
        return returnedList;
    }
}
