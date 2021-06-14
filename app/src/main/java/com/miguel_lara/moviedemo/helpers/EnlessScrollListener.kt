package com.miguel_lara.moviedemo.helpers

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miguel_lara.moviedemo.interfaces.ScrollToBottomListener

class EndlessScrollListener(
    linearLayoutManagerParam: LinearLayoutManager,
    listenerParam: ScrollToBottomListener
) : RecyclerView.OnScrollListener() {
    private val linearLayoutManager: LinearLayoutManager = linearLayoutManagerParam
    private val listener: ScrollToBottomListener = listenerParam
    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 3
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    fun onRefresh() {
        previousTotal = 0
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount = recyclerView.getChildCount()
        totalItemCount = linearLayoutManager.getItemCount()
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount
            <= firstVisibleItem + visibleThreshold
        ) {
            listener.onScrollToBottom()
            loading = true
        }
    }

}