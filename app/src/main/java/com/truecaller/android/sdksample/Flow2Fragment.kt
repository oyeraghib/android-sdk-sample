package com.truecaller.android.sdksample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.truecaller.android.sdk.TrueProfile
import kotlinx.android.synthetic.main.flow2_layouts.verificationLayout
import kotlinx.android.synthetic.main.flow_home_page.getStartedBtn
import kotlinx.android.synthetic.main.flow_home_page.homeLayout

/**
 * A simple [Fragment] subclass.
 */
class Flow2Fragment : BaseFragment() {

    private lateinit var proceedButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_flow2, container, false)
        proceedButton = view.findViewById(R.id.btnProceed)
        progressBar = view.findViewById(R.id.progress_bar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getStartedBtn.setOnClickListener { fragmentListener.getProfile() }
        proceedButton.setOnClickListener {
            when (proceedButton.tag) {
                PHONE_LAYOUT -> {
                    val phoneNumber = view.findViewById<EditText>(R.id.editPhone).text.toString()
                    fragmentListener.initVerification(phoneNumber)
                }
                OTP_LAYOUT -> {
                    val otp = view.findViewById<EditText>(R.id.editOtp).text.toString()
                    fragmentListener.validateOtp(otp)
                }
                NAME_LAYOUT -> {
                    val fullName = view.findViewById<EditText>(R.id.editFirstName).text.toString()
                    val trueProfile = TrueProfile.Builder(fullName.getFirstName(), fullName.getLastName()).build()
                    fragmentListener.verifyUser(trueProfile)
                }
            }
        }
    }

    fun showVerificationFlow() {
        homeLayout.visibility = View.GONE
        verificationLayout.visibility = View.VISIBLE
        showInputNumberView(false)
    }

    fun closeVerificationFlow() {
        homeLayout.visibility = View.VISIBLE
        verificationLayout.visibility = View.GONE
    }

    fun showInputNumberView(inProgress: Boolean) {
        proceedButton.tag = PHONE_LAYOUT
        progressBar.visibility = if (inProgress) View.VISIBLE else View.GONE
        view?.findViewById<LinearLayout>(R.id.parentLayout)?.visibility = if (inProgress) View.GONE else View.VISIBLE
        proceedButton.visibility = if (inProgress) View.GONE else View.VISIBLE

        view?.findViewById<AppCompatEditText>(R.id.editPhone)?.visibility = View.VISIBLE
        view?.findViewById<AppCompatEditText>(R.id.editOtp)?.visibility = View.GONE
        view?.findViewById<AppCompatEditText>(R.id.editFirstName)?.visibility = View.GONE

        view?.findViewById<AppCompatTextView>(R.id.header)?.text = "Please tell us your\nMobile Number"
        view?.findViewById<AppCompatTextView>(R.id.subHeader)?.text = "Login with your number"
    }

    fun showInputNameView(inProgress: Boolean) {
        proceedButton.tag = NAME_LAYOUT
        progressBar.visibility = if (inProgress) View.VISIBLE else View.GONE
        view?.findViewById<LinearLayout>(R.id.parentLayout)?.visibility = if (inProgress) View.GONE else View.VISIBLE
        proceedButton.visibility = if (inProgress) View.GONE else View.VISIBLE

        view?.findViewById<AppCompatEditText>(R.id.editFirstName)?.visibility = View.VISIBLE
        view?.findViewById<AppCompatEditText>(R.id.editPhone)?.visibility = View.GONE
        view?.findViewById<AppCompatEditText>(R.id.editOtp)?.visibility = View.GONE

        view?.findViewById<AppCompatTextView>(R.id.header)?.text = "Please tell us your\nName"
        view?.findViewById<AppCompatTextView>(R.id.subHeader)?.text = "Complete your profile"
    }

    fun showInputOtpView(inProgress: Boolean) {
        proceedButton.tag = OTP_LAYOUT
        progressBar.visibility = if (inProgress) View.VISIBLE else View.GONE
        view?.findViewById<LinearLayout>(R.id.parentLayout)?.visibility = if (inProgress) View.GONE else View.VISIBLE
        proceedButton.visibility = if (inProgress) View.GONE else View.VISIBLE

        view?.findViewById<AppCompatEditText>(R.id.editOtp)?.visibility = View.VISIBLE
        view?.findViewById<AppCompatEditText>(R.id.editPhone)?.visibility = View.GONE
        view?.findViewById<AppCompatEditText>(R.id.editFirstName)?.visibility = View.GONE

        view?.findViewById<AppCompatTextView>(R.id.header)?.text = "OTP Verification"
        view?.findViewById<AppCompatTextView>(R.id.subHeader)?.text = "Please enter the OTP"
    }
}
