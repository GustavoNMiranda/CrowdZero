package com.example.crowdzero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.crowdzero.models.FAKE_INSTITUICOES;
import com.example.crowdzero.models.Usuario;
import com.example.crowdzero.server.ApiClient;
import com.example.crowdzero.server.DataInstiuicao;
import com.example.crowdzero.server.RespostaGetInstituicao;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.crowdzero.models.Instituicao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapView extends AppCompatActivity implements OnMapReadyCallback {
    public static ArrayList<Instituicao> ListaInstituicoes = new ArrayList<Instituicao>();
    public static LatLng Localizacao_Usuario;
    FusedLocationProviderClient client;
    AddressResultReceiver resultReceiver;
    GoogleMap mMap;

    static public Geocoder geocoder;
    int GPS_PERMISSION_CODE = 1000;

    @Override
    public void onBackPressed() {
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        geocoder = new Geocoder(this);

        getAllInstituicoes();


        //--------------------------------------------------------------------------------------------------------------------------------------------------
        if (!isGPSPermissionGrated()) {
            requestGPSPermission();
        }
        //--------------------------------------------------------------------------------------------------------------------------------------------------
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //--------------------------------------------------------------------------------------------------------------------------------------------------
        client = LocationServices.getFusedLocationProviderClient(this);
        resultReceiver = new AddressResultReceiver(null);


        //--------------------------------------------------------------------------------------------------------------------------------------------------

        FloatingActionButton floatingActionButton = findViewById(R.id.FloatingButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!ListaInstituicoes.isEmpty()) {
                    Location Usuario_Location = new Location("Usuario_Location");
                    Usuario_Location.setLatitude(MapView.Localizacao_Usuario.latitude);
                    Usuario_Location.setLongitude(MapView.Localizacao_Usuario.longitude);
                    Location Instituicao_Location = new Location("Instituicao_Location");
                    Location AUX_Location = new Location("AUX_Location");
                    Instituicao AUX_Instituicao = new Instituicao();
                    float AUX_Distancia = Usuario_Location.distanceTo(Instituicao_Location);

                    for (int X = 0; X < ListaInstituicoes.size(); ++X) {
                        Instituicao_Location.setLatitude(ListaInstituicoes.get(X).getLatitude());
                        Instituicao_Location.setLongitude(ListaInstituicoes.get(X).getLongitude());

                        if(Usuario_Location.distanceTo(Instituicao_Location) <= AUX_Distancia){
                            AUX_Instituicao = ListaInstituicoes.get(X);
                            AUX_Distancia = Usuario_Location.distanceTo(Instituicao_Location);
                        }
                    }

                    BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance();
                    bottomSheetFragment.setInstituicao(AUX_Instituicao);
                    bottomSheetFragment.show(getSupportFragmentManager(),BottomSheetFragment.TAG);
                }else{
                    Toast.makeText(MapView.this,R.string.ERRO_SemInstituicao,Toast.LENGTH_SHORT).show();

                }
            }
        });

        BottomNavigationView BottomNav = findViewById(R.id.BottomNavBarView);
        BottomNav.setSelectedItemId(R.id.M_MapView);
        BottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.M_MapView:
                        break;
                    case R.id.M_Favoritos:
                        startActivity(new Intent(getApplicationContext(), MinhasInstituicoes.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.M_Estatisticas:
                        startActivity(new Intent(getApplicationContext(), Estatisticas.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.M_Perfil:
                        startActivity(new Intent(getApplicationContext(), Perfil.class));
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------
    private void getAllInstituicoes(){

        ArrayList AUX_Usuario = Login.Dados_usuario;
        Usuario usuario = (Usuario) AUX_Usuario.get(0);

        Call<RespostaGetInstituicao> Server = ApiClient.getUserService().GetInstituicoes(usuario.getID().toString());
        Server.enqueue(new Callback<RespostaGetInstituicao>() {
            @Override
            public void onResponse(Call<RespostaGetInstituicao> call, Response<RespostaGetInstituicao> response) {
                if(response.isSuccessful()) {
                    RespostaGetInstituicao respostaGetInstituicao = response.body();
                    if(respostaGetInstituicao.getSuccess()) {
                        ArrayList<DataInstiuicao> ListaServer  = respostaGetInstituicao.getData();
                        LatLng latLng;
                        ListaInstituicoes.clear();
                       //ListaInstituicoes.addAll(FAKE_INSTITUICOES.LInstituicoes());//ddddddddddddd Tirar
                        for(int X = 0; X < ListaServer.size(); ++X){
                            DataInstiuicao AUX_Resposta = (DataInstiuicao) ListaServer.get(X);
                            Instituicao AUX_Instituicao = new Instituicao();
                            AUX_Instituicao.setID(Integer.parseInt(AUX_Resposta.getId()));
                            AUX_Instituicao.setNome(AUX_Resposta.getNome_instituicao());
                            AUX_Instituicao.setEstado(Integer.parseInt(AUX_Resposta.getEstado_instituicao()));
                            AUX_Instituicao.setLatitude(Double.parseDouble(AUX_Resposta.getLatitude()));
                            AUX_Instituicao.setLongitude(Double.parseDouble(AUX_Resposta.getLongitude()));
                            if(AUX_Resposta.getUpdatedAT() != null) {
                                AUX_Instituicao.setU_Reporte(AUX_Resposta.getUpdatedAT());
                            }else
                                AUX_Instituicao.setU_Reporte("");

                            try {
                               List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(AUX_Resposta.getLatitude()),Double.parseDouble(AUX_Resposta.getLongitude()),1);
                                AUX_Instituicao.setEndereco(addresses.get(0).getThoroughfare());
                            } catch (IOException e) {
                                AUX_Instituicao.setEndereco("");
                            }
                            ListaInstituicoes.add(AUX_Instituicao);
                        }
                        AddHeatMap();
                    }
                }
            }
            @Override
            public void onFailure(Call<RespostaGetInstituicao> call, Throwable t) {
                Toast.makeText(MapView.this,getString(R.string.ERRO_Conexao) ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------
    private Boolean isGPSPermissionGrated() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------
    private void requestGPSPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_PERMISSION_CODE);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GPS_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                } else {
                    requestGPSPermission();
                }
            }
        }
    }

//--------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(20.0f);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @SuppressLint("PotentialBehaviorOverride")
            @Override
            public boolean onMarkerClick(Marker marker) {
                Instituicao instituicao = (Instituicao) marker.getTag();
                LatLng L = new LatLng(instituicao.getLatitude(),instituicao.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(L, 16));
                mMap.moveCamera(CameraUpdateFactory.scrollBy(0,450));
                MakeBottomSheet(instituicao);
                return true;
            }
        });

    }


