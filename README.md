BLX-PE-edmedina:Nails_Pasion edmedina$ adb devices
List of devices attached
ZY326LLT38      device

BLX-PE-edmedina:Nails_Pasion edmedina$ adb -s ZY326LLT38  shell setprop persist.log.tag.Volley VERBOSE


---------------------------------------------------------------------------------------
Logs

V/Volley: [74982] CacheDispatcher.run: start new dispatcher
2020-01-31 15:25:20.590 18887-18928/com.lappsmov.nailspasion D/Volley: [74982] WaitingRequestManager.maybeAddToWaitingRequests: new request, sending to network 1-https://nailspasion.com/app/get_data_convencional.php
2020-01-31 15:25:20.593 18887-18929/com.lappsmov.nailspasion D/NetworkSecurityConfig: No Network Security Config specified, using platform default
2020-01-31 15:25:20.620 18887-18927/com.lappsmov.nailspasion I/ConfigStore: android::hardware::configstore::V1_0::ISurfaceFlingerConfigs::hasWideColorDisplay retrieved: 1
2020-01-31 15:25:20.620 18887-18927/com.lappsmov.nailspasion I/ConfigStore: android::hardware::configstore::V1_0::ISurfaceFlingerConfigs::hasHDRDisplay retrieved: 0
2020-01-31 15:25:20.620 18887-18927/com.lappsmov.nailspasion I/OpenGLRenderer: Initialized EGL, version 1.4
2020-01-31 15:25:20.620 18887-18927/com.lappsmov.nailspasion D/OpenGLRenderer: Swap behavior 2
2020-01-31 15:25:20.627 18887-18927/com.lappsmov.nailspasion D/mali_winsys: EGLint new_window_surface(egl_winsys_display *, void *, EGLSurface, EGLConfig, egl_winsys_surface **, EGLBoolean) returns 0x3000
2020-01-31 15:25:20.632 18887-18887/com.lappsmov.nailspasion E/RecyclerView: No adapter attached; skipping layout
2020-01-31 15:25:20.633 18887-18887/com.lappsmov.nailspasion E/RecyclerView: No adapter attached; skipping layout
2020-01-31 15:25:20.767 18887-18917/com.lappsmov.nailspasion D/FA: Connected to remote service
2020-01-31 15:25:20.769 18887-18917/com.lappsmov.nailspasion V/FA: Processing queued up service tasks: 4
2020-01-31 15:25:22.153 18887-18929/com.lappsmov.nailspasion D/Volley: [74983] BasicNetwork.logSlowRequests: HTTP response for request=<[ ] https://nailspasion.com/app/get_data_convencional.php 0x54f03c96 NORMAL 1> [lifetime=1561], [size=4250], [rc=200], [retryCount=0]
2020-01-31 15:25:22.187 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (1601 ms) [ ] https://nailspasion.com/app/get_data_convencional.php 0x54f03c96 NORMAL 1
2020-01-31 15:25:22.189 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+0   ) [ 2] add-to-queue
2020-01-31 15:25:22.190 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+2   ) [74982] cache-queue-take
2020-01-31 15:25:22.191 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+2   ) [74982] cache-hit-expired
2020-01-31 15:25:22.192 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+1   ) [74983] network-queue-take
2020-01-31 15:25:22.194 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+1564) [74983] network-http-complete
2020-01-31 15:25:22.195 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+4   ) [74983] network-parse-complete
2020-01-31 15:25:22.196 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+2   ) [74983] network-cache-written
2020-01-31 15:25:22.196 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+0   ) [74983] post-response
2020-01-31 15:25:22.197 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+26  ) [ 2] done
2020-01-31 15:25:22.245 18887-18887/com.lappsmov.nailspasion W/Glide: Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored
2020-01-31 15:25:25.869 18887-18917/com.lappsmov.nailspasion V/FA: Inactivity, disconnecting from the service


V/Volley: [74992] CacheDispatcher.run: start new dispatcher
2020-01-31 15:26:39.377 18887-18974/com.lappsmov.nailspasion D/Volley: [74992] WaitingRequestManager.maybeAddToWaitingRequests: new request, sending to network 1-https://nailspasion.com/app/get_data_gel.php
2020-01-31 15:26:40.470 18887-18976/com.lappsmov.nailspasion D/Volley: [74993] BasicNetwork.logSlowRequests: HTTP response for request=<[ ] https://nailspasion.com/app/get_data_gel.php 0x54f03c96 NORMAL 1> [lifetime=1090], [size=2467], [rc=200], [retryCount=0]
2020-01-31 15:26:40.479 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (1108 ms) [ ] https://nailspasion.com/app/get_data_gel.php 0x54f03c96 NORMAL 1
2020-01-31 15:26:40.480 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+0   ) [ 2] add-to-queue
2020-01-31 15:26:40.480 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+4   ) [74992] cache-queue-take
2020-01-31 15:26:40.481 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+2   ) [74992] cache-hit-expired
2020-01-31 15:26:40.482 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+2   ) [74993] network-queue-take
2020-01-31 15:26:40.483 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+1092) [74993] network-http-complete
2020-01-31 15:26:40.484 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+2   ) [74993] network-parse-complete
2020-01-31 15:26:40.485 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+4   ) [74993] network-cache-written
2020-01-31 15:26:40.486 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+0   ) [74993] post-response
2020-01-31 15:26:40.487 18887-18887/com.lappsmov.nailspasion D/Volley: [2] MarkerLog.finish: (+2   ) [ 2] done