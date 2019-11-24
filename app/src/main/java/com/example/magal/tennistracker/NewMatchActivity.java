package com.example.magal.tennistracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NewMatchActivity extends AppCompatActivity implements MapFragment.OnFragmentInteractionListener, LocationListener {

    private TextView latitudeField;
    private TextView longitudeField;
    private TextView addressField;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String provider;
    private GoogleMap mMap;
    private LatLng latLng;

    EditText nomJoueur1, nomJoueur2;


    //Button buttonviewdata, buttonaddData;

    private DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);
        //buttonviewdata = (Button) findViewById(R.id.buttonviewdata);
        //buttonaddData = (Button) findViewById(R.id.buttonadddata);
        nomJoueur1 = (EditText) findViewById(R.id.nomJ1EditText);
        nomJoueur2 = (EditText) findViewById(R.id.nomJ2EditText);


        mydb = new DatabaseHelper(this);



        //CREATION DE LA MAP
        latitudeField = findViewById(R.id.TextView_lati);
        longitudeField = findViewById(R.id.TextView_long);
        addressField = findViewById(R.id.TextView_add);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Define the criteria how to select the location provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            latitudeField.setText("Location not available");
            longitudeField.setText("Location not available");
            addressField.setText("Location not available");
        }

    }

    private void AddData(String newEntry){
        boolean insertData = mydb.addJoueursData(newEntry);

        if(insertData){
            toastMessage("Data Successfully inserted !");
        }
        else
        {
            toastMessage("Something went wrong");
        }

    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void startNewGame(View view) {
        /* On récupère les noms des joueurs */
        EditText nomJ1EditText = (EditText) findViewById(R.id.nomJ1EditText);
        EditText nomJ2EditText = (EditText) findViewById(R.id.nomJ2EditText);

        String nomJ1 = nomJ1EditText.getText().toString();
        if (nomJ1.isEmpty()) {
            Toast.makeText(this, getString(R.string.J1_name_missing_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String nomJ2 = nomJ2EditText.getText().toString();
        if (nomJ2.isEmpty()) {
            Toast.makeText(this, getString(R.string.J2_name_missing_toast), Toast.LENGTH_SHORT).show();
            return;
        }

        /* Get serving player */
        int servingPlayer;
        RadioGroup serveRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        switch (serveRadioGroup.getCheckedRadioButtonId()) {
            case R.id.radio_button_J1:
                servingPlayer = 1;
                break;
            case R.id.radio_button_J2:
                servingPlayer = 2;
                break;
            default:
                Toast.makeText(this, getString(R.string.select_serving_player_toast), Toast.LENGTH_SHORT).show();
                return;
        }

        Bundle args = new Bundle();
        args.putString(MatchActivity.ARG_J1_NAME, nomJ1);
        args.putString(MatchActivity.ARG_J2_NAME, nomJ2);
        args.putInt(MatchActivity.ARG_SERVE_FIRST, servingPlayer);
        Intent i = new Intent(NewMatchActivity.this, MatchActivity.class);
        i.putExtras(args);
        startActivity(i);
    }


    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }



    @Override
    public void onLocationChanged(Location location) {

        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        latitudeField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));

        //Log.d("Location :", location.toString());
        latLng = new LatLng(lat,lng);

        String address = getAddress(this,lat,lng);
        addressField.setText(address);

        //add marker on the map without MapClick
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.placeMarker(address, lat, lng);

        String addresse = getAddress(this,lat,lng);
        addressField.setText(addresse);



    }

    //Get Address from longitude and latitude
    public String getAddress(Context ctx, double lat, double lng){
        String fullAdd=null;
        try{
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<android.location.Address> addresses = geocoder.getFromLocation(lat,lng,1);
            if(addresses.size()>0){
                Address address = addresses.get(0);
                fullAdd = address.getAddressLine(0);

                //Get the city and the location
                /*String Location = address.getLocality();
                String Country = address.getCountryName();*/
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        return fullAdd;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //pour passer lat et lng à MapFragment si on utilise le OnMapClick
    public void passData(Double lat, Double lng)
    {
        latitudeField = findViewById(R.id.TextView_lati);
        longitudeField = findViewById(R.id.TextView_long);
        latitudeField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));
    }

}
