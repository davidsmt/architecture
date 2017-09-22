package arch.carlos.pokecards.baseArch.utils;

/**
 * Created by mobgen on 9/22/17.
 */

public class RepositoryOperationNotImplementedException extends RuntimeException {

    public RepositoryOperationNotImplementedException(String message) {
        super(message, new Throwable("Not Implemented operation in repository"));
    }
}
