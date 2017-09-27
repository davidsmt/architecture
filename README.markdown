# The project
In this example we are going to use the package "baseArch" to create an App easily that uses:
-   Lifecycles handling, LiveData resources, and ViewModel as the app view architecture.
-   Retrofit2, Room, and a DomainCache, as the three datasources (API, DataBase, and local cache).
-   Dagger2 to handle injections.
-   Utils to help with Bindings, Transformations, Adapters, etc.
-   Base classes to avoid boilerplates, and to abstract the coder from the architecture complex behaviours.

In the following chapters we are going to introduce each one of these concepts as a user guide.
1. Dagger2 guide
1. Service layer
1. ViewModel handling
1. Architecture Application, Activity, and Fragment

# Dagger2 Guide (viewmodel oriented)
## Annotations
The first thing to know are the basic annotations
*   @Inject: Request dependencies to satisfy a constructor, a field or a method
*   @Module: Define a class that provides dependencies
*   @Provides: Define a method in a @Module that provides an object
*   @Component: Enables their group of selected modules so they can be injected


The idea is to have @Module classes that are responsible for providing objects which can be injected, through their @Provides annotated methods.

After that, we will use @Inject to define a dependency that will be satisfied with the proper providers.\
*By annotating @Inject on a constructor we allow Dagger to use an instance of this object to fulfill other dependencies, keeping in mind that the constructor parameters need to be provided too, so @Inject constructors are injectors of the constructor parameters and providers of the build object*

### There are more complex behaviours, but all we need in this project is to understand the basic annotations and:
*   @Singleton: Annotates Modules, providers or components, to declare that the requested object will be created once and managed as a singleton object.
*   @Component.Builder: Declares the builder for the component, Dagger has some rules about builders in order to create components succesfully
*   @Component modules: The module list used as the component dependencies

## Gradle configuration
```
    implementation "com.google.dagger:dagger:$dagger_version"
    implementation "com.google.dagger:dagger-android:$dagger_version"
    implementation "com.google.dagger:dagger-android-support:$dagger_version"

        annotationProcessor "com.google.dagger:dagger-android-processor:$dagger_version"
    annotationProcessor "com.google.dagger:dagger-compiler:$dagger_version"
```

#   BaseArch Data Injection
source: https://github.com/davidsmt/architecture/tree/base-arch/Pokecards/app/src/main/java/arch/carlos/pokecards/baseArch\di

We are going to see step-by-step the use of dagger to resolve the dependencies of the example project.



## AppInjector
source: https://github.com/davidsmt/architecture/blob/base-arch/Pokecards/app/src/main/java/arch/carlos/pokecards/baseArch/di/AppInjector.java\

In the AppInjector, we are injecting the application, activities, and fragment that need to be injected, just by getting the lifecycle of the Application and handling the callbacks.

## AppModule

source: https://github.com/davidsmt/architecture/blob/base-arch/Pokecards/app/src/main/java/arch/carlos/pokecards/baseArch/di/AppModule.java\

The AppModule will provide all the app needs to be injected, the backbone functionalities of the app.
In this example, we need to provide the service layer (API, DataBase and Cache).

We also tell the AppModule tho include in the injection the ViewModelModule.

# ViewModelModule and ViewModelKey

This module will provide a map of the injectable ViewModels in the app, assingning a ViewModelKey to each one of them, so we will be able to retrieve them with the ViewModelFactory later.

# ActivityBuildersModule
source:https://github.com/davidsmt/architecture/blob/base-arch/Pokecards/app/src/main/java/arch/carlos/pokecards/baseArch/di/ActivityBuildersModule.java\

As we saw before, the AppInjector injects all the activities created, but to do that, these activities must implement Injectable, and be included in some module. So this module contains all the activities that are needed to be injected in some point of the app lifecycle.

# AppComponent
source: https://github.com/davidsmt/architecture/blob/base-arch/Pokecards/app/src/main/java/arch/carlos/pokecards/baseArch/di/AppComponent.java\

Now, we need to have a root component that will build the dagger structure, that will include:

* The AppModule which provides the backbone services.
* The ActivityBuilder which provides the injectable activities

The build method will be called from the AppInjector init, which is called by the Application when the app starts.

# Service layer
We are building each service data source in the AppModule as we saw before, and providing the singleton instances everywhere we need.
## API
The main class is the definition of the API, in our example is the `PokeService`: https://github.com/davidsmt/architecture/blob/base-arch/Pokecards/app/src/main/java/arch/carlos/pokecards/pokeApp/api/PokeService.java . 

We also need some CallAdapters to get the requested items from the call, they can be found in the utils package.
And we also have `ApiResponse`, a response holder: https://github.com/davidsmt/architecture/blob/base-arch/Pokecards/app/src/main/java/arch/carlos/pokecards/baseArch/api/ApiResponse.java .

That will allow us to handle the responses, and get them in LiveData from the API.

## Database
In our example, we create the database definition in `PokeDB`, and then we create each Data Access Object in the dao package.
sources: https://github.com/davidsmt/architecture/tree/base-arch/Pokecards/app/src/main/java/arch/carlos/pokecards/pokeApp/db .
Also, we can stablish some utils like the RoomTypeConverters, (If necessary).

