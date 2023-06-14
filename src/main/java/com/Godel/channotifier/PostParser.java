package com.Godel.channotifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.Godel.channotifier.entity.Post;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PostParser {

	private static final Logger logger = LogManager.getLogger(Application.class);

	private static final Map<String, Pattern> keywordPatterns = new HashMap<>();

	public static boolean containsKeyword(String str, List<String> keywords) {
		if (str == null || keywords == null) {
			return false;
		}
		str = str.replaceAll("\\<.*?\\>", "");
		for (String keyword : keywords) {
			Pattern pattern = keywordPatterns.get(keyword);
			if (pattern == null) {
				pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
				keywordPatterns.put(keyword, pattern);
			}
			if (pattern.matcher(str).find()) {
				return true;
			}
		}
		return false;
	}

	public static List<Post> findPostsWithKeywords(String jsonStr, List<String> keywords, Set<Integer> seenNumbers)
			throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonStr);
		JsonNode postsNode = rootNode.path("posts");
		List<Post> matchingPosts = new ArrayList<>();
		for (JsonNode postNode : postsNode) {
			String postComment = postNode.path("com").asText();
			if (containsKeyword(postComment, keywords)) {
				Integer postNumber = Integer.valueOf(postNode.path("no").asText());
				if (!seenNumbers.contains(postNumber)) {
					Post newPost = new Post(postNumber, postComment);
					matchingPosts.add(newPost);
					logger.info("Found a match in post number " + postNumber);
					logger.info("Post:" + postComment);
				} else {
					logger.info("Post number " + postNumber + " was already found");
				}
			}
		}
		return matchingPosts;
	}

	public static List<Post> addPostInfo(List<Post> posts, String boardCode, Integer threadNumber) {
		posts.forEach(post -> {
			post.setBoardCode(boardCode);
			post.setThreadNumber(threadNumber);
			post.setPostUrl();
		});
		return posts;
	}

	public static List<Post> findPostsWithKeywords(String jsonStr, List<String> keywords) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonStr);
		JsonNode postsNode = rootNode.path("posts");
		List<Post> matchingPosts = new ArrayList<>();
		for (JsonNode postNode : postsNode) {
			String comment = postNode.path("com").asText();
			if (containsKeyword(comment, keywords)) {
				String postNumber = postNode.path("no").asText();
				matchingPosts.add(new Post(Integer.valueOf(postNumber), comment));
			}
		}
		return matchingPosts;
	}

	public static List<String> findNoWithKeywords(String jsonStr, List<String> keywords) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonStr);
		JsonNode postsNode = rootNode.path("posts");
		List<String> matchingNos = new ArrayList<>();
		for (JsonNode postNode : postsNode) {
			String comment = postNode.path("com").asText();
			if (containsKeyword(comment, keywords)) {
				matchingNos.add(postNode.path("no").asText());
			}
		}
		return matchingNos;

	}

}
