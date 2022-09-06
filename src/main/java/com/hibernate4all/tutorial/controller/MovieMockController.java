package com.hibernate4all.tutorial.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock")
public class MovieMockController {

	@Value("classpath:mock/movie-details.json")
	private Resource resourceDetails;

	@Value("classpath:mock/movies-list.json")
	private Resource resourceList;

	@Value("classpath:mock/movies-search-result.json")
	private Resource resourceSearch;

	private String getContent(Resource resource) {
		try (Reader reader = new InputStreamReader(resource.getInputStream(),
				java.nio.charset.StandardCharsets.UTF_8)) {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public String search() {
		String content = getContent(resourceSearch);
		return content;
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAll() {
		String content = getContent(resourceList);
		return content;
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String get(@PathVariable("id") Long id) {
		return getContent(resourceDetails);
	}

}
