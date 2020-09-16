package com.example.map_app_week_8

//private val LOCATION_PERMISSION_REQUEST = 1
//private lateinit var fusedLocationClient: FusedLocationProviderClient
//private lateinit var locationRequest: LocationRequest
//private lateinit var locationCallback: LocationCallback
//
//private fun getLocationAccess() {
//    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//        map.isMyLocationEnabled = true
//        getLocationUpdates()
//        startLocationUpdates()
//    }
//    else
//        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
//}
//
//
//override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//    if (requestCode == LOCATION_PERMISSION_REQUEST) {
//        if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
//            getLocationAccess()
//        }
//        else {
//            Toast.makeText(this, "User has not granted location access permission", Toast.LENGTH_LONG).show()
//            finish()
//        }
//    }
//}


//    private fun enableMyLocation() {
//        if (isPermissionGranted()) {
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.INTERNET
//                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_NETWORK_STATE
//            ) != PackageManager.PERMISSION_GRANTED)
//            {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
//                    REQUEST_LOCATION_PERMISSION
//                )
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf<String>(Manifest.permission.ACCESS_COARSE_LOCATION),
//                    REQUEST_LOCATION_PERMISSION
//                )
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf<String>(Manifest.permission.ACCESS_NETWORK_STATE),
//                    REQUEST_LOCATION_PERMISSION
//                )
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf<String>(Manifest.permission.INTERNET),
//                    REQUEST_LOCATION_PERMISSION
//                )
//                return
//            }
//            map.isMyLocationEnabled = true
//
//        }
//        else {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
//                REQUEST_LOCATION_PERMISSION
//            )
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf<String>(Manifest.permission.ACCESS_COARSE_LOCATION),
//                REQUEST_LOCATION_PERMISSION
//            )
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf<String>(Manifest.permission.ACCESS_NETWORK_STATE),
//                REQUEST_LOCATION_PERMISSION
//            )
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf<String>(Manifest.permission.INTERNET),
//                REQUEST_LOCATION_PERMISSION
//            )
//        }
//    }


//    private fun getLocationAccess() {
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.
//            ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            map.isMyLocationEnabled = true
//            getLocationUpdates()
//            startLocationUpdates()
//        }
//        else
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                LOCATION_PERMISSION_REQUEST)
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray) {
//
//        // Check if location permissions are granted and if so enable the
//        // location data layer.
//
//        if (requestCode == LOCATION_PERMISSION_REQUEST) {
//            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                Toast.makeText(this, "User has not granted location access permission",
//                    Toast.LENGTH_LONG).show()
//                enableMyLocation()
//            }
//        }
//    }


/**
 * This function enables the user to drop a pin and mark a location
 */
//    private fun setMapLongClick(map: GoogleMap) {
//        map.setOnMapLongClickListener { latLng ->
//
//            // A Snippet is Additional text that's displayed below the title.
//            val snippet = String.format(
//                Locale.getDefault(),
//                "Lat: %1$.5f, Long: %2$.5f",
//                latLng.latitude,
//                latLng.longitude
//            )
//            map.addMarker(
//                MarkerOptions()
//                    .position(latLng)
//                    .title(getString(R.string.dropped_pin))
//                    .snippet(snippet)
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
//            )
//        }
//    }


//    private fun setPoiClick(map: GoogleMap) {
//        map.setOnPoiClickListener { poi ->
//            val poiMarker = map.addMarker(
//                MarkerOptions()
//                    .position(poi.latLng)
//                    .title(poi.name)
//            )
//            poiMarker.showInfoWindow()
//        }
//    }


//        val latitude = 6.474098
//        val longitude = 3.630883
//        val zoomLevel = 15f

// Add a marker in Decagon Uno and move the camera
//        val decagonUno = LatLng(latitude, longitude)
//        map.addMarker(MarkerOptions().position(decagonUno).title("Marker in Decagon Uno"))
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(decagonUno, zoomLevel))
//        map.addMarker(MarkerOptions().position(decagonUno))

//        setMapLongClick(map)
//        setPoiClick(map)
//        enableMyLocation()


/**
 * This function checks if the user has granted permission to the app to access the user's
 * current location. and requests for the permission if it hasn't been granted.
 */

//    private fun isPermissionGranted() : Boolean {
//        return ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//
//    }


//                        val latLng = LatLng(location.latitude, location.longitude)
//
//                        Log.i("Location", "onLocationResult: $latLng")
//
//                        val markerOptions = MarkerOptions().position(latLng)
//                        map.addMarker(markerOptions)
//                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))