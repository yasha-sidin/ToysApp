# ToysApp

All app relies on ToysMacineApiClass. This app has two frames which imagine two apps. These are 
owner app and client app. Owner app helps to
add toys into Toys Machine storage using toy name, 
probability of one toy and count of this instance, see all toys in
storage, deleting and update them by number in list. Also you can 
add new settings into your app. First of all, there is minimal count of toys which provides
playing logic's swiching off. The second one is cost of one playing choice. You can change these 
two params using button 'Settings'. All activities in owner app influence on client app. For example, 
if amount of toys in Toys Machine's storage less than minimal amount of toys in settings you can't 
swich on Client app(Will be only one message which informs you about that) and can't use playing choice
if amount of toys in storage decreases to this minimal amount during using new choice
one by one. Client app. Firstly you need to add money into Tous Machine
to use plaing choices. You can win a prize in 
dependence of probability which were reserved for every toy in machine and will be informed about result. 

## Technologies

You need to install theese tools before you run app(exaples of installation on windows):

1. Python 3.11.4
    * https://www.python.org/

2. Tkinter library for python
    * In command prompt: pip install tk

3. Datetime and dateutil libraries for python
    * In command prompt: pip install python-dateutil

4. MySQL server
    * https://dev.mysql.com/doc/refman/8.0/en/windows-installation.html

5. mysql library for python
    * pip install mysql-connector-python

## Running

You need to run main.py for working app.
First of all you need to log in MySql.
After that will be created database in MySql with name 
"new_notes_big_data" and table "your_notes" in it. All your notes will be stored in database MySQL.
