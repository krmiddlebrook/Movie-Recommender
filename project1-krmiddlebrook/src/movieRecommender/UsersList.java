package movieRecommender;


import java.io.*;

/**
 * A custom linked list that stores user info. Each node in the list is of type
 * UserNode.
 * FILL IN CODE. Also, add other methods as needed.
 *
 * @author okarpenko
 *
 */
public class UsersList {
    private UserNode head = null;
    private UserNode tail = null; // ok to store tail here, will be handy for appending


    /** Insert the rating for the given userId and given movieId.
     *
     * @param userId  id of the user
     * @param movieId id of the movie
     * @param rating  rating given by this user to this movie
     */
    public void insert(int userId, int movieId, double rating) {

          // Check if UserNode already exists;
          // if not, create it and append to this list.
          // Then call insert(movieId, rating) method on the UserNode
        if (head == null) {
            UserNode newUser = new UserNode(userId);
            head = newUser;
            tail = newUser;
            newUser.insert(movieId, rating);
        }
        else {
            UserNode newUser = new UserNode(userId);

            UserNode check = get(userId);
            if (check != null) {
                check.insert(movieId, rating);
            }
            else {
                UserNode curr = head;
                while (curr.next() != null && userId > curr.next().getId()) {
                    curr = curr.next();
                }
                if (curr.next() != null && curr != tail) {
                    curr.setNext(newUser);
                    newUser.setNext(curr.next().next());
                    newUser.insert(movieId, rating);
                }
                else {
                    tail.setNext(newUser);
                    tail = newUser;
                    newUser.insert(movieId, rating);
                }
            }
        }
    }

    /**
     * Append a new node to the list
     * @param newNode a new node to append to the list
     */
    public void append(UserNode newNode) {
        // This is where tail will come in handy
        if (head == null){
            head = newNode;
            tail = newNode;
        }
        else {
            tail.setNext(newNode);
            tail = newNode;
        }
    }

    /** Return a UserNode given userId
     *
     * @param userId id of the user (as defined in ratings.csv)
     * @return UserNode for a given userId
     */
    public UserNode get(int userId) {
        UserNode cur = head;
        while (cur != null && cur.getId() != userId) {
            cur = cur.next();
        }

        if (cur != null) {
            return cur;
        }
        else {
            return null;
        }
    }




    /**
     * The method computes the similarity between the user with the given userid
     * and all the other users. Finds the maximum similarity and returns the
     * "most similar user".
     * Calls computeSimilarity method in class MovieRatingsList/
     *
     * @param userid id of the user
     * @return the node that corresponds to the most similar user
     */
    public UserNode findMostSimilarUser(int userid) {
        if (head == null) {
            return null;
        }

        UserNode mostSimilarUser = null;
        UserNode user = get(userid);
        UserNode curr = head;
        double score = 0; double similarityScore;

        while (curr != null) {
            if (curr.getId() == userid) {
                //do nothing
            }
            else {
                similarityScore = user.computeSimilarity(curr);
                if (similarityScore > score) {
                 score = similarityScore;
                 mostSimilarUser = curr;
                }
            }
            curr = curr.next();
        }
        return mostSimilarUser;
    }


    /** Print UsersList to a file  with the given name in the following format:
     (userid) movieId:rating; movieId:rating; movieId:rating;
     (userid) movieId:rating; movieId:rating;
     (userid) movieId:rating; movieId:rating; movieId:rating; movieId:rating;
     Info for each userid should be printed on a separate line
     * @param filename name of the file where to output UsersList info
     */
    public void print(String filename) {
        try {
            FileWriter file = new FileWriter(filename);

            UserNode curr = head;
            while (curr != null) {
                file.write(curr.toString() + '\n');
                curr = curr.next();
            }
            file.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
