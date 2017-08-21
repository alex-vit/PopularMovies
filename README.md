# Popular Movies
This app was built as part of the Associate Android Developer Fast Track at Udacity.

## Technical Debt Simulator 2017
I'm refactoring this from Spaghetti architecture to MVVM with RxJava and maybe Data Binding for fun and profit.

### Achievements unlocked
- [x] Reorganize data to resemble repository structure
- [x] Create remote data source
- [x] Migrate remote data source to retrofit
- [x] Add retrofit Rx adapter
- [ ] Replace (Bi)Functions with lambdas
- [ ] Create local data source with sqlbrite
- [ ] Create movies repository
- [ ] Cache everything in local data source (with LRU eviction)
- [ ] \(Optional) Also cache stuff in memory
- [ ] Create movies viewmodel and remove loaders
- [ ] Create details viewmodel and stop passing around parcels
- [ ] Migrate listeners to retrolambdas
- [ ] Bind listeners using butterknife or RxBinding
- [ ] Move UI logic into viewmodels and expose view state Rx subject
- [ ] Remove all android dependencies from Repo and ViewModels
- [ ] Unit test all the things
- [ ] Espresso test what remains
- [ ] Use Dagger2 DI instead of singletons
- [ ] _Hey, not too rough_ Data binding without accidentally creating presenters.xml
- [ ] _Hurt me plenty_ Rewrite in Kotlin (with Anko?)
- [ ] _Nightmare!_ Rewrite in Scala


## Features

### Version 1
* View popular, top rated and favorite movies
* View trailers and reviews
* Add and remove favorites

### Roadmap
* Proper tablet layout
* Search
* Browse by genre
* See similar movies
* User authentication
* Recommendations and similar movies
* Rate movies 
* View lists and favorites (on TMDB)
* Manage lists and favorites
* whatever else is in the API...

## Usage
Put a [TMDB](https://www.themoviedb.org/) V3 API key in your gradle.properties file. Enjoy.
