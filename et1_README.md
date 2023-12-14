# Global Waves
**Credits: Dan-Dominic Staicu 321CAb 2023**

https://www.jsondiff.com/

[Behind the scene:](https://youtu.be/5X0f4tF9Sl8?si=7F939ljX8TOdXuPE) https://youtu.be/5X0f4tF9Sl8?si=7F939ljX8TOdXuPE

## Overview
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

## OOP CONCEPTS USED:
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
14. Design Patterns - Command Pattern, Singleton Pattern
15. Static and Final classes, methods, variables
16. Enums
17. Lambda Functions
18. Overriding
19. Wrapper Classes for Primitive Types

## ChatGPT contribution
- Suggested the use of some basic concepts of functional programming in order to shorten the code and make it easier to be understood, such as map(), filter(), stream() and lambda expressions. It really made a huge difference in improving code's quality.
- Helped me use the Jackson library for reading the input of app.commands and writing the output.
- Helped me use the Command Pattern for the app.commands.


## GitCopilot contribution
- Suggested the use of the Singleton Pattern for the Library class.
- Written some of the java doc (after giving some examples of how to write it).
- Suggested the use of enhanced switch statements.