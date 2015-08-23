/*
 * Copyright (c) 2015 Zelory.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package id.zelory.codepolitan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import id.zelory.benih.view.BenihRecyclerListener;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.ui.ReadActivity;
import id.zelory.codepolitan.ui.adapter.MemeAdapter;

/**
 * Created on : August 3, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class MemeFragment extends AbstractHomeFragment
{
    private MemeAdapter memeAdapter;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_meme;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        super.onViewReady(bundle);
        setUpRecyclerView();
    }

    @Override
    protected void setUpAdapter()
    {
        memeAdapter = new MemeAdapter(getActivity());
        memeAdapter.setOnItemClickListener(this::onItemClick);
        recyclerView.setAdapter(memeAdapter);
    }

    private void setUpRecyclerView()
    {
        recyclerView.clearOnScrollListeners();
        recyclerView.setUpAsGrid(2);
        recyclerView.addOnScrollListener(new BenihRecyclerListener((GridLayoutManager) recyclerView.getLayoutManager(), 5)
        {
            @Override
            public void onLoadMore(int i)
            {
                currentPage++;
                articleController.loadArticles("meme", currentPage);
            }
        });
    }

    private void onItemClick(View view, int position)
    {
        Intent intent = new Intent(getActivity(), ReadActivity.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<Article>) memeAdapter.getData());
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void showArticles(List<Article> articles)
    {
        memeAdapter.add(articles);
    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {
        memeAdapter.clear();
        memeAdapter.add(articles);
    }

    @Override
    public void onDestroy()
    {
        if (memeAdapter != null)
        {
            memeAdapter.clear();
            memeAdapter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh()
    {
        super.onRefresh();
        memeAdapter.clear();
        setUpRecyclerView();
        articleController.loadArticles("meme", currentPage);
    }
}