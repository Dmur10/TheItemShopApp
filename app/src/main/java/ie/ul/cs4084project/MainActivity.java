package ie.ul.cs4084project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements LocationListener {

    //text input from the layout
    private TextInputLayout textInputSearch;
    //spinner for categories;
    private Spinner categorySpinner;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected double latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    protected boolean permission;

    /**
     * onCreate creates the Activity and sets content view to acivity_main.
     * it also initializes UI elements.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        textInputSearch = findViewById(R.id.textInputSearch);
        categorySpinner = findViewById(R.id.categorySpinner);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (categorySpinner.isSelected() && !categorySpinner.getSelectedItem().equals("-select a category-")) {
                    FindItem newFragment = new FindItem();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("Category", categorySpinner.getSelectedItem().toString());
                    newFragment.setArguments(args);
                    ft.replace(R.id.fragment, newFragment);
                    ft.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    /**
     * onClick method for home button
     * Takes the user to the main feed on clicking the button btnHome
     *
     * @param view takes in the view as a parameter
     */
    public void onHomeClicked(View view) {
        MainFeed newFragment = new MainFeed();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
    }

    /**
     * onClick method for createPost button
     * takes user to the fragment createPost to add a new item to the store on clicking btnCreatePost.
     * @param view takes in the view as a parameter
     */
    public void onCreatePostClicked(View view) {
        CreatePost newFragment = new CreatePost();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
    }

    /**
     * onClick method for message button
     * takes user to the MessageScreen fragment where they can send emails to other users on clicking btnMessage
     * @param view takes in the view as a parameter
     */
    public void onMessageClicked(View view) {
        MessageScreen newFragment = new MessageScreen();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
    }

    /**
     * onClick method for search button
     * takes user to FindItem fragment to load the search items and category if one is chosen on clicking btnSearch.
     * @param view takes in the view as a parameter
     */
    public void onSearchClicked(View view) {
        if (!(textInputSearch.getEditText().getText().toString() == "")) {
            FindItem newFragment = new FindItem();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Bundle args = new Bundle();
            args.putString("searchTerm", textInputSearch.getEditText().getText().toString());
            newFragment.setArguments(args);
            ft.replace(R.id.fragment, newFragment);
            ft.commit();
        }
    }

    public void onProfileClicked(View view) {
        Profile newFragment = new Profile();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
    }

    /**
     * method called when permissions are requested
     *
     * @param requestCode  int code of the request made
     * @param permissions  String[] permissions required
     * @param grantResults int[] whether permission is granted for each permission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                break;
            }
        }
    }

    /**
     * called on results of an activity started for result
     *
     * @param requestCode int
     * @param resultCode  int
     * @param data        Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }
}