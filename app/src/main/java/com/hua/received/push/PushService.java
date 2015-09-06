//package com.hua.received.push;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.os.IBinder;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.ibm.mqtt.IMqttClient;
//import com.ibm.mqtt.MqttClient;
//import com.ibm.mqtt.MqttException;
//import com.ibm.mqtt.MqttPersistence;
//import com.ibm.mqtt.MqttPersistenceException;
//import com.ibm.mqtt.MqttSimpleCallback;
//
//import java.util.Date;
//
//import cn.mama.util.LogUtil;
//import cn.mama.util.MMApplication;
//import cn.mama.util.NotificationBabyPreganceyUtil;
//import cn.mama.util.PreferenceUtils;
//
///*
// * PushService that does all of the work.
// * Most of the logic is borrowed from KeepAliveService.
// * http://code.google.com/p/android-random/source/browse/trunk/TestKeepAlive/src/org/devtcg/demo/keepalive/KeepAliveService.java?r=219
// */
//public class PushService extends Service {
//	// this is the log tag
//	public static final String TAG = "mqtt";
//
//	// Let's not use the MQTT persistence.
//	private static MqttPersistence MQTT_PERSISTENCE = null;
//	// We don't need to remember any state between the connections, so we use a
//	// clean start.
//	private static boolean MQTT_CLEAN_START = true;
//	// Let's set the internal keep alive for MQTT to 15 mins. I haven't tested
//	// this value much. It could probably be increased.
//	private static short MQTT_KEEP_ALIVE = 60 * 5;//告诉mqtt服务器keep_alive值为5分钟
//	// Set quality of services to 0 (at most once delivery), since we don't want
//	// push notifications
//	// arrive more than once. However, this means that some messages might get
//	// lost (delivery is not guaranteed)
//	private static int[] MQTT_QUALITIES_OF_SERVICE = { 0 };
//    private static int[] MQTT_QUALITIES_OF_SERVICE_INCLUDE_CITY = { 0 ,0};
//	private static int MQTT_QUALITY_OF_SERVICE = 0;
//	// The broker should not retain any messages.
//	private static boolean MQTT_RETAINED_PUBLISH = false;
//
//	// MQTT client ID, which is given the broker. In this example, I also use
//	// this for the topic header.
//	// You can use this to run push notifications for multiple apps with one
//	// MQTT broker.
//	public static String MQTT_CLIENT_ID = "";
//
//	// These are the actions for the service (name are descriptive enough)
//	private static final String ACTION_START = MQTT_CLIENT_ID + ".START";
//	private static final String ACTION_STOP = MQTT_CLIENT_ID + ".STOP";
//	// destory
//	private static final String ACTION_DESTORY = MQTT_CLIENT_ID + ".DESTORY";
//	private static final String ACTION_KEEPALIVE = MQTT_CLIENT_ID
//			+ ".KEEP_ALIVE";
//	private static final String ACTION_RECONNECT = MQTT_CLIENT_ID
//			+ ".RECONNECT";
//
////	 Connection log for the push service. Good for debugging.
////	 private ConnectionLog mLog;
//
//	// Connectivity manager to determining, when the phone loses connection
//	private ConnectivityManager mConnMan;
//
//	// Whether or not the service has been started.
//	private boolean mStarted;
//
//	// This the application level keep-alive interval, that is used by the
//	// AlarmManager
//	// to keep the connection active, even when the device goes to sleep.
//	private static final long KEEP_ALIVE_INTERVAL = 1000 * 60 * 5;//本地闹钟也为5分钟ping一次
//
//	// Retry intervals, when the connection is lost.
//	private static final long INITIAL_RETRY_INTERVAL = 1000 * 10;
//	private static final long MAXIMUM_RETRY_INTERVAL = 1000 * 60 * 5;
//	private static final long ALL_RETRY_INTERVAL = 1000 * 60 * 25;
//
//	// Preferences instance
//	private SharedPreferences mPrefs;
//	// We store in the preferences, whether or not the service has been started
//	public static final String PREF_STARTED = "isStarted";
//	// We also store the deviceID (target)
//	public static final String PREF_DEVICE_ID = "deviceID";
//	// We store the last retry interval
//	public static final String PREF_RETRY = "retryInterval";
//	// ip and Port
//	public static final String PREF_IP_AND_PORT = "ipAndPort";
//	// channel
//	public static final String PREF_CHANNEL = "pushChannel";
//    // city activity channel
//    public static final String PREF_CITY_CHANNEL = "pushCityChannel";
//
//	// This is the instance of an MQTT connection.
//	private MQTTConnection mConnection;
//	private long mStartTime;
//
//	IMqttClient mqttClient = null;
//
//	// 连接线程
//	private AsyncTask<Void, Void, Void> connTask = null;
//
//
//    //活动主题前缀
//    public final static  String ACTIVITY_THEME_PRE = "common/city";
//    public final static  String ACTIVITY_THEME_AFE = "/activity";
//
//	// Static method to start the service
//	public static void actionStart(final Context ctx) {
//		new AsyncTask<Void, Void, Void>() {
//			@Override
//			protected Void doInBackground(Void... arg0) {
//				Intent i = new Intent(ctx, PushService.class);
//				i.setAction(ACTION_START);
//				ctx.startService(i);
//				return null;
//			}
//		}.execute();
//	}
//
//	// Static method to stop the service
//	public static void actionBegin(final Context ctx) {
//
////		new AsyncTask<Void, Void, Void>() {
////			@Override
////			protected Void doInBackground(Void... arg0) {
//				Intent i = new Intent(ctx, PushService.class);
//				i.setAction(ACTION_STOP);
////				ctx.startService(i);
//             ctx.sendBroadcast(new Intent("cn.mama.pushService.begin"));
//
////				return null;
////			}
////		}.execute();
//	}
//
//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if("cn.mama.pushService.begin".equals(intent.getAction())){
////                stop();
////                stopSelf();
//                initPushService();
//            }
//        }
//    };
//
//	public static void actionDestroy(final Context ctx) {
//
//		new AsyncTask<Void, Void, Void>() {
//			@Override
//			protected Void doInBackground(Void... arg0) {
//				Intent i = new Intent(ctx, PushService.class);
//				i.setAction(ACTION_DESTORY);
//				ctx.startService(i);
//
//				return null;
//			}
//		}.execute();
//	}
//
//	// Static method to send a keep alive message
//	public static void actionPing(Context ctx) {
//		Intent i = new Intent(ctx, PushService.class);
//		i.setAction(ACTION_KEEPALIVE);
//		ctx.startService(i);
//	}
//
//
//    private void initPushService(){
//        mPrefs = getSharedPreferences(TAG, MODE_PRIVATE);
//        mConnMan = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//
//        MQTT_CLIENT_ID = mPrefs.getString(PREF_CHANNEL, null);
//        setStarted(true);
//        mStarted = false;
//        handleCrashedService();
//
//    }
//	@Override
//	public void onCreate() {
//		super.onCreate();
//
//		log("Creating service");
//		mStartTime = System.currentTimeMillis();
//
//       IntentFilter intentFilter = new IntentFilter("cn.mama.pushService.begin");
//        registerReceiver(broadcastReceiver,intentFilter);
//		// try {
//		// mLog = new ConnectionLog();
//		// Log.i(TAG, "Opened log at " + mLog.getPath());
//		// } catch (IOException e) {
//		// Log.e(TAG, "Failed to open log", e);
//		// }
//
//		// Get instances of preferences, connectivity manager and notification
//		// manager
//		mPrefs = getSharedPreferences(TAG, MODE_PRIVATE);
//		mConnMan = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//
//		MQTT_CLIENT_ID = mPrefs.getString(PREF_CHANNEL, null);
//
//		/*
//		 * If our process was reaped by the system for any reason we need to
//		 * restore our state with merely a call to onCreate. We record the last
//		 * "started" value and restore it here if necessary.
//		 */
//		handleCrashedService();
//	}
//
//	// This method does any necessary clean-up need in case the server has been
//	// destroyed by the system
//	// and then restarted
//	private void handleCrashedService() {
//		if (wasStarted() == true) {
//			log("Handling crashed service...");
//			// stop the keep alives
//			stopKeepAlives();
//
//			// Do a clean start
//			start();
//		}
//	}
//
//	@Override
//	public void onDestroy() {
//		log("Service destroyed (started=" + mStarted + ")");
//
//		// Stop the services, if it has been started
//        if(broadcastReceiver!=null){
//            unregisterReceiver(broadcastReceiver);
//        }
//		if (mStarted == true) {
//			stop();
//		}
//
////		if(mConnectivityChanged != null)
////			unregisterReceiver(mConnectivityChanged);
//
//		// try {
//		// if (mLog != null)
//		// mLog.close();
//		// } catch (IOException e) {
//		// }
//	}
//
//	@Override
//	public void onStart(Intent intent, int startId) {
//		super.onStart(intent, startId);
//		log("Service started with intent=" + intent);
//		if (intent == null)
//			return;
//
//		NotificationBabyPreganceyUtil.remind(PushService.this, intent);
//
//		// Do an appropriate action based on the intent.
//		if (ACTION_STOP.equals(intent.getAction()) == true) {
//			stop();
//			stopSelf();
//		} else if (ACTION_START.equals(intent.getAction()) == true) {
//			start();
//		} else if (ACTION_KEEPALIVE.equals(intent.getAction()) == true) {
//			keepAlive();
//		} else if (ACTION_RECONNECT.equals(intent.getAction()) == true) {
//			log(ACTION_RECONNECT);
//			if (isNetworkAvailable()) {
//				reconnectIfNecessary();
//			}
//		} else if (ACTION_DESTORY.equals(intent.getAction()) == true) {
//			this.stopSelf();
//		}
//	}
//
//	@Override
//	public IBinder onBind(Intent intent) {
//		return null;
//	}
//
//	// log helper function
//	private void log(String message) {
//		log(message, null);
//	}
//
//	private void log(String message, Throwable e) {
//		if (e != null) {
//			LogUtil.i(TAG, message);
//
//		} else {
//			LogUtil.i(TAG, message);
//		}
//
//		// if (mLog != null) {
//		// try {
//		// mLog.println(message);
//		// } catch (IOException ex) {
//		// }
//		// }
//	}
//
//	// Reads whether or not the service has been started from the preferences
//	private boolean wasStarted() {
//		return mPrefs.getBoolean(PREF_STARTED, false);
//	}
//
//	// Sets whether or not the services has been started in the preferences.
//	private void setStarted(boolean started) {
//		mPrefs.edit().putBoolean(PREF_STARTED, started).commit();
//		mStarted = started;
//	}
//
//	private synchronized void start() {
//		log("Starting service...");
//
//		// Do nothing, if the service is already running.
//		if (mStarted == true) {
//			Log.w(TAG, "Attempt to start connection that is already active");
//			return;
//		}
//		alarmAvoidDisconn();
//		// Establish an MQTT connection
//		connect();
//
//		// Register a connectivity listener
////		registerReceiver(mConnectivityChanged, new IntentFilter(
////				ConnectivityManager.CONNECTIVITY_ACTION));
//	}
//
//	private synchronized void stop() {
//		// Do nothing, if the service is not running.
//		if (mStarted == false) {
//			Log.w(TAG, "Attempt to stop connection not active.");
//			return;
//		}
//		alarmAvoidDisconnCancel();
//		// Save stopped state in the preferences
//		setStarted(false);
//
//		// Remove the connectivity receiver
//		// unregisterReceiver(mConnectivityChanged);
//		// Any existing reconnect timers should be removed, since we explicitly
//		// stopping the service.
//		cancelReconnect();
//
//		// Destroy the MQTT connection if there is one
//		if (mConnection != null) {
//			mConnection.disconnect();
//			mConnection = null;
//		}
//	}
//
//	//
//	private synchronized void connect() {
//
//		LogUtil.i("mqtt", "连接mqtt");
//
//		log("连接中...");
//		// fetch the device ID from the preferences.
//		final String deviceID = mPrefs.getString(PREF_DEVICE_ID, null);
//
//		// IP+port
//		final String ipAndPort = mPrefs.getString(PREF_IP_AND_PORT, null);
//
//		// Create a new connection only if the device id is not NULL
//		if (deviceID == null) {
//			log("Device ID not found.");
//		} else if (ipAndPort == null) {
//			log("Ip and Port not found.");
//		} else if (MQTT_CLIENT_ID == null) {
//			log("channel not found.");
//		} else {
//			log("tcp://" + ipAndPort + "@" + deviceID);
//			try {
//				// 参数格式："tcp://" + brokerHostName + "@" + MQTT_BROKER_PORT_NUM
//				mConnection = new MQTTConnection(ipAndPort, deviceID);
//
//			} catch (Exception e) {
//                e.printStackTrace();
//			}
//
//			setStarted(true);
//		}
//	}
//
//	private synchronized void keepAlive() {
//		try {
//
//			LogUtil.i(TAG, "keepAlive");
//			// Send a keep alive, if there is a connection.
////			if (mStarted) {
////				mConnection.sendKeepAlive();
////			}
//			if(mqttClient != null)
//			{
//				LogUtil.i("mqtt", "去ping一下");
//				mqttClient.ping();
//			}
//			else
//			{
//				LogUtil.i("mqtt", "mqttClient为空了，重连一下");
//				connect();
//			}
//
//
//		} catch (MqttException e) {
//			e.printStackTrace();
//			log("MqttException: "
//					+ (e.getMessage() != null ? e.getMessage() : "NULL"), e);
//
//			if(mConnection != null)
//				mConnection.disconnect();
//
//			mqttClient = null;
//			mConnection = null;
//			cancelReconnect();
//		}
//	}
//
//	// Schedule application level keep-alives using the AlarmManager
//	private void startKeepAlives() {
//
//		LogUtil.i(TAG, "startKeepAlives");
//
//		Intent i = new Intent();
//		i.setClass(this, PushService.class);
//		i.setAction(ACTION_KEEPALIVE);
//		PendingIntent pi = PendingIntent.getService(this, 113, i, 0);
//		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
//		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
//				System.currentTimeMillis() + KEEP_ALIVE_INTERVAL,
//				KEEP_ALIVE_INTERVAL, pi);
//	}
//
//	// Remove all scheduled keep alives
//	private void stopKeepAlives() {
//		Intent i = new Intent();
//		i.setClass(this, PushService.class);
//		i.setAction(ACTION_KEEPALIVE);
//		PendingIntent pi = PendingIntent.getService(this, 113, i, 0);
//		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
//		alarmMgr.cancel(pi);
//	}
//
//	// We schedule a reconnect based on the starttime of the service
//	public void scheduleReconnect(long startTime) {
//		// the last keep-alive interval
//		long interval = mPrefs.getLong(PREF_RETRY, INITIAL_RETRY_INTERVAL);
//
//		// Calculate the elapsed time since the start
//		long now = System.currentTimeMillis();
//		// long elapsed = now - startTime;
//
//		// // Set an appropriate interval based on the elapsed time since start
//		// if (elapsed < interval) {
//		// interval = Math.min(interval * 4, MAXIMUM_RETRY_INTERVAL);
//		// } else {
//		// interval = INITIAL_RETRY_INTERVAL;
//		// }
//
//		interval = MAXIMUM_RETRY_INTERVAL;
//
//		log("Rescheduling connection in " + interval + "ms.");
//
//		LogUtil.i("mqtt", "下次重连时间：" + new Date(interval + now).toLocaleString());
//
//		// Save the new internval
//		mPrefs.edit().putLong(PREF_RETRY, interval).commit();
//
//		// Schedule a reconnect using the alarm manager.
//		Intent i = new Intent();
//		i.setClass(this, PushService.class);
//		i.setAction(ACTION_RECONNECT);
//		PendingIntent pi = PendingIntent.getService(this, 114, i, 0);
//		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
//		alarmMgr.set(AlarmManager.RTC_WAKEUP, now + interval, pi);
//	}
//
//	// Remove the scheduled reconnect
//	public void cancelReconnect() {
//		Intent i = new Intent();
//		i.setClass(this, PushService.class);
//		i.setAction(ACTION_RECONNECT);
//		PendingIntent pi = PendingIntent.getService(this, 114, i, 0);
//		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
//		alarmMgr.cancel(pi);
//	}
//
//	private void alarmAvoidDisconn() {
//		Intent i = new Intent();
//		i.setClass(this, PushService.class);
//		i.setAction(ACTION_RECONNECT);
//		PendingIntent pi = PendingIntent.getService(this, 115, i, 0);
//		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
//		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
//				System.currentTimeMillis() + ALL_RETRY_INTERVAL,
//				ALL_RETRY_INTERVAL, pi);
//	}
//
//	private void alarmAvoidDisconnCancel() {
//		Intent i = new Intent();
//		i.setClass(this, PushService.class);
//		i.setAction(ACTION_RECONNECT);
//		PendingIntent pi = PendingIntent.getService(this, 115, i, 0);
//		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
//		alarmMgr.cancel(pi);
//	}
//
//	private synchronized void reconnectIfNecessary() {
//		if (mStarted == true && mConnection == null) {
//			log("Reconnecting...");
//			LogUtil.i("mqtt", "重新连接mqtt");
//			connect();
//		}
//	}
//
//	// This receiver listeners for network changes and updates the MQTT
//	// connection
//	// accordingly
//	private BroadcastReceiver mConnectivityChanged = new BroadcastReceiver() {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			// Get network info
//			NetworkInfo info = (NetworkInfo) intent
//					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
//
//			// Is there connectivity?
//			boolean hasConnectivity = (info != null && info.isConnected()) ? true
//					: false;
//
//			log("push连接状态:" + (hasConnectivity ? "成功" : "失败")
//					+ " mConnection==null ?=" + (mConnection == null));
//
//			if (hasConnectivity) {
//				reconnectIfNecessary();
//			} else if (mConnection != null) {
//				// if there no connectivity, make sure MQTT connection is
//				// destroyed
//				mConnection.disconnect();
//				cancelReconnect();
//				mConnection = null;
//			}
//		}
//	};
//
//	// Check if we are online
//	private boolean isNetworkAvailable() {
//		NetworkInfo info = mConnMan.getActiveNetworkInfo();
//		if (info == null) {
//			return false;
//		}
//		return info.isConnected();
//	}
//
//	// This inner class is a wrapper on top of MQTT client.
//	private class MQTTConnection {
//
//		// Creates a new connection given the broker address and initial topic
//		public MQTTConnection(final String brokerHostName,
//				final String initTopic) throws MqttException {
//
//			if (connTask != null) {
//				try {
//					connTask.cancel(true);
//				} catch (Exception e) {
//					LogUtil.i(TAG, "取消连接线程失败！");
//				}
//			}
//			connTask = new AsyncTask<Void, Void, Void>() {
//				@Override
//				protected Void doInBackground(Void... arg0) {
//					try {
//						String mqttConnSpec = "tcp://" + brokerHostName;
//
//						if(mqttClient != null)
//							mqttClient.terminate();
//
//						// Create the client and connect
//						mqttClient = MqttClient.createMqttClient(mqttConnSpec,
//								MQTT_PERSISTENCE);
//						mqttClient.connect(MQTT_CLIENT_ID, MQTT_CLEAN_START,
//								MQTT_KEEP_ALIVE);
//
//						String uidAndToken = mPrefs.getString(PREF_CHANNEL,
//								null);
//						int result = mqttClient.publish("conn/" + uidAndToken,
//								"1".getBytes(), 1, false);
//
//						LogUtil.i("mqtt", "mqtt_publish:" + result);
//
//						// messages
//						mqttClient
//								.registerSimpleHandler(new MqttSimpleCallback() {
//
//									@Override
//									public void publishArrived(String arg0,
//											byte[] arg1, int arg2, boolean arg3)
//											throws Exception {
//										// Show a notification
//										String s = new String(arg1);
//										// showNotification(s);
//										log("Got message: " + s);
//
//										Intent intent = new Intent();
//										intent.setAction(MaPushInterface.ACTION_MESSAGE_RECEIVED);
//										intent.putExtra(
//												MaPushInterface.EXTRA_MESSAGE,
//												s);
//										sendBroadcast(intent);
//									}
//
//									@Override
//									public void connectionLost()
//											throws Exception {
//										log("Loss of connection,connection downed");
//										stopKeepAlives();
//										// null itself
//										mConnection = null;
//										if (isNetworkAvailable() == true) {
//											// reconnectIfNecessary();
//											scheduleReconnect(mStartTime);
//										}
//									}
//								});
//
//
//
//                        //订阅城市活动
//                        String site = PreferenceUtils.getUserSharePref(MMApplication.getAppContext(), PreferenceUtils.Site);
//                        if(!TextUtils.isEmpty(site)){
//                            subscribeToTopicAndCity(mPrefs.getString(PREF_CHANNEL, null), mPrefs.getString(PREF_CITY_CHANNEL, null));
//                        }else{
//                            subscribeToTopic(MQTT_CLIENT_ID);
//                        }
//
//					} catch (Exception ex) {
//						ex.printStackTrace();
//					}
//
//					return null;
//				}
//
//				@Override
//				protected void onPostExecute(Void result) {
//					super.onPostExecute(result);
//
//					mStartTime = System.currentTimeMillis();
//					startKeepAlives();
//				}
//
//			};
//
//			connTask.execute();
//
//		}
//
//		// Disconnect
//		public void disconnect() {
//			try {
//				stopKeepAlives();
//				if (mqttClient != null) {
//					mqttClient.disconnect();
//				}
//
//			} catch (MqttPersistenceException e) {
//				log("MqttException"
//						+ (e.getMessage() != null ? e.getMessage() : " NULL"),
//						e);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		/*
//		 * Send a request to the message broker to be sent messages published
//		 * with the specified topic name. Wildcards are allowed.
//		 */
//		private void subscribeToTopic(String topicName) throws MqttException {
//
//			if ((mqttClient == null) || (mqttClient.isConnected() == false)) {
//				// quick sanity check - don't try and subscribe if we don't have
//				// a connection
//				log("Connection error" + "No connection");
//			} else {
//				String[] topics = { topicName };
//				int statusCode = mqttClient.subscribe(topics,
//                        MQTT_QUALITIES_OF_SERVICE);
//			}
//		}
//
//        /*
//		 * Send a request to the message broker to be sent messages published
//		 * with the specified topic name. Wildcards are allowed.
//		 */
//        private void subscribeToTopicAndCity(String topicName,String topicCity) throws MqttException {
//
//            if ((mqttClient == null) || (mqttClient.isConnected() == false)) {
//                // quick sanity check - don't try and subscribe if we don't have
//                // a connection
//                log("Connection error" + "No connection");
//            } else {
//                String[] topics = { topicName, topicCity };
//                int statusCode = mqttClient.subscribe(topics,
//                        MQTT_QUALITIES_OF_SERVICE_INCLUDE_CITY);
//            }
//        }
//
//
//
//		/*
//		 * Sends a message to the message broker, requesting that it be
//		 * published to the specified topic.
//		 */
//		private void publishToTopic(String topicName, String message)
//				throws MqttException {
//			if ((mqttClient == null) || (mqttClient.isConnected() == false)) {
//				// quick sanity check - don't try and publish if we don't have
//				// a connection
//				log("No connection to public to");
//			} else {
//				mqttClient.publish(topicName, message.getBytes(),
//						MQTT_QUALITY_OF_SERVICE, MQTT_RETAINED_PUBLISH);
//			}
//		}
//
//		private void sendKeepAlive() throws MqttException {
//			log("Sending keep alive");
//			// publish to a keep-alive topic
////			publishToTopic(MQTT_CLIENT_ID + "/keepalive",
////					mPrefs.getString(PREF_DEVICE_ID, ""));
////
////			LogUtil.i("mqtt", "去ping一下");
////
////			mqttClient.ping();
//
//		}
//	}
//
//	public class ConnAsyncTask extends AsyncTask<Void, Void, Void> {
//		@Override
//		protected Void doInBackground(Void... arg0) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//	}
//}