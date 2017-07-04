package myapp.jeet.com.dagger2android.viewmodels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.Observable;

import javax.inject.Inject;

import myapp.jeet.com.dagger2android.api.APICallbacks;
import myapp.jeet.com.dagger2android.api.RetroFitRepositry;
import myapp.jeet.com.dagger2android.databinding.ActivityMainBinding;
import myapp.jeet.com.dagger2android.models.SearchResponse;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Admin on 7/3/2017.
 */

public class MainActivityViewModel extends Observable{
	private SearchResponse mSearchResponse;
	public RetroFitRepositry mRetroFitRepositry;
	public ObservableInt progressValue=new ObservableInt();

	private CompositeSubscription compositeSubscription;
    @Inject
	public MainActivityViewModel(RetroFitRepositry mRetroFitRepositry) {
		this.mRetroFitRepositry = mRetroFitRepositry;
	}

	public void loadFoodItems(String query)
	{
		loadData(query);
	}

	private void loadData(String query)
	{
		progressValue.set(View.VISIBLE);

		compositeSubscription=new CompositeSubscription();
		Subscription subscription=mRetroFitRepositry.callSearchAPI(query, new APICallbacks<SearchResponse>() {
			@Override
			public void onSuccess(SearchResponse response) {
				progressValue.set(View.GONE);
				mSearchResponse=response;
				setChanged();
				notifyObservers();
			}

			@Override
			public void onFailed(String error) {

			}
		});
		compositeSubscription.add(subscription);
	}

	public SearchResponse getmSearchResponse() {
		return mSearchResponse;
	}
}
