package com.example.map_app_week_8

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.map_app_week_8.Constant.LocationLogging
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * This is the main activity for the map app
 */

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    /**
     * All my global variables are initialised here
     */

    private lateinit var map: GoogleMap
    private val LOCATION_PERMISSION_REQUEST = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        /**
         * Obtain the SupportMapFragment and get notified when the map is ready to be used.
         */

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        databaseRef = Firebase.database.reference
        databaseRef.addValueEventListener(getLog("fredlocation"))
        databaseRef.addValueEventListener(getLog("dapolocation"))
        databaseRef.addValueEventListener(getLog("Ransom'sLocation"))
    }


    /**
     * The function below checks if the user has granted permission to the app to access the user's
     * current location and then enables the app to constantly track the device location
     */

    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            getLocationUpdates()
            startLocationUpdates()
        } else
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), LOCATION_PERMISSION_REQUEST
            )
    }

    /**
     * Manage the location permission for the app
     */

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray,
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                getLocationAccess()
            } else {
                Toast.makeText(
                    this, "User has not granted location access permission",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getLocationAccess()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }


    /**
     * This function enables the user to change the map type
     */

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        // Change the map type based on the user's selection.
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    /**
     * Get location update from firebase, declare path to save current location
     */

    private fun getLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 20000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {

                    val location = locationResult.lastLocation
                    if (location != null) {
                        map.clear()

                        //Add to Firebase
                        var databaseRef: DatabaseReference = Firebase.database.reference
                        val locationLogging = LocationLogging(location.latitude, location.longitude)
                        databaseRef.child("Ransom'sLocation").setValue(locationLogging)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Locations written into the database",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Error occured while writing the locations",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                    }
                }
            }
        }
    }

    /**
     * Seek for all necessary permissions from user if permission wasn't granted on app installation
     */

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), LOCATION_PERMISSION_REQUEST
            )
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    /**
     * Update and receive location data from Firestore database of team
     * Set markers for team mates
     */

    fun getLog(path: String): ValueEventListener {
        val logListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val locationLogging =
                        dataSnapshot.child(path).getValue(LocationLogging::class.java)
                    var driverLat = locationLogging?.Latitude
                    var driverLong = locationLogging?.Longitude
                    if (driverLat != null && driverLong != null) {

                        val pickupMarkerDrawableF = resources.getDrawable(R.drawable.fred_maker_02,null)
                        val pickupMarkerDrawableD = resources.getDrawable(R.drawable.dapo_marker_02,null)
                        val pickupMarkerDrawableR = resources.getDrawable(R.drawable.ransom_marker_02,null)

                        val driverLoc = LatLng(driverLat, driverLong)
                        val markerOptions = when (path) {
                            "fredlocation" ->
                                MarkerOptions().position(driverLoc).title("Fred")
                                    .icon(BitmapDescriptorFactory.fromBitmap(pickupMarkerDrawableF
                                        .toBitmap(pickupMarkerDrawableF.intrinsicWidth, pickupMarkerDrawableF
                                            .intrinsicHeight, null))
                                )
                            "dapolocation" ->
                                MarkerOptions().position(driverLoc).title("Dapo")
                                    .icon(BitmapDescriptorFactory.fromBitmap(pickupMarkerDrawableD
                                        .toBitmap(pickupMarkerDrawableD.intrinsicWidth, pickupMarkerDrawableD
                                            .intrinsicHeight, null))
                                )
                            else -> MarkerOptions().position(driverLoc).title("Ransom")
                                .icon(BitmapDescriptorFactory.fromBitmap(pickupMarkerDrawableR
                                    .toBitmap(pickupMarkerDrawableR.intrinsicWidth, pickupMarkerDrawableR
                                        .intrinsicHeight, null))
                            )
                        }
                        map.addMarker(markerOptions)
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(driverLoc, 17f))


                        //Zoom level - 1: World, 5: Landmass/continent, 10: City, 15: Streets and 20: Buildings
                        Toast.makeText(
                            applicationContext,
                            "Locations accessed from the database",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            /**
             * Manage Databade error with toast
             */

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    applicationContext,
                    "Could not read from database",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
        return logListener
    }
}