package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.smartherd.globlofly.service.MessageService
import com.smartherd.globlofly.service.ServiceBuilder
import com.smartherd.globofly.R
import kotlinx.android.synthetic.main.activity_welcome.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_welcome)

		// To be replaced by retrofit code
//		message.text = "Black Friday! Get 50% cash back on saving your first spot."

		val messageSer:MessageService=ServiceBuilder.buildService(MessageService::class.java)

		val requestCall: Call<String>?=messageSer.getMessage("http://127.0.0.1:7000/messages")

		requestCall?.enqueue(object: Callback<String> {
			override fun onFailure(call: Call<String>, t: Throwable) {
				Toast.makeText(this@WelcomeActivity,"Failed error",Toast.LENGTH_LONG).show()
			}

			override fun onResponse(call: Call<String>, response: Response<String>) {
				if(response.isSuccessful){
					val msg=response.body()
					let {
						message.text=msg
					}
				}
			}

		})
	}

	fun getStarted(view: View) {
		val intent = Intent(this, DestinationListActivity::class.java)
		startActivity(intent)
		finish()
	}
}
