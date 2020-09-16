package com.misterjedu.locationtracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
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
    private val LOCATION_PERMISSION_REQUEST = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var map: GoogleMap
    lateinit var databaseRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        databaseRef = Firebase.database.reference
        databaseRef.addValueEventListener(getLogListener("Ransom'sLocation"))
        databaseRef.addValueEventListener(getLogListener("dapolocation"))
        databaseRef.addValueEventListener(getLogListener("fredlocation"))

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getLocationAccess()
    }

    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            getLocationUpdates()
            startLocationUpdates()
        } else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                getLocationAccess()
            } else {
                Toast.makeText(
                    this,
                    "User has not granted location access permission",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }


    private fun getLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.interval = 3000
        locationRequest.fastestInterval = 2000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    if (location != null) {

                        map.clear()
                        //Add to Firebase
                        lateinit var databaseRef: DatabaseReference
                        databaseRef = Firebase.database.reference
                        val locationlogging = LocationLogging(location.latitude, location.longitude)
                        databaseRef.child("dapolocation").setValue(locationlogging)
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


    fun getLogListener(path: String): ValueEventListener {
        val logListener = object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val dapoMarker = resources.getDrawable(R.drawable.dapo_marker, null)
                    val fredMarker = resources.getDrawable(R.drawable.fred_marker, null)
                    val ransomMarker = resources.getDrawable(R.drawable.ransom_marker, null)


                    val locationlogging =
                        dataSnapshot.child(path).getValue(LocationLogging::class.java)
                    var userLat = locationlogging?.Latitude
                    var userLong = locationlogging?.Longitude

                    if (userLat != null && userLong != null) {
                        val userLoc = LatLng(userLat, userLong)
                        val markerOptions = when (path) {
                            "dapolocation" -> MarkerOptions().position(userLoc).title("Oladapo")
                                .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                        dapoMarker.toBitmap(
                                            dapoMarker.intrinsicWidth,
                                            dapoMarker.intrinsicHeight,
                                            null
                                        )
                                    )
                                )


                            "fredlocation" -> MarkerOptions().position(userLoc).title("Chibuzor")
                                .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                        fredMarker.toBitmap(
                                            fredMarker.intrinsicWidth,
                                            fredMarker.intrinsicHeight,
                                            null
                                        )
                                    )
                                )
                            else -> MarkerOptions().position(userLoc).title("Ransom")
                                .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                        ransomMarker.toBitmap(
                                            ransomMarker.intrinsicWidth,
                                            ransomMarker.intrinsicHeight,
                                            null
                                        )
                                    )
                                )
                        }
                        map.addMarker(markerOptions)
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 20f))

                        Toast.makeText(
                            applicationContext,
                            "Locations accessed from the database",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

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
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

}