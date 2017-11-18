# Setting up this project

What I found when I joined this project:

+   Maven project.
+   An old report showing some code Metrics.
+   One silly test that checks nothing, but at least we can run JUnit!

## Build and Run

```bash
$ cd $PROJECT_ROOT    # The project root directory
$ mvn test
[One test failed, because it checks nothing.]
```

## Set Up IDE

IntelliJ IDEA 2017.2.5

+   Create new project of type "Maven" with no archetype and with the current Java version (Java 8 as of this writing).
+   IDEA says "There's already a pom.xml here". No problem.
+   Open the Maven tool window and refresh the project. By some miracle, it works.
+   Run the "test" task and see a test fail.

At this point, I seemed ready to write code.

## Inbox

+   Remove silly test to get the build back to "green".
+   Replace Maven with Gradle.
+   Create DMZ and Happy Zone modules.
+   Upgrade JUnit to the current version in the 4.x line.

## Open Questions

+   Do we care about the Metrics report? Should we make it part of our build to generate this report?
+   Which version of Java do we need to limit ourselves to or is Java 8 OK? What about Java 9?

