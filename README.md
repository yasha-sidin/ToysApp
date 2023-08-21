# ToysApp

All app relies on ToysMacineApiClass. This app has two frames which imagine two apps. These are 
owner app and client app. Owner app helps to
add toys into Toys Machine storage using toy name, 
probability of one toy and count of this instance, see all toys in
storage, deleting and update them by number from list. Also you can 
add new settings into your app. First of all, there is minimal count of toys which provides
playing logic's swiching off. The second one is cost of one playing choice. You can change these 
two params using button 'Settings'. All activities in owner app influence on client app. For example, 
if amount of toys in Toys Machine's storage less than minimal amount of toys in settings you can't 
swich on Client app(Will be only one message which informs you about that) and can't use playing choice
if amount of toys in storage decreases to this minimal amount during using new choice
one by one. Client app. Firstly you need to add money into Toys Machine
to use plaing choices. You can win a prize in 
dependence of probability which were reserved for every toy in machine and will be informed about the result. All prizes will be saved into special queue. After that you need to push on button 'Get toy' to get prize from queue. All toys will be saved into special client file, info from that you can see by using 
button 'See toys'. If you close window of client app without getting toys all toys in queue will be added to client file automatically
and you will be informed about that. Also you will be necessarily informed about your change.

## Technologies

You need to use dependencies from pom.xml in this repo and install theese tools before you run app:

1. Postgresql
   * https://www.postgresql.org/download/

In this project I use only java as a programming language, hibernate framework, log4j for logging connecting to database, java swing framwork for GUI.

## Running


