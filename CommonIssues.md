# Common Issues #

A couple of common issues pop up from time to time. See below for a likely resolution to your problem.

  1. Server-side java.lang.SecurityException: Blocked request without GWT permutation header (XSRF attack?).
    * This occurs because the local (Android) app tried to read the .gwt.rpc file from the server, and probably did not find it. GWT compile the web app and make those files available to to remote users. Make sure to restart the web-app after these files are made available
  1. Client-side RemoteServiceInvocationError or
    * Make sure the type that is throwing the error is implementing Serializable or IsSerializable and has a default constructor.
  1. SerializationException: Invalid type signature for some.package.
    * If you are receiving this error from the Android client, make sure your server is using the same version of GWT as the SPA-Library you are using. Because the Android library is compiled with GWT and not against it, if you are using a different version of GWT in your library source code, the RPC system recognizes the differences. The Android library is compiled with GWT due to some customized files from GWT that are overridden in order to reduce the size of the library to function quickly and correctly in the Dalvik environment. Use SPA 0.4 for GWT 2.5 and SPA 0.4.1 for GWT 2.5.1.
  1. NoClassDefFoundError on Service/ServiceAsync
    * Make sure your Service interfaces from the Web App are being properly compiled (not against) the Android project. The difference here is that if the Web app is just included as a reference project for code, the Service interfaces are not compiled into the APK. The service interfaces must be directly linked into the project (as if it was in the project itself). In Eclipse, this can be accomodated for by using the Link Source button in the Java Build Path > Source tab.
  1. Unable to find RPC Policy File
    * GWT-Compile your web-app, make sure your service is running where you expect it to be (deployed vs local testing urls), and if using GAE, make sure to alter your appengine-web.xml as noted in the [Android#Setup\_in\_Eclipse](Android#Setup_in_Eclipse.md) section. Essentially, you need to make sure that the .gwt.rpc files are available to access from the "remote" Android client.
  1. ECONNREFUSED Error
    * make sure pointing to 10.0.2.2:8888 for android loopback
  1. javax.`*` classes cannot be found
    * make sure included servlet-api.jar
  1. javax.servlet.ServletContext log: sampleServlet: ERROR: The serialization policy file '/appname/null.gwt.rpc' was not found; did you forget to include it in this deployment? AND/OR javax.servlet.ServletContext log: sampleServlet: WARNING: Failed to get the SerializationPolicy 'null' for module 'http://localhost:8888/appname/'
    * Make sure that your GWT client is creating these services otherwise they will not be whitelisted. Simply put, make a static GWT.create(Service.class) call from your GWT EntryPoint, even if the service is only used by Android. If GWT does not see it being used, it will not make the service accessible remotely. Make sure to clean and GWT-Recompile.
  1. `SafeHtml#maybeCheckCompleteHTML(String)` RuntimeException and Missing method `SafeHtmlHostedModeUtils#isCompleteHtml(String)`
    * In order to add the SafeHtml interfaces to the Android Client, we had to trim some of the dependencies that GWT contains. In this case, the streamparser system that these Utils utilized had to be eliminated, so Android client-side verification of Complete HTML is not available through SyncProxy, in testing or production mode.