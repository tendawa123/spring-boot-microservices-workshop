package io.javamicroservice.ratingsdataservice.services;

import static org.mockito.Mockito.CALLS_REAL_METHODS;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.javamicroservice.ratingsdataservice.model.Rating;
import io.javamicroservice.ratingsdataservice.resources.RatingsResource;
@Service("RatingService")
public class RatingServiceImpl implements RatingService {

	public static final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);
	private static final AtomicLong counter = new AtomicLong();
	private static List<Rating> ratings;
	static {
		ratings = populateDummyRatings();
	}

	@Override
	public List<Rating> listFindById(long id) {
		List<Rating> ratingList = new ArrayList<Rating>();
		for (Rating rating : ratings) {
			Long ratings = new Long(rating.getId());
			Long ratingId = new Long(id);
			if (ratings.equals(ratingId)) {
				ratingList.add(rating);
			}
		}
		return ratingList;
	}

	@Override
	public Rating findById(long id) {
		for (Rating rating : ratings) {
			if (rating.getId() == id) {
				return rating;
			}
		}
		return null;
	}

	@Override
	public List<Rating> findByRatingId(int ratingId) {
		List<Rating> RatingList = new ArrayList<Rating>();
		for (Rating rating : ratings) {
			if (rating.getRating()==ratingId) {
				RatingList.add(rating);
			}
		}
		return RatingList;
	}

	@Override
	public List<Rating> findByUserName(String name) {
		List<Rating> RatingList = new ArrayList<Rating>();
		for (Rating Rating : ratings) {
			if (Rating.getUserName().equalsIgnoreCase(name)) {
				RatingList.add(Rating);
			}
		}
		return RatingList;
	}

	@Override
	public void saveRating(Rating Rating) {
		Rating.setId(counter.incrementAndGet());
		ratings.add(Rating);

	}

	@Override
	public void updateRating(Rating Rating) {
		int index = ratings.indexOf(Rating);
		ratings.set(index, Rating);
	}

	@Override
	public void deleteRatingById(long id) {
		for (Iterator<Rating> iterator = ratings.iterator(); iterator.hasNext();) {
			Rating rating = iterator.next();
			if (rating.getId() == id) {
				iterator.remove();
			}
		}

	}

	@Override
	public List<Rating> findAllRatings() {
		return ratings;
	}

	@Override
	public void deleteAllRatings() {
		ratings.clear();
	}

	@Override
	public boolean isRatingExist(Rating Rating) {
		return findByUserName(Rating.getUserName()) != null;
	}

	private static List<Rating> populateDummyRatings() {
		List<Rating> Ratings = new ArrayList<Rating>();
		Ratings.add(new Rating(counter.incrementAndGet(), "100", 3, "John"));
		Ratings.add(new Rating(counter.incrementAndGet(), "200", 4, "Doe"));
		Ratings.add(new Rating(counter.incrementAndGet(), "400", 3, "Casanas"));
		Ratings.add(new Rating(counter.incrementAndGet(), "500", 5, "Marvel"));
		Ratings.add(new Rating(counter.incrementAndGet(), "600", 2, "Rahul"));
		Ratings.add(new Rating(counter.incrementAndGet(), "700", 1, "Jain"));
		return Ratings;
	}

}
