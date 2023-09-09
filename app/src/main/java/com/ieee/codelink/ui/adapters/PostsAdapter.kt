package com.ieee.codelink.ui.adapters

import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.common.getTimeDifference
import com.ieee.codelink.data.remote.BASE_URL_FOR_IMAGE
import com.ieee.codelink.databinding.CardPostBinding
import com.ieee.codelink.domain.models.Post


class PostsAdapter(
    var posts: MutableList<Post>,
    var likeClicked: (Post) -> Unit,
    var commentsClicked: (Post) -> Unit,
    var sharesClicked: (Post) -> Unit,
    var blockClicked: (Post) -> Unit,
    var saveClicked: (Post) -> Unit,
    var deleteClicked: (Post) -> Unit,
    var openPostImage: (String?, ImageView) -> Unit,
    var showComments: (Post) -> Unit,
    var showLikes: (Post) -> Unit
) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = posts.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        setViews(holder, post)
        setOnClicks(holder, post)
    }

    private fun setViews(holder: PostsAdapter.ViewHolder, post: Post) {
            setUserImage(holder , post)
            setPostImage(holder, post)
            setPostData(holder, post)
            setPostCounters(holder, post)
            setPostButtons(holder, post)
    }


    private fun setPostButtons(holder: PostsAdapter.ViewHolder, post: Post) {
        holder.binding.apply {
            btnLike.ivBtnImg.setImageResource(R.drawable.ic_like)
            btnLike.tvTxt.text = "Like"
            btnComment.ivBtnImg.setImageResource(R.drawable.ic_comment)
            btnComment.tvTxt.text = "comment"
            btnShare.ivBtnImg.setImageResource(R.drawable.ic_mini_share)
            btnShare.tvTxt.text = "share"
        }
    }

    private fun setPostCounters(holder: PostsAdapter.ViewHolder, post: Post) {
        holder.binding.apply {
            tvCommertsCounter.text = post.comments_count.toString()
            tvLikesCounter.text = post.likes_count.toString()
            tvSharesCounter.text = post.shares_count.toString()
        }
    }

    private fun setPostData(holder: PostsAdapter.ViewHolder, post: Post) {
       holder.binding.apply {
           tvUserName.text = post.user_name
           tvPostTime.text = getTimeDifference(post.updated_at)
           tvDescription.text = post.content
       }
    }

    private fun setPostImage(holder: PostsAdapter.ViewHolder, post: Post) {
        if (post.image_path != null) {
            holder.binding.ivPostImage.visibility = View.VISIBLE

            Glide.with(holder.binding.ivPostImage)
                .load(BASE_URL_FOR_IMAGE + post.image_path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(holder.binding.ivPostImage)
        }else{
            holder.binding.ivPostImage.visibility = View.GONE
        }
    }

    private fun setUserImage(holder: ViewHolder, post: Post) {
        val userImage = post.user_imageUrl
        Glide.with(holder.binding.ivUserImage)
            .load(BASE_URL_FOR_IMAGE + userImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerInside()
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .into(holder.binding.ivUserImage)
    }

    private fun setOnClicks(holder: ViewHolder, post: Post) {
        holder.binding.btnLike.root.setOnClickListener {
            likeClicked(post)
        }
        holder.binding.btnComment.root.setOnClickListener {
            commentsClicked(post)
        }
        holder.binding.btnShare.root.setOnClickListener {
            sharesClicked(post)
        }
        holder.binding.ivMore.setOnClickListener {
            moreClicked(post, it, holder)
        }
        holder.binding.ivPostImage.setOnClickListener {
            post.image_path?.let { url ->
                if (url.isNotBlank()) {
                    openPostImage(BASE_URL_FOR_IMAGE + post.image_path, holder.binding.ivPostImage)
                }
            }
        }

        holder.binding.likeIcon.setOnClickListener {
            showLikes(post)
        }

        holder.binding.tvLikesCounter.setOnClickListener {
            showLikes(post)
        }

        holder.binding.tvCommertsCounter.setOnClickListener {
            showComments(post)
        }

        holder.binding.tvCommertsCounter.setOnClickListener {
            showComments(post)
        }


    }

    private fun moreClicked (post: Post, iv: View, holder: ViewHolder) {

        val popupMenu = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            PopupMenu(
                iv.context,
                holder.binding.ivMore,
                Gravity.END,
                0,
                R.style.PopupMenuStyle
            )
        } else {
            PopupMenu(iv.context, holder.binding.ivMore)
        }
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.post_options_menu, popupMenu.menu)

        if (post.user_id == post.shareduser_id) {
            val menuItemIdToHide = R.id.block
            popupMenu.menu.removeItem(menuItemIdToHide)
        }

        if (post.image_path == null) {
            val menuItemIdToHide = R.id.save
            popupMenu.menu.removeItem(menuItemIdToHide)
        }

        setPostsMenuOnClicks(popupMenu, post)

        popupMenu.show()
    }

    private fun setPostsMenuOnClicks(popupMenu: PopupMenu, post : Post) {
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.block -> {
                    blockClicked(post)
                    true
                }
                R.id.hide -> {
                    deleteClicked(post)
                    true
                }

                R.id.save -> {
                    saveClicked(post)
                    true
                }

                else -> false
            }
        }
    }

    fun like(post: Post, isLiked: Boolean?) {
        isLiked?.let {
            val index = posts.indexOf(post)
            if (isLiked) {
                val updatedIndex = posts[index]
                updatedIndex.likes_count++
                posts[index] = updatedIndex
            } else {
                val updatedIndex = posts[index]
                if (updatedIndex.likes_count > 0) {
                    updatedIndex.likes_count--
                    posts[index] = updatedIndex
                }
            }
            this.notifyItemChanged(index)
        }
    }

    fun addPost(post: Post) {
       posts.add(post)

    }

}