package io.javamicroservice.moviecatalogservice.resources;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

	public static final Logger logger = LoggerFactory.getLogger(CatalogResource.class);
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	WebClient.Builder webClientBuilder;

	@RequestMapping("/catalogByUserId/{userId}")
	public String getCatalogByUserIds(@PathVariable("userId") String userId) {
		JSONArray jsonArrayFinal = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		final String uri = "http://pc344639.cts.com:8083/ratingsdata/ratingUserName/" + userId;
		final String movieInfoUri = "http://pc344639.cts.com:8082/movies/";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		try {
			JSONArray jsonArray = new JSONArray(result.getBody().toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				ResponseEntity<String> resultAll = restTemplate.exchange(movieInfoUri + object.getString("movieId"),
						HttpMethod.GET, entity, String.class);
				JSONObject jsObjectFinal = new JSONObject(resultAll.getBody().toString());
				jsonObject.put("movieName", jsObjectFinal.getString("name"));
				jsonObject.put("movieId", jsObjectFinal.getString("movieId"));
				jsonObject.put("UserName", object.getString("userName"));
				jsonObject.put("Rating", object.getString("rating"));
				jsonObject.put("movieDescription", jsObjectFinal.getString("description"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArrayFinal.put(jsonObject).toString();
	}

	@RequestMapping("/catalogByMovieId/{movieId}")
	public String getCatalogByMovieIds(@PathVariable("movieId") String movieId) {
		JSONArray jsonArrayFinal = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		final String uri = "http://pc344639.cts.com:8083/ratingsdata/ratingMovieId/" + movieId;
		final String movieInfoUri = "http://pc344639.cts.com:8082/movies/";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		try {
			JSONArray jsonArray = new JSONArray(result.getBody().toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				ResponseEntity<String> resultAll = restTemplate.exchange(movieInfoUri + object.getString("movieId"),
						HttpMethod.GET, entity, String.class);
				JSONObject jsObjectFinal = new JSONObject(resultAll.getBody().toString());
				jsonObject.put("movieName", jsObjectFinal.getString("name"));
				jsonObject.put("movieId", jsObjectFinal.getString("movieId"));
				jsonObject.put("UserName", object.getString("userName"));
				jsonObject.put("Rating", object.getString("rating"));
				jsonObject.put("movieDescription", jsObjectFinal.getString("description"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArrayFinal.put(jsonObject).toString();
	}
}
