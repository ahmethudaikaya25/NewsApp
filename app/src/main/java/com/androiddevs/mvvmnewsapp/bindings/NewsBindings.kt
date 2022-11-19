package com.androiddevs.mvvmnewsapp.bindings

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@BindingAdapter("imgUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view.context).load(url).into(view)
}

@BindingAdapter("isLoading")
fun View.bindVisibility(visible: Boolean?) {
    isVisible = visible == true
}

@BindingAdapter("recycleScrollListener")
fun addScrollViewListener(view: RecyclerView, listener: RecyclerView.OnScrollListener) {
    view.addOnScrollListener(listener)
}

@BindingAdapter("itemTouchListener")
fun itemTouchListenerHelper(view: RecyclerView, itemTouchHelperCallback: ItemTouchHelper.SimpleCallback) {
    ItemTouchHelper(itemTouchHelperCallback).apply {
        attachToRecyclerView(view)
    }
}

@BindingAdapter("loadWebViewWithUrl")
fun loadWebViewWithUrl(webView: WebView, url: String) {
    webView.apply {
        webViewClient = WebViewClient()
        loadUrl(url)
    }
}