## Cache
source: https://github.com/davidsmt/architecture/blob/base-arch/Pokecards/app/src/main/java/arch/carlos/pokecards/baseArch/cache/DomainCache.java
\
The DomainCache request all resources stored to implement `Cacheable` to know their domain key (The entity class name or the table name, for instance), that all the models of the same type share, and their primary key, that is the identifier of each stored item.

# ViewModel handling

## Creating the View Model
Create a View Model is as simple as extending ViewModel.
But we can store liveData objects that are linked to the view, call the service layer, and do whatever you want.
## Getting the View Model

Now, to get any viewmodel, we need just one line:
```
    @Inject
    ViewModelFactory viewModelFactory;
```

And we will be able to request all the viewmodel we need, an example of a viewmodel applied to a recycler view:
```
 mViewModel = ViewModelProviders.of(this,viewModelFactory).get(ExampleViewModel.class);
```


#   Step-By-Step: Add new activity calling new repository

In this case we are going to add an activity that shows a list of example objects:

### Add the new service
1. Create the example object
```
@Entity(primaryKeys = "id")
public class Example {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;


    public Example(String id, String name) {
        this.id = id;
        this.name = name;
    }

    ...GETTERS AND SETTERS
```

2. Create the Data Access Object
```
@Dao
public interface ExampleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Example example);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Example> examples);

    @Query("SELECT * FROM Example "
            + "ORDER BY id ASC")
    LiveData<List<Example>> loadExamples();
}
```

3. Add the new entity and Dao to the DB
```
@Database(entities = {..., Example.class, ...}, version = X)
public abstract class ExampleBD extends RoomDatabase {

    ...

    abstract public ExampleDao exampleDao();

    ...
}
```

4. Add the service call
```
    @GET("example/giveMeAllPlease")
    LiveData<ApiResponse<List<Example>>> getExamples();
```
5. Create the Example repo
```
public class ExampleRepositoryImpl {

    private final RepositoryService service;
    @Inject
    ExampleRepositoryImpl(RepositoryService repo) {
        this.service = repo;
    }


    public LiveData<Resource<List<Example>>> getExamples(boolean loadFromCache, boolean loadFromDB, boolean loadFromAPI) {
        return new BaseRepositoryRead<>(service.getAppExecutors(), new BaseRepositoryRead.CacheReadImpl<List<Example>>() {

            @Override
            public boolean CACHE_hasDomainCache() {
                return service.getCache().hasCacheForDomain(Example.DOMAIN_KEY);
            }

            @Override
            public LiveData<List<Example>> CACHE_get() {
                return service.getCache().getResource(Example.DOMAIN_KEY);
            }

            @Override
            public void CACHE_save(List<Example> item) {
                service.getCache().addResource(Example.DOMAIN_KEY, item);
            }


        }, new BaseRepositoryRead.DBReadImpl<List<Example>>() {
            @Override
            public LiveData<List<Example>> BD_get() {
                return service.getDb().ExampleDao().getCards();
            }

            @Override
            public void BD_save(@NonNull List<Example> item) {
                service.getDb().ExampleDao().insertAll(item);
            }


        }, new BaseRepositoryRead.ExternalServiceReadImpl<List<Example>, ExampleListResponse>() {
            @Override
            public LiveData<ApiResponse<ExampleListResponse>> API_get() {
                return service.getService().getExamples();
            }

            @Override
            public List<Example> API_proccesToInternal(ApiResponse<ExampleListResponse> response) {
                return response.body.getCards();
            }

            @Override
            public void API_formatError(MutableLiveData<Resource<List<Example>>> partialData, String errorMessage, int errorCode) {
                if (partialData.getValue() != null) {
                    partialData.setValue(Resource.error(errorExample, partialData.getValue().data));
                } else {
                    partialData.setValue(Resource.error(errorExample, new ArrayList<>()));
                }
            }

        }).getFrom(loadFromCache, loadFromDB, loadFromAPI);
    }

}
```
As we can see, we are implementing the GET method calling the BaseRead operation, and implementing cache, database, and external service read interfaces, with the generic parameter <List<Example>>
With that, the repo works with the CACHE, DB and the API, to retrieve the list of examples

##  Create the activity

1. Create the ViewModel
```
public class ExampleViewModel extends ViewModel {

    final ExampleRepository repo;

    @Inject
    public ExampleViewModel(ExampleRepository repository){
        repo = repository;
    }

    public LiveData<Resource<List<Example>>> getMovieList() {
        return repo.getExampleList();
    }

}
```

2. Create the Injectable Activity:
```
public class ItemListActivity extends ArchActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    .
    .
    .  

}
```
3. Get the ViewModel, and observe its liveData to show it in your view
```
ExampleViewModel = ViewModelProviders.of(this, viewModelFactory).get(ExampleViewModel.class);
                ExampleViewModel.getMovieList().observe(this, listResource -> {
                    if (listResource.status == Status.SUCCESS) {
                        mBinding.itemsRecyclerview.setAdapter(new ItemListAdapter(listResource.data, 1));
                    }
                });
}
```

## Add the injections

1. Add the activity

```
    @ContributesAndroidInjector
    abstract ItemListActivity contributeItemListActivity();
```

3. Add the ViewModel
```
    @Binds
    @IntoMap
    @ViewModelKey(ExampleViewModel.class)
    abstract ViewModel bindExampleViewModel(ExampleViewModel ExampleViewModel);
```