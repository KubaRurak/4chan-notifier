package com.Godel.channotifier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.opencsv.exceptions.CsvValidationException;

class CSVUtilsTest {

    @Test
    void testGetPostNumbersFromCSV() {
        String filePath = "src/main/resources/testposts.csv";
        Set<Integer> postNumbers = new HashSet<>();  
        CSVUtils csvUtils = new CSVUtils();
        boolean fileExists = Files.exists(Paths.get(filePath));
        System.out.println(fileExists);
        
        try {
        	csvUtils.getPostNumbersFromCSV(filePath, postNumbers);
        	if (postNumbers.isEmpty()) {System.out.println("It's empty");} else {System.out.println("not empty");}
            assertTrue(postNumbers.contains(55003874));
            assertTrue(postNumbers.contains(55007022));
            assertFalse(postNumbers.contains(789)); // Non-existing post number
            
        } catch (CsvValidationException e) {
            fail("Exception occurred: " + e.getMessage());
        }

    }

}
