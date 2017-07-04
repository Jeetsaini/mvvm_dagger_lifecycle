package myapp.jeet.com.dagger2android.view;


import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import myapp.jeet.com.dagger2android.R;

import myapp.jeet.com.dagger2android.databinding.ActivityMainBinding;
import myapp.jeet.com.dagger2android.helpers.DividerItemDecoration;
import myapp.jeet.com.dagger2android.helpers.RecyclerTouchListener;
import myapp.jeet.com.dagger2android.models.Recipe;
import myapp.jeet.com.dagger2android.models.SearchResponse;
import myapp.jeet.com.dagger2android.view.adapter.RecipeRecyclerViewAdapter;
import myapp.jeet.com.dagger2android.viewmodels.MainActivityViewModel;


public class MainActivity extends AppCompatActivity implements LifecycleRegistryOwner,HasSupportFragmentInjector,Observer{
	LifecycleRegistry lifecycleRegistry=new LifecycleRegistry(this);
	@Inject
	DispatchingAndroidInjector<android.support.v4.app.Fragment> dispatchingAndroidInjector;
	private List<Recipe> mRecipeList=new ArrayList<>();

	private ActivityMainBinding mMainActivityBinding;
	@Inject
	public MainActivityViewModel mainActivityViewModel;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBinding();
		setupObserver(mainActivityViewModel);
		handleRecyclerView();
		mainActivityViewModel.loadFoodItems("chicken");
	}
	private void initBinding()
	{
		mMainActivityBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
		//mainActivityViewModel=new MainActivityViewModel();
		mMainActivityBinding.setMainViewModel(mainActivityViewModel);
	}
	private void handleRecyclerView()
	{

		mMainActivityBinding.recipeRecyclerView.setHasFixedSize(false);
		LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
		mMainActivityBinding.recipeRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));

		mMainActivityBinding.recipeRecyclerView.setLayoutManager(mLayoutManager);
		mMainActivityBinding.recipeRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mMainActivityBinding.recipeRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, mMainActivityBinding.recipeRecyclerView, new RecyclerTouchListener.ClickListener() {
			@Override
			public void onClick(View view, int position) {
				Intent intent=new Intent(getApplicationContext(),RecipeDetailActivity.class);
				intent.putExtra("recipe_id",mRecipeList.get(position).getRecipe_id());
				startActivity(intent);
			}

			@Override
			public void onLongClick(View view, int position) {

			}
		}));
	}

	public void setupObserver(Observable observable) {
		observable.addObserver(this);
	}

	@Override
	public LifecycleRegistry getLifecycle() {
		return lifecycleRegistry;
	}


	@Override
	public AndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
		return dispatchingAndroidInjector;
	}

	@Override
	public void update(Observable observable, Object o) {
		if(observable instanceof MainActivityViewModel)
		{
			MainActivityViewModel mainActivityViewModel=(MainActivityViewModel)observable;
			SearchResponse searchResponse=mainActivityViewModel.getmSearchResponse();
			mRecipeList=searchResponse.getRecipes();
			RecipeRecyclerViewAdapter recipeRecyclerViewAdapter=new RecipeRecyclerViewAdapter(MainActivity.this,searchResponse.getRecipes());
			mMainActivityBinding.recipeRecyclerView.setAdapter(recipeRecyclerViewAdapter);

		}
	}
}