//--------------------------------------------------------------------------------------------------------------------------------------------------
    public void AddHeatMap(){
        List<LatLng> latLngs_L1 = new ArrayList<LatLng>();
        List<LatLng> latLngs_L2 = new ArrayList<LatLng>();
        List<LatLng> latLngs_L3 = new ArrayList<LatLng>();

        Instituicao instituicao;


        if(!ListaInstituicoes.isEmpty()) {

            for (int Cont = 0; Cont < ListaInstituicoes.size(); Cont++) {
                instituicao = ListaInstituicoes.get(Cont);

                if (instituicao.getEstado() == 1)
                    latLngs_L1.add(new LatLng(instituicao.getLatitude(), instituicao.getLongitude()));
                if (instituicao.getEstado() == 2)
                    latLngs_L2.add(new LatLng(instituicao.getLatitude(), instituicao.getLongitude()));
                if (instituicao.getEstado() == 3)
                    latLngs_L3.add(new LatLng(instituicao.getLatitude(), instituicao.getLongitude()));

                Marker AUX_M = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(instituicao.getLatitude(), instituicao.getLongitude())).title(instituicao.getNome())
                        .icon(BitmapDescriptorFactory.defaultMarker(205)));
                AUX_M.setTag(instituicao);
            }

            int[] colors_1 = {
                    Color.rgb(130, 209, 151)
            };

            int[] colors_2 = {
                    Color.rgb(255, 152, 0)
            };

            int[] colors_3 = {
                    Color.rgb(244, 67, 54)
            };


            float[] startPoints = {
                    1f
            };

            Gradient gradient_1 = new Gradient(colors_1, startPoints);
            Gradient gradient_2 = new Gradient(colors_2, startPoints);
            Gradient gradient_3 = new Gradient(colors_3, startPoints);


            if(!latLngs_L1.isEmpty())
                StyleHeatMap(gradient_1, latLngs_L1);
            if(!latLngs_L2.isEmpty())
                StyleHeatMap(gradient_2, latLngs_L2);
            if(!latLngs_L3.isEmpty())
                StyleHeatMap(gradient_3, latLngs_L3);

        }
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------
    public void StyleHeatMap(Gradient gradient,List<LatLng> latLngs){
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                .data(latLngs)
                .radius(50)
                .gradient(gradient)
                .build();

        TileOverlay overlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
    }

