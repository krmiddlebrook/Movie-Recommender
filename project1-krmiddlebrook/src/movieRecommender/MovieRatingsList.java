package movieRecommender;
/**
 * MovieRatingsList.
 * A class that stores movie ratings for a user in a custom singly linked list of
 * MovieRatingNode objects. Has various methods to manipulate the list. Stores
 * only the head of the list (no tail! no size!). The list should be sorted by
 * rating (from highest to smallest).
 * Fill in code in the methods of this class.
 * Do not modify signatures of methods. Not all methods are needed to compute recommendations,
 * but all methods are required for the assignment.
 */

import java.util.Iterator;
import java.util.HashMap;
import java.lang.Math;

public class MovieRatingsList implements Iterable<MovieRatingNode> {

	private MovieRatingNode head;
	// Note: you are not allowed to store the tail or the size of this list

	/**
	 * Changes the rating for a given movie to newRating. The position of the
	 * node within the list should be changed accordingly, so that the list
	 * remains sorted by rating (from largest to smallest).
	 *
	 * @param movieId id of the movie
	 * @param newRating new rating of this movie
	 */
	public void setRating(int movieId, double newRating) {
		if (newRating < 0.5 || newRating > 5) {
			System.out.println("New rating value must be between 0.5 and 5.");
			return;
		}
		MovieRatingNode curr = head;
		boolean idFound = false;
//		MovieRatingNode prev = head;
		while (curr.next() != null) {
			if (curr.next().getMovieId() == movieId) {
				curr.setNext(curr.next().next());
				idFound = true;
				break;
			}
			else {
				curr = curr.next();
			}
		}
		if (idFound) {
			insertByRating(movieId, newRating);
		}
		else {
			System.out.println("movieId not found in list");
		}
	}

    /**
     * Return the rating for a given movie. If the movie is not in the list,
     * returns -1.
     * @param movieId movie id
     * @return rating of a movie with this movie id
     */
	public double getRating(int movieId) {
		double rating = 0;
		MovieRatingNode curr = head;
		while (curr != null) {
			if (curr.getMovieId() == movieId) {
				rating += curr.getMovieRating();
				break;
			}
			curr = curr.next();
		}
		if (rating == 0) {
			return -1;
		}
		else {
			return rating;
		}
	}

    /**
     * Insert a new node (with a given movie id and a given rating) into the list.
     * Insert it in the right place based on the value of the rating. Assume
     * the list is sorted by the value of ratings, from highest to smallest. The
     * list should remain sorted after this insert operation.
     *
     * @param movieId id of the movie
     * @param rating rating of the movie
     */
	public void insertByRating(int movieId, double rating) {
		MovieRatingNode newNode = new MovieRatingNode(movieId, rating);
		if (head == null) {
			head = newNode;
		}
		else if (rating >= head.getMovieRating()) {

		    if (rating == head.getMovieRating()) {
		        MovieRatingNode current = head; int done = -1;
		        if (newNode.getMovieId() >= head.getMovieId()) {
		            newNode.setNext(head);
		            head = newNode;
		            done = 0;
                }
                while (current.next() != null && done == -1) {
		            if (rating == current.next().getMovieRating()) {
		                if (newNode.getMovieId() < current.next().getMovieId()) {
		                    current = current.next();
                        }
                        else {
		                 newNode.setNext(current.next());
		                 current.setNext(newNode);
		                 done = 0;
		                 break;
                        }
                    }
                    else {
		                break;
                    }
                }
                if (done == -1) {
		            newNode.setNext(current.next());
		            current.setNext(newNode);
                }
            }
            else {
		        newNode.setNext(head);
		        head = newNode;
            }
		}
		else {
			MovieRatingNode curr = head;
			while (curr.next() != null) {
				if (rating > curr.next().getMovieRating()) {
					newNode.setNext(curr.next());
					curr.setNext(newNode);
					break;
				}
				else if (rating == curr.next().getMovieRating()) {
					if (movieId > curr.next().getMovieId()) {
						newNode.setNext(curr.next());
						curr.setNext(newNode);
						break;
					}
					else {
						curr = curr.next();
					}
				}
				else {
					curr = curr.next();
				}
			}
			if (curr.next() == null) {
				curr.setNext(newNode);
			}
		}
	}

