package movieRecommender;

import java.util.Arrays;

/** A driver class for the MovieRecommender. In the main method, we
 * create a movie recommender, load movie data from files and compute
 * recommendations and anti-recommendations for a particular user.
 */
public class Driver {
    public static void main(String[] args) {

        MovieRecommender recommender = new MovieRecommender();
        // movies.csv and ratings.csv should be in the project folder
        recommender.loadData("movies.csv","ratings.csv");
        System.out.println("Loaded movie data...");
        recommender.findRecommendations(3, 15, "recommendations");
        System.out.println();
        recommender.findAntiRecommendations(3, 15, "antiRecommendations");



        /* Lots of tests */

//        UserNode user = new UserNode(1);
//        user.insert(1, 3);
//        user.insert(2, 4);
//        user.insert(3, 1);
//        user.getFavoriteMovies(3);
//        user.insert(4, 1);
//        int[] ids = user.getMovieIds();


//        MovieRatingsList list = new MovieRatingsList();
//        list.insertByRating(1, 5);
//        list.insertByRating(2, 2);
//        list.insertByRating(3, 3);
//        list.insertByRating(4, 3);
//        list.insertByRating(5, 4);
//        list.insertByRating(6, 4);
//        list.insertByRating(7, 4);
//        list.insertByRating(8, 2);
//        list.insertByRating(9, 1);
//        list.insertByRating(10, 5);
//        list.insertByRating(11, 5);


//        System.out.println("-----------Printing MovieRatingsList------------");
//        list.print();

//        System.out.println("------------------Sublist 5 to 5-----------------");
//        MovieRatingsList sub =  list.sublist(5, 5);
//        sub.print();

//        System.out.println("--------------MiddleNode-----------------");
//        System.out.println(list.getMiddleNode());
////        list.print();

//        System.out.println("--------------Movie rating-----------------");
//        System.out.println(list.getRating(1));
//        System.out.println(list.getRating(0));
//
//        System.out.println("----------Set rating-------------");
//        list.print();
//        list.setRating(5, -1);
//        list.print();
//
//        System.out.println("------------Get Median Rating----------------");
//        System.out.println(list.getMedianRating());
//
//        System.out.println("----------Get n BEST rated movies-----------------");
//////        try {
////        list.print();
////        MovieRatingsList best = list.getNBestRankedMovies(3);
////        best.print();
////        list.print();
////        } catch (NullPointerException e) {
////            System.out.println("returned list is null");
////        }
//
//        System.out.println("-------------Get n WORST rated movies----------------");
//        list.print();
//        MovieRatingsList worst = list.getNWorstRankedMovies(4);
//        worst.print();
//        list.print();
//
//        System.out.println("--------------Get Rating-----------------");
//        System.out.println(list.getRating(8)); //expected = 2.0
//        System.out.println(list.getRating(55)); //expected = -1
//
//        System.out.println("---------------Testing Insert by Rating--------------------");
//        MovieRatingsList list2 = new MovieRatingsList();
//        list2.insertByRating(1, 5);
//        list2.insertByRating(2, 5);
//        list2.print();

//        System.out.println("--------------Get Fav Movies--------------");
//        UserNode user = new UserNode(1);
//        user.insert(1, 1);
//        user.insert(2, 5);
//        user.insert(3, 5);
//        user.insert(4, 5);
//        user.insert(5, 4);
//        System.out.println(Arrays.toString(user.getFavoriteMovies(5)));
//
//        System.out.println("--------------Get Least Fav Movies--------------");
//        user.insert(6, 1);
//        user.insert(7, 1);
//        System.out.println(Arrays.toString(user.getLeastFavoriteMovies(5)));

//        System.out.println("-----------Testing UserList functions--------------");
//
//        System.out.println();


//        System.out.println("-------Testing UserList-----------");
//        UsersList userList = new UsersList();
//        userList.insert(1, 1, 5);
//        userList.insert(1, 2, 1);
//        userList.insert(1, 3, 3);
//        userList.insert(2, 1, 5);
//        userList.insert(2, 2, 2);
//        userList.insert(3, 1, 5);
//        userList.insert(3, 2, 5);
////        userList.get(1).getFavoriteMovies(2);
//////        userList.print("testFile.txt");
//        System.out.println(userList.findMostSimilarUser(1));


    }
}
