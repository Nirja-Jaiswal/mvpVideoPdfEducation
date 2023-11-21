package com.Padhantueducation.view_section.MainActi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.CommonClasses.VolleySingleton;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.models.GetProfileResult;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.Login.LoginSection.LoginActivity;
import com.Padhantueducation.view_section.ui.about.AboutFragment;
import com.Padhantueducation.view_section.ui.doubts.DoubtsActivity;
import com.Padhantueducation.view_section.ui.doubts.DoubtsFragment;
import com.Padhantueducation.view_section.ui.helpLine.HelpLineFragment;
import com.Padhantueducation.view_section.ui.home.Fragment.HomeFragment;
import com.Padhantueducation.view_section.ui.leaderboard.LeaderBoardFragment;
import com.Padhantueducation.view_section.ui.my_cource.MyCourceFragment;
import com.Padhantueducation.view_section.ui.my_order.PurchaseHistoryFragment;
import com.Padhantueducation.view_section.ui.myexamhitory.MyExamHistoryFragment;
import com.Padhantueducation.view_section.ui.mymembership.MyMembershipListFragment;
import com.Padhantueducation.view_section.ui.other_activity.SubscriptionActivity;
import com.Padhantueducation.view_section.ui.profile.ProfileFragment;
import com.Padhantueducation.view_section.ui.terms.TermsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Padhantueducation.CommonClasses.Utils.Get_Device_ID;
import static com.Padhantueducation.CommonClasses.Utils.Share_App;
import static com.Padhantueducation.CommonClasses.Utils.alert_dialoge;
import static com.Padhantueducation.remote.APIUrl.Logout_Api;
import static com.Padhantueducation.remote.APIUrl.PROFILE_BASE_URL;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    public static BottomNavigationView bottomNavView;
    Session session;
    CircleImageView Profile_imageView;
    Toolbar toolbar;
    View hView;
    final Fragment fragment1 = new HomeFragment();
   final Fragment fragment2 = new DoubtsFragment();
    final Fragment fragment3 = new HelpLineFragment();
    final Fragment fragment4 = new ProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    FragmentTransaction transaction;
    TextView nav_user;
     Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);




        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session=new Session(MainActivity.this);

        bottomNavView = findViewById(R.id.bottom_nav_view);
        drawer = findViewById(R.id.drawer_layout);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        displaySelectedScreen(R.id.nav_home);
        getSupportActionBar().setTitle("Home");



        hView =  navigationView.getHeaderView(0);
        nav_user = (TextView)hView.findViewById(R.id.user_name);
        Profile_imageView=hView.findViewById(R.id.imageView);





        bottomNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm.beginTransaction().add(R.id.nav_host_fragment,fragment4, "4").commit();
        fm.beginTransaction().add(R.id.nav_host_fragment,fragment3, "3").commit();
      //  fm.beginTransaction().add(R.id.nav_host_fragment,fragment2, "2").commit();
        fm.beginTransaction().add(R.id.nav_host_fragment,fragment1, "1").commit();
        setHomeItem(MainActivity.this);
        get_USer_profile_api(session.getUserId());



    }









    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.Course_Package_List:
                fragment = new MyMembershipListFragment();
                getSupportActionBar().setTitle("My Membership");
                break;
            case R.id.your_cource:
                fragment = new MyCourceFragment();
                getSupportActionBar().setTitle("My Cource");
                break;
            case R.id.purchase_history:
                fragment = new PurchaseHistoryFragment();
                getSupportActionBar().setTitle("My Order");
                break;
            case R.id.terms_condition:
                fragment = new TermsFragment();
                getSupportActionBar().setTitle("Terms & Condition");
                break;

            case R.id.subs_plan:

                startActivity(new Intent(MainActivity.this, SubscriptionActivity.class));

                break;



            case R.id.nav_myExams:
                fragment=new MyExamHistoryFragment();
                getSupportActionBar().setTitle("My Exams");
                break;

            case R.id.nav_share:
                 Share_App(MainActivity.this);
                break;


            case  R.id.nav_leaderboard:
                fragment = new LeaderBoardFragment();
                getSupportActionBar().setTitle("LeaderBoard");

                break;

            case R.id.nav_about_us:
                fragment = new AboutFragment();
                getSupportActionBar().setTitle("About");
                break;
            case R.id.nav_logout:
                LogoutDialoge(MainActivity.this);
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }


    public  void LogoutDialoge(final Context context)
    {
        final Session session=new Session(context);

        // custom dialog
         dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_logout_dialoge);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        TextView msg=dialog.findViewById(R.id.text);



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout_Api(session.getUserId());
            }
        });

        dialog.show();
    }






    public void Logout_Api(final String user_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Logout_Api,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");

                            if (result.equals("true")) {
                                session.setLogin(false);
                                session.setUserId("");
                                session.setMobile("", "");
                                session.setUser_name("");
                                session.logout();
                                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                dialog.dismiss();
                            }

                            else {
                                Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println("<><><>" + e.getMessage().toString());
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                return params;
            }
        };

        VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

    }







    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.nav_home:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fragment1);
                    transaction.commit();
                    getSupportActionBar().setTitle("Home");
                    return true;

                case R.id.nav_doubts:
                 //   startActivity(new Intent(getActivity(), DoubtsActivity.class));
                    Intent intent1 = new Intent(MainActivity.this, DoubtsActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                    return true;
                case R.id.nav_helpline:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fragment3);
                    transaction.commit();
                    getSupportActionBar().setTitle("HelpLine");
                    return true;

                case R.id.nav_profile:

                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fragment4);
                    transaction.commit();
                    getSupportActionBar().setTitle("Profile");
                    return true;

            }
            return false;
        }
    };



    public static void setHomeItem(Activity activity) {
        bottomNavView.setSelectedItemId(R.id.nav_home);
    }

    private void get_USer_profile_api(String user_id) {
      //  showProgressDialog(MainActivity.this);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<GetProfileResult> userCall = service.Get_user_profile(user_id,Get_Device_ID(MainActivity.this));

        userCall.enqueue(new Callback<GetProfileResult>() {
            @Override
            public void onResponse(Call<GetProfileResult> call, Response<GetProfileResult> response) {


                if(response.body().getResult().equalsIgnoreCase("true")) {
                    String mStrname=response.body().getData().getName();
                    String mStrimage=response.body().getData().getImage();
                    String mStremail=response.body().getData().getEmail();
                    String mStrMobile=response.body().getData().getMobile();
                    String upperString = mStrname.substring(0, 1).toUpperCase() + mStrname.substring(1).toLowerCase();

                    nav_user.setText(upperString);

                    Glide.with(MainActivity.this)
                            .load(PROFILE_BASE_URL + response.body().getData().getImage())
                            .into(Profile_imageView);




               }else {

                    if (response.body().getMsg().equals("invalid_token")) {
                        Utils.Logout_Api(session.getUserId(),MainActivity.this);
                    }

                    alert_dialoge(MainActivity.this,response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<GetProfileResult> call, Throwable t) {
          //      dismissProgressDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }



    @Override
    public void onBackPressed() {
        int seletedItemId = bottomNavView.getSelectedItemId();
        if (R.id.nav_home != seletedItemId) {
            setHomeItem(MainActivity.this);
        } else {
            Utils.Do_you_want_exit_Dialoge(this);

        }
    }




    @Override
    protected void onResume() {

     //   get_USer_profile_api(session.getUserId());
        super.onResume();
    }







}
