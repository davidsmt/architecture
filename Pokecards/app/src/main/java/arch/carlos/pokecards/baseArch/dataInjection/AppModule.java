package arch.carlos.pokecards.baseArch.dataInjection;

/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Application;
import android.arch.persistence.room.Room;
import javax.inject.Singleton;

import arch.carlos.pokecards.baseArch.cache.DomainCache;
import arch.carlos.pokecards.baseArch.utils.LiveDataCallAdapterFactory;
import arch.carlos.pokecards.pokeApp.api.PokeService;
import arch.carlos.pokecards.pokeApp.db.PokeDB;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {
    private static final String API_BASE_URL = "https://api.pokemontcg.io/v1/";

    @Singleton @Provides
    PokeService provideService() {
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(PokeService.class);
    }

    @Singleton @Provides
    DomainCache provideCache() {
        DomainCache cache = new DomainCache();
        cache.init();
        return cache;
    }

    @Singleton @Provides
    PokeDB provideDb(Application app) {
        return Room.databaseBuilder(app, PokeDB.class,"pokemon_cards.db").fallbackToDestructiveMigration().build();
    }
}
