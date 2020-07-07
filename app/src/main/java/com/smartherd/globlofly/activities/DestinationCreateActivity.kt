package com.smartherd.globofly.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.smartherd.globlofly.service.DestinationService
import com.smartherd.globlofly.service.ServiceBuilder
import com.smartherd.globofly.R
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import kotlinx.android.synthetic.main.activity_destiny_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationCreateActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_create)

		setSupportActionBar(toolbar)
		val context = this

		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		btn_add.setOnClickListener {
			val newDestination = Destination()
			newDestination.city = et_city.text.toString()
			newDestination.description = et_description.text.toString()
			newDestination.country = et_country.text.toString()

			// To be replaced by retrofit code
//			SampleData.addDestination(newDestination)
//            finish() // Move back to DestinationListActivity

			val destinationService:DestinationService=ServiceBuilder.buildService(DestinationService::class.java)

			val requestCall: Call<Destination> = destinationService.setDestination(newDestination)

			requestCall.enqueue(object : Callback<Destination> {
				override fun onFailure(call: Call<Destination>, t: Throwable) {
					Toast.makeText(this@DestinationCreateActivity,"Add New Destination Failure",Toast.LENGTH_LONG).show()
				}

				override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
					if(response.isSuccessful){
						var newDestination: Destination? = response.body()
						Toast.makeText(this@DestinationCreateActivity,"Successfully Added",Toast.LENGTH_LONG).show()
						finish()
					}else{
						Toast.makeText(this@DestinationCreateActivity,"Add New Destination Failure",Toast.LENGTH_LONG).show()
					}
				}

			}
			)

		}
	}
}
