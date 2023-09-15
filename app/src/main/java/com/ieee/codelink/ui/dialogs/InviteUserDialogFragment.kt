package com.ieee.codelink.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ieee.codelink.databinding.DialogInviteUserBinding
import com.ieee.codelink.domain.models.Team
import com.ieee.codelink.ui.adapters.InviteUserAdapter

class InviteUserDialogFragment(
    private val teams: List<Team>,
    private val inviteUser: (List<Team>) -> Unit,
) : DialogFragment() {

    private lateinit var binding: DialogInviteUserBinding
    private lateinit var inviteAdapter: InviteUserAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogInviteUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        setOnClicks()
    }

    private fun setUpRv() {
        inviteAdapter = InviteUserAdapter(
            teams as MutableList<Team>
        )
        binding.rvLikes.adapter = inviteAdapter
    }

    private fun setOnClicks() {
        binding.ivCancel.setOnClickListener {
            this.dismiss()
        }
        binding.btnInvite.setOnClickListener {
            inviteUser(inviteAdapter.getCheckedTeams())
            this.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        initDialog()
    }

    private fun initDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requireActivity().setFinishOnTouchOutside(false)
        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        this.dialog!!.window!!.setLayout(((9 * width) / 10), (8 * height) / 10)
    }


}