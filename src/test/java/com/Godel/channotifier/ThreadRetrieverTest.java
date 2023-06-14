package com.Godel.channotifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ThreadRetrieverTest {
	
    String jsonStr;

    @BeforeEach
    public void setUp() throws Exception {
        jsonStr = new String(Files.readAllBytes(Paths.get(getClass().getResource("/testthreads.json").toURI())));
    }
    
    @Test
    @DisplayName("Print out all the thread numbers")
    public void testThreadRetriever() throws Exception {
    	List<Integer> expectedThreadNumbers = Arrays.asList(570368, 612621, 612288, 612000, 612334, 612658, 547797, 612692, 612573, 609884, 611378, 561868, 539494, 609287, 612691, 599079, 609711, 611311, 591172, 609611, 603852, 612675, 612122, 610792, 569063, 612645, 610210, 609363, 597408, 612327, 610858, 609254, 610633, 610937, 610850, 610333, 610834, 612407, 566458, 611036, 612594, 612482, 610964, 610570, 610972, 610550, 608740, 612498, 612446, 574188, 612353, 612203, 612109, 604897, 547093, 612366, 609903, 611728, 587263, 593161, 603976, 612044, 605122, 611835, 609663, 612206, 610545, 609072, 607829, 601798, 612118, 605203, 582941, 612046, 610368, 609376, 609957, 610609, 610147, 611307, 609615, 608674, 601225, 610083, 608932, 609415, 609534, 604077, 610638, 611732, 611867, 611734, 611872, 609780, 608299, 611813, 602191, 608951, 611790, 608633, 610086, 611714, 569358, 611094, 611086, 572890, 609961, 609763, 608834, 609414, 546816, 609650, 611654, 601036, 596701, 611591, 611100, 546973, 611402, 610864, 609649, 605725, 609743, 609608, 611241, 609748, 608789, 610831, 610467, 607016, 609568, 609760, 611290, 611130, 597271, 609556, 609520, 609416, 611095, 608395, 609336, 609340, 609122, 609041, 605538, 604751, 610629, 611118, 609324, 610320, 611028, 608668);
    	ThreadRetriever threadRetriever = new ThreadRetriever();
    	List<Integer> actualThreadNumbers = threadRetriever.retrieveThreadNumbers(jsonStr);
//    	for (int num : actualThreadNumbers) {
//    		System.out.println("Thread number: " + num);
//    	}
    	assertEquals(expectedThreadNumbers,actualThreadNumbers);
    }

}
