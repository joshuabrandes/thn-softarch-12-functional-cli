package ohm.softa.a12.icndb.suppliers;

import ohm.softa.a12.icndb.ICNDBApi;
import ohm.softa.a12.icndb.ICNDBService;
import ohm.softa.a12.model.JokeDto;
import ohm.softa.a12.model.ResponseWrapper;
import org.apache.commons.lang3.NotImplementedException;

import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

/**
 * Supplier implementation to retrieve all jokes of the ICNDB in a linear way
 * @author Peter Kurfer
 */

public final class AllJokesSupplier {

    /* ICNDB API proxy to retrieve jokes */
    private final ICNDBApi icndbApi;

	private final int jokeCount;
	private int currentJoke = 0;

    public AllJokesSupplier() {
        icndbApi = ICNDBService.getInstance();
        /* fetch the total count of jokes the API is aware of
         * to determine when all jokes are iterated and the counters have to be reset */
		int tmpCount;
		try {
			jokeCount = icndbApi.getJokeCount().get().getValue();
		} catch (InterruptedException | ExecutionException exception) {
			throw new RuntimeException("Joke count couldn't be retrieved");
		}
    }

    public ResponseWrapper<JokeDto> get() {
        /* TODO retrieve the next joke
         * note that there might be IDs that are not present in the database
         * you have to catch an exception and continue if no joke was retrieved to an ID
         * if you retrieved all jokes (count how many jokes you successfully fetched from the API)
         * reset the counters and continue at the beginning */
		try {
			return icndbApi.getJoke(currentJoke)
				.thenApply(respone -> {
					if (respone.isSuccessfull()) {
						return respone;
					} else {
						return null;
					}
				})
				.get();
		} catch (InterruptedException | ExecutionException ex) {
			throw new RuntimeException("Something went wrong during receiving the next joke.");
		}
	}

}
