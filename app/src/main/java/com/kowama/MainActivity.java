package com.kowama;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.kowama.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button open_submit;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.apptoolbar);
        setSupportActionBar(toolbar);
        open_submit = findViewById(R.id.toolbar_submit_button);
        viewPager = findViewById(R.id.main_activity_view_pager);
        tabLayout = findViewById(R.id.main_activity_tabLayout);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,
                getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        open_submit.setOnClickListener(openSubmitActivity);
    }

    // Open submit activity on button clicked
    private View.OnClickListener openSubmitActivity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent openIntent = new Intent(MainActivity.this,
                    SubmitActivity.class);
            startActivity(openIntent);
        }
    };
}



