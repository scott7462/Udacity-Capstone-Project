package scott.com.workhard.utils;

import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import scott.com.workhard.app.App;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/10/16.
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
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private boolean withHeader = false;
    private int space;

    public SpacesItemDecoration(boolean withHeader, @DimenRes int dimenSpace) {
        this.withHeader = withHeader;
        space = App.getGlobalContext().getResources().getDimensionPixelSize(dimenSpace);
    }

    public SpacesItemDecoration(@DimenRes int dimenSpace) {
        space = App.getGlobalContext().getResources().getDimensionPixelSize(dimenSpace);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        if (withHeader && parent.getChildLayoutPosition(view) == 0) {
            return;
        }
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == (withHeader ? 1 : 0)) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}