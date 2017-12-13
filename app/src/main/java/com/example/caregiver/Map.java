package com.example.caregiver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.caregiver.R.id.map;

public class Map extends AppCompatActivity
        implements OnMapReadyCallback {
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private GoogleMap mMap;
    private Marker m;


    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                   double lat = Double.parseDouble(String.valueOf(dataSnapshot.child("message").child("location").child("lat").getValue()));
                   double lon = Double.parseDouble(String.valueOf(dataSnapshot.child("message").child("location").child("lon").getValue()));
                   moveMarker(mMap,lat,lon);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        myRef.child("message").child("location").child("lat").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
////                String b = dataSnapshot.child("lon").getValue().toString();
////                   double lat = Double.parseDouble(String.valueOf(dataSnapshot.child("lat").getValue()));
////                   double lon = Double.parseDouble(String.valueOf(dataSnapshot.child("lon").getValue()));
////                   addMarker(mMap,lat,lon);
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            initiallizeLocation();
        }

    private void initiallizeLocation() {
        double lat = 42.36036;
        double lon = -71.128;
        addMarker(mMap,lat,lon);
    }

    public void addMarker(GoogleMap googleMap, double lat, double lon) {
            mMap = googleMap;
            LatLng Patloc = new LatLng(lat, lon);
            m = googleMap.addMarker(new MarkerOptions().position(Patloc)
                    .title("Patient Location"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(Patloc));

    }

    public void moveMarker(GoogleMap googleMap, double lat, double lon){
        mMap = googleMap;
        m.setPosition(new LatLng(lat,lon));
    }


    }

//    LatLng Patloc = new LatLng(lat, lon);
//            googleMap.addMarker(new MarkerOptions().position(Patloc)
//                    .title("Patient Location"));
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(Patloc));
//
//   mFirebaseDatabase = FirebaseDatabase.getInstance();
//           myRef = mFirebaseDatabase.getReference();
//           myRef.child("message").child("location").addChildEventListener(new ChildE
//
//                lat = Double.parseDouble(String.valueOf(dataSnapshot.child("lat").getValue()));
//                        lon = Double.parseDouble(String.valueOf(dataSnapshot.child("lon").getValue()));
