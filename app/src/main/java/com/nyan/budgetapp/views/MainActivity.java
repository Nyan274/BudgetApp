package com.nyan.budgetapp.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nyan.budgetapp.R;
import com.nyan.budgetapp.utils.ViewPagerAdapter;
import com.nyan.budgetapp.views.categories.CategoriesFragment;
import com.nyan.budgetapp.views.graph.GraphFragment;
import com.nyan.budgetapp.views.transactions.TransactionsFragment;

public class MainActivity extends AppCompatActivity {
    TabLayout tabs;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        setupViewPager();
    }

    private void setupViews() {
        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
    }

    private void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        viewPagerAdapter.addFragment(GraphFragment.newInstance(), getString(R.string.graph_title));
        viewPagerAdapter.addFragment(TransactionsFragment.newInstance(), getString(R.string.transaction_title));
        viewPagerAdapter.addFragment(CategoriesFragment.newInstance(), getString(R.string.category_title));
        viewPager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }
}
