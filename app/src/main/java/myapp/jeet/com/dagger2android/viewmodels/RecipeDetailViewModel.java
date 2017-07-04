package myapp.jeet.com.dagger2android.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.Observable;

import javax.inject.Inject;

import myapp.jeet.com.dagger2android.api.APICallbacks;
import myapp.jeet.com.dagger2android.api.RetroFitRepositry;
import myapp.jeet.com.dagger2android.models.RecipeDetailItem;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Admin on 7/3/2017.
 */

public class RecipeDetailViewModel{
   private RetroFitRepositry mRetroFitRepositry;
   public ObservableField<String> recipeName=new ObservableField<>();
   public ObservableField<String> recipeTitle=new ObservableField<>();
   public ObservableField<String> imageUrl=new ObservableField<>();
   public ObservableInt progressValue=new ObservableInt();

   @Inject
	public RecipeDetailViewModel(RetroFitRepositry mRetroFitRepositry) {
		this.mRetroFitRepositry = mRetroFitRepositry;
	}

	public void getRecipeData(String id)
	{
		loadData(id);
	}
	private void loadData(String id)
	{
		progressValue.set(View.VISIBLE);
		CompositeSubscription compositeSubscription=new CompositeSubscription();
		Subscription subscription=mRetroFitRepositry.getRecipe(id, new APICallbacks<RecipeDetailItem>() {
			@Override
			public void onSuccess(RecipeDetailItem response) {
				progressValue.set(View.GONE);
				recipeName.set(response.getRecipe().getPublisher());
				recipeTitle.set(response.getRecipe().getTitle());
				imageUrl.set(response.getRecipe().getImage_url());

			}

			@Override
			public void onFailed(String error) {

			}
		});
		compositeSubscription.add(subscription);
	}
}
