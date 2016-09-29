package scott.com.workhard.app.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import scott.com.workhard.R;
import scott.com.workhard.app.ui.create_workout.CreateWorkoutActivity;
import scott.com.workhard.app.ui.home.FrgHome;
import scott.com.workhard.app.ui.profile.FrgProfile;
import scott.com.workhard.app.ui.workout.WorkoutActivity;
import scott.com.workhard.base.view.BaseActivity;
import scott.com.workhard.bus.event.EventAlterDialog;
import scott.com.workhard.entities.Workout;

import static scott.com.workhard.app.ui.home.FrgHome.HISTORY;
import static scott.com.workhard.app.ui.home.FrgHome.HOME;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {

    private static final String CONTENT_FRAGMENT = "content";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dLMain)
    DrawerLayout drawer;
    @BindView(R.id.nVMain)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        savedFragmentState(savedInstanceState);
        iniViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, CONTENT_FRAGMENT, getSupportFragmentManager().findFragmentById(R.id.container));
    }

    private void savedFragmentState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            navigateMainContent(getSupportFragmentManager().getFragment(
                    savedInstanceState, CONTENT_FRAGMENT), getString(R.string.app_name));
        } else {
            navigateMainContent(FrgHome.newInstance(HOME), getString(R.string.app_name));
        }
    }

    private void iniViews() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(this);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        bindHeaderNavigationView();
    }

    private void bindHeaderNavigationView() {
        View headerView = navigationView.getHeaderView(0);
        ImageView iVHomeAvatar = (ImageView) headerView.findViewById(R.id.iVHomeAvatar);
        TextView tVHomeName = (TextView) headerView.findViewById(R.id.tVHomeName);

        //TODO get user from present and show information
        tVHomeName.setText("Pedro Scott");
        iVHomeAvatar.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dLMain);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                navigateMainContent(FrgHome.newInstance(HOME), getString(R.string.app_name));
                break;
            case R.id.nav_history:
                navigateMainContent(FrgHome.newInstance(HISTORY), getString(R.string.app_name));
                break;
            case R.id.nav_profile:
                navigateMainContent(FrgProfile.newInstance(), getString(R.string.frg_profile_title));
                break;
            case R.id.nav_create:
                CreateWorkoutActivity.newInstance(this);
                break;
            case R.id.nav_logout:
                doLogout();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void doLogout() {
        EventBus.getDefault().post(new EventAlterDialog().withMessage(getString(R.string.logout_message))
                .withNegativeButton(getString(R.string.action_not), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .withPositveButton(getString(R.string.action_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }));
    }

    public static void newInstance(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        clearKeyboardFromScreen();
    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    public void goToWorkout(Workout item) {
        WorkoutActivity.newInstance(this, item);
    }
}
