package io.javamicroservice.ratingsdataservice.model;

public class Rating {

	private long id;
    private String movieId;
    private int rating;
    private String userName;
    
    public Rating() {
		id=0;
	}
    public Rating(long id,String movieId, int rating,String userName) {
    	this.id = id;
        this.movieId = movieId;
        this.rating = rating;
        this.userName=userName;
    }

    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
