# Global Waves - Part 3
**Credits: Dan-Dominic Staicu 321CAb 2023**

[Behind the scene:](https://youtu.be/5X0f4tF9Sl8?si=7F939ljX8TOdXuPE) https://youtu.be/5X0f4tF9Sl8?si=7F939ljX8TOdXuPE

## Overview - 3rd step
Continued the project the same GitHub repo and my own skeleton from the first 2 stages;

### Design patterns

   1. Extended with new commands that were implemented over the **command pattern** from the 1st stage of this project. It was easy to add new classes for each command.
It was necessary to have different classes for each command because their behaviour is generally different. For the output of the general data about the command, I have created a method that prints it;
In my opinion right now, only a few classes could have been spared. For example, Prev/Next or BuyPremium/CancelPremium could have been merged into one class;
Otherwise, I chose the **command pattern** in order to avoid the cyclomatic complexity created by a switch or many branches of if...else;
The **command pattern** is implemented by having a *Command* abstract class that is extended by the commands; It has the abstract method execute, implemented differently by each command;
In the main function, it keeps an Array of Command(s) after reading the input. Using a for loop, it iterates through all the commands and calls the execute() method of each in order to perform their actions;

   2. The **Singleton pattern** was implemented on the Library class because I want to have exactly one instance of the library (where I keep all the songs, albums, users, entities in general etc);
With this pattern it is easy to access the instance without passing it as an argument to the method. The execute() method of the commands still have the Library param because I implemented that before thinking about singleton and was easier t keep things like that.
It is implemented by having a private constructor in the Library. this is called only from inside by the method that initializes the library (at the beginning when imputing the data from the given library.json);
It gets the only created instance of library by using the getInstance static method;
bonus: chatGPT suggested me that I make this method "synchronized", even though it is not necessary in my project. This keyword is used for thread safety. It ensures only one thread can execute the method at a time.

   3. When creating a new user, I used the **factory pattern** because it creates a new User object (that can be a normal, host or artist) according to the input;
All types of users extend the abstract class User; the final class UserFactory has a method that returns an User object according to the given type;
It is only one method that returns an object of a subclass of User; the subclasses are not abstract, they have implementations for all their methods;

   4. The **strategy pattern** is used in multiple classes/interfaces; It is used whenever I have multiple objects of different type that have mutual features, but with different behaviour;
For example, the Searchable interface is implemented by a lot of classes: Album, Artist, Host, Playlist, Podcast and Song;
All of these can be found in the searchbar (for some cases, their Page, but the SearchBar should be able to find them);
They have mutual methods that behave differently. For example, the Song, Album and Playlist have to be able to be loaded in the queue of a music player;
The Song is loaded directly in the queue as the only element in the player. On the other side, for the Album or Playlist, this method clears the queue and adds all the songs from these collections in the queue;

   5. I tried implementing the **Observer pattern** over the existing code for notification. The User abstract class acts as the Observer (by having the method sendNotification), and the Artist and Hosts are the subjects(by having the methods subscribe and unsubscribe)
When the Subjects add new merch/events etc the observers (subscribers) are sent a notification that is kept in the NormalUser;

   Bonus:
   1. **Builder pattern** implemented by the Project Lombok library to reduce boilerplate code such as builders;
the annotation @Builder is used to automatically generate a static inner class in the classes that has methods corresponding to each field of the outer class;
The **builder pattern** is used to construct complex objects step by step; this pattern encapsulates the construction of an object so that the Jackson library can deserialize a JSON input into a class;
In my project, it is used for the commands;


## Implemented over my skeleton from the 2nd stage:

1. Wrapped and User Statistics
2. Monetization
3. Notifications
4. Recommendations
5. Extended Page Navigation

### Wrapped and Statistics
Created new class for every NormalUser that keeps track of all the listened tracks.
When wrapped is called, the statistics are calculated over the whole history (that also tracks the deleted audio files/collections)

### Monetization
When the user is free, I keep a history of the songs listened as free. When an ad break is happening, the revenue for each artist and song is calculated according to the given formulas
The free history is deleted and keeps track only on the following songs, until the next ad break and has to calculate again the revenues;

Same story for a premium user, we keep a premium history in parallel for the tracks listened as premium.
it calculates the revenue per listened song at the end, end meaning either the user cancels premium, either the program ends; the revenue is calculated for each song and artist according to the given formula.

### Notifications
Notifications are explained above, where I explained how I tried implementing Observer pattern;

### Recommendations
The recommendations are calculated when the command is inputted; according to the type of the recommendation, it searches over the library for songs, as described in the request of the project;
The recommendations are kept in the user in lists; It also keeps track of what was recommended last time, so it can call load it in the player when load recommendations is called;
loadRecommendations uses the load method that is used at the call of load command;



## Overview - 2nd step
Continued the project on the same repo and skeleton as the first.

### Implemented on my own skeleton from the 1st step

In the 2nd step of the project, there were the following new features added:
1. New types of commands: specific to Admin, Artist or Host
2. New types of users: Artist and Host
3. Pagination - HomePage, LikedContent Page, Host Page, Artist Page
4. New types of content: Album
5. New SearchBar functionality: search for Artist/Host Pages
6. Online/offline status of an user
7. New General stats

## Extending the project

### New types of commands
They were easy to extend because I was using the **command pattern** in the 1st step. I just had to create new classes that inherit the Command class and override the execute method.
All the classes of the commands implement the **builder pattern** by using the lombok annotation `@Builder` that automatically generates the builder class for the command.
It is required by the Jackson library in order to deserialize the input into a list of commands given in format JSON.
All the commands can be found in the app.commands package. They are organized in subpackages according to their functionality.
I have created a new method in the abstract class Command that handles the generic output of JSON that is common for all the commands.

### New types of users
I have decided to rewrite the User class from the 1st step and create 3 new classes: NormalUser, Artist and Host.
All of these classes inherit the User abstract class because they have common fields and at some point, I need to iterate through all of them in order to find if a user exists (regardless of its type).
All the classes used for the users are in the app.entities.userside package. There are split into 3 more packages according to their type.
Artist and Host have classes for their special items (Events, Merch, Announcements). The normalUser has the classes from the 1st step (Searchbar and Player).
Artist and Host implement the interface Searchable (that was previously called playable) because they can be searched in the app. This interface is also implemented by the audio collections and song, that can also be loaded into a player by using the default method loadInPlayer (overridden in every class);
I have implemented the **factory pattern** in order to create new users at the AddUser command.

### Pagination
Pages are part of the userside package because they are used by the users. They are implemented as an interface because they have the common method printPage.
Every Artist and Host has a specific Page assigned to them (by using a strong composition). The NormalUser has a reference to the page it currently is on.
The pages have fields that describe the content of the page (lists of songs, playlists etc) that are constructed by getting info from the library at the moment of interrogation.

### Album
It is defined as a class in the playable package. It is a collection of songs that can be played in a player. It implements the Searchable interface and has a list of Songs as audioFiles.
The addition and removal of the album from the library is handled in the AddAlbum and RemoveAlbum commands classes. They use functions from library in order to decide if the deletion is possible.
The loading/playing/shuffling etc. of the album is handled by the exact same mechanisms as the 1st step, because the Player is handling a Searchable object.

### SearchBar functionality
The SearchBar also handles only Searchable objects. Regardless of their subtype, because of using the **strategy pattern** they are all called by the same method, but handled differently, according to the request.
Using the handleSelect method, there is a different behaviour for every type of object that is selected from the search results.
Form now, there are 2 main categories: audioFiles vs Pages that request different approaches.

### Online/offline status of an user
I have modified the updateTime function from the library and how it was called from main. Now, it simulates the tame at every input for all the users, not only for the user that calls the command.
This way, I can easily check if an artist can be deleted (if nothing that he owns is playing in another playlist).
For handling if a user is offline I have just added one new field in the UserPlayer class. If it is offline, it is handled the same way as if it was paused, but without changing the parameter that keeps track of pause.

### New General Stats
I have created a new static class called Stats that contains all the methods for getting the general stats of the app.
They iterate through what is needed from the library at the time they are called and count/sort/filter what is needed.


## Design Patterns

Their use is explained above. Singleton and command were used/explained below in the readme about the 1st step

1. Singleton
2. Command
3. Factory
4. Strategy
5. Builder (lombok)


### Overview - 1st step
This project implements a simulation of a Music Player alike to Spotify with multiple users that can play songs, podcasts and create playlists.
Using OOP concepts, I created classes in order to describe the existing objects:

1. Abstract class 'Command' that is inherited by all the other app.commands. I have used the **command pattern** in order to encapsulate in an object all the data required for performing a given action (command).
   All the other app.commands inherit this class and override the execute method. This way, I can easily add new app.commands in the future. Here I am using the concept of **dynamic polymorphism**.
   The app.commands are organized in packages according to their functionality.
   Using the **Generics** concept, in my main function I am reading the input using Jackson in a List<Commands> (regardless of their subclass); this way I can easily iterate through the list and execute the app.commands according to their specific individual behaviour.

2. Library class that contains all the songs, podcasts and playlists. It is implemented as a singleton class, so that I can access it from anywhere in the code. It contains a list of all the songs, podcasts and playlists.

3. Playable interface that is implemented by the Song, Playlist and Podcast classes. It contains the app.common methods for these two classes. It's used for **dynamic polymorphism** because search is returning a List<Playable> that have mutual functions. At search, I don't care if the object is a song, podcast or playlist, I just want to know if it contains the given filters.

4. Abstract Class AudioFile that is inherited by the Song and PodcastEpisode classes. It contains the app.common methods for these two classes. It's used for **dynamic polymorphism** because UserPlayer has a List<AudioFile> that have mutual functions.

5. The UserPlayer class is a good example of **Composition** because it contains a lot of fields that answer to the question "has a ...?". Every Player has a SearchBar and a List of AudioFiles.
   I want to mention the Enum of RepeatStates used in this class that helps me keep track of the repeat states of the player.

6. In class SearchBar you can find an example of aggregation because it has a List of Playable objects that are not destroyed when the SearchBar is destroyed. This class handles the searching of a Player.
   It keeps track of the last search results and the last selected result from the search.
7. The User class is a clone of the UserInput class that also contains new fields such as the UserPlayer, a list of favouriteSongs and a list of followedPlaylists. (also an example of **composition**).

8. The classes in the app.common package are final and contain only static variables. They are constants used in the project.

### OOP CONCEPTS USED:
1. Constructors
2. References
3. Inheritance
4. Composition
5. Aggregation
6. Static Polymorphism
7. Dynamic Polymorphism
8. Parametric Polymorphism
9. Generics
10. Abstract Classes and Methods
11. Interfaces
12. Packages
13. Exceptions
14. Design Patterns
15. Static and Final classes, methods, variables
16. Enums
17. Lambda Functions
18. Overriding
19. Wrapper Classes for Primitive Types
20. Collections

### ChatGPT contribution
- Suggested the use of some basic concepts of functional programming in order to shorten the code and make it easier to be understood, such as map(), filter(), stream() and lambda expressions. It really made a huge difference in improving code's quality.
- Helped me use the Jackson library for reading the input of app.commands and writing the output.
- Helped me use the Command Pattern for the app.commands.
- Helped with the refactoring of the code for better use of streams


### GitCopilot contribution
- Suggested the use of the Singleton Pattern for the Library class.
- Written some of the java doc (after giving some examples of how to write it).
- Suggested the use of enhanced switch statements.