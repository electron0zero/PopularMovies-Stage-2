# Popular Movies App, Stage 2
Popular Movies App, Stage 2 App for  my Android Nano Degree Program from  [Udacity](https://www.udacity.com/)

## Building
go to [https://www.themoviedb.org/](https://www.themoviedb.org/) and get your API KEY

Put Your API KEY in (\app\src\main\res\values\)**strings.xml** file at this line **\<string name="API_KEY">your-api-key-here\</string>**

# Sources
[picasso](http://square.github.io/picasso/)
[https://www.themoviedb.org/](https://www.themoviedb.org/)

[https://developer.android.com/](https://developer.android.com/)

[http://stackoverflow.com/](http://stackoverflow.com/)

[http://ux.stackexchange.com/](http://ux.stackexchange.com/)

[https://www.reddit.com/r/androiddev](https://www.reddit.com/r/androiddev)

## SDK version

    minSdkVersion 19
    targetSdkVersion 23

## Screenshots

<p align="center">
<img src="https://raw.githubusercontent.com/electron0zero/PopularMovies-Stage-2/master/Screenshots/1.png" height="300">
<img src="https://raw.githubusercontent.com/electron0zero/PopularMovies-Stage-2/master/Screenshots/2.png" height="180">
<img src="https://raw.githubusercontent.com/electron0zero/PopularMovies-Stage-2/master/Screenshots/3.png" height="300">
<img src="https://raw.githubusercontent.com/electron0zero/PopularMovies-Stage-2/master/Screenshots/4.png" height="180">
<img src="https://raw.githubusercontent.com/electron0zero/PopularMovies-Stage-2/master/Screenshots/5.png" height="300">
<img src="https://raw.githubusercontent.com/electron0zero/PopularMovies-Stage-2/master/Screenshots/6.png" height="300">
<img src="https://raw.githubusercontent.com/electron0zero/PopularMovies-Stage-2/master/Screenshots/7.png" height="300">
</p>

## Known Issues/Miner Bugs/TODO

    important
##### if you have [PopularMovies-Stage-1](https://github.com/electron0zero/PopularMovies-Stage-1) from me installed and you install This app it won't work because of same package name for both apps.so remove that app first and then install this app. <i>"com.wordpress.electron0zero.popularmovies"<i> is package name if you want to check for it.


1. Multiple AsyncTask causes crash, for example we opened detail view of a movie and then before we able to fetch reviews and trailers for that movie switch to another movie and open it's details views and if we ask to fetch reviews and trailers for new movie and older one is still not completed (slow network conditions) , that results into a crash.
Solution : switch to sync adaptors and ditch the AsyncTask

2. after internal errors handle them gracefully with retry and notify user when we utterly failed so user can take action.

3. add support for notification when there is no network
