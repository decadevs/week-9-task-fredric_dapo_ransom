//package com.misterjedu.locationtracker
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.util.Log
//import android.view.Menu
//import android.view.MenuItem
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import com.google.android.gms.location.*
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//
//private lateinit var fusedLocationClient: FusedLocationProviderClient
//private lateinit var locationRequest: LocationRequest
//private lateinit var locationCallback: LocationCallback
//
//private lateinit var map: GoogleMap
//
//private val REQUEST_LOCATION_PERMISSION = 1
//
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_maps)
//
//    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//    val mapFragment = supportFragmentManager
//        .findFragmentById(R.id.map) as SupportMapFragment
//    mapFragment.getMapAsync(this)
//}
//
//override fun onMapReady(googleMap: GoogleMap) {
//    map = googleMap
//
////        val latitude = 6.474306
////        val longitude = 3.630909
////        val zoomLevel = 17f
////
////        val homeLatLng = LatLng(latitude, longitude)
////        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
////        map.addMarker(MarkerOptions().position(homeLatLng))
//
//    enableMyLocation()
//}
//
//
//override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//    val inflater = menuInflater
//    inflater.inflate(R.menu.menu_options, menu)
//    return true
//}
//
//
//override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//    R.id.normal_map -> {
//        map.mapType = GoogleMap.MAP_TYPE_NORMAL
//        true
//    }
//
//    R.id.hybrid_map -> {
//        map.mapType = GoogleMap.MAP_TYPE_HYBRID
//        true
//    }
//
//    R.id.satellite_map -> {
//        map.mapType = GoogleMap.MAP_TYPE_SATELLITE
//        true
//    }
//
//    R.id.terrain_map -> {
//        map.mapType = GoogleMap.MAP_TYPE_TERRAIN
//        true
//    }
//
//    else -> super.onOptionsItemSelected(item)
//}
//
//
////    private fun isPermissionGranted(): Boolean {
////        return ContextCompat.checkSelfPermission(
////            this, Manifest.permission.ACCESS_FINE_LOCATION
////        ) == PackageManager.PERMISSION_GRANTED
////    }
//
//
//private fun enableMyLocation() {
//    if (ActivityCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED
//    ) {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//            REQUEST_LOCATION_PERMISSION
//        )
//
//    } else {
//        map.isMyLocationEnabled = true
//        getLocationUpdates()
//        startLocationUpdates()
//        return
//
//    }
//}
//
//
//override fun onRequestPermissionsResult(
//    requestCode: Int,
//    permissions: Array<out String>,
//    grantResults: IntArray
//) {
//    if (requestCode == REQUEST_LOCATION_PERMISSION) {
//        if (grantResults.isNotEmpty() && (grantResults[0]) == PackageManager.PERMISSION_GRANTED) {
//            enableMyLocation()
//        }
//    } else {
//        Toast.makeText(this, "You did not grant location access permission", Toast.LENGTH_SHORT)
//            .show()
//        finish()
//    }
//}
//
//private fun getLocationUpdates() {
//    Log.i("Current Location", "Get Location Updates")
//    locationRequest = LocationRequest()
//    locationRequest.interval = 30000
//    locationRequest.fastestInterval = 20000
//    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//
//    locationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            Log.i("LocationResult", "On Location Result ")
//            if (locationResult.locations.isNotEmpty()) {
//                val location = locationResult.lastLocation
//                if (location != null) {
//                    val latLng = LatLng(location.latitude, location.longitude)
//                    val markerOptions = MarkerOptions().position(latLng)
//                    map.addMarker(markerOptions)
//                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
//                    Log.i("Current Location", "${location.latitude} , ${location.altitude}")
//                }
//            } else {
//                Log.i("Location Result", "On Location Result is Empty ")
//            }
//        }
//    }
//}
//
//private fun startLocationUpdates() {
//    Log.i("Current Location", "Start Location Updates")
//    if (ActivityCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED
//    ) {
//        fusedLocationClient.requestLocationUpdates(
//            locationRequest,
//            locationCallback,
//            null
//        )
//        return
//    } else {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//            REQUEST_LOCATION_PERMISSION
//        )
//    }
//}