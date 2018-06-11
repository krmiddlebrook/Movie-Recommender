package movieRecommender;

import java.util.Iterator;

/** UserNode. The class represents a node in the UsersList.
 *  Stores a userId, a list of ratings of type MovieRatingsList,
 *  and a reference to the "next" user in the list.
 *  FILL IN CODE in methods getFavoriteMovies and getLeastFavoriteMovies
 *
 *  Do not modify signatures of methods.
 *  */
public class UserNode {
    private int userId;
    private MovieRatingsList movieRatings;
    private UserNode nextUser;

    /**
     * A constructor for the UserNode.
     *
     * @param id User id
     */
    public UserNode(int id) {
        userId = id;
        movieRatings = new MovieRatingsList();
        nextUser = null;
    }

    /**
     * Getter for the next reference
     *
     * @return the next node in the linked list of users
     */
    public UserNode next() {
        return nextUser;
    }

    /**
     * Setter for the next reference
     *
     * @param anotherUserNode A user node
     */
    public void setNext(UserNode anotherUserNode) {
        this.nextUser = anotherUserNode;
    }

    /**
     * Return a userId stored in this node
     */
    public int getId() {
        return userId;
    }

    /**
     * Print info contained in this node:
     * userId and a list of ratings.
     * Expected format: (userid) movieId:rating; movieId:rating; movieId:rating;
     */
    public void print() {
        System.out.print("(" + userId + ") ");
        movieRatings.print();

    }

    /**
     * Return a string of info contained in this node:
     * userId and a list of ratings.
     * Expected format: (userId) movieId:rating; movieId:rating; movieId:rating
     */
    public String toString() {
        String user = ("(" + userId + ") " + movieRatings.toString());
        return user;
    }


    /**
     * Add rating info for a given movie to the MovieRatingsList
     * for this user node
     *
     * @param movieId id of the movie
     * @param rating  rating of the movie
     */
    public void insert(int movieId, double rating) {
        movieRatings.insertByRating(movieId, rating);

    }

    /**
     * Returns an array of user's favorite movies (up to n). These are the
     * movies that this user gave the rating of 5.
     *
     * @param n the maximum number of movies to return
     * @return array containing movie ids of movies rated as 5 (by this user)
     */
    public int[] getFavoriteMovies(int n) {
        int[] bestMovies = new int[n];
        MovieRatingsList movies = movieRatings.getNBestRankedMovies(n).sublist(5, 5);

        Iterator<MovieRatingNode> it = movies.iterator(0);
        int size = 0;
        while (it.hasNext()) {
            bestMovies[size] = it.next().getMovieId();
            size++;
        }

        int[] favMovies = new int[size];
        System.arraycopy(bestMovies, 0, favMovies, 0, size);

        return favMovies;
    }

    /**
     * Returns boolean for if the user has seen and rated the movie
     *
     * @param movieId movie id
     * @return true if user has seen
     */
    public boolean hasSeenMovie(int movieId) {
        if (movieRatings.getRating(movieId) == -1) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * Returns an array of movies the user likes the least (up to n). These
     * are the movies that this user gave the rating of 1.
     *
     * @param n the maximum number of movies to return
     * @return array of movie ids of movies rated as 1
     */
    public int[] getLeastFavoriteMovies(int n) {
        int[] worstMovies = new int[n];
        MovieRatingsList movies = movieRatings.getNWorstRankedMovies(n).sublist(1, 1);


        Iterator<MovieRatingNode> it = movies.iterator();
        int size = 0;
        while (it.hasNext()) {
            worstMovies[size] = it.next().getMovieId();
            size++;
        }


        int[] leastFavorite = new int[size];
        for (int j = 0; j < size ; j++) {
            leastFavorite[j] = worstMovies[j];
        }

        return leastFavorite;
    }

    /**
     * Computes the similarity of this user with the given "other" user using
     * Pearson correlation - simply calls computeSimilarity method
     * from MovieRatingsList
     *
     * @param otherUser a user to compare the current user with
     * @return similarity score
     */
    public double computeSimilarity(UserNode otherUser) {
        return movieRatings.computeSimilarity(otherUser.movieRatings);
    }

}
