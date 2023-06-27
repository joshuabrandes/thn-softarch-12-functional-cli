package ohm.softa.a12.cnjdb.suppliers;

import ohm.softa.a12.cnjdb.CNJDBApi;
import ohm.softa.a12.cnjdb.CNJDBService;
import ohm.softa.a12.model.JokeDto;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Supplier implementation to retrieve all jokes of the ICNDB in a linear way
 * @author Peter Kurfer
 */

public final class AllJokesSupplier implements Supplier<JokeDto> {

    /* ICNDB API proxy to retrieve jokes */
    private final CNJDBApi cnjdbApi;

	// private final int jokeCount;
	private final List<String> knownJokeIds;
	private int currentJoke = 0;

    public AllJokesSupplier() {
        cnjdbApi = new CNJDBService().getInstance();
		knownJokeIds = new ArrayList<>();
        /* fetch the total count of jokes the API is aware of
         * to determine when all jokes are iterated and the counters have to be reset */
    }

	@Override
    public JokeDto get() {
        /* TODO retrieve the next joke
         * note that there might be IDs that are not present in the database
         * you have to catch an exception and continue if no joke was retrieved to an ID
         * if you retrieved all jokes (count how many jokes you successfully fetched from the API)
         * reset the counters and continue at the beginning */
		JokeDto result = null;
		int triesCount = 0;
		while (result == null && triesCount < 20) {
			try {
				triesCount++;
				var tmp = cnjdbApi.getRandomJoke().get();
				if (!knownJokeIds.contains(tmp.getId())) {
					knownJokeIds.add(tmp.getId());
					currentJoke++; // counts how many jokes where found
					result = tmp;
				}
			} catch (InterruptedException | ExecutionException ex) {
				triesCount++;
			}
		}
		return result;
	}

}
