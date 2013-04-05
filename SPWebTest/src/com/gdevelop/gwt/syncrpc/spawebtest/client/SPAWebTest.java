/*
 * Copyright 2013 Blue Esoteric Web Development, LLC (http://www.blueesoteric.com/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 */
package com.gdevelop.gwt.syncrpc.spawebtest.client;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.junit.client.impl.JUnitHost;
import com.google.gwt.junit.client.impl.JUnitHostAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.CollectionsTestService;
import com.google.gwt.user.client.rpc.CollectionsTestServiceAsync;
import com.google.gwt.user.client.rpc.CoreJavaTestService;
import com.google.gwt.user.client.rpc.CoreJavaTestServiceAsync;
import com.google.gwt.user.client.rpc.CustomFieldSerializerTestService;
import com.google.gwt.user.client.rpc.CustomFieldSerializerTestServiceAsync;
import com.google.gwt.user.client.rpc.EnumsTestService;
import com.google.gwt.user.client.rpc.EnumsTestServiceAsync;
import com.google.gwt.user.client.rpc.InheritanceTestService;
import com.google.gwt.user.client.rpc.InheritanceTestServiceAsync;
import com.google.gwt.user.client.rpc.MixedSerializableEchoService;
import com.google.gwt.user.client.rpc.MixedSerializableEchoServiceAsync;
import com.google.gwt.user.client.rpc.ObjectGraphTestService;
import com.google.gwt.user.client.rpc.ObjectGraphTestServiceAsync;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.TestSetFactory;
import com.google.gwt.user.client.rpc.TestSetFactory.MarkerTypeArrayList;
import com.google.gwt.user.client.rpc.TypeCheckedObjectsTestService;
import com.google.gwt.user.client.rpc.TypeCheckedObjectsTestServiceAsync;
import com.google.gwt.user.client.rpc.UnicodeEscapingService;
import com.google.gwt.user.client.rpc.UnicodeEscapingService.InvalidCharacterException;
import com.google.gwt.user.client.rpc.UnicodeEscapingServiceAsync;
import com.google.gwt.user.client.rpc.UnicodeEscapingTest;
import com.google.gwt.user.client.rpc.ValueTypesTestService;
import com.google.gwt.user.client.rpc.ValueTypesTestServiceAsync;
import com.google.gwt.user.client.rpc.XsrfTestService;
import com.google.gwt.user.client.rpc.XsrfTestServiceAsync;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Preethum
 * @since 0.4
 */
public class SPAWebTest implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	public final LargePayloadServiceAsync payloadService = GWT
			.create(LargePayloadService.class);
	public final CollectionsTestServiceAsync collectionsService = GWT
			.create(CollectionsTestService.class);
	public final CoreJavaTestServiceAsync coreJavaService = GWT
			.create(CoreJavaTestService.class);
	public final EnumsTestServiceAsync enumTestService = GWT
			.create(EnumsTestService.class);
	public final CustomFieldSerializerTestServiceAsync customFieldSerService = GWT
			.create(CustomFieldSerializerTestService.class);
	public final InheritanceTestServiceAsync inheritanceService = GWT
			.create(InheritanceTestService.class);
	public final ObjectGraphTestServiceAsync objectGraphService = GWT
			.create(ObjectGraphTestService.class);
	public final MixedSerializableEchoServiceAsync mixedEchoService = GWT
			.create(MixedSerializableEchoService.class);
	public final TypeCheckedObjectsTestServiceAsync typeCheckedService = GWT
			.create(TypeCheckedObjectsTestService.class);
	public final UnicodeEscapingServiceAsync unicodeService = GWT
			.create(UnicodeEscapingService.class);
	public final XsrfTestServiceAsync xsrfTestService = GWT
			.create(XsrfTestService.class);
	public final JUnitHostAsync junitService = GWT.create(JUnitHost.class);
	public final ValueTypesTestServiceAsync valuesService = GWT
			.create(ValueTypesTestService.class);

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {

		try {
			UnicodeEscapingTest.verifyStringContainingCharacterRange(0, 0,
					new String());
		} catch (InvalidCharacterException e) {

		}
		new GWTTestCase() {

			@Override
			public String getModuleName() {
				return null;
			}
		};
		ServiceDefTarget serTarget = (ServiceDefTarget) unicodeService;
		serTarget.setServiceEntryPoint("/spawebtest/unicodeEscape");
		unicodeService.getStringContainingCharacterRange(0, 1,
				new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {

					}

					@Override
					public void onSuccess(String result) {

					}

				});
		// TestSetValidator.isValidComplexCyclicGraph(TSFAccessor
		// .createComplexCyclicGraph());
		coreJavaService.echoMathContext(
				new MathContext(5, RoundingMode.CEILING),
				new AsyncCallback<MathContext>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(MathContext result) {

					}

				});
		collectionsService.echo(TestSetFactory.createArrayList(),
				new AsyncCallback<ArrayList<MarkerTypeArrayList>>() {
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(ArrayList<MarkerTypeArrayList> result) {

					}
				});
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			@Override
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				// if (!FieldVerifier.isValidName(textToServer)) {
				// errorLabel.setText("Please enter at least four characters");
				// return;
				// }

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");

				// Greet Server for General T1 Object
				// greetingService.greetServer(textToServer,
				// new AsyncCallback<T1>() {
				// @Override
				// public void onFailure(Throwable caught) {
				// // Show the RPC error message to the user
				// dialogBox
				// .setText("Remote Procedure Call - Failure");
				// serverResponseLabel
				// .addStyleName("serverResponseLabelError");
				// serverResponseLabel.setHTML(SERVER_ERROR);
				// dialogBox.center();
				// closeButton.setFocus(true);
				// }
				//
				// @Override
				// public void onSuccess(T1 result) {
				// dialogBox.setText("Remote Procedure Call");
				// serverResponseLabel
				// .removeStyleName("serverResponseLabelError");
				// serverResponseLabel.setHTML(result.getText());
				// dialogBox.center();
				// closeButton.setFocus(true);
				// }
				// });
				// Greet Server for ArrayList<String>
				greetingService.greetServerArr(textToServer,
						new AsyncCallback<ArrayList<String>>() {
							@Override
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							@Override
							public void onSuccess(ArrayList<String> result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result.get(0));
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
}