//--------------------------------------------------------------------------------------------------------------------------------------------------

    public void MakeBottomSheet(Instituicao instituicao){
        BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance();
        bottomSheetFragment.setInstituicao(instituicao);
        bottomSheetFragment.show(getSupportFragmentManager(),BottomSheetFragment.TAG);
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onResume()
    {
        super.onResume();

        //--------------------------------------------------------------------------------------------------------------------------------------------------
        //GooglePlayServices
        int ErrorCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        switch (ErrorCode) {
            case ConnectionResult.SERVICE_MISSING:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult.SERVICE_DISABLED:
                GoogleApiAvailability.getInstance().getErrorDialog(this, ErrorCode, 0, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                }).show();

                break;
            case ConnectionResult.SUCCESS:
                break;
        }
        //--------------------------------------------------------------------------------------------------------------------------------------------------
        //Permições
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
        //--------------------------------------------------------------------------------------------------------------------------------------------------
        // Ultima localização
        client.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(Location location)
            {

                if(location != null)
                {
                    mMap.setMyLocationEnabled(true);
                    LatLng L;
                    try {
                        Instituicao instituicao = getIntent().getExtras().getParcelable("instituicao");
                        L = new LatLng(instituicao.getLatitude(),instituicao.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(L, 16));
                        mMap.moveCamera(CameraUpdateFactory.scrollBy(0,450));
                        MakeBottomSheet(instituicao);
                    } catch (Exception e) {
                        L = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(L, 16));
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        //--------------------------------------------------------------------------------------------------------------------------------------------------
        // Parametros de localização
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(15 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //--------------------------------------------------------------------------------------------------------------------------------------------------
        // Verificação se o GPS esta ligado
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(builder.build()).addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>()
        {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse)
            {
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                if(e instanceof ResolvableApiException)
                {
                    try
                    {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MapView.this,10);
                    }catch (IntentSender.SendIntentException e1)
                    {
                    }
                }
            }
        });
        //--------------------------------------------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------------------------------------------------------------------------------------------------------
        // Localizaçóes recorrentes
        final LocationCallback locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult)
            {
                if(locationResult == null)
                    return;

                for(Location location : locationResult.getLocations())
                {

                    mMap.getUiSettings().setMyLocationButtonEnabled(true);

                    //AddHeatMap();
                    //Toast.makeText(MapView.this,location.getLatitude()+"",Toast.LENGTH_SHORT).show();
                    //LatLng Localizacao = new LatLng(location.getLatitude(), location.getLongitude());
                   // mMap.addMarker(new MarkerOptions().position(Localizacao));

                    if(!Geocoder.isPresent())
                        return;
                    Localizacao_Usuario = new LatLng(location.getLatitude(),location.getLongitude());
                    //startIntentService(location);
                }
            }

            @Override
            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability)
            {

            }
        };
        client.requestLocationUpdates(locationRequest, locationCallback,null);
        //--------------------------------------------------------------------------------------------------------------------------------------------------
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------


    private void startIntentService(Location location)
    {
        Intent intent =new Intent(this,FetchAddressService.class);
        intent.putExtra(Constants.RECEIVER,resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA,location);
        startService(intent);

    }

//--------------------------------------------------------------------------------------------------------------------------------------------------
    private class AddressResultReceiver extends ResultReceiver {

    public AddressResultReceiver(Handler handler)
    {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData)
    {
        if (resultData == null)
            return;

        final String addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);

        if (addressOutput != null)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   // Toast.makeText(MapView.this,addressOutput,Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}


}
