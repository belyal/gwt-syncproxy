

# Android Library #

The SyncProxy Android library (**SPALibrary**) version of the **gwt-syncproxy** project was created to give the same gwt-syncproxy
functionality to Android devices, with the specific case of being able to utilize Android's internal account manager system
for communicating to Google App Engine services. This library gives you the ability to use available Google Accounts on the device
as the account to login to your web-app with. Additionally, in the testing phase, the library give developers the ability to send
fake accounts to the local GAE Dev server, much in the same way that the dev server creates a fake login URL. Thus far, I've used this
successfully in development, but I don't yet have this in a production app, so please let me know if you come across any bugs or issues.
This android interface was created from reviewing and integrating ideas found from the following sources:
[Authenticating against App Engine from an Android App](http://blog.notdot.net/2010/05/Authenticating-against-App-Engine-from-an-Android-app).

The library currently provides support for GAE backend logins. However, if you are not using a backend (including GAE) that requires a login,
the system performs nearly identically to the standard Java Library's functionality. However, it is still recommended to review the AsyncTask
section on calling RPCs as any RPC call still needs to be conducted away from the main thread, see [Android#RPC\_in\_Android](Android#RPC_in_Android.md). The library requires
API 14 as a minimum specifically for the token retrieving ability,
[java.lang.String, android.os.Bundle, android.app.Activity, android.accounts.AccountManagerCallback<android.os.Bundle>, android.os.Handler) AccountManager#getAuthToken()](https://developer.android.com/reference/android/accounts/AccountManager.html#getAuthToken(android.accounts.Account,)

~~The current BETA version of the library has one dependency that the developer must include in the libs/ path of their Android project.
It is your server's backend `servlet-api.jar`. Essentially this is the jar that contains your javax. classes. In a regular java project,
these classes are provided within the `gwt-user.jar`. However, since this jar is much larger than we need for Android functionality,
in order to reduce the result library jar size (and ultimately the APK size), we need to have that jar as a separate library. This
technique (as compared to the Alpha development version (see strikethrough below) has brought the sample project APK size down from
approx 2.2MB to 350KB.~~

~~The current development version of the library depends on gwt-servlet.jar, which is in the src/lib for the **SPALibrary** project, or can be
retrieved from any GAE generated project. Once I get the project layout and some other bugs squared away, I'll be introducing linked sources
directly into the SPALibrary so that the created SPALibrary.jar will not depend on the gwt-servlet.jar. Additionally, with the linked sources,
the footprint of this system will drop dramatically. It's currently at about 2mb (when integrated with the target app), and based on prior testing,
I expect that to drop to a range of about ~200K, maybe less. The reason for the dramatic drop is that referencing gwt-servlet pulls in a number
of continuously referenced files within those sources, that have no actual bearing on this library. With a few modifications to a couple of the
sources high up in the hierarchy, I can cut off the deep references that pull so many sources into the library. It will take some time to get
a proper set of these linked sources and modifications, but should be possible now that adequate testing is place.~~

### EnumMap Not Supported on Android ###

The EnumMap object is not supported on Android because the Serializer provided with GWT serializes a different version of the EnumMap class than
is provided in the Android OS.

## Setup in Eclipse ##

The No UI Utilization tag indicates that the developer will not be needing the test Account selection system provided in the library (See
[Android#Mock\_Android\_Account](Android#Mock_Android_Account.md). If you intend to use the mock account system included in the library, you must have that compilation set
available to eclipse as a library project (not just the `spalibrary.jar`).

Since I use Eclipse as my IDE, I'll describe the easiest setup for it here, but a similar process should work for most IDE's. As an Android
library, you must drop this library in the `libs/` path of your Android project. After that, the easiest way to reduce code duplication
is to Source-Link your gwt services Async files and shared paths. In your Android project's build path, Source Tab, click Link-Source. Point
the path to your GWT/GAE `project/src` path and give it a name, like _GWTServices_. Then, add the following inclusion filters:

```
**/*Service.java
**/*ServicesAsync.java
**/shared/**
```

Lastly, the GWT-App must be GWT-compiled to function in Dev Mode and the following line must be modified in appengine-web.xml on line 19
(if using GAE):

```
<exclude path="**.gwt.rpc" /> 
	to
<include path="**.gwt.rpc" />
```

Now your Android project has everything it needs to know about your GWT/GAE project's services and shared files.

## Android Account Selection ##

The usage of the library on an Android platform is similar to the Java style, with the exception that you cannot send network data on the
main thread. The library compensates for this in the background, so you shouldn't have to do anything too specific to work around this problem.
The **SPALibrary** contains a modified version of the LoginUtils.java to login to GAE which will be described below. The login mechanism is dependent
on having an Account object available to utilize for creating the cookie that will be sent along with RPCs. The MainActivity class in the
**SPAAppTest** project has a working mockup of the mechanism used to utilize Accounts and work with RPCs (against the server's greetServer method).
The **SPALibrary** has a very simplistic account selector that can be utilized, or you may create and use your own to select accounts. The only
benefit to the included account selector is that it allows you to create fake accounts to send during Dev mode. In either case, using this Account,
you will be given a java.net.CookieManager which will be used for all _newProxyInstance_ calls.

All Android GAE Login's utilizing an Account need to create a listener to respond when the login is ready to proceed. It should implemented in some
fashion to the code below, and it used as the listener parameter in the _loginAppEngine_ method calls described in the following sections.

```
		listener = new CookieManagerAvailableListener() {
			@Override
			public void onAuthFailure() {
				// TODO Handle Authentication Failure
				throw new RuntimeException("Authentication Failed");
			}

			@Override
			public void onCMAvailable(CookieManager cm) {
				// TODO CM Available, continue with your RPC Request
			}
		};
```

### Google App Engine Login ###

In Android, Account tokens can be invalid, so this library automatically does a single retry for logging in after invalidating the account's
auth token if a failure occurs. Specifically, the accountManager#invalidateAuthToken() method is called with the appropriate parameters, and then
sends another request to get the the authtoken. See the GAEAMCallback class for details.

To start, Set the target url, _LoginUtils#setLoginUrl()_, to be Android's host loopback interface (10.0.2.2:8888). See
[Android Emulator Networking](http://developer.android.com/tools/devices/emulator.html#emulatornetworking) for more details. Also Then login with the following parameter descriptions, _LoginUtils#loginAppEngine(Account parent, CookieManagerAvailableListener listener, Account account)_.

| Parameter | Value | Purpose |
|:----------|:------|:--------|
| parent | usually `this` | Provides the application context and also represents the Activity to be recalled once an Account has been selected |
| listener | A localized CMAL implementation | This listener is called once the Login is completed, indicating that your app should continue with the provided CookieManager |
| account | A Google Account representing the user that wishes to be logged in. | As expected |

Once making this call, your system should continue when the listener is called with the CookieManager (`cm`). This `cm` will then be
used in RPC calls as follows:

```
	rpcService = (GreetingServiceAsync) SyncProxy.newProxyInstance(
				GreetingServiceAsync.class, "http://10.0.2.2:8888/spawebtest/",
				"greet", cm);
```

#### RPC in Android ####

Again, in Android, any network call needs to be done off the main thread, so you can use the following style to create easy-to-manage calls:

```
class RPCAction extends AsyncTask<MainActivity, Void, MainActivity> {
	GreetingServiceAsync rpcService;
	CookieManager cm;

	public RPCAction(CookieManager cm) {
		this.cm = cm;
	}

	@Override
	protected MainActivity doInBackground(MainActivity... act) {
		// TODO Your rpcService instantiation here utilizing the CookieManager cm
		return act[0];
	}

	@Override
	protected void onPostExecute(final MainActivity act) {
		// TODO Your RPC Call here
	}
```

Your RPC Call should also remember to make UI changes on the UI thread. Note the _#runOnUiThread()_ call to make UI changes. You can
utilize a setup as follows within the _onPostExecute_:
```
		rpcService.greetServer(((EditText) act.findViewById(R.id.input))
				.getText().toString(), new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(final ArrayList<String> result) {
				act.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Update UI
					}
				});
			}
		});
```

During any given session, once you receive the CM, it is easier to store that CM (probably at the Application level or in a Singleton, depending
on your side of the fence) so that it can be utilized for any RPC during that session without having to _loginAppEngine_ in every separate
activity. However, it should be tested for an handled not to utilize a stale CookieManager between sessions, and if any rpc request fails, it
is recommended to perform an automatic re-login before deciding that there is another underlying issue present.

### Mock Android Account ###

The ability to create mock accounts is designed for use only in Dev mode. Fake Accounts are tagged as LoginUtils#TEST\_ACCOUNT\_TYPE for the account
type. These account types will not work unless you are in dev mode (as defined by loginUrl being set to the loopback interface). Attempts to do so
will send up a stating the issue and will throw an AccountsException if you are not utilizing the AccountSelector.

In order to use the built in account selection system for creating mock accounts, you have to checkout the source for the `SPALibrary`
and reference it as an Android Library Project within Eclipse (See SourceAndTesting). This is because I haven't found any information online about how to include
Android resource files (such as layout.xml and string.xml) in with the spalibrary.jar that is created for the library. If anyone knows how to
do this, enter a ticket or contact me and I'll revise the functionality.

To utilize the ability to mock Android accounts, make the call to _LoginUtils#useAccountSelector(true)_ prior to making a login request.
Once completed, make the request to login to GAE, _LoginUtils#loginAppEngine(Activity parent, CookieManagerAvailableListener listener, Account account, int requestCode)_.
See the parameter descriptions in  [Android#Google\_App\_Engine\_Login](Android#Google_App_Engine_Login.md) and below:

| Parameter | Value | Purpose |
|:----------|:------|:--------|
| account | In context of getting a mock Account, `null` | When trying to get a mock account, you don't need to send in an account because it will be created for you |
| requestCode | A Local integer that can be used to reference an ActivityResult when returning (see below) | Provides a reference to the account selection activity return |

Since the Account selector is a separate activity, your activity must be able to receive data from the AccountSelector activity when an account
(fake or otherwise) has been selected. This is done be implementing the following method:

```
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MY_REQUEST_ID) {
			if (resultCode == RESULT_OK) {
				Account account = (Account) data.getExtras().get(
						LoginUtils.ACCOUNT_KEY);
				try {
					LoginUtils.loginAppEngine(this, listener, account);
					waitForCM = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
```

In the above method, note the MY\_REQUEST\_ID field, which should be the same field sent in the first call to _loginAppEngine_. Also note
the use of the _waitForCM_ boolean value, which is used to prevent your activity's onResume() from triggering a continuous loop if you attempt
Login/Account selection as part of your Activity's startup sequence. See the MainActivity example for more details.