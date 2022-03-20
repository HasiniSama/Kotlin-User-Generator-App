package com.kln.android.usergeneratorapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kln.android.usergeneratorapp.api.UserAPIService
import com.kln.android.usergeneratorapp.databinding.FragmentFirstBinding
import com.kln.android.usergeneratorapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val userAPIService = UserAPIService.create()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val editText = binding.edittextOne.editableText
            val user = userAPIService.getUser(editText.toString());

            user.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {

                    val body = response.body()
                    body?.let {
                        binding.textviewThirteen.text = ""
                        binding.textviewThree.text = it.email
                        binding.textviewFour.text = it.name
                        binding.textviewEleven.text = it.website
                        binding.textviewEight.text = it.address.street
                        binding.textviewNine.text = it.address.suite
                        binding.textviewTen.text = it.address.city

                        val phoneString = StringBuffer(it.phone)
                        if (phoneString.contains("x")){
                            phoneString.replace(phoneString.length-6, phoneString.length, "")
                            binding.textviewTwelve.text = phoneString
                        }else {
                            binding.textviewTwelve.text = phoneString
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    binding.textviewThirteen.text = "Error: Please enter a valid ID"
                }

            })

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}