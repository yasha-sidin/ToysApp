# ToysApp

All app relies on ToysMachineApiClass. This app has two frames which imagine two apps. These are 
owner app and client app. 

Owner app helps to
add toys into Toys Machine storage using toy name, 
probability of one toy and count of this instance, see all toys in
storage, deleting and update them by number from list. Also, you can 
add new settings into your app. First of all, there is minimal count of toys which provides
playing logic's switching off. The second one is cost of one playing choice. You can change these 
two params using button 'Settings'. All activities in owner app influence on client app. For example, 
if amount of toys in Toys Machine's storage less than minimal amount of toys in settings you can't 
switch on Client app(Will be only one message which informs you about that) and can't use playing choice
if amount of toys in storage decreases to this minimal amount during using new choice
one by one. 

Client app. Firstly you need to add money into Toys Machine
to use playing choices. You can win a prize in 
dependence on probability which were reserved for every toy in machine and will be informed about the result. All prizes will be saved into special queue. After that you need to push on button 'Get toy' to get prize from queue. All toys will be saved into special client file, info from that you can see by using 
button 'See prizes'. If you close window of client app without getting toys all toys in queue will be added to client file automatically,
and you will be informed about that. Also, you will be necessarily informed about your change.

## Technologies

You need to use dependencies from pom.xml in this repo and install this tool before you run app:

1. Postgresql
   * https://www.postgresql.org/download/

In this project I use only java as a programming language, hibernate framework for work with database postgresql, log4j for logging connection to database, java swing framework for GUI.

Also, you need to make database toysdb using postgresql and create a new user in postgres-server, give him all privileges for this new db, or you can use main postgres user. After that create new table in db executing this script:
````
CREATE TABLE IF NOT EXISTS public.toysapp
(
    id integer NOT NULL DEFAULT nextval('toysapp_id_seq'::regclass),
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    probability real NOT NULL DEFAULT 0,
    CONSTRAINT toysapp_pkey PRIMARY KEY (id)
)
````
Pay attention to directory 'resources'. You can see hibernate.cfg.xml and
log4j.properties files where. These files help to working hibernate framework using logging.
You need to fill special params in hibernate.cfg.xml for working your app. They are database where you execute script: toysdb, user: ex. postgres,
password: ******. For example: 
````
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="connection.url">jdbc:postgresql://localhost:5432/toysdb</property>
        <property name="connection.driver_class">org.postgresql.Driver</property>
        <property name="connection.username">YOURUSER</property>
        <property name="connection.password">YOURPASSWORD</property>
        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.format_sql">true</property>
        <property name="hibernate.highlight_sql">true</property>

        <mapping class="model.entity.Toy"/>

    </session-factory>
</hibernate-configuration>
````
## Running
You need to run OwnerApp.java for working Owner app. Please do it at first because you won't be able to 
start working with Client app. Add toys into Toys machine. Run ClientApp.java for working Client app. Let's play!
