package myapp.jeet.com.dagger2android.view;

import android.app.Fragment;
import android.arch.lifecycle.LifecycleFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import myapp.jeet.com.dagger2android.R;

import myapp.jeet.com.dagger2android.databinding.RecipeFragmentBinding;
import myapp.jeet.com.dagger2android.di.Injectable;
import myapp.jeet.com.dagger2android.models.RecipeDetail;
import myapp.jeet.com.dagger2android.models.RecipeDetailItem;
import myapp.jeet.com.dagger2android.viewmodels.RecipeDetailViewModel;

/**
 * Created by Admin on 6/19/2017.
 */

public class RecipeDetailFragment extends LifecycleFragment implements Injectable{
	private android.support.v4.app.Fragment mRecipeFragment;
	RecipeFragmentBinding mRecipeFragmentBinding;

	@Inject
	RecipeDetailViewModel mRecipeDetailViewModel;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRecipeFragmentBinding= DataBindingUtil.inflate(inflater,R.layout.recipe_fragment,container,false);
		View view=mRecipeFragmentBinding.getRoot();
		mRecipeFragmentBinding.setRecipeDetailViewModel(mRecipeDetailViewModel);
		mRecipeFragment=getFragmentManager().findFragmentById(R.id.recipe_fragment);
		mRecipeDetailViewModel.getRecipeData("35120");
		return view;

	}



}