    /**
     * Computes similarity between two lists of ratings using Pearson correlation.
	 * https://en.wikipedia.org/wiki/Pearson_correlation_coefficient
	 * Note: You are allowed to use a HashMap for this method.
     *
     * @param otherList another MovieRatingList
     * @return similarity computed using Pearson correlation
     */
    public double computeSimilarity(MovieRatingsList otherList) {
		double similarity = 0;
		HashMap<Integer, Double> userMap = new HashMap<>();
		MovieRatingNode curr = head;
		while (curr != null) {
			userMap.put(curr.getMovieId(), curr.getMovieRating());
			curr = curr.next();
		}

		int n = 0; double xRatings = 0; double yRatings = 0; double sumXY = 0;
		double sumXSquared = 0; double sumYSquared = 0;

		for (Integer key : userMap.keySet()) {

			double user2Rating = otherList.getRating(key);

			if (user2Rating != -1) {
				n++;
				xRatings += userMap.get(key);
				yRatings += user2Rating;
				sumXY += user2Rating * userMap.get(key);
				sumXSquared += userMap.get(key) * userMap.get(key);
				sumYSquared += user2Rating * user2Rating;
			}
		}



		double expectedValue = (n*sumXY) - (xRatings * yRatings);

		double xVariance = Math.sqrt((n*sumXSquared) - Math.pow(xRatings, 2));
		double yVariance = Math.sqrt((n*sumYSquared) - Math.pow(yRatings, 2));
		double variance = (xVariance * yVariance);

        if (variance == 0) {
            similarity = -50;
            return similarity;
        }
        else {
            similarity = (expectedValue / variance);
            return similarity;
        }
    }

    /**
     * Returns a sublist of this list where the rating values are in the range
     * from begRating to endRating, inclusive.
     *
     * @param begRating lower bound for ratings in the resulting list
     * @param endRating upper bound for ratings in the resulting list
     * @return sublist of the MovieRatingsList that contains only nodes with
     * rating in the given interval
     */
	public MovieRatingsList sublist(int begRating, int endRating) {
		if(head == null) {
			System.out.println("List is empty.");
			return null;
		}
		else if (begRating > endRating) {
			System.out.println("Beginning rating must be greater than or equal to end rating.");
			return null;
		}
		else if (begRating < .5) {
			System.out.println("Beginning rating must be greater than or equal to 0.5.");
			return null;
		}
		else if (endRating > 5) {
			System.out.println("End rating must be less than or equal to 5.");
			return null;
		}
		else if (head.next() == null && head.getMovieRating() > endRating || head.getMovieRating() < begRating) {
			return null;
		}

		else {
			MovieRatingsList res = new MovieRatingsList();
			MovieRatingNode curr = head;
			if (endRating == begRating) {
				while (curr.next() != null && curr.getMovieRating() != endRating) {
					curr = curr.next();
				}
				res.head = curr;
				MovieRatingNode pt2 = curr;
				while (pt2.next() != null && pt2.next().getMovieRating() == endRating) {
					pt2 = pt2.next();
				}
				pt2.setNext(null);
				return res;
			} else {
				while (curr.next() != null && curr.getMovieRating() > endRating) {
					curr = curr.next();
				}
				res.head = curr;
				MovieRatingNode pt2 = curr;
				while (pt2.next() != null && pt2.next().getMovieRating() >= begRating) {
					pt2 = pt2.next();
				}
				pt2.setNext(null);
				return res;
			}
		}
	}

	/** Traverses the list and prints the ratings list in the following format:
	 *  movieId:rating; movieId:rating; movieId:rating;  */
	public void print() {
		if (head == null) {
			System.out.println("List is empty");
		}
		try {
			MovieRatingNode curr = head;
			while (curr != null) {
				System.out.print(curr.getMovieId() + ":" + curr.getMovieRating() + "; ");
				curr = curr.next();
			}
			System.out.println();
		} catch (NullPointerException err) {
			System.out.println("List is empty");
		}

	}

	/** Traverses the list and prints the ratings list in the following format:
	 *  movieId:rating; movieId:rating; movieId:rating;  */
	public String toString() {
		String ratings = "";
		if (head == null) {
			ratings += "No ratings";
			return ratings;
		}
		MovieRatingNode curr = head;
		while (curr != null) {
			ratings += (curr.getMovieId() + ":" + curr.getMovieRating() + "; ");
			curr = curr.next();
		}
		return ratings;

	}


	/**
	 * Returns a list of all the movies a user has seen
	 * @return linked list of movies user has seen
	 */
	public MovieRatingsList getMovies() {
		if (head == null) {
			return null;
		}
		MovieRatingsList movies = new MovieRatingsList();
		MovieRatingNode curr = head;
		movies.head = curr;
		while(curr.next() != null) {
			curr = curr.next();
		}
		return movies;

	}

	/**
	 * Returns the middle node in the list - the one half way into the list.
	 * Needs to have the running time O(n), and should be done in one pass
     * using slow & fast pointers (as described in class).
	 *
	 * @return the middle MovieRatingNode
	 */
	public MovieRatingNode getMiddleNode() {
		try {
			MovieRatingNode fast = head;
			MovieRatingNode slow = head;
			while (fast.next() != null) {
				if (fast.next().next() == null) {
					return slow;
				}
				else {
					fast = fast.next().next();
					slow = slow.next();
				}
			}
			return slow;
		} catch (NullPointerException e1) {
			System.out.println("List is empty.");
			return null;
		}
	}

