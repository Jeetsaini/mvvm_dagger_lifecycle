package myapp.jeet.com.dagger2android.helpers;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Admin on 7/3/2017.
 */

public final class DataBinder {
	private DataBinder() {
		//NO-OP
	}

	@BindingAdapter("imageUrl")
	public static void setImageUrl(ImageView imageView, String url) {
		Context context = imageView.getContext();
		Glide.with(context).load(url).into(imageView);
	}
}
