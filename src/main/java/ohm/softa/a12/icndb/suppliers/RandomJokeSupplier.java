package ohm.softa.a12.icndb.suppliers;

import ohm.softa.a12.icndb.ICNDBApi;
import ohm.softa.a12.icndb.ICNDBService;
import ohm.softa.a12.model.JokeDto;
import ohm.softa.a12.model.ResponseWrapper;
import org.apache.commons.lang3.NotImplementedException;

import java.util.concurrent.ExecutionException;

/**
 * @author Peter Kurfer
 */

public final class RandomJokeSupplier {

    /* ICNDB API proxy to retrieve jokes */
    private final ICNDBApi icndbApi;

    public RandomJokeSupplier() {
        icndbApi = ICNDBService.getInstance();
    }

    public ResponseWrapper<JokeDto> get() {
        /* fetch a random joke synchronously
         * if an exception occurs return null */
		try {
			return icndbApi.getRandomJoke().get();
		} catch (InterruptedException | ExecutionException ex) {
			return null;
		}
	}
}
