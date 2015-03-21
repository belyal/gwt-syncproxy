# Roadmap #

At current there are very few things on the roadmap for this project as it is relatively self-contained. However
as time permits, we will attempt to resolve the remaining issues (or close them if we cannot verify or get
further input). Additionally, below are some of the goals for the next version.

## 0.6 - Target Completion by Q3 2015 ##

  * Migrate App Engine login utilities to Google API from GData https://code.google.com/p/gdata-java-client/wiki/MigratingToGoogleApiJavaClient
  * Migrate library to Gradle base (AndroidStudio Compatibility pending resolution of issue https://code.google.com/p/android/issues/detail?id=64957)
  * Support for XSRF Servlets (RpcToken support was added in 0.5)
  * Verification and testing enhancement of the LoginUtil properties for GAE in POJ and Android Library
  * Conversion to Git Repository from current SVN
  * Support Code Splitting of Gwt systems - Patch available in [Issue 20](https://code.google.com/p/gwt-syncproxy/issues/detail?id=20) but needs significant testing to verify
  * Add CookieMode STRICT/LAX for whether or not SyncProxy will automatically populate the Cookie fields `domain` and `path` in RSSP

## 0.5 - Released Jan 2015 ##

  * ~~Update main gwt-syncproxy project to use Java Generics~~
  * ~~Fix some non-critical warnings in the source code throughout the project~~
  * Modify the system as needed when GWT 3.0 releases towards the end of this year (see gwtproject.com or the 2013 IO Presentation on the GWT Roadmap) **Delayed pending release of 3.0**
  * Update the Android library portion of this project to comply with the new Android Studio (if possible) **Delayed pending resolution of Android Tools Issue: https://code.google.com/p/android/issues/detail?id=64957**
  * ~~Mavenize~~ Expecting to use Gradle to allow compatibility with Android Studio for Andorid Library. Will also start publishing to maven-repo (or other alternative) **Delayed to 0.5.1**
  * ~~Cleanup unused tests dev files~~
  * ~~Increase testing Code Coverage to > 95% (currently approx 70% for gwt-syncproxy src)~~ Arbitrarily completed, significant testing enhancements in place
  * ~~Change download section to Google Drive in compliance with http://google-opensource.blogspot.com/2013/05/a-change-to-google-code-download-service.html~~ - Completed as of Version 0.4.2
