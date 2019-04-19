package io.javamicroservice.ratingsdataservice.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import io.javamicroservice.customerror.CustomError;
import io.javamicroservice.ratingsdataservice.model.Rating;
import io.javamicroservice.ratingsdataservice.services.RatingService;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

	public static final Logger logger = LoggerFactory.getLogger(RatingsResource.class);
	@Autowired
	RatingService ratingService;
	
	@RequestMapping(value = "/ratings", method = RequestMethod.GET)

	public ResponseEntity<List<Rating>> listAllRatings() {
		List<Rating> Ratings = ratingService.findAllRatings();
		if (Ratings.isEmpty()) {
			return new ResponseEntity(new CustomError("Ratings record not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Rating>>(Ratings, HttpStatus.OK);
	}

	/**
	 * 
	 * @param name
	 * @return gets Rating by userName
	 */
	@RequestMapping(value = "/ratingUserName/{userName}", method = RequestMethod.GET)
	public ResponseEntity<List<Rating>> listAllRatingUserName(@PathVariable("userName") String name) {
		logger.info("Fetching Rating with userName {}", name);
		List<Rating> RatingList = ratingService.findByUserName(name);
		if (RatingList.isEmpty()) {
			logger.error("Rating with userName {} not found.", name);
			return new ResponseEntity(new CustomError("Rating with userName " + name + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Rating>>(RatingList, HttpStatus.OK);
	}

	/**
	 * 
	 * @param id
	 * @return gets one Rating by rating id.
	 */
	@RequestMapping(value = "/ratingId/{ratingId}", method = RequestMethod.GET)
	public ResponseEntity<List<Rating>> getRatingById(@PathVariable(value = "ratingId") int ratingId) {
		logger.info("Fetching Rating with id {}", ratingId);
		List<Rating> Rating = ratingService.findByRatingId(ratingId);
		if (Rating.isEmpty()) {
			logger.error("Rating with id {} not found.", ratingId);
			return new ResponseEntity(new CustomError("Rating with id " + ratingId + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Rating>>(Rating, HttpStatus.OK);
	}

	/**
	 * 
	 * @param id
	 * @return gets one Rating by id.
	 */
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Rating>> getById(@PathVariable(value = "id") Long id) {
		logger.info("Fetching Rating with id {}", id);
		List<Rating> Rating = ratingService.listFindById(id);
		if (Rating.isEmpty()) {
			logger.error("Rating with id {} not found.", id);
			return new ResponseEntity(new CustomError("Rating with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Rating>>(Rating, HttpStatus.OK);
	}
	/**
	 * 
	 * @param Rating
	 * @param ucBuilder
	 * @return creates one Rating.
	 * 
	 */
	@RequestMapping(value = "/rating/", method = RequestMethod.POST)
	public ResponseEntity<?> createRating(@RequestBody Rating Rating, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Rating : {}", Rating);
		if (ratingService.isRatingExist(Rating)) {
			logger.error("Unable to create. A Rating with name {} already exist", Rating.getUserName());
			return new ResponseEntity(
					new CustomError("Unable to create. A Rating with name " + Rating.getUserName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		ratingService.saveRating(Rating);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/Rating/{id}").buildAndExpand(Rating.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param id
	 * @param Rating
	 * @return updates one Rating.
	 */
	@RequestMapping(value = "/rating/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateRatingById(@PathVariable("id") long id, @RequestBody Rating Rating) {
		logger.info("Updating Rating with id {}", id);
		Rating currentRating = ratingService.findById(id);
		if (currentRating == null) {
			logger.error("Unable to update. Rating with id {} not found.", id);
			return new ResponseEntity(new CustomError("Unable to upate. Rating with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		currentRating.setMovieId(Rating.getMovieId());
		currentRating.setRating(Rating.getRating());
		currentRating.setUserName(Rating.getUserName());
		ratingService.updateRating(currentRating);
		return new ResponseEntity<Rating>(currentRating, HttpStatus.OK);
	}

	/**
	 * 
	 * @param id
	 * @return deletes one Rating.
	 */
	@RequestMapping(value = "/rating/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRatingById(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Rating with id {}", id);
		Rating Rating = ratingService.findById(id);
		if (Rating == null) {
			logger.error("Unable to delete. Rating with id {} not found.", id);
			return new ResponseEntity(new CustomError("Unable to delete Rating with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		ratingService.deleteRatingById(id);
		return new ResponseEntity<Rating>(HttpStatus.NO_CONTENT);
	}
	/**
	 * 
	 * @return deletes all Ratings.
	 */
	@RequestMapping(value = "/ratings/", method = RequestMethod.DELETE)
	public ResponseEntity<Rating> deleteAllRatings() {
		logger.info("Deleting All Ratings");
		ratingService.deleteAllRatings();
		return new ResponseEntity<Rating>(HttpStatus.NO_CONTENT);
	}

}
