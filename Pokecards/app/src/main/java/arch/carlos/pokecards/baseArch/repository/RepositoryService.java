package arch.carlos.pokecards.baseArch.repository;

import javax.inject.Inject;

import arch.carlos.pokecards.baseArch.AppExecutors;
import arch.carlos.pokecards.baseArch.cache.DomainCache;
import arch.carlos.pokecards.pokeApp.api.PokeService;
import arch.carlos.pokecards.pokeApp.db.PokeDB;

/**
 * Created by mobgen on 9/20/17.
 */

public class RepositoryService {

    private final PokeDB db;
    private final AppExecutors appExecutors;
    private final PokeService service;
    private final DomainCache cache;

    @Inject
    RepositoryService(AppExecutors appExecutors, PokeDB db,
                    PokeService service, DomainCache cache) {
        this.db = db;
        this.service = service;
        this.appExecutors = appExecutors;
        this.cache = cache;
    }

    public PokeDB getDb() {
        return db;
    }

    public AppExecutors getAppExecutors() {
        return appExecutors;
    }

    public PokeService getService() {
        return service;
    }

    public DomainCache getCache(){
        return cache;
    }
}
