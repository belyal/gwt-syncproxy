# SyncProxy Quick Start #

As of SyncProxy 0.5, GWT Style creation methods are available to create a service. [Android](Android.md) projects require a slightly different configuration
for creating the services since they need to be run on a different thread than the UI, so please see the [Android](Android.md) wiki for details. In both cases,
make sure you have the Service and ServiceAsync source files available to your project. Also make sure your Service classes implement the RemoteServiceRelativePath annotation.

### The service interface ###
For example, we have an helloApp application and our GreetingService:
```
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
  String greetServer(String name);
}
```

And the service implementation:
```
public class GreetingServiceImpl extends RemoteServiceServlet
      implements GreetingService {
  public String greetServer(String name) {
    return "Hello, " + name;
  }
}
```

## Client Code ##

```
	// Not specifically necessary, but makes it easier to utilize the create methods directly
	SyncProxy.setBaseURL("http://127.0.0.1:8888/myapp");
	// Note you do not need to cast
	GreetingServiceAsync greetService = SyncProxy.create(GreetingService.class);
	// Note that in both cases, the non-Async interface is the provided class
	GreetingService greetServiceSync = SyncProxy.createSync(GreetingService.class); 
```

## Customized Proxy Creation ##

The SyncProxy library allows you to customize some of the details of creating the service using the SyncProxy#ProxySettings() object. So using the ProxySettings()
object, you can create services on the fly as such:

```
// Note in this case that createProxy takes the ServiceAsync class when that's the goal
GreetingServiceAsync greetService = SyncProxy.createProxy(GreetingServiceAsync.class, 
	new ProxySettings().setServerBaseUrl("http://127.0.0.1:8888/myapp") // Overrides SyncProxy.setBaseUrl if set
	.setRemoteServiceRelativePath("greetme") // Overrides the Annotation of the service
	.setCookieManager(myCookieManager); // Can have different sessions, uses default unless specified
// Note in this case that createProxy takes the Service class since that's the object wanted
GreetingService greetServiceSync = SyncProxy.createProxy(GreetingService.class, 
	new ProxySettings().setServerBaseUrl("http://127.0.0.1:3333/myapp2") // Overrides SyncProxy.setBaseUrl if set
	.setRemoteServiceRelativePath("greetmethere") // Overrides the Annotation of the service
	.setCookieManager(myCookieManager2); // Can have different sessions, uses default unless specified
```

If you typically use the ServiceDefTarget interface to specify the service's entry point, you may do that with the SyncProxy object as follows:

```
	// Suppress exception that the service interface doesn't have the RemoteServiceRelativePath annotation
	SyncProxy.suppressRelativePathWarning(true);
	GreetingServiceAsync greetService = SyncProxy.create(GreetingService.class);
	((ServiceDefTarget) greetService).setServiceEntryPoint(getModuleBaseURL()
		+ "greetingEntryPoint");
	// You may also use the ProxySettings() object to manually set the Entry Point as shown above #setRemoteServiceRelativePath
```

As of 0.5, SyncProxy now stores the settings for a given service within the InvocationHandler, and it is both retrievable and modifiable between
service calls. To access/change these settings, use the following example. You can of course provide your own implementation of HasProxySettings
instead of the provided ProxySettings class if needed.

```
	GreetingServiceAsync greetService = SyncProxy.create(GreetingService.class);
	CookieManager cm = ((HasProxySettings) greetService).getCookieManager();
	
```

## Service Calls ##

Asynchronous calls are made in the same way as in GWT, utilizing the same AsyncCallback class:

```
	greetService.greetServer("SyncProxy", new AsyncCallback(){
  		public void onFailure(Throwable caught) {
    		...
  		}
  		public void onSuccess(String result) {
    		...
  		}
	});
```

In the event that an asynchronous call is not what you want, as shown above, you can create a proxy based on the non-Async interface. In such a case,
you can easily place the following method call which will hang that thread until it returns from the server. Note that in this case, we are using the
greetServiceSync object, as created above.

```
	String result = greetServiceSync.greetServer("SyncProxy");
```

## Logging ##

As of SyncProxy 0.5, all logging is done using the java.util Logging system. There is a custom handler built into the Android library to convert these logs
for viewing within the LogCat viewer as well. To utilize the logger, simply do the following:

```
	// For Android	
	for (Class<?> clazz : SyncProxy.getLoggerClasses()) {
		FixedAndroidHandler.setupLogger(Logger.getLogger(clazz.getName()));
	}
	// Plain Old Java and Android
	SyncProxy.setLoggingLevel(Level.FINER); // You may choose any Level
```

## Multiple sessions ##

From version 0.3, SyncProxy uses standard Java java.net.CookieManager to manage client session. A bug preventing some cookies from automatically
being transferred has been fixed as of 0.5 for the POJ and Android libraries.

```
	CookieManager empSession = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
	CookieManager mgrSession = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
	SyncProxy.setBaseURL("http://example.com/helloApp");
	GreetingServiceAsync empRpcService = SyncProxy.create(GreetingService.class, 
		new ProxySettings().setCookieManager(empSession));
	GreetingService mgrRpcService = SyncProxy.createSync(GreetingService.class,
        new ProxySettings().setCookieManager(mgrSession));
```

# Legacy Support #

The following methods were made available in previous versions of SyncProxy, but have not been fully tested in the 0.5 release. The should continue
to function as before, but full support for these features will be upgraded in the next release: See [Roadmap](Roadmap.md).


## Invoke GWT RPC services deployed on AppEngine ##
For security-enabled RPC services deployed on AppEngine, we have to login before calling RPC methods.

```
CookieManager userSession = LoginUtils.loginAppEngine("https://example.appspot.com",
    "http://example.appspot.com/helloApp/greet",
    "emailaddress@gmail.com", "password");
```

Then invoke the RPC method:

```
private static GreetingService rpcService =
  SyncProxy.createSync(GreetingService.class,
        new ProxySettings()().setCookieManager(userSession));

String result = rpcService.greetServer("SyncProxy");
```