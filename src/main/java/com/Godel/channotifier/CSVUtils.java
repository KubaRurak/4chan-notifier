package com.Godel.channotifier;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.Godel.channotifier.entity.Post;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvValidationException;


public class CSVUtils {
	

    public void savePostsToCSV(List<Post> posts, String filePath) {

        boolean fileExists = Files.exists(Paths.get(filePath));

        try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter(filePath, true))
                .withSeparator('\t')
                .withQuoteChar('\'')
                .build()) {
            // Write header
            if (!fileExists) {
                String[] header = {"Board", "Thread Number", "Post Number", "Comment", "Post URL", "Added at"};
                writer.writeNext(header);
            }

            LocalDateTime dateTime = LocalDateTime.now();
            for (Post post : posts) {
                String[] data = {
                        post.getBoardCode(),
                        post.getThreadNumber().toString(),
                        post.getPostNumber().toString(),
                        replaceSpecialChars(post.getComment()),
                        post.getPostUrl(),
                        dateTime.toString()
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void getPostNumbersFromCSV(String filePath, Set<Integer> postNumbers) throws CsvValidationException {
    	
        if (Files.exists(Paths.get(filePath))) {
            try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                    .withCSVParser(new CSVParserBuilder()
                            .withQuoteChar('\'')
                            .withSeparator('\t')
                            .build())
                    .build()) {
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    // Assuming the post number is stored in the third column
                    String postNumberStr = nextLine[2];
                    try {
                        int postNumber = Integer.parseInt(postNumberStr);
                        postNumbers.add(postNumber);
                    } catch (NumberFormatException e) {
                        // Ignore invalid post numbers
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String replaceSpecialChars(String text) {
        return text.replace("&gt;&gt;", ">>").replace("&#039;", "'");
    }
}
