# MyCards

MyCards is a flashcard application for learning Japanese. The difference from normal flashcard applications is that 
instead of requiring that you source the words for the flashcards and make them manually yourself, the app will take 
the words you put in, find related ones and create the cards for you. The premise is that the app will ask you for 
information about yourself (what is your job? Hobbies? Favourite subject at school?). It will use those words to find
relevant words that you can learn to use in a conversation about yourself with a Japanese speaker.

## System Requirements
+ Your Android device must be at least Android SDK Level 30. 
  Some of MyCards core functionality requires this level of API, so we cannot guarantee 
  that it will work on lower versions. 

## App Features
+ //TODO

## Upcoming Features

### Priority
+ ~~Use Room preload feature to load the jmdict local db.~~ Completed (DATE)
+ Implement count of total cards in CardDisplayFragment.
+ Implement 'back' function in CardDisplayFragment, so users can navigate forwards and backwards within a Deck.
+ Move to a better finish screen (this may be as complex as using child/parent fragments).

### Non-priority
+ Implement an Activity-hosted AppBar to store Settings (Maintenance) page, return to Home (InputFragment) easily,
  and show (TODO - new) licences/about page. See: https://developer.android.com/guide/fragments/appbar?hl=en&authuser=1.
+ Add a splash screen at start up/some pre-load screen while Room loads the jmdict database
+ Add card counter to Deck screen. Complexity to consider: syncing if Cards are deleted. 
  Atm, Cards and Decks are entirely independent (deleting Cards does not delete/alter their Decks;
  deleting Decks does not remove the Cards).
  
### Non-functional improvements
+ Use View Binding to replace findViewById. Can improve performance/prevent memory leaks: 
  https://developer.android.com/topic/libraries/view-binding.
+ Migrate from Dagger to DaggerHilt and reduce boilerplate code. 
  This will also help with implementing SavedStateHandle in ViewModel.
+ Leverage the power of new Android class SavedStateHandle in ViewModel to handle Fragment state which is not complex
  and should be stored beyond app process destruction (after VM is destroyed) but is too trivial to be persisted to local store.  
+ Investigate how to manipulate Fragment lifecycle to enable postponed Fragments between Fragment transitions,
  so that when loading takes time (eg remote server API call), the whole Fragment can be postponed until ready.
  
### Other Ideas 
+ Ways to pre-process user input Strings for better results:
  + Spellcheck function OR
  + Dropdown that shows pre-loaded words from jmdict db (so autocomplete function), 
    to encourage users to input only those words that would have a jmdict entry.  
    
## Licences

Testing mirror git repo