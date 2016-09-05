package scott.com.workhard.app.base.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/4/16.
 *          <p>
 *          Copyright (C) 2015 The Android Open Source Project
 *          <p/>
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *          <p/>
 * @see <a href = "http://www.aprenderaprogramar.com" /> http://www.apache.org/licenses/LICENSE-2.0 </a>
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public abstract class BaseSimpleAdapter<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {

    private List<T> items = new ArrayList<>();
    private ListingItemClickListener<T> clickListener;

    public List<T> getItems() {
        return items;
    }

    @Override
    public int getItemCount() {
        validateItemNullAndCreate();
        if (items.size() == 0) {
            return getPositionByRules();
        } else {
            return items.size() + getPositionByRules();
        }
    }

    @Override
    public int getItemViewType(int position) {
        validateItemNullAndCreate();
        if (isHeaderView() && isEntryState()) {
            if (items.size() == 0 && position == 1) {
                return ENTRY_VIEW;
            } else if (items.size() >= 0 && position == 0) {
                return ENTRY_VIEW;
            }
        } else if (isEntryState() && items.size() == 0) {
            return ENTRY_VIEW;
        } else if (isHeaderView() && items.size() >= 0 && position == 0) {
            return HEADER_VIEW;
        }
        return super.getItemViewType(position);
    }

    private void validateItemNullAndCreate() {
        if (items == null) {
            items = new ArrayList<>();
        }
    }

    /**
     * This method find an item adapter by position.
     *
     * @param position Where the item is in the list of base items.
     * @return The item that found in the position or null in case to don't founded.
     */
    public T getItem(int position) {
        if (items != null && position - getPositionByRules() < items.size()) {
            return items.get(position - getPositionByRules());
        }
        return null;
    }

    /**
     * This method clear the base list items and add the new list of items.
     * if you past a null the list of items go to create a new entry list.
     *
     * @param list list of items to insert in the base list of items in the adapter.
     */
    public void addData(@Nullable List<T> list) {
        validateItemNullAndCreate();
        items.clear();
        this.items.addAll(list == null ? new ArrayList<T>() : list);
        notifyDataSetChanged();
    }

    /**
     * This method add the new list of items if you past a null.
     * the list of items go to create a new entry list.
     *
     * @param list list of items to add in the base list of items in the adapter.
     */
    public void uploadData(@Nullable List<T> list) {
        validateItemNullAndCreate();
        this.items.addAll(list == null ? new ArrayList<T>() : list);
        notifyDataSetChanged();
    }

    /**
     * This method find and remove an item adapter by position.
     *
     * @param item to remove in list.
     * @return The true in case to find and remove the items.
     */
    public boolean removeItem(T item) {
        validateItemNullAndCreate();
        if (items.remove(item)) {
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    /**
     * This method find and remove an item adapter by position
     *
     * @param position Where the item is in the list of base items.
     * @return The true in case to find and remove the items.
     */
    public boolean removeItemByPosition(int position) {
        validateItemNullAndCreate();
        if (position < items.size()) {
            items.remove(position);
            notifyItemRemoved(position + getPositionByRules());
            return true;
        }
        return false;
    }

    /**
     * This method add an item adapter and notify all the adapter.
     *
     * @param item the item to insert in list.
     */
    public void addItem(@NonNull T item) {
        validateItemNullAndCreate();
        items.add(item);
        notifyDataSetChanged();
    }

    /**
     * This method add an item adapter by position
     *
     * @param item     the item to insert in list.
     * @param position the position to insert in list
     */
    public void addItemByPosition(@NonNull T item, int position) {
        validateItemNullAndCreate();
        if (position < items.size()) {
            items.add(position, item);
            notifyItemInserted(position + getPositionByRules());
        }
    }

    public ListingItemClickListener<T> getClickListener() {
        return clickListener;
    }

    public void addClickListener(ListingItemClickListener<T> clickListener) {
        this.clickListener = clickListener;
    }

    public int getPositionByRules() {
        return ((isEntryState() && items.size() == 0) ? 1 : 0) + (isHeaderView() ? 1 : 0);
    }

    private interface ListingItemClickListener<T> {
        void onListingViewsClick(T item, int position);
    }

    /**
     * Get position of the item in the list.
     *
     * @param adapterPosition Receive the position of the adapter.
     * @return The Position of the items in the list.
     */
    public int getItemPostion(int adapterPosition) {
        return adapterPosition - getPositionByRules();
    }

    /**
     * Entry State Elements.
     */
    protected static final int ENTRY_VIEW = 2000;
    private boolean entryState = false;

    public boolean isEntryState() {
        return entryState;
    }

    /**
     * Set the entry state value
     *
     * @param entryState Is true if you want user a entry state by default is false.
     */
    public void setEntryState(boolean entryState) {
        this.entryState = entryState;
    }

    protected class EntryViewHolder extends RecyclerView.ViewHolder {

        public EntryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (getClickListener() != null) {
                initListeners();
            }
        }

        private void initListeners() {

        }
    }

    /**
     * Header elements.
     */
    protected static final int HEADER_VIEW = 3000;
    private boolean headerView;

    public boolean isHeaderView() {
        return headerView;
    }

    /**
     * Set the entry state value in constructor builder
     *
     * @param headerView Is true if you want user a entry state by default is false.
     */
    public void setHeaderView(boolean headerView) {
        this.headerView = headerView;
    }


}
