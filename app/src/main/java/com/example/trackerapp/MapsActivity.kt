package com.example.trackerapp

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.trackerapp.model.LocationLogging
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

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    private val LOCATION_PERMISSION_REQUEST = 1

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    lateinit var databaseRef: DatabaseReference


    /**determine intervals for receiving location updates**/
    private lateinit var locationRequest: LocationRequest

    /**receives notification from fusedLocationProvider when the device location has changed**/
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        /**instantiate fusedLocationClient**/
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        databaseRef = Firebase.database.reference
        databaseRef.addValueEventListener(getLog("fredlocation"))
        databaseRef.addValueEventListener(getLog("dapolocation"))
        databaseRef.addValueEventListener(getLog("Ransom'sLocation"))

    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        getLocationAccess()
    }

    /**request device location permission**/
    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
            getLocationUpdates()
            startLocationUpdates()
        } else
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                getLocationAccess()
            } else {
                Toast.makeText(this, "User has not granted location access permission", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }


    private fun getLocationUpdates() {
        locationRequest = LocationRequest()

        /**interval for receiving location updates**/
        locationRequest.interval = 20000

        /**shortest interval for receiving location callBack**/
        locationRequest.fastestInterval = 10000

        /**get most accurate location of the device**/
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {

            /**gets long and lat of device location**/
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    if (location != null) {

                        /**remove previous location**/
                        map.clear()

                        /**Add to Firebase**/
                        var databaseRef: DatabaseReference = Firebase.database.reference
                        val locationlogging = LocationLogging(location.latitude, location.longitude)
                        databaseRef.child("fredlocation").setValue(locationlogging)
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
                                            "Error occurred while writing the locations",
                                            Toast.LENGTH_LONG
                                    ).show()
                                }
                    }
                }
            }
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST)
            return
        }
        /**register location callBack with the location client**/
        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
                /***current thread will be use for the callBack**/
        )
    }


    /**get current location of all team members**/
    fun getLog(path: String): ValueEventListener {
        val logListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val locationlogging =
                            dataSnapshot.child(path).getValue(LocationLogging::class.java)
                    val teamLat = locationlogging?.Latitude
                    val teamLong = locationlogging?.Longitude
                    if (teamLat != null && teamLong != null) {
                        val teamLoc = LatLng(teamLat, teamLong)

                        fun customMarker(image: Int): Drawable {
                            return resources.getDrawable(image, null)
                        }


                        val markerOptions = when (path) {

                            "fredlocation" -> MarkerOptions()

                                    .position(teamLoc).title("Fred")

                                    .icon(BitmapDescriptorFactory.fromBitmap(customMarker(R.drawable.fred).toBitmap(customMarker(R.drawable.fred).intrinsicWidth, customMarker(R.drawable.fred).intrinsicHeight, null)))

                            "dapolocation" -> MarkerOptions()

                                    .position(teamLoc).title("Dapo")

                                    .icon(BitmapDescriptorFactory.fromBitmap(customMarker(R.drawable.dapo).toBitmap(customMarker(R.drawable.dapo).intrinsicWidth, customMarker(R.drawable.dapo).intrinsicHeight, null)))

                            else -> MarkerOptions()

                                    .position(teamLoc).title("Ransom")

                                    .icon(BitmapDescriptorFactory.fromBitmap(customMarker(R.drawable.ransom).toBitmap(customMarker(R.drawable.ransom).intrinsicWidth, customMarker(R.drawable.ransom).intrinsicHeight, null)))

                        }

                        map.addMarker(markerOptions)
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(teamLoc, 20f))

                        Toast.makeText(
                                applicationContext,
                                "Locations accessed from the database",
                                Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Could not read from database", Toast.LENGTH_LONG)
                        .show()
            }
        }
        return logListener
    }


    /**menu setUp**/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_menu, menu)
        return true
    }

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


}