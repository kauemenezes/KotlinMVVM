package com.codingwithmitch.foodrecipes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.codingwithmitch.foodrecipes.adapters.OnRecipeListener
import com.codingwithmitch.foodrecipes.adapters.RecipeRecyclerAdapter
import com.codingwithmitch.foodrecipes.util.VerticalSpacingItemDecorator
import com.codingwithmitch.foodrecipes.viewmodels.RecipeListViewModel
import kotlinx.android.synthetic.main.activity_recipe_list.*

class RecipeListActivity : BaseActivity(), OnRecipeListener {

    private val TAG = "RecipeListActivity"
    private lateinit var mRecipeListViewModel: RecipeListViewModel
    private lateinit var mRecyclerView: RecyclerView
    private var mAdapter: RecipeRecyclerAdapter? = null
    private var mSearchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        mRecyclerView = recipe_list

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel::class.java)

        initRecyclerView()
        subscriberObservers()
        initSearchView()
        if (!mRecipeListViewModel.isViewingRecipes()) {
            // display search categories
            displaySearchCategories()
        }
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    private fun subscriberObservers() {
        mRecipeListViewModel.getRecipes().observe(this, Observer {
            it?.let {recipes ->
                if (mRecipeListViewModel.isViewingRecipes()) {
                    mRecipeListViewModel.setIsPerformingQuery(false)
                    mAdapter?.setRecipes(recipes)
                }
            }
        })
    }

    private fun initRecyclerView() {
        mAdapter = RecipeRecyclerAdapter(this)
        val itemDecorator = VerticalSpacingItemDecorator(30)
        mRecyclerView.addItemDecoration(itemDecorator)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!mRecyclerView.canScrollVertically(1)) {
                    // search the next page
                    mRecipeListViewModel.searchNextPage()
                }
            }
        })
    }

    private fun initSearchView() {
        mSearchView = search_view
        mSearchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                mAdapter?.displayLoading()
                mRecipeListViewModel.searchRecipesApi(query, 1)
                mSearchView?.clearFocus()

                return false
            }

        })

    }

    override fun onRecipeClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCategoryClick(category: String) {
        mAdapter?.displayLoading()
        mRecipeListViewModel.searchRecipesApi(category, 1)
        mSearchView?.clearFocus()
    }

    private fun displaySearchCategories() {
        mRecipeListViewModel.setIsViewingRecipes(false)
        mAdapter?.displaySearchCategories()
    }

    override fun onBackPressed() {
        if (mRecipeListViewModel.onBackPressed()) {
            super.onBackPressed()
        } else {
            displaySearchCategories()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recipe_search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_categories) {
            displaySearchCategories()
        }
        return super.onOptionsItemSelected(item)
    }
}
