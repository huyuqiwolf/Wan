package com.hlox.app.wan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Fragment mLastFragment;
    private BottomNavigationView mBottomMenu;
    private BottomNavigationView.OnNavigationItemSelectedListener mMenuListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    changeFragment(HomeFragment.class);
                    break;
                case R.id.menu_mine:
                    changeFragment(MineFragment.class);
                    break;
                case R.id.menu_project:
                    changeFragment(ProjectFragment.class);
                    break;
                case R.id.menu_qa:
                    changeFragment(OfficalAccountFragment.class);
                    break;
                default:
            }
            return true;
        }
    };

    private void changeFragment(Class<? extends Fragment> fragmentClass) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(mLastFragment!=null){
            transaction.hide(mLastFragment);
        }
        Fragment fragment = manager.findFragmentByTag(fragmentClass.getName());
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            fragment = createFragment(fragmentClass.getName());
            if (fragment == null) {
                Log.e(TAG, "unknown fragment");
                transaction.commit();
                return;
            }
            transaction.add(R.id.fl_content, fragment, fragmentClass.getName());
        }
        mLastFragment = fragment;
        transaction.commit();
    }

    private Fragment createFragment(String name) {
        if (HomeFragment.class.getName().equals(name)) {
            return new HomeFragment();
        } else if (OfficalAccountFragment.class.getName().equals(name)) {
            return new OfficalAccountFragment();
        } else if (ProjectFragment.class.getName().equals(name)) {
            return new ProjectFragment();
        } else if (MineFragment.class.getName().equals(name)) {
            return new MineFragment();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomMenu = findViewById(R.id.bottom_menu);
        mBottomMenu.setOnNavigationItemSelectedListener(mMenuListener);
        mBottomMenu.setSelectedItemId(R.id.menu_home);
    }
}