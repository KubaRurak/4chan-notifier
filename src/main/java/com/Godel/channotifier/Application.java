package com.Godel.channotifier;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.Godel.channotifier.entity.Post;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Profile("command-line")
@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private static final Logger logger = LogManager.getLogger(Application.class);

	@Scheduled(fixedRate = 3000000) // Run every 30 minutes
	public void runApplication() {

		String boardName = "biz";
		List<String> userKeywords = List.of("airdrop", "airdropped");

//    	String boardName = "a";
//    	List<String> userKeywords = List.of("anime");

		String threadListJsonString = "https://a.4cdn.org/" + boardName + "/threads.json";
		String csvFilePath = "src/main/resources/data/myposts-" + boardName + ".csv";
		
		String jsonPostsString;
		
		List<String> allJsonPostStrings = new ArrayList<>();
		
		
		List<Post> allMatchingPosts = new ArrayList<>();
		List<Post> newMatchingPosts = new ArrayList<>();

		Set<Integer> seenPostNumbers = new HashSet<>();
		boolean csvFileExists = Files.exists(Paths.get(csvFilePath));

		if (csvFileExists) {
			logger.info("Opening CSV file in filepath: {}", csvFilePath);
			CSVUtils csvUtilsRead = new CSVUtils();
			try {
				csvUtilsRead.getPostNumbersFromCSV(csvFilePath, seenPostNumbers);
			} catch (CsvValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
		try {
			String jsonThreadsStr = JsonUtils.urlToJsonString(threadListJsonString);
			ThreadRetriever threadRetriever = new ThreadRetriever();
			List<Integer> threadList = threadRetriever.retrieveThreadNumbers(jsonThreadsStr);
			for (Integer threadNumber : threadList) {
				logger.info("Retrieved thread number: {}", threadNumber);
			}

			
			
			
			
			
			for (Integer threadNumber : threadList) {
				String threadUrl = "https://a.4cdn.org/" + boardName + "/thread/" + threadNumber + ".json";
				jsonPostsString = JsonUtils.urlToJsonString(threadUrl);
				if (jsonPostsString != null) {
					newMatchingPosts = PostParser.findPostsWithKeywords(jsonPostsString, userKeywords, seenPostNumbers);
					PostParser.addPostInfo(newMatchingPosts, boardName, threadNumber);
					allMatchingPosts.addAll(newMatchingPosts);
				} else {
					logger.warn("Thread {} doesn't exist or couldn't be retrieved.", threadNumber);
				}
			}
			if (!allMatchingPosts.isEmpty()) {
				logger.info("Saving found comments and links to a CSV file...");
				CSVUtils csvUtils = new CSVUtils();
				csvUtils.savePostsToCSV(allMatchingPosts, csvFilePath);
				logger.info("Saving complete");

			} else {
				logger.info("No comments found with the specified keywords.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("An error occurred while retrieving the JSON data: " + e.getMessage());
		}
	}
}
