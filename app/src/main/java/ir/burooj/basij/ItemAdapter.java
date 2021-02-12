package ir.burooj.basij;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Typeface;

import android.os.SystemClock;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

import ir.burooj.basij.apiClass.Like;
import ir.burooj.basij.groups.ListShowActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {


    private  List<Item> itemList;
    private final Context mContext;
    private final String token;
    private final String userId;
    public static ApiInterface apiInterface;
    Activity activity;


    public ItemAdapter(List<Item> itemList, Context mContext, String token,
                       ApiInterface apiInterface1, String userId, Activity activity) {
        this.itemList = itemList;
        this.mContext = mContext;
        this.token = token;
        this.userId = userId;
        this.activity = activity;
        apiInterface = apiInterface1;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ItemAdapter.MyViewHolder(itemView);
    }



    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Item aItem = itemList.get(position);

        //holder.image.setImageResource(R.drawable.defaultpic);
        holder.numberLike.setText("");
        try {
            if (!aItem.getLikes().equals("0")) {
                holder.numberLike.setText(aItem.getLikes() + "  " + "پسند");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.image.setImageResource(R.drawable.defaultpic);

        MainActivity.pica.load(aItem.getImageUrl())
                .placeholder(R.drawable.defaultpic)
                .error(R.drawable.errorpic)
                .into(holder.image);


        MainActivity.pica.load("https://burooj.ir/"+aItem.getProfile_image_path())
                .placeholder(R.drawable.ic_account_logo)
                .error(R.drawable.ic_account_logo)
                .into(holder.logo);


        //لایک بود یا نه

        if (aItem.getLiked().equals("1")) {
            holder.like.setImageResource(R.drawable.like_red_un);
        } else {
            holder.like.setImageResource(R.drawable.like_red);
        }
        holder.user.setText(aItem.getNickname());
        holder.title.setText(aItem.getTitle());
        holder.date.setText(aItem.getDate());
        holder.time.setText(aItem.getTime());
        holder.description.setText(Tools.toSmall(30, aItem.getDescription()));
        //آن کلیک لیست
        holder.aItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShowListItemScrollingActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, holder.image, ViewCompat.getTransitionName(holder.image));

                String[] strings = new String[11];
                strings[0] = aItem.getImageUrl();
                strings[1] = aItem.getTitle();
                strings[2] = aItem.getDescription();
                strings[3] = aItem.getNickname();
                strings[4] = aItem.getDate();
                strings[5] = aItem.getTime();
                strings[6] = aItem.getPostId();
                strings[7] = aItem.getLiked();
                strings[8] = aItem.getLikes();
                strings[9] = String.valueOf(position);
                strings[10]=aItem.getWriterId();
                intent.putExtra("SData", strings);
                mContext.startActivity(intent, options.toBundle());
            }
        });

        holder.time0 = 0;
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long timeInMilliseconds = SystemClock.uptimeMillis();
                if (timeInMilliseconds - holder.time0 < 400) {
                    holder.time0 = 0;
                    like(holder, position);
                } else {
                    holder.time0 = SystemClock.uptimeMillis();
                }
            }
        });


        // لایک
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like(holder, position);
            }
        });
        //اشتراک
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, aItem.getTitle() + "\n" + "\n" + aItem.getDescription() + "\n" + aItem.getDate());
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "The title");
                mContext.startActivity(Intent.createChooser(shareIntent, "اشتراک با:"));
            }
        });

        holder.constraintGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ListShowActivity.class);
                intent.putExtra("id", aItem.getWriterId());
                intent.putExtra("name", aItem.getNickname());
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        });
    }

    private void like(final MyViewHolder holder, final int position) {
        final Item aItem = itemList.get(position);

        Call<Like> like = apiInterface.like(userId, aItem.getPostId(), token);
        like.enqueue(new Callback<Like>() {
            @Override
            public void onResponse(@NotNull Call<Like> call, @NotNull Response<Like> response) {
                Like like1 = response.body();
                if (like1 != null) {
                    if (like1.getResponse().equals("1")) {
                        if (like1.getAction().equals("like")) {
                            // لایک شده

/*                            final ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.3f, 1, 1.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            scaleAnimation.setDuration(200);
                            scaleAnimation.setFillAfter(false);
                            scaleAnimation.setRepeatCount(2);
                            scaleAnimation.setRepeatMode(Animation.REVERSE);
                            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    final ScaleAnimation scaleAnimation = new ScaleAnimation(1.3f, 1, 1.3f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                    scaleAnimation.setDuration(250);
                                    holder.like.startAnimation(scaleAnimation);


                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            holder.like.startAnimation(scaleAnimation);*/

                            final AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1);
                            alphaAnimation.setDuration(200);

                            final ScaleAnimation scaleAnimation = new ScaleAnimation(0.1f, 1.2f, 0.1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

                            AnimationSet animationSet = new AnimationSet(true);
                            animationSet.setDuration(200);
                            animationSet.setFillAfter(true);
                            animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
                            animationSet.addAnimation(alphaAnimation);
                            animationSet.addAnimation(scaleAnimation);
                            animationSet.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    final ScaleAnimation scaleAnimation = new ScaleAnimation(1.2f, 0.9f, 1.2f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                    scaleAnimation.setDuration(200);
                                    holder.like.startAnimation(scaleAnimation);
                                    scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            final ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f, 1, 0.9f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                            scaleAnimation.setDuration(300);
                                            holder.like.startAnimation(scaleAnimation);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            holder.like.startAnimation(animationSet);

                            holder.like.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_red_un));

                            aItem.setLiked("1");
                            aItem.setLikes(String.valueOf((Integer.parseInt(aItem.getLikes()) + 1)));
                            holder.numberLike.setText(aItem.getLikes() + "  " + "پسند");

                            try {
                                holder.imageViewLike.setVisibility(View.VISIBLE);
                                Animation in = AnimationUtils.loadAnimation(activity, android.R.anim.fade_in);
                                in.setDuration(3000);
                                in.setStartOffset(0);
                                holder.imageViewLike.startAnimation(in);


                                Thread time_control = new Thread() {
                                    public void run() {
                                        try {
                                            sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } finally {
                                            activity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Animation in1 = AnimationUtils.loadAnimation(activity, android.R.anim.fade_out);
                                                    in1.setDuration(200);
                                                    in1.setStartOffset(0);
                                                    holder.imageViewLike.startAnimation(in1);
                                                }
                                            });
                                            Thread time_control = new Thread() {

                                                public void run() {
                                                    try {
                                                        sleep(199);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    } finally {
                                                        activity.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                holder.imageViewLike.setVisibility(View.INVISIBLE);

                                                            }
                                                        });
                                                    }
                                                }
                                            };
                                            time_control.start();
                                        }
                                    }
                                };
                                time_control.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else if (like1.getAction().equals("unlike")) {
                            //از لایک در اومد
                            holder.like.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_red));
                            aItem.setLiked("0");
                            try {
                                aItem.setLikes(String.valueOf((Integer.parseInt(aItem.getLikes()) - 1)));

                                if (Integer.parseInt(aItem.getLikes()) > 0) {
                                    holder.numberLike.setText(aItem.getLikes() + "  " + "پسند");
                                } else {
                                    holder.numberLike.setText("");

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "مشکلی پیش آمده  (like.getAction() != (unlike) or (like", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(mContext, "مشکلی پیش آمده (like1.getResponse() != (1", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "مشکلی پیش آمده like = null", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Like> call, Throwable t) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image, like, share,logo;
        TextView user;
        TextView title;
        TextView description;
        TextView date;
        TextView time;
        TextView numberLike;
        LinearLayout aItem;
        long time0;
        ImageView imageViewLike;
        ConstraintLayout constraintGroup;

        MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            user = itemView.findViewById(R.id.txt_user);
            title = itemView.findViewById(R.id.txt_title);
            description = itemView.findViewById(R.id.txt_description);
            date = itemView.findViewById(R.id.txt_Date);
            time = itemView.findViewById(R.id.txt_time);
            aItem = itemView.findViewById(R.id.item_layout);
            like = itemView.findViewById(R.id.likee);
            share = itemView.findViewById(R.id.share);
            numberLike = itemView.findViewById(R.id.like_number);
            imageViewLike = itemView.findViewById(R.id.imageView5682);
            logo = itemView.findViewById(R.id.image_view_group2);
            constraintGroup = itemView.findViewById(R.id.group_open);

            Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "light.ttf");
            description.setTypeface(tf);

        }
    }


    public void updateList(List<Item> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(this.itemList, newList));
        diffResult.dispatchUpdatesTo(this);
    }
}

