package com.Godel.channotifier;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.Godel.channotifier.entity.Post;

class PostParserTest {
	
    String jsonStr;

    @BeforeEach
    public void setUp() throws Exception {
        jsonStr = new String(Files.readAllBytes(Paths.get(getClass().getResource("/testposts.json").toURI())));
    }
    
    @DisplayName("Testing finding the whole post with keywords")
    @Nested    
    class testFindPostWithKeywords{
	    @Test
	    @DisplayName("One match case sensitive")
	    public void testFindPostWithKeywords1() throws Exception {
	        List<String> keywords = Arrays.asList("rally");
	        List<Post> expectedMatchingPosts = new ArrayList<>();
	        expectedMatchingPosts.add(new Post(54902800, "aaa bbb rally ccc"));
	        List<Post> actualMatchingPosts = PostParser.findPostsWithKeywords(jsonStr, keywords);
	        for (Post post : actualMatchingPosts) {
	            System.out.println("Post Number: " + post.getPostNumber());
	            System.out.println("Comment: " + post.getComment());
	        }
	        assertEquals(expectedMatchingPosts, actualMatchingPosts);
	    }
    }
    
    @DisplayName("Testing finding post number with keywords")
    @Nested
    class testFindNoWithKeywords{
	    @Test
	    @DisplayName("One match case sensitive")
	    public void testFindNoWithKeywords1() throws Exception {
	        List<String> keywords = Arrays.asList("rally");
	        List<String> expectedMatchingNos = Arrays.asList("54902800");
	        List<String> actualMatchingNos = PostParser.findNoWithKeywords(jsonStr, keywords);
	        assertEquals(expectedMatchingNos, actualMatchingNos);
	    }
	    @Test
	    @DisplayName("One match case insensitive")
	    public void testFindNoWithKeywords2() throws Exception {
	        List<String> keywords = Arrays.asList("RALLY");
	        List<String> expectedMatchingNos = Arrays.asList("54902800");
	        List<String> actualMatchingNos = PostParser.findNoWithKeywords(jsonStr, keywords);
	        assertEquals(expectedMatchingNos, actualMatchingNos);
	    }
	    @Test
	    @DisplayName("Two matches")
	    public void testFindNoWithKeywords3() throws Exception {
	        List<String> keywords = Arrays.asList("home");
	        List<String> expectedMatchingNos = Arrays.asList("54902910","54902918");
	        List<String> actualMatchingNos = PostParser.findNoWithKeywords(jsonStr, keywords);
	        assertEquals(expectedMatchingNos, actualMatchingNos);
	    }
	    @Test
	    @DisplayName("No match")
	    public void testFindNoWithKeywords4() throws Exception {
	        List<String> keywords = Arrays.asList("Anonymous");
	        List<String> expectedMatchingNos = Arrays.asList();
	        List<String> actualMatchingNos = PostParser.findNoWithKeywords(jsonStr, keywords);
	        assertEquals(expectedMatchingNos, actualMatchingNos);
	    }
    }

}
