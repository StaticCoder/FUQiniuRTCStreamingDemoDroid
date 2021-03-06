package com.qiniu.pili.droid.rtcstreaming.demo.faceunity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.qiniu.pili.droid.rtcstreaming.demo.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lirui on 2017/1/20.
 */

public class EffectAndFilterSelectAdapter extends RecyclerView.Adapter<EffectAndFilterSelectAdapter.ItemViewHolder> {

    public static final int[] EFFECT_ITEM_RES_ARRAY = {
            R.mipmap.ic_delete_all, R.mipmap.yuguan, R.mipmap.lixiaolong, R.mipmap.matianyu,
            R.mipmap.yazui, R.mipmap.mood, R.mipmap.item0204
    };
    public static final String[] EFFECT_ITEM_FILE_NAME = {"none", "yuguan.bundle", "lixiaolong.bundle",
            "mask_matianyu.bundle", "yazui.bundle", "Mood.bundle", "item0204.bundle"
    };

    public static final int[] FILTER_ITEM_RES_ARRAY = {
            R.mipmap.nature, R.mipmap.delta, R.mipmap.electric, R.mipmap.slowlived, R.mipmap.tokyo, R.mipmap.warm
    };
    public final static String[] FILTERS_NAME = {"nature", "delta", "electric", "slowlived", "tokyo", "warm"};

    public static final int RECYCLEVIEW_TYPE_EFFECT = 0;
    public static final int RECYCLEVIEW_TYPE_FILTER = 1;

    private RecyclerView mOwnerRecyclerView;
    private int mOwnerRecyclerViewType;

    private ArrayList<Boolean> mItemsClickStateList;
    private final int EFFECT_DEFAULT_CLICK_POSITION = 1;
    private final int FILTER_DEFAULT_CLICK_POSITION = 0;
    private int mLastClickPosition = -1;
    private OnItemSelectedListener mOnItemSelectedListener;

    public EffectAndFilterSelectAdapter(RecyclerView recyclerView, int recyclerViewType) {
        mOwnerRecyclerView = recyclerView;
        mOwnerRecyclerViewType = recyclerViewType;

        mItemsClickStateList = new ArrayList<>();
        initItemsClickState();
    }

    @Override
    public int getItemCount() {
        return mOwnerRecyclerViewType == RECYCLEVIEW_TYPE_EFFECT ?
                EFFECT_ITEM_RES_ARRAY.length :
                FILTER_ITEM_RES_ARRAY.length;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        if (mItemsClickStateList.get(position) == null || !mItemsClickStateList.get(position)) {
            holder.mItemView.setUnselectedBackground();
        } else {
            holder.mItemView.setSelectedBackground();
        }

        if (mOwnerRecyclerViewType == RECYCLEVIEW_TYPE_EFFECT) {
            holder.mItemView.setItemIcon(EFFECT_ITEM_RES_ARRAY[position % EFFECT_ITEM_RES_ARRAY.length]);
        } else {
            holder.mItemView.setItemIcon(FILTER_ITEM_RES_ARRAY[position % FILTER_ITEM_RES_ARRAY.length]);
            holder.mItemView.setItemText(FILTERS_NAME[position % FILTER_ITEM_RES_ARRAY.length].toUpperCase());
        }

        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastClickPosition != position) {
                    ItemViewHolder lastItemViewHolder = (ItemViewHolder) mOwnerRecyclerView.findViewHolderForAdapterPosition(mLastClickPosition);
                    if (lastItemViewHolder != null) {
                        lastItemViewHolder.mItemView.setUnselectedBackground();
                    }
                    mItemsClickStateList.set(mLastClickPosition, false);
                }
                holder.mItemView.setSelectedBackground();
                setClickPosition(position);
            }
        });
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(new EffectAndFilterItemView(parent.getContext(), mOwnerRecyclerViewType));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        EffectAndFilterItemView mItemView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemView = (EffectAndFilterItemView) itemView;
        }
    }

    private void initItemsClickState() {
        if (mItemsClickStateList == null) {
            return;
        }
        mItemsClickStateList.clear();
        if (mOwnerRecyclerViewType == RECYCLEVIEW_TYPE_EFFECT) {
            mItemsClickStateList.addAll(Arrays.asList(new Boolean[EFFECT_ITEM_RES_ARRAY.length]));
            setClickPosition(EFFECT_DEFAULT_CLICK_POSITION);
        } else {
            mItemsClickStateList.addAll(Arrays.asList(new Boolean[FILTER_ITEM_RES_ARRAY.length]));
            setClickPosition(FILTER_DEFAULT_CLICK_POSITION);
        }
    }

    private void setClickPosition(int position) {
        if (position < 0) {
            return;
        }
        mItemsClickStateList.set(position, true);
        mLastClickPosition = position;
        if (mOnItemSelectedListener != null) {
            mOnItemSelectedListener.onItemSelected(position);
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int itemPosition);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }
}
