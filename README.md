
# Onramp Android Take Home Project
# Author Christopher Holmes

## Application Objective

The objective of this application is to store body weight workouts so a user can record their workouts.
The MVVM pattern is used to keep longstanding operations such as inserting data in to a database
away from the user interface.


## Material Design Components used

RecyclerView - used in MainActivity to display the workouts stored in the SQLite database

TextInputEditText - used to collect userInput for workout name, exercise name, and the exercises's reps and sets.

Material Card View - used in the recyclerview to display date name, and list of exercises for a workout

FloatingActionButton - used in MainActivity to prompt the new workout name fragment

AppBarLayout - used in MainActivity to harness options Menu for navigating to the Settings Activity

## MVVM pattern

The WorkOutViewModel class recieves input from the MainActivity class and the NewWorkOutFragment class.
The WorkOutViewModel proceeds to deliver user inputted data to the WorkOutRepo. The WorkOutRepo inserts
data into the WorkOutDao which is an interfacing implementing the android Room architecture. Room provides
a sqlite database for storing the users's workout history.

The Model View Viewmodel pattern is maintained through this application as a vast majority of business logic
is handled through the ViewModel and Respository. The functionalityt of the Activities inflate the XML files to the screen, take
user data, and hand it over to the ViewModel.


## Three Activities

The first activity the user encounters, LauncherActivity, is a splash screen which displays the application's
logo and purpose. Body weight training is placed to inform the user that only workouts which are not using weights are
going to be tracked.

![Imgur](https://i.imgur.com/biIczcrl.png)

The second activity, MainActivity, is contains an AppBarLayout, RecyclerView, and floating action button.
When the user adds new workouts with the floating action button, workouts are inserted in to the SQLite
Room database and then dispalyed in the recyclerview. There is also an options menu which provides access
to the third activity.

![Imgur](https://i.imgur.com/pwMsab2l.png)

The third activity, SettingsActivity, allows the user to set a time for daily notificaitons to take place.
A TimePicker and Switch are placed with a Button to allow the user to save their new settings. Snackbars
are used to inform the user when the settings have been changed.

![Imgur](https://i.imgur.com/IXPCFs1l.png)

# One Service

The service, NotificationService, is designed to give the user a daily notification to remind them to
work out. This service is called upon by a BroadcastReciever. The BroadcastReciever is managed by an alarm
manager which is called in the SettingsActivity.

# Two Fragments

There are two separate fragments used to collect user data and pass them to the ViewModel. The first fragment
the user interacts with is the NameWorkOutFragment which allows the user to give their new workout a name.
The second fragment collects the exercise, reps and sets that the user completes during their workout.

![Imgur](https://i.imgur.com/WVAqvHWl.png)

![Imgur](https://i.imgur.com/kNyQK3Rl.png)





