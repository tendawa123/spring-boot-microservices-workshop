package io.javamicroservice.ratingsdataservice.services;

import java.util.List;

import io.javamicroservice.ratingsdataservice.model.Rating;

public interface RatingService {

	List<Rating> listFindById(long id);

	Rating findById(long id);

	List<Rating> findByRatingId(int id);

	List<Rating> findByUserName(String name);

	void saveRating(Rating Rating);

	void updateRating(Rating Rating);

	void deleteRatingById(long id);

	List<Rating> findAllRatings();

	void deleteAllRatings();

	boolean isRatingExist(Rating Rating);
}
