# Global Waves - Part 2
**Credits: Dan-Dominic Staicu 321CAb 2023**

[Behind the scene:](https://youtu.be/5X0f4tF9Sl8?si=7F939ljX8TOdXuPE) https://youtu.be/5X0f4tF9Sl8?si=7F939ljX8TOdXuPE


## Overview - 2nd step
Continued the project ont the same repo and skeleton as the first.

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
All the classes of the commands implement the **builder pattern** by using the lombok addnotation `@Builder` that automatically generates the builder class for the command.
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