    /**
     * Returns the median rating (the number that is halfway into the sorted
     * list). To compute it, find the middle node and return it's rating. If the
     * middle node is null, return -1.
     *
     * @return rating stored in the node in the middle of the list
     */
	public double getMedianRating() {
		MovieRatingNode middleNode = getMiddleNode();
		if (middleNode == null) {
			return -1;
		}
		else {
			return middleNode.getMovieRating(); // don't forget to change it
		}
	}

    /**
     * Returns a RatingsList that contains n best rated movies. These are
     * essentially first n movies from the beginning of the list. If the list is
     * shorter than size n, it will return the whole list.
     *
     * @param n the maximum number of movies to return
     * @return MovieRatingsList containing movies rated as 5
     */
	public MovieRatingsList getNBestRankedMovies(int n) {
			MovieRatingsList best = new MovieRatingsList();
			MovieRatingNode fast = head;
			MovieRatingNode slow = head;
			for (int i = 1; i < n+1; i++) {
				fast = fast.next();
			}
			while (slow != fast) {
				best.insertByRating(slow.getMovieId(), slow.getMovieRating());
				slow = slow.next();
			}
			return best;
	}

    /**
     * Returns a RatingsList that contains n worst rated movies for this user.
     * Essentially, these are the last n movies from the end of the list. You are required to
	 * use slow & fast pointers to find the n-th node from the end (as discussed in class).
	 * Note: This method should compute the result in one pass. Do not use size variable
	 * Do NOT use reverse(). Do not destroy the list.
     *
     * @param n the maximum number of movies to return
     * @return MovieRatingsList containing movies rated as 1
     */
	public MovieRatingsList getNWorstRankedMovies(int n) {
		MovieRatingsList worst = new MovieRatingsList();
		MovieRatingNode fast = head;
		MovieRatingNode slow = head;
		for (int i = 1; i < n; i++) {
			fast = fast.next();
		}
		while (fast.next() != null) {
			fast = fast.next();
			slow = slow.next();
		}
		worst.head = slow;
		return worst;
	}

	/** Push the new element on top of the list */
	public MovieRatingNode push(MovieRatingNode node) {
		MovieRatingNode newTop = new MovieRatingNode(node.getMovieId(), node.getMovieRating());
		newTop.setNext(null);
		return newTop;
	}

    /**
     * Return a new list that is the reverse of the original list. The returned
     * list is sorted from lowest ranked movies to the highest rated movies.
     * Use only one additional MovieRatingsList (the one you return) and constant amount
     * of memory. You may NOT use arrays, ArrayList and other built-in Java Collections classes.
     * Read description carefully for requirements regarding implementation of this method.
	 *
     * @param h head of the MovieRatingList to reverse
     * @return reversed list
     */
	public MovieRatingsList reverse(MovieRatingNode h) {
		if (head == null) {
			return null;
		} else {
			MovieRatingsList r = new MovieRatingsList();
			MovieRatingNode curr = head.next();
			MovieRatingNode top = null;
			r.head = new MovieRatingNode(h.getMovieId(), h.getMovieRating());
			while (curr != null) {
				top = push(curr);
				top.setNext(r.head);
				r.head = top;
				curr = curr.next();
			}
			return r;
		}
	}

	public Iterator<MovieRatingNode> iterator(int index) {

		return new MovieRatingsListIterator(0);
	}

	public Iterator<MovieRatingNode> iterator() {

		return new MovieRatingsListIterator(0);
	}


	/**
	 * Inner class, MovieRatingsListIterator
	 * The iterator for the ratings list. Allows iterating over the MovieRatingNode-s of
	 * the list.
	 * FILL IN CODE
	 */
	private class MovieRatingsListIterator implements Iterator<MovieRatingNode> {

		MovieRatingNode curr;

		public MovieRatingsListIterator(int index) {
			MovieRatingNode dummy = new MovieRatingNode(0, 3);
			dummy.setNext(head);
			if (index < 0) {
				index = 0;
			}
			if (head == null) {
				System.out.println("No ratings in list");
			}
			curr = dummy;
			for (int i = 0; i < index; i++) {
				curr = curr.next();
			}

		}

		@Override
		public boolean hasNext() {
			return curr != null && curr.next() != null;
		}

		@Override
		public MovieRatingNode next() {
			if (!hasNext()) {
				System.out.println("No next element");
				return null;
			}
			curr = curr.next();
			return curr;
		}

	}

}
