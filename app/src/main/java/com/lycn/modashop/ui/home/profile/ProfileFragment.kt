package com.lycn.modashop.ui.home.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.lycn.modashop.data.model.LoggedInUser
import com.lycn.modashop.databinding.FragmentCartBinding
import com.lycn.modashop.databinding.FragmentProfileBinding
import com.lycn.modashop.ui.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnSignOut.setOnClickListener {
            signOut()
        }

        profileViewModel.loggedInUserResult.observe(viewLifecycleOwner) { data ->
            binding.pbProfileLoading.visibility = View.GONE
            binding.cvProfile.visibility = View.VISIBLE
            bindingLoggedInUser(data)
        }

        return root
    }

    override fun onStart() {
        super.onStart()
        profileViewModel.fetchLoggedInUser()
    }

    private fun signOut() {
        profileViewModel.signOut()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        activity?.finish()
    }

    private fun bindingLoggedInUser(loggedInUser: LoggedInUser) {
        binding.tvName.text = loggedInUser.displayName
        binding.tvEmail.text = loggedInUser.email
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}