# hardcoreparkour

##Built using IntelliJ IDEA
https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA

##Template created with libGDX
- https://libgdx.badlogicgames.com

Instructions for setting up libGDX can be found on that site as well


##Game developement walk through video on YouTube
- https://www.youtube.com/watch?v=85A1w1iD2oA&list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK&index=1

##Lots of other cool project tutorials by the same dude:
- https://www.youtube.com/user/ForeignGuyMike/playlists

##Integrating IntelliJ with git
- https://www.jetbrains.com/help/idea/2016.2/using-git-integration.html

One thing that is very cool. If you have set up git for yor project 
directory the old fashion way, IntelliJ will automatically detect the 
git files and allow you to add/commit/push your code through the IDE by
clicking and keyboard shortcuts. Still ok to do ye olde way via CLI.

<<<<<<< HEAD
##Compile and run in browser
- ./gradlew html:superDev

##Compile for web server deployment
- ./gradlew html:dist
- web application can be found in html/build
- you can test application locally by running a light weight python based web server
  - python -m SimpleHTTPServer
  - point browser to localhost:8000
- when you are ready to push to cf, create a war of all the contents in /html/build
  - jar cvf app.jar * (from inside the build folder)
- now you can push the jar to cf
  - cf push <app name> -p app.jar

#See my work in progress
- http://test2-lsl.cfapps.io/
- press 'z' to make the box jump
=======
Android SDK Path on Windows: C:\Users\<user>\AppData\Local\Android\android-sdk

