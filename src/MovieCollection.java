import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> everyoneEver;
    private ArrayList<String> genreList;
    private ArrayList<Movie> moviesOfAllTime;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        ArrayList<String> everyone = new ArrayList<String>();
        for (Movie movie : movies) {
            String[] everyoneInThatMovie = movie.getCast().split("\\|");
            for (String actor : everyoneInThatMovie) {
                if (!everyone.contains(actor)) {
                    everyone.add(actor);
                }
            }
        }
        everyoneEver = everyone;
        ArrayList<String> genres = new ArrayList<String>();
        for (Movie movie : movies) {
            String[] allTheGenres = movie.getGenres().split("\\|");
            for (String genre : allTheGenres) {
                if (!genres.contains(genre)) {
                    genres.add(genre);
                }
            }
        }
        sortGeneral(genres);
        genreList = genres;
        for (int i = 0; i < movies.size(); i++) {
            for (int j = 1; j < movies.size(); j++) {
                Movie movieOne = movies.get(i);
                Movie movieTwo = movies.get(j);
                if (movieOne.getUserRating() > movieTwo.getUserRating()) {
                    movies.set(i, movieTwo);
                    movies.set(j, movieOne);
                }
            }
        }
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)ighest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortGeneral(ArrayList<String> sortNOW) {
        for (int i = 1; i < sortNOW.size(); i++) {
            String name = sortNOW.get(i);
            int possibleIndex = i;
            while (possibleIndex > 0 && name.compareTo(sortNOW.get(possibleIndex - 1)) < 0) {
                sortNOW.set(possibleIndex, sortNOW.get(possibleIndex - 1));
                possibleIndex --;
            }
            sortNOW.set(possibleIndex, name);
        }
    }

    private void sortReviews(Movie[] justForTheTop50ngl) {
        for (int i = 0; i < justForTheTop50ngl.length; i++) {

        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter an actor: ");
        String actors = scanner.nextLine();
        actors = actors.toLowerCase();
        ArrayList<String> actorsWithThatName = new ArrayList<String>();
        for (String actor : everyoneEver) {
            String name = actor.toLowerCase();
            if (name.contains(actors)) {
                actorsWithThatName.add(actor);
            }
        }
        sortGeneral(actorsWithThatName);
        for (int i = 0; i < actorsWithThatName.size(); i++) {
            String name = actorsWithThatName.get(i);
            int number = i + 1;
            System.out.println("" + number + ". " + name);
        }
        System.out.println("Which actor are you talking about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String nameTheyWant = actorsWithThatName.get(choice - 1);
        ArrayList<Movie> moviesWithThatActor = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getCast().contains(nameTheyWant)) {
                moviesWithThatActor.add(movie);
            }
        }
        sortResults(moviesWithThatActor);
        for (int i = 0; i < moviesWithThatActor.size(); i++) {
            String movie = moviesWithThatActor.get(i).getTitle();
            int number = i + 1;
            System.out.println("" + number + ". " + movie);
        }
        System.out.println("Which movie do you want to learn more about?");
        System.out.print("Enter number: ");
        int movieChoice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = moviesWithThatActor.get(movieChoice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void searchKeywords()
    {
        System.out.print("Enter a keyword: ");
        String keyword = scanner.nextLine();
        keyword = keyword.toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++) {
            String movieKeywords = movies.get(i).getKeywords();
            movieKeywords = movieKeywords.toLowerCase();
            if (movieKeywords.contains(keyword)) {
                results.add(movies.get(i));
            }
        }
        sortResults(results);
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();
            int number = i + 1;
            System.out.println("" + number + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        for (int i = 0; i < genreList.size(); i++) {
            String genre = genreList.get(i);
            int number = i + 1;
            System.out.println("" + number + ". " + genre);
        }
        System.out.println("Which genre do you want to search movies for?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String genreTheyWant = genreList.get(choice - 1);
        ArrayList<Movie> moviesOfThatGenre = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getGenres().equals(genreTheyWant)) {
                moviesOfThatGenre.add(movie);
            }
        }
        sortResults(moviesOfThatGenre);
        for (int i = 0; i < moviesOfThatGenre.size(); i++) {
            String name = moviesOfThatGenre.get(i).getTitle();
            int number = i + 1;
            System.out.println("" + number + ". " + name);
        }
        System.out.println("Which movie do you want to learn more about?");
        System.out.print("Enter number: ");
        int decision = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = moviesOfThatGenre.get(decision - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu");
        scanner.nextLine();
    }

    private void listHighestRated()
    {

    }

    private void listHighestRevenue()
    {

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}