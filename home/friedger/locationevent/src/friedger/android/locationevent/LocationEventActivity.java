/* 
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package friedger.android.locationevent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentReceiver;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * Base for location event activities
 */
public class LocationEventActivity extends Activity {

	public class myIntentReceiver extends IntentReceiver {

		@Override
		public void onReceiveIntent(Context arg0, Intent arg1) {

			Object location = arg1.getExtra("location");
			// deal with update
			if (location instanceof Location
					&& ((Location) location).distanceTo(_startPoint) > _distance) {
				_locationManager.removeUpdates(_intent);
				_label.setText(String.valueOf(System.currentTimeMillis()));
			}
			// deal with proximity alert
			if (location == null) {
				_label.setText(String.valueOf(System.currentTimeMillis()));
			}
		}

	}

	private float _distance = 100;
	private final String LOCATION_EVENT = getClass().getName()
			+ ".LOCATION_EVENT";

	private TextView _label;
	private LocationManager _locationManager;
	private Handler _handler = new Handler();

	private TextView _location;

	private Runnable _updater = new Runnable() {
		public void run() {
			updateLocationText();
			// update current location
			_handler.postDelayed(_updater, 2000);
		};

	};

	private Location _startPoint;
	private Intent _intent;

	public LocationEventActivity() {
	}

	/**
	 * Called with the activity is first created.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		// Set the layout for this activity.
		setContentView(R.layout.location_event_activity);

		// remember the label to update
		_label = (TextView) findViewById(R.id.text);
		_location = (TextView) findViewById(R.id.location);

		// register intent receiver
		IntentFilter filter = new IntentFilter(LOCATION_EVENT);
		myIntentReceiver receiver = new myIntentReceiver();
		registerReceiver(receiver, filter);

		// get current location
		_locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		_startPoint = getCurrentLocation();

		updateLocationText();

		// register alert
		_intent = new Intent(LOCATION_EVENT);
		_locationManager.addProximityAlert(_startPoint.getLatitude(),
				_startPoint.getLongitude(), _distance, -1, _intent);
		_locationManager.requestUpdates(_locationManager.getProviders().get(0),
				100, 10.0f, _intent);
		// update current location
		_handler.postDelayed(_updater, 2000);
	}

	private void updateLocationText() {
		Location location = getCurrentLocation();
		_location.setText(location.getLatitude() + ":"
				+ location.getLongitude() + "("
				+ location.distanceTo(_startPoint) + ")");
	}

	private Location getCurrentLocation() {
		LocationProvider locationProvider = _locationManager.getProviders()
				.get(0);
		Location location = _locationManager
				.getCurrentLocation(locationProvider.getName());
		return location;
	}
}
