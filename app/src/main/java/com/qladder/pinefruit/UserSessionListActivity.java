package com.qladder.pinefruit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserSessionListActivity extends AppCompatActivity {

    List<SessionInfo> mSessionInfoList;
    ListView mListView;
    Query db;
    SessionInfo sessionInfo;
    UserSearchListAdapter adapter;
    String country;
    String city;
    String userName;
    String userEmail;
    String userID;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(UserSessionListActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.p_activity_session_list);
        mListView = (ListView) findViewById(R.id.u_listview);
        mSessionInfoList = new ArrayList<SessionInfo>();
        final String searchText = getIntent().getStringExtra("searchText");

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(UserSessionListActivity.this);
        if(acct != null)
        {
            userName = acct.getDisplayName();
            userEmail = acct.getEmail();
            userID = acct.getId();

            //   Toast.makeText(this,"Inside the provider Name : "+personName,Toast.LENGTH_LONG).show();

        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(UserSessionListActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }


        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location != null) {
                            try {
                                city = "";
                                country = "";
                                Geocoder geo = new Geocoder(UserSessionListActivity.this, Locale.getDefault());
                                List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                //Locality = addresses.get(0).getLocality();
                                city = addresses.get(0).getAdminArea();
                                country = addresses.get(0).getCountryName();
                                Toast.makeText(UserSessionListActivity.this, "Inside default Location : " + city + country, Toast.LENGTH_LONG).show();
                                db = FirebaseDatabase.getInstance().getReference("Session").child(country).child(city).child(userID);
                                db.addValueEventListener(new ValueEventListener() {
                                    // The below line commented is to be used for User view to see only records ready for booking
                                    //db.orderByChild("sessionStatus").equalTo("Booking").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot != null) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                sessionInfo = new SessionInfo("", "", "", "", "", "", "", "", "", "", "", "","","","");
                                                sessionInfo.sessionID = ds.child("sessionID").getValue().toString();
                                                sessionInfo.sessionStatus = ds.child("sessionStatus").getValue().toString();
                                                sessionInfo.mfromtime = ds.child("mfromtime").getValue().toString();
                                                sessionInfo.mtotime = ds.child("mtotime").getValue().toString();
                                                sessionInfo.sessionName = ds.child("sessionName").getValue().toString();
                                                sessionInfo.providerID =ds.child("providerID").getValue().toString();
                                                sessionInfo.providerName = ds.child("providerName").getValue().toString();
                                                sessionInfo.providerOrg = ds.child("providerOrg").getValue().toString();
                                                sessionInfo.userName = ds.child("userName").getValue().toString();
                                                sessionInfo.userEmail = ds.child("userEmail").getValue().toString();
                                                sessionInfo.userID = ds.child("userID").getValue().toString();
                                                sessionInfo.providerLatitude = ds.child("providerLatitude").getValue().toString();
                                                sessionInfo.providerLongitude = ds.child("providerLongitude").getValue().toString();
                                                sessionInfo.mdate = ds.child("mdate").getValue().toString();
                                                 if((sessionInfo.sessionStatus.trim().equals("Booking"))
                                                         && ((sessionInfo.sessionName.toLowerCase().trim().contains(searchText.toLowerCase().trim()))
                                                            || (sessionInfo.providerName.toLowerCase().trim().contains(searchText.toLowerCase().trim()))
                                                         ||(sessionInfo.providerOrg.toLowerCase().trim().contains(searchText.toLowerCase().trim()))
                                                            )
                                                  )
                                                {
                                                    mSessionInfoList.add(sessionInfo);
                                                    adapter = new UserSearchListAdapter(getApplicationContext(), R.layout.u_row, mSessionInfoList);
                                                }
                                                mListView.setAdapter(adapter);

                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                        // ERRRRRRRROR
                                        System.out.println("**************ONCANCALLED***********8");
                                    }


                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });






        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               /* Toast.makeText(getApplicationContext(), "ITEM SELECTED IS  :  " + mSessionInfoList.get(i).getProviderID()
                                +"\n session : "+mSessionInfoList.get(i).getSessionName()
                                +"\n org : "+mSessionInfoList.get(i).getProviderOrg()
                                +"\n provider name: "+mSessionInfoList.get(i).getProviderName()
                                +"\n from :"+mSessionInfoList.get(i).getMfromtime()
                                +"\n to :"+mSessionInfoList.get(i).getMtotime()
                                +"\n Lat :"+mSessionInfoList.get(i).getProviderLatitude()
                                +"\n Date :"+mSessionInfoList.get(i).getMdate()
                                +"\n usename :"+mSessionInfoList.get(i).getUserName()
                        , Toast.LENGTH_LONG).show();*/

                Intent providerInfoIntent = new Intent(UserSessionListActivity.this, UserSessionDetailScreen.class);
                providerInfoIntent.putExtra("providerID",mSessionInfoList.get(i).getProviderID());
                providerInfoIntent.putExtra("providerName",mSessionInfoList.get(i).getProviderName());
                providerInfoIntent.putExtra("sessionName",mSessionInfoList.get(i).getSessionName());
                providerInfoIntent.putExtra("providerOrg",mSessionInfoList.get(i).getProviderOrg());
                providerInfoIntent.putExtra("mFromTime",mSessionInfoList.get(i).getMfromtime());
                providerInfoIntent.putExtra("mToTime",mSessionInfoList.get(i).getMtotime());
                providerInfoIntent.putExtra("mDate",mSessionInfoList.get(i).getMdate());
                providerInfoIntent.putExtra("providerLatitude",mSessionInfoList.get(i).getProviderLatitude());
                providerInfoIntent.putExtra("providerLongitude",mSessionInfoList.get(i).getProviderLongitude());
                startActivity(providerInfoIntent);
                 finish();

            }
        });


    }


}
