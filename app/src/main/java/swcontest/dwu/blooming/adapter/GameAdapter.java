package swcontest.dwu.blooming.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

import swcontest.dwu.blooming.R;
import swcontest.dwu.blooming.databinding.ItemCardBinding;
import swcontest.dwu.blooming.dto.GameDto;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder>{
    Vector<GameDto> cards = new Vector<>();

    private Activity activity;
    private Context context;

    private int width = 0, height = 0;

    private boolean startAnimate = false;

    public GameAdapter(Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = ItemCardBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCardBinding binding = holder.binding;

        if (height != 0 && width != 0) {
            binding.pictureLayout.getLayoutParams().width = width;
            binding.pictureLayout.getLayoutParams().height = height;
        }

        GameDto card = cards.get(position);
        String display = card.getDisplay();
        int check = card.getCheck();
        binding.pictureTxtView.setText(display);
        Log.e("check[" + position + "] = ", String.valueOf(check));
        if(check == 0 && startAnimate) {
            binding.pictureTxtView.animate()
                    .rotationY(90)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            binding.pictureTxtView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                            binding.pictureTxtView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                            binding.pictureTxtView.animate()
                                    .rotationYBy(90)
                                    .rotationY(180)
                                    .setDuration(200)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            card.setCheck(1);
                                        }
                                    })
                                    .start();
                        }
                    })
                    .start();
        } else if (check == 2) {
            binding.pictureTxtView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            binding.pictureTxtView.setBackgroundColor(Color.BLACK);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setUpPicture(Vector<GameDto> pictures) {
        this.cards = pictures;
        notifyDataSetChanged();
    }

    public void setLength(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setStartAnimate(boolean startAnimate) {
        this.startAnimate = startAnimate;
        notifyDataSetChanged();
    }

    public void update(int pos, int check) {
        cards.get(pos).setCheck(check);
        notifyItemChanged(pos);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemCardBinding binding;

        ViewHolder(ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}

